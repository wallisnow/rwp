<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/common/taglibs.jsp" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    
    <title><s:text name="title"/></title>
	<%@ include file="/common/header.jsp"%>
	
	<script type="text/javascript">

	cmdAdd="/system/sysdp/add.action";
	cmdDelete="/system/sysdp/delete.action";
	cmdEdit="/system/sysdp/edit.action";


	$(function(){
		initIframePageEnv("dialog");
		
		setTimeout(initSelectEvent, 100);
	});

	function doTest(dpId, dpName){
		$.dialog.open(ctx + "/system/sysdp/tree.action", {
			id:"selectSysDp", 
			title: "组织选择", 
			width: 900, 
			height: 500, 
			close: function(){
				var checkItems = art.dialog.data("selectkey");
				art.dialog.removeData("selectkey");
				if(typeof(checkItems) != "undefined"){
					if(checkItems == "-1"){
						
					}else{
						alert(checkItems.length);
						for(var i = 0; i < checkItems.length; i++){
							alert(checkItems[i].dpId + "|" + checkItems[i].dpName);
						}
					}
				}	
			},
			lock:true,
			drag:false
			}, 
			false
		);
	}
	
	</script>
  </head>
  
<body scroll="no">

<form action="${ctx}/system/sysdp/select.action" method="post" id="queryForm">

<s:hidden name="pageNo" value="%{paramsMap.pageNo}"/>
<s:hidden name="pageSize" value="%{paramsMap.pageSize}"/>

<div class="pageMsgInfo">
</div>

<div class="searchPageHeader">
	
		<div class="searchContent">
			<p>
				<label><s:text name="_seq_dpId"/>:</label>
				<s:textfield name="_seq_dpId" value="%{paramsMap._seq_dpId}"/>
			</p>
			<p>
				<label><s:text name="_sk_dpName"/>:</label>
				<cx:textfield name="_sk_dpName" value="%{paramsMap._sk_dpName}"/>
			</p>
			<p>
				<label><s:text name="_seq_dpCode"/>:</label>
				<cx:textfield name="_seq_dpCode" value="%{paramsMap._seq_dpCode}"/>
			</p>
		</div>
		
		<div class="searchBar">
			<a href="javascript:void(0);" onclick="doQuery();return false;" class="linkbutton" data-options="iconCls:'icon-query'"><s:text name="query"/></a>
			<a href="javascript:void(0);" onclick="doReset();return false;" class="linkbutton" data-options="iconCls:'icon-reset'"><s:text name="reset"/></a>
		</div>

</div>

<div class="searchPageContent">
	<div class="cssTable">
	<table class="list" nowrapTD="false" style="width:100%;">
		<thead>
			<tr>
				<th><input type="checkbox" name="chooseAll" onclick="checkAll()"/></th>
				<th><s:text name="dpId"/></th>
				<th><s:text name="dpName"/></th>
				<th><s:text name="parentDpId"/></th>
				<th><s:text name="dpFullName"/></th>
			</tr>
		</thead>
		<tbody>
		<c:forEach var="item" items="${page.datas}" >
			<tr PK="${item.dpId}|${item.dpName}">
				<td><input type="checkbox" name="PK" value="${item.dpId}"/></td>
				<td><c:out value="${item.dpId}"/></td>
				<td><c:out value="${item.dpName}"/></td>
				<td><c:out value="${item.parentDpId}"/></td>
				<td><c:out value="${item.dpFullName}"/></td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	</div>
	<div class="panelBar">
		<div class="pages">
			<span>每页显示</span>
			<cx:select list='{"10", "20", "50", "100"}' cssClass="combox" name="newPageSize" onchange="changePageSize()" value="%{paramsMap.newPageSize}"/>
			<span>条，共${!empty page ? page.totalCount:0}条</span>
		</div>
		<cx:Page page="${page}"/>
	</div>
</div>

</form>

</body>
</html>
