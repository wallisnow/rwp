<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<%@ page import="com.congxing.core.web.filter.MenuAccessMonitor"%>
<%
	String changeStatus = request.getParameter("changeStatus");
	if (changeStatus != null && changeStatus.compareTo("true") == 0) {
		boolean nowVal = MenuAccessMonitor.IS_MENU_MONITOR;
		MenuAccessMonitor.IS_MENU_MONITOR = !nowVal;
	}
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<title>Menu Monitor Switcher</title>
		<%@ include file="/common/header.jsp"%>
		<script language="javascript">
function changeMonitorStatus() {
	var nowStatus = "<%=MenuAccessMonitor.IS_MENU_MONITOR%>";
	var confirmStr = "";
	if (nowStatus == "true") {
		confirmStr = "确认要关闭菜单访问日志记录功能吗？";
	} else {
		confirmStr = "确认要开启菜单访问日志记录功能吗？";
	}
	if (confirm(confirmStr)) {
		$("#changeStatus").val("true");
		$("#mmform").submit();
	}
}
</script>
	</head>
	<body>
		<div
			style="border: 1px solid gray; margin: 20px; padding: 10px; width: 400px;">
			<h2 style="font-size: 20px; margin: 10px;">
				设置是否记录菜单访问日志
			</h2>
			<form id="mmform" action="${ctx}/console/menumonitor.jsp"
				method="post">
				<!-- 是否记录日志开关 -->
				<input type="hidden" id="changeStatus" name="changeStatus"
					value="false" />
				<!-- 当前日志记录状态 -->
				<div style="margin-left: 20px; margin-top: 10px;">
					当前状态：
					<strong style="color: red;"><%=MenuAccessMonitor.IS_MENU_MONITOR%></strong>
				</div>
				<div style="margin-left: 20px; margin-top: 10px;">
					<input type="button" onclick="changeMonitorStatus()"
						value="点击切换日志记录状态" />
				</div>
			</form>
		</div>
	</body>
</html>
