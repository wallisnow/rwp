<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ include file="/common/taglibs.jsp" %>
<%-- 必填项，设置引用的批量操作模板 --%>
<tiles:insertTemplate template="/common/batch/layout.jsp">


<%-- 必填项，设置页面标题 --%>
<tiles:putAttribute name="title">
文件上传--简单上传示例
</tiles:putAttribute>



<%-- 必填项，上传url的设置，需要在struts中配置 --%>
<tiles:putAttribute name="cmdUpload" value="/example/simpleupload.action"/>



<%-- 必填项，批处理url的设置，一般不用改，就是/batch.action --%>
<tiles:putAttribute name="cmdBatch" value="/batch.action"/>



<%-- 可选项，返回按钮url设置 --%>
<tiles:putAttribute name="cmdReturn" value="/simplebatch/show.action"/>



<%-- 必填项，设置批处理类的全名，该类应继承BaseBatchTaskBean或其子类 --%>
<tiles:putAttribute name="batchBeanName"
	value="com.congxing.example.simplebatch.view.SimpleBatchBean"/>



<%-- 可选项，设置用于获取页面参数的实体类全名，该类应实现BatchParam接口 --%>
<tiles:putAttribute name="batchParamClassName"
	value="com.congxing.example.simplebatch.view.SimpleBatchParam"/>



<%-- 可选项，文件重命名时的前缀，默认是当前登录用户的用户名 --%>
<tiles:putAttribute name="prefix" value="RRR1234"/>



<%-- 可选项，文件重命名类型，默认是rename。
可选值为:
  unchange   保持文件原名不变
    rename   以“前缀_timing”方式重命名
     union   以“前缀_timing_文件原名”方式重命名
 --%>
<tiles:putAttribute name="style" value="rename"/>




<%-- 可选项，在此处编写需要额外指定的页面内容，如需额外采集的参数等等 --%>
<tiles:putAttribute name="content">
<div class="searchContent">
	<p>
		<label><s:text name="_se_condition1"/>:</label>
		<cx:textfield name="_se_condition1" value="%{paramsMap._se_condition1}"/>
	</p>
	<p>
		<label><s:text name="_dle_condition2"/>:</label>
		<cx:textfield name="_dle_condition2" value="%{paramsMap._dle_condition2}" onclick="WdatePicker();"/>
	</p>
	<p>
		<label><s:text name="_ngt_condition3"/>:</label>
		<cx:select list="$STATUS" emptyOption="true" headerKey="" headerValue="" name="_ngt_condition3" value="%{paramsMap._ngt_condition3}"/>
	</p>
</div>
</tiles:putAttribute>



<%-- 必填项，导入文件每行的格式说明 --%>
<tiles:putAttribute name="format">
店员号码|所属代理商编号|店员姓名
</tiles:putAttribute>



<%-- 必填项，批处理操作的说明 --%>
<tiles:putAttribute name="memo">
此操作可以批量导入空充店员信息
此操作可以批量导入空充店员信息
此操作可以批量导入空充店员信息
</tiles:putAttribute>



<%-- 必填项，文件内容示例--%>
<tiles:putAttribute name="example">
13512345678|SFLVS00206|张三通讯店
13512345679|SFLVS00106|李四
13512345678|SFLVS00206|张三通讯店
13512345679|SFLVS00106|李四
</tiles:putAttribute>



<%-- 可选项，在页面最后位置增加内容，一般不用 --%>
<tiles:putAttribute name="end">
</tiles:putAttribute>

</tiles:insertTemplate>



