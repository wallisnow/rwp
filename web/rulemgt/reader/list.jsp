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
	});
	
	/* 页面中选中或取消所有BoCfg配置项*/
	function checkRuleAll(){
		if($("input[name=chooseRuleAll]").attr("checked")){
			$("input[name=PK]").each(function(){
				$chbox = $(this);
				$chbox.attr("checked", true);
			});
		}else{
			$("input[name=PK]").each(function(){
				$chbox = $(this);
				$chbox.attr("checked", false);
			});
		}
	}
	
	function doAddReader(){
		var url = ctx + "/rulemgt/reader/add.action";
		var form = document.forms[0];
		form.action = url;
		form.submit();
	}
	
	function doEditReader(){
		var url = ctx + "/rulemgt/reader/edit.action";
		var PK = findPKS();
		if(PK.length < 1){
			alert("请选择修改记录");
			return;
		}else if(PK.length > 1){
			alert("只能对一条记录进行修改");
			return;
		}
		var rulesetPK = $('#rulesetPK').val();
		url = addParam(url, "readerId", PK[0]);
		url = addParam(url, "rulesetPK", rulesetPK);
		window.location.href = url;
	}
	
	function doDelReader(){
		var PK = findPKS();
		if(PK.length < 1){
			alert("请选择删除记录");
		}
		var url = ctx + "/rulemgt/reader/deleteJson.action";
		var params = {
			readerIds : PK.join("|")	
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
	function doTestReaderCfg(){
		var url = addParam(ctx + "/rulemgt/reader/testReader.action", "rulesetPK", $("input[name='rulesetPK']").val());
		window.location.href = url;
	}

	function doEdit(PK){
		var rulesetPK = $('#rulesetPK').val();
		var url = addParam(ctx + "/rulemgt/reader/edit.action", "readerId", PK);
		url = addParam(url, "rulesetPK", rulesetPK);
		window.location.href = url;
	}
	-->
	</script>
</head>

<body>

<h2 class="contentTitle" style="margin-bottom:0px;">取数器管理 <cx:Msg/></h2>

<form action="${ctx}/rulemgt/reader/list.action" method="post">
<input type="hidden" id="rulesetPK" name="rulesetPK" value="${paramsMap.rulesetPK}"/>

<div class="searchPageContent">
	<div class="panelBar">
		<ul class="toolBar">
		<c:if test="${'DRAFT' eq paramsMap.status}">
			<li><a class="add" href="javascript:doAddReader();"><span>添加</span></a></li>
			<li><a class="edit" href="javascript:doEditReader();"><span>修改</span></a></li>
			<li><a class="delete" href="javascript:doDelReader();"><span>删除</span></a></li>
		</c:if>
		<li><a class="icon" href="Javascript:doTestReaderCfg();"><span>取数器测试</span></a></li>
		</ul>
	</div>
	<div class="tableBar">
	<table class="list">
		<thead>
		<tr>
			<th><input type="checkbox" name="chooseRuleAll" onclick="checkRuleAll();"/></th>
			<th>规则集名称</th>
			<th>规则集版本</th>
			<th>状态</th>
			<th>取数器名称</th>
			<th>取数器描述</th>
			<th>数据源</th>
			<th width="30%">取数SQL</th>
			<th>优先级</th>
		</tr>
		</thead>
		<tbody id="rulesetDetTbody">
		<c:forEach var="item" items="${paramsMap.readerDatas}">
		<tr ondblclick='javascript:doEdit("${item.readerId}");'>
			<td><input type="checkbox" name="PK" value="${item.readerId}"/></td>
			<td>
				<c:out value="${paramsMap.rulesetName}" />
			</td>
			<td><c:out value="${item.rulesetVersion}" /></td>
			<td>
				<cx:Code2Name definition="$RULESET_STATUS" value="${paramsMap.status}"/>
			</td>
			<td><c:out value="${item.readerName}" /></td>
			<td><c:out value="${item.readerDesc}" /></td>
			<td>
				<cx:Code2Name definition="!com.congxing.rulemgt.dbconfig.model.DbConfigVO*dbId*dbName" value="${item.dbId}" />
			</td>
			<td><c:out value="${item.readerSql}" /></td>
			<td><c:out value="${item.readerPriority}" /></td>
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

