<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/common/taglibs.jsp" %>
<c:set var="ISEDIT" value="${'TRUE' eq paramsMap.ISEDIT}"/>
<c:set var="ISNEW" value="${'TRUE' eq paramsMap.ISNEW}"/>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
    
    <title><s:text name="title"/></title>
	<%@ include file="/common/header.jsp"%>

	<script type="text/javascript">
	<!--
	$(function(){
		initPageEnv("list");
		var rulesetPK = $("input[name=rulesetPK]").val();
		var boResults = listJson("com.congxing.rulemgt.ruleset.model.RulesetBoVO", "rulesetId|rulesetVersion", rulesetPK);
		var boDatas = [];
		if("success" == boResults.status){
			boDatas = eval('(' + boResults.message + ')');
		}
		var tboHtml = [];
		for(var idx = 0; idx < boDatas.length; idx++){
			tboHtml.push("<tr>");
			tboHtml.push("<td>" + boDatas[idx].boDesc +  "</td>");
			tboHtml.push("<td>" + boDatas[idx].boName +  "</td>");
			var ruleBoResult = nameJson("$DATATYPE", boDatas[idx].dataType);
			if("success" == ruleBoResult.status){
				tboHtml.push("<td>" + ruleBoResult.message +  "</td>");
			}else{
				tboHtml.push("<td>" + ruleBoResult.dataType +  "</td>");
			}
			tboHtml.push("<td><input type='text' name='" + boDatas[idx].boName + "' class='textInput'/></td>");
			tboHtml.push("</tr>");
		}
		$("#boCfg").html(tboHtml.join(""));
	});
	
	function doTestCfg(){
		var formObj = form2Obj();
		formObj["dt"] = new Date() + Math.random();
		$.getJSON(ctx + "/rulemgt/reader/testReaderJson.action", formObj, function(result){
			if("success" == result.status){
				var flag = true;
				var json_result = eval('(' + result.message + ')');
				for(var key in json_result){
					flag = false;
					$("input[name=" + key + "]").val(json_result[key]);
				}
				if(flag){
					alert("查询不到数据!");
				}{
					alert("查询成功!");
				}
			}else{
				alert(result.message);	
			}
		});
	}
	
	function form2Obj(){
		var formObj = {};
		$("form input").each(function(){
			$this = $(this);
			formObj[$this.attr("name")] = $this.val();
		});
		doReset();
		return formObj;
	}
	
	function doReturnRuleDatas(){
		var url = ctx + "/rulemgt/reader/list.action?rulesetPK=${paramsMap.rulesetPK}";
		window.location.href = url;
	}
	
	-->
	</script>
</head>
  
<body>
<form action="" method="post">
<input type="hidden" name="rulesetPK" value="${paramsMap.rulesetPK}"/>

<div class="searchPageContent">
	<div class="panelBar">
		<ul class="toolBar">
			<li style="margin-left:-20px;"><span>规则集业务对象配置</span></li>
			<li><span style="padding-top:5px;color:red"><cx:Msg/></span></li>
		</ul>
	</div>
	<div class="tableBar">
	<table class="list nowrap" style="width:100%">
		<thead>
		<tr>
			<th width="220px">对象描述</th>
			<th width="160px">对象名称</th>
			<th width="160px">对象类型</th>
			<th>对象测试值</th>
		</tr>
		</thead>
		<tbody id="boCfg">
		</tbody>
	</table>
	</div>
	<div class="formBar">
		请输入参数：<input name="param" /><a href="javascript:void(0);" onclick="doTestCfg();return false;" class="linkbutton"><s:text name="test"/></a>
		<a href="javascript:void(0);" onclick="doReturnRuleDatas();return false;" class="linkbutton" data-options="iconCls:'icon-return'"><s:text name="return"/></a>
	</div>
</div>
</form>
</body>
</html>