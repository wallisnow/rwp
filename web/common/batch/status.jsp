<%@page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="cx" uri="/WEB-INF/congxing-tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	String contextPath = request.getContextPath();
	String taskBeanName = request.getParameter("taskBeanName");
	if (taskBeanName == null) {
		taskBeanName = (String) request.getAttribute("taskBeanName");
	}
	Object taskBean = request.getSession().getAttribute(taskBeanName);
	pageContext.setAttribute("TASKBEANREF", taskBean);
%>
<html>
	<head>
		<title><c:out value="${param.title}" /></title>
		<c:if test="${TASKBEANREF.running eq 'TRUE'}">
		<script language="JavaScript" type="text/JavaScript">
		setTimeout("location='<%=contextPath%>/common/batch/status.jsp?taskBeanName=<%=taskBeanName%>'", 6000);
		</script>
		</c:if>
	</head>
	<body>
		<table style="margin-bottom: 20px;font-size: 12px;" align="center" width="90%">
			<tr>
				<cx:DisplayOpr task="<%=taskBeanName%>" />
			</tr>
		</table>
		<c:if
			test="${TASKBEANREF.completed eq 'TRUE' and !empty TASKBEANREF.filename and TASKBEANREF.start_end eq 'FALSE'}">
			<table width="90%" style="margin-left: 30px;font-size: 12px;">
				<thead>
					<tr>
						<th align="center" colspan="2">
							<span style="color: gray; font-size: 16px;">批量导入报告</span>
						</th>
					</tr>
				</thead>
				<tbody style="vertical-align: top;">
					<tr>
						<td style="width:100px;">
							导入总记录数:
						</td>
						<td>
							<c:out value="${TASKBEANREF.countrecord}" />
						</td>
					</tr>
					<tr>
						<td>
							成功数:
						</td>
						<td>
							<c:out value="${TASKBEANREF.ok}" />
						</td>
					</tr>
					<tr>
						<td>
							失败数:
						</td>
						<td>
							<c:out value="${TASKBEANREF.fail}" />
						</td>
					</tr>
					<tr>
						<td>
							处理结果记录:
						</td>
						<td>
							<c:choose>
								<c:when test="${!empty TASKBEANREF.resultFile}">
									<a href="<%=contextPath%>/common/batch/upload.jsp?filename=<c:out value="${TASKBEANREF.resultFile}"/>">
										下载结果文件 </a>
								</c:when>
								<c:otherwise>
									无记录
								</c:otherwise>
							</c:choose>
						</td>
					</tr>
				</tbody>
			</table>
		</c:if>
	</body>
</html>
