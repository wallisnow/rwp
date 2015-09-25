<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/common/taglibs.jsp" %>

<%
String titleName = "用户角色信息";
response.addHeader("Content-disposition", "attachment;filename=" + (new String(titleName.getBytes("GBK"), "iso-8859-1")) + ".xls");
%>

<?xml version="1.0"?>
<?mso-application progid="Excel.Sheet"?>
<Workbook xmlns="urn:schemas-microsoft-com:office:spreadsheet"
 xmlns:o="urn:schemas-microsoft-com:office:office"
 xmlns:x="urn:schemas-microsoft-com:office:excel"
 xmlns:ss="urn:schemas-microsoft-com:office:spreadsheet"
 xmlns:html="http://www.w3.org/TR/REC-html40">
 <DocumentProperties xmlns="urn:schemas-microsoft-com:office:office">
  <Author>微软用户</Author>
  <LastAuthor>微软用户</LastAuthor>
  <Created>2012-05-11T03:11:23Z</Created>
  <Company>微软中国</Company>
  <Version>14.00</Version>
 </DocumentProperties>
 <OfficeDocumentSettings xmlns="urn:schemas-microsoft-com:office:office">
  <AllowPNG/>
 </OfficeDocumentSettings>
 <ExcelWorkbook xmlns="urn:schemas-microsoft-com:office:excel">
  <WindowHeight>4935</WindowHeight>
  <WindowWidth>13905</WindowWidth>
  <WindowTopX>120</WindowTopX>
  <WindowTopY>15</WindowTopY>
  <ProtectStructure>False</ProtectStructure>
  <ProtectWindows>False</ProtectWindows>
 </ExcelWorkbook>
 <Styles>
  <Style ss:ID="Default" ss:Name="Normal">
   <Alignment ss:Vertical="Center"/>
   <Borders/>
   <Font ss:FontName="宋体" x:CharSet="134" ss:Size="11" ss:Color="#000000"/>
   <Interior/>
   <NumberFormat/>
   <Protection/>
  </Style>
  <Style ss:ID="s91">
   <Alignment ss:Horizontal="Center" ss:Vertical="Center"/>
   <Borders>
    <Border ss:Position="Bottom" ss:LineStyle="Continuous" ss:Weight="1"/>
    <Border ss:Position="Left" ss:LineStyle="Continuous" ss:Weight="1"/>
    <Border ss:Position="Right" ss:LineStyle="Continuous" ss:Weight="1"/>
    <Border ss:Position="Top" ss:LineStyle="Continuous" ss:Weight="1"/>
   </Borders>
   <Font ss:FontName="宋体" x:CharSet="134" ss:Size="11" ss:Color="#FFFFFF"
    ss:Bold="1"/>
   <Interior ss:Color="#4F81BD" ss:Pattern="Solid"/>
  </Style>
  <Style ss:ID="s92">
   <Alignment ss:Horizontal="Center" ss:Vertical="Center" ss:WrapText="1"/>
   <Borders>
    <Border ss:Position="Bottom" ss:LineStyle="Continuous" ss:Weight="1"/>
    <Border ss:Position="Left" ss:LineStyle="Continuous" ss:Weight="1"/>
    <Border ss:Position="Right" ss:LineStyle="Continuous" ss:Weight="1"/>
    <Border ss:Position="Top" ss:LineStyle="Continuous" ss:Weight="1"/>
   </Borders>
   <Font ss:FontName="宋体" x:CharSet="134" ss:Size="11" ss:Color="#FFFFFF"
    ss:Bold="1"/>
   <Interior ss:Color="#4F81BD" ss:Pattern="Solid"/>
  </Style>
  <Style ss:ID="s93">
   <Borders>
    <Border ss:Position="Bottom" ss:LineStyle="Continuous" ss:Weight="1"/>
    <Border ss:Position="Left" ss:LineStyle="Continuous" ss:Weight="1"/>
    <Border ss:Position="Right" ss:LineStyle="Continuous" ss:Weight="1"/>
    <Border ss:Position="Top" ss:LineStyle="Continuous" ss:Weight="1"/>
   </Borders>
  </Style>
 </Styles>
 <Worksheet ss:Name="用户角色信息">
  <Table ss:ExpandedColumnCount="8" ss:ExpandedRowCount="65000" x:FullColumns="1"
   x:FullRows="1" ss:DefaultColumnWidth="54" ss:DefaultRowHeight="13.5">
   <Column ss:AutoFitWidth="0" ss:Width="75.75" ss:Span="1"/>
   <Column ss:Index="3" ss:AutoFitWidth="0" ss:Width="147.75"/>
   <Column ss:AutoFitWidth="0" ss:Width="75.75" ss:Span="1"/>
   <Column ss:Index="6" ss:AutoFitWidth="0" ss:Width="63.75" ss:Span="2"/>
   <Row ss:AutoFitHeight="0" ss:Height="24.9375">
    <Cell ss:StyleID="s91"><Data ss:Type="String">用户登录号</Data></Cell>
    <Cell ss:StyleID="s91"><Data ss:Type="String">用户名称</Data></Cell>
    <Cell ss:StyleID="s91"><Data ss:Type="String">组织架构全名</Data></Cell>
    <Cell ss:StyleID="s91"><Data ss:Type="String">角色标识</Data></Cell>
    <Cell ss:StyleID="s91"><Data ss:Type="String">角色名称</Data></Cell>
    <Cell ss:StyleID="s91"><Data ss:Type="String">角色状态</Data></Cell>
    <Cell ss:StyleID="s91"><Data ss:Type="String">开始时间</Data></Cell>
    <Cell ss:StyleID="s92"><Data ss:Type="String">结束时间</Data></Cell>
   </Row>
   <c:forEach var="item" items="${page.datas}" >
   <Row ss:AutoFitHeight="0" ss:Height="24.9375">
    <Cell ss:StyleID="s93"><Data ss:Type="String"><c:out value="${item.user.userId}" /></Data></Cell>
    <Cell ss:StyleID="s93"><Data ss:Type="String"><c:out value="${item.user.userName}" /></Data></Cell>
    <Cell ss:StyleID="s93"><Data ss:Type="String"><Data ss:Type="String"><cx:Code2Name definition="!com.congxing.system.sysdp.model.SysDpVO*dpId*dpFullName" value="${item.user.dpId}"/></Data></Data></Cell>
    <Cell ss:StyleID="s93"><Data ss:Type="String"><c:out value="${item.userOfRole.roleId}"/></Data></Cell>
    <Cell ss:StyleID="s93"><Data ss:Type="String"><c:out value="${item.role.roleName}" /></Data></Cell>
    <Cell ss:StyleID="s93"><Data ss:Type="String"><cx:Code2Name definition="$STATUS" value="${item.userOfRole.status}"/></Data></Cell>
    <Cell ss:StyleID="s93"><Data ss:Type="String"><c:out value="${item.userOfRole.beginTime}" /></Data></Cell>
    <Cell ss:StyleID="s93"><Data ss:Type="String"><c:out value="${item.userOfRole.endTime}" /></Data></Cell>
   </Row>
   </c:forEach>
  </Table>
 </Worksheet>
</Workbook>
