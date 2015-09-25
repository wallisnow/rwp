<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/common/taglibs.jsp" %>
<c:set var="ISEDIT" value="${'TRUE' eq paramsMap.ISEDIT}"/>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
	<title><s:text name="title"/></title>
	<%@ include file="/common/header.jsp"%>
	<script type="text/javascript">
	<!-- 
	cmdSave="/rulemgt/ruleset/save.action";
	cmdEdit="/rulemgt/ruleset/edit.action";
	cmdReturn='/rulemgt/ruleset/list.action';
	
	$(function(){
		initPageEnv("content");
	});

	function doSave(){
		var versionDesc = $("#versionDesc").val();
		if(typeof(versionDesc) == "undefined" || versionDesc == ""){
			alert("请输入版本描述");
			return;
		}
		art.dialog.data("versionDesc", versionDesc);
		$.dialog.close();
		
	}

	function doReturn(){
		$.dialog.close();
	}
	
	-->
	</script>
</head>

<body scroll="no">


<div class="formPageHeader">
	
	<div class="formContent">	
		<p class="nowrap">
			<label>版本描述</label>
			<s:textarea name="versionDesc" rows="13" cols="58" value="%{paramsMap.versionDesc}" cssClass="textInput required"/>
		</p>	
	</div>
	
	<div class="formBar">
		<a href="javascript:void(0);" onclick="doSave();return false;" class="linkbutton" data-options="iconCls:'icon-save'">提交</a>
		<a href="javascript:void(0);" onclick="doReturn();return false;" class="linkbutton" data-options="iconCls:'icon-return'"><s:text name="return"/></a>
	</div>

</div>
</form>
</body>
</html>

