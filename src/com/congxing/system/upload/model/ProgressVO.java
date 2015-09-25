package com.congxing.system.upload.model;

import com.congxing.system.attach.model.AttachVO;

public class ProgressVO {
	
	public static final String STATUS_START = "start";
	public static final String STATUS_PROGRESS = "progress";
	public static final String STATUS_DONE = "done";
	public static final String STATUS_ERROR = "error";
	
	private long totalSize = 0;
	private long bytesRead = 0;
	@SuppressWarnings("unused")
	private long elapsedTime = 0;
	private long startTime = System.currentTimeMillis();
	private String status = STATUS_DONE;
	private String errorMsg;
	
	private AttachVO attachVO;
	private String attachId;
	
	
	public ProgressVO(long totalSize){
		this.totalSize = totalSize;
		this.status = STATUS_START;
	}
	
	public ProgressVO(){
		this.status = STATUS_START;
	}
	
	public long getTotalSize() {
		return totalSize;
	}
	public void setTotalSize(long totalSize) {
		this.totalSize = totalSize;
	}
	public long getBytesRead() {
		return bytesRead;
	}
	public void setBytesRead(long bytesRead) {
		this.bytesRead = bytesRead;
	}
	
	public void addBytesRead(long bytesRead) {
		this.bytesRead = this.bytesRead + bytesRead;
	}
	
	public long getElapsedTime() {
		return (System.currentTimeMillis() - startTime)/1000;
	}
	public void setElapsedTime(long elapsedTime) {
		this.elapsedTime = elapsedTime;
	}
	public long getStartTime() {
		return startTime;
	}
	public void setStartTime(long startTime) {
		this.startTime = startTime;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getErrorMsg() {
		return errorMsg;
	}
	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}
	public AttachVO getAttachVO() {
		return attachVO;
	}
	public void setAttachVO(AttachVO attachVO) {
		this.attachVO = attachVO;
	}

	public String getAttachId() {
		return attachId;
	}

	public void setAttachId(String attachId) {
		this.attachId = attachId;
	}
}
