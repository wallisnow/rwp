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

	$(function(){
		initPageEnv("content");
	});
	
	function doSaveRule(){
		if(!formCheck()){
			return;
		}
		var form = document.forms[0];
		form.action = ctx + "/rulemgt/ruleset/saveRuleManual.action";
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

<div class="formPageHeader">
	<div class="formContent">
 		<fieldset id="rule_base" style="margin:5px; padding:5px;">
 			<legend>规则基本信息 </legend>
 			<p>
 				<label>规则中文名称</label>
 				<input type="text" name="ruleZhName" value="${paramsMap.ruleZhName}" class="required"/>
 			</p>
 			<p>
 				<label>规则英文名称</label>
 				<input type="text" name="ruleEnName"  value="${paramsMap.ruleEnName}" class="required"/>
 			</p>
 			<p>
 				<label>规则优先级</label>
 				<input type="text" name="ruleSalience"  value="${paramsMap.ruleSalience}" class="required"/>
 				<a href="javascript:void(0)" class="linkbutton" title="温馨提示" data-options="plain:true,iconCls:'icon-tip'" onclick='showMemo();'></a>
 			</p>
 			<p class="nowrap">
 				<label>规则描述</label><a></a>
 				<s:textarea name="ruleDesc" rows="3" cols="80" value="%{paramsMap.ruleDesc}" cssClass="required"/>
 			</p>
 		</fieldset>
 		
 		<fieldset>
 			<legend>规则脚本</legend>
			<s:textarea name="ruleCode" value="%{paramsMap.ruleCode}" rows="18" cssClass="required" cssStyle="width:99%; margin:0px 1px 0px 1px; padding-left:5px; line-heigth:21px;"/>
 		</fieldset>
 	</div>
 	<div class="formBar">
		<a href="javascript:void(0);" onclick="doSaveRule();return false;" class="linkbutton" data-options="iconCls:'icon-save'"><s:text name="save"/></a>
		<a href="javascript:void(0);" onclick="doReturnListRule();return false;" class="linkbutton" data-options="iconCls:'icon-return'"><s:text name="return"/></a>
 	</div>
</div>


</form>
</body>

</html>