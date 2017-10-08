package cn.ou.web;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.aliyuncs.exceptions.ClientException;

import cn.ou.Code.demo;

public class AjaxCheckServlet extends HttpServlet {
	
	/**
	 * 1.先获取前台的手机号码参数
	 * 2.将手机号码参数传进发送短信方法里，OK了
	 * 3.额外：当用户获取验证码，并将码输入到验证框，
	 * 4.RequestServlet去先获取产生出的随机验证码，再获取前端用户输入的验证码
	 * 5.再再进行校验，成功就进行下一步，不匹配就提示重新获取验证码
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		//获取手机号码参数
		String phone = request.getParameter("phonenumber");
		
		
		try {
			//调用函数
			demo.sendSms(phone);
			
			System.out.println("获得的数据   " + phone);  
		} catch (ClientException e) {
			e.printStackTrace();
		}
		
		
		 
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		doGet(request, response);
	}

}
