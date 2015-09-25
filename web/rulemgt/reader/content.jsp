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
	
	//加载参数数据类型
	var DataTypeJsonResults = listJson("com.congxing.system.syscode.model.SysCodeVO", "type", "DATATYPE");
	var DataTypeJsonDatas = [];
	if("success" == DataTypeJsonResults.status){
		DataTypeJsonDatas = eval('(' + DataTypeJsonResults.message + ')');
	}else{
		alert(DataTypeJsonResults.message);
	}
	
	var DataTypeArray = [];
	DataTypeArray.push("<select name='#DATATYPE#' style='width:100%;' style='width:100%;'>");
	DataTypeArray.push("	<option value='0' selected>请选择数据类型 </option>");
	for(var idx = 0; idx < DataTypeJsonDatas.length; idx++){
		if("*" != DataTypeJsonDatas[idx].kind){
			DataTypeArray.push("	<option value='" + DataTypeJsonDatas[idx].kind + "'>" + DataTypeJsonDatas[idx].kindname + "</option>");
		}
	}
	DataTypeArray.push("</select>");
	var DataTypeHtml = DataTypeArray.join("");
	

	//加载入参类型
	var ParamTypeJsonResults = listJson("com.congxing.system.syscode.model.SysCodeVO", "type", "PARAMTYPE");
	var ParamTypeJsonDatas = [];
	if("success" == ParamTypeJsonResults.status){
		ParamTypeJsonDatas = eval('(' + ParamTypeJsonResults.message + ')');
	}else{
		alert(ParamTypeJsonResults.message);
	}

	var ParamTypeArray = [];
	ParamTypeArray.push("<select name='#PARAMTYPE#' function='TypeControl' style='width:100%;'>");
	ParamTypeArray.push("	<option value='0' selected>请选择入参类型 </option>");
	for(var idx = 0; idx < ParamTypeJsonDatas.length; idx++){
		if("*" != ParamTypeJsonDatas[idx].kind){
			ParamTypeArray.push("	<option value='" + ParamTypeJsonDatas[idx].kind + "'>" + ParamTypeJsonDatas[idx].kindname + "</option>");
		}
	}
	ParamTypeArray.push("</select>");
	var ParamTypeHtml =ParamTypeArray.join("");
	
	
	/*加载业务对象*/
	var factJsonDatas = [];
	var fact_query_url = ctx + "/rulemgt/ruleset/listRulesetBoJson.action";
	var rulesetPK = "${paramsMap.rulesetPK}";
	var fact_query_params = {
			rulesetPK : rulesetPK
	};
	var fact_query_result;
	$.ajax({
		type : "POST",
		url : fact_query_url,
		data : fact_query_params,
		dataType : 'json',
		async: false,
		success : function(data){fact_query_result = data} 
	});
	if("success" == fact_query_result.status){
		factJsonDatas = eval('(' + fact_query_result.message + ')');
	}
	
	var ParamValueInputArray = [];
	ParamValueInputArray.push("<input type='text' name='#PARAMVALUE#' style='width:98%;' class='textInput'/>");
	var ParamValueInputHtml = ParamValueInputArray.join("");

	var ParamValueSelectArray = [];
	ParamValueSelectArray.push("<select name='#PARAMVALUE#' style='width:100%;' class='textInput'>");
	ParamValueSelectArray.push("	<option value=''>请选择...</option>");
	
	for(var i = 0; i < factJsonDatas.length; i++){
		ParamValueSelectArray.push("	<option value='" + factJsonDatas[i].boId + "' dataType='" + factJsonDatas[i].dataType + "'>" + factJsonDatas[i].boDesc + "</option>");
	}
	ParamValueSelectArray.push("</select>");
	var ParamValueSelectHtml = ParamValueSelectArray.join("");
	
	
	$(function(){
		initPageEnv(initCurrentPageLayout);
		initTypeControlEvent();
	});
	
	function initCurrentPageLayout(){
		var pageH = $(window).height();
		var h2Title = $("h2.contentTitle").outerHeight(true) || 0;//标题栏高度
		var formContentH = $("div.formContent").outerHeight(true);
		var formBarH = $("div.formBar").outerHeight(true) || 0;
		var panelBarH =  $("div.panelBar").outerHeight(true) || 0;//页面中间工具栏高度
		var panelBarNum = $("div.panelBar").length;
		var leftH = pageH - h2Title - formContentH - formBarH - (panelBarH * panelBarNum);
		$(".tableBar:eq(0)").height(leftH * 0.6);
		$(".tableBar:eq(1)").height(leftH * 0.4);
	}
	
	function initTypeControlEvent(){
		$("select[function=TypeControl]").each(function(){
			$(this).unbind("change");
			$(this).change(function(event){
				var typeObj = $(event.currentTarget);
				var paramType = $(typeObj).val();
				if("FACTOR" == paramType){
					$(typeObj).parents("tr").find("[name=paramValue]").remove();
					$(typeObj).parents("tr").find("td:eq(3)").append(ParamValueSelectHtml.replaceAll("#PARAMVALUE#", "paramValue"));
				}else{
					$(typeObj).parents("tr").find("[name=paramValue]").remove();
					$(typeObj).parents("tr").find("td:eq(3)").append(ParamValueInputHtml.replaceAll("#PARAMVALUE#", "paramValue"));
				}
			});
		});
	}
	function doAddValue(){
		var valueHtml = [];
		valueHtml.push("<tr>");
		valueHtml.push("<td><input type='text' name='valueIdx' class='textInput' style='width:96%;'/></td>");
		valueHtml.push("<td>" + ParamValueSelectHtml.replaceAll("#PARAMVALUE#", "boId")+ "</td>");
		valueHtml.push("</tr>");
		$("#valueCfg").append(valueHtml.join(""));
		$("table.list").cssTable();
		initTypeControlEvent();
	}
	function doDelValue(){
		$("#valueCfg").find("tr:last").remove();
	}
	
	function doAddParam(){
		var paramIdx = $("#paramCfg").find("tr").length + 1;
		var paramHtml = [];
		paramHtml.push("<tr>");
		paramHtml.push("<td><input type='text' name='paramIdx' value='"+paramIdx+"' readonly='true' style='width:100%;'/></td>");
		paramHtml.push("<td>" + DataTypeHtml.replaceAll("#DATATYPE#", "dataType")+ "</td>");
		paramHtml.push("<td>" + ParamTypeHtml.replaceAll("#PARAMTYPE#", "paramType")+ "</td>");
		paramHtml.push("<td>" + ParamValueSelectHtml.replaceAll("#PARAMVALUE#", "paramValue")+ "</td>");
		paramHtml.push("</tr>"	);
		$("#paramCfg").append(paramHtml.join(""));
		$("table.list").cssTable();
		initTypeControlEvent();
	}
	function doDelParam(){
		$("#paramCfg").find("tr:last").remove();
	}
	
	function doSave(){
		var hasEmpty = false;
		var emptyObj;
		$("select, input").each(function(){
			if(!hasEmpty){
				var thisValue = $(this).val();
				if(thisValue == "" || thisValue.length == 0){
					hasEmpty = true;
					emptyObj = $(this);
				}
			}
		});

		if(hasEmpty){
			alert("请给页面中的元素输入必要的值");
			$(emptyObj).css("border-color", "red");
			emptyObj.focus();
			return;
		}

		var form = document.forms[0];
		form.action = ctx + "/rulemgt/reader/save.action";
		form.submit();
	}
	
	function doReturnListRule(){
		var form = document.forms[0];
		form.action = ctx + "/rulemgt/reader/list.action";
		form.submit();
	}
	function showMemo(){
		var content = "";
		content += "<div style=\"margin:-20px 0 0 -20px; padding 0px; font-size:12px; line-height:23px; font-family:Arial, sans-serif;\">";
		content += "1、默认优先级为10，数值越大优先级越高;" + "</br>";
		content += "2、数值最大的为主取数器，其它为副取数器;" + "</br>";
		content += "3、同一个规则集下的取数器优先级值请不要重复;" + "</br>";
		content += "</div>";
		window.top.art.dialog(
			{
				title: "温馨提示",
				content: content
			}
		);
	}
	function testReaderSQL(){
		var url = ctx + "/rulemgt/reader/testReaderSQL.action";
		var dbId = $("select[name='dbId']").val();
		if(dbId < 1 ){
			alert("请选择数据源！");
			return;
		}
		var readerSql = $("textarea[name='readerSql']").val();
		if(readerSql < 1 ){
			alert("请输入表名！");
			return;
		}
		var params = {"dbId" : dbId,"readerSql" : readerSql};
		$.getJSON(url, params, function(result){
			if("success" == result.status){
				alert("查询成功，返回数据条数为："+result.message);
			}else{
				alert("查询失败："+result.message);
			}
		}); 
	}
	</script>
