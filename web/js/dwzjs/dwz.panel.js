/**
 * @author Roger Wu
 * @version 1.0
 */
(function($){
	$.extend($.fn, {
		jPanel:function(options){
			var op = $.extend({coll:"collapsable", exp:"expandable"}, options);
			return this.each(function(){
				var $panel = $(this);
				var close = $panel.hasClass("close");
				var collapse = $panel.hasClass("collapse");
				
				var header = $("div.panelHeader", $panel);
				var title = $(">h1", $panel);
				var $content = $("div.panelContent", $panel);
				var footer = $("div.panelFooter", $panel);
				
				var defaultH = $panel.attr("defH")?$panel.attr("defH"):0;
				var minH = $panel.attr("minH")?$panel.attr("minH"):0;
				if (close) 
					$content.css({
						height: "0px",
						display: "none"
					});
				else {
					if (defaultH > 0) 
						$content.height(defaultH + "px");
					else if(minH > 0){
						$content.css("minHeight", minH+"px");
					}
				}
				if(!collapse) return;
				var $pucker = $("a", header);
				var inH = $content.innerHeight() - 6;
				if(minH > 0 && minH >= inH) {
					defaultH = minH;
				} else{
					defaultH = inH;
				}
				$pucker.click(function(){
					if($pucker.hasClass(op.exp)){
						$content.jBlindDown({to:defaultH,call:function(){
							$pucker.removeClass(op.exp).addClass(op.coll);
							if(minH > 0) $content.css("minHeight",minH+"px");
						}});
					} else { 
						if(minH > 0) $content.css("minHeight","");
						if(minH >= inH) $content.css("height", minH+"px");
						$content.jBlindUp({call:function(){
							$pucker.removeClass(op.coll).addClass(op.exp);
						}});
					}
					return false;
				});
			});
		}
	});
})(jQuery);     
