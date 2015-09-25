<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/common/taglibs.jsp" %>
<c:set var="ISEDIT" value="${'TRUE' eq paramsMap.ISEDIT}"/>
<c:set var="ISNEW" value="${'TRUE' eq paramsMap.ISNEW}"/>

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
		contentFormControl();
	});
	-->
	</script>
</head>

<body scroll="no">

<h2 class="contentTitle"><s:text name="title"/><cx:Msg/></h2>

<form method="post" id="contentForm" action="">
<input type="hidden" name="ISNEW" value="${paramsMap.ISNEW}"/>
<input type="hidden" name="ISEDIT" value="${paramsMap.ISEDIT}"/>
<input type="hidden" name="rulesetId" value="${paramsMap.rulesetId}" />
<input type="hidden" name="rulesetVersion" value="${paramsMap.rulesetVersion}" />

<div class="formPageHeader">
	
	<div class="formContent">
		<p class="nowrap">
			<label><s:text name="rulesetType"/></label>
			<cx:select name="rulesetType" list="$CJ_TYPE" headerKey="" cssClass="required" headerValue="请选择" emptyOption="true" value='%{paramsMap.get("rulesetType")}' />
		</p>	
		<p class="nowrap">
			<label><s:text name="rulesetName"/></label>
			<input name="rulesetName" type="text" value="${paramsMap.rulesetName}" class="required"/>
		</p>	
		<p class="nowrap">
			<label><s:text name="rulesetDesc"/></label>
			<s:textarea name="rulesetDesc" rows="3" cols="70" value="%{paramsMap.rulesetDesc}" cssClass="required"/>
		</p>
		<c:if test="${ISNEW}">
			<p class="nowrap">
				<label><s:text name="versionDesc"/></label>
				<s:textarea name="versionDesc" rows="3" cols="70" value="%{paramsMap.versionDesc}" cssClass="required"/>
			</p>	
		</c:if>
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

