<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/common/taglibs.jsp" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    
    <title><s:text name="title"/></title>
	<%@ include file="/common/header.jsp"%>

	<script type="text/javascript">
	<!-- 
	cmdSave="/system/menu/save.action";
	cmdAdd="/system/menu/add.action";
	cmdEdit="/system/menu/edit.action";
	cmdReturn="/system/menu/list.action";

	$(function(){
		initPageEnv("content");
		contentFormControl();
	});
	
	function doAddMenu(){
		enableForm();
		var url = ctx + cmdAdd;
		var form = document.forms[0];
		form.action = url;
		form.submit();
	}

	function doDeleteMenu(){
		var menuId = $("#menuId").val();
		if("" == menuId || menuId.length == 1){
			art.dialog.alert("请选择菜单进行删除操作");
			return;
		}
		$.dialog.confirm("是否确认删除菜单【" + $("#menuName").val() + "】", deleteMenu);
	}

	function deleteMenu(){
		var params = {"menuId" : $("#menuId").val(), "dt" : new Date() + Math.random()};
		$.getJSON(ctx + "/system/menu/deleteMenuJson.action", params, function(result) {
			if("success" == result.status) {
				art.dialog.tips(result.message);
				$("div.formBar").hide();
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
<s:hidden name="seqId" value="%{paramsMap.seqId}"/>
<s:hidden name="parentMenuId" value="%{paramsMap.parentMenuId}"/>


<div class="formPageHeader">
	
	<div class="formContent">
		<p class="nowrap">
			<label><s:text name="menuId"/></label>
			<s:textfield name="menuId" value="%{paramsMap.menuId}" readonly="true"/>
		</p>
		<p class="nowrap">
			<label><s:text name="menuName"/></label>
			<s:textfield name="menuName" value="%{paramsMap.menuName}" cssClass="required editControl" cssStyle="width:300px"/>
		</p>
		<p class="nowrap">
			<label><s:text name="status"/></label>
			<cx:select list="$TORF" name="status" cssClass="required combox editControl" value="%{paramsMap.status}" emptyOption="true" headerKey="" headerValue="请选择状态"/>
		</p>
		<p class="nowrap">
			<label><s:text name="menuType"/></label>
			<cx:select list="$MENUTYPE" name="menuType" cssClass="required combox editControl" value="%{paramsMap.menuType}" emptyOption="true" headerKey="" headerValue="请选择菜单类型"/>
		</p> 
		<p class="nowrap">
			<label><s:text name="menuDesc"/></label>
			<s:textfield name="menuDesc" value="%{paramsMap.menuDesc}" cssClass="editControl" cssStyle="width:300px"/>
		</p>
		<p class="nowrap">
			<label><s:text name="menuUrl"/></label>
			<s:textfield name="menuUrl" value="%{paramsMap.menuUrl}" cssClass="editControl" cssStyle="width:300px"/>
		</p>
	</div>
	
	<div class="formBar">
		<c:if test="${! empty paramsMap.parentMenuId}">
			<c:if test="${'TRUE' eq paramsMap.ISEDIT}">
				<a href="javascript:void(0);" onclick="doSave();return false;" class="linkbutton" data-options="iconCls:'icon-save'"><s:text name="save"/></a>
			</c:if>
		</c:if>
		<c:if test="${'TRUE' ne paramsMap.ISEDIT}">
			<a href="javascript:void(0);" onclick="doEditContent();return false;" class="linkbutton" data-options="iconCls:'icon-edit'"><s:text name="edit"/></a>
			<a href="javascript:void(0);" onclick="doDeleteMenu();return false;" class="linkbutton" data-options="iconCls:'icon-delete'"><s:text name="delete"/></a>
		</c:if>
		<c:if test="${'TRUE' ne paramsMap.ISNEW}">
			<a href="javascript:void(0);" onclick="doAddMenu();return false;" class="linkbutton" data-options="iconCls:'icon-add'"><s:text name="add"/></a>
		</c:if>
	</div>

</div>

</form>

</body>
</html>
