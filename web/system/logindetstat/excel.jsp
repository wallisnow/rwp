<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/common/taglibs.jsp" %>

<%
String titleName = "系统登录及菜单访问统计";
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
  <LastAuthor>admin</LastAuthor>
  <Created>2012-05-11T03:11:23Z</Created>
  <Company>微软中国</Company>
  <Version>12.00</Version>
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
  <Style ss:ID="s70">
   <Alignment ss:Horizontal="Center" ss:Vertical="Center"/>
   <Borders>
    <Border ss:Position="Bottom" ss:LineStyle="Continuous" ss:Weight="1"/>
    <Border ss:Position="Left" ss:LineStyle="Continuous" ss:Weight="1"/>
    <Border ss:Position="Right" ss:LineStyle="Continuous" ss:Weight="1"/>
    <Border ss:Position="Top" ss:LineStyle="Continuous" ss:Weight="1"/>
   </Borders>
   <Interior ss:Color="#0070C0" ss:Pattern="Solid"/>
  </Style>
  <Style ss:ID="s71">
   <Alignment ss:Horizontal="Center" ss:Vertical="Center"/>
   <Borders>
    <Border ss:Position="Bottom" ss:LineStyle="Continuous" ss:Weight="1"/>
    <Border ss:Position="Left" ss:LineStyle="Continuous" ss:Weight="1"/>
    <Border ss:Position="Right" ss:LineStyle="Continuous" ss:Weight="1"/>
    <Border ss:Position="Top" ss:LineStyle="Continuous" ss:Weight="1"/>
   </Borders>
  </Style>
 </Styles>
 <Worksheet ss:Name="系统登录及菜单访问统计表">
  <Table ss:ExpandedColumnCount="7" ss:ExpandedRowCount="65000" x:FullColumns="1"
   x:FullRows="1" ss:DefaultColumnWidth="54" ss:DefaultRowHeight="13.5">
   <Column ss:Index="2" ss:AutoFitWidth="0" ss:Width="63"/>
   <Column ss:AutoFitWidth="0" ss:Width="88.5"/>
   <Column ss:AutoFitWidth="0" ss:Width="163.5"/>
   <Column ss:Index="6" ss:AutoFitWidth="0" ss:Width="110.25"/>
   <Column ss:AutoFitWidth="0" ss:Width="65.25"/>
   <Row ss:AutoFitHeight="0" ss:Height="33.75">
    <Cell ss:StyleID="s70"><Data ss:Type="String">日期</Data></Cell>
    <Cell ss:StyleID="s70"><Data ss:Type="String">用户ID</Data></Cell>
    <Cell ss:StyleID="s70"><Data ss:Type="String">用户名称</Data></Cell>
    <Cell ss:StyleID="s70"><Data ss:Type="String">组织ID</Data></Cell>
    <Cell ss:StyleID="s70"><Data ss:Type="String">组织名称</Data></Cell>
    <Cell ss:StyleID="s70"><Data ss:Type="String">登陆次数</Data></Cell>
    <Cell ss:StyleID="s70"><Data ss:Type="String">点击的菜单次数</Data></Cell>
   </Row>
   <c:forEach var="item" items="${page.datas}" >
   <Row ss:AutoFitHeight="0" ss:Height="24.9375">
    <Cell ss:StyleID="s71"><Data ss:Type="String"><c:out value="${item.statDay}" /></Data></Cell>
    <Cell ss:StyleID="s71"><Data ss:Type="String"><c:out value="${item.userId}" /></Data></Cell>
    <Cell ss:StyleID="s71"><Data ss:Type="String"><c:out value="${item.userVO.userName}" /></Data></Cell>
    <Cell ss:StyleID="s71"><Data ss:Type="String"><c:out value="${item.userVO.dpId}" /></Data></Cell>
    <Cell ss:StyleID="s71"><Data ss:Type="String"><cx:Code2Name definition="!com.congxing.system.sysdp.model.SysDpVO*dpId*dpFullName" value="${item.userVO.dpId}"/></Data></Cell>
    <Cell ss:StyleID="s71"><Data ss:Type="Number"><c:out value="${item.loginNum}" /></Data></Cell>
    <Cell ss:StyleID="s71"><Data ss:Type="Number"><c:out value="${item.visitNum}" /></Data></Cell>
   </Row>
   </c:forEach>
  </Table>
 </Worksheet>
</Workbook>
