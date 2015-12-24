<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title><s:text name="title" /></title>
<%@ include file="/common/header.jsp"%>

<script type="text/javascript">

	function allowDrop(ev) {
	    ev.preventDefault();
	}
	
	function drag(ev) {
	    ev.dataTransfer.setData("text", ev.target.id);
	}
	
	function drop(ev) {
	    ev.preventDefault();
	    var data = ev.dataTransfer.getData("text");
	    ev.target.appendChild(document.getElementById(data));
	}

</script>

</head>
<body scroll="no">
	<form method="post" id="contentForm">
			<div>
				<div id="conditions">
				</div>
			</div>
	</form>

</body>
</html>