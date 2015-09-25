<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ include file="/common/taglibs.jsp" %>

<tiles:insertTemplate template="/common/batch/layout.jsp">

<tiles:putAttribute name="title">
文件上传--最简单无附加示例
</tiles:putAttribute>

<tiles:putAttribute name="cmdUpload" value="/example/noneupload.action"/>

<tiles:putAttribute name="cmdBatch" value="/batch.action"/>

<tiles:putAttribute name="batchBeanName"
	value="com.congxing.example.simplebatch.view.NoneAdditionBatchBean"/>

<tiles:putAttribute name="format">
店员号码|所属代理商编号|店员姓名
</tiles:putAttribute>

<tiles:putAttribute name="memo">
此操作可以批量导入空充店员信息
</tiles:putAttribute>

<tiles:putAttribute name="example">
13512345678|SFLVS00206|张三通讯店
13512345679|SFLVS00106|李四
</tiles:putAttribute>

</tiles:insertTemplate>



