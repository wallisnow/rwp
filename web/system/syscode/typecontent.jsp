<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/common/taglibs.jsp" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    
    <title><s:text name="title"/></title>
	<%@ include file="/common/header.jsp"%>

	<script type="text/javascript">
	<!-- 
	
	cmdSave="/system/syscode/typesave.action";
	cmdEdit="/system/syscode/typeedit.action";
	cmdReturn="/system/syscode/typelist.action";
	
	$(function(){
		initPageEnv("content");
		contentFormControl();
	});
	-->
	</script>

</head>
  
<body scroll="no">

<h2 class="contentTitle"><s:text name="title"/><cx:Msg/></h2>

<form method="post" id="contentForm" action="">
<s:hidden name="seqId" value="%{paramsMap.seqId}"/>
<s:hidden name="kind" value="%{paramsMap.kind}"/>
<input type="hidden" name="ISNEW" value="${paramsMap.ISNEW}"/>
<input type="hidden" name="ISEDIT" value="${paramsMap.ISEDIT}"/>

<div class="formPageHeader">
	
	<div class="formContent">
		<p>
			<label><s:text name="type"/></label>
			<s:textfield name="type" value="%{paramsMap.type}" cssClass="required enableOnlyNew"/>
		</p>
		<p>
			<label><s:text name="name"/></label>
			<s:textfield name="name" value="%{paramsMap.name}" cssClass="required"/>
		</p>
	</div>
	
	<div class="formBar">
		<c:if test="${'TRUE' eq paramsMap.ISEDIT}">
			<a href="javascript:void(0);" onclick="doSave();return false;" class="linkbutton" data-options="iconCls:'icon-save'"><s:text name="save"/></a>
		</c:if>
		<c:if test="${'TRUE' ne paramsMap.ISEDIT}">
			<a href="javascript:void(0);" onclick="doEditContent();return false;" class="linkbutton" data-options="iconCls:'icon-edit'"><s:text name="edit"/></a>
		</c:if>
		<a href="javascript:void(0);" onclick="doReturn();return false;" class="linkbutton" data-options="iconCls:'icon-return'"><s:text name="return"/></a>
	</div>
</div>

</form>

</body>
</html>
