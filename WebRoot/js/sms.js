/* 前台获取短信验证码，按钮效果 */
var InterVarobj;//timer变量，控制时间
var count = 60;//间隔函数，1秒执行
var curCount;//当前剩余秒数
var code = "";//验证码
var codeLength = 6;//验证码长度


function sendMessage(){
	curCount = count;
	var phone = $("input[name='phonenumber']").val().trim();
	var span = $("input[name='phonenumber']").next("span");
	if(phone !=''){
		// 设置button效果，开始计时  
        $("input[type='button']").attr("disabled", "true");  
        //$("input[type='button']").val("请在" + curCount + "秒内输入验证码");  
        $("input[type='button']").val(curCount + "秒后再获取"); 
        InterValObj = window.setInterval(SetRemainTime, 1000); // 启动计时器，1秒执行一次  
	}else{  
        $("input[name='phonenumber']").html("<font color='red'>× 手机号码不能为空</font>");  
    } 
}

//timer处理函数  
function SetRemainTime() {  
  if (curCount == 0) {                  
      window.clearInterval(InterValObj);// 停止计时器  
      $("input[type='button']").removeAttr("disabled");// 启用按钮  
      $("input[type='button']").val("重新发送验证码");  
      code = ""; // 清除验证码。如果不清除，过时间后，输入收到的验证码依然有效  
  }else {  
      curCount--;  
      //$("input[type='button']").val("请在" + curCount + "秒内输入验证码");  
      $("input[type='button']").val(curCount + "秒后再获取");
  }  
}  