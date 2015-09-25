package com.congxing.system.upload.view;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.io.IOUtils;

import com.congxing.core.utils.DateFormatFactory;
import com.congxing.core.utils.FilenameUtil;
import com.congxing.core.utils.JsonUtils;
import com.congxing.core.utils.Struts2Utils;
import com.congxing.core.web.sequence.Sequence;
import com.congxing.core.web.struts2.BaseAction;
import com.congxing.system.attach.model.AttachVO;
import com.congxing.system.upload.model.ProgressVO;
import com.congxing.system.user.model.UserVO;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.inject.Inject;

@SuppressWarnings("serial")
public class UploadAction extends BaseAction {

	/* 封装上传文件域的属性 */
	private File image;
	/* 封装上传文件类型的属性 */
	private String imageContentType;
	/* 封装上传文件名的属性 */
	private String imageFileName;
	/* 接受依赖注入的属性 */
	@Inject("attachSavePath")
	private String savePath;
	
	private static final String FILE_UPLOAD_PROGRESS = "FILE_UPLOAD_PROGRESS";
	
	/**
	 * 进入上传附件页面
	 * @return
	 */
	public String attach(){
		return ActionSupport.SUCCESS;
	}

	/**
	 * 上传附件操作
	 * @return
	 */
	public String uploadFile() {
		ProgressVO progress = new ProgressVO();
		Struts2Utils.setSessionAttribute(FILE_UPLOAD_PROGRESS, progress);
		try{
			this.uploadFile(progress, userVO);
		}catch(Exception ex) {
			progress.setStatus(ProgressVO.STATUS_ERROR);
			progress.setErrorMsg(ex.getMessage());
			ex.printStackTrace();
		}
		progress.setStatus(ProgressVO.STATUS_DONE);
		return SUCCESS;
	}
	
	/**
	 * 获取文件上传进度
	 */
	public void progressJson(){
		try{
			ProgressVO progress = (ProgressVO) Struts2Utils.getSessionAttribute(FILE_UPLOAD_PROGRESS);
			this.sendJsonMessage(JsonUtils.toJson(progress));
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}
	
	/**
	 * 上传文件并保存附件操作
	 * @param request
	 * @param response
	 * @param progress
	 * @param userVO
	 */
	private void uploadFile(ProgressVO progress, UserVO userVO){
        try {
        	String saveFileName = FilenameUtil.nameByStyle(userVO.getUserId(), FilenameUtil.NAMESTYLE_RENAME, this.getImageFileName(), true);

			String month = DateFormatFactory.getInstance("yyyyMM").format(new java.util.Date());
			String newSavePath = this.getSavePath() + File.separator + month;
			File dir = new File(newSavePath);
			if(!dir.exists()){
				dir.mkdir();
			}
			String targetFullFileName = newSavePath + File.separator + saveFileName;
			progress.setTotalSize(this.getImage().length());
			this.copy(this.getImage(), targetFullFileName, progress);
			AttachVO attachVO = this.saveAttachVO(this.getImageFileName(), savePath, month, saveFileName, userVO);
			progress.setAttachVO(attachVO);
			progress.setAttachId(attachVO.getAttId());
		} catch (FileUploadException e) {
			progress.setErrorMsg(e.getMessage());
			progress.setStatus(ProgressVO.STATUS_ERROR);
			e.printStackTrace();
		} catch (Exception ex){
			progress.setErrorMsg(ex.getMessage());
			progress.setStatus(ProgressVO.STATUS_ERROR);
			ex.printStackTrace();
		}
		progress.setStatus(ProgressVO.STATUS_DONE);
	}
	
	/**
	 * 保存文件
	 * @param srcFile 原始文件
	 * @param targetFullFileName 目标存储文件名(包含全路径)
	 * @param progress
	 * @throws Exception 
	 */
	@SuppressWarnings("unused")
	private void copy(File srcFile, String targetFullFileName, ProgressVO progress) throws Exception{
		FileInputStream fis = null;
		FileOutputStream fos = null;
    	try{
    		fis = new FileInputStream(srcFile);
    		fos = new FileOutputStream(targetFullFileName);
    		
    		int br = 0;
			int fileLength = 0;
			byte[] buffer = new byte[1024];
			long bytesRead = 0L;
			
			while((br = fis.read(buffer)) != -1){
				fos.write(buffer, 0, br);
				bytesRead = bytesRead + br;
				fileLength += br;
				progress.setBytesRead(bytesRead);
			}
    	}catch(Exception ex){
    		ex.printStackTrace();
    		throw new Exception(ex.getMessage());
    	}finally{
			IOUtils.closeQuietly(fis);
			IOUtils.closeQuietly(fos);
    	}
    }
	/**
	 * 保存附件对象
	 * @param fileName
	 * @param savePath
	 * @param saveName
	 * @param user
	 * @return
	 * @throws Exception
	 */
	private AttachVO saveAttachVO(String fileName, String savePath, String saveMonth, String saveName, UserVO user) throws Exception{
		AttachVO attachVO = new AttachVO();
		attachVO.setAttId(String.valueOf(Sequence.getSequence()));
		attachVO.setFileName(fileName);
		attachVO.setSavePath(savePath);
		attachVO.setSaveMonth(saveMonth);
		attachVO.setSaveName(saveName);
		attachVO.setUpTime(new java.util.Date());
		attachVO.setOprCode(user.getUserId());
		return (AttachVO) this.getService().doCreate(AttachVO.class, attachVO);
	}

	/**
	 * 返回上传文件的保存位置
	 * 
	 * @return
	 */
	public String getSavePath() throws Exception {
		return this.savePath;
	}

	public void setSavePath(String savePath) {
		this.savePath = savePath;
	}

	public File getImage() {
		return image;
	}

	public void setImage(File image) {
		this.image = image;
	}

	public String getImageContentType() {
		return imageContentType;
	}

	public void setImageContentType(String imageContentType) {
		this.imageContentType = imageContentType;
	}

	public String getImageFileName() {
		return imageFileName;
	}

	public void setImageFileName(String imageFileName) {
		this.imageFileName = imageFileName;
	}

}
