<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/common/taglibs.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    
    <title><s:text name="title"/></title>
	<%@ include file="/common/header.jsp"%>
	<script type="text/javascript">
	<!-- 

		cmdSave="/system/user/save.action";
		cmdReturn="/system/user/list.action";
	
	$(function(){
		initPageEnv("content");
		
	});
	function dochangePwd(){
		if(formCheck("contentForm")){
			var params = {
				userId : $("#userId").val(),
				newPwd : $("#newpassword").val()
			};

			if(!checkPwd(params.newPwd)){
				art.dialog.alert(pwdChkMsg);
				return;
			}
			
			$.getJSON(ctx + "/system/user/adminChangePwdJson.action", params, function(json){
				if("success" == json.status){
					$("div.buttonActive").hide();
					$("input").attr("disabled", "true");
				}
				art.dialog.tips(json.message,"2");
				//art.dialog.close();
				return;
			});
		}
		
	}
	
	function doclose(){
	  	art.dialog.close();
	  	return;
	}
	</script>

</head>

<body scroll="no">

<form method="post" id="contentForm" name="form1" action="">
<input type="hidden" name="userId" id="userId"  value="${paramsMap.userId}"/>


<div class="formPageHeader">
	
	<div class="formContent">

		<p>
			<label>新密码</label>
			<s:password name="newpassword" cssClass="required"/>
		</p>
		<p>
			<label>新密码确认</label>
			<s:password name="newpassword1" equalTo="#newpassword" cssClass="required"/>
		</p>
		
	</div>
	
	<div class="formBar">
		<a href="javascript:void(0);" onclick="dochangePwd();return false;" class="linkbutton" data-options="iconCls:'icon-ok'">确定</a>
		<a href="javascript:void(0);" onclick="doclose();return false;" class="linkbutton" data-options="iconCls:'icon-no'">取消</a>
	</div>

</div>

</form>

</body>
</html>
