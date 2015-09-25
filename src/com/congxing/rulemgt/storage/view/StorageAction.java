/**
 * auto-generated code
 * 2013-12-23 20:12:04
 */
package com.congxing.rulemgt.storage.view;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.congxing.core.dbtool.DBConnectionUtils;
import com.congxing.core.hibernate.Page;
import com.congxing.core.pack.DefaultBody;
import com.congxing.core.pack.DefaultHeader;
import com.congxing.core.pack.PackInfo;
import com.congxing.core.utils.DateFormatFactory;
import com.congxing.core.utils.JsonUtils;
import com.congxing.core.utils.ParamsBuilder;
import com.congxing.core.utils.PkUtils;
import com.congxing.core.utils.Struts2Utils;
import com.congxing.core.web.constant.Constants;
import com.congxing.core.web.sequence.Sequence;
import com.congxing.core.web.struts2.BaseAction;
import com.congxing.core.web.struts2.JsonResult;
import com.congxing.rulemgt.dbconfig.model.DbConfigVO;
import com.congxing.rulemgt.ruleset.dao.QueryRulesetStrategy;
import com.congxing.rulemgt.ruleset.model.RuleExchangeVO;
import com.congxing.rulemgt.ruleset.model.RulesetBoVO;
import com.congxing.rulemgt.ruleset.model.RulesetListVO;
import com.congxing.rulemgt.ruleset.model.RulesetVO;
import com.congxing.rulemgt.ruleset.socket.SocketPool;
import com.congxing.rulemgt.ruleset.socket.SocketPoolFactory;
import com.congxing.rulemgt.storage.dao.DeleteStorageStrategy;
import com.congxing.rulemgt.storage.dao.SaveStorageStrategy;
import com.congxing.rulemgt.storage.model.StorageParamVO;
import com.congxing.rulemgt.storage.model.StorageVO;
import com.opensymphony.xwork2.ActionSupport;

/**
 * <p>
 * Title: RuleStorageInfoAction <br/>
 * Description: Struts main control Class for RuleStorageInfo <br/>
 * Copyright: Copyright (c) 2006 <br/>
 * Company: Revenco Tech Ltd. <br/>
 * </p>
 * 
 * @author jinjiebin
 * @version 1.0
 * @since 2013-12-23
 */
@SuppressWarnings("serial")
public class StorageAction extends BaseAction {
	static Logger logger = Logger.getLogger(StorageAction.class);
    public StorageAction() {
        this.voClass = StorageVO.class;
        this.pkNameArray = new String[] { "storageId" };        
    }
	
	@Override
	public String list() {
		try{
			paramsMap = ParamsBuilder.buildMapFromHttpRequest();
			String rulesetPK = (String)paramsMap.get("rulesetPK");
			if(StringUtils.isBlank(rulesetPK)){
				throw new Exception("非法进入");
			}
			String[] PK = StringUtils.split(rulesetPK, "\\|");
			RulesetListVO listVO = new RulesetListVO();
			listVO.set_leq_rulesetId(PK[0]);
			listVO.set_seq_rulesetVersion(PK[1]);
			
			Page page = (Page) this.getService().doProcess(new QueryRulesetStrategy(listVO));
			
			if(page.getTotalCount() < 0){
				throw new Exception("非法进入");
			}else if(page.getTotalCount() > 1){
				throw new Exception("非法进入");
			}
			this.setTargetObjectToParamsMap(listVO, paramsMap);
			this.setTargetObjectToParamsMap(page.getDatas().get(0), paramsMap);
			
		}catch(Exception ex){
			ex.printStackTrace();
			this.addErrorMessage(ex.getMessage());
			return ActionSupport.ERROR;
		}
		return ActionSupport.SUCCESS;
	}

