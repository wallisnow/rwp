<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/common/taglibs.jsp" %>
<%@ taglib uri="/struts-tags" prefix="s" %>
<c:set var="ISEDIT" value="${'TRUE' eq paramsMap.ISEDIT}"/>
<c:set var="ISNEW" value="${'TRUE' eq paramsMap.ISNEW}"/>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
    
    <title><s:text name="title"/></title>
	<%@ include file="/common/header.jsp"%>

	<script type="text/javascript">
	
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
		$(".tableBar").height(leftH);
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
	/* 页面中选中或取消所有BoCfg配置项*/
	function checkAll(){
		if($("input[name=chooseAll]").attr("checked")){
			$("input[name=paramCfg]").each(function(){
				$chbox = $(this);
				$chbox.attr("checked", true);
			});
		}else{
			$("input[name=paramCfg]").each(function(){
				$chbox = $(this);
				$chbox.attr("checked", false);
			});
		}
	}
	function doAddItem(){
		var itemHtml = [];
		itemHtml.push("<tr>");
		itemHtml.push("<td style='text-align: center'><input type='checkbox' name='paramCfg'/></td>");
		itemHtml.push("<td><input type='text' name='colName' class='textInput' style='width:96%;'/></td>");
		itemHtml.push("<td>" + ParamTypeHtml.replaceAll("#PARAMTYPE#", "paramType") + "</td>");
		itemHtml.push("<td>" + ParamValueSelectHtml.replaceAll("#PARAMVALUE#", "paramValue") + "</td>");
		itemHtml.push("</tr>");
		$("#paramCfg").append(itemHtml.join(""));
		$("table.list").cssTable();
		initTypeControlEvent();
	}
	function doDelItem(){
		$("input[name=paramCfg]").each(function(){
			$chbox = $(this);
			if($chbox.attr("checked")){
				$chbox.parents("tr").remove();	
			}
		});
	}
	
	function doSaveStorage(){
		var hasEmpty = false;
		var emptyObj;
		$("input,select").each(function(){
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
		form.action = ctx + "/rulemgt/storage/save.action";
		form.submit();
	}
	
	function doReturnListRule(){
		var form = document.forms[0];
		form.action = ctx + "/rulemgt/storage/list.action";
		form.submit();
	}
	function autoSetColumnJson(){
		var url = ctx + "/rulemgt/storage/autoSetColumnJson.action";
		var dbId = $("select[name='dbId']").val();
		if(dbId < 1 ){
			alert("请选择数据源！");
			return;
		}
		var tableName = $("input[name='tableName']").val();
		if(tableName < 1 ){
			alert("请输入表名！");
			return;
		}
		if(!confirm("是否确定自动填充字段?")){
			return;
		}
		var params = {"dbId" : dbId,"tableName" : tableName};
		$.getJSON(url, params, function(result){
			if("success" == result.status){
				var datas = [];
				datas = eval('(' + result.message + ')');
				doAutoAddItem (datas);
			}else{
				alert(result.message);
			}
		}); 
	}
	function doAutoAddItem(columnArr){
		$("#paramCfg").empty();
		$.each(columnArr,function(n,value) {  
			var itemHtml = [];
			itemHtml.push("<tr>");
			itemHtml.push("<td style='text-align: center'><input type='checkbox' name='paramCfg' /></td>");
			itemHtml.push("<td><input type='text' name='colName' class='textInput'value='"+value+"' style='width:96%;'/></td>");
			itemHtml.push("<td>" + ParamTypeHtml.replaceAll("#PARAMTYPE#", "paramType") + "</td>");
			itemHtml.push("<td>" + ParamValueSelectHtml.replaceAll("#PARAMVALUE#", "paramValue") + "</td>");
			itemHtml.push("</tr>");
			$("#paramCfg").append(itemHtml.join(""));
			$("table.list").cssTable();
			initTypeControlEvent();
	  	});
		
	}
	</script>
</head>
  
<body>

<form method="post" id="contentForm" action="">
<input type="hidden" name="ISNEW" value="${paramsMap.ISNEW}"/>
<input type="hidden" name="ISEDIT" value="${paramsMap.ISEDIT}"/>
<input type="hidden" name="storageId" value="${paramsMap.storageId}" />
<input type="hidden" name="rulesetId" value="${paramsMap.rulesetId}" />
<input type="hidden" name="rulesetVersion" value="${paramsMap.rulesetVersion}" />
<input type="hidden" name="rulesetPK" value="${paramsMap.rulesetPK}" />

<div class="formPageHeader">
	<div class="formContent">
 		<fieldset id="rule_base" style="margin:5px; padding:5px;">
 			<legend>存储器基本信息 </legend>
 			<p>
 				<label>存储器名称</label>
 				<input type="text" name=storageName class="required" value="${paramsMap.storageName}"/>
 			</p>
 			<p class="nowrap">
 				<label>数据源</label>
 				<cx:select name="dbId" value="%{paramsMap.dbId}" list="!com.congxing.rulemgt.dbconfig.model.DbConfigVO*dbId*dbName" emptyOption="true" headerKey="" headerValue="请选择..." cssClass="combox required"/>
 			</p>
 			<p class="nowrap"  >
 				<label>存储表名</label>
 				<input type="text" name=tableName class="required" value="${paramsMap.tableName}"/>
 				<a href="javascript:void(0)" class="linkbutton" title="自动填充字段" data-options="plain:true,iconCls:'icon-edit'" onclick='autoSetColumnJson();'></a>
 			</p>
 			<p class="nowrap">
 				<label>存储器描述</label>
 				<s:textarea id="storageDesc" name="storageDesc" rows="2" cols="80" value="%{paramsMap.storageDesc}" cssClass="required"/>
 			</p>
 		</fieldset>
 	</div>
 	<div class="panelBar">
		<ul class="toolBar">
			<li><a class="add" href="javascript:doAddItem();"><span>添加</span></a></li>
			<li><a class="delete" href="javascript:doDelItem();"><span>删除</span></a></li>
		</ul>
	</div>
	<div class="tableBar">
	<table class="list">
		<thead>
		<tr>
			<th width="50px"><input type="checkbox" name="chooseAll" onclick="checkAll();"/></th>
			<th>表字段</th>
			<th width="35%">存储类型</th>
			<th width="35%">存储取值</th>
		</tr>
		</thead>
		<tbody id="paramCfg">
			<c:forEach var="item" items="${paramsMap.paramDatas}">
			<c:set target="${paramsMap}" property="paramType"  value="${item.paramType}"/>
			<c:set target="${paramsMap}" property="paramValue"  value="${item.paramValue}"/>
			<tr>
				<td style="text-align: center">
					<input type="checkbox" name="paramCfg"/>
				</td>
				<td>
					<input type='text' name='colName' style='width:98%;' value='${item.colName}'/>
				</td>
				<td>
					<cx:select list="$PARAMTYPE"  function='TypeControl' name="paramType" cssClass="textInput" cssStyle="width:98%;" value="%{paramsMap.paramType}" emptyOption="true" headerKey="" headerValue="请选择入参类型"/>
				</td>
				<td>
					<c:if test="${'FACTOR' eq paramsMap.paramType}">
						<s:select list="%{paramsMap.boDatas}"  name="paramValue" listKey="boId" listValue="boDesc" value="%{paramsMap.paramValue}" cssStyle="width:100%"  headerKey="" headerValue="请选择..."></s:select>	
					</c:if>
					<c:if test="${'FACTOR' ne paramsMap.paramType}">
						<input type='text' name='paramValue' class='textInput' style='width:98%;' value='${item.paramValue}'/>
					</c:if>
				</td>
			</tr>
			</c:forEach>
		</tbody>
	</table>
	</div>
 	<div class="formBar">
		<a href="javascript:void(0);" onclick="doSaveStorage();return false;" class="linkbutton" data-options="iconCls:'icon-save'"><s:text name="save"/></a>
		<a href="javascript:void(0);" onclick="doReturnListRule();return false;" class="linkbutton" data-options="iconCls:'icon-return'"><s:text name="return"/></a>
 	</div>
</div>
</form>
</body>
</html>
