/**
 * Theme Plugins
 * @author ZhangHuihua@msn.com
 */
(function($){
	$.fn.extend({
		cssTable: function(options){

			return this.each(function(){
				var $this = $(this);
				var nowrap = $this.hasClass("nowrap");
				var haschkbox = $this.find("th:first").find(":checkbox").length > 0;
				
				if(nowrap){//表格具有nowrap属性时,置表头nowrap为TRUE
					$this.find("th").each(function(){
						$(this).attr("nowrap", true);
					});
					
					//$this.find("td").each(function(){
					//	$(this).attr("nowrap", true);
					//});
				}
				
				if(haschkbox){//首列为checkbox项, 则居中展示
					$this.find("tr").each(function(){
						$(this).find("td:first").css("text-align", "center");
						$(this).find("th:first").css("text-align", "center");
					});
				}
				
				var $trs = $this.find('tbody>tr');
				
				$trs.each(function(index){
					var $tr = $(this);
					if(index % 2 == 1){
						$tr.addClass("evenbg");
					}
				});
				
				$trs.hoverClass("hover").each(function(index){
					var $tr = $(this);
					if (index % 2 == 1) $tr.addClass("trbg");
					
					$tr.click(function(){
						$trs.filter(".selected").removeClass("selected");
						$tr.addClass("selected");
					});
				});
			});
		}
	});
})(jQuery);
