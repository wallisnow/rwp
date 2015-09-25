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
		cmdReturn='/rulemgt/ruleset/list.action';
	
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
			doSubJson(url, params);
		}

		function doCopy(){
			var PKS = findPKS();
			if(PKS.length < 1){
				alert("请选择记录进行编辑");
				return;
			}
			var url = ctx + cmdCopy;
			var versionDesc = "";
			$.dialog.open(ctx + "/rulemgt/ruleset/versiondesc.jsp", {
				id:"versiondesc", 
				title: "版本描述", 
				width: 500, 
				height: 300, 
				close: function(){
						versionDesc = art.dialog.data("versionDesc");
						art.dialog.removeData("versionDesc");
						if(typeof(versionDesc) != "undefined"){
							var params = {"PK":PKS.join(","), "versionDesc":versionDesc, "dt":Math.random()};
							doSubJson(url, params);
						}
				},
				lock:true,
				drag:false
				}, 
				false
				);
		}

		function doOverdue(){
			var PKS = findPKS();
			if(PKS.length < 1){
				alert("请选择记录进行编辑");
				return;
			}
			var url = ctx + cmdOverdue;
			var params = {"PK":PKS.join(","), "dt":Math.random()};
			doSubJson(url, params);
		}

		function doSubJson(url, params){
			var async = $.ajaxSettings.async;
			$.ajaxSettings.async = false;
			$.getJSON(url, params, function callback(jsonResult){
				if("error" == jsonResult.status){
					alert("提交失败:" + jsonResult.message);
				}else{
					alert(jsonResult.message);
				}
			});
			$.ajaxSettings.async = async;
			form = document.forms[0];
			form.submit();
		}
		function doRulesetEdit(rulesetPK){
			var url = addParam(ctx + "/rulemgt/ruleset/editRulesetCfg.action", "rulesetPK", rulesetPK);
			window.location.href = url;
		}

		function doListRule(rulesetPK){
			var url = addParam(ctx + "/rulemgt/ruleset/listRule.action", "rulesetPK", rulesetPK);
			window.location.href = url;
		}
		
		function doListReader(rulesetPK){
			var url = addParam(ctx + "/rulemgt/reader/list.action", "rulesetPK", rulesetPK);
			window.location.href = url;
		}
		function doListtorage(rulesetPK){
			var url = addParam(ctx + "/rulemgt/storage/list.action", "rulesetPK", rulesetPK);
			window.location.href = url;
		}
	-->
	</script>
</head>

<body>
<form action="${ctx}/rulemgt/ruleset/listdetail.action" method="post" id="queryForm" onsubmit="return formCheck();">

<s:hidden name="_leq_rulesetId" value="%{paramsMap._leq_rulesetId}" />

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
		<p>	
			<label><s:text name="_dge_createTime"/>:</label>
			<s:textfield  name="_dge_createTime"  value="%{paramsMap._dge_createTime}" 	cssClass="Wdate" onclick="WdatePicker({isShowClear:false,readOnly:true})"/>
		</p>
		<p>	
			<label><s:text name="_dle_createTime"/>:</label>
			<s:textfield  name="_dle_createTime"  value="%{paramsMap._dle_createTime}" 	cssClass="Wdate" onclick="WdatePicker({isShowClear:false,readOnly:true})"/>
		</p>
	</div>

	<div class="searchBar">
		<a href="javascript:void(0);" onclick="doQuery();return false;" class="linkbutton" data-options="iconCls:'icon-query'"><s:text name="query"/></a>
		<a href="javascript:void(0);" onclick="doReset();return false;" class="linkbutton" data-options="iconCls:'icon-reset'"><s:text name="reset"/></a>
		<a href="javascript:void(0);" onclick="doReturn();return false;" class="linkbutton" data-options="iconCls:'icon-return'"><s:text name="return"/></a>
	</div>
	
</div>
<div class="searchPageContent">
	<div class="panelBar">
		<ul class="toolBar">
			<li><a class="add" href="Javascript:doPublish();"><span>发布版本</span></a></li>
			<li><a class="edit" href="Javascript:doCopy();"><span>添加版本</span></a></li>
			<li><a class="delete" href="Javascript:doOverdue();"><span>作废</span></a></li>
		</ul>
	</div>
	<div class="tableBar">
	<table class="list nowrap">
		<thead>
		<tr>
			<th><input type="checkbox" name="chooseAll" onclick="checkAll()"/></th>
			<th><s:text name="version"/></th>
			<th><s:text name="rulesetName"/></th>
			<th><s:text name="rulesetDesc"/></th>
			<th><s:text name="versionDesc"/></th>
			<th><s:text name="creator"/></th>
			<th><s:text name="createTime"/></th>
			<th><s:text name="status"/></th>
			<th>步骤一</th>
			<th>步骤二</th>
			<th>步骤三</th>
			<th>步骤四</th>
		</tr>
		</thead>
		<tbody>
		<c:forEach var="item" items="${page.datas}">
			<tr PK="${item.rulesetId}|${item.rulesetVersion}">
				<td><input type="checkbox" name="PK" value="${item.rulesetId}|${item.rulesetVersion}"/></td>
				<td>
					<c:out value="${item.rulesetVersion}" />
				</td>
				<td>
					<c:out value="${item.rulesetName}" />
				</td>
				<td>
					<c:out value="${item.rulesetDesc}" />
				</td>
				<td>
					<c:out value="${item.versionDesc}" />
				</td>
				<td>
					<cx:Code2Name definition="!com.congxing.system.user.model.UserVO*userId*userName" value="${item.creator}"/>
				</td>
				<td>
					<c:out value="${item.createTime}" />
				</td>
				<td>
					<cx:Code2Name definition="$RULESET_STATUS" value="${item.status}" />
				</td>
				<td style="text-align: center; ">
					<c:if test="${'DRAFT' eq item.status}">
						<a href="javascript:void(0);" title="业务对象配置" onclick='doRulesetEdit("${item.rulesetId}|${item.rulesetVersion}");return false;' class="linkbutton" data-options="plain:true, iconCls:'icon-config'">规则因子配置</a>
					</c:if>
					<c:if test="${'DRAFT' ne item.status}">
						<a href="javascript:void(0);" title="业务对象查询" onclick='doRulesetEdit("${item.rulesetId}|${item.rulesetVersion}");return false;' class="linkbutton" data-options="plain:true, iconCls:'icon-preview'">规则因子查看</a>
					</c:if>
				</td>
				<td style="text-align: center; ">
					<a href="javascript:void(0);" title="业务规则配置" onclick='doListRule("${item.rulesetId}|${item.rulesetVersion}");return false;' class="linkbutton" data-options="plain:true, iconCls:'icon-config'">业务规则配置</a>
				</td>
				<td style="text-align: center; ">
					<a href="javascript:void(0);" title="取数器配置" onclick='doListReader("${item.rulesetId}|${item.rulesetVersion}");return false;' class="linkbutton" data-options="plain:true, iconCls:'icon-config'">取数器配置</a>
				</td>
				<td style="text-align: center; ">
					<a href="javascript:void(0);" title="存储器配置" onclick='doListtorage("${item.rulesetId}|${item.rulesetVersion}");return false;' class="linkbutton" data-options="plain:true, iconCls:'icon-config'">存储器配置</a>
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

