/**
 * auto-generated code
 * 2013-05-30 06:47:34
 */
package com.congxing.system.attach.model;

import java.io.File;
import java.io.Serializable;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * <p>
 * Title: AttachVO <br/>
 * Description: Mapping Object for table SYS_ATTACH <br/>
 * Copyright: Copyright (c) 2013 <br/>
 * Company: Revenco Tech Ltd. <br/>
 * </p>
 * 
 * @author LIUKANGJIN
 * @version 1.0
 * @since 2013-05-30
 */
public class AttachVO implements Serializable {

   /**
    * Determines if a de-serialized file is compatible with this class.
    *
    * Maintainers must change this value if and only if the new version
    * of this class is not compatible with old versions. See Sun docs
    * for <a href=http://java.sun.com/products/jdk/1.1/docs/guide
    * /serialization/spec/version.doc.html> details. </a>
    *
    * Not necessary to include in first version of the class, but
    * included here as a reminder of its importance.
    */	 
    private static final long serialVersionUID = 1L;
	
    /** identifier field */
    private java.lang.String attId;
    
    /** nullable persistent field */
    private java.lang.String fileName;
    
    /** nullable persistent field */
    private java.lang.String savePath;
    
    /** nullable persistent field */
    private java.lang.String saveMonth;
    
    /** nullable persistent field */
    private java.lang.String saveName;
    
    /** nullable persistent field */
    private java.util.Date upTime;
    
    /** nullable persistent field */
    private java.lang.String oprCode;
    
    /** full constructor */
    public AttachVO(java.lang.String attId,java.lang.String fileName,java.lang.String savePath,java.lang.String saveMonth,java.lang.String saveName,java.util.Date upTime,java.lang.String oprCode) {
        this.attId = attId;
        this.fileName = fileName;
        this.savePath = savePath;
        this.saveMonth = saveMonth;
        this.saveName = saveName;
        this.upTime = upTime;
        this.oprCode = oprCode;
    }

    /** minimal constructor */
    public AttachVO(java.lang.String attId) {
        this.attId = attId;
    }
    
    /** default constructor */
    public AttachVO() {
    }
    
    public java.lang.String getAttId() {
        return this.attId;
    }

    public void setAttId(java.lang.String attId) {
        this.attId = attId;
    }
    
    public java.lang.String getFileName() {
        return this.fileName;
    }

    public void setFileName(java.lang.String fileName) {
        this.fileName = fileName;
    }
    
    public java.lang.String getSavePath() {
        return this.savePath;
    }

    public void setSavePath(java.lang.String savePath) {
        this.savePath = savePath;
    }
    
    public java.lang.String getSaveMonth() {
        return this.saveMonth;
    }

    public void setSaveMonth(java.lang.String saveMonth) {
        this.saveMonth = saveMonth;
    }
    
    public java.lang.String getSaveName() {
        return this.saveName;
    }

    public void setSaveName(java.lang.String saveName) {
        this.saveName = saveName;
    }
    
    public java.util.Date getUpTime() {
        return this.upTime;
    }

    public void setUpTime(java.util.Date upTime) {
        this.upTime = upTime;
    }
    
    public java.lang.String getOprCode() {
        return this.oprCode;
    }

    public void setOprCode(java.lang.String oprCode) {
        this.oprCode = oprCode;
    }
    
    public String toString() {
        return new ToStringBuilder(this)
            .append("attId", getAttId())
            .toString();
    }

    public boolean equals(Object other) {
        if ( !(other instanceof AttachVO) ) return false;
        AttachVO castOther = (AttachVO) other;
        return new EqualsBuilder()
            .append(this.getAttId(), castOther.getAttId())
            .isEquals();
    }
    
    public int hashCode() {
        return new HashCodeBuilder()
            .append(getAttId())
            .toHashCode();
    }
    

    /**
     * 服务器端存储文件全路径名
     * @return
     */
    public String getFullFilePath(){
    	return this.getSavePath() + File.separator + this.getSaveMonth() + File.separator + this.getSaveName();
    }
}

