/**
 * auto-generated code
 * 2013-06-05 03:16:38
 */
package com.congxing.rulemgt.reader.view;

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
import org.apache.commons.lang3.StringUtils;
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
import com.congxing.core.web.constant.Constants;
import com.congxing.core.web.sequence.Sequence;
import com.congxing.core.web.struts2.BaseAction;
import com.congxing.core.web.struts2.JsonResult;
import com.congxing.rulemgt.dbconfig.model.DbConfigVO;
import com.congxing.rulemgt.reader.dao.DeleteReaderStrategy;
import com.congxing.rulemgt.reader.dao.SaveReaderStrategy;
import com.congxing.rulemgt.reader.model.ReaderParamVO;
import com.congxing.rulemgt.reader.model.ReaderVO;
import com.congxing.rulemgt.reader.model.ReaderValueVO;
import com.congxing.rulemgt.ruleset.dao.QueryRulesetStrategy;
import com.congxing.rulemgt.ruleset.model.RuleExchangeVO;
import com.congxing.rulemgt.ruleset.model.RulesetBoVO;
import com.congxing.rulemgt.ruleset.model.RulesetListVO;
import com.congxing.rulemgt.ruleset.model.RulesetVO;
import com.congxing.rulemgt.ruleset.socket.SocketPool;
import com.congxing.rulemgt.ruleset.socket.SocketPoolFactory;
import com.congxing.rulemgt.ruleset.view.RulesetAction;
import com.opensymphony.xwork2.ActionSupport;

/**
 * <p>
 * Title: RulesetAction <br/>
 * Description: Struts main control Class for Ruleset <br/>
 * Copyright: Copyright (c) 2006 <br/>
 * Company: Revenco Tech Ltd. <br/>
 * </p>
 * 
 * @author LIUKANGJIN
 * @version 1.0
 * @since 2013-06-05
 */
@SuppressWarnings("serial")
public class ReaderAction extends BaseAction {
	static Logger logger = Logger.getLogger(ReaderAction.class);
	public ReaderAction() {
        this.voClass = ReaderVO.class;
        this.pkNameArray = new String[] { "readerId" };        
    }
	
	@SuppressWarnings("unchecked")
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
			
			ReaderVO readerVO = new ReaderVO();
			readerVO.setReaderId(Sequence.getSequence());
			readerVO.setRulesetId(rulesetVO.getRulesetId());
			readerVO.setRulesetVersion(rulesetVO.getRulesetVersion());
			String hql = "FROM RulesetBoVO WHERE rulesetId = ? AND rulesetVersion = ?";
			readerVO.setBoDatas((List<RulesetBoVO>)this.getService().doFindByHQL(hql, rulesetVO.getRulesetId(), rulesetVO.getRulesetVersion()));
			//初始化数据
			readerVO.setReaderName("READER_"+DateFormatFactory.getInstance("yyyyMMdd_HHmm").format(new Date()).replace("-", ""));
			readerVO.setReaderPriority(10);
			
