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
		//alert('${paramsMap.keys}');
	});

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

	function getTargetTable(sqlst) {

		if ($.isEmptyObject(sqlst) || isEmpty(sqlst)) {
			return;
		}

		sql = sqlst.toUpperCase();
		var segments = sql.split(/[\s\,\;]+/);
		if (segments.indexOf("WHERE") === -1) {
			var targetTable = getCertainSegments(segments, 'FROM', null);
		} else {
			var targetTable = getCertainSegments(segments, 'FROM', 'FROM');
		}
		return targetTable[0];
	}
	
	function testSqlInput(sql){
		var test = sql.toUpperCase();
		if ($.isEmptyObject(test) || isEmpty(test)) {
			return false;
		}
		if(test.indexOf("SELECT")<0){
			return false;
		}
		if(test.indexOf("FROM")<0){
			return false;
		}
		if(!test.match("^SELECT")){
			return false;
		}
		return true;
	}

	function generateConfigTable() {

		var sql = $("#sqlStatement").val();
		var sql = sql.toUpperCase();
		if(!testSqlInput(sql)){
			alert("请检查您的sql语句，是否为单表查询，或包含特殊字符等");
			return;
		}

		var status = "${param.STATUS}";
		//查询结果匹配
		var	segments = sql.split(/[\s\,\;]+/);
		var	results = getCertainSegments(segments, 'SELECT', 'FROM');

		var targetTable = getTargetTable(sql);

		var url = ctx + "/system/generalconfig/checkFields.action";
		url = addParam(url, "dbId", $('#dbId').val());
		url = addParam(url, 'currentTable', targetTable);

		if (results.toString().match(/^\*$/)) {
			url = addParam(url, 'fieldsArray', "FULLQUERY");
		} else {
			url = addParam(url, 'fieldsArray', results);
		}

		//var datas = [{'COLUMN_NAME':'aaa'},{'COLUMN_NAME':'bbb'},{'COLUMN_NAME':'ccc'}];
		$.getJSON( url,
				   JSON.stringify(results),
				   function(result) {
										$("#tableRename").val(targetTable);
										var datas = result;
										var $datas = $('#tmpl-tbody-tr').tmpl(datas);
										$('#tbody').html($datas);
										//如果数据源配置不正确
										if ($("#tbody").children().length == 0) {
											alert("解析出错, 请检查您的数据源配置是否正确, 或是否为单表查询sql语句, 错误内容: " + result.message);
											return;
											}
									});
		}

	function getCfgTableData() {
		var rows = [];
		var $headers = $("th");
		var $rows = $("tbody tr").each(function(index) {
											$cells = $(this).find("td");
											rows[index] = {};
											$cells.each(function(cellIndex) {
														if ($(this).children().prop("tagName") === "SELECT") {
															rows[index][$($headers[cellIndex]).attr('name')] = $(this).children().find(":selected").val();
														} else if ($(this).children().prop("tagName") === "TEXTAREA") {
															rows[index][$($headers[cellIndex]).attr('name')] = $(this).children().val();
														} else {
															rows[index][$($headers[cellIndex]).attr('name')] = $(this).text();
														}
														//rows[index][$($headers[cellIndex]).attr('name')] = $(this).val();  			  
													});
										});

		var obj = {};
		obj.cfgrows = rows;
		return obj;
	}

	function previewReportForm() {
		var cfgtable = getCfgTableData();
		var skeys = [];
		var sHiddens = [];
		var sTrs = [];
		$.each(cfgtable, function(key, value) {
			$.each(value, function(index, ele) {
			    if(index>5){
			    	if(ele.isSelectedAsKey==="T"){
			    		var skey = new Object();
			    		if(!isEmpty(ele.rename)&&!isBlank(ele.rename)){
			    			skey.name=ele.rename;
			    		}else{
			    			skey.name=ele.field;
			    		}
			    		skeys.push(skey);
			    	}
			    	if(ele.isSetAsHidden==="T"){
			    		var sHidden = new Object();
			    		if(!isEmpty(ele.rename)&&!isBlank(ele.rename)){
			    			sHidden.name=ele.rename;
			    		}else{
			    			sHidden.name=ele.field;
			    		}
			    		sHiddens.push(sHidden);
			    		var sTr = new Object();
			    		sTr.name=" ... ... ";
			    		sTrs.push(sTr);
			    	}
			    }
			});
		});
 
		$("#searchctt").empty();
		$("#keys").tmpl(skeys).appendTo("#searchctt");
 
		$("#trctt").empty();
		$("#headers").tmpl(sHiddens).appendTo("#trctt");
		
		$("#trbctt").empty();
		$("#bodytp").tmpl(sTrs).appendTo("#trbctt");

		var d = art.dialog({
		    title: 'message',
		    content: '<div>'+$("#preview").html()+"</div>",
		    close: function(){
		    	$(this).dialog("close");
		    },
			lock : true,
			drag : false
		});
		
		$(".aui_content").css('width','500');
		$(".aui_content").css('height','300');
		d.show();
	}

	function generateResultPage() {
		if ($("#tbody").children().length == 0) {
			alert("请先解析您的sql语句");
			return;
		}
		var url = ctx + "/system/generalconfig/generateReprotForm.action";

		var dbId = $("#dbId").val();
		var configid = $("#generalconfigId").val();
		var formtitle = $("#tableRename").val();
		var formname = $("#tableName").val();
		if ($.trim(formname) === '') {
			alert("请输入表单名!");
			return;
		}
		var resource = $('#dbId').val();
		var sqlstatement = $('#sqlStatement').val();
		var author = $('#author').val();
		var mender = $('#mender').val();
		var des = $('#des').val();

		var formurl = $('#formurl').val();
		var creatingtime = $('#creatingtime').val();
		var modtime = $('#modtime').val();

		var cfgtable = getCfgTableData();

		$.ajax({
			url : url,
			data : {
				dbId : dbId,
				configid : configid,
				formtitle : formtitle,
				formname : formname,
				resource : resource,
				sqlstatement : sqlstatement,
				author : author,
				mender : mender,
				des : des,
				formurl : formurl,
				creatingtime : creatingtime,
				modtime : modtime,
				stt : "${param.STATUS}",
				cfgtable : cfgtable
			},
			//dataType : "json",
			type : "post",
			success : function(result) {
				alert(JSON.stringify(result));
				var url = ctx + "/system/generalconfig/show.action";
				window.location = url;
				},
			error : function(result) {
				alert(JSON.stringify(result));
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
		<input type="hidden" name="ISNEW" value="${paramsMap.ISNEW}" />
		<input type="hidden" name="ISEDIT" value="${paramsMap.ISEDIT}" />
		<input id=generalconfigId type="hidden" value="${paramsMap.generalconfigId}" />
		<input id=formurl type="hidden" value="${paramsMap.url}" />
		<input id="author" type="hidden" value="${paramsMap.author}" />
		<input id="creatingtime" type="hidden" value="${paramsMap.creatingtime}" />
		<input id="modtime" type="hidden" value="${paramsMap.modtime}" />

		<div class="formContent">
			<div>
				<div style="height: 10%">
					当前所操作的表:
					<s:textfield id="tableRename" type="text" value="%{paramsMap.formtitle}" />
					表名称:
					<s:textfield id="tableName" type="text" value="%{paramsMap.formname}" />
					<hr style="height: 1px; border: none; border-top: 1px dashed #0066CC;" />
				</div>
				<div>
					<label> 数据源 </label>
					<cx:select id="dbId" name="dbId" value="%{paramsMap.dbId}" list="!com.congxing.rulemgt.dbconfig.model.DbConfigVO*dbId*dbName" emptyOption="false" headerKey="" headerValue="请选择..." cssClass="combox required" />
				</div>


				<div>
					<br> <label> sql语句 </label>
					<s:textarea id="sqlStatement" rows="1" cols="30" value="%{paramsMap.sqlstatement}"></s:textarea>
					<a href="javascript:void(0);" id="doSqlParser" onclick="generateConfigTable();return false;" class="linkbutton" data-options="iconCls:'icon-save'">解析</a>
				</div>
				<div>

					解析查询结果<br>

					<div id="resultTable">
						<table id="rTable" border="1">
							<thead>
								<tr>
									<th name="field">字段</th>
									<th name="fieldName">字段注释</th>
									<th name="isSelectedAsKey">查询关键字</th>
									<th name="isSetAsHidden">显示此字段</th>
									<th name="rename">字段重命名</th>
									<th name="generalConfigData_id" style='display: none;'>配置编码</th>>
								</tr>
							<thead>
							<tbody id="tbody">
								<c:if test="${not empty  paramsMap.configTable}">
									<c:forEach var="item" items="${paramsMap.configTable}">
										<tr>
											<td><c:out value="${item.field}" /></td>
											<td><textarea readonly rows="1"> <c:out value="${item.fieldName}" /></textarea></td>
											<td>
												<c:set target="${paramsMap}" property="isSelectedAsKey" value="${item.isSelectedAsKey}" />
												<cx:select list="$TORF" name="status" cssClass="required editControl" value="%{paramsMap.isSelectedAsKey}" emptyOption="true" headerKey="" headerValue="请选择" />
											</td>
											<td><c:set target="${paramsMap}" property="isSetAsHidden" value="${item.isSetAsHidden}" />
												<cx:select list="$TORF" name="status" cssClass="required editControl" value="%{paramsMap.isSetAsHidden}" emptyOption="true" headerKey="" headerValue="请选择" />
											</td>
											<td><textarea rows="1"><c:out value="${item.rename}" /></textarea></td>
											<td style='display: none;'><c:out value="${item.generalConfigData_id}" /></td>
										</tr>
									</c:forEach>
								</c:if>
							</tbody>
						</table>

						<hr
							style="height: 1px; border: none; border-top: 1px dashed #0066CC;" />
					</div>

					<div>
						<label> 表单描述 </label>
						<s:textarea id="des" rows="5" cols="60" value="%{paramsMap.des}"
							disabled="false"></s:textarea>
					</div>
					<!-- <div>
					解析查询条件<br>
					<div id="condiTable"></div>
				</div> -->

					<div>
						<hr style="height: 1px; border: none; border-top: 1px dashed #0066CC;" />
						<a href="javascript:void(0);" onclick="previewReportForm();return false;" class="linkbutton" data-options="iconCls:'icon-save'"> 预览</a>
						<a href="javascript:void(0);" onclick="generateResultPage();return false;" class="linkbutton" data-options="iconCls:'icon-save'"> 执行</a>
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
				<td><cx:select list="$TORF" name="status" cssClass="required  editControl" value="%{paramsMap.status}" emptyOption="true" headerKey="" headerValue="请选择"/></td>
				<td><cx:select list="$TORF" name="status" cssClass="required  editControl" value="%{paramsMap.status}" emptyOption="true" headerKey="" headerValue="请选择"/></td>
				<td><input type="text" name="columnRename"></td>
			</tr>
	</script>
	
	<!-- dialog key模板 -->
	<script id="keys" type="text/x-jquery-tmpl">	
					<p>
						<label><c:out value="{{= name}}"/>:</label>
						<cx:textfield rows="1"/>
					</p>		
	</script>
	
	<!-- dialog header模板 -->
	<script id="headers" type="text/x-jquery-tmpl">
       			<th><span>{{= name}}</span></th>
	</script>
	
	<!-- dialog body模板 -->
	<script id="bodytp" type="text/x-jquery-tmpl">
       			<td><center><span>{{= name}}</span></center></td>
	</script>
	
	<div id="preview" style="display: none">
		<div class="searchPageHeader">
			<div id="searchctt" class="searchContent">			
			</div>

			<div class="searchBar">
				<a href="javascript:void(0);" class="linkbutton" data-options="iconCls:'icon-query'"><s:text name="query" /></a>
				<a href="javascript:void(0);" class="linkbutton" data-options="iconCls:'icon-reset'"><s:text name="reset" /></a>
			</div>
		</div>
		
		
		<div class="searchPageContent">
			<div class="tableBar">
				<table class="list nowrap">
					<thead>
						<tr id="trctt">
						</tr>
					</thead>
					<tbody>
	      					<tr id="trbctt">			        		
					      	</tr>
					</tbody>
				</table>
			</div>
		</div>
	
	</div>
	
</body>
</html>