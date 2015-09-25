<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/common/taglibs.jsp" %>
<c:set var="isEditable" value="${'TRUE' eq ISEDIT}" scope="request"/>
<c:set var="isCreative" value="${'TRUE' eq ISNEW}" scope="request"/>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    
    <title><s:text name="title"/></title>
	<%@ include file="/common/header.jsp"%>
	<script src="${ctx}/js/dwzjs/dwz.pagination.js" type="text/javascript"></script>
	<script src="${ctx}/js/page.util.js" type="text/javascript"></script>

	<script type="text/javascript">


	$(function(){
		$(window).resize(function(){
			initSysDpSize();
		});

		setTimeout(function(){
			initSysDpUI();
			initSysDpSize();
		}, 50);
	});

	function initSysDpUI(){
		var treeXML = "${request.treeXML}";
		$("#treePanel").append(treeXML);
		$("#treePanel > ul:first").addClass("tree treeFolder");
		$("ul.tree").jTree();

		$("a").each(function(){
			$(this).click(function(event){
				$this = $(this);
				var url = ctx + "/noticemgmt/notice/ntlist.action";
				url = addParam(url, "_sk_parentDpId", $this.attr("PK"));
				window.parent.document.getElementById("contentFrame").src = url;;
			});
		});
	}
	function initSysDpSize(){
		var pageH = getTotalHeight();
		var treePanelH = pageH;
		$("#treePanel").height(treePanelH);
	}
	
	function oncheck(){
		//var json = arguments[0];
		//var checkValue = "";//确定选择值
		//$("div.checked").each(function(){
		//	$this = $(this);
		//	checkValue+=$this.find("input:first").val() + "|";
		//});
		//alert(checkValue);
		//var indeterminateValue = "";//不确定值
		//$("div.indeterminate").each(function(){
		//	$this = $(this);
		//	indeterminateValue+=$this.find("input:first").val() + "|";
		//});
		//alert(indeterminateValue);
	}

	function doSure(){
		var checkItems = new Array();//确定选择值
		$("div.checked").each(function(){
			$this = $(this);
			var name = $this.find("input:first").attr("name");
			var value = $this.find("input:first").val();
			var obj = {"dpId" : value, "dpName" : name};
			checkItems.push(obj);
		});
		if(typeof(checkItems) != "undefined"){
			art.dialog.data("selectkey", checkItems);
		}else{
			art.dialog.data("selectkey", "-1");
		}
		art.dialog.close();
	}
	function doClear(){
		art.dialog.data("selectkey", "-1");
		art.dialog.close();
	}
	function doClose(){
		art.dialog.close();
	}

	</script>

</head>
  
<body scroll="no" width="100%">

<div id="treePanel" style="background:#FFF; OVERFLOW-Y:auto; border:solid 1px #b8d0d6; text-align:left; height:100%;">

</div>

</body>
</html>
