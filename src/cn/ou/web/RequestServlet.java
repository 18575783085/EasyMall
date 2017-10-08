package cn.ou.web;

import static org.hamcrest.CoreMatchers.nullValue;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.exceptions.ClientException;
import com.mchange.v2.c3p0.ComboPooledDataSource;

import cn.ou.Code.demo;
import cn.ou.Util.JdbcUtils;
import cn.ou.Util.WebUtils;

public class RequestServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//1.解决乱码
		//1.1解决请求参数乱码（post提交）
		request.setCharacterEncoding("utf-8");
		//1.2解决响应正文乱码
		response.setContentType("text/html;charset=utf-8");
		
		//2.获取用户参数
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		String password2 = request.getParameter("password2");
		String nickname = request.getParameter("nickname");
		String email = request.getParameter("email");
		String phonenumber = request.getParameter("phonenumber");
		String smsvalistr = request.getParameter("smsvalistr");
		String valistr = request.getParameter("valistr");
		
		//3.数据校验（将信息响应到前端）
		//3.1判断参数是否为空,如果为空，请求转发回到注册页面
		if(WebUtils.isNull(username)){
			//如果用户名为空，跳转回注册页面提示用户“用户名不能为空！”
			request.setAttribute("msg", "用户名不能为空！");
			request.getRequestDispatcher("/regist.jsp").forward(request, response);
			return;
		}
		if(WebUtils.isNull(password)){
			request.setAttribute("msg", "密码不能为空！");
			request.getRequestDispatcher("/regist.jsp").forward(request, response);
			return;
		}
		if(WebUtils.isNull(password2)){
			request.setAttribute("msg", "确认密码不能为空！");
			request.getRequestDispatcher("/regist.jsp").forward(request, response);
			return;
		}
		if(WebUtils.isNull(nickname)){
			request.setAttribute("msg", "昵称不能为空！");
			request.getRequestDispatcher("/regist.jsp").forward(request, response);
			return;
		}
		if(WebUtils.isNull(email)){
			request.setAttribute("msg", "邮箱不能为空！");
			request.getRequestDispatcher("/regist.jsp").forward(request, response);
			return;
		}
		if(WebUtils.isNull(phonenumber)){
			request.setAttribute("msg", "手机号码不能为空！");
			request.getRequestDispatcher("/regist.jsp").forward(request, response);
			return;
		}
		if(WebUtils.isNull(smsvalistr)){
			request.setAttribute("msg", "短信验证码不能为空！");
			request.getRequestDispatcher("/regist.jsp").forward(request, response);
			return;
		}
		if(WebUtils.isNull(valistr)){
			request.setAttribute("msg", "验证码不能为空！");
			request.getRequestDispatcher("/regist.jsp").forward(request, response);
			return;
			
		}
		
		//3.2判断两次密码是否一致
		if(!password.equals(password2)){
			request.setAttribute("msg", "两次密码不一致！");
			request.getRequestDispatcher("/regist.jsp").forward(request, response);
			return;
		}
		
		//3.3判断邮箱格式是否正确
		//邮箱正则：^\\w+@\\w+(\\.[a-z]+)+$
		if(!email.matches("^\\w+@\\w+(\\.[a-z]+)+$")){
			request.setAttribute("msg", "邮箱格式不正确！");
			request.getRequestDispatcher("/regist.jsp").forward(request, response);
			return;
		}
		
		//3.4判断验证码是否一致（根据 手机 获取的 验证码，再进行 判断）
		//3.4.1.判断手机号码是否是11位
		if(!phonenumber.matches("^1[3|5|8][0-9]{9}$")){
			request.setAttribute("msg", "手机号码格式不正确！");
			request.getRequestDispatcher("/regist.jsp").forward(request, response);
			return;
		}
		/**
		 * 考虑问题：应该是通过前端的获取验证码按钮来获取验证码，而不是通过注册提交按钮来获取验证码！！！？？
		 * (1)前端获取验证码
		 * (2)后台通过提交按钮来判断，两次验证码是否匹配
		 */
		/*//3.4.2调用短信api接口
		try {
			//调用方法,通过前端获取手机号码参数→发短信
			//创建对象，调用获取产生的随机码
			demo d = new demo();
			SendSmsResponse sendmsm = demo.sendSms(phonenumber);
			
			//3.4.3获取用户输入的短信验证码，跟确认验证码进行判断
			if(!smsvalistr.equals(d.getCode()) && smsvalistr != d.getCode()){
				request.setAttribute("msg", "验证码不正确");
				request.getRequestDispatcher("/regist.jsp").forward(request, response);
				return;
			}
		} catch (ClientException e) {
			e.printStackTrace();
		}*/
		
		//4.注册用户（将注册信息保存到数据库）
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		try {
			ComboPooledDataSource cpds = new ComboPooledDataSource();
			conn = cpds.getConnection();
			
			//4.1判断用户名是否已存在
			String sql = "select * from user where username=?";
			
			ps = conn.prepareStatement(sql);
			
			ps.setString(1, username);
			rs = ps.executeQuery();
			
			if(rs.next()){//用户名已存在
				request.setAttribute("msg", "用户名已存在！");
				request.getRequestDispatcher("/regist.jsp").forward(request, response);
				return;
			}
			//?????
			ps.close();
			
			//用户名不存在，将用户注册数据保存进数据库
			sql = "insert into user values(null,?,?,?,?)";
			ps = conn.prepareStatement(sql);
			
			//设置参数
			ps.setString(1, username);
			ps.setString(2, password);
			ps.setString(3, nickname);
			ps.setString(4, email);
			
			//执行sql语句
			ps.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			//关闭资源
			JdbcUtils.close(conn, ps, rs);
		}
		
		
		//5.提示用户注册成功，3秒之后跳转到主页
		response.getWriter().write("<h1 style='color:red;text-align:center'>" +
				"恭喜您注册成功, 3秒之后将会跳转到主页...</h1>");
		response.setHeader("Refresh", "3;url="+request.getContextPath()+"/index.jsp");
		
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		doGet(request, response);
	}

}
