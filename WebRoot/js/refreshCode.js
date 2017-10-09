/* 点击图片刷新验证码 */
function refreshcode(){
	document.getElementById("verification").src="/servlet/ValiImageServlet?now="+new Date();
}