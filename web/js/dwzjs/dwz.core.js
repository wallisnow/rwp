/**
 * @author ZhangHuihua@msn.com
 * 
 */

var DWZ = {
	// sbar: show sidebar
	keyCode: {
		ENTER: 13, ESC: 27, END: 35, HOME: 36,
		SHIFT: 16, TAB: 9,
		LEFT: 37, RIGHT: 39, UP: 38, DOWN: 40,
		DELETE: 46, BACKSPACE:8
	},
	eventType: {
		pageClear:"pageClear",	// 用于重新ajaxLoad、关闭nabTab, 关闭dialog时，去除xheditor等需要特殊处理的资源
		resizeGrid:"resizeGrid"	// 用于窗口或dialog大小调整
	},
	isOverAxis: function(x, reference, size) {
		//Determines when x coordinate is over "b" element axis
		return (x > reference) && (x < (reference + size));
	},
	isOver: function(y, x, top, left, height, width) {
		//Determines when x, y coordinates is over "b" element
		return this.isOverAxis(y, top, height) && this.isOverAxis(x, left, width);
	},
	
	pageInfo: {pageNum:"pageNum", numPerPage:"numPerPage", orderField:"orderField", orderDirection:"orderDirection"},
	statusCode: {ok:200, error:300, timeout:301},
	ui:{sbar:true},
	frag:{
		dialogFrag : '<div class="dialog" style="top:150px; left:300px;"> <div class="dialogHeader" onselectstart="return false;" oncopy="return false;" onpaste="return false;" oncut="return false;"> <div class="dialogHeader_r"> <div class="dialogHeader_c"> <a class="close" href="#close">close</a> <a class="maximize" href="#maximize">maximize</a> <a class="restore" href="#restore">restore</a> <a class="minimize" href="#minimize">minimize</a> <h1>弹出窗口</h1> </div> </div> </div> <div class="dialogContent layoutBox unitBox"> </div> <div class="dialogFooter"><div class="dialogFooter_r"><div class="dialogFooter_c"></div></div></div> <div class="resizable_h_l" tar="nw"></div> <div class="resizable_h_r" tar="ne"></div> <div class="resizable_h_c" tar="n"></div> <div class="resizable_c_l" tar="w" style="height:300px;"></div> <div class="resizable_c_r" tar="e" style="height:300px;"></div> <div class="resizable_f_l" tar="sw"></div> <div class="resizable_f_r" tar="se"></div> <div class="resizable_f_c" tar="s"></div> </div>',
		dialogProxy : '<div id="dialogProxy" class="dialog dialogProxy"> <div class="dialogHeader"> <div class="dialogHeader_r"> <div class="dialogHeader_c"> <a class="close" href="#close">close</a> <a class="maximize" href="#maximize">maximize</a> <a class="minimize" href="#minimize">minimize</a> <h1></h1> </div> </div> </div> <div class="dialogContent"></div> <div class="dialogFooter"> <div class="dialogFooter_r"> <div class="dialogFooter_c"> </div> </div> </div> </div>',
		taskbar : '<div id="taskbar" style="left:0px; display:none;"> <div class="taskbarContent"> <ul></ul> </div> <div class="taskbarLeft taskbarLeftDisabled" style="display:none;">taskbarLeft</div> <div class="taskbarRight" style="display:none;">taskbarRight</div></div>',
		dwzFrag : '<div id="splitBar"></div> <div id="splitBarProxy"></div> <!--拖动效果--> <div class="resizable"></div> <!--阴影--> <div class="shadow" style="width:508px; top:148px; left:296px;"> <div class="shadow_h"> <div class="shadow_h_l"></div> <div class="shadow_h_r"></div> <div class="shadow_h_c"></div> </div> <div class="shadow_c"> <div class="shadow_c_l" style="height:296px;"></div> <div class="shadow_c_r" style="height:296px;"></div> <div class="shadow_c_c" style="height:296px;"></div> </div> <div class="shadow_f"> <div class="shadow_f_l"></div> <div class="shadow_f_r"></div> <div class="shadow_f_c"></div> </div> </div> <!--遮盖屏幕--> <div id="alertBackground" class="alertBackground"></div> <div id="dialogBackground" class="dialogBackground"></div> <div id="background" class="background"></div> <div id="progressBar" class="progressBar">数据加载中，请稍等...</div>',
		pagination : '<ul> <li class="j-first"> <a class="first" href="javascript:;"><span>首页</span></a> <span class="first"><span>首页</span></span> </li> <li class="j-prev"> <a class="previous" href="javascript:;"><span>上一页</span></a> <span class="previous"><span>上一页</span></span> </li> #pageNumFrag# <li class="j-next"> <a class="next" href="javascript:;"><span>下一页</span></a> <span class="next"><span>下一页</span></span> </li> <li class="j-last"> <a class="last" href="javascript:;"><span>末页</span></a> <span class="last"><span>末页</span></span> </li> <li class="jumpto"><input name=\"pageNo\" id=\"pageNo\" class="textInput" type="text" size="4" value="#currentPage#" /><input class="goto" type="button" value="确定" /></li> </ul>',
		alertBoxFrag : '<div id="alertMsgBox" class="alert"><div class="alertContent"><div class="#type#"><div class="alertInner"><h1>#title#</h1><div class="msg">#message#</div></div><div class="toolBar"><ul>#butFragment#</ul></div></div></div><div class="alertFooter"><div class="alertFooter_r"><div class="alertFooter_c"></div></div></div></div>',
		alertButFrag : '<li><a class="button" rel="#callback#" onclick="alertMsg.close()" href="javascript:"><span>#butMsg#</span></a></li>',
		calendarFrag : '<div id="calendar"> <div class="main"> <div class="head"> <table width="100%" border="0" cellpadding="0" cellspacing="2"> <tr> <td><select name="year"></select></td> <td><select name="month"></select></td> <td width="20"><span class="close">×</span></td> </tr> </table> </div> <div class="body"> <dl class="dayNames"><dt>日</dt><dt>一</dt><dt>二</dt><dt>三</dt><dt>四</dt><dt>五</dt><dt>六</dt></dl> <dl class="days">日期列表选项</dl> <div style="clear:both;height:0;line-height:0"></div> </div> <div class="foot"> <table class="time"> <tr> <td> <input type="text" class="hh" maxlength="2" start="0" end="23"/>: <input type="text" class="mm" maxlength="2" start="0" end="59"/>: <input type="text" class="ss" maxlength="2" start="0" end="59"/> </td> <td><ul><li class="up">&and;</li><li class="down">&or;</li></ul></td> </tr> </table> <button type="button" class="clearBut">清空</button> <button type="button" class="okBut">确定</button> <div> <div class="tm"> <ul class="hh"> <li>0</li> <li>1</li> <li>2</li> <li>3</li> <li>4</li> <li>5</li> <li>6</li> <li>7</li> <li>8</li> <li>9</li> <li>10</li> <li>11</li> <li>12</li> <li>13</li> <li>14</li> <li>15</li> <li>16</li> <li>17</li> <li>18</li> <li>19</li> <li>20</li> <li>21</li> <li>22</li> <li>23</li> </ul> <ul class="mm"> <li>0</li> <li>5</li> <li>10</li> <li>15</li> <li>20</li> <li>25</li> <li>30</li> <li>35</li> <li>40</li> <li>45</li> <li>50</li> <li>55</li> </ul> <ul class="ss"> <li>0</li> <li>10</li> <li>20</li> <li>30</li> <li>40</li> <li>50</li> </ul> </div> </div> </div>',
		navTabCM : '<ul id="navTabCM"> <li rel="reload">刷新标签页</li> <li rel="closeCurrent">关闭标签页</li> <li rel="closeOther">关闭其它标签页</li> <li rel="closeAll">关闭全部标签页</li> </ul>',
		dialogCM : '<ul id="dialogCM"> <li rel="closeCurrent">关闭弹出窗口</li> <li rel="closeOther">关闭其它弹出窗口</li> <li rel="closeAll">关闭全部弹出窗口</li> </ul>',
		externalFrag : '<iframe src="{url}" style="width:100%;height:{height};" frameborder="no" border="0" marginwidth="0" marginheight="0"></iframe>'
	}, //page fragment
	_msg:{
		statusCode_503 : '服务器当前负载过大或者正在维护!',
		validateFormError : '提交数据不完整，{0}个字段有错误，请改正后再提交!',
		sessionTimout : '会话超时，请重新登录!',
		alertSelectMsg : '请选择信息!',
		forwardConfirmMsg : '继续下一步!',
		dwzTitle : 'DWZ富客户端框架',
		mainTabTitle : '我的主页'	
	}, //alert message
	_set:{
		loginUrl:"", //session timeout
		loginTitle:"", //if loginTitle open a login dialog
		debug:false
	},
	msg:function(key, args){
		var _format = function(str,args) {
			args = args || [];
			var result = str || "";
			for (var i = 0; i < args.length; i++){
				result = result.replace(new RegExp("\\{" + i + "\\}", "g"), args[i]);
			}
			return result;
		}
		return _format(this._msg[key], args);
	},
	debug:function(msg){
		if (this._set.debug) {
			if (typeof(console) != "undefined") console.log(msg);
			else alert(msg);
		}
	},
	loadLogin:function(){
		if ($.pdialog && DWZ._set.loginTitle) {
			$.pdialog.open(DWZ._set.loginUrl, "login", DWZ._set.loginTitle, {mask:true,width:520,height:260});
		} else {
			window.location = DWZ._set.loginUrl;
		}
	},
	
	/*
	 * json to string
	 */
	obj2str:function(o) {
		var r = [];
		if(typeof o =="string") return "\""+o.replace(/([\'\"\\])/g,"\\$1").replace(/(\n)/g,"\\n").replace(/(\r)/g,"\\r").replace(/(\t)/g,"\\t")+"\"";
		if(typeof o == "object"){
			if(!o.sort){
				for(var i in o)
					r.push(i+":"+DWZ.obj2str(o[i]));
				if(!!document.all && !/^\n?function\s*toString\(\)\s*\{\n?\s*\[native code\]\n?\s*\}\n?\s*$/.test(o.toString)){
					r.push("toString:"+o.toString.toString());
				}
				r="{"+r.join()+"}"
			}else{
				for(var i =0;i<o.length;i++) {
					r.push(DWZ.obj2str(o[i]));
				}
				r="["+r.join()+"]"
			}
			return r;
		}
		return o.toString();
	},
	jsonEval:function(data) {
		try{
			if ($.type(data) == 'string')
				return eval('(' + data + ')');
			else return data;
		} catch (e){
			return {};
		}
	},
	ajaxError:function(xhr, ajaxOptions, thrownError){
		if (alertMsg) {
			alertMsg.error("<div>Http status: " + xhr.status + " " + xhr.statusText + "</div>" 
				+ "<div>ajaxOptions: "+ajaxOptions + "</div>"
				+ "<div>thrownError: "+thrownError + "</div>"
				+ "<div>"+xhr.responseText+"</div>");
		} else {
			alert("Http status: " + xhr.status + " " + xhr.statusText + "\najaxOptions: " + ajaxOptions + "\nthrownError:"+thrownError + "\n" +xhr.responseText);
		}
	},
	ajaxDone:function(json){
		if(json.statusCode == DWZ.statusCode.error) {
			if(json.message && alertMsg) alertMsg.error(json.message);
		} else if (json.statusCode == DWZ.statusCode.timeout) {
			if(alertMsg) alertMsg.error(json.message || DWZ.msg("sessionTimout"), {okCall:DWZ.loadLogin});
			else DWZ.loadLogin();
		} else {
			if(json.message && alertMsg) alertMsg.correct(json.message);
		};
	},

	init:function(options){
		var op = $.extend({
				loginUrl:"login.html", loginTitle:null, callback:null, debug:false, 
				statusCode:{}
			}, options);
		this._set.loginUrl = op.loginUrl;
		this._set.loginTitle = op.loginTitle;
		this._set.debug = op.debug;
		$.extend(DWZ.statusCode, op.statusCode);
		$.extend(DWZ.pageInfo, op.pageInfo);
	}
};


(function($){
	// DWZ set regional
	$.setRegional = function(key, value){
		if (!$.regional) $.regional = {};
		$.regional[key] = value;
	};
	
	$.fn.extend({
		/**
		 * @param {Object} op: {type:GET/POST, url:ajax请求地址, data:ajax请求参数列表, callback:回调函数 }
		 */
		ajaxUrl: function(op){
			var $this = $(this);

			$this.trigger(DWZ.eventType.pageClear);
			
			$.ajax({
				type: op.type || 'GET',
				url: op.url,
				data: op.data,
				cache: false,
				success: function(response){
					var json = DWZ.jsonEval(response);
					
					if (json.statusCode==DWZ.statusCode.error){
						if (json.message) alertMsg.error(json.message);
					} else {
						$this.html(response).initUI();
						if ($.isFunction(op.callback)) op.callback(response);
					}
					
					if (json.statusCode==DWZ.statusCode.timeout){
						if ($.pdialog) $.pdialog.checkTimeout();
						if (navTab) navTab.checkTimeout();
	
						alertMsg.error(json.message || DWZ.msg("sessionTimout"), {okCall:function(){
							DWZ.loadLogin();
						}});
					} 
					
				},
				error: DWZ.ajaxError,
				statusCode: {
					503: function(xhr, ajaxOptions, thrownError) {
						alert(DWZ.msg("statusCode_503") || thrownError);
					}
				}
			});
		},
		loadUrl: function(url,data,callback){
			$(this).ajaxUrl({url:url, data:data, callback:callback});
		},
		initUI: function(){
			return this.each(function(){
				if($.isFunction(initUI)) initUI(this);
			});
		},
		/**
		 * adjust component inner reference box height
		 * @param {Object} refBox: reference box jQuery Obj
		 */
		layoutH: function($refBox){
			return this.each(function(){
				var $this = $(this);
				if (! $refBox) $refBox = $this.parents("div.layoutBox:first");
				var iRefH = $refBox.height();
				var iLayoutH = parseInt($this.attr("layoutH"));
				var iH = iRefH - iLayoutH > 50 ? iRefH - iLayoutH : 50;
				
				if ($this.isTag("table")) {
					$this.removeAttr("layoutH").wrap('<div layoutH="'+iLayoutH+'" style="overflow:auto;height:'+iH+'px"></div>');
				} else {
					$this.height(iH).css("overflow","auto");
				}
			});
		},
		hoverClass: function(className, speed){
			var _className = className || "hover";
			return this.each(function(){
				var $this = $(this), mouseOutTimer;
				$this.hover(function(){
					if (mouseOutTimer) clearTimeout(mouseOutTimer);
					$this.addClass(_className);
				},function(){
					mouseOutTimer = setTimeout(function(){$this.removeClass(_className);}, speed||10);
				});
			});
		},
		focusClass: function(className){
			var _className = className || "textInputFocus";
			return this.each(function(){
				$(this).focus(function(){
					$(this).addClass(_className);
				}).blur(function(){
					$(this).removeClass(_className);
				});
			});
		},
		inputAlert: function(){
			return this.each(function(){
				
				var $this = $(this);
				
				function getAltBox(){
					return $this.parent().find("label.alt");
				}
				function altBoxCss(opacity){
					var position = $this.position();
					return {
						width:$this.width(),
						top:position.top+'px',
						left:position.left +'px',
						opacity:opacity || 1
					};
				}
				if (getAltBox().size() < 1) {
					if (!$this.attr("id")) $this.attr("id", $this.attr("name") + "_" +Math.round(Math.random()*10000));
					var $label = $('<label class="alt" for="'+$this.attr("id")+'">'+$this.attr("alt")+'</label>').appendTo($this.parent());
					
					$label.css(altBoxCss(1));
					if ($this.val()) $label.hide();
				}
				
				$this.focus(function(){
					getAltBox().css(altBoxCss(0.3));
				}).blur(function(){
					if (!$(this).val()) getAltBox().show().css("opacity",1);
				}).keydown(function(){
					getAltBox().hide();
				});
			});
		},
		isTag:function(tn) {
			if(!tn) return false;
			return $(this)[0].tagName.toLowerCase() == tn?true:false;
		},
		/**
		 * 判断当前元素是否已经绑定某个事件
		 * @param {Object} type
		 */
		isBind:function(type) {
			var _events = $(this).data("events");
			return _events && type && _events[type];
		},
		/**
		 * 输出firebug日志
		 * @param {Object} msg
		 */
		log:function(msg){
			return this.each(function(){
				if (console) console.log("%s: %o", msg, this);
			});
		}
	});
	
	/**
	 * 扩展String方法
	 */
	$.extend(String.prototype, {
		isPositiveInteger:function(){
			return (new RegExp(/^[1-9]\d*$/).test(this));
		},
		isInteger:function(){
			return (new RegExp(/^\d+$/).test(this));
		},
		isNumber: function(value, element) {
			return (new RegExp(/^-?(?:\d+|\d{1,3}(?:,\d{3})+)(?:\.\d+)?$/).test(this));
		},
		trim:function(){
			return this.replace(/(^\s*)|(\s*$)|\r|\n/g, "");
		},
		startsWith:function (pattern){
			return this.indexOf(pattern) === 0;
		},
		endsWith:function(pattern) {
			var d = this.length - pattern.length;
			return d >= 0 && this.lastIndexOf(pattern) === d;
		},
		replaceSuffix:function(index){
			return this.replace(/\[[0-9]+\]/,'['+index+']').replace('#index#',index);
		},
		trans:function(){
			return this.replace(/&lt;/g, '<').replace(/&gt;/g,'>').replace(/&quot;/g, '"');
		},
		encodeTXT: function(){
			return (this).replaceAll('&', '&amp;').replaceAll("<","&lt;").replaceAll(">", "&gt;").replaceAll(" ", "&nbsp;");
		},
		replaceAll:function(os, ns){
			return this.replace(new RegExp(os,"gm"),ns);
		},
		replaceTm:function($data){
			if (!$data) return this;
			return this.replace(RegExp("({[A-Za-z_]+[A-Za-z0-9_]*})","g"), function($1){
				return $data[$1.replace(/[{}]+/g, "")];
			});
		},
		replaceTmById:function(_box){
			var $parent = _box || $(document);
			return this.replace(RegExp("({[A-Za-z_]+[A-Za-z0-9_]*})","g"), function($1){
				var $input = $parent.find("#"+$1.replace(/[{}]+/g, ""));
				return $input.val() ? $input.val() : $1;
			});
		},
		isFinishedTm:function(){
			return !(new RegExp("{[A-Za-z_]+[A-Za-z0-9_]*}").test(this)); 
		},
		skipChar:function(ch) {
			if (!this || this.length===0) {return '';}
			if (this.charAt(0)===ch) {return this.substring(1).skipChar(ch);}
			return this;
		},
		isValidPwd:function() {
			return (new RegExp(/^([_]|[a-zA-Z0-9]){6,32}$/).test(this)); 
		},
		isValidMail:function(){
			return(new RegExp(/^\w+((-\w+)|(\.\w+))*\@[A-Za-z0-9]+((\.|-)[A-Za-z0-9]+)*\.[A-Za-z0-9]+$/).test(this.trim()));
		},
		isSpaces:function() {
			for(var i=0; i<this.length; i+=1) {
				var ch = this.charAt(i);
				if (ch!=' '&& ch!="\n" && ch!="\t" && ch!="\r") {return false;}
			}
			return true;
		},
		isPhone:function() {
			return (new RegExp(/(^([0-9]{3,4}[-])?\d{3,8}(-\d{1,6})?$)|(^\([0-9]{3,4}\)\d{3,8}(\(\d{1,6}\))?$)|(^\d{3,8}$)/).test(this));
		},
		isUrl:function(){
			return (new RegExp(/^[a-zA-z]+:\/\/([a-zA-Z0-9\-\.]+)([-\w .\/?%&=:]*)$/).test(this));
		},
		isExternalUrl:function(){
			return this.isUrl() && this.indexOf("://"+document.domain) == -1;
		}
	});
})(jQuery);

/** 
 * You can use this map like this:
 * var myMap = new Map();
 * myMap.put("key","value");
 * var key = myMap.get("key");
 * myMap.remove("key");
 */
function Map(){

	this.elements = new Array();
	
	this.size = function(){
		return this.elements.length;
	}
	
	this.isEmpty = function(){
		return (this.elements.length < 1);
	}
	
	this.clear = function(){
		this.elements = new Array();
	}
	
	this.put = function(_key, _value){
		this.remove(_key);
		this.elements.push({key: _key, value: _value});
	}
	
	this.remove = function(_key){
		try {
			for (i = 0; i < this.elements.length; i++) {
				if (this.elements[i].key == _key) {
					this.elements.splice(i, 1);
					return true;
				}
			}
		} catch (e) {
			return false;
		}
		return false;
	}
	
	this.get = function(_key){
		try {
			for (i = 0; i < this.elements.length; i++) {
				if (this.elements[i].key == _key) { return this.elements[i].value; }
			}
		} catch (e) {
			return null;
		}
	}
	
	this.element = function(_index){
		if (_index < 0 || _index >= this.elements.length) { return null; }
		return this.elements[_index];
	}
	
	this.containsKey = function(_key){
		try {
			for (i = 0; i < this.elements.length; i++) {
				if (this.elements[i].key == _key) {
					return true;
				}
			}
		} catch (e) {
			return false;
		}
		return false;
	}
	
	this.values = function(){
		var arr = new Array();
		for (i = 0; i < this.elements.length; i++) {
			arr.push(this.elements[i].value);
		}
		return arr;
	}
	
	this.keys = function(){
		var arr = new Array();
		for (i = 0; i < this.elements.length; i++) {
			arr.push(this.elements[i].key);
		}
		return arr;
	}
}
