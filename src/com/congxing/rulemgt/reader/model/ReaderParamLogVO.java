package com.congxing.rulemgt.reader.model;

import java.io.Serializable;
import java.util.Date;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * <p>
 * Title: ReaderParamLogVO <br/>
 * Description: Mapping Object for table READER_PARAM_LOG <br/>
 * Copyright: Copyright (c) 2013 <br/>
 * Company: Revenco Tech Ltd. <br/>
 * </p>
 * 
 * @author jinjiebin
 * @version 1.0
 * @since 2013-12-17
 */
public class ReaderParamLogVO implements Serializable {

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
	
    /** notnull persistent field */
    private java.lang.Long logId;
    
    /** nullable persistent field */
    private java.lang.String oprCode;
    
    /** nullable persistent field */
    private java.util.Date oprTime;
    
    /** nullable persistent field */
    private java.lang.String oprType;
    
    /** identifier field */
    private java.lang.Long paramId;
    
    /** nullable persistent field */
    private java.lang.Long readerId;
    
    /** nullable persistent field */
    private java.lang.Integer paramIdx;
    
    /** nullable persistent field */
    private java.lang.String dataType;
    
    /** nullable persistent field */
    private java.lang.String paramType;
    
    /** nullable persistent field */
    private java.lang.String paramValue;

	public ReaderParamLogVO(Long logId, String oprCode, Date oprTime,
			String oprType, Long paramId, Long readerId, Integer paramIdx,
			String dataType, String paramType, String paramValue) {
		super();
		this.logId = logId;
		this.oprCode = oprCode;
		this.oprTime = oprTime;
		this.oprType = oprType;
		this.paramId = paramId;
		this.readerId = readerId;
		this.paramIdx = paramIdx;
		this.dataType = dataType;
		this.paramType = paramType;
		this.paramValue = paramValue;
	}

	/** minimal constructor */
    public ReaderParamLogVO(java.lang.Long logId) {
        this.logId = logId;
    }
    
    /** default constructor */
    public ReaderParamLogVO() {
    }
    
    public java.lang.Long getLogId() {
        return this.logId;
    }

    public void setLogId(java.lang.Long logId) {
        this.logId = logId;
    }
    
    public java.lang.String getOprCode() {
        return this.oprCode;
    }

    public void setOprCode(java.lang.String oprCode) {
        this.oprCode = oprCode;
    }
    
    public java.util.Date getOprTime() {
        return this.oprTime;
    }

    public void setOprTime(java.util.Date oprTime) {
        this.oprTime = oprTime;
    }
    
    public java.lang.String getOprType() {
        return this.oprType;
    }

    public void setOprType(java.lang.String oprType) {
        this.oprType = oprType;
    }
    
    public java.lang.Long getParamId() {
        return this.paramId;
    }

    public void setParamId(java.lang.Long paramId) {
        this.paramId = paramId;
    }
    
    public java.lang.Long getReaderId() {
        return this.readerId;
    }

    public void setReaderId(java.lang.Long readerId) {
        this.readerId = readerId;
    }
    
    
    public java.lang.String getParamType() {
        return this.paramType;
    }

    public void setParamType(java.lang.String paramType) {
        this.paramType = paramType;
    }
    
    
    public java.lang.Integer getParamIdx() {
		return paramIdx;
	}

	public void setParamIdx(java.lang.Integer paramIdx) {
		this.paramIdx = paramIdx;
	}

	public java.lang.String getDataType() {
		return dataType;
	}

	public void setDataType(java.lang.String dataType) {
		this.dataType = dataType;
	}

	public java.lang.String getParamValue() {
        return this.paramValue;
    }

    public void setParamValue(java.lang.String paramValue) {
        this.paramValue = paramValue;
    }
    
    public String toString() {
        return new ToStringBuilder(this)
            .append("paramId", getParamId())
            .toString();
    }

    public boolean equals(Object other) {
        if ( !(other instanceof ReaderParamLogVO) ) return false;
        ReaderParamLogVO castOther = (ReaderParamLogVO) other;
        return new EqualsBuilder()
            .append(this.getLogId(), castOther.getLogId())
            .isEquals();
    }
    
    public int hashCode() {
        return new HashCodeBuilder()
            .append(getLogId())
            .toHashCode();
    }

}

