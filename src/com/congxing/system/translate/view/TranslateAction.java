package com.congxing.system.translate.view;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.congxing.core.utils.JsonUtils;
import com.congxing.core.utils.ParamsBuilder;
import com.congxing.core.web.struts2.BaseAction;
import com.congxing.core.web.struts2.JsonResult;
import com.congxing.core.web.translate.TranslateUtilsFactory;
import com.opensymphony.xwork2.ActionSupport;

@SuppressWarnings("serial")
public class TranslateAction extends BaseAction {
	
	/**
	 * 获取VO实例信息
	 * @throws Exception
	 */
	public void voJson() throws Exception{
		JsonResult result = new JsonResult();
		try{
			paramsMap = ParamsBuilder.buildMapFromHttpRequest();
			String voName = (String)paramsMap.get("voName");
			String keys = (String)paramsMap.get("keys");
			String values = (String) paramsMap.get("values");
			if(StringUtils.isBlank(voName) || StringUtils.isBlank(keys) || StringUtils.isBlank(values)){
				this.sendJsonMessage(JsonResult.create(ActionSupport.ERROR, "[voName|keys|values]部分为空"));
				return;
			}
			try{
				this.voClass = Class.forName(voName);
			}catch(Exception ex){
			}
			if(null == voClass){
				this.sendJsonMessage(JsonResult.create(ActionSupport.ERROR, "[voName=" + voName + "]定义有误"));
				return;
			}
			
			String []keyArr = StringUtils.split(keys, "\\|");
			String []valueArr = StringUtils.split(values, "\\|");
			
			if(valueArr.length < keyArr.length){
				this.sendJsonMessage(JsonResult.create(ActionSupport.ERROR, "[keys|values]不匹配"));
				return;
			}
			
			Map<String, Object> properties = new HashMap<String, Object>();
			for(int idx = 0; idx < keyArr.length; idx++){
				properties.put(keyArr[idx], valueArr[idx]);
			}
			try{
				this.entityVO = this.getService().doFindFirstByProperties(voClass, properties);
			}catch(Exception ex){
			}
			if(null == entityVO){
				this.sendJsonMessage(JsonResult.create(ActionSupport.ERROR, "记录不存在"));
				return;
			}
			result.setStatus(ActionSupport.SUCCESS);
			this.setTargetObjectToParamsMap(this.getEntityVO(), paramsMap);
			this.sendJsonMessage(JsonResult.create(ActionSupport.SUCCESS, JsonUtils.toJson(paramsMap)));
		}catch(Exception ex){
			this.sendJsonMessage(JsonResult.create(ActionSupport.ERROR, "异常结束,异常信息："  + ex.getMessage()));
		}
	}
	
	/**
	 * 获取集合信息
	 * @throws IOException  
	 */
	public void listJson() throws IOException {
		try{
			paramsMap = ParamsBuilder.buildMapFromHttpRequest();
			String voName = (String)paramsMap.get("voName");
			String keys = (String)paramsMap.get("keys");
			String values = (String) paramsMap.get("values");
			
			if(StringUtils.isBlank(voName)){
				this.sendJsonMessage(JsonResult.create(ActionSupport.ERROR, "[voName]信息为空"));
				return;
			}
			
			String []keyArr = StringUtils.split(keys, "\\|");
			String []valueArr = StringUtils.split(values, "\\|");
			
			Map<String, Object> properties = new HashMap<String, Object>();
			
			if(keyArr != null && valueArr != null){
				if(valueArr.length < keyArr.length){
					this.sendJsonMessage(JsonResult.create(ActionSupport.ERROR, "[keys|values]不匹配"));
					return;
				}
				for(int idx = 0; idx < keyArr.length; idx++){
					properties.put(keyArr[idx], valueArr[idx]);
				}
			}
			try{
				this.voClass = Class.forName(voName);
			}catch(Exception ex){
			}
			
			if(null == voClass){
				this.sendJsonMessage(JsonResult.create(ActionSupport.ERROR, "[voName=" + voName + "]定义有误"));
				return;
			}
			List<?> datas = this.getService().doFindByProperties(voClass, properties);
			this.sendJsonMessage(JsonResult.create(ActionSupport.SUCCESS, JsonUtils.toJson(datas)));
		}catch(Exception ex){
			this.sendJsonMessage(JsonResult.create(ActionSupport.ERROR, "异常结束,异常信息："  + ex.getMessage()));
		}
	}
	
	/**
	 * 获取翻译信息
	 * @throws IOException 
	 */
	public void nameJson() throws IOException{
		try{
			paramsMap = ParamsBuilder.buildMapFromHttpRequest();
			String definition = (String) paramsMap.get("definition");
			String value = (String) paramsMap.get("value");
			
			if(StringUtils.isBlank(definition)){
				this.sendJsonMessage(JsonResult.create(ActionSupport.ERROR, "参数信息有误"));
				return;
			}
			
			String name = TranslateUtilsFactory.getInstance().getName(definition, value);
			this.sendJsonMessage(JsonResult.create(ActionSupport.SUCCESS, name));
		}catch(Exception ex){
			this.sendJsonMessage(JsonResult.create(ActionSupport.ERROR, "异常结束,异常信息："  + ex.getMessage()));
		}
	}
	
	

}
