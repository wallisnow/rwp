<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/common/taglibs.jsp" %>
<c:set var="isEditable" value="${'TRUE' eq ISEDIT}" scope="request"/>
<c:set var="isCreative" value="${'TRUE' eq ISNEW}" scope="request"/>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    
    <title><s:text name="title"/></title>
	<%@ include file="/common/header.jsp"%>

	<script type="text/javascript">

	cmdDownfile = "/system/download/downFileByFullFilePath.action";
	
	$(function(){
		initPageEnv("content");
	});

	function downFile(){
		//var url = ctx + cmdDownfile;
		//var form = document.forms[0];
		//form.action = url;
		//form.submit();
		window.location.href = ctx + "/system/download/downFileByAttachId.action?attachId=137033638551572";
	}
	</script>

</head>
  
<body scroll="no">

<form method="post" id="contentForm" action="${ctx}/system/attach/downFileByFullFilePath.action">
<div class="pageMsgInfo">
	<cx:Msg/>
</div>

<div class="formPageHeader">
	
	<div class="formContent">
		<p class="nowrap">
			<label>文件全路径：</label>
			<s:textfield name="fullFilePath" value="%{paramsMap.fullFilePath}"  cssClass="required" cssStyle="width:40%;"/>
		</p>
	</div>
	
	<div class="formBar">
		<ul>
			<li><div class="buttonActive"><div class="buttonContent"><button type="button" onclick="downFile();">下载文件</button></div></div></li>
		</ul>
	</div>

</div>

</form>

</body>
</html>
