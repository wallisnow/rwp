<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/common/taglibs.jsp" %>
<c:set var="ISEDIT" value="${'TRUE' eq paramsMap.ISEDIT}"/>
<c:set var="ISNEW" value="${'TRUE' eq paramsMap.ISNEW}"/>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
    
    <title><s:text name="title"/></title>
	<%@ include file="/common/header.jsp"%>
	
	<link href="${ctx}/rulemgt/ruleset/contentrule.css" rel="stylesheet" type="text/css"/>

	<script type="text/javascript">
	
	
	$(function(){
		if("TRUE" != "${paramsMap.ISNEW}"){
			var ruleVO = voJson("com.congxing.rulemgt.ruleset.model.RuleVO", "ruleId", "${paramsMap.ruleId}");
			if("success" == ruleVO.status){
				var ruleJson = eval('(' + ruleVO.message + ')');
				$(".when_cfg").html(ruleJson.ruleWhenHtml);
				$(".then_cfg").html(ruleJson.ruleThenHtml);
			}
		}
		
		$(window).resize(function(){
			initPageLayout("content");
		});
		setTimeout(function(){
			initCurrentPageUI();
			initPageLayout("content");
		}, 50);
	});
	
	function doBuildRuleSemantics(){
		if(!(checkForm())){
			alert("页面信息不完整");
			return;
		}
		var ruleSemantics = buildRuleSemantics();
		$("#ruleDescDiv").html(ruleSemantics);
	}
	
	function doSaveRule(){
		if(!(checkForm())){
			alert("页面信息不完整");
			return;
		}
		/**此方法为在chrome浏览器下面取value值属性**/
		$("input").each(function(){
			$input = $(this);
			var thval = $input.val();
			if(thval.trim().length > 0){
				$input.attr("value", thval);
			}
		});
		$("input[name=ruleWhenHtml]").val($(".when_cfg").html());
		$("input[name=ruleThenHtml]").val($(".then_cfg").html());
		$("input[name=ruleSemantics]").val(buildRuleSemantics());
		$("input[name=ruleCode]").val(buildRuleCode());
		var form = document.forms[0];
		form.action = ctx + "/rulemgt/ruleset/saveRule.action";
		form.submit();
	}
	
	function doReturnListRule(){
		var form = document.forms[0];
		form.action = ctx + "/rulemgt/ruleset/listRule.action";
		form.submit();
	}
	function showMemo(){
		var content = "";
		content += "<div style=\"margin:-20px 0 0 -20px; padding 0px; font-size:12px; line-height:23px; font-family:Arial, sans-serif;\">";
		content += "1、默认优先级为10，数值越大优先级越高;" + "</br>";
		content += "2、同一个规则集下的取数器优先级值请不要重复;" + "</br>";
		content += "</div>";
		window.top.art.dialog(
			{
				title: "温馨提示",
				content: content
			}
		);
	}
	</script>
</head>
  
<body>

<h2 class="contentTitle" style="margin-bottom:0px;">规则配置<cx:Msg/></h2>

<form method="post" id="contentForm" action="">
<input type="hidden" name="ISNEW" value="${paramsMap.ISNEW}"/>
<input type="hidden" name="ISEDIT" value="${paramsMap.ISEDIT}"/>
<input type="hidden" name="ruleId" value="${paramsMap.ruleId}" />
<input type="hidden" name="rulesetPK" value="${paramsMap.rulesetPK}" />
<input type="hidden" name="rulesetId" value="${paramsMap.rulesetId}" />
<input type="hidden" name="rulesetVersion" value="${paramsMap.rulesetVersion}" />
<input type="hidden" name="ruleType" value="${paramsMap.ruleType}" />
<input type="hidden" name="ruleWhenHtml"/>
<input type="hidden" name="ruleThenHtml"/>
<input type="hidden" name="ruleSemantics"/>
<input type="hidden" name="ruleCode"/>

<div class="formPageHeader">
	<div class="formContent">
 		<fieldset id="rule_base" style="margin:5px; padding:5px;">
 			<legend>规则基本信息 </legend>
 			<p>
 				<label>规则中文名称</label>
 				<input type="text" name="ruleZhName" value="${paramsMap.ruleZhName}"/>
 			</p>
 			<p>
 				<label>规则英文名称</label>
 				<input type="text" name="ruleEnName"  value="${paramsMap.ruleEnName}"/>
 			</p>
 			<p>
 				<label>规则优先级</label>
 				<input type="text" name="ruleSalience"  value="${paramsMap.ruleSalience}"/><a href="javascript:void(0)" class="linkbutton" title="温馨提示" data-options="plain:true,iconCls:'icon-tip'" onclick='showMemo();'></a>
 			</p>
 			<p class="nowrap">
 				<label>规则描述</label>
 				<s:textarea name="ruleDesc" rows="3" cols="80" value="%{paramsMap.ruleDesc}" />
 			</p>
 		</fieldset>
 		
 		<fieldset>
 			<legend>条件 (<a href="javascript:void(0);" class="linkbutton" data-options="plain:true, iconCls:'icon-add'" name="AddWhenCfg" onclick="addGroupWhenCfg()">增加组规则</a> &nbsp;&nbsp;<a href="javascript:void(0);" class="linkbutton" data-options="plain:true, iconCls:'icon-delete'" name="DelWhenCfg" onclick="deleteGroupWhenCfg()">删除组规则</a>)</legend>
			<div class="when_cfg">
				<ul class="when_cfg_ul">
				</ul>
			</div>
 		</fieldset>
 		
 		<fieldset>
 			<legend>动作 ( <a href="javascript:void(0)" class="linkbutton" data-options="plain:true" onclick="addThenAssign();">赋值操作</a>)</legend>
 			<div class="then_cfg">
				<ul class="then_cfg_ul">
				</ul>
			</div>
 		</fieldset>
 		
 		<fieldset id="ruleSemantics">
 			<legend>规则语义</legend>
 			<div id="ruleDescDiv" style="text-align: left; margin-left: 5px;">
 			</div>
 		</fieldset>
 	</div>
 	<div class="formBar">
		<a href="javascript:void(0);" onclick="doBuildRuleSemantics();return false;" class="linkbutton">生成规则语义</a>
		<a href="javascript:void(0);" onclick="doSaveRule();return false;" class="linkbutton" data-options="iconCls:'icon-save'"><s:text name="save"/></a>
		<a href="javascript:void(0);" onclick="doReturnListRule();return false;" class="linkbutton" data-options="iconCls:'icon-return'"><s:text name="return"/></a>
 	</div>
</div>
</form>

<script src="${ctx}/rulemgt/ruleset/contentrule.js" type="text/javascript"></script>

</body>

</html>

