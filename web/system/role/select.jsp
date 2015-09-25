<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/common/taglibs.jsp" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    
    <title><s:text name="title"/></title>
	<%@ include file="/common/header.jsp"%>
	
	<script type="text/javascript">

	cmdAdd="/system/role/add.action";
	cmdDelete="/system/role/delete.action";
	cmdEdit="/system/role/edit.action";


	$(function(){
		initPageEnv("list");
		setTimeout(initSelectEvent, 100);
	});
	
	</script>
  </head>
  
  <body scroll="no">

<form action="${ctx}/system/role/select.action" method="post" id="queryForm">
<s:hidden name="pageNo" value="%{paramsMap.pageNo}"/>
<s:hidden name="pageSize" value="%{paramsMap.pageSize}"/>

<div class="pageMsgInfo">
</div>

<div class="searchPageHeader">
	
	<div class="searchContent">
		<p>
			<label><s:text name="_seq_roleId"/>:</label>
			<cx:textfield name="_seq_roleId" value="%{paramsMap._seq_roleId}"/>
		</p>
		<p>
			<label><s:text name="_sk_roleName"/>:</label>
			<cx:textfield name="_sk_roleName" value="%{paramsMap._sk_roleName}"/>
		</p>
		<p>
			<label><s:text name="_seq_status"/>:</label>
			<cx:select list="$STATUS" emptyOption="true" cssClass="combox" headerKey="" headerValue="" value="%{paramsMap._seq_status}"/>
		</p>
	</div>
	
	<div class="searchBar">
		<a href="javascript:void(0);" onclick="doQuery();return false;" class="linkbutton" data-options="iconCls:'icon-query'"><s:text name="query"/></a>
		<a href="javascript:void(0);" onclick="doReset();return false;" class="linkbutton" data-options="iconCls:'icon-reset'"><s:text name="reset"/></a>
	</div>

</div>

<div class="searchPageContent">
	<div class="tableBar">
	<table class="list">
		<thead>
			<tr>
				<th><input type="checkbox" name="chooseAll" onclick="checkAll()"/></th>
				<th><s:text name="roleId"/></th>
				<th><s:text name="roleName"/></th>
				<th><s:text name="roleDesc"/></th>
				<th><s:text name="status"/></th>
				<th><s:text name="beginTime"/></th>
				<th><s:text name="endTime"/></th>
			</tr>
		</thead>
		<tbody>
		<c:forEach var="item" items="${page.datas}" >
			<tr PK="${item.roleId}|${item.roleName}">
				<td><input type="checkbox" name="PK" value="${item.roleId}"/></td>
				<td><c:out value="${item.roleId}" /></td>
				<td><c:out value="${item.roleName}"/></td>
				<td><c:out value="${item.roleDesc}"/></td>
				<td><cx:Code2Name definition="$STATUS" value="${item.status}"/></td>
				<td><c:out value="${item.beginTime}"/></td>
				<td><c:out value="${item.endTime}"/></td>
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
