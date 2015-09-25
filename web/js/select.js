/**
 * 初始化页面选择事件
 * 
 * @return
 */
function initSelectEvent() {
	$("tbody tr").each(function() {
		$(this).dblclick(function() {
			art.dialog.data("selectkey", $(this).attr("PK"));
			art.dialog.close();
		});
	});
}

/**
 * 选择框
 * 
 * @return
 */
function selectDialog(textNameArray, diaInfo) {
	$.dialog
			.open(
					ctx + diaInfo.url,
					{
						id : "selectDialog",
						title : diaInfo.title,
						width : diaInfo.width,
						height : diaInfo.height,
						close : function() {
							var selectValue = art.dialog.data('selectkey');
							art.dialog.removeData('selectkey');
							for (var i = 0; i < textNameArray.length; i++) {
								if (typeof ($("#" + textNameArray[i].toString())) == 'undefined')
									continue;
								$("#" + textNameArray[i].toString()).val('');
							}
							if (typeof (selectValue) != 'undefined') {
								var array = selectValue.split("|");
								for (var i = 0; i < textNameArray.length; i++) {
									if (typeof ($("#"
											+ textNameArray[i].toString())) == 'undefined')
										continue;
									$("#" + textNameArray[i].toString())
											.val('');
									if (typeof (array[i]) == 'undefined')
										continue;
									$("#" + textNameArray[i].toString()).val(
											array[i].toString());
								}
							}
						},
						lock : true,
						drag : false
					}, false);
}

/**
 * 普通用户选择
 * 
 * @param userId
 * @param userName
 * @return
 */
function selectUser(userId, userName, email, mobileno) {
	$.dialog.open(ctx + "/system/user/select.action", {
		id : "selectUser",
		title : "用户选择",
		width : 900,
		height : 500,
		close : function() {
			var selectValue = art.dialog.data("selectkey");
			art.dialog.removeData("selectkey");
			$("#" + userId).val("");
			$("#" + userName).val("");
			$("#" + email).val("");
			$("#" + mobileno).val("");
			if (typeof (selectValue) != "undefined") {
				var array = selectValue.split("|");
				$("#" + userId).val(array[0]);
				$("#" + userName).val(array[1]);
				$("#" + email).val(array[2]);
				$("#" + mobileno).val(array[3]);
			}
		},
		lock : true,
		drag : false
	}, false);
}

/**
 * 特定角色用户选择
 * 
 * @param userId
 * @param userName
 * @return
 */
function selectUserWithRole(roleId, userId, userName) {
	$.dialog.open(ctx + "/system/user/selectWithRole.action?_seq_roleId="
			+ roleId, {
		id : "selectUser",
		title : "特定角色用户选择",
		width : 900,
		height : 500,
		close : function() {
			var selectValue = art.dialog.data("selectkey");
			art.dialog.removeData("selectkey");
			$("#" + userId).val("");
			$("#" + userName).val("");
			if (typeof (selectValue) != "undefined") {
				var array = selectValue.split("|");
				$("#" + userId).val(array[0]);
				$("#" + userName).val(array[1]);
			}
		},
		lock : true,
		drag : false
	}, false);
}

/**
 * 角色选择
 * 
 * @param roleId
 * @param roleName
 * @return
 */
function selectRole(roleId, roleName) {
	$.dialog.open(ctx + "/system/role/select.action", {
		id : "selectRole",
		title : "角色选择",
		width : 900,
		height : 500,
		close : function() {
			var selectValue = art.dialog.data("selectkey");
			art.dialog.removeData("selectkey");
			$("#" + roleId).val("");
			$("#" + roleName).val("");
			if (typeof (selectValue) != "undefined") {
				var array = selectValue.split("|");
				$("#" + roleId).val(array[0]);
				$("#" + roleName).val(array[1]);
			}
		},
		lock : true,
		drag : false
	}, false);
}

/**
 * 产品选择
 * 
 * @param roleId
 * @param roleName
 * @return
 */
