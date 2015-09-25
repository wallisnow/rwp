package com.congxing.system.download.view;

import java.io.File;
import java.io.InputStream;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.congxing.core.utils.ParamsBuilder;
import com.congxing.core.web.struts2.BaseAction;
import com.congxing.system.attach.model.AttachVO;
import com.opensymphony.xwork2.ActionSupport;

@SuppressWarnings("serial")
public class DownloadAction extends BaseAction {
	
	static final Logger logger = Logger.getLogger(DownloadAction.class);
	/* 原始文件名 */
	private String fileName;
	/* 文件存储位置全名 */
	private String fullFilePath;
	
	public String downfile(){
		return ActionSupport.SUCCESS;
	}
	
	/**
	 * 根据附件ID下载文件
	 * @return
	 */
	public String downFileByAttachId(){
		try{
			paramsMap = ParamsBuilder.buildMapFromHttpRequest();
			String attachId = (String) paramsMap.get("attachId");//"131973966682810";
			if(StringUtils.isNotBlank(attachId)){
				AttachVO attachVO = (AttachVO) this.getService().doFindByPK(AttachVO.class, attachId);
				if(null == attachVO){
					this.addErrorMessage("下载附件不存在.");
					return ActionSupport.ERROR;
				}
				fileName = attachVO.getFileName();
				String separator = System.getProperty("file.separator");
				StringBuffer sb = new StringBuffer();
				sb.append(attachVO.getSavePath().trim()).append(separator);
				sb.append(attachVO.getSaveMonth().trim()).append(separator);
				sb.append(attachVO.getSaveName().trim());
				fullFilePath = sb.toString();
			}else{
				this.addErrorMessage("请求资源不存在或地址不正确.");
				return ActionSupport.ERROR;
			}
		}catch(Exception ex){
			ex.printStackTrace();
			this.addErrorMessage(ex.getMessage());
			return ActionSupport.ERROR;
		}
        return ActionSupport.SUCCESS;
	}
	
	/**
	 * 根据附件全路径下载附件
	 * @return
	 */
	public String downFileByFullFilePath(){
		try{
			paramsMap = ParamsBuilder.buildMapFromHttpRequest();
			fullFilePath = (String) paramsMap.get("fullFilePath");
			if(StringUtils.isBlank(fullFilePath)){
				this.addErrorMessage("请求资源不存在或地址不正确.");
				return ActionSupport.ERROR;
			}
			File file = new File(fullFilePath);
			if(file.exists() && !file.isDirectory()){
				fileName = StringUtils.substringAfterLast(fullFilePath, File.separator);
			}else{
				this.addErrorMessage("请求资源不存在或地址不正确.");
				return ActionSupport.ERROR;
			}
		}catch(Exception ex){
			ex.printStackTrace();
			this.addErrorMessage(ex.getMessage());
			return ActionSupport.ERROR;
		}
        return ActionSupport.SUCCESS;
	}
	
	public String getFileName() throws Exception {
		return new String(fileName.getBytes("GBK"), "ISO-8859-1");
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public InputStream getInputStream() throws Exception {
		java.io.File downFile = new java.io.File(this.getFullFilePath());
		return new java.io.FileInputStream(downFile);
	}
	
	public String getFullFilePath() {
		return fullFilePath;
	}

	public void setFullFilePath(String fullFilePath) {
		this.fullFilePath = fullFilePath;
	}

}
