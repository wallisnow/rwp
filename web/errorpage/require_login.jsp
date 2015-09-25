<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%String contextPath = request.getContextPath();
%>
<c:choose>
<c:when  test="${!empty sessionScope.ExpectUrl}">
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<title>异常提示</title>
		<script language="javascript">
function doback() {
  history.back();
}
function docontinue() {
  top.location="<c:out value='${param.LoginUrl}' />";
}
function doclose() {
  parent.close();
}
function overButton(node) {
	node.style.backgroundColor = "#FFCE00";
}

function overOutButton(node) {
	node.style.backgroundColor = "#DFDFDF";
}
</script>
	</head>
	<body bgcolor="#FFFFFF" text="#000000">
		<p>
			&nbsp;
		</p>
		<table width="90%" border="0" cellspacing="5" cellpadding="4" align="center" style="text-align:middle">
			<tr>
				<th align="center" colspan="2">
					系 统 错 误
				</th>
			</tr>
			<tr>
				<td class="row-light" rowspan="5" valign="top" width="20%" align="center">
					
				</td>
				<td class="row-light">
					提示信息：
				</td>
			</tr>
			<tr>
				<td class="row-light">
					你没有登录系统或你的登录已经过期，请登录！
				</td>
			</tr>
			<tr>
				<td align=center class="row-light">
      				&nbsp; <input type="button" onmouseover="javascript:overButton(this);" onmouseout="javascript:overOutButton(this);" class="bt" accesskey="c" onClick="docontinue()" value="登录(C)">
					&nbsp;
					<input type="button" onmouseover="javascript:overButton(this);" onmouseout="javascript:overOutButton(this);" class="bt" accesskey="x" onClick="doclose()" value="关闭(X)">
					&nbsp;
				</td>
			</tr>
		</table>
		<p>
			&nbsp;
		</p>
	</body>
</html>
</c:when>
<c:otherwise>
<script type="text/javascript">
top.location="<c:out value='${param.LoginUrl}' />";
</script>
</c:otherwise>
</c:choose>
