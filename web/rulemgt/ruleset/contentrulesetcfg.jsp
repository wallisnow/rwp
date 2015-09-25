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
		$(window).resize(function(){
			initCurPageLayout();
		});
		setTimeout(function(){
			initPageUI();
			initCurPageLayout();
		}, 50);
	});
	
	function initCurPageLayout(){
		var pageH = $(window).height();//可视区域高度
		var pageW = $(window).width();//可视区域宽度
		var h2TitleH = $("h2.contentTitle").outerHeight(true);//标题栏高度
		if(h2TitleH == null){
			h2TitleH = 0;
		}
		var panelBarH = $("div.panelBar").outerHeight(true);
		var panelBarNum = $("div.panelBar").length;
		var formBarH = $("div.formBar").outerHeight(true);
		var tableBarTH = pageH - h2TitleH - (panelBarH * panelBarNum) - formBarH;
		//$("div.tableBar:eq(0)").height(tableBarTH * 0.6).css("overflow-y", "scroll");
		//$("div.tableBar:eq(1)").height(tableBarTH * 0.4).css("overflow-y", "scroll");
		
		$("div.tableBar:").height(tableBarTH).css("overflow-y", "scroll");
		$("div.tableBar>table").width(pageW - 20);
	}
	
	function doAddBo(){
		var boHtml = [];
		var DataTypeJsonResults = listJson("com.congxing.system.syscode.model.SysCodeVO", "type", "DATATYPE");
		var DataTypeJsonDatas = [];
		if("success" == DataTypeJsonResults.status){
			DataTypeJsonDatas = eval('(' + DataTypeJsonResults.message + ')');
		}else{
			alert(DataTypeJsonResults.message);
			return;
		}
		boHtml.push("<tr>");
		boHtml.push("<td style='text-align: center'><input type='checkbox' name='BoCfg'/></td>");
		boHtml.push("<td><input type='text' name='boName' style='width:96%;'/></td>");
		boHtml.push("<td><input type='text' name='boDesc' style='width:98%;'/></td>");
		boHtml.push("<td>");
		boHtml.push("	<select name='dataType' style='width:98%;'>");
		boHtml.push(" 		<option value=''>请选择数据类型</option>");
		for(var idx = 0; idx < DataTypeJsonDatas.length; idx++){
			if("*" != DataTypeJsonDatas[idx].kind){
				boHtml.push("	<option value='" + DataTypeJsonDatas[idx].kind + "'>" + DataTypeJsonDatas[idx].kindname + "</option>");
			}
		}
		boHtml.push("	</select>");
		boHtml.push("</td>")
		boHtml.push("</tr>");
		$("#boCfg").append(boHtml.join(""));
		$("table.list").cssTable();
	}
	
	function doAddFun(){
		var boHtml = [];
		boHtml.push("<tr>");
		boHtml.push("<td style='text-align: center'><input type='checkbox' name='FunCfg'/></td>");
		boHtml.push("<td><input type='text' name='funName' class='textInput' style='width:98%;'/></td>");
		boHtml.push("<td><input type='text' name='funDesc' class='textInput' style='width:98%;'/></td>");
		boHtml.push("</tr>");
		$("#funCfg").append(boHtml.join(""));
		$("table.list").cssTable();
	}
	
	/* 页面中选中或取消所有BoCfg配置项*/
	function checkBoCfgAll(){
		if($("input[name=chooseBoCfgAll]").attr("checked")){
			$("input[name=BoCfg]").each(function(){
				$chbox = $(this);
				$chbox.attr("checked", true);
			});
		}else{
			$("input[name=BoCfg]").each(function(){
				$chbox = $(this);
				$chbox.attr("checked", false);
			});
		}
	}
	
	function checkFunCfgAll(){
		if($("input[name=chooseFunCfgAll]").attr("checked")){
			$("input[name=FunCfg]").each(function(){
				$chbox = $(this);
				$chbox.attr("checked", true);
			});
		}else{
			$("input[name=FunCfg]").each(function(){
				$chbox = $(this);
				$chbox.attr("checked", false);
			});
		}
	}
	
	function doDelBoCfg(){
		$("input[name=BoCfg]").each(function(){
			$chbox = $(this);
			if($chbox.attr("checked")){
				$chbox.parent().parent().remove();	
			}
		});
	}
	
	function doDelFunCfg(){
		$("input[name=FunCfg]").each(function(){
			$chbox = $(this);
			if($chbox.attr("checked")){
				$chbox.parent().parent().remove();	
			}
		});
	}
	
	function doSaveRulesetCfg(){
		var obj;
		var hasFind = false;
		$("input[name=boName], input[name=boDesc], select[name=boType], input[name=funName], input[name=funDesc]").each(function(){
			var value = $(this).val();
			if(value == ""){
				if(!hasFind){
					obj = $(this);
					hasFind = true;	
				}
			}
		});
		if(hasFind){
			alert("请填写相关规则配置信息");
			obj.focus();
			return;
		}
		
		var url = ctx + "/rulemgt/ruleset/saveRulesetCfg.action";
		var form = document.forms[0];
		form.action = url;
		form.submit();
	}
	
	function doReturnRulesetList(){
		var url = ctx + "/rulemgt/ruleset/listdetail.action?_leq_rulesetId=${RulesetVO.rulesetId}";
		window.location.href = url;
	}
	-->
	</script>
