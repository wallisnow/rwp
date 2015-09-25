/**
 * 附件处理JS文件
 */

/**
 * 上传附件操作(更新附件字段值)
 * @param voName
 * @param pkCol
 * @param pkValue
 * @param attCol
 * @return
 */
function uploadAttach(voName, pkCol, pkValue, attCol){
	if(typeof(art.dialog.data("ATTACH")) != "undefined"){
		art.dialog.removeData("ATTACH");
	}
	$.dialog.open(ctx + "/system/upload/attach.action", {
		id:"uploadAttachDialog", 
		title: "请选择上传附件", 
		width: 520, 
		height: 320, 
		close: function(){
			var attach = art.dialog.data("ATTACH");
			art.dialog.removeData("ATTACH");
			var attachId;
			if(typeof(attach) != "undefined"){
				attachId = attach.attId;
			}
			if(typeof(attachId) != "undefined"){
				var url = ctx + "/system/attach/uploadAttachJson.action?t=" + new Date() + "&m=" + Math.random();
				var sendJson = {
					voName: voName, 
					pkCol: pkCol,
					pkValue: pkValue,
					attCol: attCol,
					attId: attachId
				};
				$.getJSON(url, sendJson, function callback(json){
					if("success" == json.status){
						window.location = ctx + cmdAttach;
					}else{
						art.dialog.alert("保存附件失败,失败原因:" + json.message);
					}
				});
			}
		},			
		lock:true,
		drag:false
		}, 
		false
	);
}

/**
 * 删除附件操作(删除表中附件字段值同时删除附件)
 * @param voName
 * @param pkCol
 * @param pkValue
 * @param attCol
 * @param attId
 * @return
 */
function deleteAttach(voName, pkCol, pkValue, attCol, attId){
	var url = ctx + "/system/attach/uploadAttachJson.action?t=" + new Date() + "&m=" + Math.random();
	var sendJson = {
		voName: voName, 
		pkCol: pkCol,
		pkValue: pkValue,
		attCol: attCol,
		attId: attId
	};
	$.getJSON(url, sendJson, function callback(json){
		if("success" == json.status){
			window.location = ctx + cmdAttach;
		}else{
			art.dialog.alert("删除附件失败,失败原因:" + json.message);
		}
	});
}


/**
 * 上传附件操作(更新页面中属性值)
 * @param voName
 * @param pkCol
 * @param pkValue
 * @param attCol
 * @return
 */
function uploadAttachPage(pageAttId, pageAttName){
	$.dialog.open(ctx + "/system/upload/attach.action", {
			id:"uploadAttachDialog", 
			title: "请选择上传附件", 
			width: 520, 
			height: 320, 
			close: function(){
				var attach = art.dialog.data("ATTACH");
				art.dialog.removeData("ATTACH");
				if(typeof(attach) != "undefined"){
					$("#" + pageAttId).val(attach.attId);
					$("#" + pageAttName).val(attach.fileName);
				}
			},			
			lock:true,
			drag:false
		}, 
		false
	);
}


/**
 * 附件下载(根据附件ID下载附件)
 * @param attachId
 * @return
 */
function downFileByAttachId(attachId){
	var url = ctx + "/system/download/downFileByAttachId.action?attachId=" + attachId;
	window.location.href = url;
}

/**
 * 附件下载(根据附件ID下载附件)
 * @param attachId
 * @return
 */
function downFileByFullFilePath(fullFilePath){
	var url = ctx + "/system/download/downFileByFullFilePath.action?fullFilePath=" + attachId;
	window.location.href = url;
}

