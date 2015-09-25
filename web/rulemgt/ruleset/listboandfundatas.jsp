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
		
		function doRulesetCfg(rulesetPK){
			var url = addParam(ctx + "/rulemgt/ruleset/editRulesetCfg.action", "rulesetPK", rulesetPK);
			window.location.href = url;
		}
		
		function doListRule(){
			var PKS = findPKS();
			if(PKS.length < 1){
				alert("请选择记录进行编辑");
				return;
			}else if(PKS.length > 1){
				alert("请选择单条记录进行编辑");
				return;
			}
			var url = addParam(ctx + "/rulemgt/ruleset/listRule.action", "rulesetPK", PKS[0]);
			window.location.href = url;
		}
	-->
	</script>
</head>

<body>


<form action="${ctx}/rulemgt/ruleset/listBoAndFunDatas.action" method="post" id="queryForm" onsubmit="return formCheck();">

<div class="searchPageHeader">
	<div class="searchContent">
		<p>	
			<label><s:text name="_sk_rulesetName"/>:</label>
			<s:textfield  name="_sk_rulesetName"  value="%{paramsMap._sk_rulesetName}" />	
		</p>
		<p>	
			<label><s:text name="_seq_status"/>:</label>
			<cx:select name="_seq_status" list="$RULESET_STATUS" cssClass="combox" emptyOption="true" value="%{paramsMap._seq_status}"/>
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
			<th width="5%"><s:text name="operate"/></th>
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
			<tr PK="${item.rulesetId}|${item.rulesetVersion}">
				<td style="text-align: center; width: 50px;">
					<c:if test="${'DRAFT' eq item.status}">
						<a href="javascript:void(0);" title="配置" onclick='doRulesetCfg("${item.rulesetId}|${item.rulesetVersion}");return false;' class="linkbutton" data-options="plain:true, iconCls:'icon-config'">配置</a>
					</c:if>
					<c:if test="${'DRAFT' ne item.status}">
						<a href="javascript:void(0);" title="查询" onclick='doRulesetCfg("${item.rulesetId}|${item.rulesetVersion}");return false;' class="linkbutton" data-options="plain:true, iconCls:'icon-preview'">查看</a>
					</c:if>
				</td>
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

