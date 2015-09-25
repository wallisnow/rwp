package com.congxing.core.web.taglib.component;

import java.io.Writer;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.components.Component;

import com.congxing.core.web.translate.TranslateUtils;
import com.congxing.core.web.translate.TranslateUtilsFactory;
import com.opensymphony.xwork2.util.ValueStack;

public class AttachComponnent extends Component{
	
	private String voName;
	private String pkCol;
	private String pkValue;
	private String attCol;
	private String attId;
	
	private boolean disabledUpload = false;
	private boolean disabledDelete = false;
	
	private static final String uploadAttach = "<a href=\"javascript:void(0)\" class=\"btnAttach\" onclick='uploadAttach(\"#VONAME#\", \"#PKCOL#\", \"#PKVALUE#\", \"#ATTCOL#\");'></a>";
	private static final String deleteAttach = "<a href=\"javascript:void(0)\" onclick='deleteAttach(\"#VONAME#\", \"#PKCOL#\", \"#PKVALUE#\", \"#ATTCOL#\", \"#ATTID#\");'>[<font style=\"color:red;font-weight:bold;\">X</font>] </a>";
	private static final String downAttach = "<a href='javascript:downAttach(\"#ATTID#\");'>#attachName#</a>";
	
	public AttachComponnent(ValueStack stack) {
		super(stack);
	}
	
	public boolean start(Writer writer) {
		boolean result = super.start(writer);
		try{
			TranslateUtils translateUtils = TranslateUtilsFactory.getInstance();
			StringBuffer outBuf = new StringBuffer("");
			
			if(!this.isDisabledUpload()){
				String thUploadAttach = uploadAttach;
				thUploadAttach = thUploadAttach.replaceAll("#VONAME#", voName);
				thUploadAttach = thUploadAttach.replaceAll("#PKCOL#", pkCol);
				thUploadAttach = thUploadAttach.replaceAll("#PKVALUE#", pkValue);
				thUploadAttach = thUploadAttach.replaceAll("#ATTCOL#", attCol);
				outBuf.append(thUploadAttach);
			}
			
			if(StringUtils.isNotBlank(attId)){
				String []attArray = attId.split(",");
				for (String thId : attArray) {
					if(StringUtils.isBlank(thId))continue;
					String attName = translateUtils.format("!com.congxing.system.attach.model.AttachVO*attId*fileName", thId);
					if(StringUtils.isBlank(attName))continue;
					if(!this.isDisabledDelete()){
						String thDeleteAttach = deleteAttach;
						thDeleteAttach = thDeleteAttach.replaceAll("#VONAME#", voName);
						thDeleteAttach = thDeleteAttach.replaceAll("#PKCOL#", pkCol);
						thDeleteAttach = thDeleteAttach.replaceAll("#PKVALUE#", pkValue);
						thDeleteAttach = thDeleteAttach.replaceAll("#ATTCOL#", attCol);
						thDeleteAttach = thDeleteAttach.replaceAll("#ATTID#", thId);
						outBuf.append(thDeleteAttach);
					}
					
					String thDownAttach = downAttach;
					thDownAttach = thDownAttach.replaceAll("#ATTID#", thId);
					thDownAttach = thDownAttach.replaceAll("#attachName#", attName);
					outBuf.append(thDownAttach).append("&nbsp;&nbsp;");
				}
			}
			writer.write(outBuf.toString());
		}catch(Exception ex){
			ex.printStackTrace();
		}
		return result;
	}

	public String getVoName() {
		return voName;
	}
	public void setVoName(String voName) {
		this.voName = voName;
	}
	public String getPkCol() {
		return pkCol;
	}
	public void setPkCol(String pkCol) {
		this.pkCol = pkCol;
	}
	public String getPkValue() {
		return pkValue;
	}
	public void setPkValue(String pkValue) {
		this.pkValue = pkValue;
	}
	public String getAttCol() {
		return attCol;
	}
	public void setAttCol(String attCol) {
		this.attCol = attCol;
	}
	public String getAttId() {
		return attId;
	}
	public void setAttId(String attId) {
		this.attId = attId;
	}

	public boolean isDisabledUpload() {
		return disabledUpload;
	}

	public void setDisabledUpload(boolean disabledUpload) {
		this.disabledUpload = disabledUpload;
	}

	public boolean isDisabledDelete() {
		return disabledDelete;
	}

	public void setDisabledDelete(boolean disabledDelete) {
		this.disabledDelete = disabledDelete;
	}

}
