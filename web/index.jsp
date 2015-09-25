<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/common/taglibs.jsp" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>

<title>汕头移动数据挖掘平台业务规则库</title>
<%@ include file="/common/header.jsp"%>

<style type="text/css">

</style>
<script type="text/javascript">

$(function(){
	$.getJSON("${ctx}" + "/system/menu/menuJson.action", {"dt" : Math.random()}, function(result){
		if("success" == result.status){
			$("div.accordion").html(result.message);
			$("div.accordionContent").each(function(){
				$(this).find("ul:eq(0)").addClass("tree treeFolder");
			});
			
			$("div.accordionContent a").each(function(){
				var menuUrl = $(this).attr("menuUrl");
				if(typeof(menuUrl) != 'undefined' && menuUrl.toString().length > 0 && menuUrl.toString()  != 'null'){
					var hrefUrl = "${ctx}" + menuUrl;
					$(this).attr("href", hrefUrl);
					$(this).attr("target", "navTab");
					$(this).attr("external", "true");//采用外部方式打开
				}
			});
			
			DWZ.init({debug:false});
			initEnv();
		}else{
			alert(result.message);
		}
	});
});


function doChangePwd(){
	art.dialog.open(ctx + "/system/user/chgpwd.jsp", {
			id:"doChangePwd", 
			title: "修改密码", 
			width: 300, 
			height: 200, 
			close: function(){},
			lock:true,
			drag:false
		}, 
		false
	);
}


function doOut(){
	art.dialog.confirm("是否确认退出?", function(){
			window.location = "${ctx}/system/loginout.action";
		}
	);
}

</script>

</head>

<body scroll="no">
	<div id="layout">
		<div id="header">
			<div class="headerNav">
				<a class="logo" href="#">标志</a>
				<ul class="nav">
					<li>你好,&nbsp;&nbsp;${session.USER.userName},&nbsp;&nbsp;欢迎登录&nbsp;</li>
					<c:if test="${'L' eq sessionScope.USER.userType}">
					<li><a href="javascript:doChangePwd();">修改密码 </a></li>
					</c:if>
					<li><a href="javascript:void(0)" onclick="doOut();">退出</a></li>
				</ul>
			</div>

			<!-- navMenu -->
			
		</div>

		<div id="leftside">
			<div id="sidebar_s">
				<div class="collapse">
					<div class="toggleCollapse"><div></div></div>
				</div>
			</div>
			<div id="sidebar">
				<div class="toggleCollapse"><h2>主菜单</h2><div>收缩</div></div>

				<div class="accordion" fillSpace="sidebar">
				</div>
			</div>
		</div>
		<div id="container">
			<div id="navTab" class="tabsPage">
				<div class="tabsPageHeader">
					<div class="tabsPageHeaderContent"><!-- 显示左右控制时添加 class="tabsPageHeaderMargin" -->
						<ul class="navTab-tab">
							<li tabid="main" class="main"><a href="javascript:;"><span><span class="home_icon">我的主页</span></span></a></li>
						</ul>
					</div>
					<div class="tabsLeft">left</div><!-- 禁用只需要添加一个样式 class="tabsLeft tabsLeftDisabled" -->
					<div class="tabsRight">right</div><!-- 禁用只需要添加一个样式 class="tabsRight tabsRightDisabled" -->
					<div class="tabsMore">more</div>
				</div>
				<ul class="tabsMoreList">
					<li><a href="javascript:;">我的主页</a></li>
				</ul>
				<div class="navTab-panel tabsPageContent layoutBox">
					<div class="page unitBox">
						<iframe src="${ctx}/index_bg.jsp" style="width:2000px; height:800px;" frameborder="no" border="0" marginwidth="0" marginheight="0"></iframe>
					</div>
				</div>
			</div>
		</div>
	</div>
	<div id="footer">Copyright &copy; 2013 汕头移动分公司</div>
</body>
</html>