</head>

<body>


<form action="${ctx}/rulemgt/ruleset/list.action" method="post">

<input type="hidden" name="rulesetPK" value="${RulesetVO.rulesetId}|${RulesetVO.rulesetVersion}"/>

<div class="searchPageContent">
	<div class="panelBar">
		<ul class="toolBar">
			<li style="margin-left:-20px;"><span>规则因子配置</span></li>
			<li><span style="padding-top:5px;color:red"><cx:Msg/></span></li>
		</ul>
	</div>
	<div class="panelBar">
		<ul class="toolBar">
			<c:if test="${'DRAFT' eq RulesetVO.status}">
			<li><a class="add" href="javascript:doAddBo();"><span>添加</span></a></li>
			<li><a class="delete" href="javascript:doDelBoCfg();"><span>删除</span></a></li>
			</c:if>
		</ul>
	</div>
	<div class="tableBar">
	<table class="list nowrap">
		<thead>
		<tr>
			<th width="50px"><input type="checkbox" name="chooseBoCfgAll" onclick="checkBoCfgAll();"/></th>
			<th width="160px">对象名称</th>
			<th>对象描述</th>
			<th width="160px">数据类型</th>
		</tr>
		</thead>
		<tbody id="boCfg">
			<c:forEach var="item" items="${RulesetVO.boDatas}">
			<c:set target="${paramsMap}" property="dataType"  value="${item.dataType}"/>
			<tr>
				<td style="text-align: center">
					<input type="checkbox" name="BoCfg"/>
				</td>
				<td>
					<input type='text' name='boName' style='width:96%;' value='${item.boName}'/>
				</td>
				<td>
					<input type='text' name='boDesc' style='width:98%;' value='${item.boDesc}'/>
				</td>
				<td>
					<cx:select list="$DATATYPE" name="dataType" cssStyle="width:98%;" value="%{paramsMap.dataType}" emptyOption="true" headerKey="" headerValue="请选择数据类型"/>
				</td>
			</tr>
			</c:forEach>
		</tbody>
	</table>
	</div>
	
	<div class="formBar">
		<c:if test="${'DRAFT' eq RulesetVO.status}">
			<a href="javascript:void(0);" onclick="doSaveRulesetCfg();return false;" class="linkbutton" data-options="iconCls:'icon-save'"><s:text name="save"/></a>
		</c:if>
		<a href="javascript:void(0);" onclick="doReturnRulesetList();return false;" class="linkbutton" data-options="iconCls:'icon-return'"><s:text name="return"/></a>
	</div>
</div>
</form>
</body>
</html>

