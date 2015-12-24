
var dragObject = new function () {
	var me = this;
	
	var dragNode;
	var currentlyDraggedNode;
	var idCdtCpt=0;
	
	// drop area for columns and conditions
	var clm,cdt;
	
	/* runs when the page is loaded */
	me.init = function () {
		
		if (EventHelpers.hasPageLoadHappened(arguments)) {
			return;
		}	
		/* The node being dragged */
		//dragNode=document.getElementById('toDrag');
		dragNode=$(".tree li").each(function(){
			EventHelpers.addEvent($(this).find("a").get("0"), 'dragstart', userDragStartEvent);
			//EventHelpers.addEvent($(this).find("a").get("0"), 'drag', dragEvent); 
			EventHelpers.addEvent($(this).find("a").get("0"), 'dragend', userDragEndEvent); 
		});
		
		/* The nodes that report to the user what is happening to that node*/
		eventNoticeNode = document.getElementById('eventNotice');
		//dragEventNoticeNode = document.getElementById('dragEventNotice');
		/* The drag event handlers */
		//EventHelpers.addEvent(dragNode, 'dragstart', dragStartEvent);
		//EventHelpers.addEvent(dragNode, 'drag', dragEvent); 
		//EventHelpers.addEvent(dragNode, 'dragend', dragEndEvent);
		
		//set results(columns) cfg div as a drop target
		clm = $("#clm").get(0);
		setDropTarget(clm);
		//set conditions cfg div as a drop target
		cdt = $("#cdt").get(0);
		setDropTarget(cdt);
	}
	
	function setDropTarget(dropTarget){
		//EventHelpers.addEvent(dropTarget, 'dragover', cancel);
		//alert(dropTarget.id);
		EventHelpers.addEvent(dropTarget, 'dragover', userDragOverListEvent);
		EventHelpers.addEvent(dropTarget, 'dragleave', userDragLeaveListEvent);
		EventHelpers.addEvent(dropTarget, 'drop', userDropListEvent);	
	}
	
	/* 
	 * The dragstart event handler logs to the user when the event started.
	 */
	function userDragStartEvent(e) {
	
		currentlyDraggedNode = $(this);
		//alert(this);
		console.log("drag start");
		//$(this).attr("href","test");
		//eventNoticeNode.innerHTML = sprintf("<strong>%s</strong>: Drag Event started.<br />%s", new Date(), eventNoticeNode.innerHTML);
	}
	
	/*
	 * The drag event reports to the user that dragging is on. 
	 */
	
	function dragEvent(e) {
		//dragEventNoticeNode.innerHTML = "Currently dragging. ";
		console.log("draging");
	}
	
	/*
	 * The dragend event logs to the user when the event had finished *and*
	 * also reports that dragging has now stopped.
	 */
	function userDragEndEvent(e) {
		
		//拖拽对象
		//var $this=$(this);
		//查询结果配置创建
		//var areaColumns = $("#clm");
		//createFieldLine($this, areaColumns, "columns");
		
		console.log("drag end");
	}
	
	function userDragLeaveListEvent(e){
		//setHelpVisibility(this, false);
	}
	
	function userDropListEvent(e) {
		/*
		 * To ensure that what we are dropping here is from this page
		 */
		//setHelpVisibility(this, false);
		//拖拽对象
		//var $this=$(this);
		//查询结果配置创建
		//var areaColumns = $("#clm");
		var type;
		if(this.id==="clm"){
			type="columns";
		}else if(this.id==="cdt"){
			type="conditions";
		}
		
		createFieldLine(currentlyDraggedNode, this, type);
		
		userDragEndEvent(e);
	}
	
	function userDragOverListEvent(e) {
		//setHelpVisibility(this, true);
		EventHelpers.preventDefault(e);
	}
	
	function setHelpVisibility(node, isVisible) {
		var helpNodeId = node.id + "Help";
		var helpNode = document.getElementById(helpNodeId);
		
		if (isVisible) {
			helpNode.className =  'showHelp';
		} else {
			helpNode.className =  '';
		}
	}
	
	//拖拽对象, drop域, 域类型
	function createFieldLine($this, $area, type){
		
		//var conditionsDiv = $("#cdt");
		var area = $($area);
		var newLine = $("<div></div>");
		newLine.css("display","inline-block");
		newLine.css("width","100%");
		var newColumnId;
		
		if(type === "columns"){	
			newColumnId = "cf-column-"+idCdtCpt++;
		}else if(type === "conditions"){
			newColumnId = "cf-cindition-"+idCdtCpt++;
		}
		
		newLine.attr("id", newColumnId);
		//newColumn.css("white-space","nowrap");
		//alert($this.text());
		var field = $this.text();
		
		//当前字段所属的table
		var table = $this.parent().parent().parent().prev().text();
		
		//当前拖拽的字段
		var fieldNode = $("<div></div>").text(field);
		fieldNode.css("float","left");
		fieldNode.css("padding-right","10px");
		
		//重命名当前字段
		var renameNode = $("<div></div>");
		var aRename = $("<a></a>").text(field);
		//var inputRename = $("<input/>").text(field);
		//inputRename.attr("type","hidden");
		
		//重命名字段
		aRename.editable("click", function(e){
			  //alert(e.value);
		});
		
		renameNode.css("float","left");
		renameNode.css("padding-right","10px");
		//renameNode.css('margin-left','10px');
		//renameNode.css('margin-right','10px');

		renameNode.append(aRename);
		
		//删除此配置条件行
		var cdtRemover = $("<div></div>");
		var aRemover = $("<a>remove</a>");
		
		aRemover.click(function(){
			$(this).parent().parent().remove();
		});
		
		cdtRemover.append(aRemover);

		cdtRemover.css("float","left");
		cdtRemover.css("padding-right","10px");
		
		if(type === "columns"){
			newLine.append(fieldNode,renameNode,cdtRemover);
		}else if(type === "conditions"){
			if(!$.trim( area.html() ).length){
				newLine.append(fieldNode, createComparator(), cdtRemover);
			}else{
				//如果是非首个条件，则加入and/or
				newLine.append(cureateAndOr , fieldNode, createComparator(), cdtRemover);
			}
		}
		area.append(newLine);
	}
	
	//比较器
	function createComparator(){
		
		var comparatorArea = $("<div></div>");
		comparatorArea.css("float","left");
		comparatorArea.css("padding-right","10px");
		//comparatorArea.css("width","auto");
		
		var comparator = $("<select></select>");
		comparator.css("float","left");
		var $datas = $('#comparatorList').tmpl();
		comparator.html($datas);
		
		comparatorArea.append(comparator);
		
		comparator.change(function(){
			if($(this).val()==="8"){
				comparatorArea.find("div").empty();
				comparatorArea.append(createComparatorValue("query"));
			}else{
				comparatorArea.find("div").empty();
				comparatorArea.append(createComparatorValue("normal"));
			}
		});
		
		if(comparator.val()==="1"){
			comparatorArea.append(createComparatorValue("normal"));
		}
		
		return comparatorArea;
	}
	
	function createComparatorValue(type){
		// 条件值输入域
		var value = $("<div></div>");
		//value.attr("id","compareValue");
		value.css("float","left");
		//value.css("padding-right","10px");
		var valueA;
	
		if(type==="normal"){
			valueA = $("<a></a>").text("input value");
			valueA.editable("click", function(e){
				
			});
		}else if(type==="query"){
			valueA = $("<a></a>").text("edit query");
			valueA.click(function(){
				createSubqueryDialog().show();
			});	
		}
		value.append(valueA);
		return value;
	}
}

	function createSubqueryDialog(){
		
		var d = art.dialog({
		    title: 'message',
		    content: '<div>'+$("#preview").html()+"</div>",
		    close: function(){
		    	$(this).dialog("close");
		    },
			lock : true,
			drag : false
		});
		
		return d;
	}
	
	function cureateAndOr(){
		var andorDiv = $("<div></div>");
		//andorDiv.css("display","inline-block");
		andorDiv.css("float","left");
		andorDiv.css("padding-right","10px");
		var andor=$("<select></select>");
		var $datas = $('#andorbox').tmpl();
		andor.html($datas);
		andorDiv.append(andor);
		return andorDiv;
	}

// fixes visual cues in IE and Chrome.
DragDropHelpers.fixVisualCues=true;

EventHelpers.addPageLoadEvent('dragObject.init');
