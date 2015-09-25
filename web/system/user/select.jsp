<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/common/taglibs.jsp" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    
    <title><s:text name="title"/></title>
	<%@ include file="/common/header.jsp"%>
	

	<script type="text/javascript">

	cmdAdd="/system/user/add.action";
	cmdDelete="/system/user/delete.action";
	cmdEdit="/system/user/edit.action";


	$(function(){
		initPageEnv("list");
		setTimeout(initSelectEvent, 100);
	});
	
	</script>
  </head>
  
<body scroll="no">

<form action="${ctx}/system/user/select.action" method="post" id="queryForm">

<div class="searchPageHeader">
	
		<div class="searchContent">
			<p>
				<label><s:text name="_sk_userId"/>:</label>
				<s:textfield name="_sk_userId" value="%{paramsMap._sk_userId}"/>
			</p>
			<p>
				<label><s:text name="_sk_userName"/>:</label>
				<cx:textfield name="_sk_userName" value="%{paramsMap._sk_userName}"/>
			</p>
		</div>
		
		<div class="divider"></div>
		
		<div class="searchBar">
			<a href="javascript:void(0);" onclick="doQuery();return false;" class="linkbutton" data-options="iconCls:'icon-query'"><s:text name="query"/></a>
			<a href="javascript:void(0);" onclick="doReset();return false;" class="linkbutton" data-options="iconCls:'icon-reset'"><s:text name="reset"/></a>
		</div>

</div>

<div class="searchPageContent">
	<div class="tableBar">
	<table class="list nowrap">
		<thead>
			<tr>
				<th><s:text name="userName"/></th>
				<th><s:text name="userId"/></th>
				<th><s:text name="mobileno"/></th>
				<th><s:text name="dpFullName"/></th>
				<th><s:text name="userType"/></th>
			</tr>
		</thead>
		<tbody>
		<c:forEach var="item" items="${page.datas}" >
			<tr PK="${item.userId}|${item.userName}|${item.email}|${item.mobileno}">
				<td><c:out value="${item.userName}"/></td>
				<td><c:out value="${item.userId}"/></td>
				<td><c:out value="${item.mobileno}"/></td>
				<td><c:out value="${item.dpFullName}"/></td>
				<td><cx:Code2Name definition="$USERTYPE" value="${item.userType}"/></td>
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
