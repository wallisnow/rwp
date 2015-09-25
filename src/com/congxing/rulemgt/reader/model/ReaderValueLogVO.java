package com.congxing.rulemgt.reader.model;

import java.io.Serializable;
import java.util.Date;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * <p>
 * Title: ReaderValueLogVO <br/>
 * Description: Mapping Object for table READER_VALUE_LOG <br/>
 * Copyright: Copyright (c) 2013 <br/>
 * Company: Revenco Tech Ltd. <br/>
 * </p>
 * 
 * @author jinjiebin
 * @version 1.0
 * @since 2013-12-17
 */
public class ReaderValueLogVO implements Serializable {

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
    private java.lang.Long valueId;
    
    /** nullable persistent field */
    private java.lang.Long readerId;
    
    /** notnull persistent field */
    private java.lang.Long boId;
    
    /** nullable persistent field */
    private java.lang.Integer valueIdx;
    

    public ReaderValueLogVO(Long logId, String oprCode, Date oprTime,
			String oprType, Long valueId, Long readerId, Long boId,
			Integer valueIdx) {
		super();
		this.logId = logId;
		this.oprCode = oprCode;
		this.oprTime = oprTime;
		this.oprType = oprType;
		this.valueId = valueId;
		this.readerId = readerId;
		this.boId = boId;
		this.valueIdx = valueIdx;
	}

	/** minimal constructor */
    public ReaderValueLogVO(java.lang.Long logId) {
        this.logId = logId;
    }
    
    /** default constructor */
    public ReaderValueLogVO() {
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
    
    public java.lang.Long getValueId() {
        return this.valueId;
    }

    public void setValueId(java.lang.Long valueId) {
        this.valueId = valueId;
    }
    
    public java.lang.Long getReaderId() {
        return this.readerId;
    }

    public void setReaderId(java.lang.Long readerId) {
        this.readerId = readerId;
    }
    
	public java.lang.Long getBoId() {
		return boId;
	}

	public void setBoId(java.lang.Long boId) {
		this.boId = boId;
	}
	
    public java.lang.Integer getValueIdx() {
		return valueIdx;
	}

	public void setValueIdx(java.lang.Integer valueIdx) {
		this.valueIdx = valueIdx;
	}

	public String toString() {
        return new ToStringBuilder(this)
            .append("valueId", getValueId())
            .toString();
    }

    public boolean equals(Object other) {
        if ( !(other instanceof ReaderValueLogVO) ) return false;
        ReaderValueLogVO castOther = (ReaderValueLogVO) other;
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

