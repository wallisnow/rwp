<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/common/taglibs.jsp" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
	<title>上传文件</title>
	<%@ include file="/common/header.jsp"%>

<style type="text/css">
body {
	background:#fff;
	font: 12px/22px Verdana,Arial,sans-serif, "Times New Roman" ,宋体;
	margin:0;
	padding:0;
	text-align:left;
}

#header {
	width:100%;
	overflow: hidden;
	height: 30px;
	background: #eef4f5;
	font-size: 18px;
	margin:0px;
	padding:0px;
}

#header span {
	margin:5px 0 0 5px;
}


/=======================
#chooseDiv table {
	width:100%;
	border:0;
}
#chooseDiv table tr{
	height:30px;
	margin:0;
	padding:0;
}

#chooseDiv table td.left{
	width:80px;
	text-align:center;
}

#chooseDiv table td.right{
	text-align:left;
	padding-left:5px;
}

#chooseDiv table input{
	height: 23px;
	margin-bottom:2px;
	width:320px;
}

#chooseDiv table button{
	height: 23px;
	margin-bottom:2px;
	width:80px;
	margin-left:10px;
}


//======================
#progressDiv table {
	width:100%;
}
#progressDiv table tr{
	height:24px;
}

#progressDiv table td.left{
	width:80px;
	text-align:center;
}

#progressDiv table td.right{
	text-align:left;
	padding-left:5px;
}
</style>

<script language="javascript">

$(document).ready(function() {
	$("#progressDiv").hide();
	$("#msgDiv").hide();
});

var subFixArray =  new Array("doc", "docx", "xls", "xlsx", "ppt", "pptx", "txt","mht", "rar");

function checkSubFix(fileSubFix){
	for(var index = 0; index < subFixArray.length; index++){
		if(subFixArray[index] == fileSubFix)return true;
	}
	return false;
}

function controlPage(boolFlag){
	if(boolFlag){
		$("#uploadFile").attr("readonly",true);
		$("#btnUpload").attr("disabled",true);
	}else{
		$("#uploadFile").removeAttr("readonly");
		$("#btnUpload").removeAttr("disabled");
	}
}

function beginUpload() {
	var file = $("#uploadFile").val();
	if(typeof(file) == "undefined")return false;
	var fileSubFix = file.substring(file.lastIndexOf(".") + 1).toLowerCase();
	if(!checkSubFix(fileSubFix)){
		$("#msgDiv").show();
		$("#msgDiv").html("文件类型不正确!");
		return false;
	}
	controlPage(true);
	
	$("#progressDiv").show();
	$("#msgDiv").hide();

	var i = setInterval(function() {
		$.getJSON("${ctx}/system/upload/progressJson.action?t=" + new Date() + "&m=" + Math.random(),
				function(data) {
					if (data == null) {
						clearInterval(i);
						//location.reload(true);
						return;
					}
					if (data.status == "done"){
						clearInterval(i);
						$("#progressDiv").hide();
						art.dialog.data("ATTACH", data.attachVO);
						art.dialog.close();
						//location.reload(true);
						return;
					}
					if (data.status == "error"){
						$("#msgDiv").show();
						$("#msgDiv").html(data.errorMsg);
						controlPage(false);
						return;
					}
					var percentage = Math.floor(100 * parseInt(data.bytesRead) / parseInt(data.totalSize));
					var ratio = "未知";
					if(data.elapsedTime > 0){
						ratio = Math.floor(parseInt(data.bytesRead)/parseInt(data.elapsedTime));
					}
					var backDivW = Math.floor($("#backDiv").width());
					var backDivPerW = backDivW/100;
					$("#display").html("已上传：" + percentage +"%" + "  速度:" + data.elapsedTime + "K/S");
					var leftW = percentage * backDivPerW - backDivW;
					$("#color").css("left", leftW);
				});
		}, 1000);
	return true;
}
</script>

</head>

<body scroll="no">

<div id="header">
	<span>文件上传(类型：doc,docx,xls,xlsx,ppt,pptx,txt,mht,rar)</span>
</div>

<form action="${ctx}/system/upload/uploadFile.action" id="uploadForm" enctype="multipart/form-data" method="post" target="progressFrame" onsubmit="return beginUpload();">

<div id="chooseDiv">
	<table>
		<tr>
			<td class="left">选择附件:</td>
			<td class="right"><input type="file" name="image" id="uploadFile"/><button type="submit" it="btnUpload">上传</button></td>
		</tr>
	</table>
</div>

<div id="msgDiv" style="width:100%; padding-left:15px;">
</div>

</form>

<div id="progressDiv">
	<table>
		<tr>
			<td class="left">进度展示:</td>
			<td class="right">
				<div id="backDiv" style="position:relative; width:400px; height:24px; padding:0 5px; border:1px solid #ccc; font:Arial; font-size:12px; z-index:0; overflow:hidden;">
				    <div id="display" style="width:410px;height:24px;line-height:24px;text-align:center;position:absolute;top:0;left:0;z-index:3;"></div>
				    <div id="color" style="display:block;width:412px;height:26px;background:#DCDCDC;position:absolute;left:-412px;top:0;z-index:2;"></div>
				</div>
			</td>
		</tr>
	</table>
</div>

<iframe style="display: none;" name="progressFrame"></iframe>

</body>

</html>