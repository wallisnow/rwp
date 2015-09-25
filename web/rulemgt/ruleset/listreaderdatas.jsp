<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/common/taglibs.jsp" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
	<title><s:text name="title"/></title>
	<%@ include file="/common/header.jsp"%>
	<script type="text/javascript">
	<!--
		cmdAdd = '/rulemgt/ruleset/add.action';
		cmdDelete = '/rulemgt/ruleset/delete.action';
		cmdEdit = '/rulemgt/ruleset/edit.action';
		cmdLoad = '/rulemgt/ruleset/batch.jsp';
		cmdExcel = '/rulemgt/ruleset/excel.action';
	
		$(function(){
			initPageEnv("list");
		});
		
		function doListReader(){
			var PKS = findPKS();
			if(PKS.length < 1){
				alert("请选择记录进行编辑");
				return;
			}else if(PKS.length > 1){
				alert("请选择单条记录进行编辑");
				return;
			}
			var url = addParam(ctx + "/rulemgt/reader/list.action", "rulesetPK", PKS[0]);
			window.location.href = url;
		}
		function doShowListReader(PK){
			var url = addParam(ctx + "/rulemgt/reader/list.action", "rulesetPK", PK);
			window.location.href = url;
		}
	-->
	</script>
</head>

<body>
<form action="${ctx}/rulemgt/ruleset/listReaderDatas.action" method="post" id="queryForm" onsubmit="return formCheck();">

<div class="searchPageHeader">
	<div class="searchContent">
		<p>	
			<label><s:text name="_sk_rulesetName"/>:</label>
			<s:textfield  name="_sk_rulesetName"  value="%{paramsMap._sk_rulesetName}" />	
		</p>
		<p>	
			<label><s:text name="_sk_creator"/>:</label>
			<s:textfield  name="_sk_creator"  value="%{paramsMap._sk_creator}" />
			<a href="javascript:void(0)" class="linkbutton" data-options="plain:true,iconCls:'icon-preview'" onclick='selectUser("_sk_creator");'></a>
		</p>
		<p>	
			<label><s:text name="_seq_status"/>:</label>
			<cx:select list="$RULESET_STATUS"  name="_seq_status"  value="%{paramsMap._seq_status}" emptyOption="true" headerKey="" headerValue="请选择状态"/>
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
			<li><a class="config" href="Javascript:doListReader();"><span>配置取数器</span></a></li>
		</ul>
	</div>
	<div class="tableBar">
	<table class="list nowrap">
		<thead>
		<tr>
			<th><input type="checkbox" name="chooseAll" onclick="checkAll()"/></th>
			<th><s:text name="version"/></th>
			<th><s:text name="rulesetName"/></th>
			<th><s:text name="versionDesc"/></th>
			<th><s:text name="status"/></th>
			<th><s:text name="creator"/></th>
			<th><s:text name="createTime"/></th>
		</tr>
		</thead>
		<tbody>
		<c:forEach var="item" items="${page.datas}">
			<tr PK="${item.rulesetId}|${item.rulesetVersion}" ondblclick='javascript:doShowListReader("${item.rulesetId}|${item.rulesetVersion}");'>
				<td><input type="checkbox" name="PK" value="${item.rulesetId}|${item.rulesetVersion}"/></td>
				<td style="text-align: center; width: 100px;"><c:out value="${item.rulesetVersion}" /></td>
				<td><c:out value="${item.rulesetName}" /></td>
				<td><c:out value="${item.versionDesc}" /></td>
				<td>
					<cx:Code2Name definition="$RULESET_STATUS" value="${item.status}" />
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

