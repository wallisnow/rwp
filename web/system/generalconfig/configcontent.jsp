<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title><s:text name="title" /></title>
<%@ include file="/common/header.jsp"%>
<script type="text/javascript">
	$(function() {
		initPageEnv("content");
		var status = "${param.STATUS}";
		if(status==="EIDT"){
			generateConfigTable();
		}
	});
	
	function getTargetTable(sqlst){
		
		if($.isEmptyObject(sqlst) || isEmpty(sqlst)){
			return;
		}
		
		sql=sqlst.toUpperCase();
		var segments = sql.split(/[\s\,\;]+/);
		if(segments.indexOf("WHERE") === -1){
			var targetTable = getCertainSegments(segments, 'FROM', null);
		}else{
			var targetTable = getCertainSegments(segments, 'FROM', 'FROM');
		}
		return targetTable[0];
	}
	
	function generateConfigTable() {
		
		var sql = $("#sqlStatement").val();
		
		if ($.isEmptyObject(sql) || isEmpty(sql)) {
			alert("sql statement should not be empty");
			return;
		}
		
		if ($.isEmptyObject($('#dbId')) || isEmpty($('#dbId'))) {
			alert("datasource should not be empty");
			return;
		}
		
		//查询结果匹配
		var segments = sql.split(/[\s\,\;]+/);
		var results = getCertainSegments(segments, 'select', 'from');
		
		var targetTable =getTargetTable(sql);
		
		var url = ctx + "/system/generalconfig/checkFields.action";	
		url = addParam(url, "dbId", $('#dbId').val());
		url = addParam(url, 'currentTable', targetTable);
		 
		if (results.toString().match(/^\*$/)){
			url = addParam(url, 'fieldsArray', "FULLQUERY");
		}else{
			url = addParam(url, 'fieldsArray', results);
		}

		//var datas = [{'COLUMN_NAME':'aaa'},{'COLUMN_NAME':'bbb'},{'COLUMN_NAME':'ccc'}];
		$.getJSON(url, JSON.stringify(results), function(result) {
			//$("#result").val(result.message);
			var datas = result;
			var $datas = $('#tmpl-tbody-tr').tmpl(datas);
			$('#tbody').html($datas);
		});
	}

	function doExcutesql() {
		var formObj = form2Obj();
		formObj["dt"] = new Date() + Math.random();
		var url = ctx + "/system/sqlmanage/excutesql.action";
		url = addParam(url, "dbId", $('#dbId').val());
		url = addParam(url, "sql", encodeURI(encodeURI($('#sql').val())));
		alert(url);
		$.getJSON(url, null, function(result) {
			$("#result").val(result.message);
		});
	}


	
	function form2Obj() {
		var formObj = {};
		$("form input").each(function() {
			$this = $(this);
			formObj[$this.attr("name")] = $this.val();
		});
		$("form textarea").each(function() {
			$this = $(this);
			formObj[$this.attr("name")] = $this.val();
		});
		$("form select").each(function() {
			$this = $(this);
			formObj[$this.attr("name")] = $this.val();
		});
		return formObj;
	}

	function doReturnRuleDatas() {
		var url = ctx
				+ "/rulemgt/ruleset/listRule.action?rulesetPK=${paramsMap.rulesetPK}";
		window.location.href = url;
	}

	function CreateResultTable(rowCount, cellCount, fields) {

		if ($.isEmptyObject(fields)) {
			alert(" please check the query results in your sql setatment");
			return;
		}

		$("#resultTable").empty();
		var table = $("<table border=\"1\">");
		//var table =$("#rTable");
		table.appendTo($("#resultTable"));

		var titleTr = $("<tr></tr>");

		var thField = $("<th>字段</th>");
		var thName = $("<th>字段名</th>");
		var thSelectKey = $("<th>是否为用于查询</th>");
		var thHidden = $("<th>是否显示</th>");
		var thRename = $("<th>字段重命名</th>");

		titleTr.appendTo(table);

		thField.appendTo(titleTr);
		thName.appendTo(titleTr);
		thSelectKey.appendTo(titleTr);
		thHidden.appendTo(titleTr);
		thRename.appendTo(titleTr);

		for (var i = 0; i < rowCount; i++) {
			var tr = $("<tr></tr>");
			tr.appendTo(table);

			var fieldName = fields[i];
			//alert(fieldName);
			var fieldTd = $("<td>" + fieldName + "</td>");

			var fieldCnName = fieldName.concat("_cnName_" + i);
			var fieldSelectKey = fieldName.concat("_selectKey_" + i);
			var fieldHidden = fieldName.concat("_hidden_" + i);
			var fieldRename = fieldName.concat("_reName_" + i);

			//alert(fieldRename);

			fieldTd.appendTo(tr);
			for (var j = 1; j < cellCount; j++) {
				//var renameArea=$("<input id='"+fieldName+"' type="textarea" value="" />");
				var td = $("<td></td>");
				if (j === 1) {
					var cnName = $('<input>').attr({
						id : fieldCnName,
						type : 'text',
						value : ''
					});

					cnName.appendTo(td);
				}

				if (j === 2) {
					var selectKey = $('<select>').attr({
						id : fieldSelectKey
					});
					var op = [ '是', '否' ];
					for ( var x in op) {
						selectKey.append($('<option/>').html(op[x]));
					}
					selectKey.appendTo(td);
				}

				if (j === 3) {
					var hidden = $('<select>').attr({
						id : fieldHidden
					});
					var op = [ '是', '否' ];
					for ( var y in op) {
						hidden.append($('<option/>').html(op[y]));
					}
					hidden.appendTo(td);
				}

				if (j === 4) {
					var renameArea = $('<input>').attr({
						id : fieldRename,
						type : 'text',
						value : ''
					});

					renameArea.appendTo(td);
				}

				td.appendTo(tr);
			}
		}
		//trend.appendTo(table);
		$("#resultTable").append("</table>");
	}

	//创建 条件 表格
	//TODO
	function CreateConditionTable(rowCount, cellCount, conditions) {

		if ($.isEmptyObject(conditions)) {
			alert(" please check the query conditions in your sql setatment");
			return;
		}

		$("#condiTable").empty();
		var table = $("<table border=\"1\">");

		table.appendTo($("#condiTable"));
		var titleTr = $("<tr></tr>");
		var thRename = $("<th>约束条件</th>");

		titleTr.appendTo(table);
		thRename.appendTo(titleTr);

		for (var i = 0; i < rowCount; i++) {
			var tr = $("<tr></tr>");
			tr.appendTo(table);

			var fieldTd = $("<td>" + conditions[i] + "</td>");

			//alert(fieldRename);
			fieldTd.appendTo(tr);
		}
		//trend.appendTo(table);
		$("#resultTable").append("</table>");
	}

	//拆分sql语句
	function parseSql() {

		var sql = $("#sqlStatement").val();
		//checkIsSQL();

		if ($.isEmptyObject(sql) || isEmpty(sql)) {
			alert("sql statement should not be empty");
			return;
		}

		var segments = sql.split(/[\s\,\;]+/);
		//alert(segments);
		if (!(segments[0].match('select'))) {
			alert('please input a select statement');
		}
		//var re= /^(select)-(\d{3,8})$-/
		var results = getCertainSegments(segments, 'select', 'from');

		//TODO
		//如果为 查询条件为 *
		//alert(results.toString());
		if (results.toString().match(/^\*$/)) {
			//alert("####");	
			$.getJSON(ctx + "/system/generalconfig/checkFields.action", null,
					function(result) {
						var allFiledsName = [];
						//alert(JSON.stringify(result));
						$.each(result, function(key, value) {
							var column = value.COLUMN_NAME;
							allFiledsName.push(column);
						});
						//var fields = JSON.stringify(result);
						CreateResultTable(allFiledsName.length, 5,
								allFiledsName);
					});
		} else {
			//创建结果表格
			CreateResultTable(results.length, 5, results);
		}
		
		var conditions = getCertainSegments(segments, 'where', null);

		//返回where后条件数组, 解析sql的条件
		//var conditionGroup = parseCondition(conditions);
		//CreateConditionTable(conditionGroup.length, 1, conditionGroup);
	}

	//截取部分数组
	function getCertainSegments(array, start, end) {

		var startIndex = 0;
		var endIndex = 0;

		var seg = [];

		if (!(array.length > 0)) {
			alert('array is null');
			return;
		}

		if (array.indexOf(start) < 0) {
			return;
		}

		if (!(isEmpty(start)) && !(isEmpty(end))) {

			for (var i = 0; i < array.length; i++) {
				if (array[i].match(start)) {
					startIndex = i;
				}

				if (array[i].match(end)) {
					endIndex = i;
				}
			}

			startIndex += 1;
			endIndex -= 1;

		} else if (isEmpty(end)) {

			for (var i = 0; i < array.length; i++) {
				if (array[i].match(start)) {
					startIndex = i;
				}
			}

			startIndex += 1;
			endIndex = array.length - 1;
		} else if (isEmpty(start)) {

			for (var i = 0; i < array.length; i++) {
				if (array[i].match(end)) {
					endIndex = i;
				}
			}

			startIndex = 0;
			endIndex -= 1;
		}

		for (var i = startIndex; i <= endIndex; i++) {
			seg.push(array[i]);
		}

		return seg;
	}

	//可变参数
	function vriableParams() {
		var params = "";
		for (var i = 0; i < arguments.length; i++) {
			params = params + " " + arguments[i];
		}
		return params;
	}

	//截取条件
	function parseCondition(conditions) {

		if ($.isEmptyObject(conditions)) {
			//alert(" please check the query conditions in your sql setatment");
			return;
		}

		//alert(conditions);
		//sql 语句全变为大写
		$.each(conditions, function(index, item) {
			conditions[index] = item.toUpperCase();
		});

		var condiGroup = [];
		//var conditionsCounter = 1;
		//var conditionIndex = 0;

		var linkerIndex = 0;

		var linker = containsLogicLinker(conditions);

		if (isEmpty(linker)) {
			for (var i = 0; i < conditions.length; i++) {
				if (i === 0) {
					condiGroup[0] = "";
					condiGroup[0].push(condition[i]);
				} else {
					condiGroup[0].push(condition[i]);
				}
			}
			return condiGroup;
		} else {
			//conditionsCounter+=1;
			linkerIndex = conditions.indexOf(linker);

			condiGroup[0] = conditions.slice(0, linkerIndex);
			condiGroup[1] = conditions
					.slice(linkerIndex + 1, conditions.length);

			//alert(condiGroup[1]);
			return condiGroup;
		}
	}

	//判断sql条件中是否含有and，or
	function containsLogicLinker(array) {

		var reAnd = /^and$/i;
		var reOr = /^or$/i;

		if (array.indexOf(reAnd) < 0) {
			return 'AND';
		} else if (array.indexOf(reOr) < 0) {
			return 'OR'
		} else {
			return null;
		}
	}

	function generateResultPage() {
		var sql = $("#sqlStatement").val().toUpperCase();
		//var form = document.forms[0];
		var url = ctx + "/system/generalconfig/executeSql.action";
		url = addParam(url, "dbId", $('#dbId').val());
		url = addParam(url, "sql",
				encodeURI(encodeURI($("#sqlStatement").val())));
		//form.action=url;
		//form.submit();

		$.getJSON(url, null, function(result) {
			//alert(result);
			$("#result").val(result.message);
		});
	}

	function testParseSql() {
		var url = ctx + "/system/generalconfig/checkFields.action";
		window.location.href = url;
	}

	function previewReportForm() {

		var rows = [];
		var $headers = $("th");
		var $rows = $("tbody tr").each(function(index) {
														$cells = $(this).find("td");
														rows[index] = {};
														$cells.each(function(cellIndex) {
																	//rows[index][$($headers[cellIndex]).html()] = $(this).html();
																	//alert($(this).find("span").text());
																	if ($(this).children().prop("tagName") === "SELECT") {
																		rows[index][$($headers[cellIndex])
																				.attr('name')] = $(this)
																				.children().find(
																						":selected").val();
																	} else {
																		rows[index][$($headers[cellIndex])
																				.attr('name')] = $(this)
																				.text();
																	}
																	//rows[index][$($headers[cellIndex]).attr('name')] = $(this).val();  			  
																});
														});

		var obj = {};
		obj.myrows = rows;
		//alert(JSON.stringify(obj));

		//var formData=$("#contentForm").serialize();
		var url = ctx + "/system/generalconfig/perviewReportForm.action";
		//url = addParam(url, "configData", obj);
		
		var sql = $("#sqlStatement").val();
		var segments = sql.split(/[\s\,\;]+/);
		
		if(segments.indexOf("where") === -1){
			var targetTable = getCertainSegments(segments, 'from', null);
		}else{
			var targetTable = getCertainSegments(segments, 'from', 'where');
		}
		
		$.ajax({
			url : url,
			data : {
				d : obj,
				t : targetTable
			},
			dataType : "json",
			type : "post",
			success : function(result) {
				alert(result);
			}
		});
	}

	//判断字符串为空
	function isEmpty(str) {
		return (!str || 0 === str.length);
	}

	//判断字符串为空白
	function isBlank(str) {
		return (!str || /^\s*$/.test(str));
	}
