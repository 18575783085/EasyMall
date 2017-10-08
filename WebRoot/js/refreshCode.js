/* 点击图片刷新验证码 */
function refreshcode(){
	document.getElementById(verification).src="<%= request.getContextPath() %>/servlet/ValiImageServlet?flag="+Math.random();
}