	@SuppressWarnings("unchecked")
	public String save(){
		try{
			paramsMap = ParamsBuilder.buildMapFromHttpRequest();
			
			StorageVO storageVO = new StorageVO();
			this.setParamsMapToTargetObject(paramsMap, storageVO);
			
			String[] colNames = this.getParamValueByParamsMap(paramsMap, "colName");
			String[] paramTypes = this.getParamValueByParamsMap(paramsMap, "paramType");
			String[] paramValues = this.getParamValueByParamsMap(paramsMap, "paramValue");
			
			List<StorageParamVO> paramDatas = new ArrayList<StorageParamVO>();
			for(int idx = 0; idx < colNames.length; idx++){
				StorageParamVO paramVO = new StorageParamVO();
				paramVO.setStorageId(storageVO.getStorageId());
				paramVO.setColName(colNames[idx]);
				paramVO.setParamType(paramTypes[idx]);
				paramVO.setParamValue(paramValues[idx]);
				paramDatas.add(paramVO);
			}
			storageVO.setParamDatas(paramDatas);
			
			SaveStorageStrategy strategy = new SaveStorageStrategy(storageVO, userVO);
			String isNew = (String) paramsMap.get(Constants.PAGE_ISNEW);
			if(Constants.STRING_TRUE.equals(isNew)){
				strategy.setOprType(Constants.OPRTYPE_ADD);
			}else{
				strategy.setOprType(Constants.OPRTYPE_MODIFY);
			}
			storageVO = (StorageVO) this.getService().doProcess(strategy);
			

			String hql = "FROM RulesetBoVO WHERE rulesetId = ? AND rulesetVersion = ?";
			storageVO.setBoDatas((List<RulesetBoVO>)this.getService().doFindByHQL(hql, storageVO.getRulesetId(), storageVO.getRulesetVersion()));
			
			this.setTargetObjectToParamsMap(storageVO, paramsMap);
			
			this.addInfoMessage("保存成功");
			this.setEdit(false);
			this.setNew(false);
		}catch(Exception ex){
			this.addErrorMessage(ex.getMessage());
			ex.printStackTrace();
			return ActionSupport.ERROR;
		}
		return ActionSupport.SUCCESS;
	}
	
	
	/**
	 * 删除存储器信息
	 * @throws IOException
	 */
	public void deleteJson() throws IOException {
		try{
			paramsMap = ParamsBuilder.buildMapFromHttpRequest();
			if(!paramsMap.containsKey("storageIds")){
				throw new Exception("非法进入");
			}
			String[] storageIds = StringUtils.split((String)paramsMap.get("storageIds"), "\\|");
			if(storageIds.length < 1){
				throw new Exception("非法进入");
			}
			DeleteStorageStrategy strategy = new DeleteStorageStrategy(storageIds, userVO);
			this.getService().doProcess(strategy);
			this.sendJsonMessage(JsonResult.create(ActionSupport.SUCCESS, "删除成功"));
		}catch(Exception ex){
			this.addErrorMessage(ex.getMessage());
			ex.printStackTrace();
			this.sendJsonMessage(JsonResult.create(ActionSupport.ERROR, ex.getMessage()));
		}
	}
	
	public String add(){
		try{
			paramsMap = ParamsBuilder.buildMapFromHttpRequest();
			String rulesetPK = (String)paramsMap.get("rulesetPK");
			if(StringUtils.isBlank(rulesetPK)){
				throw new Exception("非法进入");
			}
			RulesetVO rulesetVO = (RulesetVO) this.getService().doFindByPK(RulesetVO.class, PkUtils.getPkVO(RulesetVO.class, new String[] {"rulesetId", "rulesetVersion"}, rulesetPK));
			if(null == rulesetVO){
				throw new Exception("规则集信息不存在");
			}
			
			StorageVO storageVO = new StorageVO();
			storageVO.setStorageId(Sequence.getSequence());
			storageVO.setRulesetId(rulesetVO.getRulesetId());
			storageVO.setRulesetVersion(rulesetVO.getRulesetVersion());
			//初始化数据
			storageVO.setStorageName("STORAGE_"+DateFormatFactory.getInstance("yyyyMMdd_HHmm").format(new Date()).replace("-", ""));

			this.setTargetObjectToParamsMap(storageVO, paramsMap);
			this.setNew(true);
			this.setEdit(true);
		}catch(Exception ex){
			ex.printStackTrace();
			this.addErrorMessage(ex.getMessage());
			return ActionSupport.ERROR;
		}
		return ActionSupport.SUCCESS;
	}
	
