package com.congxing.system.sqlmanage.view;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.apache.commons.lang3.StringUtils;

import com.congxing.core.dbtool.DBConnectionUtils;
import com.congxing.core.utils.ParamsBuilder;
import com.congxing.core.utils.PkUtils;
import com.congxing.core.web.struts2.BaseAction;
import com.congxing.core.web.struts2.JsonResult;
import com.congxing.rulemgt.dbconfig.model.DbConfigVO;
import com.congxing.system.role.model.RoleVO;
import com.opensymphony.xwork2.ActionSupport;


@SuppressWarnings("serial")
public class SqlManageAction extends BaseAction{	
	
	public SqlManageAction(){
		this.voClass = RoleVO.class;
		this.pkNameArray = new String[]{"roleId"};
	}
	
	/**
	 * 表字段自动填充
	 * @throws IOException
	 */
	public void	excutesql() throws IOException{
		Connection conn = null;
		try{
			paramsMap = ParamsBuilder.buildMapFromHttpRequest();
			String dbId = (String)paramsMap.get("dbId");
			if(StringUtils.isBlank(dbId)){
				throw new Exception("数据源不能为空");
			}
			String sqlStr = java.net.URLDecoder.decode((String)paramsMap.get("sql"),"UTF-8"); 
			if(StringUtils.isBlank(sqlStr)){
				throw new Exception("SQL不能为空");
			}
			String[] sqlArr = sqlStr.split(";");
			
			DbConfigVO configVO = (DbConfigVO) this.getService().doFindByPK(DbConfigVO.class,PkUtils.getPkVO(DbConfigVO.class, new String[] { "dbId" },dbId) );
			
			conn = DBConnectionUtils.getConnection(configVO.getDriverClass(), configVO.getJdbcUrl(), configVO.getUser(), configVO.getPassword());
			conn.setAutoCommit(false);
			for(String sql:sqlArr){
				System.out.println("SQL:"+sql.toString());
				if(StringUtils.isNotBlank(sql.toString())){
					PreparedStatement ps = conn.prepareStatement(sql.toString());
					ps.execute();
				}
			}
			conn.commit();
			this.sendJsonMessage(JsonResult.create(ActionSupport.SUCCESS, "执行成功"));
		}catch(Exception ex){
			if(conn!=null){
				try {
					conn.rollback();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			ex.printStackTrace();
			this.sendJsonMessage(JsonResult.create(ActionSupport.ERROR, ex.getMessage()));
		}finally{
			if(conn!=null){
				try {
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
	} 

}
