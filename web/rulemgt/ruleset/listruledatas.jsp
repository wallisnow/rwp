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
		function doRulesetCfg(){
			var PKS = findPKS();
			if(PKS.length < 1){
				alert("请选择记录进行编辑");
				return;
			}else if(PKS.length > 1){
				alert("请选择单条记录进行编辑");
				return;
			}
			var url = addParam(ctx + "/rulemgt/ruleset/editRulesetCfg.action", "rulesetPK", PKS[0]);
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
		
		function doTestRulesetCfg(){
			var PKS = findPKS();
			if(PKS.length < 1){
				alert("请选择记录进行编辑");
				return;
			}else if(PKS.length > 1){
				alert("请选择单条记录进行编辑");
				return;
			}
			var url = addParam(ctx + "/rulemgt/ruleset/testRuleset.action", "rulesetPK", PKS[0]);
			window.location.href = url;
		}
		
		function doPreviewRuleset(){
			var PKS = findPKS2();
			if(PKS.length < 1){
				alert("请选择记录进行预览");
				return;
			}else if(PKS.length > 1){
				alert("请选择单条记录进行预览");
				return;
			}
			var params = {
				"rulesetPK" : PKS[0],
				"dt" : new Date() + Math.random()
			};
			$.getJSON(ctx + "/rulemgt/ruleset/previewRulesetJson.action", params, function(jsonResult){
				if("success" == jsonResult.status){
					var ruleDatas = eval('(' + jsonResult.message + ')');
					var html = [];
					html.push("<table class=\"list nowrap\"><thead><tr><th>规则号</th><th>优先级</th><th>代码</th></tr></thead><tbody>");
					for(var idx = 0; idx < ruleDatas.length; idx++){
						html.push("<tr>");
						html.push("<td>"+ruleDatas[idx].ruleEnName+"</td>");
						html.push("<td>"+ruleDatas[idx].ruleSalience+"</td>");
						if(ruleDatas[idx].ruleType == "AUTO"){
							html.push("<td  style=\"word-wrap: break-word;word-break:break-all;\">"+ruleDatas[idx].ruleSemantics+"</td>");
						}else{
							html.push("<td  style=\"width:300px;word-wrap: break-word;word-break:break-all;\">"+ruleDatas[idx].ruleCode+"</td>");
						}
						
						html.push("</tr>");
					}
					html.push("</tbody></table>");
					top.art.dialog(
						{ 
							icon: 'face-smile',
							title : "规则预览",
							content : html.join("")
						}
					);
				}else{
					alert(jsonResult.message);
				}
			});
		}
	-->
	</script>
</head>

<body>
<form action="${ctx}/rulemgt/ruleset/listRuleDatas.action" method="post" id="queryForm" onsubmit="return formCheck();">

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
			<li><a class="config" href="Javascript:doListRule();"><span>配置业务规则</span></a></li>
			<li><a class="preview" href="Javascript:doPreviewRuleset();"><span>预览规则</span></a></li>
			<li><a class="icon" href="Javascript:doTestRulesetCfg();"><span>业务规则测试</span></a></li>
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
			<tr PK="${item.rulesetId}|${item.rulesetVersion}">
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

