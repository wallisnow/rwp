<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/common/taglibs.jsp" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    
    <title><s:text name="title"/></title>
	<%@ include file="/common/header.jsp"%>
	

	<script type="text/javascript">

	$(function(){
		initIframePageEnv("dialog");
		
		setTimeout(initSelectEvent, 100);
	});
	
	</script>
  </head>
  
<body scroll="no">

<form action="${ctx}/system/user/selectWithRole.action" method="post" id="queryForm">

<s:hidden name="pageNo" value="%{paramsMap.pageNo}"/>
<s:hidden name="pageSize" value="%{paramsMap.pageSize}"/>
<s:hidden name="_seq_roleId" value="%{paramsMap._seq_roleId}"/>

<div class="pageMsgInfo">
</div>

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
			<ul>
				<li><div class="buttonActive"><div class="buttonContent"><button type="button" onclick="doQuery();"><s:text name="query"/></button></div></div></li>
				<li><div class="buttonActive"><div class="buttonContent"><button type="button" id="doreset" onclick="doReset();"><s:text name="reset"/></button></div></div></li>
			</ul>
		</div>

</div>

<div class="searchPageContent">
	<table class="table" style="width: 100%" nowrapTD="false">
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
			<tr PK="${item.user.userId}|${item.user.userName}|${item.user.email}|${item.user.mobileno}">
				<td><c:out value="${item.user.userName}"/></td>
				<td><c:out value="${item.user.userId}"/></td>
				<td><c:out value="${item.user.mobileno}"/></td>
				<td><c:out value="${item.user.dpFullName}"/></td>
				<td><cx:Code2Name definition="$USERTYPE" value="${item.user.userType}"/></td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="panelBar">
		<div class="pages">
			<span>每页显示</span>
			<cx:select list='{"10", "20", "50", "100"}' cssClass="combox" name="newPageSize" onchange="changePageSize()" value="%{paramsMap.newPageSize}"/>
			<span>条，共${!empty page ? page.totalCount:0}条</span>
		</div>
		<cx:Page page="${page}"/>
	</div>
</div>

</form>

</body>
</html>
