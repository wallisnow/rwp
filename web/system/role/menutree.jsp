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
	cmdSave="/system/role/save.action";
	cmdEdit="/system/role/edit.action";
	cmdReturn="/system/role/list.action";
	
	$(function(){
		$(window).resize(function(){
			initCurrentLayout();
		});
	
		setTimeout(function(){
			initCurrentPageUI();
			initCurrentLayout();
		}, 50);
	});
	
	function initCurrentLayout(){
		var pageW = $(window).width();
		var pageH = $(window).height();
		var panelTW = pageW - 10;
		$("div.panel:eq(0)").width(panelTW * 0.6);
		$("div.panel:eq(1)").width(panelTW * 0.4);
		var headerH = $("div.panelHeader").outerHeight(true);
		var footerH = $("div.panelFooter").outerHeight(true);
		var titleH = $("h2.contentTitle").outerHeight(true);
		$("#pageTree, #controlTree").height(pageH - headerH - footerH - titleH - 10);
	}
	
	function initCurrentPageUI(){
		initPageUI();
		var roleId = $("input[name='roleId']").val();
		if(roleId.toString().length > 0){
			var url = ctx + "/system/role/queryRoleMenuPageJson.action";
			var params = {"roleId" : roleId};
			var result = ajaxJsonSyn(url, params);
			if("success" == result.status){
				$("#pageTree").html(result.message);
				$("#pageTree > ul:first").addClass("tree treeFolder treeCheck expend");
				
				$("#pageTree a").each(function(){
					var chked = eval($(this).attr("chked"));
					if(chked){
						$(this).attr("checked", true);
					}
				});
				
				$("#pageTree>ul.tree").jTree();
				
				$("ul a").each(function(){
					$(this).click(function(event){
						var $this = $(this);
						var menuId = $this.attr("value");
						var thUrl = ctx + "/system/role/queryRoleMenuControlJson.action";
						var thParams = {"roleId" : roleId, "menuId" : menuId};
						var thResult = ajaxJsonSyn(thUrl, thParams);
						
						if("success" == thResult.status){
							$("#controlTree").html(thResult.message);
							$("#controlTree > ul:first").addClass("tree treeFolder treeCheck expend");
							
							$("#controlTree a").each(function(){
								var chked = eval($(this).attr("chked"));
								if(chked){
									$(this).attr("checked", true);
								}
							});
							
							$("#controlTree>ul.tree").jTree();
						}else{
							alert(result.message);
						}
						event.stopPropagation();
					});
				});
			}else{
				alert(result.message);
			}
		}
	}
	
	function doSavePage(){
		var addMenus = new Map();//增加菜单
		var delMenus = new Map();//删除菜单
		//确定选择值
		$("#pageTree div.checked").each(function(){
			var menuId = $(this).find("input").val();
			addMenus.put(menuId, menuId)
		});
		//不确定值
		$("#pageTree div.indeterminate").each(function(){
			var menuId = $(this).find("input").val();
			addMenus.put(menuId, menuId)
		});
		$("#pageTree").find("input").each(function(){
			var menuId = $(this).val();
			if(!addMenus.containsKey(menuId)){
				delMenus.put(menuId, menuId)
			}
		});
		doSaveMenu(addMenus, delMenus);
	}
	
	function doSaveControl(){
		var addMenus = new Map();//增加菜单
		var delMenus = new Map();//删除菜单
		//确定选择值
		$("#controlTree div.checked").each(function(){
			var menuId = $(this).find("input").val();
			addMenus.put(menuId, menuId)
		});
		//不确定值
		$("#controlTree div.indeterminate").each(function(){
			var menuId = $(this).find("input").val();
			addMenus.put(menuId, menuId)
		});
		$("#controlTree").find("input").each(function(){
			var menuId = $(this).val();
			if(!addMenus.containsKey(menuId)){
				delMenus.put(menuId, menuId)
			}
		});
		doSaveMenu(addMenus, delMenus);
	}
	
	function doSaveMenu(addMenus, delMenus){
		var url = ctx + "/system/role/saveRoleOfMenusJson.action";
		var roleId = $("input[name='roleId']").val();
		var params = {"roleId" : roleId, "addMenus" : addMenus.keys().join("|"), "delMenus" : delMenus.keys().join("|")};
		if(roleId.toString().length > 0){
			$.getJSON(url, params, function(data){
				if("success" == data.status){
					art.dialog.tips(data.message, 1.5)
				}else{
					alert(data.message);
				}
			});
		}
	}
	
	-->
	</script>

</head>
  
<body scroll="no">

<form method="post" id="contentForm" action="">

<input type="hidden" name="roleId" value="${paramsMap.roleId}"/>

<div class="searchPageContent">
	<div class="panel" style="float:left; margin:2px;">
		<div class="panelHeader">
			<div class="panelHeaderContent">
				<h1>
					菜单信息
					<a href="javascript:void(0)" class="save" onclick="doSavePage();"></a>
				</h1>
			</div>
		</div>
		<div class="panelContent" id="pageTree">
		</div>
		<div class="panelFooter">
			<div class="panelFooterContent">
			</div>
		</div>
	</div>
	<div class="panel" style="float:left; margin:2px;">
		<div class="panelHeader">
			<div class="panelHeaderContent">
				<h1>
					控制点信息
					<a href="javascript:void(0)" class="save" onclick="doSaveControl();"></a>
				</h1>
			</div>
		</div>
		<div class="panelContent" id="controlTree">
		</div>
		<div class="panelFooter">
			<div class="panelFooterContent">
			</div>
		</div>
	</div>
</div>

</form>

</body>
</html>
