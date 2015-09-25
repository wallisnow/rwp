<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/common/taglibs.jsp" %>
<c:set var="ISEDIT" value="${'TRUE' eq paramsMap.ISEDIT}" />

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    
    <title><s:text name="title"/></title>
	<%@ include file="/common/header.jsp"%>

	<script type="text/javascript">
	<!-- 
	cmdSave="/rulemgt/dbconfig/save.action";
	cmdEdit="/rulemgt/dbconfig/edit.action";
	cmdReturn="/rulemgt/dbconfig/list.action";
	
	$(function(){
		initPageEnv("content");
		contentFormControl();
	});
	function testDbConnectionJson(){
			var url = ctx + "/rulemgt/dbconfig/testDbConnctionJson.action";
			var driverClass = $("select[name='driverClass']").val();
			if(driverClass < 1 ){
				alert("请选择驱动包名！");
				return;
			}
			var jdbcUrl = $("input[name='jdbcUrl']").val();
			if(jdbcUrl < 1 ){
				alert("请输入数据库连接串！");
				return;
			}
			var user = $("input[name='user']").val();
			if(user < 1 ){
				alert("请输入用户名！");
				return;
			}
			var password = $("input[name='password']").val();
			if(password < 1 ){
				alert("请输入用户密码！");
				return;
			}
			var params = {"driverClass" : driverClass,"jdbcUrl" : jdbcUrl,"user" : user,"password" : password};
			$.getJSON(url, params, function(result){
				if("success" == result.status){
					if(result.message == "SUCCESS"){
						alert("测试成功");
					}else{
						alert("测试失败："+result.message);
					}
				}else{
					alert(result.message);
				}
			});
	}
	-->
	</script>

</head>
  
<body scroll="no">

<h2 class="contentTitle">数据源配置<cx:Msg/></h2>

<form method="post" id="contentForm" action="">
<input type="hidden" name="ISNEW" value="${paramsMap.ISNEW}"/>
<input type="hidden" name="ISEDIT" value="${paramsMap.ISEDIT}"/>
<input type="hidden" name="dbId" value="${paramsMap.dbId}"/>

<div class="formPageHeader">
	
	<div class="formContent">
		<p>
			<label><s:text name="dbName"/></label>
			<input type="text" name="dbName" value="${paramsMap.dbName}" class="required"/>
		</p>
		<p class="nowrap">
			<label><s:text name="driverClass"/></label>
			<cx:select name="driverClass" value="%{paramsMap.driverClass}" list="$DRIVERCLASS" emptyOption="true" headerKey="" headerValue="请选择..." accesskey="required"/>
			
		</p>
		<p class="nowrap">
			<label><s:text name="jdbcUrl"/></label>
			<input type="text" name="jdbcUrl" value="${paramsMap.jdbcUrl}" class="required" style="width:30%;"/>
		</p>
		<p class="nowrap">
			<label><s:text name="user"/></label>
			<input type="text" name="user" value="${paramsMap.user}" class="required" style="width:30%;"/>
		</p>
		<p class="nowrap">
			<label><s:text name="password"/></label>
			<input type="password" name="password" value="${paramsMap.password}" class="required" style="width:30%;"/>
		</p>
		<p class="nowrap">
			<label><s:text name="dbDesc"/></label>
			<s:textarea name="dbDesc" rows="2" cols="80" value="%{paramsMap.dbDesc}" cssClass="required"/>
		</p>
	</div>
	
	<div class="formBar">
		<a href="javascript:void(0);" onclick="testDbConnectionJson();return false;" class="linkbutton" data-options="iconCls:'icon-help'">测试</a>
		<c:if test="${ISEDIT}">
		<a href="javascript:void(0);" onclick="doSave();return false;" class="linkbutton" data-options="iconCls:'icon-save'"><s:text name="save"/></a>
		</c:if>
		<c:if test="${!ISEDIT}">
		<a href="javascript:void(0);" onclick="doEditContent();return false;" class="linkbutton" data-options="iconCls:'icon-edit'"><s:text name="edit"/></a>
		</c:if>
		<a href="javascript:void(0);" onclick="doReturn();return false;" class="linkbutton" data-options="iconCls:'icon-return'"><s:text name="return"/></a>
	</div>

</div>

</form>

</body>
</html>
