<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/common/taglibs.jsp" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    
    <title><s:text name="title"/></title>
	<%@ include file="/common/header.jsp"%>
	
	<script type="text/javascript">

	cmdAdd = "/system/menumonitorconfig/add.action";
	cmdDelete = "/system/menumonitorconfig/delete.action";
	cmdEdit = "/system/menumonitorconfig/edit.action";


	$(function(){
		initPageEnv("list");
	});
	
	</script>
  </head>
  
  <body scroll="no">

<form action="${ctx}/system/menumonitorconfig/list.action" method="post" id="queryForm" onsubmit="return formCheck();">

<div class="searchPageHeader">
	
	<div class="searchContent">
		<p>
			<label><s:text name="_sk_monitorName"/>:</label>
			<cx:textfield name="_sk_monitorName" value="%{paramsMap._sk_monitorName}"/>
		</p>
		<p>
			<label><s:text name="_sk_monitorUrl"/>:</label>
			<cx:textfield name="_sk_monitorUrl" value="%{paramsMap._sk_monitorUrl}"/>
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
			<li><a class="add" href="Javascript:doAdd();"><span>添加</span></a></li>
			<li><a class="edit" href="Javascript:doEditList();"><span>修改</span></a></li>
			<li class="line"></li>
			<li><a class="delete" href="Javascript:doDelete();"><span>删除</span></a></li>
		</ul>
	</div>
	<div class="tableBar">
	<table class="list">
		<thead>
			<tr>
				<th><input type="checkbox" name="chooseAll" onclick="checkAll()"/></th>
				<th><s:text name="monitorId"/></th>
				<th><s:text name="monitorName"/></th>
				<th><s:text name="monitorUrl"/></th>
				<th><s:text name="memo"/></th>
			</tr>
		</thead>
		<tbody>
		<c:forEach var="item" items="${page.datas}" >
			<tr PK="${item.monitorId}">
				<td align="center"><input type="checkbox" name="PK" value="${item.monitorId}"/></td>
				<td><c:out value="${item.monitorId}" /></td>
				<td><c:out value="${item.monitorName}"/></td>
				<td><c:out value="${item.monitorUrl}"/></td>
				<td><c:out value="${item.memo}"/></td>
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
