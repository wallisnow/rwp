/*加载业务对象*/
var factJsonDatas = [];
var fact_query_url = ctx + "/rulemgt/ruleset/listRulesetBoJson.action";
var fact_query_rulesetPK = $("input[name='rulesetPK']").val();
var fact_query_params = {
	rulesetPK : fact_query_rulesetPK
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

/*加载函数对象*/
var funJsonDatas = [];
var fun_query_url = ctx + "/rulemgt/ruleset/listRulesetFunJson.action";
var fun_query_rulesetPK = $("input[name='rulesetPK']").val();
var fun_query_params = {
	rulesetPK : fun_query_rulesetPK
};
var fun_query_result;
$.ajax({
	type : "POST",
	url : fun_query_url,
	data : fun_query_params,
	dataType : 'json',
	async: false,
	success : function(data){fun_query_result = data} 
});

if("success" == fun_query_result.status){
	funJsonDatas = eval('(' + fun_query_result.message + ')');
}

/** 关系运算符选择项 **/
var RelationOperatorArray = [];
RelationOperatorArray.push("<div id='RelationOperator' class='selectBox' >");
RelationOperatorArray.push("	<ul>");
RelationOperatorArray.push("		<li key='&#62'>&#62</li>");
RelationOperatorArray.push("		<li key='&#62&#61'>&#62&#61</li>");
RelationOperatorArray.push("		<li key='&#61&#61'>&#61&#61</li>");
RelationOperatorArray.push("		<li key='&#60'>&#60</li>");
RelationOperatorArray.push("		<li key='&#60&#61'>&#60&#61</li>");
RelationOperatorArray.push("		<li key='&#33&#61'>&#33&#61</li>");
RelationOperatorArray.push("	<ul>");
RelationOperatorArray.push("</div>");
var RelationOperator = RelationOperatorArray.join("");

/** 逻辑运算符选择项 **/
var LogicOperatorArray = [];
LogicOperatorArray.push("<div id='LogicOperator' class='selectBox'>");
LogicOperatorArray.push("	<ul>");
LogicOperatorArray.push("		<li key='&#38&#38'>&#38&#38</li>");
LogicOperatorArray.push("		<li key='&#124&#124'>&#124&#124</li>");
LogicOperatorArray.push("	</ul>");
LogicOperatorArray.push("</div>");
var LogicOperator = LogicOperatorArray.join("");

/** 算术运算符选择项 **/
var ArithOperatorArray = [];
ArithOperatorArray.push("<div id='ArithOperator' class='selectBox'>");
ArithOperatorArray.push("	<ul>");
ArithOperatorArray.push("		<li key='&#43'>&#43</li>");
ArithOperatorArray.push("		<li key='&#45'>&#45</li>");
ArithOperatorArray.push("		<li key='&#215'>&#215</li>");
ArithOperatorArray.push("		<li key='&#247'>&#247</li>");
ArithOperatorArray.push("	</ul>");
ArithOperatorArray.push("</div>");
var ArithOperator = ArithOperatorArray.join("");


/** 规则因子--选择型 **/
var FactorSelectArray = [];
FactorSelectArray.push("<div id='FactorSelect' class='selectBox'>");
FactorSelectArray.push("	<ul>");
for(var i = 0; i < factJsonDatas.length; i++){
	FactorSelectArray.push("	<li key='" + factJsonDatas[i].boName + "' dataType='" + factJsonDatas[i].dataType + "'>" + factJsonDatas[i].boDesc + "</li>");
}
FactorSelectArray.push("	</ul>");
FactorSelectArray.push("</div>");
var FactorSelect = FactorSelectArray.join("");

/** 函数--选择型 **/
var FunctionSelectArray = [];
FunctionSelectArray.push("<div id='FunctionSelect' class='selectBox'>");
FunctionSelectArray.push("	<ul>");
for(var i = 0; i < funJsonDatas.length; i++){
	FunctionSelectArray.push("	<li key='" + funJsonDatas[i].funName + "'>" + funJsonDatas[i].funDesc + "</li>");
}
FunctionSelectArray.push("	</ul>");
FunctionSelectArray.push("</div>");
var FunctionSelect = FunctionSelectArray.join("");

var FactorInputTemplate = '<input type="text" id="#ID#" class="factorInput"/>';

var AssignOperatorTemplate = '<input type="text" id="#ID#" class="assignOperatorInput" VALUE="=" readonly="true"/>';

var FactorSelectTemplate = '<input type="text" id="#ID#" class="selectBox factorSelect" dataSource="FactorSelect" key="" />';

var RelationOperatorTemplate = '<input type="text" id="#ID#" class="selectBox operatorSelect" dataSource="RelationOperator" key=""/>';

var LogicOperatorTemplate = '<input type="text" id="#ID#" class="selectBox operatorSelect" dataSource="LogicOperator" key="" />';

var ArithOperatorTemplate = '<input type="text" id="#ID#" class="selectBox operatorSelect" dataSource="ArithOperator" key=""/>';

var FunctionSelectTemplate = '<input type="text" id="#ID#" class="selectBox functionSelect" dataSource="FunctionSelect" key=""/>';

var TypeControlTemplate = '<a href="javascript:void(0);" id="#ID#" class="linkbutton" data-options="plain:true,iconCls:\'icon-value\'" function="TypeControl" type="value"></a>';



function initSelectBox(){
	$("body").append(RelationOperator);
	$("body").append(LogicOperator);
	$("body").append(ArithOperator);
	$("body").append(FactorSelect);
	$("body").append(FunctionSelect);
}

function hideSelectBox(selectBoxId){
	if(typeof(selectBoxId) != "undefined"){
		$("#" + selectBoxId).hide();
	}else{
		$("div.selectBox").hide();
	}
	$(document).unbind("click");
}


/**
 * 加载页面linkbutton样式
 * @memberOf {TypeName} 
 */
function initCurrentPageUI(){
	initLinkButtonUI();
	initTypeControlUI();
	initSelectBox();
	initSelectBoxUI();
	initDelThenEvent();
}

/**
 * 初始化linkbutton样式
 * @memberOf {TypeName} 
 */
function initLinkButtonUI(){
	$("a.linkbutton").each(function(){
		var options = $(this).attr("data-options");
		if(typeof(options) == "string"){
			if(options.toString().indexOf(":",0) > 0){
				$(this).linkbutton(eval('({' + options + '})'));
			}else{
				$(this).linkbutton(options);
			}
		}else{
			$(this).linkbutton();
		}
	});
}
/**
 * 初始化selectBox样式
 * @memberOf {TypeName} 
 */
function initSelectBoxUI(){
	$("input.selectBox").each(function(){
		$(this).attr("readonly", true);
		$(this).unbind("click");//先解除绑定事件
		$(this).click(function(event){//重新添加绑定事件
			var $inputBox = $(this);
			var $dataSource = $inputBox.attr("dataSource") || "";
			var $selectBox = $("#" + $dataSource);
			var curKey = $(this).attr("key") || "";
			$selectBox.find("li").hoverClass();
			$selectBox.find("li.selected").removeClass("selected");
			$selectBox.find("li").each(function(idx){
				var thisKey = $(this).attr("key");
				if(curKey == thisKey){
					$(this).addClass("selected");
				}
			});
			if($selectBox.height() > 300) {
				$selectBox.css({"height":"300px"});
			}
			var selectBoxW = $inputBox.width();
			$selectBox.css("width", selectBoxW + "px");
			var top = $inputBox.offset().top + $inputBox.height();//计算相对展示高度
			if(top + $inputBox.height() > $(window).height() - 20) {
				top =  $(window).height() - 20 - $inputBox.height();
			}
			var left = $inputBox.offset().left;
			$selectBox.css({top:top, left:left}).show();
			$selectBox.find("li").each(function(){
				$(this).unbind("click");
				$(this).click(function(event){
					hideSelectBox($dataSource);
					var key = $(this).attr("key");
					var dataType = $(this).attr("dataType");
					var text = $(this).text();
					$inputBox.attr("key", key).attr("dataType", dataType).attr("value", text);
				});
				$(document).unbind("click");
			});
			$(document).click(function(event){
				hideSelectBox();
				$(document).unbind("click");
			});
			event.stopPropagation();
		});
	});
}

/**
 * 初始化类型控制UI样式及事件
 * @memberOf {TypeName} 
 */
function initTypeControlUI(){
	$("[function=TypeControl]").each(function(){
		$(this).unbind("click");
		$(this).click(function(event){
			var curType = $(this).attr("type");
			var newType = "";
			if("value" == curType){
				newType = "property";
				$(this).find("span.icon-value").removeClass("icon-value").addClass("icon-property");
				$(this).attr("type", "property");
			}else{
				$(this).find("span.icon-property").removeClass("icon-property").addClass("icon-value");
				$(this).attr("type", "value");
				newType = "value";
			}
			var typeCtlObj = $(event.currentTarget);
			var typeCtlVal = $(typeCtlObj).val();
			var typeCtlName = $(typeCtlObj).attr("id");
			var right_p_id = typeCtlName.replace("type_ctl", "right_p");
			$("#" + right_p_id).remove();
			if("value" == newType){
				$(typeCtlObj).after(FactorInputTemplate.replaceAll("#ID#", right_p_id));
			}
			if("property" == newType){
				$(typeCtlObj).after(FactorSelectTemplate.replaceAll("#ID#", right_p_id));
				initSelectBoxUI();
			}
		});
	});
}


/**
 * 初始化添加条件操作事件
 * @memberOf {TypeName} 
 */
function addGroupWhenCfg(){
	var liIdx = 1;
	if($(".when_cfg_ul").find("li").length > 0){
		var lastLiIdx = $(".when_cfg_ul").find("li:last").attr("idx");
		liIdx = parseInt(lastLiIdx) + 1;
	}else{
		liIdx = 1;
	}
	var whenPrefix = "when_" + liIdx;
	var whenHtml = [];
	var left_p_id = whenPrefix + "_left_p_1"; 
	var rel_op_id = whenPrefix + "_rel_op_1";
	var type_ctl_id = whenPrefix + "_type_ctl_1";
	var right_p_id = whenPrefix + "_right_p_1";
	
	whenHtml.push("<li idx='" + liIdx + "'>");
	
	if(liIdx > 1){
		var logic_group_op_id = whenPrefix + "_logic_group_op"; 
		whenHtml.push(LogicOperatorTemplate.replaceAll("#ID#", logic_group_op_id));
	}
	whenHtml.push("(");
	whenHtml.push(FactorSelectTemplate.replaceAll("#ID#", left_p_id));
	whenHtml.push(RelationOperatorTemplate.replaceAll("#ID#", rel_op_id));
	whenHtml.push(TypeControlTemplate.replaceAll("#ID#", type_ctl_id));
	whenHtml.push(FactorInputTemplate.replaceAll("#ID#", right_p_id));
	
	whenHtml.push("<a href='javascript:void(0);' class='linkbutton' data-options=\"plain:true,iconCls:'icon-add'\" name='AddWhenOperatorCfg'></a>");
	whenHtml.push(")</li>");
	$(".when_cfg_ul").append(whenHtml.join(""));
	initCurrentPageUI();
	initAddWhenOperatorCfg();
}

function initAddWhenOperatorCfg(){
	$("a[name='AddWhenOperatorCfg']").each(function(){
		$(this).unbind("click");
		$(this).click(function(event){
			var addOpObj = $(event.currentTarget);
			var lastTypeCtlObj = $(addOpObj).parents("li").find("[function='TypeControl']:last");
			var lastTypeCtlId = $(lastTypeCtlObj).attr("id");//when_1_type_ctl_1
			var nameArray = lastTypeCtlId.split("_");
			var whenPrefix = nameArray[0] + "_" + nameArray[1];
			var nextOpIdx = parseInt(nameArray[4]) + 1;
			
			var logic_op_id = whenPrefix + "_logic_op_" +  nextOpIdx;
			var left_p_id = whenPrefix + "_left_p_" +  nextOpIdx;
			var rel_op_id = whenPrefix + "_rel_op_" +  nextOpIdx;
			var type_ctl_id = whenPrefix + "_type_ctl_" +  nextOpIdx;
			var right_p_id = whenPrefix + "_right_p_" +  nextOpIdx;
			
			var whenHtml = [];
			whenHtml.push(LogicOperatorTemplate.replaceAll("#ID#", logic_op_id));
			whenHtml.push(FactorSelectTemplate.replaceAll("#ID#", left_p_id));
			whenHtml.push(RelationOperatorTemplate.replaceAll("#ID#", rel_op_id));
			whenHtml.push(TypeControlTemplate.replaceAll("#ID#", type_ctl_id));
			whenHtml.push(FactorInputTemplate.replaceAll("#ID#", right_p_id));
			
			$(addOpObj).before(whenHtml.join(""));
			
			if(nextOpIdx == 2){
				var delOpObj = "<a href='javascript:void(0);' class='linkbutton' data-options=\"plain:true,iconCls:'icon-delete'\" name='DelWhenOperatorCfg'></a>";
				$(addOpObj).after(delOpObj);
			}
			initCurrentPageUI();
			initDelWhenOperatorCfg();
		});
	});
}

function initDelWhenOperatorCfg(){
	$("a[name='DelWhenOperatorCfg']").each(function(){
		$(this).unbind("click");
		$(this).click(function(event){
			var delOpObj = $(event.currentTarget);
			var lastTypeCtlObj = $(delOpObj).parents("li").find("[function='TypeControl']:last");
			var lastTypeCtlId = $(lastTypeCtlObj).attr("id");//when_1_type_ctl_1
			var nameArray = lastTypeCtlId.split("_");
			var whenPrefix = nameArray[0] + "_" + nameArray[1];
			var lastOpIdx = parseInt(nameArray[4]);
			
			var logic_op_id = whenPrefix + "_logic_op_" +  lastOpIdx;
			var left_p_id = whenPrefix + "_left_p_" +  lastOpIdx;
			var rel_op_id = whenPrefix + "_rel_op_" +  lastOpIdx;
			var type_ctl_id = whenPrefix + "_type_ctl_" +  lastOpIdx;
			var right_p_id = whenPrefix + "_right_p_" +  lastOpIdx;
			
			if(lastOpIdx == 2){
				$(delOpObj).remove();
			}
			$("#" + logic_op_id).remove();
			$("#" + left_p_id).remove();
			$("#" + rel_op_id).remove();
			$("#" + type_ctl_id).remove();
			$("#" + right_p_id).remove();
		});
	});
}

/**
 * 删除条件操作
 * @memberOf {TypeName} 
 */
function deleteGroupWhenCfg(){
	$(".when_cfg_ul").find("li:last").remove();
}


/** 增加赋值操作 */
function addThenAssign(){
	var liIdx = 1;
	if($(".then_cfg_ul").find("li").length > 0){
		var lastLiIdx = $(".then_cfg_ul").find("li:last").attr("idx");
		liIdx = parseInt(lastLiIdx) + 1;
	}else{
		liIdx = 1;
	}
	var thenPrefix = "then_" + liIdx
	var thenHtml = [];
	var left_p_id = thenPrefix + "_left_p";
	var assign_op_id = thenPrefix + "_assign";
	var type_ctl_id = thenPrefix + "_type_ctl_" + 1;
	var right_p_id = thenPrefix + "_right_p_" + 1;
	
	thenHtml.push("<li idx='" + liIdx + "' type='assign'>");
	thenHtml.push("<a href='javascript:void(0);' class='linkbutton' data-options=\"plain:true,iconCls:'icon-no'\" name='DelThen'></a>");
	thenHtml.push(FactorSelectTemplate.replaceAll("#ID#", left_p_id));
	thenHtml.push(AssignOperatorTemplate.replaceAll("#ID#", assign_op_id));
	thenHtml.push(TypeControlTemplate.replaceAll("#ID#", type_ctl_id));
	thenHtml.push(FactorInputTemplate.replaceAll("#ID#", right_p_id));
	thenHtml.push("<a href='javascript:void(0);' class='linkbutton' data-options=\"plain:true,iconCls:'icon-add'\" name='AddThenOperatorCfg'></a>");
	thenHtml.push("</li>");
	$(".then_cfg_ul").append(thenHtml.join(""));
	initCurrentPageUI();
	initDelThenEvent();
	initAddThenOperatorCfgEvent();
}

/**
 * 删除动作操作单元
 * @memberOf {TypeName} 
 */
function initDelThenEvent(){
	$("a[name='DelThen']").each(function(){
		$(this).unbind("click");
		$(this).click(function(event){
			var delThenObj = $(event.currentTarget);
			$(delThenObj).parents("li").remove();
		});
	});
}

/** 增加赋值操作中增加或删除操作元素操作 */
function initAddThenOperatorCfgEvent(){
	$("a[name='AddThenOperatorCfg']").each(function(){
		$(this).unbind("click");
		$(this).click(function(event){
			var addOpObj = $(event.currentTarget);
			var lastTypeCtlObj = $(addOpObj).parents("li").find("[function='TypeControl']:last");
			var lastTypeCtlId = $(lastTypeCtlObj).attr("id");//then_1_type_ctl_1
			var nameArray = lastTypeCtlId.split("_");
			var thenPrefix = nameArray[0] + "_" + nameArray[1];
			var nextOpIdx = parseInt(nameArray[4]) + 1;
			
			if(nextOpIdx <= 2){
				var delOpObj = "<a href='javascript:void(0);' class='linkbutton' data-options=\"plain:true,iconCls:'icon-delete'\" name='DelThenOperatorCfg'></a>";
				$(addOpObj).after(delOpObj);
			}		
			var newOpHtml = [];
			
			var right_op_id = thenPrefix + "_right_op_" + nextOpIdx;
			var type_ctl_id = thenPrefix + "_type_ctl_" + nextOpIdx;
			var right_p_id = thenPrefix + "_right_p_" + nextOpIdx;
			
			newOpHtml.push(ArithOperatorTemplate.replaceAll("#ID#", right_op_id));
			newOpHtml.push(TypeControlTemplate.replaceAll("#ID#", type_ctl_id));
			newOpHtml.push(FactorInputTemplate.replaceAll("#ID#", right_p_id));
			$(addOpObj).before(newOpHtml.join(""));
			initCurrentPageUI();
			initDelThenOperatorCfgEvent();
		});
	});
}

function initDelThenOperatorCfgEvent(){
	$("a[name='DelThenOperatorCfg']").each(function(){
		$(this).unbind("click");
		$(this).click(function(event){
			var delOpObj = $(event.currentTarget);
			var lastTypeCtlObj = $(delOpObj).parents("li").find("[function='TypeControl']:last");
			var lastTypeCtlId = $(lastTypeCtlObj).attr("id");//then_1_type_ctl_2
			var nameArray = lastTypeCtlId.split("_");
			var thenPrefix = nameArray[0] + "_" + nameArray[1];
			var lastOpIdx = parseInt(nameArray[4]);
			
			if(lastOpIdx == 2){//当只有两个操作数时，优先移出“删除操作单元”
				$(delOpObj).remove();
			}
			var last_right_op_id = thenPrefix + "_right_op_" + lastOpIdx;
			var last_type_ctl_id = thenPrefix + "_type_ctl_" + lastOpIdx;
			var last_right_p_id = thenPrefix + "_right_p_" + lastOpIdx;
			$("#" + last_right_op_id).remove();
			$("#" + last_type_ctl_id).remove();
			$("#" + last_right_p_id).remove();
		});
	});
}


/* 增加函数操作 */
function addThenFunction(){
	var liIdx = 1;
	if($(".then_cfg_ul").find("li").length > 0){
		var lastLiIdx = $(".then_cfg_ul").find("li:last").attr("idx");
		liIdx = parseInt(lastLiIdx) + 1;
	}else{
		liIdx = 1;
	}
	var thenPrefix = "then_" + liIdx;
	var thenHtml = [];
	thenHtml.push("<li idx='" + liIdx + "' type='function'>");
	thenHtml.push("<a href='javascript:void(0);' class='linkbutton' style='float:left' data-options=\"plain:true,iconCls:'icon-no'\" name='DelThen'></a>");
	thenHtml.push(FunctionSelectTemplate.replaceAll("#ID#", thenPrefix + "_function"));
	thenHtml.push("</li>");
	$(".then_cfg_ul").append(thenHtml.join(""));
	initCurrentPageUI();
	initDelThenEvent();
}

function checkForm(){
	var flag = false;
	$("input").each(function(){
		$input = $(this);
		var selectBox = $input.hasClass("selectBox") || false;
		if(selectBox){
			var key = $input.attr("key");
			if(key.length == 0){
				flag = true;
			}
		}else{
			var tvalue = $input.val();
			if(tvalue.length == 0){
				flag = true;
			}
		}
	});
	return flag;
}

function buildRuleSemantics(){
	var ruleSemantics = [];
	ruleSemantics.push("<pre style='line-height:21px;'>");
	ruleSemantics.push("规则 ");
	ruleSemantics.push($("input[name=ruleZhName]").val() + "<br/>");
	ruleSemantics.push("优先级 ");
	ruleSemantics.push($("input[name=ruleSalience]").val() + "<br/>");
	ruleSemantics.push("如果<br/>");
	var when_cfg_ul_len = $(".when_cfg_ul").find("li").length;
	if(when_cfg_ul_len < 1){
		ruleSemantics.push("	TRUE");
	}else{
		ruleSemantics.push("	");
		for(var idx = 1; idx <= when_cfg_ul_len; idx++){//li的idx属性从1开始
			var liObj = $(".when_cfg_ul").find("li[idx='" + idx + "']");
			var whenPrefix = "when_" + $(liObj).attr("idx");
			$("input", liObj).each(function(idx){
				$input = $(this);
				var tagId = $input.attr("id");
				if(idx == 0){
					if(tagId.indexOf("group_op", 0) > 0){
						ruleSemantics.push(" " + $input.val() + "(");
					}else{
						ruleSemantics.push("(" + $input.val());
					}
				}else{
					ruleSemantics.push(" " + $input.val());
				}
			});
			ruleSemantics.push(")")
		}
	}
	
	ruleSemantics.push("<br/>那么<br/>");
	
	var then_cfg_ul_len = $(".then_cfg_ul").find("li").length;
	
	for(var idx = 0; idx < then_cfg_ul_len; idx++){
		var liObj = $(".then_cfg_ul").find("li").eq(idx);
		ruleSemantics.push("	");
		$("input", liObj).each(function(idx){
			$input = $(this);
			ruleSemantics.push($input.val());
		});
		ruleSemantics.push(";");
	}
	ruleSemantics.push("</pre>");
	return ruleSemantics.join("");
}

function getElementVal(element){
	if($(element).hasClass("selectBox")){
		return $(element).attr("key");
	}else{
		return $(element).val();
	}
}

function buildRuleCode(){
	var ruleCode = "";
	
	ruleCode += "if \n";

	var when_cfg_ul_len = $(".when_cfg_ul").find("li").length;
	
	if(when_cfg_ul_len < 1){
		ruleCode += " true\n";
	}else{
		for(var idx = 1; idx <= when_cfg_ul_len; idx++){//li的idx属性从1开始
			var liObj = $(".when_cfg_ul").find("li[idx='" + idx + "']");
			$("input", liObj).each(function(idx){
				$input = $(this);
				var tagId = $input.attr("id");
				if(idx == 0){
					if(tagId.indexOf("group_op", 0) > 0){
						ruleCode += " " + getElementVal($input) + "(";
					}else{
						ruleCode += "(" + getElementVal($input);
					}
				}else{
					if(tagId.indexOf("right_p",0) >= 0){
						if($input.hasClass("selectBox")){
							ruleCode += getElementVal($input);
						}else{
							var left_p_id = tagId.replace("right_p", "left_p");
							var dataType = $("#" + left_p_id).attr("dataType");
							if("STRING" == dataType){
								ruleCode += "\"" + getElementVal($input) + "\"";
							}else if("LONG" == dataType || "INTEGER" == dataType || "DOUBLE" == dataType){
								ruleCode += getElementVal($input);
							}else if("DATE" == dataType){
								ruleCode += "[" + getElementVal($input) + "]";
							}else{
								alert("参数类型错误");
								return;
							}
						}
					}else{
						ruleCode += getElementVal($input);
					}
				}
			});
			ruleCode += ")\n";
		}
	}
	
	ruleCode += "then\n"
	var then_cfg_ul_len = $(".then_cfg_ul").find("li").length;
	for(var idx = 0; idx < then_cfg_ul_len; idx++){
		var liObj = $(".then_cfg_ul").find("li").eq(idx);
		var left_p_id = "then_" + $(liObj).attr("idx") + "_left_p";
		var dataType = $("#" + left_p_id).attr("dataType");
		var type = $(liObj).attr("type");
		if("assign" == type){//赋值操作
			$("input", liObj).each(function(idx){
				var $input = $(this);
				var tagId = $input.attr("id");
				if(tagId.indexOf("right_p", 0) > 0){
					if($input.hasClass("selectBox")){
						ruleCode += getElementVal($input);
					}else{
						if("STRING" == dataType){
							ruleCode += "\"" + getElementVal($input) + "\"";
						}else if("LONG" == dataType || "INTEGER" == dataType || "DOUBLE" == dataType){
							ruleCode += getElementVal($input);
						}else if("DATE" == dataType){
							ruleCode += "[" + getElementVal($input) + "]";
						}else{
							alert("参数类型错误");
							return;
						}
					}
				}else{
					ruleCode += getElementVal($input);
				}
			});
			ruleCode += ";\n";
		}else{//函数操作
			$("input", liObj).each(function(idx){
				var $input = $(this);
				ruleCode += getElementVal($input);
			});
			ruleCode += ";\n";
		}
	}
	ruleCode += "endif"
	return ruleCode;
}
