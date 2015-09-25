<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/common/taglibs.jsp" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    
    <title><s:text name="title"/></title>
	<%@ include file="/common/header.jsp"%>
	<script type="text/javascript">

	cmdAdd = "/system/menumonitordet/add.action";
	cmdDelete = "/system/menumonitordet/delete.action";
	cmdEdit = "/system/menumonitordet/edit.action";
	cmdExcel = "/system/menumonitordet/excel.action";

	$(function(){
		initPageEnv("list");
	});
	
	</script>
  </head>
  
  <body scroll="no">

<form action="${ctx}/system/menumonitordet/list.action" method="post" id="queryForm" onsubmit="return formCheck();">
<div class="searchPageHeader">
	
	<div class="searchContent">
		<p>
			<label><s:text name="_sk_userId"/>:</label>
			<cx:textfield name="_sk_userId" value="%{paramsMap._sk_userId}"/>
		</p>
		<p>
			<label><s:text name="_sk_userName"/>:</label>
			<cx:textfield name="_sk_userName" value="%{paramsMap._sk_userName}"/>
		</p>
		<p>
			<label><s:text name="_sk_monitorName"/>:</label>
			<cx:textfield name="_sk_monitorName" value="%{paramsMap._sk_monitorName}"/>
		</p>
		<p>
			<label><s:text name="_dge_beginTime"/>:</label>
			<cx:textfield name="_dge_beginTime" value="%{paramsMap._dge_beginTime}" onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})"/>
		</p>
		<p>
			<label><s:text name="_dle_endTime"/>:</label>
			<cx:textfield name="_dle_endTime" value="%{paramsMap._dle_endTime}" onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})"/>
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
	<table class="list" style="width:120%;">
		<thead>
			<tr>
				<th width="10%"><s:text name="userId"/></th>
				<th width="10%"><s:text name="userName"/></th>
				<th width="10%"><s:text name="userIp"/></th>
				<th width="10%"><s:text name="dpFullName"/></th>
				<th width="10%"><s:text name="monitorName"/></th>
				<th width="30%"><s:text name="oprParams"/></th>
				<th width="10%"><s:text name="beginTime"/></th>
				<th width="10%"><s:text name="endTime"/></th>
			</tr>
		</thead>
		<tbody>
		<c:forEach var="item" items="${page.datas}" >
			<tr>
				<td><c:out value="${item.user.userId}" /></td>
				<td><c:out value="${item.user.userName}"/></td>
				<td><c:out value="${item.detVO.userIp}"/></td>
				<td>
					<cx:Code2Name definition="!com.congxing.system.sysdp.model.SysDpVO*dpId*dpFullName" value="${item.user.dpId}"/>
				</td>
				<td><c:out value="${item.configVO.monitorName}"/></td>
				<td>
					<div style="display: block;width:350px;overflow: hidden;white-space: nowrap;text-overflow: ellipsis;">
					<c:out value="${item.detVO.oprParams}"/>
					</div>
				</td>
				<td>
					<fmt:formatDate value="${item.detVO.beginTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					<fmt:formatDate value="${item.detVO.endTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
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
