<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<c:set var="ISEDIT" value="${'TRUE' eq paramsMap.ISEDIT}" />

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
	<head>

		<title><s:text name="title" />
		</title>
		<%@ include file="/common/header.jsp"%>

		<script type="text/javascript">
	<!--
	$(function(){
		initPageEnv("content");
	});
	function doExcutesql(){
		var formObj = form2Obj();
		formObj["dt"] = new Date() + Math.random();
		$.getJSON(ctx + "/system/sqlmanage/excutesql.action", formObj, function(result){
			$("#result").val(result.message );
		});
	}
	
	function form2Obj(){
		var formObj = {};
		$("form input").each(function(){
			$this = $(this);
			formObj[$this.attr("name")] = $this.val();
		});
		$("form textarea").each(function(){
			$this = $(this);
			formObj[$this.attr("name")] = $this.val();
		});
		$("form select").each(function(){
			$this = $(this);
			formObj[$this.attr("name")] = $this.val();
		});
		return formObj;
	}
	
	function doReturnRuleDatas(){
		var url = ctx + "/rulemgt/ruleset/listRule.action?rulesetPK=${paramsMap.rulesetPK}";
		window.location.href = url;
	}
	
	-->
	</script>
	</head>

	<body>
		<form method="post" id="contentForm" action="">
			<div class="formPageHeader">
				<div class="formContent">
					<p class="nowrap">
						<label>
							数据源
						</label>
						<cx:select name="dbId" value="%{paramsMap.dbId}"
							list="!com.congxing.rulemgt.dbconfig.model.DbConfigVO*dbId*dbName"
							emptyOption="true" headerKey="" headerValue="请选择..."
							cssClass="combox required" />
					</p>
					<p class="nowrap">
						<label>
							请输入SQL
						</label>
						<s:textarea name="sql" rows="8" cols="60" value=""></s:textarea>
					</p>
					<p class="nowrap">
						<label>
							结果
						</label>
						<s:textarea id="result" name="result" rows="5" cols="60" value=""
							disabled="true"></s:textarea>
					</p>
				</div>

				<div class="formBar">
					<c:if test="${ISEDIT}">
						<a href="javascript:void(0);"
							onclick="doExcutesql();return false;" class="linkbutton"
							data-options="iconCls:'icon-save'">执行 </a>
					</c:if>
				</div>
			</div>
		</form>
	</body>
</html>