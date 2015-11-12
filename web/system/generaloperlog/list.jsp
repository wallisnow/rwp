<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>

<title><s:text name="title" /></title>
<%@ include file="/common/header.jsp"%>


<script type="text/javascript">
	cmdAdd = "/system/user/add.action";
	cmdDelete = "/system/user/delete.action";
	cmdEdit = "/system/user/edit.action";
	cmdLoad = "/system/user/batch.jsp";

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

	<form action="${ctx}/system/user/list.action" method="post" id="listForm">
		<div class="pageMsgInfo">
			<cx:Msg />
		</div>
		<div class="searchPageHeader">
			<div class="searchContent">
				<p>
					<label><s:text name="_sge_dt"/>:</label>
					<s:textfield name="_sge_dt" value="%{paramsMap._sge_dt}" cssClass="required Wdate readonly" onclick="WdatePicker({dateFmt:'yyyyMMdd'})"/>
				</p>
				<p>
					<label><s:text name="_sle_dt"/>:</label>
					<s:textfield name="_sle_dt" value="%{paramsMap._sle_dt}" cssClass="required Wdate readonly" onclick="WdatePicker({dateFmt:'yyyyMMdd'})"/>
				</p>
			</div>

			<div class="searchBar">
				<a href="javascript:void(0);" onclick="doQuery();return false;" class="linkbutton" data-options="iconCls:'icon-query'"><s:text name="query" /></a>
				<a href="javascript:void(0);" onclick="doReset();return false;" class="linkbutton" data-options="iconCls:'icon-reset'"><s:text name="reset" /></a>
				<a href="javascript:void(0);" onclick="doLoad();return false;" class="linkbutton"><s:text name="load" /></a>
			</div>
		</div>

		<div class="searchPageContent">
			<div class="panelBar">
				<ul class="toolBar">
				</ul>
			</div>
			<div class="tableBar">
				<table class="list nowrap">
					<thead>
						<tr>
							<th><input type="checkbox" name="chooseAll"
								onclick="checkAll()" /></th>
							<th><s:text name="logId" /></th>
							<th><s:text name="userId" /></th>
							<th><s:text name="username" /></th>
							<th><s:text name="operation" /></th>
							<th><s:text name="userip" /></th>
							<th><s:text name="dt" /></th>
							<th><s:text name="comment" /></th>
						</tr>
					</thead>
					<tbody>
						<c:forEach var="item" items="${page.datas}">
							<tr PK="${item.userId}">
								<td><input type="checkbox" name="PK" value="${item.logId}"/></td>
								<td><c:out value="${item.userId}" /></td>
								<td><c:out value="${item.username}" /></td>
								<td><c:out value="${item.operation}" /></td>
								<td><c:out value="${item.userip}" /></td>
								<td><c:out value="${item.dt}" /></td>
								<td><c:out value="${item.comment}" /></td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
			</div>
			<div class="panelBar">
				<cx:Page page="${page}" />
			</div>
		</div>

	</form>

</body>
</html>
