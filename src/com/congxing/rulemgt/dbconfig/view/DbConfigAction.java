/**  

* @Title: DbConfigAction.java

* @Package com.congxing.rulemgt.dbconfig.view

* @Description: TODO(用一句话描述该文件做什么)

* @author LIUKANGJIN

* @date 2014-1-24 上午10:58:27

* @version V1.0  

*/

package com.congxing.rulemgt.dbconfig.view;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Map;

import com.congxing.core.utils.ParamsBuilder;
import com.congxing.core.web.sequence.Sequence;
import com.congxing.core.web.struts2.BaseAction;
import com.congxing.core.web.struts2.JsonResult;
import com.congxing.rulemgt.dbconfig.model.DbConfigVO;
import com.congxing.system.user.model.UserVO;
import com.opensymphony.xwork2.ActionSupport;

/**
 * <p>@Description: </p>
 *
 * <p>@author: LIUKANGJIN</p>
 *
 * <p>@date: 2014-1-24<p>
 *
 * <p>@version: V.1.0<p>
 *
 */
@SuppressWarnings("serial")
public class DbConfigAction extends BaseAction {
	
	public DbConfigAction(){
		this.voClass = DbConfigVO.class;
		this.pkNameArray = new String[]{"dbId"};
	}
	
	protected void setAddEntityVO(Object entityVO, Map<String, Object> paramsMap, UserVO userVO) throws Exception {
		DbConfigVO dbConfigVO = (DbConfigVO) entityVO;
		paramsMap.clear();
		dbConfigVO.setDbId(Sequence.getSequence());
	}
	
	/**
	 * 数据源测试
	 * @throws IOException
	 */
	@SuppressWarnings("unchecked")
	public void testDbConnctionJson() throws IOException{
		try{
			paramsMap = ParamsBuilder.buildMapFromHttpRequest();
			String driverClass = (String)paramsMap.get("driverClass");
			String jdbcUrl = (String)paramsMap.get("jdbcUrl");
			String user = (String)paramsMap.get("user");
			String password = (String)paramsMap.get("password");
			String msg = this.testConnection(driverClass, jdbcUrl, user, password);
			this.sendJsonMessage(JsonResult.create(ActionSupport.SUCCESS,msg));
		}catch(Exception ex){
			this.addErrorMessage(ex.getMessage());
			ex.printStackTrace();
			this.sendJsonMessage(JsonResult.create(ActionSupport.ERROR, ex.getMessage()));
		}
	}
	private String  testConnection(String driverClass,String jdbcUrl,String user,String password) {
		String msg = "SUCCESS";
//		Statement statement = null;
		Connection conn = null;
		try {
			Class.forName(driverClass);
			conn = java.sql.DriverManager.getConnection(jdbcUrl, user, password);
//			statement = conn.createStatement();
//			statement.execute("select * from dual");
		} catch (ClassNotFoundException e) {
			msg = "数据驱动有误！";
			e.printStackTrace();
		} catch (SQLException e) {
			msg = e.getMessage();
			e.printStackTrace();
		}   finally{
//			if(statement!=null){
//				try {
//					statement.close();
//				} catch (SQLException e) {
//					e.printStackTrace();
//				}
//			}
			if(conn!=null){
				try {
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return msg;
	}
}
