<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/common/taglibs.jsp" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    
    <title><s:text name="title"/></title>
	<%@ include file="/common/header.jsp"%>

	<script type="text/javascript">

	cmdAdd = "/rulemgt/dbconfig/add.action";
	cmdDelete = "/rulemgt/dbconfig/delete.action";
	cmdEdit = "/rulemgt/dbconfig/edit.action";

	$(function(){
		initPageEnv("list");
	});
	function doEdit(PK){
		var url = addParam(ctx + cmdEdit, "PK", PK);
		window.location.href = url;
	}
	</script>
  </head>
  
<body scroll="no">

<form action="${ctx}/rulemgt/dbconfig/list.action" method="post" id="queryForm">

<div class="searchPageHeader">
	
	<div class="searchContent">
		<p>
			<label><s:text name="_sk_dbName"/>:</label>
			<s:textfield name="_sk_dbName" value="%{paramsMap._sk_dbName}"/>
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
			<li><a class="delete" href="Javascript:doDelete();"><span>删除</span></a></li>	
		</ul>
	</div>
	<div class="tableBar">
	<table class="list nowrap">
		<thead>
			<tr>
				<th><input type="checkbox" name="chooseAll" onclick="checkAll()"/></th>
				<th><s:text name="dbName"/></th>
				<th><s:text name="driverClass"/></th>
				<th><s:text name="jdbcUrl"/></th>
				<th><s:text name="user"/></th>
				<th><s:text name="dbDesc"/></th>
			</tr>
		</thead>
		<tbody>
		<c:forEach var="item" items="${page.datas}" >
			<tr PK="${item.dbId}"  ondblclick='javascript:doEdit("${item.dbId}");'>
				<td align="center"><input type="checkbox" name="PK" value="${item.dbId}"/></td>
				<td><c:out value="${item.dbName}"/></td>
				<td>
					<cx:Code2Name definition="$DRIVERCLASS" value="${item.driverClass}"/>
				</td>
				<td><c:out value="${item.jdbcUrl}"/></td>
				<td><c:out value="${item.user}"/></td>
				<td><c:out value="${item.dbDesc}"/></td>
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
