<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/common/taglibs.jsp" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    
    <title><s:text name="title"/></title>
	<%@ include file="/common/header.jsp"%>
	
	<script type="text/javascript">

	cmdAdd = "/system/role/add.action";
	cmdDelete = "/system/role/delete.action";
	cmdEdit = "/system/role/edit.action";


	$(function(){
		initPageEnv("list");
	});

	function doAssign(){
		var selectCount = 0;
		var selectPK = "";
		$("#queryForm").find("input[name=PK]").each(function(){
			$chbox = $(this);
			if($chbox.attr("checked")){
				selectCount += 1;
				selectPK = $chbox.val();
			}
		});
		if(selectCount < 1){
			$.dialog.alert("请检查选择记录");
			return;
		}
		if(selectCount > 1){
			$.dialog.alert("只能选择一个角色进行赋权操作");
			return;
		}
		var url = ctx + "/system/role/showroleofmenu.action";
		$("#queryForm").action = url;
		url = addParam(url, 'roleId', selectPK);
		window.location = url;
	}
	function doAddRole(){
		var addUrl = ctx + cmdAdd;
		art.dialog.open(addUrl, {
			id:"addRole", 
			title: "新增角色信息", 
			width: 900, 
			height: 500, 
			close: function(){
				window.location.reload(true);
			},
			lock:true,
			drag:false
			}, 
			false);
	}
	function doEditListRole(){
		var selectCount = $("tbody").find(".selected").length;
		if(selectCount < 1){
			$.dialog.alert("请检查选择记录");
			return;
		}
		if(selectCount > 1){
			$.dialog.alert("只能选择一个角色进行修改操作");
			return;
		}
		var selectPK =  $("tbody").find(".selected").attr("PK");
		
		var editUrl = ctx + cmdEdit;
		editUrl = addParam(editUrl, "PK", selectPK);
		art.dialog.open(editUrl, {
			id:"addRole", 
			title: "修改角色信息", 
			width: 900, 
			height: 500, 
			close: function(){
				window.location.href = ctx + "/system/role/list.action";
			},
			lock:true,
			drag:false
			}, 
			false);
	}
	function doAssignRole(roleId){
		var pageH = $(window).height();
		
		$(window.parent.document).find("#roleMenu").height(pageH).attr("src", ctx + "/system/role/menutree.action?roleId=" + roleId);
	}
	</script>
  </head>
  
<body>

<h2 class="contentTitle"><s:text name="title"/></h2>

<form action="${ctx}/system/role/list.action" method="post" id="queryForm" onsubmit="return formCheck();">

<div class="searchPageHeader">
	
	<div class="searchContent">
		<p>
			<label><s:text name="_seq_roleId"/>:</label>
			<cx:textfield name="_seq_roleId" value="%{paramsMap._seq_roleId}"/>
		</p>
		<p>
			<label><s:text name="_sk_roleName"/>:</label>
			<cx:textfield name="_sk_roleName" value="%{paramsMap._sk_roleName}"/>
		</p>
		<p>
			<label><s:text name="_seq_status"/>:</label>
			<cx:select list="$TORF" name="_seq_status" cssClass="combox" emptyOption="true" headerKey="" headerValue="请选择" value="%{paramsMap._seq_status}"/>
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
			<li><a class="add" href="Javascript:doAddRole();"><span>添加</span></a></li>
			<li><a class="edit" href="Javascript:doEditListRole();"><span>修改</span></a></li>
		</ul>
	</div>
	<div class="tableBar">
	<table class="list" width="150%">
		<thead>
			<tr>
				<th><s:text name="roleId"/></th>
				<th><s:text name="roleName"/></th>
				<th><s:text name="roleDesc"/></th>
				<th><s:text name="status"/></th>
				<th><s:text name="beginTime"/></th>
				<th><s:text name="endTime"/></th>
				<th>操作</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach var="item" items="${page.datas}" >
			<tr PK="${item.roleId}">
				<td><c:out value="${item.roleId}" /></td>
				<td><c:out value="${item.roleName}"/></td>
				<td><c:out value="${item.roleDesc}"/></td>
				<td><cx:Code2Name definition="$TORF" value="${item.status}"/></td>
				<td><cx:Code2Name definition="#yyyy-MM-dd HH:mm:ss" value="${item.beginTime}"/></td>
				<td><cx:Code2Name definition="#yyyy-MM-dd HH:mm:ss" value="${item.endTime}"/></td>
				<td align="center">
					<a class="btnRel" href="javascript:void(0);" onclick='doAssignRole("${item.roleId}");'>权限设置</a>
				</td>
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