	@SuppressWarnings("unchecked")
	public String edit(){
		try{
			paramsMap = ParamsBuilder.buildMapFromHttpRequest();
			String storageId = (String)paramsMap.get("storageId");
			if(StringUtils.isBlank(storageId)){
				throw new Exception("非法进入");
			}
			StorageVO storageVO = (StorageVO) this.getService().doFindByPK(StorageVO.class, PkUtils.getPkVO(StorageVO.class, "storageId", storageId));
			if(null == storageVO){
				throw new Exception("存储器信息不存在");
			}
			List<StorageParamVO> paramDatas = (List<StorageParamVO>) this.getService().doFindByProperty(StorageParamVO.class, "storageId", storageId);
			storageVO.setParamDatas(paramDatas);

			String hql = "FROM RulesetBoVO WHERE rulesetId = ? AND rulesetVersion = ?";
			storageVO.setBoDatas((List<RulesetBoVO>)this.getService().doFindByHQL(hql, storageVO.getRulesetId(), storageVO.getRulesetVersion()));
			
			this.setTargetObjectToParamsMap(storageVO, paramsMap);
			Struts2Utils.setRequestAttribute("StorageVO", storageVO);
			this.setEdit(true);
			this.setNew(false);
		}catch(Exception ex){
			this.addErrorMessage(ex.getMessage());
			ex.printStackTrace();
			return ActionSupport.ERROR;
		}
		return ActionSupport.SUCCESS;
	} 
	/**
	 * 表字段自动填充
	 * @throws IOException
	 */
	public void	autoSetColumnJson2() throws IOException{
		try{
			String sql = "select COLUMN_NAME  from information_schema.`COLUMNS` where TABLE_NAME=?";
			paramsMap = ParamsBuilder.buildMapFromHttpRequest();
			String dbId = (String)paramsMap.get("dbId");
			String tableName = (String)paramsMap.get("tableName");
			
			DbConfigVO configVO = (DbConfigVO) this.getService().doFindByPK(DbConfigVO.class,PkUtils.getPkVO(DbConfigVO.class, new String[] { "dbId" },dbId) );
			
			if(configVO.getDriverClass().contains("oracle")){
				sql = "select column_name from user_tab_columns where TABLE_NAME=?";
			}else if(configVO.getDriverClass().contains("mysql")){
				sql = "select COLUMN_NAME  from information_schema.`COLUMNS` where TABLE_NAME=?";
			}
			Connection conn = DBConnectionUtils.getConnection(configVO.getDriverClass(), configVO.getJdbcUrl(), configVO.getUser(), configVO.getPassword());
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1,tableName.toUpperCase());
			ResultSet rs = ps.executeQuery();
			List<String> columnList = new ArrayList<String>();
			while(rs.next()){
				String columnName = rs.getString("column_name");
				columnList.add(columnName);
			}
			if(columnList.size()>0){
				this.sendJsonMessage(JsonResult.create(ActionSupport.SUCCESS, JsonUtils.toJson(columnList)));
			}else{
				this.sendJsonMessage(JsonResult.create(ActionSupport.ERROR,"没有查询到相关字段，请确认表名是否正确！"));
			}
			
		}catch(Exception ex){
			this.addErrorMessage(ex.getMessage());
			ex.printStackTrace();
			this.sendJsonMessage(JsonResult.create(ActionSupport.ERROR, ex.getMessage()));
		}
	} 
	/**
	 * 表字段自动填充
	 * @throws IOException
	 */
	public void	autoSetColumnJson() throws IOException{
		try{
			paramsMap = ParamsBuilder.buildMapFromHttpRequest();
			String dbId = (String)paramsMap.get("dbId");
			String tableName = (String)paramsMap.get("tableName");
			
			DbConfigVO configVO = (DbConfigVO) this.getService().doFindByPK(DbConfigVO.class,PkUtils.getPkVO(DbConfigVO.class, new String[] { "dbId" },dbId) );
			
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("configVO", configVO);
			map.put("tableName", tableName);
			
			DefaultHeader header = new DefaultHeader();
			header.setCmdId("10003");
			DefaultBody body = new DefaultBody();
			body.setDatatrans(JsonUtils.toJson(map));
			
			PackInfo packInfo = new PackInfo(header, body);
			
			SocketPool socketPool = SocketPoolFactory.getSocketPool("drools_server");
			if(socketPool == null){
				this.sendJsonMessage(new JsonResult(ActionSupport.ERROR, "请检查后台服务配置是否错误！"));
				return;
			}
			Socket client = null;
	        DataOutputStream out = null;
	        DataInputStream in = null;
	        JsonResult result = null;
	        
			try{
				client = socketPool.getSocket();
				out = new DataOutputStream((client.getOutputStream()));
				
				logger.info("StorageAction Begin RuleExchangeVO[" + JsonUtils.toJson(packInfo) + "]");
				
				byte[] request = packInfo.getBytes();
				
	            out.write(request);
	            out.flush();
	            
	            in = new DataInputStream(client.getInputStream());
	            byte[] reply = new byte[]{};
	            byte[] buffer = new byte[1024];
	            int byteread = 0;
	            while((byteread = in.read(buffer)) != -1){
	            	byte[] tmpReadBuf = new byte[byteread];
	            	System.arraycopy(buffer, 0, tmpReadBuf, 0, byteread);
	            	reply = ArrayUtils.addAll(reply, tmpReadBuf);
	            }
	            packInfo.setPackInfo(reply);
	            logger.info("StorageAction End Result[" + JsonUtils.toJson(packInfo) + "]");
	            short code = Short.parseShort(BeanUtils.getProperty(packInfo.getBody(), "resultcode"));
	            String datatrans = BeanUtils.getProperty(packInfo.getBody(), "datatrans");
	            if(0 == code){
	            	result = new JsonResult(ActionSupport.SUCCESS, datatrans);
	            }else{
	            	result = new JsonResult(ActionSupport.ERROR, datatrans);
	            }
	            this.sendJsonMessage(result);
			}catch(Exception ex){
				ex.printStackTrace();
				throw new Exception(ex.getMessage());
			}finally{
				if(null != in){
					in.close();
				}
				if(null != out){
					out.close();
				}
				if(null != client){
					client.close();
				}
				socketPool.free(client);
			}
		}catch(Exception ex){
			this.addErrorMessage(ex.getMessage());
			ex.printStackTrace();
			this.sendJsonMessage(JsonResult.create(ActionSupport.ERROR, ex.getMessage()));
		}
	} 
}

