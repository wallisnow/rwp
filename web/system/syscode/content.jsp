<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/common/taglibs.jsp" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    
    <title><s:text name="title"/></title>
	<%@ include file="/common/header.jsp"%>

	<script type="text/javascript">
	<!-- 
	
	cmdSave="/system/syscode/save.action";
	cmdEdit="/system/syscode/edit.action";
	cmdReturn="/system/syscode/list.action";
	cmdAdd = "/system/syscode/add.action";
	
	$(function(){
		initPageEnv("content");
		contentFormControl();
	});

	function doReturnSysList(){
		var url = addParam(ctx + cmdReturn, "_seq_type", "${paramsMap.type}");
		window.location = url;
	}

	function doAddContinue(){
		var url = addParam(ctx + cmdAdd, "_seq_type", "${paramsMap.type}");
		window.location = url;
	}
	-->
	</script>

</head>
  
<body scroll="no">

<h2 class="contentTitle"><s:text name="title"/><cx:Msg/></h2>

<form method="post" id="contentForm" action="">
<s:hidden name="seqId" value="%{paramsMap.seqId}"/>
<input type="hidden" name="ISNEW" value="${paramsMap.ISNEW}"/>
<input type="hidden" name="ISEDIT" value="${paramsMap.ISEDIT}"/>

<div class="formPageHeader">
	
	<div class="formContent">
		<p>
			<label><s:text name="type"/></label>
			<s:textfield name="type" value="%{paramsMap.type}" readonly="true" cssClass="required disableAwayls"/>
		</p>
		<p>
			<label><s:text name="name"/></label>
			<s:textfield name="name" value="%{paramsMap.name}" readonly="true" cssClass="required disableAwayls"/>
		</p>
		<p>
			<label><s:text name="kind"/></label>
			<s:textfield name="kind" value="%{paramsMap.kind}" cssClass="required"/>
		</p>
		<p>
			<label><s:text name="kindname"/></label>
			<s:textfield name="kindname" value="%{paramsMap.kindname}" cssClass="required"/>
		</p>
		<p>
			<label><s:text name="memo"/></label>
			<s:textfield name="memo" value="%{paramsMap.memo}"/>
		</p>
	</div>
	
	<div class="formBar">
		<c:if test="${'TRUE' eq paramsMap.ISEDIT}">
			<a href="javascript:void(0);" onclick="doSave();return false;" class="linkbutton" data-options="iconCls:'icon-save'"><s:text name="save"/></a>
		</c:if>
		<c:if test="${'TRUE' ne paramsMap.ISEDIT}">
			<a href="javascript:void(0);" onclick="doEditContent();return false;" class="linkbutton" data-options="iconCls:'icon-edit'"><s:text name="edit"/></a>
			<a href="javascript:void(0);" onclick="doAddContinue();return false;" class="linkbutton" data-options="iconCls:'icon-edit'">继续新增</a>
		</c:if>
		<a href="javascript:void(0);" onclick="doReturnSysList();return false;" class="linkbutton" data-options="iconCls:'icon-return'"><s:text name="return"/></a>
	</div>

</div>

</form>

</body>
</html>
