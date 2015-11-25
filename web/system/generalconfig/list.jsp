<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title><s:text name="title" /></title>
<%@ include file="/common/header.jsp"%>
<script type="text/javascript">
	$(function() {
		initPageEnv("list");
	});
	
	cmdAdd="/system/generalconfig/add.action";
	cmdSave="/system/generalconfig/save.action";
	cmdEdit="/system/generalconfig/edit.action";
	cmdDelete ="/system/generalconfig/delete.action";
	cmdReturn="/system/generalconfig/list.action";
	
	function addConfig(){
		var url = addParam(ctx + cmdAdd, "STATUS", "ADD");
		window.location = url;
	}
	
	function editConfig(formId){
		var url = addParam(ctx + cmdEdit, "STATUS", "EDIT");
		var form;
		if(typeof(formId) == "undefined"){
			form = document.forms[0];
		}else{
			form = document.forms[formId];
		}
		if(typeof(form) == "undefined"){
			alert("请检查页面定义是否有误");
			return;
		}
		var PKS = findPKS();
		if(PKS.length < 1){
			alert("请选择编辑记录");
			return;
		}else if(PKS.length > 1){
			alert("请确认一次只能编辑一条记录");
			return;
		}

		url = addParam(url, "PK", PKS[0]);
		window.location = url;
	}
</script>
</head>
<body>

	<h2 class="contentTitle">
		<s:text name="title" />
		<cx:Msg />
	</h2>

	<form action="${ctx}/system/generalconfig/list.action" method="post" id="queryForm" onsubmit="return formCheck();">

		<div class="pageMsgInfo">
			<cx:Msg />
		</div>

		<div class="searchPageHeader">
			<div class="searchContent">	
					<label>
					<p>
						<label><s:text name="_seq_formtitle" />:</label>
						<cx:textfield name="_seq_formtitle" value="%{paramsMap._seq_formtitle}" />
					</p>
					<p>
						<label><s:text name="_seq_formname" />:</label>
						<cx:textfield name="_seq_formname" value="%{paramsMap._seq_formname}" />
					</p>				
			</div>
			<div class="searchBar">
					<a href="javascript:void(0);" onclick="doQuery();return false;" class="linkbutton" data-options="iconCls:'icon-query'"><s:text name="query"/></a>
					<a href="javascript:void(0);" onclick="doReset();return false;" class="linkbutton" data-options="iconCls:'icon-reset'"><s:text name="reset"/></a>
			</div>
		</div>
		<div class="searchPageContent">
			<div class="panelBar">
				<ul class="toolBar">
					<li><a class="add" href="Javascript:addConfig();"><span>添加</span></a></li>
					<li><a class="edit" href="Javascript:editConfig();"><span>修改</span></a></li>
				 	<li><a class="delete" href="Javascript:doDelete();"><span>删除</span></a></li>
				</ul>
			</div>
			<div class="tableBar">
				<table class="list nowrap">
					<thead>
						<tr>
							<th><input type="checkbox" name="chooseAll" onclick="checkAll()" /></th>
							<th><s:text name="generalconfigId" /></th>
							<th><s:text name="formtitle" /></th>
							<th><s:text name="formname" /></th>
							<th><s:text name="resource" /></th>
							<th><s:text name="sqlstatement" /></th>
							<th><s:text name="author" /></th>
							<th><s:text name="url" /></th>
						</tr>
					</thead>
					<tbody>
						<c:forEach var="item" items="${page.datas}">
							<tr PK="${item.generalconfigId}">
								<td><input type="checkbox" name="PK" value="${item.generalconfigId}" /></td>
								<td><c:out value="${item.generalconfigId}" /></td>
								<td><c:out value="${item.formtitle}" /></td>
								<td><c:out value="${item.formname}" /></td>
								<td><c:out value="${item.resource}" /></td>
								<td><c:out value="${item.sqlstatement}" /></td>
								<td><c:out value="${item.author}" /></td>
								<td><c:out value="${item.url}" /></td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
			</div>
			<div class="panelBar">
				<cx:Page page="${page}"/>
			</div>
		</div>
	</form>

</body>
</html>