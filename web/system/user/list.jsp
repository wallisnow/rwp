<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>

<title><s:text name="title" /></title>
<%@ include file="/common/header.jsp"%>


<script type="text/javascript">
	cmdAdd = "/system/user/add.action";
	cmdDelete = "/system/user/delete.action";
	cmdEdit = "/system/user/edit.action";
	cmdLoad = "/system/user/batch.jsp";

	$(function() {
		initPageEnv("list");
	});

	function dochangePwd() {
		var form;
		if (typeof (formId) == "undefined") {
			form = document.forms[0];
		} else {
			form = document.forms[formId];
		}
		if (typeof (form) == "undefined") {
			art.dialog.alert("请检查页面定义是否有误");
			return;
		}
		var delCount = 0;
		var userId = "";
		var userType = "";
		$(form).find("input[name=PK]").each(function() {
			$chbox = $(this);
			if ($chbox.attr("checked")) {
				userId = $chbox.attr("value");
				userType = $chbox.attr("userType");
				delCount += 1;
			}
		});
		if (delCount < 1) {
			art.dialog.alert("请选择编辑记录");
			return;
		} else if (delCount > 1) {
			art.dialog.alert("请确认一次只能编辑一条记录");
			return;
		}

		var params = {
			voName : "com.congxing.system.user.model.UserVO",
			pkName : "userId",
			pkValue : userId
		};

		if ("L" == userType) {//只有本地用户才能修改密码
			$.dialog.open("${ctx}/system/user/adminChangePwd.action?userId="
					+ userId, {
				id : "selectRole",
				title : "修改密码",
				width : 300,
				height : 200,
				close : function() {
				},
				lock : true,
				drag : false
			}, false);
		} else {
			art.dialog.alert("Portal用户不能修改密码！");
		}
	}
</script>
</head>

<body>

	<h2 class="contentTitle">
		<s:text name="title" />
		<cx:Msg />
	</h2>

	<form action="${ctx}/system/user/list.action" method="post" id="listForm">
		<div class="pageMsgInfo">
			<cx:Msg />
		</div>
		<div class="searchPageHeader">
			<div class="searchContent">
				<p>
					<label><s:text name="_sk_userId" />:</label>
					<s:textfield name="_sk_userId" value="%{paramsMap._sk_userId}" />
					<a href="javascript:void(0)" class="btnLook" onclick='selectUser("_sk_userId");'></a>
				</p>
				<p>
					<label><s:text name="_sk_userName" />:</label>
					<cx:textfield name="_sk_userName" value="%{paramsMap._sk_userName}" />
				</p>
			</div>

			<div class="searchBar">
				<a href="javascript:void(0);" onclick="doQuery();return false;" class="linkbutton" data-options="iconCls:'icon-query'"><s:text name="query" /></a>
				<a href="javascript:void(0);" onclick="doReset();return false;" class="linkbutton" data-options="iconCls:'icon-reset'"><s:text name="reset" /></a>
				<a href="javascript:void(0);" onclick="doLoad();return false;" class="linkbutton"><s:text name="load" /></a>
			</div>
		</div>

		<div class="searchPageContent">
			<div class="panelBar">
				<ul class="toolBar">
					<li><a class="add" href="Javascript:doAdd();"><span>添加</span></a></li>
					<li><a class="edit" href="Javascript:doEditList();"><span>修改</span></a></li>
					<li><a class="edit" href="Javascript:dochangePwd();"><span>修改密码</span></a></li>
				</ul>
			</div>
			<div class="tableBar">
				<table class="list nowrap">
					<thead>
						<tr>
							<th><input type="checkbox" name="chooseAll"
								onclick="checkAll()" /></th>
							<th><s:text name="userId" /></th>
							<th><s:text name="userName" /></th>
							<th><s:text name="mobileno" /></th>
							<th><s:text name="dpId" /></th>
							<th><s:text name="dpName" /></th>
							<th><s:text name="dpFullName" /></th>
							<th><s:text name="userType" /></th>
						</tr>
					</thead>
					<tbody>
						<c:forEach var="item" items="${page.datas}">
							<tr PK="${item.userId}">
								<td><input type="checkbox" name="PK" value="${item.userId}"
									userType="${item.userType}" /></td>
								<td><c:out value="${item.userId}" /></td>
								<td><c:out value="${item.userName}" /></td>
								<td><c:out value="${item.mobileno}" /></td>
								<td><c:out value="${item.dpId}" /></td>
								<td><c:out value="${item.dpName}" /></td>
								<td><c:out value="${item.dpFullName}" /></td>
								<td><cx:Code2Name definition="$USERTYPE"
										value="${item.userType}" /></td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
			</div>
			<div class="panelBar">
				<cx:Page page="${page}" />
			</div>
		</div>

	</form>

</body>
</html>
