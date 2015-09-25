<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ include file="/common/taglibs.jsp" %>
<%@ include file="/common/header.jsp"%>
<%-- 必填项，设置引用的批量操作模板 --%>
<tiles:insertTemplate template="/common/batch/layoutxls.jsp">

<script type="text/javascript">
	$(document).ready(function(){
		$("batch").each(function(){
			$(this).height("140px");
		});
	});
</script>

<%-- 必填项，设置页面标题 --%>
<tiles:putAttribute name="title">
用户角色关系导入
</tiles:putAttribute>


<%-- 必填项，上传url的设置，需要在struts中配置 --%>
<tiles:putAttribute name="cmdUpload" value="/system/userofrole/upload.action"/>



<%-- 必填项，批处理url的设置，一般不用改，就是/batch.action --%>
<tiles:putAttribute name="cmdBatch" value="/batch.action"/>



<%-- 可选项，返回按钮url设置 --%>
<tiles:putAttribute name="cmdReturn" value="/system/userofrole/list.action"/>


<tiles:putAttribute name="batchBeanName" value="com.congxing.system.userofrole.view.UserOfRoleBatchBean"/>



<tiles:putAttribute name="batchParamClassName" value="com.congxing.system.userofrole.model.UserOfRoleVO"/>

 
<%-- 必填项，导入文件每行的格式说明 --%>
<tiles:putAttribute name="format">
EXCEL表头列说明：
用户登录号|角色名称|开始日期|结束日期
</tiles:putAttribute>

<%-- 必填项，批处理操作的说明 --%>
<tiles:putAttribute name="memo">
开始日期、结束日期格式：yyyyMMdd
</tiles:putAttribute>

<%-- 必填项，文件内容示例--%>
<tiles:putAttribute name="example">
<a href="${ctx}/system/userofrole/userofrole.xls">用户角色关系导入模板</a>
</tiles:putAttribute>


</tiles:insertTemplate>