function selectProduct(prodCode, prodName, enteCode, busiCode, prodManager) {
	$.dialog.open(ctx + "/activity/dataservice/syproduct/select.action", {
		id : "selectProduct",
		title : "产品选择",
		width : 900,
		height : 500,
		close : function() {
			var selectValue = art.dialog.data("selectkey");
			art.dialog.removeData("selectkey");
			$("#" + prodCode).val("");
			$("#" + prodName).val("");
			$("#" + enteCode).val("");
			$("#" + busiCode).val("");
			$("#" + prodManager).val("");
			if (typeof (selectValue) != "undefined") {
				var array = selectValue.split("|");
				$("#" + prodCode).val(array[0]);
				$("#" + prodName).val(array[1]);
				$("#" + enteCode).val(array[2]);
				$("#" + busiCode).val(array[3]);
				$("#" + prodManager).val(array[4]);
			}
		},
		lock : true,
		drag : false
	}, false);
}

/**
 * 二级活动编码选择
 * 
 * @param roleId
 * @param roleName
 * @return
 */
function selectSecAct(secActCode, secActName, firActCode) {
	$.dialog.open(ctx + "/activity/market/secact/select.action", {
		id : "selectProduct",
		title : "二级活动编码选择",
		width : 900,
		height : 500,
		close : function() {
			var selectValue = art.dialog.data("selectkey");
			art.dialog.removeData("selectkey");
			$("#" + secActCode).val("");
			$("#" + secActName).val("");
			$("#" + firActCode).val("");
			if (typeof (selectValue) != "undefined") {
				var array = selectValue.split("|");
				$("#" + secActCode).val(array[0]);
				$("#" + secActName).val(array[1]);
				$("#" + firActCode).val(array[2]);
			}
		},
		lock : true,
		drag : false
	}, false);
}

/**
 * 活动监控请求信息选择
 * 
 * @return
 */
function selectReqInfo(reqId) {
	$.dialog.open(ctx
			+ "/activity/dataservice/localact/select.action?reqType=1", {
		id : "selectReqInfo",
		title : "申请序号选择",
		width : 900,
		height : 500,
		close : function() {
			var selectValue = art.dialog.data("selectkey");
			art.dialog.removeData("selectkey");
			if (typeof (selectValue) != "undefined") {
				$("#" + reqId).val(selectValue);
			}
		},
		lock : true,
		drag : false
	}, false);
}

/*
 * 一级活动编码选择 @param roleId @param roleName @return
 */
function selectFirAct(firActCode, firActName) {
	$.dialog.open(ctx + "/activity/market/firact/select.action", {
		id : "selectFirAct",
		title : "一级活动编码选择",
		width : 900,
		height : 500,
		close : function() {
			var selectValue = art.dialog.data("selectkey");
			art.dialog.removeData("selectkey");
			$("#" + firActCode).val("");
			$("#" + firActName).val("");
			if (typeof (selectValue) != "undefined") {
				var array = selectValue.split("|");
				$("#" + firActCode).val(array[0]);
				$("#" + firActName).val(array[1]);
			}
		},
		lock : true,
		drag : false
	}, false);

}

/**
 * 数业活动编码选择
 * 
 * @param roleId
 * @param roleName
 * @return
 */
function selectSYActCode(actCode, actName) {
	$.dialog.open(ctx + "/activity/dataservice/formalactivity/select.action", {
		id : "selectProduct",
		title : "数业活动编码选择",
		width : 900,
		height : 500,
		close : function() {
			var selectValue = art.dialog.data("selectkey");
			art.dialog.removeData("selectkey");
			$("#" + actCode).val("");
			$("#" + actName).val("");
			if (typeof (selectValue) != "undefined") {
				var array = selectValue.split("|");
				$("#" + actCode).val(array[0]);
				$("#" + actName).val(array[1]);
			}
		},
		lock : true,
		drag : false
	}, false);
}

/**
 * 营销方案ID选择
 * 
 * @param roleId
 * @param roleName
 * @return
 */
function selectYxplan(yxplanId, yxplanName) {
	$.dialog.open(ctx + "/activity/market/yxplan/select.action", {
		id : "selectActPlan",
		title : "营销方案ID选择",
		width : 900,
		height : 500,
		close : function() {
			var selectValue = art.dialog.data("selectkey");
			art.dialog.removeData("selectkey");
			$("#" + yxplanId).val("");
			$("#" + yxplanName).val("");
			if (typeof (selectValue) != "undefined") {
				var array = selectValue.split("|");
				$("#" + yxplanId).val(array[0]);
				$("#" + yxplanName).val(array[4]);
			}
		},
		lock : true,
		drag : false
	}, false);
}

