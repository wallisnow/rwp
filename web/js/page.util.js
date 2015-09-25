/**
 * 页面验证方法
 */
function formCheck(formId){
	if(typeof(formId) == "undefined"){
		formId = $(document.forms[0]).attr("id");
	}
	if(typeof(formId) == "undefined"){
		alert("请检查FormId是否存在");
		return false;
	}
	var validator = new ValidatorWrapper($("#" + formId));
	if(!validator.validate()){
		return false;
	}
	return true;
}

/**
 * 取页面选中项主键值数组
 * @returns {Array}
 */
function findPKS(){
	var PKS = [];
	$("input[name=PK]").each(function(){
		$chbox = $(this);
		if($chbox.attr("checked")){
			pkvalue = $chbox.attr("value");
			PKS.push(pkvalue);
		}
	});
	return PKS;
}

/**
 * 取页面选中项主键值数组(在无checkbox的时候考虑Table中选中行)
 * @memberOf {TypeName} 
 * @return {TypeName} 
 */
function findPKS2(){
	var PKS = [];
	$("input[name=PK]").each(function(){
		$chbox = $(this);
		if($chbox.attr("checked")){
			pkvalue = $chbox.attr("value");
			PKS.push(pkvalue);
		}
	});
	if(PKS.length > 0){
		return PKS;
	}
	$("tbody tr.selected").each(function(){
		$tr = $(this);
		var PK = $tr.attr("PK");
		if(PK.length > 0){
			PKS.push(PK);
		}
	});
	return PKS;
}

function submitForm(formId){
	if(formCheck(formId)){
		$("#" + formId).submit();
	}
}

function disableForm(){
	$("input, checkbox, ratio, textarea, select").each(function(){
		var $this = $(this);
		$this.addClass("readonly");
	});
}
function enableForm(){
	$("input, checkbox, ratio, textarea, select").each(function(){
		var $this = $(this);
		$this.removeClass("readonly");
	});
}

/**
 * disableAwayls 一直不可以编辑
 * enableOnlyNew 只有新增时才可编辑
 */
function contentFormControl(){
	var isNew = $("input[name=ISNEW]").val();
	var isEdit = $("input[name=ISEDIT]").val();

	if("TRUE" != isEdit){//页面不可编辑时全部不可编辑
		$("input, checkbox, ratio, textarea, select").each(function(){
			var $this = $(this);
			$this.addClass("readonly");
		});
	}else{//页面可以编辑时,首先调整全部可编辑
		$("input, checkbox, ratio, textarea, select").each(function(){
			var $this = $(this);
			var disableAwayls = $this.hasClass("disableAwayls") || false;
			var enableOnlyNew = $this.hasClass("enableOnlyNew") || false;
			if(disableAwayls){
				$this.addClass("readonly");
			}
			if(enableOnlyNew && "TRUE" != isNew){
				$this.addClass("readonly");
			}
		});
	}
	$("input.readonly, checkbox.readonly, textarea.readonly, select.readonly").each(function(){
		var $this = $(this);
		$this.attr("readonly", "true");
		if($this.is("select")){
			disableSelect($this.attr("id"));
		}
	});
}

/**
 * Select标签增加readonly样式,不可用
 * @param {Object} selectId
 */
function disableSelect(selectId){
	if(typeof(selectId) != "undefined"){
		$("#combox_" + selectId).find("a").addClass("readonly");
	}else{
		$("div.select").find("a").addClass("readonly");
	}
}

/**
 * Select标签移除readonly样式,可用
 * @param {Object} selectId
 */
function enableSelect(selectId){
	if(typeof(selectId) != "undefined"){
		$("#combox_" + selectId).find("a").removeClass("readonly");
	}else{
		$("div.select").find("a").removeClass("readonly");
	}
}


/**
 * 在指定URL后面加上参数
 * @param url
 * @param name
 * @param value
 * @return
 */
function addParam(url, name, value) {
	if (url != null && name != null) {
		if (url.indexOf('?') == -1) {
			url = url + '?' + name + '=' + value;
		}else {
			url = url + '&' + name + '=' + value;
		}
	}
	return url;
}
/**
 * 增加操作
 * @return
 */
function doAdd() {
	window.location = ctx + cmdAdd;
}

/**
 * 返回操作
 * @param formId
 * @return
 */
