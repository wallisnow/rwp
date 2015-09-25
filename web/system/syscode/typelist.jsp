<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/common/taglibs.jsp" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    
    <title><s:text name="title"/></title>
	<%@ include file="/common/header.jsp"%>

	<script type="text/javascript">

	cmdAdd="/system/syscode/typeadd.action";
	cmdDelete="/system/syscode/typedelete.action";
	cmdEdit="/system/syscode/typeedit.action";


	$(function(){
		initPageEnv("list");
		$("tbody tr").each(function(){
			$(this).dblclick(function(){
				showTypeList($(this).attr("type"));
			});
		});
	});

	function showTypeList(type){
		if(typeof(type) != "undefined"){
			window.location = ctx + "/system/syscode/list.action?_seq_type=" + type;
		}
	}
	
	</script>
  </head>
  
<body scroll="no">

<form action="${ctx}/system/syscode/typelist.action" method="post" id="queryForm">

<div class="searchPageHeader">
	
	<div class="searchContent">
		<p>
			<label><s:text name="_sk_type"/>:</label>
			<s:textfield name="_sk_type" value="%{paramsMap._sk_type}"/>
		</p>
		<p>
			<label><s:text name="_sk_name"/>:</label>
			<cx:textfield name="_sk_name" value="%{paramsMap._sk_name}"/>
		</p>
	</div>
	
	<div class="searchBar">
		<a href="javascript:void(0);" onclick="doQuery();return false;" class="linkbutton" data-options="iconCls:'icon-query'"><s:text name="query"/></a>
		<a href="javascript:void(0);" onclick="doReset();return false;" class="linkbutton" data-options="iconCls:'icon-reset'"><s:text name="reset"/></a>
	</div>

</div>

<div class="searchPageContent">
	<div class="panelBar">
		<ul class="toolBar">
			<li><a class="add" href="Javascript:doAdd();"><span>添加</span></a></li>
			<li><a class="edit" href="Javascript:doEditList();"><span>修改</span></a></li>
			<li><a class="delete" href="Javascript:doDelete();"><span>删除</span></a></li>
		</ul>
	</div>
	<div class="tableBar">
	<table class="list">
		<thead>
			<tr>
				<th><input type="checkbox" name="chooseAll" onclick="checkAll()"/></th>
				<th><s:text name="seqId"/></th>
				<th><s:text name="type"/></th>
				<th><s:text name="name"/></th>
			</tr>
		</thead>
		<tbody>
		<c:forEach var="item" items="${page.datas}" >
			<tr PK="${item.seqId}" type="${item.type}">
				<td><input type="checkbox" name="PK" value="${item.seqId}"/></td>
				<td><c:out value="${item.seqId}"/></td>
				<td><c:out value="${item.type}"/></td>
				<td><c:out value="${item.name}"/></td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	</div>
	<div class="panelBar">
		<cx:Page page="${page}"/>
	</div>
</div>

</form>

</body>
</html>