</head>
  
<body>

<form method="post" id="contentForm" action="">
<input type="hidden" name="ISNEW" value="${paramsMap.ISNEW}"/>
<input type="hidden" name="ISEDIT" value="${paramsMap.ISEDIT}"/>
<input type="hidden" name="readerId" value="${paramsMap.readerId}" />
<input type="hidden" name="rulesetId" value="${paramsMap.rulesetId}" />
<input type="hidden" name="rulesetVersion" value="${paramsMap.rulesetVersion}" />
<input type="hidden" name="rulesetPK" value="${paramsMap.rulesetPK}" />

<div class="formPageHeader">
	<div class="formContent">
 		<fieldset id="rule_base" style="margin:5px; padding:5px;">
 			<legend>取数器基本信息 </legend>
 			<p>
 				<label>取数器名称</label>
 				<input type="text" name=readerName class="required" value="${paramsMap.readerName}"/>
 			</p>
 			<p>
 				<label>优先级</label>
 				<input type="text" name=readerPriority class="required" value="${paramsMap.readerPriority}"/>
 				<a href="javascript:void(0)" class="linkbutton" title="温馨提示" data-options="plain:true,iconCls:'icon-tip'" onclick='showMemo();'></a>
 			</p>
 			<p>
 				<label>数据源</label>
 				<cx:select name="dbId" value="%{paramsMap.dbId}" list="!com.congxing.rulemgt.dbconfig.model.DbConfigVO*dbId*dbName" emptyOption="true" headerKey="" headerValue="请选择..." cssClass="combox required"/>
 			</p>
 			<p class="nowrap">
 				<label>取数器SQL</label>
 				<s:textarea name="readerSql" rows="4" cols="120" value="%{paramsMap.readerSql}" cssClass="required"/>
 				<a href="javascript:void(0)" class="linkbutton" title="请输入完整SQL，点击进行查询测试" data-options="plain:true,iconCls:'icon-preview'" onclick='testReaderSQL();'></a>
 			</p>
 			<p class="nowrap">
 				<label>取数器描述</label>
 				<s:textarea id="readerDesc" name="readerDesc" rows="2" cols="80" value="%{paramsMap.readerDesc}" cssClass="required"/>
 			</p>
 		</fieldset>
 	</div>
	<div class="panelBar">
		<ul class="toolBar">
			<li><a class="add" href="javascript:doAddValue();"><span>结果赋值</span></a></li>
			<li><a class="delete"  href="javascript:doDelValue();"><span>删除</span></a></li>
		</ul>
	</div>
	<div class="tableBar">
	<table class="list nowrap">
		<thead>
		<tr>
			<th width="10%">字段序号</th>
			<th width="80%">赋值变量</th>
		</tr>
		</thead>
		<tbody id="valueCfg">
			<c:forEach var="item" items="${paramsMap.valueDatas}">
				<c:set target="${paramsMap}" property="boId" value="${item.boId}" />
				<tr>
					<td>
						<input type="text" name="valueIdx" value="${item.valueIdx}" style="width:96%;"/>
					</td>
					<td>
						<cx:select name="boId" list="%{paramsMap.boDatas}" listKey="boId" listValue="boDesc" value="%{paramsMap.boId}" cssStyle="width:100%;" headerKey="" headerValue="请选择..." />	
					</td>
				</tr>
		</c:forEach>
		</tbody>
	</table>
	</div>
	<div class="panelBar">
		<ul class="toolBar">
			<li><a class="add"  href="javascript:doAddParam();"><span>参数赋值</span></a></li>
			<li><a class="delete" href="javascript:doDelParam();"><span>删除</span></a></li>
		</ul>
	</div>
	<div class="tableBar">
	<table class="list">
		<thead>
		<tr>
			<th>参数字段序号</th>
			<th width="15%">数据类型</th>
			<th width="15%">参数类型</th>
			<th width="60%">参数值</th>
		</tr>
		</thead>
		<tbody id="paramCfg">
			<c:forEach var="item" items="${paramsMap.paramDatas}">
			<c:set target="${paramsMap}" property="dataType" value="${item.dataType}" />
			<c:set target="${paramsMap}" property="paramType" value="${item.paramType}" />
			<c:set target="${paramsMap}" property="paramValue" value="${item.paramValue}" />
			<tr>
				<td>
					<input type="text" name="paramIdx" value="${item.paramIdx}" style="width:100%;" readonly="true"/>
				</td>
				<td>
					<cx:select list="$DATATYPE" name="dataType" cssStyle="width:100%;" value="%{paramsMap.dataType}" emptyOption="true" headerKey="" headerValue="请选择数据类型"/>
				</td>
				<td>
					<cx:select list="$PARAMTYPE" function='TypeControl' name="paramType" cssStyle="width:100%;" value="%{paramsMap.paramType}" emptyOption="true" headerKey="" headerValue="请选择入参类型"/>
				</td>
				<td>
					<c:if test="${'FACTOR' eq item.paramType}">
						<s:select list="%{paramsMap.boDatas}" name="paramValue" label="" listKey="boId" listValue="boDesc" value="%{paramsMap.paramValue}" cssStyle="width:100%"  headerKey="" headerValue="请选择..."/>	
					</c:if>
					<c:if test="${'FACTOR' ne item.paramType}">
						<input type='text' name='paramValue' class='textInput' style='width:98%;' value='${item.paramValue}'/>
					</c:if>
				</td>
			</tr>
			</c:forEach>
		</tbody>
	</table>
	</div>
 	<div class="formBar">
		<a href="javascript:void(0);" onclick="doSave();return false;" class="linkbutton" data-options="iconCls:'icon-save'"><s:text name="save"/></a>
		<a href="javascript:void(0);" onclick="doReturnListRule();return false;" class="linkbutton" data-options="iconCls:'icon-return'"><s:text name="return"/></a>
 	</div>
</div>
</form>
</body>
</html>
