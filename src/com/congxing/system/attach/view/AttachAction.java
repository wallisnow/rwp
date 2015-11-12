package com.congxing.system.attach.view;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.congxing.core.utils.ParamsBuilder;
import com.congxing.core.utils.PkUtils;
import com.congxing.core.web.struts2.BaseAction;
import com.congxing.core.web.struts2.JsonResult;
import com.congxing.system.attach.dao.DeleteAttachStrategy;
import com.congxing.system.attach.model.AttachVO;
import com.opensymphony.xwork2.ActionSupport;

@SuppressWarnings("serial")
public class AttachAction extends BaseAction{
	
	public static Logger logger = Logger.getLogger(AttachAction.class);
	

	/**
	 * 上传附件操作
	 * @throws IOException
	 */
	public void uploadAttachJson()  throws IOException{
		try{
			paramsMap = ParamsBuilder.buildMapFromHttpRequest();
			AttachData targetAttach = new AttachData();
			this.setParamsMapToTargetObject(paramsMap, targetAttach);
			
			if(!targetAttach.isValid()){
				JsonResult result = new JsonResult(ActionSupport.ERROR, "参数无效");
				this.sendJsonMessage(result.toJson());
				return;
			}
			this.voClass = Class.forName(targetAttach.getVoName());
			this.pkNameArray = targetAttach.getPkCol().split("\\|");
			Serializable pk = PkUtils.getPkVO(voClass, pkNameArray, targetAttach.getPkValue());
			Object entityVO = this.getService().doFindByPK(voClass, pk);
			String orgAttIds = BeanUtils.getProperty(entityVO, targetAttach.getAttCol());
			
			String newAttIds = "";
			
			/* 附件以逗号分隔 */
			if(StringUtils.isNotBlank(orgAttIds)){
				newAttIds = orgAttIds + "," + targetAttach.getAttId();
			}else{
				newAttIds = targetAttach.getAttId();
			}
			logger.info("Upload Attach[" + this.getUserVO().getUserId() + "|" + orgAttIds + "|" + newAttIds + "]");
			
			BeanUtils.setProperty(entityVO, targetAttach.getAttCol(), newAttIds);
			this.getService().doUpdate(voClass, entityVO,userVO);
		} catch (Exception ex){
			ex.printStackTrace();
			this.addErrorMessage(ex.getMessage());
			JsonResult result = new JsonResult(ActionSupport.ERROR, ex.getMessage());
			this.sendJsonMessage(result.toJson());
		}
		JsonResult result = new JsonResult(ActionSupport.SUCCESS);
		this.sendJsonMessage(result.toJson());
	}
	
	
	/**
	 * 删除附件操作
	 * @throws IOException
	 */
	public void deleteAttachJson() throws IOException{
		try{
			paramsMap = ParamsBuilder.buildMapFromHttpRequest();
			AttachData targetAttach = new AttachData();
			this.setParamsMapToTargetObject(paramsMap, targetAttach);
			
			if(!targetAttach.isValid()){
				JsonResult result = new JsonResult(ActionSupport.ERROR, "参数无效");
				this.sendJsonMessage(result.toJson());
				return;
			}
			
			this.voClass = Class.forName(targetAttach.getVoName());
			this.pkNameArray = targetAttach.getPkCol().split("\\|");
			Serializable pk = PkUtils.getPkVO(voClass, pkNameArray, targetAttach.getPkValue());
			Object entityVO = this.getService().doFindByPK(voClass, pk);
			String attIds = BeanUtils.getProperty(entityVO, targetAttach.getAttCol());
			
			String newAttIds = "";
			if(attIds.indexOf(targetAttach.getAttId()) > -1){
				if(attIds.equals(targetAttach.getAttId())){
					newAttIds = "";
				}else{
					newAttIds = StringUtils.replace(attIds, targetAttach.getAttId(), "");
				}
				if(null != newAttIds){
					newAttIds = StringUtils.replace(newAttIds, ",,", ",");
				}
			}
			//删除附件信息
			BeanUtils.setProperty(entityVO, targetAttach.getAttCol(), newAttIds.toString());
			DeleteAttachStrategy delStrategy = new DeleteAttachStrategy(voClass, entityVO, targetAttach.getAttId());
			this.getService().doProcess(delStrategy);
			
			AttachVO delAttachVO = (AttachVO) this.getService().doFindByPK(AttachVO.class, targetAttach.getAttId());
			if(null != delAttachVO){
				logger.info("Delete Attach[" + this.getUserVO().getUserId() + "|" + targetAttach.getAttId() + "|" + delAttachVO.getFullFilePath() + "]");	
				
				File attFile = new File(delAttachVO.getFullFilePath());
				FileUtils.deleteQuietly(attFile);
			}
		} catch (Exception ex){
			ex.printStackTrace();
			this.addErrorMessage(ex.getMessage());
			JsonResult result = new JsonResult(ActionSupport.ERROR, ex.getMessage());
			this.sendJsonMessage(result.toJson());
		}
		JsonResult result = new JsonResult(ActionSupport.SUCCESS);
		this.sendJsonMessage(result.toJson());
	}
}