			this.setTargetObjectToParamsMap(readerVO, paramsMap);
			this.setEdit(true);
			this.setNew(true);
		}catch(Exception ex){
			this.addErrorMessage(ex.getMessage());
			ex.printStackTrace();
			return ActionSupport.ERROR;
		}
		return ActionSupport.SUCCESS;
	}
	
	@SuppressWarnings("unchecked")
	public String edit(){
		try{
			paramsMap = ParamsBuilder.buildMapFromHttpRequest();
			String readerId = (String)paramsMap.get("readerId");
			if(StringUtils.isBlank(readerId)){
				throw new Exception("非法进入");
			}
			ReaderVO readerVO = (ReaderVO) this.getService().doFindByPK(voClass, new Long(readerId));
			if(null == readerVO){
				throw new Exception("取数器信息不存在");
			}
			
			readerVO.setParamDatas((List<ReaderParamVO>) this.getService().doFindByProperty(ReaderParamVO.class, "readerId", readerVO.getReaderId()));
			readerVO.setValueDatas((List<ReaderValueVO>) this.getService().doFindByProperty(ReaderValueVO.class, "readerId", readerVO.getReaderId()));
			
			String hql = "FROM RulesetBoVO WHERE rulesetId = ? AND rulesetVersion = ?";
			readerVO.setBoDatas((List<RulesetBoVO>)this.getService().doFindByHQL(hql, readerVO.getRulesetId(), readerVO.getRulesetVersion()));
			
			this.setTargetObjectToParamsMap(readerVO, paramsMap);
			this.setEdit(true);
			this.setNew(false);
		}catch(Exception ex){
			this.addErrorMessage(ex.getMessage());
			ex.printStackTrace();
			return ActionSupport.ERROR;
		}
		return ActionSupport.SUCCESS;
	}
	
	@SuppressWarnings("unchecked")
	public String save(){
		try{
			paramsMap = ParamsBuilder.buildMapFromHttpRequest();
			ReaderVO readerVO = new ReaderVO();
			this.setParamsMapToTargetObject(paramsMap, readerVO);
			SaveReaderStrategy strategy = new SaveReaderStrategy(readerVO, userVO);
			
			String[] boIds = this.getParamValueByParamsMap(paramsMap, "boId");
			String[] valueIdxs = this.getParamValueByParamsMap(paramsMap, "valueIdx");
			
			List<ReaderValueVO> valueDatas = new ArrayList<ReaderValueVO>();
			for(int index = 0; index < boIds.length; index++){
				ReaderValueVO valueVO = new ReaderValueVO();
				valueVO.setValueId(Sequence.getSequence());
				valueVO.setReaderId(readerVO.getReaderId());
				valueVO.setBoId(new Long(boIds[index]));
				valueVO.setValueIdx(new Integer(valueIdxs[index]));
				valueDatas.add(valueVO);
			}
			readerVO.setValueDatas(valueDatas);
			
			String []paramIdxs = this.getParamValueByParamsMap(paramsMap, "paramIdx");
			String []dataTypes = this.getParamValueByParamsMap(paramsMap, "dataType");
			String []paramTypes = this.getParamValueByParamsMap(paramsMap, "paramType");
			String []paramValues = this.getParamValueByParamsMap(paramsMap, "paramValue");
			
			List<ReaderParamVO> paramDatas = new ArrayList<ReaderParamVO>();
			
			for(int index = 0; index < paramIdxs.length; index++){
				ReaderParamVO paramVO = new ReaderParamVO();
				paramVO.setParamId(Sequence.getSequence());
				paramVO.setReaderId(readerVO.getReaderId());
				paramVO.setParamIdx(new Integer(paramIdxs[index]));
				paramVO.setDataType(dataTypes[index]);
				paramVO.setParamType(paramTypes[index]);
				paramVO.setParamValue(paramValues[index]);
				paramDatas.add(paramVO);
			}
			readerVO.setParamDatas(paramDatas);
			
			String isNew = (String) paramsMap.get(Constants.PAGE_ISNEW);
			if(Constants.STRING_TRUE.equals(isNew)){
				strategy.setOprType(Constants.OPRTYPE_ADD);
			}else{
				strategy.setOprType(Constants.OPRTYPE_MODIFY);
			}
			readerVO = (ReaderVO) this.getService().doProcess(strategy);
			
			String hql = "FROM RulesetBoVO WHERE rulesetId = ? AND rulesetVersion = ?";
			readerVO.setBoDatas((List<RulesetBoVO>)this.getService().doFindByHQL(hql, readerVO.getRulesetId(), readerVO.getRulesetVersion()));
			
			this.setTargetObjectToParamsMap(readerVO, paramsMap);
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
	 * 规则列表信息
	 * @return
	 */
	public String list(){
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
			this.addErrorMessage(ex.getMessage());
			ex.printStackTrace();
			return ActionSupport.ERROR;
		}
		return ActionSupport.SUCCESS;
	}
	
	/**
	 * 删除取数器信息
	 * @throws IOException
	 */
	public void deleteJson() throws IOException {
		try{
			paramsMap = ParamsBuilder.buildMapFromHttpRequest();
			if(!paramsMap.containsKey("readerIds")){
				throw new Exception("非法进入");
			}
			String[] readerIds = StringUtils.split((String)paramsMap.get("readerIds"), "\\|");
			if(readerIds.length < 1){
				throw new Exception("非法进入");
			}
			DeleteReaderStrategy strategy = new DeleteReaderStrategy(readerIds, userVO);
			this.getService().doProcess(strategy);
			this.sendJsonMessage(JsonResult.create(ActionSupport.SUCCESS, "删除成功"));
		}catch(Exception ex){
			this.addErrorMessage(ex.getMessage());
			ex.printStackTrace();
			this.sendJsonMessage(JsonResult.create(ActionSupport.ERROR, ex.getMessage()));
		}
	}
	/**
	 * 取数SQL测试
	 * @throws IOException
	 */
	public void	testReaderSQL() throws IOException{
		try{
			paramsMap = ParamsBuilder.buildMapFromHttpRequest();
			String dbId = (String)paramsMap.get("dbId");
			String readerSql = (String)paramsMap.get("readerSql");
			
			DbConfigVO configVO = (DbConfigVO) this.getService().doFindByPK(DbConfigVO.class,PkUtils.getPkVO(DbConfigVO.class, new String[] { "dbId" },dbId) );
			
			Connection conn = DBConnectionUtils.getConnection(configVO.getDriverClass(), configVO.getJdbcUrl(), configVO.getUser(), configVO.getPassword());
			if(conn == null){
				this.sendJsonMessage(JsonResult.create(ActionSupport.ERROR, "请检查数据源是否正确！"));
				return;
			}
			PreparedStatement ps = conn.prepareStatement(readerSql);
			ResultSet rs = ps.executeQuery();
			int count = 0;
			while(rs.next()){
				count++;
			}
			this.sendJsonMessage(JsonResult.create(ActionSupport.SUCCESS, ""+count));
		}catch(Exception ex){
			this.addErrorMessage(ex.getMessage());
			ex.printStackTrace();
			this.sendJsonMessage(JsonResult.create(ActionSupport.ERROR, ex.getMessage()));
		}
	} 
	public String testReader(){
		paramsMap = ParamsBuilder.buildMapFromHttpRequest();
		return ActionSupport.SUCCESS;
	}
	/**
	 * 配置配置测试运行方法
	 * @throws IOException
	 */
	@SuppressWarnings("unchecked")
	public void testReaderJson() throws IOException{
		try{
			paramsMap = ParamsBuilder.buildMapFromHttpRequest();

			String rulesetPK = (String)paramsMap.get("rulesetPK");
			String param = (String)paramsMap.get("param")== null ? "" : (String)paramsMap.get("param");
			if(StringUtils.isBlank(rulesetPK)){
				throw new Exception("非法进入");
			}
			String[] pkArr = rulesetPK.split("\\|");
			
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("rulesetId", pkArr[0]);
			map.put("version", pkArr[1]);
			map.put("param", param);
			
			DefaultHeader header = new DefaultHeader();
			header.setCmdId("10002");
			DefaultBody body = new DefaultBody();
			body.setDatatrans(JsonUtils.toJson(map));
			
			PackInfo packInfo = new PackInfo(header, body);
			
			SocketPool socketPool = SocketPoolFactory.getSocketPool("drools_server");
			Socket client = null;
	        DataOutputStream out = null;
	        DataInputStream in = null;
	        JsonResult result = null;
	        
			try{
				client = socketPool.getSocket();
				out = new DataOutputStream((client.getOutputStream()));
				
				logger.info("ReaderAction Begin RuleExchangeVO[" + JsonUtils.toJson(packInfo) + "]");
				
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
	            logger.info("ReaderAction End Result[" + JsonUtils.toJson(packInfo) + "]");
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
			ex.printStackTrace();
			this.sendJsonMessage(new JsonResult(ActionSupport.ERROR, ex.getMessage()));
		}
	}
}