/**
 * 公告选择
 * 
 * @param userId
 * @param userName
 * @return
 */
function selectNotice(ntId, title) {
	$.dialog.open(ctx + "/noticemgmt/notice/select.action", {
		id : "selectNotice",
		title : "公告选择",
		width : 900,
		height : 500,
		close : function() {
			var selectValue = art.dialog.data("selectkey");
			art.dialog.removeData("selectkey");
			$("#" + ntId).val("");
			$("#" + title).val("");
			if (typeof (selectValue) != "undefined") {
				var array = selectValue.split("|");
				$("#" + ntId).val(array[0]);
				$("#" + title).val(array[1]);
			}
		},
		lock : true,
		drag : false
	}, false);
}

/**
 * 组织架构选择
 * 
 * @param dpId
 * @param dpName
 * @return
 */
function selectSysDp(dpId, dpName) {
	$.dialog.open(ctx + "/system/sysdp/tree.action", {
		id : "selectSysDp",
		title : "组织选择",
		width : 900,
		height : 500,
		close : function() {
			var checkItems = art.dialog.data("selectkey");
			art.dialog.removeData("selectkey");
			if (typeof (checkItems) != "undefined") {
				if (checkItems == "-1") {

				} else {
					var dpIds = "";
					var dpNames = "";
					for (var i = 0; i < checkItems.length; i++) {
						dpIds += checkItems[i].dpId + "|";
						dpNames += checkItems[i].dpName + ";";
					}
					$("#" + dpId).val(dpIds);
					$("#" + dpName).val(dpNames);
				}
			}
		},
		lock : true,
		drag : false
	}, false);
}

function selectSysDpList(dpId, dpName) {
	$.dialog.open(ctx + "/system/sysdp/select.action", {
		id : "selectSysDpList",
		title : "组织架构选择",
		width : 900,
		height : 500,
		close : function() {
			var selectValue = art.dialog.data("selectkey");
			art.dialog.removeData("selectkey");
			$("#" + dpId).val("");
			$("#" + dpName).val("");
			if (typeof (selectValue) != "undefined") {
				var array = selectValue.split("|");
				$("#" + dpId).val(array[0]);
				$("#" + dpName).val(array[1]);
			}
		},
		lock : true,
		drag : false
	}, false);
}

function selectBatchList(batch) {
	var reportType = $("select[name='reportType']").val();
	var cjType = $("select[name='cjType']").val();
	var oprMonth = $("input[name='oprMonth']").val();
	var rptStatus = $("input[name='rptStatus']").val();
	if (reportType.length < 1) {
		alert("请选择报表类型");
		return;
	}
	if (cjType.length < 1) {
		alert("请选择酬金类型");
		return;
	}
	if (oprMonth.length < 1) {
		alert("请选择月份");
		return;
	}
	var url = ctx + "/report/monthdatasinfo/listbatch.action";
	url = addParam(url, "reportType", reportType);
	url = addParam(url, "cjType", cjType);
	url = addParam(url, "oprMonth", oprMonth);
	url = addParam(url, "rptStatus", rptStatus);
	$.dialog.open(url, {
		id : "selectBatchList",
		title : "批次号选择",
		width : 500,
		height : 500,
		close : function() {
			var selectValue = art.dialog.data("selectkey");
			art.dialog.removeData("selectkey");
			$("#" + batch).val("");
			if (typeof (selectValue) != "undefined") {
				$("#" + batch).val(selectValue);
			}
		},
		lock : true,
		drag : false
	}, false);
}

function selectTest(username) {
	$.dialog.open(ctx + "/system/test/select.action", {
		id : "selectTest",
		title : "测试",
		width : 900,
		height : 500,
		close : function() {
			var selectValue = art.dialog.data("selectkey");
			art.dialog.removeData("selectkey");
			$("#" + username).val("");
			if (typeof (selectValue) != "undefined") {
				var array = selectValue.split("|");
				$("#" + username).val(array[0]);
			}
		},
		lock : true,
		drag : false
	}, false);
}