package com.congxing.core.web.struts2;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Map;

import com.congxing.core.utils.FilenameUtil;
import com.congxing.core.utils.ParamsBuilder;

/**
 *
 * <p>
 * Title:
 * </p>
 * <p>
 * Description: 上传文件
 * </p>
 * <p>
 * Copyright: Copyright (c) 2005
 * </p>
 * <p>
 * Company: maywide
 * </p>
 *
 * @author: Lai Weilong
 * @versino: 1.0
 */

public class UploadFileAction extends BaseBatchAction {

	private static final long serialVersionUID = 4867050409655424799L;

	private File uploadFile;

	private String fileName;

	private String contentType;

	public File getUploadFile() {
		return uploadFile;
	}

	public void setUploadFile(File uploadFile) {
		this.uploadFile = uploadFile;
	}

	public void setUploadFileContentType(String contentType) {
		this.contentType = contentType;
	}

	public String getContentType() {
		return contentType;
	}

	public void setUploadFileFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getFileName() {
		return fileName;
	}

	public String execute() throws Exception {
		try {
			saveFileAs();
			paramsMap = ParamsBuilder.buildMapFromHttpRequest();
			this.setTargetObjectToParamsMap(batchFileInfo, paramsMap);
			this.setTargetObjectToParamsMap(prepareBatchParam(), paramsMap);
			return SUCCESS;
		} catch (Exception ex) {
			ex.printStackTrace();
			this.addErrorMessage("操作失败,失败原因:" +  ex.getMessage());
			return "input";
		}
	}

	/**
	 * 将上传文件另存至特定目录
	 *
	 * @param actionMapping
	 * @param request
	 * @param form
	 * @param user
	 * @return
	 * @throws Exception
	 */
	protected void saveFileAs() throws Exception {

		if (uploadFile == null || !uploadFile.isFile() || uploadFile.length() == 0) {
			throw new Exception("要导入的文件并不存在!");
		}

//		if (!"text/plain".equalsIgnoreCase(contentType) 
//				&& !"application/vnd.ms-excel".equalsIgnoreCase(contentType) 
//				&& !"application/octet-stream".equalsIgnoreCase(contentType)) {
//			throw new Exception("要导入的文件类型不正确，本系统只导入指定的文件格式：txt或者excel文本文件!" + "你导入的文件类型为：" + contentType);
//		}
		
		if (!(fileName.toLowerCase().endsWith(".txt") || fileName.toLowerCase().endsWith(".xls") || fileName.toLowerCase().endsWith(".xlsx"))) {
			throw new Exception("要导入的文件类型不正确，本系统只导入指定的文件格式：txt文本或者xls或者xlsx文件!");
		}

		prepareBatchFileInfo();
		
		InputStream stream = null;

		OutputStream bos = null;

		try {
			// retrieve the file data
			stream = new FileInputStream(uploadFile);
			bos = new FileOutputStream(batchFileInfo.getSaveAsFullPath());
			int bytesRead = 0;
			byte[] buffer = new byte[8192];
			while ((bytesRead = stream.read(buffer, 0, 8192)) != -1) {
				bos.write(buffer, 0, bytesRead);
			}

		} catch (FileNotFoundException fnfe) {
			throw new Exception("源文件没找到!");
		} catch (IOException ioe) {
			throw new Exception("文件读写错误");
		} catch (Exception e) {
			throw e;
		} finally {
			// close the stream
			if (bos != null) {
				bos.close();
			}
			if (stream != null) {
				stream.close();
			}
		}
	}

	private void prepareBatchFileInfo() throws Exception {
		batchFileInfo = new BatchFileInfo();

		Map<String, Object> batchParams = ParamsBuilder.buildMapFromHttpRequest();

		this.setParamsMapToTargetObject(batchParams, batchFileInfo);

		StringBuffer buffer = new StringBuffer();

		String location = FilenameUtil.getWebAppRelatedUploadLocation();

		String saveAsName = FilenameUtil.nameByStyle(batchFileInfo.getRenamePrefix(), batchFileInfo.getRenameStyle(), fileName, false);

		if (!(saveAsName.toLowerCase().endsWith(".txt") || saveAsName.toLowerCase().endsWith(".xls") || saveAsName.toLowerCase().endsWith(".xlsx"))) {
			if(fileName.toLowerCase().endsWith(".txt")){
				saveAsName = saveAsName + ".txt";
			}else if(fileName.toLowerCase().endsWith(".xls")){
				saveAsName = saveAsName + ".xls";
			}else if(fileName.toLowerCase().endsWith(".xlsx")){
				saveAsName = saveAsName + ".xlsx";
			}
		}
		
		buffer.append(location).append(saveAsName);

		batchFileInfo.setSaveAsFullPath(buffer.toString());
		batchFileInfo.setSaveAsFileName(saveAsName);
		batchFileInfo.setOldFileName(this.fileName);
	}

}
