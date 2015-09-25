function initPageEnv(options) {
	$(window).resize(function(){
		if(typeof(options) == "string"){
			initPageLayout(options);
		}else if(typeof(options) == "function"){
			options();
		}else{
			alert("initPageEnv 参数错误");
		}
	});
	
	setTimeout(function(){
		initPageUI();
		if(typeof(options) == "string"){
			initPageLayout(options);
		}else if(typeof(options) == "function"){
			options();
		}else{
			alert("initPageEnv 参数错误");
		}
	}, 50);
}


function initPageLayout(listOrContent){
	
	var pageH = $(window).height();//可视区域高度
	var pageW = $(window).width();//可视区域宽度
	
	var h2TitleH = $("h2.contentTitle").outerHeight(true) || 0;//标题栏高度
	
	var formBarH = $("div.formBar").outerHeight(true) || 0;//页面底部工具栏高度
	
	var formBarNum = $("div.formBar").length;
	
	var panelBarH =  $("div.panelBar").outerHeight(true) || 0;//页面中间工具栏高度
	
	var panelBarNum = $("div.panelBar").length;
	
	if("list" == listOrContent){
		var searchPageHeaderH = $("div.searchPageHeader").outerHeight(true) || 0;
		
		var tableBarH = pageH - h2TitleH - searchPageHeaderH - (panelBarH * panelBarNum) - (formBarH * formBarNum);
		
		$("div.tableBar").width(pageW).height(tableBarH);
		
		var tableW = $("div.tableBar>table").width();
		
		if(tableW <= pageW){
			$("div.tableBar").css("overflow", "auto");
		}
		
	}else if("content" == listOrContent){
		
		var formContentH = pageH - h2TitleH - (formBarH * formBarNum);
		
		$("div.formContent").height(formContentH);
		
	}
}

function initPageUI(_box){
	
	$("table tbody td").each(function(index) {
		var $this = $(this);
		if($("input, select, a", this).length <= 0){
			$this.attr("title", $this.text().trim());
		}
	});
	
	$("a.linkbutton", _box).each(function(){
		var options = $(this).attr("data-options");
		if(typeof(options) == "string"){
			if(options.toString().indexOf(":",0) > 0){
				$(this).linkbutton(eval('({' + options + '})'));
			}else{
				$(this).linkbutton(options);
			}
		}else{
			$(this).linkbutton();
		}
	});
	
	initUI(_box);
}