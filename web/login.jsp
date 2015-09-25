<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>汕头移动数据挖掘平台业务规则库</title>
<link href="${ctx}/css/login.css" rel="stylesheet" type="text/css" />
<script src="${ctx}/js/jquery-1.7.2.js" type="text/javascript"></script>

<script src="${ctx}/js/artdialog/jquery.artDialog.js?skin=blue"
	type="text/javascript"></script>
<script src="${ctx}/js/artdialog/plugins/iframeTools.js"
	type="text/javascript"></script>

<script type="text/javascript">
function login(){
	var userId = $("input[name='userId']").val();
	var password = $("input[name='password']").val(); 
	if(userId == ""){
		art.dialog.alert("请输入登录工号");
		$("input[name='userId']").focus();
		return;
	}
	if(password == ""){
		art.dialog.alert("请输入密码");
		$("input[name='password']").focus();
		return;
	}
	$("#loginForm").submit();
}

function cancle(){
	$("input[name='userId']").val("");
	$("input[name='password']").val("");
	$("input[name='userId']").focus();
}


document.onkeydown = function(e) {
	// 兼容FF和IE和Opera
	var theEvent = e || window.event;
	var code = theEvent.keyCode || theEvent.which || theEvent.charCode;//获取键盘code值
	if (code == 13) {  //如果是回车键
		login();
	}
}

</script>

</head>
<body>
	<div class="login_table">
		<form id="loginForm" action="${ctx}/system/login.action">
			<div class="login_form">
				<div class="login_input">
					<input name="userId" type="text" class="textInput" value="CXCS01" />
				</div>
				<div class="login_input">
					<input name="password" type="password" class="textInput" value="1" />
				</div>
				<div class="login_button">
					<input name="" type="button" class="login_button1a"
						onmousedown=this.className=
						"login_button3a" onmouseover=this.className=
						"login_button1b" onmouseout=this.className=
						"login_button1a" onclick="login();" /><input name="" type="button"
						class="login_button2a" onmousedown=this.className=
						"login_button3b" onmouseover=this.className=
						"login_button2b" onmouseout=this.className=
						"login_button2a" onclick="cancle();" />
				</div>
				<div>
					<cx:Msg />
				</div>
			</div>
		</form>
	</div>
</body>
</html>