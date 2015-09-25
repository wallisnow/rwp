<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<HEAD>
	<title>十分抱歉，您要查看的网页当前已过期，或已被更名或删除！</title>
	<META http-equiv=Content-Type content="text/html; charset=UTF-8">
	<STYLE type=text/css>
		INPUT {FONT-SIZE: 12px}
		TD {FONT-SIZE: 12px}
		.p2 {FONT-SIZE: 12px}
		.p6 {FONT-SIZE: 12px; COLOR: #1b6ad8}
		A {COLOR: #1b6ad8; TEXT-DECORATION: none}
		A:hover {COLOR: red}
	</STYLE>

</HEAD>
<BODY oncontextmenu="return false" onselectstart="return false">
	<TABLE cellSpacing=0 cellPadding=0 width=540 align=center border=0>
		<TBODY>
			<TR>
				<TD vAlign=top height=270>
					<DIV align=center>
						<BR>
						<BR>
						<IMG height=211 src="${ctx}/common/404/error.gif" width=329>
						<BR>
						<TABLE cellSpacing=0 cellPadding=0 width="80%" border=0>
							<TBODY>
								<TR>
									<TD>
										<FONT color=#ff0000><IMG height=13 src="${ctx}/common/404/emessage.gif" width=12>&nbsp;无法访问本页的原因是：<cx:Msg/></FONT>
									</TD>
								</TR>
							</TBODY>
						</TABLE>
					</DIV>
				</TD>
			</TR>
			<TR>
				<TD height=5></TD>
			<TR>
				<TD align=middle>
					<CENTER>
						<TABLE cellSpacing=0 cellPadding=0 width=480 border=0>
							<TBODY>
								<TR>
									<TD width=6>
										<IMG height=26 src="${ctx}/common/404/left.gif" width=7>
									</TD>
									<TD background="${ctx}/common/404/bg.gif">
										<DIV align=center>
											<FONT class=p6>
												<A href="javascript:history.go(-1)">返回出错页</A> 
											</FONT>
										</DIV>
									</TD>
									<TD width=7>
										<IMG src="${ctx}/common/404/right.gif">
									</TD>
								</TR>
							</TBODY>
						</TABLE>
					</CENTER>
				</TD>
			</TR>
		</TBODY>
	</TABLE>
</BODY>
</HTML>