function doReturn() {
	window.location = ctx + cmdReturn;
}

/**
 * 导入操作
 * @return
 */
function doLoad() {
	window.location = ctx + cmdLoad;
}

/**
 * 在list页面进行编辑操作
 * @param formId
 * @return
 */
function doEditList(formId) {
	var form;
	if(typeof(formId) == "undefined"){
		form = document.forms[0];
	}else{
		form = document.forms[formId];
	}
	if(typeof(form) == "undefined"){
		alert("请检查页面定义是否有误");
		return;
	}
	var PKS = findPKS();
	if(PKS.length < 1){
		alert("请选择编辑记录");
		return;
	}else if(PKS.length > 1){
		alert("请确认一次只能编辑一条记录");
		return;
	}
	var url = addParam(ctx + cmdEdit, "PK", PKS[0]);
	window.location = url;
}

/**
 * 在content页面进行编辑操作
 * @param formId
 * @return
 */
function doEditContent(formId) {
	var url = ctx + cmdEdit;
	var form;
	if(typeof(formId) == "undefined"){
		form = document.forms[0];
	}else{
		form = document.forms[formId];
	}
	if(!form){
		alert("请检查页面定义是否有误");
		return;
	}
	enableForm();
	form.action = url;
	form.submit();
}
/**
 * 保存记录信息
 * @param formId
 * @return
 */
function doSave(formId) {//save操作必须需要formId,因为涉及页面验证
	var url = ctx + cmdSave;
	if(typeof(formId) == "undefined"){
		formId = $(document.forms[0]).attr("id");
	}
	if(typeof(formId) == "undefined"){
		alert("请确认formId是否存在");
		return;
	}
	if(formCheck(formId)) {
		enableForm();
		var form = document.forms[formId];;
		form.action = url;
		form.submit();
	}
}

/**
 * 删除选中记录
 * @param formId
 * @return
 */
function doDelete(formId) {
	if(typeof(formId) == "undefined"){
		formId = $(document.forms[0]).attr("id");
	}
	if(typeof(formId) == "undefined"){
		alert("请确认formId是否存在");
		return;
	}
	var PKS = findPKS();
	if(PKS.length < 1){
		alert("请选择删除记录");
		return;
	}
	if(confirm("是否真的删除?")){
		var url = ctx + cmdDelete;
		var form = document.forms[formId];
		form.action = url;
		form.submit();
	}
}

/**
 * 逻辑删除选中记录
 * @param formId
 * @return
 */
function doDeleteLogic(formId) {
	if(typeof(formId) == "undefined"){
		formId = $(document.forms[0]).attr("id");
	}
	if(typeof(formId) == "undefined"){
		alert("请确认formId是否存在");
		return;
	}
	var PKS = findPKS();
	if(PKS.length < 1){
		alert("请选择删除记录");
		return;
	}
	art.dialog.confirm('你确定要删除吗？', function () {
			var url = ctx + cmdDelete;
			var form = document.forms[formId];
			form.action = url;
			form.submit();
		}, function () {
	});
}

/**
 * 页面查询
 * @param formId
 * @return
 */
function doQuery(formId){
	if(typeof(formId) == "undefined"){
		formId = $(document.forms[0]).attr("id");
	}
	if(typeof(formId) == "undefined"){
		alert("请确认formId是否存在");
		return;
	}
	if(formCheck(formId)) {
		$("form").find("#pageNo").val("1");
		var form = document.forms[formId];
		form.submit();
	}
}

function doReset() {
	$(":text").attr("value","");
	$("select").attr("value","");
}
/**
 * 选择全部
 * @return
 */
