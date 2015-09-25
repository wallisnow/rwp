<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/common/taglibs.jsp" %>
<c:set var="isEditable" value="${'TRUE' eq ISEDIT}" scope="request"/>
<c:set var="isCreative" value="${'TRUE' eq ISNEW}" scope="request"/>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    
    <title><s:text name="title"/></title>
	<%@ include file="/common/header.jsp"%>

	<script type="text/javascript">


	$(function(){
		initPageUI()
		var treePanelH = $(window).height();
		$("#treePanel").height(treePanelH);
		$("a[target=contentFrame]").each(function(){
			$(this).click(function(event){
				$this = $(this);
				window.parent.document.getElementById("contentFrame").src=$this.attr("href");
			});
		});
	});
	function oncheck(){
		var json = arguments[0];
		var checkValue="";
		$(json.items).each(function(i){
			checkValue += this.value + "|"; 
		});
		alert(checkValue);
	}

	</script>

</head>
  
<body scroll="no">

<div id="treePanel" style="background:#FFF; OVERFLOW-Y:auto; border:solid 1px #b8d0d6; text-align:left;">
	<ul class="tree treeFolder expand">
		<li><a href="${ctx}/system/syscode/typelist.action" target="contentFrame">业务参数定义</a>
			<c:if test="${! empty page.datas}">
				<ul>
					<c:forEach var="item" items="${page.datas }">
						<li><a href="${ctx}/system/syscode/list.action?_seq_type=${item.type}" target="contentFrame">${item.name}</a></li>
					</c:forEach>
				</ul>
			</c:if>
		</li>
	</ul>
</div>

</body>
</html>
