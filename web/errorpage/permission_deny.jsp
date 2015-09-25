<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	String contextPath = request.getContextPath();
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<title>异常提示</title>
		<script language="javascript">
function doback() {
	history.back();
}
function docontinue() {
	window.location.replace("");
}
function doclose() {
	parent.close();
}
</script>

	</head>

	<body bgcolor="#FFFFFF" text="#000000" marginwidth="0" marginheight="0"
		leftmargin="0" rightmargin="0" topmargin="0" bottommargin="0">
		<p>
			&nbsp;
		</p>
		<table width="90%" border="0" cellspacing="5" cellpadding="4" style="text-align:middle">
			<tr>
				<th align="center" colspan="2">
					系 统 错 误
				</th>
			</tr>
			<tr>
				<td class="row-light" rowspan="5" valign="top" width="20%"
					align="center">
				</td>
				<td class="row-light">
					提示信息：
				</td>
			</tr>
			<tr>
				<td class="row-light">
					你没有权限使用此功能！
				</td>
			</tr>
			<tr>
				<td align=center class="row-light">
					&nbsp;
					<input type="button" onmouseover="javascript:overButton(this);"
						onmouseout="javascript:overOutButton(this);" class="bt"
						accesskey="b" onClick="doback()" value="返 回">
					&nbsp;<!-- 
					<input type="button" onmouseover="javascript:overButton(this);"
						onmouseout="javascript:overOutButton(this);" class="bt"
						accesskey="x" onClick="doclose()" value="关 闭">
					&nbsp; -->
				</td>
			</tr>
		</table>
		<p>
			&nbsp;
		</p>
	</body>
</html>
