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
	
	cmdSave="/system/menumonitorconfig/save.action";
	cmdEdit="/system/menumonitorconfig/edit.action";
	cmdReturn="/system/menumonitorconfig/list.action";
	
	$(function(){
		initPageEnv("content");
		contentFormControl();
	});
	-->
	</script>

</head>
  
<body scroll="no">

<form method="post" id="contentForm" action="">
<input type="hidden" name="ISNEW" value="${paramsMap.ISNEW}"/>
<input type="hidden" name="ISEDIT" value="${paramsMap.ISEDIT}"/>

<div class="formPageHeader">
	
	<div class="formContent">
		<p class="nowrap">
			<label><s:text name="monitorId"/></label>
			<s:textfield name="monitorId" value="%{paramsMap.monitorId}" readonly="true"/>
		</p>
		<p class="nowrap">
			<label><s:text name="monitorName"/></label>
			<s:textfield name="monitorName" value="%{paramsMap.monitorName}" cssClass="required" cssStyle="width:400px;"/>
		</p>
		<p class="nowrap">
			<label><s:text name="monitorUrl"/></label>
			<s:textfield name="monitorUrl" value="%{paramsMap.monitorUrl}" cssClass="required" cssStyle="width:400px;"/>
		</p>
		<p class="nowrap">
			<label><s:text name="memo"/></label>
			<s:textfield name="memo" value="%{paramsMap.memo}" cssStyle="width:400px;"/>
		</p>
	</div>
	
	<div class="formBar">
		<c:if test="${ISEDIT}">
		<a href="javascript:void(0);" onclick="doSave();return false;" class="linkbutton" data-options="iconCls:'icon-save'"><s:text name="save"/></a>
		</c:if>
		<c:if test="${!ISEDIT}">
		<a href="javascript:void(0);" onclick="doEditContent();return false;" class="linkbutton" data-options="iconCls:'icon-edit'"><s:text name="edit"/></a>
		</c:if>
		<a href="javascript:void(0);" onclick="doReturn();return false;" class="linkbutton" data-options="iconCls:'icon-return'"><s:text name="return"/></a>
	</div>

</div>

</form>

</body>
</html>
