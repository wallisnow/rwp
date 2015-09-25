/*
通用数据水平层级选择控件
作者：绿豆糕
版本：v0.70
修改时间：2010年11月22日
要求数据格式：纯文本，数据项之间以","分隔，数据项数值和描述文本之间以":"分隔，可以在参数中自定义分隔符。
*/
;(function($){
//弹出层
$.openLayer = function(p){
	var param = $.extend({
		maxItems : 5,//最多选取项数字限制
		showLevel : 5,//显示级别
		oneLevel : true,//是否限制选择相同级别的数据，可以不为同一个父节点，
		//false为不限制，可以同时选择不同级别的数据，true为限制。
		onePLevel : false,//是否限制选择相同级别,并且是同一个父节点的数据，
		//false为不限制，可以同时选择不同级别的数据，true为限制。
		//此参数只有在oneLevel:true时才有效
		splitChar : ",:",//数据分隔符，第一个字符为各项之间的分隔符，第二个为每项中id和显示字符串的分隔符。
		returnValue : "",//以，分隔的选取结果id存放的位置id，默认为一个input。
		returnText : "",//以，分隔的选取结果文字存放的位置id，可以为span，div等容器。
		title : "请选择",//弹出窗口标题
		width : 650,//弹出窗口宽度
		span_width : {d1:70,d3:150},//可以自定义每一层级数据项显示宽度，用来对其排版。
		url : "",																					//ajax请求url
		pid : "0",																				//父id
		shared : true,																		//如果页面中有多于1个的弹出选择,是否共享缓存数据
		index : 1,																				//如果页面中有多于1个的弹出选择,如果不共享之前的操作界面则必须设置不同的index值，否则不同的弹出选择共享相同的操作界面。
		cacheEnable : true,																//是否允许缓存
		dragEnable : true,																//是否允许鼠标拖动
		pText : ""
	},p||{});

	var fs = {
		init_Container : function(){	//初始化头部和内容容器
			//标题
			var TITLE = param.title + ", 最多能选择 " + param.maxItems + " 项！";
			var CLOSE = "<span id='_cancel' style='cursor:pointer;'>[取消]</span>&nbsp;&nbsp;<span id='_ok' style='cursor:pointer;'>[确定]</span>";		//头部
			var htmlDiv = "";
			htmlDiv += "<div id='heads'>";
			htmlDiv += "<div id='headdiv'><span id='title'>" + TITLE + "</span><span id='close'>" + CLOSE + "</span></div>";
			htmlDiv += "</div>";
			htmlDiv += "<div id='containers'></div>";
			return htmlDiv;
		},
		init_area : function(){			//初始化数据容器
			var _container = $("#containers");																				//已选择项容器
			var selArea = $("<div id='selArea'><div>已选择项目：</div></div>");
			_container.append(selArea); 
			if (param.maxItems == 1){ selArea.hide(); }

			//初始化第一层级数据容器，以后各级容器都clone本容器
			var d1 = $("<div id='d1'></div>");
			var dc = $("<div id='dc'></div>");

			_container.append(dc).append(d1);//加入数据容器中
			dc.hide();
			fs.add_data(d1);//添加数据
		},
		add_data : function(targetid){					//添加数据到容器，添加事件，初始化下一层次容器
			targetid.nextAll().remove();					//删除目标容器之后的所有同级别容器

			var pid = param.pid;									//查询数据的参数，父id
			var url = param.url;									//ajax查询url
			var data;												//返回数据变量

			if(param.cacheEnable){ data = _cache[pid];}	//如果cache开启则首先从cache中取得数据
			
			if(typeof(data) != "undefined"){
				set_data(targetid, data);
				return;
			}
			
			//如果cache中没有数据并且url和pid都设置了,发起ajax请求
			if (typeof(data) == "undefined" &&  url != ""){
				$.ajax({
					type: "POST",
					url: url,
					data: {definition:pid},
					dataType: 'json',
					async : false,
					success: function(json){
						if("success" == json.status){
							data = eval("(" + json.message + ")");
							fs.set_data(targetid, data);
							//if(param.cacheEnable){ _cache[pid] = data;}		//cache允许,保存数据到cache
						}
					}
				});
			}
		},
		
		set_data : function(targetid, data){
			if(data.length < 1){
				targetid.empty().show().append($("<span style='color:red;'>没有下级数据！</span>"));
				return;
			}
			var span_width = eval("param.span_width."+targetid.attr("id"));								//每个数据显示项的宽度
			span_width = (span_width == undefined ? param.span_width.d1:span_width );			//没有设置的话，就使用第一个数据容器的值
			var inspan_width = ($.browser.msie)?1:3;																			//内部文字和checkbox之间的距离

			var html = [];
			//循环获得格式化的显示字符串
			for(var i = 0 ; i < data.length ; i++){
				html.push("<span style='width:"+span_width+"px;white-space:nowrap;float:left;'>");
				html.push("<input type='checkbox' value='" + data[i].code + "'>");
				html.push("<span name='"+targetid.attr("id")+"' style='margin-left:"+inspan_width+"px;'>" + data[i].name + "</span>");
				html.push("</span>");
			}
			targetid.empty().show().append($(html.join("")));															//格式化的html代码放入目标容器
			if(param.maxItems > 1){fs.change_status(targetid);}														//同步状态,单选状态无必要
			
			
			fs.add_input_event(targetid);
			fs.add_span_event(targetid);
		},
		
		init_event : function(){																												//绑定已选择框中checkbox的事件，确定，取消事件响应
			$("#selArea").find(":input").live("click",function(){
				$(this).parent().remove();
				$("#containers > div").find(":input[value="+this.value+"]").attr("checked",false);
			});
			$("#_cancel").click(function(){
				$("#bodybg").hide();
				$("#popupAddr").fadeOut();
				$("select").css("display", "");
			});
			$("#_ok").click(function(){
				var vals = "";
				var txts = "";
				$("#selArea").find(":input").each(function(i){
					vals += ("," + this.value);
					txts += ("," + $(this).next().text());
				});
				fs.set_returnVals(param.returnValue,vals);
				fs.set_returnVals(param.returnText,txts);
		
				$("#bodybg").hide();
				$("#popupAddr").fadeOut();
				$("select").css("display", "");
			});
		},
		change_status : function(targetid){ //切换不同元素，形成不同下级列表时候，同步已选择区的元素和新形成区元素的选中状态
			var selArea = $("#selArea");
			var selinputs = selArea.find(":input");
			var vals =[];

			if(selinputs.length > 0){
				selinputs.each(function(){ vals.push(this.value); });
			}
			targetid.find(":input").each(function(){
				if($.inArray(this.value,vals) != -1){ this.checked = true; }
			});
		},
		add_input_event : function(targetid){	//新生成的元素集合添加input的单击事件响应
			var selArea = $("#selArea");
			targetid.find(":input").click(function(){
				if (param.maxItems == 1){
					selArea.find("span").remove();
					$("#containers > div").find(":checked:first").not($(this)).attr("checked",false);
					$(this).css("color","white");
					selArea.append($(this).parent().clone());
					$("#_ok").click();
				}else {
					if(this.checked && fs.check_level(this) && fs.check_num(this)){
						selArea.append($(this).parent().clone().css({"width":"","background":"","border":""}));
					}else{
						selArea.find(":input[value="+this.value+"]").parent().remove();
					}			
				}
			});
		},
		add_span_event : function(targetid){	//新生成的元素集合添加span的单击事件响应
			var maxlev = param.showLevel;
			var thislevel = parseInt(targetid.attr("id").substring(1));
	
			var spans = targetid.children("span");
			spans.children("span").click(function(e){
				if (maxlev > thislevel){
					var next=$("#dc").clone();
					next.attr("id","d"+(thislevel+1));
					targetid.after(next);
			
					spans.css({"background":"","border":"","margin":""});
					$(this).parent().css({"background":"orange","border":"1px ridge","margin":"-1"});
					param.pid = $(this).prev().val();
					fs.add_data(next,param);
				}else{
					//alert("当前设置只允许显示" +  maxlev + "层数据！");
				}
			});
		},
		check_num : function(obj){	//检测最多可选择数量
			if($("#selArea").find(":input").size() < param.maxItems){
				return true;
			}else{
				obj.checked = false;
				alert("最多只能选择"+param.maxItems+"个选项");
				return false;
			}
		},
		check_level : function(obj){	//检测是否允许选取同级别选项或者同父id选项
			var selobj = $("#selArea > span");
			if(selobj.length ==0) return true;

			var oneLevel = param.oneLevel;
			if(oneLevel == false){
				return true;
			}else{
				var selLevel = selobj.find("span:first").attr("name");		//已选择元素的级别
				var thislevel = $(obj).next().attr("name");					//当前元素级别
				if(selLevel != thislevel) {
					obj.checked = false;
					//alert("当前设定只允许选择同一级别的元素！");
					return  false;
				}else{
					var onePLevel = param.onePLevel;		//是否设定只允许选择同一父id的同级元素
					if (onePLevel == false) {
						return true;
					}else{
						var parentId = selobj.attr("name");								//已选择元素的父id
						var thisParentId = $(obj).parent().attr("name");	//当前元素父id
						if (parentId != thisParentId){
							obj.checked = false;
							alert("当前设定只允许选择同一级别并且相同上级的元素！");
							return false;
						}
						return true;
					}
				}
			}
		},
		set_returnVals : function(id,vals) {	//按"确定"按钮时处理、设置返回值
			if(id != ""){
				var Container = $("#" + id);
				if(Container.length > 0){
					if(Container.is("input")){
						Container.val(vals.substring(1));
					}else{
						Container.text(vals.substring(1));
					}
				}
			}	
		},
		init_style : function() {	//初始化css
			var _width = param.width-(2*2) - (5*2); 

			var css = [];
			css.push("#popupAddr {position:absolute; width:"+param.width+"px; height:auto; background-color:#B0C4DE; z-index:99; text-align:left; margin:0px; padding:0px;}");
			css.push("#bodybg {width:100%;z-index:98;position:absolute;top:0;left:0;background-color:#fff;opacity:0.1;filter:alpha(opacity =10);}");
			css.push("#heads {width:100%; font-size:12px; margin:0; padding:0;}");
			css.push("#headdiv {color:black; background-color:#B0C4DE; font-size:13px; height:25px; margin:0px; border:2px groove;}");
			css.push("#title {line-height:30px; padding-left:10px; float:left;}");
			css.push("#close {float:right; padding-right:12px; line-height:30px;}");
			css.push("#containers {width:100%; height:auto; margin:0; padding:0; background-color:white;}");
			css.push("#selArea {width:"+_width+"px; height:48px; margin-top:1px; padding:5px; float:left; border:2px groove;}");
	
			var d_css = "{width:"+_width+"px; margin-top:1px; padding:5px; height:auto; float:left; border:2px groove; }";
			css.push("dc "+d_css);
			for (i = 1; i <=param.showLevel; i++) { 
				css.push("#d" + i + " " + d_css); 
			}
			$("head").append($("<style>"+css.join(" ")+"</style>"));
		}
	};

	if (window._cache == undefined || !param.shared ){ _cache = {}; }
	if (window._index == undefined) { _index = param.index; }

	fs.init_style();//初始化样式

	var popupDiv = $("#popupAddr");	//创建一个div元素
	if (popupDiv.length == 0 ) {
		popupDiv = $("<div id='popupAddr'></div>");
		$("body").append(popupDiv);
	}
	
	var bodyBack = $("#bodybg");  //创建背景层
	if (bodyBack.length == 0 ) {
		bodyBack = $("<div id='bodybg'></div>");
		bodyBack.height($(window).height());
		$("body").append(bodyBack);
		popupDiv.html(fs.init_Container());	//弹出层内容
		fs.init_area();
		fs.init_event();
	}else {
		if (_index != param.index) {
			popupDiv.html(fs.init_Container(param));
			fs.init_area();
			fs.init_event();
			_index = param.index;
		}
	}

	var yPos = (document.body.offsetHeight - popupDiv.height()) / 2;
	var xPos = (document.body.offsetWidth - popupDiv.width()) / 2;
	
	popupDiv.css({"top": yPos,"left": xPos}).show();
	
	if (param.dragEnable) {     //允许鼠标拖动   
	    var _move=false;        //移动标记   
	    var _x,_y;              //鼠标离控件左上角的相对位置   
	    popupDiv.mousedown(function(e){
	        _move=true;   
	        _x=e.pageX-parseInt(popupDiv.css("left"));   
	        _y=e.pageY-parseInt(popupDiv.css("top"));   
	           
	        $('body').mousemove(function(e){
	            if(_move){   
	                popupDiv.css({cursor:'move'})   
	                var x=e.pageX-_x;//移动时根据鼠标位置计算控件左上角的绝对位置   
	                var y=e.pageY-_y;   
	                popupDiv.css({top:y,left:x});//控件新位置   
	            }
	         }).mouseup(function(){ _move=false; popupDiv.css({cursor:'default'})});   
	    });
	}
	bodyBack.show();
	$("select").css("display", "none");
	
	popupDiv.fadeIn();
	
}

})(jQuery)