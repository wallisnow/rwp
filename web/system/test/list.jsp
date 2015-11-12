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
</script>
</head>
<body>

	<h2 class="contentTitle">
		<s:text name="title" />
		<cx:Msg />
	</h2>

	<form action="${ctx}/system/test/list.action" method="post"
		id="listForm">

		<div class="pageMsgInfo">
			<cx:Msg />
		</div>

		<div class="searchPageHeader">
			<div class="searchContent">
				<p>
					<label><s:text name="_seq_username" />:</label>
					<cx:textfield name="_seq_username" value="%{paramsMap._seq_username}" />
				</p>
			</div>
			<div class="searchBar">
				<a href="javascript:void(0);" onclick="doQuery();return false;"
					class="linkbutton" data-options="iconCls:'icon-query'"><s:text
						name="query" /></a> <a href="javascript:void(0);"
					onclick="doReset();return false;" class="linkbutton"
					data-options="iconCls:'icon-reset'"><s:text name="reset" /></a>
			</div>
		</div>
		<div class="searchPageContent">
			<div class="panelBar">
				<ul class="toolBar">
					<li><a class="add" href="Javascript:doAdd();"><span>添加</span></a></li>
					<li><a class="edit" href="Javascript:doEditList();"><span>修改</span></a></li>
				</ul>
			</div>
			<div class="tableBar">
				<table class="list nowrap">
					<thead>
						<tr>
							<th><input type="checkbox" name="chooseAll" onclick="checkAll()" /></th>
							<th><s:text name="testId" /></th>
							<th><s:text name="username" /></th>
							<th><s:text name="password" /></th>
						</tr>
					</thead>
					<tbody>
						<c:forEach var="item" items="${page.datas}">
							<tr PK="${item.testId}">
								<td><input type="checkbox" name="PK" value="${item.testId}" /></td>
								<td><c:out value="${item.testId}" /></td>
								<td><c:out value="${item.username}" /></td>
								<td><c:out value="${item.password}" /></td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
			</div>
		</div>
	</form>

</body>
</html>