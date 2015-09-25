<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/common/taglibs.jsp" %>
<c:set var="ISEDIT" value="${'TRUE' eq paramsMap.ISEDIT}" />
<c:set var="ISSUC" value="${'TRUE' eq paramsMap.ISSUC}" />
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    
    <title><s:text name="title"/></title>
	<%@ include file="/common/header.jsp"%>

	<script type="text/javascript">
	<!-- 
	cmdSave="/system/role/save.action";
	cmdEdit="/system/role/edit.action";
	cmdReturn="/system/role/list.action";
	
	$(function(){
		initPageEnv("content");
		contentFormControl();
		if(${ISSUC}){
			art.dialog.close();
		}
	});
	
	function doSaveJson(){
		var url = ctx + "/system/role/saveRoleJson.action";;
		var formData = $("form").serializeArray();
		formData["dt"] = new Date() + Math.random();
		formData["roleName"] = encodeURI(encodeURI($('#roleName').val()));
		$.getJSON(url, formData, function(result){
			if("success" == result.status){
				art.dialog.close();
			}else{
				art.dialog.alert(result.message);
			}
		});
	}
	-->
	</script>

</head>
  
<body scroll="no">

<h2 class="contentTitle"><s:text name="title"/><cx:Msg/></h2>

<form method="post" id="contentForm" action="">
<input type="hidden" name="ISNEW" value="${paramsMap.ISNEW}"/>
<input type="hidden" name="ISEDIT" value="${paramsMap.ISEDIT}"/>

<div class="formPageHeader">
	
	<div class="formContent">
		<p>
			<label><s:text name="roleId"/></label>
			<s:textfield name="roleId" value="%{paramsMap.roleId}" readonly="true" />
		</p>
		<p>
			<label><s:text name="roleName"/></label>
			<s:textfield id="roleName" name="roleName" value="%{paramsMap.roleName}" cssClass="required"/>
		</p>
		<p>
			<label><s:text name="status"/></label>
			<cx:select list="$TORF" name="status" cssClass="combox required" value="%{paramsMap.status}" emptyOption="true" headerKey="" headerValue="请选择状态"/>
		</p>
		<p>
			<label><s:text name="beginTime"/></label>
			<cx:textfield name="beginTime" value="%{paramsMap.beginTime}" cssClass="required Wdate" onclick="WdatePicker({isShowClear:false,readOnly:true, dateFmt:'yyyy-MM-dd 00:00:00'})"/>
		</p>
		<p>
			<label><s:text name="endTime"/></label>
			<cx:textfield name="endTime" value="%{paramsMap.endTime}" cssClass="required Wdate" onclick="WdatePicker({isShowClear:false,readOnly:true, dateFmt:'yyyy-MM-dd 23:59:59'})"/>
		</p>
		<p class="nowrap">
			<label><s:text name="roleDesc"/></label>
			<s:textarea id="roleDesc" name="roleDesc" rows="4" cols="60" value="%{paramsMap.roleDesc}" cssClass="required"></s:textarea>
		</p>
	</div>
	
	<div class="formBar">
		<c:if test="${ISEDIT}">
			<a href="javascript:void(0);" onclick="doSave();return false;" class="linkbutton" data-options="iconCls:'icon-save'"><s:text name="save"/></a>
		</c:if>
		<c:if test="${!ISEDIT}">
			<a href="javascript:void(0);" onclick="doEditContent();return false;" class="linkbutton" data-options="iconCls:'icon-edit'"><s:text name="edit"/></a>
		</c:if>
	</div>

</div>

</form>

</body>
</html>
