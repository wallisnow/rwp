<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/common/taglibs.jsp" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    
    <title><s:text name="title"/></title>
	<%@ include file="/common/header.jsp"%>
	
	<script type="text/javascript">

	cmdAdd="/system/sysdp/add.action";
	cmdDelete="/system/sysdp/delete.action";
	cmdEdit="/system/sysdp/edit.action";


	$(function(){
		initPageEnv("list");
	});
		
	</script>
  </head>
  
<body scroll="no">

<form action="${ctx}/system/sysdp/list.action" method="post" id="queryForm">

<div class="searchPageHeader">
	
	<div class="searchContent">
		<p>
			<label><s:text name="_seq_dpId"/>:</label>
			<s:textfield name="_seq_dpId" value="%{paramsMap._seq_dpId}"/>
		</p>
		<p>
			<label><s:text name="_sk_dpName"/>:</label>
			<cx:textfield name="_sk_dpName" value="%{paramsMap._sk_dpName}"/>
		</p>
		<p>
			<label><s:text name="_seq_dpCode"/>:</label>
			<cx:textfield name="_seq_dpCode" value="%{paramsMap._seq_dpCode}"/>
		</p>
	</div>
	
	<div class="searchBar">
		<a href="javascript:void(0);" onclick="doQuery();return false;" class="linkbutton" data-options="iconCls:'icon-query'"><s:text name="query"/></a>
		<a href="javascript:void(0);" onclick="doReset();return false;" class="linkbutton" data-options="iconCls:'icon-reset'"><s:text name="reset"/></a>
	</div>

</div>

<div class="searchPageContent">
	<div class="tableBar">
	<table class="list nowrap" style="width:150%">
		<thead>
			<tr>
				<th><s:text name="dpId"/></th>
				<th><s:text name="dpName"/></th>
				<th><s:text name="parentDpId"/></th>
				<th><s:text name="dpFullName"/></th>
				<th><s:text name="dpCode"/></th>
				<th><s:text name="isTmpDp"/></th>
				<th><s:text name="orderNo"/></th>
			</tr>
		</thead>
		<tbody>
		<c:forEach var="item" items="${page.datas}" >
			<tr PK="${item.dpId}">
				<td><c:out value="${item.dpId}"/></td>
				<td><c:out value="${item.dpName}"/></td>
				<td><c:out value="${item.parentDpId}"/></td>
				<td><c:out value="${item.dpFullName}"/></td>
				<td><c:out value="${item.dpCode}"/></td>
				<td><c:out value="${item.isTmpDp}"/></td>
				<td><c:out value="${item.orderNo}"/></td>
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
