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
		var treeXML = "${paramsMap.treeXML}";
		$("#treePanel").append(treeXML);
		$("#treePanel > ul:first").addClass("tree treeFolder expend");
		$("#treePanel a").each(function(){
			$this = $(this);
			var actionUrl = "${ctx}/system/menu/view.action?PK=" + $this.attr("PK");
			$this.attr("href", actionUrl);
			$this.attr("target", "contentFrame")
		});
		$("ul.tree").jTree();
		
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
		//alert($("#treePanel").html());
		var checkValue = "";//确定选择值
		$("div.checked").each(function(){
			$this = $(this);
			checkValue+=$this.find("input:first").val() + "|";
		});
		alert(checkValue);
		var indeterminateValue = "";//不确定值
		$("div.indeterminate").each(function(){
			$this = $(this);
			indeterminateValue+=$this.find("input:first").val() + "|";
		});
		alert(indeterminateValue);
	}

	</script>

</head>
  
<body scroll="no">

<div id="treePanel" style="background:#FFF; OVERFLOW-Y:auto; border:solid 1px #b8d0d6; text-align:left; height:100%;">

</div>

</body>
</html>
