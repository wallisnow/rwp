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
	
	function doAddRule(){
		var url = ctx + "/rulemgt/ruleset/addRule.action";
		url = addParam(url, "ruleType", "AUTO");
		var form = document.forms[0];
		form.action = url;
		form.submit();
	}
	
	function doAddRuleManual(){
		var url = ctx + "/rulemgt/ruleset/addRuleManual.action";
		url = addParam(url, "ruleType", "MANUAL");
		var form = document.forms[0];
		form.action = url;
		form.submit();
	}
	
	function doEditRule(){
		var PKS = [];
		var ruleType = null;
		$("input[name=PK]").each(function(){
			$chbox = $(this);
			if($chbox.attr("checked")){
				pkvalue = $chbox.attr("value");
				ruleType = $chbox.parents("tr").attr("ruleType");
				PKS.push(pkvalue);
			}
		});
		if(PKS.length < 1){
			alert("请选择修改记录");
			return;
		}else if(PKS.length > 1){
			alert("只能对一条记录进行修改");
			return;
		}
		var url = ctx + "/rulemgt/ruleset/editRule.action";
		if("MANUAL" == ruleType){
			url = ctx + "/rulemgt/ruleset/editRuleManual.action";
		}
		
		url = addParam(url, "ruleId", PKS[0]);
		url = addParam(url, "rulesetPK", $("input[name='rulesetPK']").val());
		url = addParam(url, "ruleType", ruleType);
		window.location.href = url;
	}
	function doDbClickEditRule(ruleId,ruleType){
		var url = ctx + "/rulemgt/ruleset/editRule.action";
		if("MANUAL" == ruleType){
			url = ctx + "/rulemgt/ruleset/editRuleManual.action";
		}
		
		url = addParam(url, "ruleId", ruleId);
		url = addParam(url, "rulesetPK", $("input[name='rulesetPK']").val());
		url = addParam(url, "ruleType", ruleType);
		window.location.href = url;
	}
	
	function doDelRule(){
		var PK = findPKS();
		if(PK.length < 1){
			alert("请选择删除记录");
		}
		var url = ctx + "/rulemgt/ruleset/deleteRuleJson.action";
		var params = {
			ruleIds : PK.join("|")	
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
		var pk = $("input[name='rulesetPK']").val();
		var att = pk.split("|");
		var url = ctx + "/rulemgt/ruleset/listdetail.action?_leq_rulesetId="+att[0];
		window.location.href = url;
	}

	function doTestRulesetCfg(){
		var url = addParam(ctx + "/rulemgt/ruleset/testRuleset.action", "rulesetPK", $("input[name='rulesetPK']").val());
		window.location.href = url;
	}
	
	function doPreviewRuleset(){
		var params = {
			"rulesetPK" : $("input[name='rulesetPK']").val(),
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
						html.push("<td  style=\"word-wrap: break-word;word-break:break-all;\"><textarea style=\"width:100%;overflow-y:visible;height:80px;\">"+ruleDatas[idx].ruleSemantics+"</td>");
					}else{
						html.push("<td  style=\"width:300px;\"><textarea style=\"width:100%;overflow-y:visible;height:80px;\">"+ruleDatas[idx].ruleCode+"</textarea></td>");
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

<h2 class="contentTitle" style="margin-bottom:0px;">规则列表信息<cx:Msg/></h2>

<form action="${ctx}/rulemgt/ruleset/list.action" method="post">
<input type="hidden" name="rulesetPK" value="${paramsMap.rulesetPK}"/>

<div class="searchPageContent">
	<div class="panelBar">
		<ul class="toolBar">
			<c:if test="${'DRAFT' eq paramsMap.status}">
			<li><a class="add" href="javascript:doAddRule();"><span>自动规则</span></a></li>
			<li><a class="add" href="javascript:doAddRuleManual();"><span>手工规则</span></a></li>
			<li><a class="edit" href="javascript:doEditRule();"><span>修改</span></a></li>
			<li><a class="delete" href="javascript:doDelRule();"><span>删除</span></a></li>
			</c:if>
			<li><a class="preview" href="Javascript:doPreviewRuleset();"><span>预览规则</span></a></li>
			<li><a class="icon" href="Javascript:doTestRulesetCfg();"><span>业务规则测试</span></a></li>
		</ul>
	</div>
	<div class="tableBar">
	<table class="list nowrap">
		<thead>
		<tr>
			<th width="50px"><input type="checkbox" name="chooseRuleAll" onclick="checkRuleAll();"/></th>
			<th width="180px">规则英文名称</th>
			<th width="180px">规则中文名称</th>
			<th>规则类型</th>
			<th>规则优先级</th>
			<th>规则描述</th>
		</tr>
		</thead>
		<tbody id="rulesetDetTbody">
		<c:forEach var="item" items="${RuleDatas}">
		<tr ruleType="${item.ruleType}"  ondblclick='javascript:doDbClickEditRule("${item.ruleId}","${item.ruleType}");'>
			<td><input type="checkbox" name="PK" value="${item.ruleId}"/></td>
			<td><c:out value="${item.ruleEnName}" /></td>
			<td><c:out value="${item.ruleZhName}" /></td>
			<td>
				<cx:Code2Name definition="$RULETYPE" value="${item.ruleType}" />
			</td>
			<td><c:out value="${item.ruleSalience}" /></td>
			<td><c:out value="${item.ruleDesc}" /></td>
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

