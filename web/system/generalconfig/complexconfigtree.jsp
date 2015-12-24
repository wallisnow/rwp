<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<c:set var="isEditable" value="${'TRUE' eq ISEDIT}" scope="request" />
<c:set var="isCreative" value="${'TRUE' eq ISNEW}" scope="request" />

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>

<title><s:text name="title" /></title>
<%@ include file="/common/header.jsp"%>
<script type="text/javascript" src="${ctx}/js/dragdrop/shared/js/sprintf.js"></script>
<script type="text/javascript" src="${ctx}/js/dragdrop/shared/js/EventHelpers.js"></script>
<script type="text/javascript" src="${ctx}/js/dragdrop/shared/js/DragDropHelpers.js"></script>
<script type="text/javascript" src="${ctx}/js/dragdrop/dragObjectWithEvent.js"></script>
<script type="text/javascript" src="${ctx}/js/editable/jquery.editable.js"></script>
<script type="text/javascript" src="${ctx}/js/editable/jquery.editable.min.js"></script>
<script type="text/javascript">
	$(function() {

		initPageUI();
		var treePanelH = $(window).height();
		$("#treePanel").height(treePanelH);
		var treeXML = "${paramsMap.treeXML}";
		//alert(treeXML);
		$("#treePanel").append(treeXML);
		$("#treePanel > ul:first").addClass("tree treeFolder expend");
		$("#treePanel a").each(
				function() {
					$this = $(this);
					var actionUrl = "${ctx}/system/generalconfig/view.action?PK="
							+ $this.attr("PK");
					$this.attr("href", "#");
					//$this.attr("target", "contentFrame");
				});
		$("ul.tree").jTree();

		$("a[target=contentFrame]").each( function() {
													$(this).click(function(event) {
																		$this = $(this);
																		window.parent.document.getElementById("contentFrame").src = $this.attr("href");
																		});
														});
		
		$(".tree.treeFolder.expend ul li").hide();
		$(".collapsable").attr('class','expandable');
		$(".folder_collapsable").attr('class','folder_expandable');
		
		$(".tree li a").click(function(){
			if($(this).data("clicked",true)){
				
				$(this).parent().parent().find("ul li").show();
				$(this).parent().parent().find("ul li a").attr("id","toDrag");
				$(this).parent().parent().find("ul li a").attr("draggable","true");
				//$(this).parent().parent().find("ul").sortable();
				$(this).parent().find(".expandable").attr('class','collapsable');
				$(this).parent().find(".folder_expandable").attr('class','folder_collapsable');
			}
		});
		
		
	});

	function oncheck() {
		var json = arguments[0];
		var checkValue = "";
		$(json.items).each(function(i) {
			checkValue += this.value + "|";
		});
		alert(checkValue);
		//alert($("#treePanel").html());
		var checkValue = "";//确定选择值
		$("div.checked").each(function() {
			$this = $(this);
			checkValue += $this.find("input:first").val() + "|";
		});
		alert(checkValue);
		var indeterminateValue = "";//不确定值
		$("div.indeterminate").each(function() {
			$this = $(this);
			indeterminateValue += $this.find("input:first").val() + "|";
		});
		alert(indeterminateValue);
	}
	
</script>
</head>

<body scroll="no">
	
	<div id="treePanel" style="background: #FFF; OVERFLOW-Y: auto; border: solid 1px #b8d0d6; text-align: left; float:left; width:25%; height: 100%;">
	</div>
	<form action="">
		<div id="configuration" style="display: inline-block;  OVERFLOW-Y: auto; float:left; margin-left: auto; margin-right: auto; width:850px;">
			<div id="areaConfiguration" style="display: inline-block; float:left; margin-left: auto; margin-right: auto; width:550px; height: 550px; border: solid 5px #b8d0d6;">
				<label> columns: </label>
				
				<div id="clm" style="position: relative; left: 20px; width:500px; height: 250px; border: solid 1px #b8d0d6;">
				</div>
					
			
				<label> conditions: </label>	
				
				<div id="cdt" style="position: relative;left: 20px; width:500px; height: 250px; border: solid 1px #b8d0d6;">
				</div>
			</div>
			<div id="areaExecution" style="display: inline-block; float:left; margin-left: 30px; width:240px; height: 550px; border: solid 5px #b8d0d6;">
				<label> SQL: </label>
				<div id="resultStatement" style="position: relative; left: 10px; width:220px; height: 450px; border: solid 1px #b8d0d6;">
				</div>
				
				<label> menu: </label>
				<div id="entryExecution" style="position: relative; left: 10px; width:220px; height: 40px; border: solid 1px #b8d0d6;">
					<input type="submit" value="submit"/>
					<input type="button" value="execute"/>
				</div>
			</div>
		</div>
	</form>
	<!-- 比较器模板 -->
	<script id="comparatorList" type="text/x-jquery-tmpl">
                <option value="1">is equal to</option>
                <option value="2">is not equal to</option>
                <option value="3">like</option>
                <option value="4">contains</option>
				<option value="5">does not contain</option>
				<option value="6">start with</option>
				<option value="7">does not start with</option>
				<option value="8">in sub query</option>
	</script>
	
	<!-- 逻辑条件模板 -->
	<script id="andorbox" type="text/x-jquery-tmpl">
                <option value="0">and</option>
                <option value="1">or</option>
	</script>
	
</body>
</html>
