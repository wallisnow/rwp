<%@page contentType="text/html;charset=UTF-8"%>
<%@ page import="com.congxing.core.utils.StringUtils"%>
<jsp:useBean id="upload" scope="page" class="com.congxing.core.download.SmartUpload" />

<%
    String fileAbsName = request.getParameter("filename");

    String downloadName = fileAbsName;
    int i = 0;
    i = fileAbsName.lastIndexOf('/');
    if(i != -1){
        downloadName = fileAbsName.substring(i + 1, fileAbsName.length());
    } else {
        i = fileAbsName.lastIndexOf('\\');
        if(i != -1){
            downloadName = fileAbsName.substring(i + 1, fileAbsName.length());
        }
    }

    fileAbsName = StringUtils.fromHttpParameter(fileAbsName);

    upload.initialize(pageContext);
    upload.setContentDisposition(null);
    upload.downloadFile(fileAbsName, null, downloadName);

    out.clear();
	out = pageContext.pushBody();
%>