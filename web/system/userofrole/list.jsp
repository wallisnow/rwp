<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/common/taglibs.jsp" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    
    <title><s:text name="title"/></title>
	<%@ include file="/common/header.jsp"%>

	<script type="text/javascript">

	cmdAdd = "/system/userofrole/add.action";
	cmdDelete = "/system/userofrole/delete.action";
	cmdEdit = "/system/userofrole/edit.action";
	cmdLoad = "/system/userofrole/batch.jsp";
	cmdExcel = "/system/userofrole/excel.action";

	$(function(){
		initPageEnv("list");
	});
	
	</script>
  </head>
  
<body scroll="no">

<form action="${ctx}/system/userofrole/list.action" method="post" id="queryForm">

<div class="searchPageHeader">
	
	<div class="searchContent">
		<p>
			<label><s:text name="_seq_userId"/>:</label>
			<s:textfield name="_seq_userId" value="%{paramsMap._seq_userId}"/>
		</p>
		<p>
			<label><s:text name="_seq_roleId"/>:</label>
			<cx:textfield name="_seq_roleId" value="%{paramsMap._seq_roleId}"/>
		</p>
	</div>
	
	<div class="searchBar">
		<a href="javascript:void(0);" onclick="doQuery();return false;" class="linkbutton" data-options="iconCls:'icon-query'"><s:text name="query"/></a>
		<a href="javascript:void(0);" onclick="doReset();return false;" class="linkbutton" data-options="iconCls:'icon-reset'"><s:text name="reset"/></a>
		<a href="javascript:void(0);" onclick="doLoad();return false;" class="linkbutton"><s:text name="load"/></a>
	</div>

</div>

<div class="searchPageContent">
	<div class="panelBar">
		<ul class="toolBar">
			<li><a class="add" href="Javascript:doAdd();"><span>添加</span></a></li>
			<li><a class="edit" href="Javascript:doEditList();"><span>修改</span></a></li>
			<li><a class="delete" href="Javascript:doDelete();"><span>删除</span></a></li>			
			<li class="line">line</li>
			<li><a class="excel" onclick="doExcel();"><span>导出EXCEL</span></a></li>
		</ul>
	</div>
	<div class="tableBar">
	<table class="list nowrap">
		<thead>
			<tr>
				<th><input type="checkbox" name="chooseAll" onclick="checkAll()"/></th>
				<th><s:text name="userId"/></th>
				<th><s:text name="userName"/></th>
				<th><s:text name="dpFullName"/></th>
				<th><s:text name="roleId"/></th>
				<th><s:text name="roleName"/></th>
				<th><s:text name="status"/></th>
				<th><s:text name="beginTime"/></th>
				<th><s:text name="endTime"/></th>
			</tr>
		</thead>
		<tbody>
		<c:forEach var="item" items="${page.datas}" >
			<tr PK="${item.userOfRole.urId}">
				<td align="center"><input type="checkbox" name="PK" value="${item.userOfRole.urId}"/></td>
				<td><c:out value="${item.userOfRole.userId}"/></td>
				<td><c:out value="${item.user.userName}"/></td>
				<td>
					<cx:Code2Name definition="!com.congxing.system.sysdp.model.SysDpVO*dpId*dpFullName" value="${item.user.dpId}"/>
				</td>
				<td><c:out value="${item.userOfRole.roleId}"/></td>
				<td><c:out value="${item.role.roleName}"/></td>
				<td><cx:Code2Name definition="$TORF" value="${item.userOfRole.status}"/></td>
				<td>
					<cx:Code2Name definition="#yyyy-MM-dd HH:mm:ss" value="${item.userOfRole.beginTime}"/>
				</td>
				<td>
					<cx:Code2Name definition="#yyyy-MM-dd HH:mm:ss" value="${item.userOfRole.endTime}"/>
				</td>
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
