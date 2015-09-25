<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>

<head>

<title><s:text name="title" /></title>

<%@ include file="/common/header.jsp"%>

<script type="text/javascript">

	cmdExcel = "/system/logindetstat/excel.action";
	
	$(function(){
		initPageEnv("list");
	});

</script>
</head>

<body scroll="no">

<form action="${ctx}/system/logindetstat/list.action" method="post" id="queryForm" onsubmit="return formCheck();">

<div class="searchPageHeader">
	<div class="searchContent">
		<p>
			<label><s:text name="_sk_userId"/>:</label>
			<s:textfield name="_sk_userId" value="%{paramsMap._sk_userId}" />
		</p>
		<p>
			<label><s:text name="_sge_statDay"/>:</label>
			<s:textfield name="_sge_statDay" value="%{paramsMap._sge_statDay}" cssClass="required Wdate readonly" onclick="WdatePicker({dateFmt:'yyyyMMdd'})"/>
		</p>
		<p>
			<label><s:text name="_sle_statDay"/>:</label>
			<s:textfield name="_sle_statDay" value="%{paramsMap._sle_statDay}" cssClass="required Wdate readonly" onclick="WdatePicker({dateFmt:'yyyyMMdd'})"/>
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
			<li><a class="excel" onclick="doExcel();"><span>导出EXCEL</span></a></li>
		</ul>
	</div>
	<div class="tableBar">
	<table class="list nowrap">
		<thead>
			<tr>
				<th><s:text name="statDay"/></th>
				<th><s:text name="userId"/></th>
				<th><s:text name="userName"/></th>
				<th><s:text name="dpId"/></th>
				<th><s:text name="dpName"/></th>
				<th><s:text name="loginNum"/></th>
				<th><s:text name="visitNum"/></th>
			</tr>
		</thead>
		<tbody>
		<c:forEach var="item" items="${page.datas}">
			<tr>
				<td><c:out value="${item.statDay}" /></td>
				<td><c:out value="${item.userId}" /></td>
				<td><c:out value="${item.userVO.userName}" /></td>
				<td><c:out value="${item.userVO.dpId}" /></td>
				<td>
					<cx:Code2Name definition="!com.congxing.system.sysdp.model.SysDpVO*dpId*dpFullName" value="${item.userVO.dpId}"/>
				</td>
				<td ><c:out value="${item.loginNum}" /></td>
				<td><c:out value="${item.visitNum}" /></td>
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
