<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!DOCTYPE HTML>
<html>
	<head>
		<title>欢迎注册EasyMall</title>
		<meta http-equiv="Content-type" content="text/html; charset=UTF-8" />
		<link rel="stylesheet" href="<%= request.getContextPath() %>/css/regist.css"/>
		<script src="<%= request.getContextPath() %>/js/jquery-1.4.2.js"></script>
		<script type="text/javascript" src="<%= request.getContextPath()%>/js/regist.js"></script>
		<script type="text/javascript" src="<%= request.getContextPath()%>/js/sms.js"></script>
		<!-- 点击图片刷新 -->
		<script type="text/javascript" src="<%= request.getContextPath()%>/js/refreshCode.js"></script>
	</head>
	<body>
		<form onsubmit="return checkForm()" 
		action="<%= request.getContextPath() %>/servlet/RequestServlet" method="POST">
			<h1>欢迎注册EasyMall</h1>
			<table>
				<tr>
					<td  style="color:red;text-align:center;" colspan="2" >
						<!-- 如果后台返回信息（参数为空），在此处获取提示消息提示用户
							如果后台无返回信息（参数不为空），则此处为空白  -->
						<%=
							request.getAttribute("msg") ==  null  ? "": request.getAttribute("msg") 
						%>
					</td>
				</tr>
				<tr>
					<td class="tds">用户名：</td>
					<td>
						<input type="text" name="username"
						onblur="checkNull('username','用户名不能为空')"
						value="<%= request.getParameter("username")==null ? "": request.getParameter("username") %>"
						/>
						<span></span>
					</td>
					
				</tr>
				<tr>
					<td class="tds">密码：</td>
					<td>
						<input type="password" name="password"
						onblur="checkNull('password','密码不能为空')"
						value="<%= request.getParameter("password")==null ? "": request.getParameter("password") %>"
						/>
						<span></span>
					</td>
					
				</tr>
				<tr>
					<td class="tds">确认密码：</td>
					<td>
						<input type="password" name="password2"
						onblur="checkPassword('password','两次密码不一致')"
						value="<%= request.getParameter("password2")==null ? "": request.getParameter("password2")  %>"
						/>
						<span></span>
					</td>
				</tr>
				<tr>
					<td class="tds">昵称：</td>
					<td>
						<input type="text" name="nickname"
						onblur="checkNull('nickname','昵称不能为空')"
						value="<%= request.getParameter("nickname")==null ? "": request.getParameter("nickname") %>"
						/>
						<span></span>
					</td>
				</tr>
				<tr>
					<td class="tds">邮箱：</td>
					<td>
						<input type="text" name="email"
						onblur="checkEmail('email','邮箱格式不正确')"
						value="<%= request.getParameter("email")==null ? "": request.getParameter("email") %>"
						/>
						<span></span>
					</td>
				</tr>
				<tr>
					<td class="tds">手机号码：</td>
					<td>
						<input type="text" name="phonenumber"
						onblur="checkNumber('phonenumber','手机号码格式不正确')"
						value="<%= request.getParameter("phonenumber")==null ? "": request.getParameter("phonenumber") %>"
						/>	
						<span></span>
					</td>
				</tr>
				<tr>
					<td class="tds">短信验证码：</td>
					<td>
						<input type="text" name="smsvalistr"
						value="<%= request.getParameter("smsvalistr")==null ? "": request.getParameter("smsvalistr") %>"
						/>
						<input type="button" value="获取验证码" onclick="sendMessage()"/>	
						<span></span>
					</td>
				</tr>
				<tr>
					<td class="tds">验证码：</td>
					<td>
						<input type="text" name="valistr"
						onblur="checkNull('valistr','验证码不能为空')"
						value="<%= request.getParameter("valistr")==null ? "": request.getParameter("valistr") %>"
						/>
						<img src="<%= request.getContextPath() %>/servlet/ValiImageServlet" width="" height="" alt="加载失败" 
						title="看不清点击刷新验证码" id="verification" onclick="refreshcode()"/>
						<span></span>		
					</td>
				</tr>
				<tr>
					<td class="sub_td" colspan="2" class="tds">
						<input type="submit" value="注册用户"/>
					</td>
				</tr>
			</table>
		</form>
	</body>
</html>

