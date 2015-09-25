/**
 * @author Roger Wu
 */

(function($){
	var allSelectBox = [];
	var killAllBox = function(bid){
		$.each(allSelectBox, function(i){
			if (allSelectBox[i] != bid) {
				if (!$("#" + allSelectBox[i])[0]) {
					$("#op_" + allSelectBox[i]).remove();
					//allSelectBox.splice(i,1);
				} else {
					$("#op_" + allSelectBox[i]).css({ height: "", width: "" }).hide();
				}
				$(document).unbind("click", killAllBox);
			}
		});
	};
	
	$.extend($.fn, {
		comboxSelect: function(options){
			return this.each(function(){
				var box = $(this);
				var boxId = box.attr("id");
				
				allSelectBox.push(boxId);
				$("a", box).click(function(){
					var readonly = $(this).hasClass("readonly") || false;
					if(readonly)return false;
					var options = $("#op_"+boxId);
					if (options.is(":hidden")) {
						if(options.height() > 300) {
							options.css({height:"300px",overflow:"scroll"});
						}
						var top = box.offset().top+box[0].offsetHeight-50;
						if(top + options.height() > $(window).height() - 20) {
							top =  $(window).height() - 20 - options.height();
						}
						options.css({top:top,left:box.offset().left}).show();
						killAllBox(boxId);
						$(document).click(killAllBox);
					} else {
						$(document).unbind("click", killAllBox);
						killAllBox();
					}
					return false;
				});
				$("#op_"+box.attr("id")).find(">li").comboxOption(box);
			});
		},
		comboxOption: function(box){
			return this.each(function(){
				$(this).hoverClass();
				$(this).click(function(event){
					var $this = $(this);
					$this.parent().find(".selected").removeClass("selected");
					$this.addClass("selected");
					$("a", box).text($this.text());
					var selectId = box.attr("id").substr(7);
					var $select = $("#" + selectId);
					
					if ($select.val() != $this.attr("ref")) {
						 $select.val($this.attr("ref")).trigger("change");
					}
					event.preventDefault();
				});
			});
		},
		combox:function(){
			/* 清理下拉层 */
			var _selectBox = [];
			$.each(allSelectBox, function(i){ 
				if ($("#" + allSelectBox[i])[0]) {
					_selectBox.push(allSelectBox[i]);
				} else {
					$("#op_" + allSelectBox[i]).remove();
				}
			});
			allSelectBox = _selectBox;
			
			return this.each(function(i){
				var $this = $(this).removeClass("combox");
				var name = $this.attr("name");
				var value= $this.val();
				var label = $("option[value=" + value + "]",$this).text();
				var ref = $this.attr("ref");
				var refUrl = $this.attr("refUrl") || "";
				var defClass = $this.attr("class");
				var readonly = $this.hasClass("readonly");
				
				var selectId = $this.attr("id");
				if(typeof(selectId) == "undefined"){
					selectId = "tmp_select_" + Math.round(Math.random()*10000000);
					$this.attr("id", selectId);
				}
				var cid = $this.attr("id");
				var select = '<div class="combox"><div id="combox_'+ cid +'" class="select"' + (ref?' ref="' + ref + '"' : '') + '>';
				select += '<a href="javascript:" class="'+ defClass +'" name="' + name +'" value="' + value + '">' + label +'</a></div></div>';
				var options = '<ul class="comboxop" id="op_combox_'+ cid +'">';
				$("option", $this).each(function(){
					var option = $(this);
					options += "<li ref=\"" + option.prop("value") + "\" ";
					if(value==option.prop("value")){
						options += "class=\"selected\""
					}
					options += ">";
					options += option.text();
					options += "</li>"
				});
				options +="</ul>";
				
				$("body").append(options);
				$this.after(select);
				var selectW = $this.outerWidth();
				$("#combox_"+ cid).find("a").width(selectW - 19);//19为向下图标宽度
				$("#op_combox_"+ cid).find("li").width(selectW);
				
				$("div.select", $this.next()).comboxSelect().append($this);
				
				if (ref && refUrl) {
					function _onchange(event){
						var $ref = $("#"+ref);
						if ($ref.size() == 0) return false;
						$.ajax({
							type:'POST', dataType:"json", url:refUrl.replace("{value}", encodeURIComponent($this.attr("value"))), cache: false,
							data:{},
							success: function(json){
								if (!json) return;
								var html = '';
	
								$.each(json, function(i){
									if (json[i] && json[i].length > 1){
										html += '<option value="'+json[i][0]+'">' + json[i][1] + '</option>';
									}
								});
								
								var $refCombox = $ref.parents("div.combox:first");
								$ref.html(html).insertAfter($refCombox);
								$refCombox.remove();
								$ref.trigger("change").combox();
							},
							error: DWZ.ajaxError
						});
					}
					
					$this.unbind("change", _onchange).bind("change", _onchange);
				}
				
			});
		}
	});
})(jQuery);
