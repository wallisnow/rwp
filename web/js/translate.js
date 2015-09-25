
/**
 * 页面翻译JS方法(同步方法)
 * @param definition
 * @param value
 * @returns
 */
function nameJson(definition, value){
	var params = {
		definition : definition,	
		value : value
	};
	var url = ctx + "/system/translate/nameJson.action";
	return ajaxJsonSyn(url, params);
}

/**
 * 翻译JS方法(异步方法)
 * @param definition
 * @param value
 * @param nameId
 */
function code2name(definition, value, nameId){
	var params = {
		definition : definition,	
		value : value
	};
	$.getJSON(ctx + "/system/translate/nameJson.action", params, function(json){
		if("success" == json.status){
			$("#" + nameId).val(json.message);
		}
	});
}

/**
 * 页面对象方法
 * @param voName
 * @param keys
 * @param values
 * @returns
 */
function voJson(voName, keys, values){
	var params = {
		voName : voName,
		keys : keys,
		values : values
	};
	var url = ctx + "/system/translate/voJson.action";
	return ajaxJsonSyn(url, params);
}

/**
 * 页面列表方法
 * @param voName
 * @param keys
 * @param values
 * @returns
 */
function listJson(voName, keys, values){
	var params = {
		voName : voName,
		keys : keys,
		values : values
	};
	var url = ctx + "/system/translate/listJson.action";
	return ajaxJsonSyn(url, params);
}

/**
 * Ajax同步方法调用
 * @param url
 * @param params
 * @returns
 */
function ajaxJsonSyn(url, params){
	var result;
	$.ajax({
		type : "POST",
		url : url,
		data : params,
		dataType : 'json',
		async: false,
		success : function(data){result = data} 
	});
	return result;
}

