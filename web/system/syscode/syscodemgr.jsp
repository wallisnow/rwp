<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/common/taglibs.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>

  
<frameset cols="250px,*" border="0">
  <frame src="${ctx}/system/syscode/tree.action" name="treeFrame" id="name" scrolling="no" noresize="noresize">
  <frame src="${ctx}/system/syscode/typeshow.action" name="contentFrame" id="contentFrame">
</frameset>

</html>