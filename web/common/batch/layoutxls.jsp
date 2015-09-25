<%@ page contentType="text/html; charset=utf-8"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ include file="/common/taglibs.jsp"%>
<tiles:useAttribute id="action" name="cmdUpload"
	classname="java.lang.String" scope="request" />
<html>
	<head>
		<title><tiles:getAsString name="title" /></title>
		<%@ include file="/common/header.jsp"%>
		<script type="text/javascript">

var cmdUpload = "<tiles:getAsString name='cmdUpload' />";
var cmdBatch = "<tiles:getAsString name='cmdBatch' />";
var cmdReturn = "<tiles:getAsString name='cmdReturn' ignore='true'/>";

var judge = true;
function showHelp() {
	if (!judge) {
		judge = true;
		$("#btnHelp button").html("隐藏格式帮助");
		$("#manualHelpTable").show();
	} else {
		judge = false;
		$("#btnHelp button").html("显示格式帮助");
		$("#manualHelpTable").hide();
	}
}

function href() {
	location = ctx + "/common/batch/upload.jsp?filename=" + $("#saveAsFullPath").val();
}

function doReturn() {
	location.href = ctx + cmdReturn;
}

function doBatchTask() {
	if ($("#saveAsFullPath").val() == "") {
		alert("请上传待处理文件");
		return;
	}
	var old_url = formItem.action;
	var old_tgt = formItem.target;
	var url = ctx + cmdBatch;
	formItem.action = url;
	formItem.target = "bottomFrame";
	formItem.submit();
	formItem.action = old_url;
	formItem.target = old_tgt;
	$("#unloadedFileName").hide();
	$("#btnProcess").attr("disabled", "disabled");
	//$("#btnUpload").attr("disabled", "disabled");
	judge = true;
	showHelp();
}

$(document).ready(function() {
	if ($("#saveAsFullPath").val() != null
			&& $("#saveAsFullPath").val() != "") {
		$("#uploadedInfo").show();
	} else {
		$("#btnProcess").attr("disabled", "disabled");
	}
	if (cmdReturn != "") {
		$("#btnReturn").show();
	}
	$("#manualHelpTable td").css( {
		"padding-top" : "10px",
		"padding-left" : "10px"
	});
	$("#btnProcess button").click(doBatchTask);
	$("#btnHelp button").click(showHelp);
	$("#btnReturn button").click(doReturn);
	judge = true;
	showHelp();
			
	//校验上传文件是否为空,如果为空，不提交表单
	$("#btnUpload button").click(function() {
		if ($("#uploadFile").attr("value") == '') {
			alert("请选择上传文件");
			return;
		} else {
			$("#formItem").submit();
		}
	});
	$("a").css("color", "blue");
});
</script>
	</head>
	<body style="overflow: auto;">
		<div>
			<s:actionerror />
			<s:fielderror />
		</div>
		<div class="formPageHeader">

			<form action="${ctx}${requestScope.action}" id="formItem"
				method="POST" enctype="multipart/form-data">

				<input type="hidden" id="batchBeanName" name="batchBeanName"
					value="<tiles:getAsString name='batchBeanName' />" />

				<input type="hidden" id="batchParamClassName"
					name="batchParamClassName"
					value="<tiles:getAsString name='batchParamClassName' ignore='true' />" />

				<input type="hidden" id="style" name="style"
					value="<tiles:getAsString name='style' ignore='true' />" />

				<input type="hidden" id="prefix" name="prefix"
					value="<tiles:getAsString name='prefix' ignore='true' />" />

				<s:hidden id="saveAsFullPath" name="saveAsFullPath"
					value="%{paramsMap.saveAsFullPath}" />

				<s:hidden id="saveAsFileName" name="saveAsFileName"
					value="%{paramsMap.saveAsFileName}" />

				<s:hidden id="oldFileName" name="oldFileName"
					value="%{paramsMap.oldFileName}" />

				<div id="fileArea"
					style="text-align: left; margin: 10px 10px 10px 20px;">
					<p>
						<label>
							选择上传文件：
						</label>
						<s:file name="uploadFile" label="上传的文件" cssStyle="width:400px" />
					</p>
					<div id="uploadedInfo"
						style="display: none; margin-left: 20px; margin-top: 5px; font-style: italic;">
						<p id="unloadedFileName">
							<label style="width: 100px;">
								已经上传文件:
							</label>
							<a href="javascript:href()"><span style="color: blue"><s:property value="fileName" /></span> </a>
						</p>
						<p>
							<label style="width: 100px;">
								导入提醒:
							</label>
							<strong><font color="red">请点击批量导入导入上传文件</font></strong>
						</p>
					</div>
				</div>

				<div class="divider"></div>

				<tiles:insertAttribute name="content" ignore="true" />

				<div class="divider"></div>

				<div id="buttonlist" class="formBar">
					<ul>
						<li>
							<div id="btnUpload" class="buttonActive">
								<div class="buttonContent">
									<button type="button">
										上 传
									</button>
								</div>
							</div>
						</li>
						<li>
							<div id="btnProcess" class="button">
								<div class="buttonContent">
									<button type="button">
										批量导入
									</button>
								</div>
							</div>
						</li>
						<li>
							<div id="btnHelp" class="button">
								<div class="buttonContent">
									<button type="button">
										隐藏格式帮助
									</button>
								</div>
							</div>
						</li>
						<li>
							<div id="btnReturn" class="button" style="display: none;">
								<div class="buttonContent">
									<button type="button">
										返 回
									</button>
								</div>
							</div>
						</li>
					</ul>
				</div>

				<div id="manualHelpTable"
					style="text-align: left; margin-left: 20px;">
					<table>
						<tbody style="vertical-align: top;">
							<tr>
								<td>
									文件类型:
								</td>
								<td>
									.xls电子表格文件 (1.表格中不需要留空行； 2.文件名的后缀必须是小写的".xls"； 3.不支持xlsx格式文件)
								</td>
							</tr>
							<tr>
								<td>
									备注:
								</td>
								<td>
									<pre><tiles:getAsString name="memo" /></pre>
								</td>
							</tr> 
							<tr>
								<td>
									导入模板:
								</td>
								<td style="color: blue;">
									<pre><tiles:getAsString name="example" /></pre>
								</td>
							</tr>
						</tbody>
					</table>
				</div>
			</form>
		</div>

		<tiles:insertAttribute name="end" ignore="true" />

		<div class="showstatus" style="padding-top: 30px; text-align: center;">
			<iframe name="bottomFrame" src="${ctx}/null.html" frameborder="0"
				height="300" width="100%"></iframe>
		</div>
	</body>
</html>
