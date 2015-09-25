<%@ page language="java" import="java.io.*,java.sql.*,javax.sql.* ,javax.naming.*"%>
<%@ page contentType="text/html;charset=gb2312" %> 
<% 
String sDBDriver = "oracle.jdbc.driver.OracleDriver";
String sConnStr = "jdbc:oracle:thin:@127.0.0.1:1521:orcl";
Connection conn = null;
ResultSet rs = null;
Statement stmt = null;
try{
Class.forName(sDBDriver); 
conn = DriverManager.getConnection(sConnStr,"frnt","frnt");
stmt = conn.createStatement();

rs = stmt.executeQuery("select 1,2,3 from dual");
while (rs.next()){
out.println("" + rs.getInt(1) + "");
out.println(":"+"<br>");
}
rs.close(); 
}
catch(Exception e) 
{
out.println(e); 
} 


%>
