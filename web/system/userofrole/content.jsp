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
	cmdSave="/system/userofrole/save.action";
	cmdEdit="/system/userofrole/edit.action";
	cmdReturn="/system/userofrole/list.action";
	
	$(function(){
		initPageEnv("content");
		contentFormControl();
	});
	-->
	</script>

</head>
  
<body scroll="no">

<form method="post" id="contentForm" action="">
<input type="hidden" name="urId" value="${paramsMap.urId}"/>
<input type="hidden" name="ISNEW" value="${paramsMap.ISNEW}"/>
<input type="hidden" name="ISEDIT" value="${paramsMap.ISEDIT}"/>

<div class="formPageHeader">
	
	<div class="formContent">
		<p>
			<label><s:text name="userId"/></label>
			<s:textfield name="userId" value="%{paramsMap.userId}" readonly="true" cssClass="required"/>
			<a href="javascript:void(0)" class="btnLook" onclick='selectUser("userId", "userName");'></a>
		</p>
		<p>
			<label><s:text name="userName"/></label>
			<input name="userName" id="userName" readonly value='<cx:Code2Name definition="!com.congxing.system.user.model.UserVO*userId*userName" value="${paramsMap.userId}"/>'/>
		</p>
		<p class="nowrap" style="height:1px;"></p>
		<p>
			<label><s:text name="roleId"/></label>
			<s:textfield name="roleId" value="%{paramsMap.roleId}" readonly="true"  cssClass="required"/>
			<a href="javascript:void(0)" class="btnLook" onclick='selectRole("roleId", "roleName");'></a>
		</p>
		<p>
			<label><s:text name="roleName"/></label>
			<input name="roleName" id="roleName" readonly value='<cx:Code2Name definition="!com.congxing.system.role.model.RoleVO*roleId*roleName" value="${paramsMap.roleId}"/>'/>
		</p>
		<p class="nowrap" style="height:1px;"></p>
		<p>
			<label><s:text name="status"/></label>
			<cx:select list="$TORF" name="status" cssClass="required combox" value="%{paramsMap.status}" emptyOption="true" headerKey="" headerValue="请选择状态"/>
		</p>
		<p>
			<label><s:text name="beginTime"/></label>
			<cx:textfield name="beginTime" value="%{paramsMap.beginTime}" cssClass="required Wdate" onclick="WdatePicker({isShowClear:false,readOnly:true,dateFmt:'yyyy-MM-dd 00:00:00'})"/>
		</p>
		<p>
			<label><s:text name="endTime"/></label>
			<cx:textfield name="endTime" value="%{paramsMap.endTime}" cssClass="required Wdate" onclick="WdatePicker({isShowClear:false,readOnly:true,dateFmt:'yyyy-MM-dd 23:59:59'})"/>
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