</script>
</head>
<body scroll="no">

	<form method="post" id="contentForm">
		<div class="formContent">
			<div>
				<div style="height: 10%">
					当前数据源:<input id="tableRename" type="text" value="" />
					<hr style="height: 1px; border: none; border-top: 1px dashed #0066CC;" />
				</div>
				<div>
					<label> 数据源 </label>
					<cx:select id="dbId" name="dbId" value="%{paramsMap.dbId}"
						list="!com.congxing.rulemgt.dbconfig.model.DbConfigVO*dbId*dbName"
						emptyOption="true" headerKey="" headerValue="请选择..."
						cssClass="combox required" />
				</div>


				<div>
					<br> <label> sql语句 </label>
					<s:textarea id="sqlStatement" rows="2" cols="50" value=""></s:textarea>
					<a href="javascript:void(0);" id="doSqlParser"
						onclick="generateConfigTable();return false;" class="linkbutton"
						data-options="iconCls:'icon-save'">解析</a>
				</div>
				<div>

					解析查询结果<br>

					<div id="resultTable">
						<table id="rTable" border="1">
							<thead>
								<tr>
									<th name="field">字段</th>
									<th name="fieldName">字段名</th>
									<th name="isSelectedAsKey">是否为用于查询</th>
									<th name="isSetAsHidden">是否显示</th>
									<th name="rename">字段重命名</th>
								</tr>
							<thead>
							<tbody id="tbody">

							</tbody>
						</table>


						<hr
							style="height: 1px; border: none; border-top: 1px dashed #0066CC;" />
					</div>

					<!-- <div>
					解析查询条件<br>
					<div id="condiTable"></div>
				</div> -->

					<div>
						<hr
							style="height: 1px; border: none; border-top: 1px dashed #0066CC;" />
						<a href="javascript:void(0);"
							onclick="previewReportForm();return false;" class="linkbutton"
							data-options="iconCls:'icon-save'"> 预览</a> <a
							href="javascript:void(0);"
							onclick="generateResultPage();return false;" class="linkbutton"
							data-options="iconCls:'icon-save'"> 执行</a>
					</div>

					<!-- 
				<div>
					<s:textarea id="result" name="result" rows="5" cols="60" value=""
						disabled="true"></s:textarea>
				</div>
				 -->
				</div>

			</div>
	</form>



	<!-- JS模板 -->
	<script id="tmpl-tbody-tr" type="text/x-jquery-tmpl">
<tr>
				<td><span>{{= COLUMN_NAME}}</span></td>
				<td><span>{{= COLUMN_COMMENT}}</span></td>
				<td><cx:select list="$TORF" name="status" cssClass="required combox editControl" value="%{paramsMap.status}" emptyOption="true" headerKey="" headerValue="请选择状态"/></td>
				<td><cx:select list="$TORF" name="status" cssClass="required combox editControl" value="%{paramsMap.status}" emptyOption="true" headerKey="" headerValue="请选择状态"/></td>
				<td> <input type="text" name="columnRename"></td>
</tr>
</script>

</body>
</html>