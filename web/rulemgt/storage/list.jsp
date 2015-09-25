<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/common/taglibs.jsp" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
	<title><s:text name="title"/></title>
	<%@ include file="/common/header.jsp"%>
	<script type="text/javascript">
	<!--
	cmdAdd = '/rulemgt/storage/add.action';
	cmdDelete = '/rulemgt/storage/delete.action';
	cmdEdit = '/rulemgt/rstorage/edit.action';
	
	$(function(){
		initPageEnv("list");
	});

	function doAddStorage(){
		var url = ctx + "/rulemgt/storage/add.action";
		var form = document.forms[0];
		form.action = url;
		form.submit();
	}
	
	function doEditStorage(){
		var url = ctx + "/rulemgt/storage/edit.action";
		var PK = findPKS();
		if(PK.length < 1){
			alert("请选择修改记录");
			return;
		}else if(PK.length > 1){
			alert("只能对一条记录进行修改");
			return;
		}
		var rulesetPK = $('#rulesetPK').val();
		url = addParam(url, "storageId", PK[0]);
		url = addParam(url, "rulesetPK", rulesetPK);
		window.location.href = url;
	}
	function doDelStorage(){
		var PK = findPKS();
		if(PK.length < 1){
			alert("请选择删除记录");
			return;
		}
		var url = ctx + "/rulemgt/storage/deleteJson.action";
		var params = {
			storageIds : PK.join("|")	
		};
		$.getJSON(url, params, function(result){
			if("success" == result.status){
				window.location.reload();
			}else{
				alert(result.message);
			}
		});
	}
	
	function doReturnRulesetList(){
		var url = ctx + "/rulemgt/ruleset/listdetail.action?_leq_rulesetId=${paramsMap._leq_rulesetId}";
		window.location.href = url;
	}
	function doEdit(PK){
		var rulesetPK = $('#rulesetPK').val();
		var url = addParam(ctx + "/rulemgt/storage/edit.action", "storageId", PK);
		url = addParam(url, "rulesetPK", rulesetPK);
		window.location.href = url;
	}
	-->
	</script>
</head>

<body>

<h2 class="contentTitle" style="margin-bottom: 0px;">存储器管理 <cx:Msg/></h2>

<form action="${ctx}/rulemgt/storage/list.action" method="post">
<input type="hidden" id="rulesetPK" name="rulesetPK" value="${paramsMap.rulesetPK}" />

<div class="searchPageContent">
	<div class="panelBar">
		<ul class="toolBar">
		<c:if test="${'DRAFT' eq paramsMap.status}">
			<li><a class="add" href="javascript:doAddStorage();"><span>添加</span></a></li>
			<li><a class="edit" href="javascript:doEditStorage();"><span>修改</span></a></li>
			<li><a class="delete" href="javascript:doDelStorage();"><span>删除</span></a></li>
		</c:if>
		</ul>
	</div>
	<div class="tableBar">
	<table class="list">
		<thead>
		<tr>
			<th><input type="checkbox" name="chooseAll" onclick="checkAll();"/></th>
			<th>规则集名称</th>
			<th>规则集版本</th>
			<th>状态</th>
			<th>存储器名称</th>
			<th>数据源</th>
			<th>表名</th>
			<th>存储器描述</th>
		</tr>
		</thead>
		<tbody id="rulesetDetTbody">
		<c:forEach var="item" items="${paramsMap.storageDatas}">
		<tr ondblclick='javascript:doEdit("${item.storageId}");'>
			<td><input type="checkbox" name="PK" value="${item.storageId}"/></td>
			<td><c:out value="${paramsMap.rulesetName}" /></td>
			<td><c:out value="${item.rulesetVersion}" /></td>
			<td>
				<cx:Code2Name definition="$RULESET_STATUS" value="${paramsMap.status}" />
			</td>
			<td><c:out value="${item.storageName}" /></td>
			<td>
				<cx:Code2Name definition="!com.congxing.rulemgt.dbconfig.model.DbConfigVO*dbId*dbName" value="${item.dbId}" />
			</td>
			<td><c:out value="${item.tableName}" /></td>
			<td><c:out value="${item.storageDesc}" /></td>
		</tr>
		</c:forEach>
		</tbody>
	</table>
	</div>
	<div class="formBar">
		<a href="javascript:void(0);" onclick="doReturnRulesetList();return false;" class="linkbutton" data-options="iconCls:'icon-return'"><s:text name="return"/></a>
	</div>
</div>
</form>
</body>
</html>

