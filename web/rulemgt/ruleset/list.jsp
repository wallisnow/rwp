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
		cmdPublic = '/rulemgt/ruleset/publicJson.action';
		cmdCopy = '/rulemgt/ruleset/copyJson.action';
		cmdOverdue = '/rulemgt/ruleset/overdueJson.action';
		cmdDetail = '/rulemgt/ruleset/listdetail.action';
		
	
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
			var url = addParam(ctx + "/rulemgt/ruleset/editRulesetCfg.action", "rulesetId", PKS[0]);
			
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
			var url = addParam(ctx + "/rulemgt/ruleset/listRule.action", "rulesetId", PKS[0]);
			window.location.href = url;
		}

		function doPublish(){
			var PKS = findPKS();
			if(PKS.length < 1){
				alert("请选择记录进行编辑");
				return;
			}
			var url = ctx + cmdPublic;
			var params = {"PK":PKS.join(","), "dt":Math.random()};
			$.getJSON(url, params, function callback(jsonResult){
				if("error" == jsonResult.status){
					$.dialog.alert("提交失败:" + jsonResult.message);
				}else{
					$.dialog.alert(jsonResult.message);
					$("#subButton").attr("disabled", "true");
				}
			});
		}

		function doCopy(){
			var PKS = findPKS();
			if(PKS.length < 1){
				alert("请选择记录进行编辑");
				return;
			}
			var url = ctx + cmdCopy;
			var params = {"PK":PKS.join(","), "dt":Math.random()};
			$.getJSON(url, params, function callback(jsonResult){
				if("error" == jsonResult.status){
					$.dialog.alert("提交失败:" + jsonResult.message);
				}else{
					$.dialog.alert(jsonResult.message);
					$("#subButton").attr("disabled", "true");
				}
			});
		}

		function doOverdue(){
			var PKS = findPKS();
			if(PKS.length < 1){
				alert("请选择记录进行编辑");
				return;
			}
			var url = ctx + cmdOverdue;
			var params = {"PK":PKS.join(","), "dt":Math.random()};
			$.getJSON(url, params, function callback(jsonResult){
				if("error" == jsonResult.status){
					$.dialog.alert("提交失败:" + jsonResult.message);
				}else{
					$.dialog.alert(jsonResult.message);
					$("#subButton").attr("disabled", "true");
				}
			});
		}

		function doshowDetail(rulesetId){

			var url = addParam(ctx + cmdDetail, "_leq_rulesetId", rulesetId);
			window.location.href = url;
		}
	-->
	</script>
</head>

<body>
<form action="${ctx}/rulemgt/ruleset/list.action" method="post" id="queryForm" onsubmit="return formCheck();">

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
	<div class="panelBar">
		<ul class="toolBar">
			<li><a class="add" href="Javascript:doAdd();"><span>添加</span></a></li>
			<li><a class="edit" href="Javascript:doEditList();"><span>修改</span></a></li>
		</ul>
	</div>
	<div class="tableBar">
	<table class="list nowrap">
		<thead>
		<tr>
			<th><input type="checkbox" name="chooseAll" onclick="checkAll()"/></th>
			<th><s:text name="rulesetType"/></th>
			<th><s:text name="rulesetName"/></th>
			<th><s:text name="rulesetDesc"/></th>
			<th><s:text name="versionDetail"/></th>
		</tr>
		</thead>
		<tbody>
		<c:forEach var="item" items="${page.datas}">
			<tr PK="${item.rulesetId}|${item.rulesetVersion}" ondblclick='javascript:doshowDetail("${item.rulesetId}");'>
				<td><input type="checkbox" name="PK" value="${item.rulesetId}|${item.rulesetVersion}"/></td>
				<td>
				<cx:Code2Name definition="$CJ_TYPE" value="${item.rulesetType}" />
				</td>
				<td>
					<c:out value="${item.rulesetName}" />
				</td>
				<td>
					<c:out value="${item.rulesetDesc}" />
				</td>
				<td style="text-align:center;" ><a href="javascript:void(0);" onclick="doshowDetail('${item.rulesetId}');return false;" class="linkbutton" data-options="plain:true, iconCls:'icon-query'">明细</a></td>
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

