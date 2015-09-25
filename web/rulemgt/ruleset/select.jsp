<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/common/taglibs.jsp" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
	<title><s:text name="title"/></title>
	<%@ include file="/common/header.jsp"%>
	<script type="text/javascript">
	<!--
		$(function(){
			initPageEnv("list");
			setTimeout(initSelectEvent, 100);
		});
	-->
	</script>
</head>

<body>
<form action="${ctx}/rulemgt/ruleset/select.action" method="post" id="queryForm" onsubmit="return formCheck();">
<input type="hidden" name="_seq_status" value="${paramsMap._seq_status}"/>

<div class="searchPageHeader">
	<div class="searchContent">
		<p>	
			<label><s:text name="_seq_rulesetType"/>:</label>
			<cx:select name="_seq_rulesetType" list="$CJ_TYPE" headerKey="" headerValue="请选择" emptyOption="true" value='%{paramsMap.get("_seq_rulesetType")}' />
		</p>
		<p>	
			<label><s:text name="_sk_rulesetName"/>:</label>
			<s:textfield  name="_sk_rulesetName"  value="%{paramsMap._sk_rulesetName}" />	
		</p>
	</div>
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
			<th><s:text name="rulesetName"/></th>
			<th><s:text name="version"/></th>
			<th><s:text name="rulesetType"/></th>
			<th><s:text name="rulesetDesc"/></th>
			<th><s:text name="status"/></th>
			<th><s:text name="versionDesc"/></th>
			<th><s:text name="creator"/></th>
			<th><s:text name="createTime"/></th>
		</tr>
		</thead>
		<tbody>
		<c:forEach var="item" items="${page.datas}">
			<tr PK="${item.rulesetId}|${item.rulesetVersion}|${item.rulesetName}" >
				<td>
					<c:out value="${item.rulesetName}" />
				</td>
				<td>
					<c:out value="${item.rulesetVersion}" />
				</td>
				<td>
				<cx:Code2Name definition="$RULESET_TYPE" value="${item.rulesetType}" />
				</td>
				<td>
					<c:out value="${item.rulesetDesc}" />
				</td>
				<td>
					<cx:Code2Name definition="$RULESET_STATUS" value="${item.status}" />
				</td>
				<td>
					<c:out value="${item.versionDesc}" />
				</td>
				<td>
					<cx:Code2Name definition="!com.congxing.system.user.model.UserVO*userId*userName" value="${item.creator}"/>
				</td>
				<td>
					<fmt:formatDate value="${item.createTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
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

