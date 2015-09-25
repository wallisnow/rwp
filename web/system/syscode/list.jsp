<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/common/taglibs.jsp" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    
    <title><s:text name="title"/></title>
	<%@ include file="/common/header.jsp"%>

	<script type="text/javascript">

	cmdAdd="/system/syscode/add.action";
	cmdEdit="/system/syscode/edit.action";


	$(function(){
		initPageEnv("list");
	});

	function doAddSyscode(){
		var url = addParam(ctx + cmdAdd, "_seq_type", "${paramsMap._seq_type}");
		window.location = url;
	}
	
	function doDeleteItems(){
		var PKS = findPKS();
		var url = "${ctx}" + "/system/syscode/deleteJson.action";
		if(PKS.length < 1){
			alert("请选择删除记录");
			return;
		}
		var params = {"PK": PKS.join("|")};
		$.getJSON(url, params, function callback(datas){
			if("success" == datas.status){
				window.location.reload();
			}
		});
	}
	
	</script>
  </head>
  
<body>

<form action="${ctx}/system/syscode/list.action" method="post" id="queryForm">

<s:hidden name="_seq_type" value="%{paramsMap._seq_type}"/>

<div class="searchPageHeader">
	
		<div class="searchContent">
			<p>
				<label><s:text name="_seq_kind"/>:</label>
				<cx:textfield name="_seq_kind" value="%{paramsMap._seq_kind}"/>
			</p>
			<p>
				<label><s:text name="_sk_kindname"/>:</label>
				<cx:textfield name="_sk_kindname" value="%{paramsMap._sk_kindname}"/>
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
			<li><a class="add" href="Javascript:doAddSyscode();"><span>添加</span></a></li>
			<li><a class="edit" href="Javascript:doEditList();"><span>修改</span></a></li>
			<li><a class="delete" href="Javascript:doDeleteItems();"><span>删除</span></a></li>
		</ul>
	</div>
	<div class="tableBar">
	<table class="list">
		<thead>
			<tr>
				<th><input type="checkbox" name="chooseAll" onclick="checkAll()"/></th>
				<th><s:text name="type"/></th>
				<th><s:text name="name"/></th>
				<th><s:text name="kind"/></th>
				<th><s:text name="kindname"/></th>
				<th><s:text name="memo"/></th>
			</tr>
		</thead>
		<tbody>
		<c:forEach var="item" items="${page.datas}" >
			<tr PK="${item.seqId}">
				<td align="center"><input type="checkbox" name="PK" value="${item.seqId}"/></td>
				<td><c:out value="${item.type}"/></td>
				<td><c:out value="${item.name}"/></td>
				<td><c:out value="${item.kind}"/></td>
				<td><c:out value="${item.kindname}"/></td>
				<td><c:out value="${item.memo}"/></td>
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
