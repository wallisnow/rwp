<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/common/taglibs.jsp" %>

<%
String titleName = "菜单监控日志";
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
  <Created>2012-05-02T07:43:20Z</Created>
  <Company>微软中国</Company>
  <Version>14.00</Version>
 </DocumentProperties>
 <OfficeDocumentSettings xmlns="urn:schemas-microsoft-com:office:office">
  <AllowPNG/>
 </OfficeDocumentSettings>
 <ExcelWorkbook xmlns="urn:schemas-microsoft-com:office:excel">
  <WindowHeight>8310</WindowHeight>
  <WindowWidth>18225</WindowWidth>
  <WindowTopX>480</WindowTopX>
  <WindowTopY>30</WindowTopY>
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
  <Style ss:ID="s62">
   <Alignment ss:Horizontal="Center" ss:Vertical="Center"/>
   <Borders>
    <Border ss:Position="Bottom" ss:LineStyle="Continuous" ss:Weight="1"/>
    <Border ss:Position="Left" ss:LineStyle="Continuous" ss:Weight="1"/>
    <Border ss:Position="Right" ss:LineStyle="Continuous" ss:Weight="1"/>
    <Border ss:Position="Top" ss:LineStyle="Continuous" ss:Weight="1"/>
   </Borders>
   <Font ss:FontName="宋体" x:CharSet="134" ss:Size="11" ss:Color="#FFFFFF"
    ss:Bold="1"/>
   <Interior ss:Color="#0070C0" ss:Pattern="Solid"/>
  </Style>
  <Style ss:ID="s63">
   <Alignment ss:Horizontal="Center" ss:Vertical="Center" ss:WrapText="1"/>
   <Borders>
    <Border ss:Position="Bottom" ss:LineStyle="Continuous" ss:Weight="1"/>
    <Border ss:Position="Left" ss:LineStyle="Continuous" ss:Weight="1"/>
    <Border ss:Position="Right" ss:LineStyle="Continuous" ss:Weight="1"/>
    <Border ss:Position="Top" ss:LineStyle="Continuous" ss:Weight="1"/>
   </Borders>
   <Font ss:FontName="宋体" x:CharSet="134" ss:Size="11" ss:Color="#FFFFFF"
    ss:Bold="1"/>
   <Interior ss:Color="#0070C0" ss:Pattern="Solid"/>
  </Style>
  <Style ss:ID="s64">
   <Borders>
    <Border ss:Position="Bottom" ss:LineStyle="Continuous" ss:Weight="1"/>
    <Border ss:Position="Left" ss:LineStyle="Continuous" ss:Weight="1"/>
    <Border ss:Position="Right" ss:LineStyle="Continuous" ss:Weight="1"/>
    <Border ss:Position="Top" ss:LineStyle="Continuous" ss:Weight="1"/>
   </Borders>
  </Style>
  <Style ss:ID="s65">
   <Borders>
    <Border ss:Position="Bottom" ss:LineStyle="Continuous" ss:Weight="1"/>
    <Border ss:Position="Left" ss:LineStyle="Continuous" ss:Weight="1"/>
    <Border ss:Position="Right" ss:LineStyle="Continuous" ss:Weight="1"/>
    <Border ss:Position="Top" ss:LineStyle="Continuous" ss:Weight="1"/>
   </Borders>
   <NumberFormat ss:Format="yyyy/mm/dd\ hh:mm:ss"/>
  </Style>
 </Styles>
 <Worksheet ss:Name="Sheet1">
  <Table ss:ExpandedColumnCount="8" ss:ExpandedRowCount="65000" x:FullColumns="1"
   x:FullRows="1" ss:DefaultColumnWidth="54" ss:DefaultRowHeight="13.5">
   <Column ss:AutoFitWidth="0" ss:Width="75.75" ss:Span="1"/>
   <Column ss:Index="3" ss:AutoFitWidth="0" ss:Width="99.75" ss:Span="1"/>
   <Column ss:Index="5" ss:AutoFitWidth="0" ss:Width="123.75"/>
   <Column ss:AutoFitWidth="0" ss:Width="183.75"/>
   <Column ss:AutoFitWidth="0" ss:Width="135.75" ss:Span="1"/>
   <Row ss:AutoFitHeight="0" ss:Height="20.0625">
    <Cell ss:StyleID="s62"><Data ss:Type="String">用户ID</Data></Cell>
    <Cell ss:StyleID="s62"><Data ss:Type="String">用户名</Data></Cell>
    <Cell ss:StyleID="s62"><Data ss:Type="String">请求IP</Data></Cell>
    <Cell ss:StyleID="s62"><Data ss:Type="String">组织架构全名</Data></Cell>
    <Cell ss:StyleID="s62"><Data ss:Type="String">菜单名称</Data></Cell>
    <Cell ss:StyleID="s62"><Data ss:Type="String">菜单参数值</Data></Cell>
    <Cell ss:StyleID="s62"><Data ss:Type="String">查询开始时间</Data></Cell>
    <Cell ss:StyleID="s63"><Data ss:Type="String">查询结束时间</Data></Cell>
   </Row>
   <c:forEach var="item" items="${page.datas}" >
   <Row ss:AutoFitHeight="0" ss:Height="20.0625">
    <Cell ss:StyleID="s64"><Data ss:Type="String"><c:out value="${item.user.userId}" /></Data></Cell>
    <Cell ss:StyleID="s64"><Data ss:Type="String"><c:out value="${item.user.userName}"/></Data></Cell>
    <Cell ss:StyleID="s64"><Data ss:Type="String"><c:out value="${item.detVO.userIp}"/></Data></Cell>
    <Cell ss:StyleID="s64"><Data ss:Type="String"><cx:Code2Name definition="!com.congxing.system.sysdp.model.SysDpVO*dpId*dpFullName" value="${item.user.dpId}"/></Data></Cell>
    <Cell ss:StyleID="s64"><Data ss:Type="String"><c:out value="${item.configVO.monitorName}"/></Data></Cell>
    <Cell ss:StyleID="s64"><Data ss:Type="String"><c:out value="${item.detVO.oprParams}"/></Data></Cell>
    <Cell ss:StyleID="s65"><Data ss:Type="String"><fmt:formatDate value="${item.detVO.beginTime}" pattern="yyyy-MM-dd HH:mm:ss"/></Data></Cell>
    <Cell ss:StyleID="s65"><Data ss:Type="String"><fmt:formatDate value="${item.detVO.endTime}" pattern="yyyy-MM-dd HH:mm:ss"/></Data></Cell>
   </Row>
   </c:forEach>
  </Table>
 </Worksheet>
</Workbook>