function checkAll(){
	if($("input[name=chooseAll]").attr("checked")){
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


/**
 * 提交记录
 * @param formId
 * @return
 */
function doSubmit(formId) {
	var url = ctx + cmdSubmit;
	var formId;
	if(typeof(formId) == "undefined"){
		formId = $(document.forms[0]).attr("id");
	}
	if(typeof(formId) == "undefined"){
		alert("请确认formId是否存在");
		return;
	}
	if(formCheck(formId)) {
		enableForm();
		var form = document.forms[formId];
		form.action = url;
		form.submit();
	}
}

/**
 * 批量审批选中记录
 * @param formId
 * @return
 */
function doSubmit(formId) {
	if(typeof(formId) == "undefined"){
		formId = $(document.forms[0]).attr("id");
	}
	if(typeof(formId) == "undefined"){
		alert("请确认formId是否存在");
		return;
	}
	var delCount = 0;
	$("#" + formId).find("input[name=PK]").each(function(){
		$chbox = $(this);
		if($chbox.attr("checked")){
			delCount += 1;
		}
	});
	if(delCount < 1){
		alert("请选择审批记录");
	}else{
			if(confirm("是否全部审批通过？")){
				var url = ctx + cmdSubmit;
				var form = document.forms[formId];
				form.action = url;
				form.submit();
			}
			else
				return;
	}
}

function doExcel(){
	if(typeof(formId) == "undefined"){
		formId = $(document.forms[0]).attr("id");
	}
	if(typeof(formId) == "undefined"){
		alert("请确认formId是否存在");
		return;
	}
	if(formCheck(formId)) {
		var url = ctx + cmdExcel;
		var form = document.forms[formId];
		var oldurl = form.action;
		form.action = url;
		form.submit();
		form.action = oldurl;
	}
}

/**
 * 密码安全检查
 * 密码长度必须为六位
 * 密码中必须包括字母、特殊字段和数据
 */
var numberPwd = /\d+/;
var letterPwd = /[A-Za-z]+/;
var specialLetterPwd = /[!@#\$%\^\&\*_\.]+/;
var pwdChkMsg = "1、密码长度必须为八位字符;<br>2、密码中必须包含数字1-9;<br/>3、密码中必须包含字母A-Z或a-z;<br>4、密码中必须包含特殊字符!@#$%^&*-.;";

function checkPwd(pwd){
	if(pwd.length != 8){
		return false;
	}else if(!numberPwd.test(pwd)){
		return false;
	}else if(!letterPwd.test(pwd)){
		return false;
	}else if(!specialLetterPwd.test(pwd)){
		return false;
	}else{
		return true;
	}	
}

function saveQueryCache(value){
	hideChoseDiv();
	var queryCache = art.dialog.data("queryCache");
	if(typeof(queryCache) == "undefined"){
		queryCache = new Stack(10);
	}
	queryCache.push(value);
	art.dialog.data("queryCache", queryCache);
}

function getQueryCache(){
	var queryCache = art.dialog.data("queryCache");
	if(typeof(queryCache) != "undefined"){
		try{
			return queryCache.values();
		}catch(e){
			queryCache = new Stack(10);
			art.dialog.data("queryCache", queryCache);
		}
	}
	return new Array();
}

function createChoseDiv(target){
	var top = target.offset().top;
	var left = target.offset().left;
	var height = target.height();
	var width = target.width();
	
	if($("#choseDiv").size() < 1){
		$("<div id=\"choseDiv\" class=\"choseDiv\"></div>").appendTo("body");
		$("<iframe id=\"choseIfm\" src=\"javascript:false;\" scrolling=\"no\" frameborder=\"0\" style=\"position:absolute; top:0px; left:0px; display:none;\"></iframe>").appendTo("body");
	}
	var divTop = top + height + 2;
	var divWidth = width * 2;
	$("#choseDiv, #choseIfm").css({"top" : divTop + "px", "left" : left + "px"});
	$("#choseDiv").html("");
	
	var tableHtml = "<ul>";
	
	var values = getQueryCache();
	
	for(var index = 0; index < values.length && index < 4; index ++){
		tableHtml += "<li>";
		tableHtml += values[index];
		tableHtml += "</li>";
	}	
	tableHtml += "</ul>";
	
	$(tableHtml).appendTo("#choseDiv");
	
	$("#choseDiv>ul li").each(function(){
		$this = $(this);
		$this.bind("click", function(){
			$li = $(this);
			$(target).val($li.text());
			hideChoseDiv();
		});
	});
	if(values.length >= 1){
		showChoseDiv();
	}else{
		hideChoseDiv();
	}
}
function showChoseDiv(){
	$("#choseDiv").show();
	$("#choseIfm").css({"height" : $("#choseDiv").height(), "width" : $("#choseDiv").width(), "display" : "block"});
}

function hideChoseDiv(){
	$("#choseDiv").hide();
	$("#choseIfm").css("display", "none");
}

