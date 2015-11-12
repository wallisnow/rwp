<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>

<title><s:text name="title" /></title>
<%@ include file="/common/header.jsp"%>


<script type="text/javascript">
	cmdAdd = "/system/user/add.action";
	cmdDelete = "/system/user/delete.action";
	cmdEdit = "/system/user/edit.action";
	cmdLoad = "/system/user/batch.jsp";

	$(function() {
		initPageEnv("list");
	});

	function queryByKey(formId){
		if(typeof(formId) == "undefined"){
			formId = $(document.forms[0]).attr("id");
		}
		if(typeof(formId) == "undefined"){
			alert("请确认formId是否存在");
			return;
		}
		if(formCheck(formId)) {
			$("form").find("#pageNo").val("1");
			var form = document.forms[formId];
			form.submit();
		} 
	}
</script>
</head>

<body>

	<h2 class="contentTitle">
		<s:text name="title" />
		<cx:Msg />
	</h2>

	<form action="${ctx}/system/generalconfig/queryByKey.action" method="post" id="listForm">
		<div class="pageMsgInfo">
			<cx:Msg />
		</div>

		<div class="searchPageHeader">
			<div class="searchContent">	
					<c:if test="${not empty  paramsMap.keys}">
						<c:forEach items="${paramsMap.keys}" var="item">						
							<p>
								<label><c:out value="${item.COLUMN_COMMENT}" />:</label>
								<cx:textfield rows="1" name="key_%{paramsMap.key}" value="%{item.COLUMN_COMMENT}"/>
							</p>
						</c:forEach>
					</c:if>				
			</div>

			<div class="searchBar">
				<a href="javascript:void(0);" class="linkbutton" data-options="iconCls:'icon-query'"><s:text name="query" /></a>
				<a href="javascript:void(0);" class="linkbutton" data-options="iconCls:'icon-reset'"><s:text name="reset" /></a>
			</div>
		</div>

		<div class="searchPageContent">
			<div class="tableBar">
				<table class="list nowrap">
					<thead>
						<tr>
							<c:forEach items="${paramsMap.headers}" var="column">
       						 <th><c:out value="${column.COLUMN_COMMENT}" /></th>
      						</c:forEach>
						</tr>
					</thead>
					<tbody>
	      					<tr>
					          	<td></td>				        		
					      	</tr>
					</tbody>
				</table>
			</div>
			<div class="panelBar">
				<cx:Page page="${page}" />
			</div>
		</div>

	</form>

</body>
</html>
