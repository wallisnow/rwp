<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/common/taglibs.jsp" %>
<c:set var="ISEDIT" value="${'TRUE' eq paramsMap.ISEDIT}" />

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    
    <title><s:text name="title"/></title>
	<%@ include file="/common/header.jsp"%>

	<script type="text/javascript">
	<!-- 
	cmdSave="/system/user/save.action";
	cmdEdit="/system/user/edit.action";
	cmdReturn="/system/user/list.action";
	
	$(function(){
		initPageEnv("content");
		contentFormControl();
	});
	-->
	</script>

</head>

<body>

<h2 class="contentTitle"><s:text name="title"/><cx:Msg/></h2>

<form method="post" id="contentForm" action="">
<input type="hidden" name="ISNEW" value="${paramsMap.ISNEW}"/>
<input type="hidden" name="ISEDIT" value="${paramsMap.ISEDIT}"/>

<div class="formPageHeader">
	
	<div class="formContent">
		<p>
			<label><s:text name="userId"/></label>
			<s:textfield name="userId" value="%{paramsMap.userId}" cssClass="required enableOnlyNew"/>
		</p>
		<p>
			<label><s:text name="userName"/></label>
			<s:textfield name="userName" value="%{paramsMap.userName}" cssClass="required"/>
		</p>
		<p>
			<label><s:text name="email"/></label>
			<s:textfield name="email" value="%{paramsMap.email}" cssClass="email required"/>
		</p>
		<p>
			<label><s:text name="mobileno"/></label>
			<s:textfield name="mobileno" value="%{paramsMap.mobileno}" cssClass="required"/>
		</p>
		<p>
			<label><s:text name="dpId"/></label>
			<s:textfield name="dpId" value="%{paramsMap.dpId}"/>
		</p>
		<p>
			<label><s:text name="userType"/></label>
			<cx:select list="$USERTYPE" name="userType" cssClass="combox required" value="%{paramsMap.userType}" emptyOption="true" headerKey="" headerValue="请选择用户类型"/>
		</p>
		<p>
			<label><s:text name="status"/></label>
			<cx:select list="$TORF" name="status" cssClass="combox required" value="%{paramsMap.status}" emptyOption="true" headerKey="" headerValue="请选择状态"/>
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
