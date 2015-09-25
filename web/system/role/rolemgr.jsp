<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/common/taglibs.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>

  
<frameset cols="60%,*" frameborder="no" border="0">
  <frame src="${ctx}/system/role/list.action" name="roleList" id="roleList"  scrolling="auto" noresize="noresize">
  <frame src="${ctx}/system/role/menutree.action" name="roleMenu" id="roleMenu">
</frameset>

</html>
