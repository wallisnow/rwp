/**
 * auto-generated code
 * 2013-12-24 19:11:00
 */
package com.congxing.rulemgt.storage.model;

import java.io.Serializable;
import java.util.Date;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * <p>
 * Title: RuleStorageParamLogVO <br/>
 * Description: Mapping Object for table ST_RULE_STORAGE_PARAM_LOG <br/>
 * Copyright: Copyright (c) 2013 <br/>
 * Company: Revenco Tech Ltd. <br/>
 * </p>
 * 
 * @author jinjiebin
 * @version 1.0
 * @since 2013-12-24
 */
public class StorageParamLogVO implements Serializable {

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
    private java.lang.Long logId;
    
    /** nullable persistent field */
    private java.lang.String oprCode;
    
    /** nullable persistent field */
    private java.lang.String oprType;
    
    /** nullable persistent field */
    private java.util.Date oprTime;
    
    /** notnull persistent field */
    private java.lang.Long paramId;
    
    /** nullable persistent field */
    private java.lang.Long storageId;
    
    /** nullable persistent field */
    private java.lang.String colName;
    
    /** nullable persistent field */
    private java.lang.String paramType;
    
    
    /** nullable persistent field */
    private java.lang.String paramValue;

	public StorageParamLogVO(Long logId, String oprCode, String oprType,
			Date oprTime, Long paramId, Long storageId, String colName,
			String paramType, String paramValue) {
		super();
		this.logId = logId;
		this.oprCode = oprCode;
		this.oprType = oprType;
		this.oprTime = oprTime;
		this.paramId = paramId;
		this.storageId = storageId;
		this.colName = colName;
		this.paramType = paramType;
		this.paramValue = paramValue;
	}

	/** minimal constructor */
    public StorageParamLogVO(java.lang.Long logId) {
        this.logId = logId;
    }
    
    /** default constructor */
    public StorageParamLogVO() {
    }
    
    public java.lang.Long getLogId() {
		return logId;
	}

	public void setLogId(java.lang.Long logId) {
		this.logId = logId;
	}

	public java.lang.String getOprCode() {
		return oprCode;
	}

	public void setOprCode(java.lang.String oprCode) {
		this.oprCode = oprCode;
	}

	public java.lang.String getOprType() {
		return oprType;
	}

	public void setOprType(java.lang.String oprType) {
		this.oprType = oprType;
	}

	public java.util.Date getOprTime() {
		return oprTime;
	}

	public void setOprTime(java.util.Date oprTime) {
		this.oprTime = oprTime;
	}

	public java.lang.Long getParamId() {
		return paramId;
	}

	public void setParamId(java.lang.Long paramId) {
		this.paramId = paramId;
	}

	public java.lang.Long getStorageId() {
		return storageId;
	}

	public void setStorageId(java.lang.Long storageId) {
		this.storageId = storageId;
	}

	public java.lang.String getColName() {
		return colName;
	}

	public void setColName(java.lang.String colName) {
		this.colName = colName;
	}

	public java.lang.String getParamType() {
		return paramType;
	}

	public void setParamType(java.lang.String paramType) {
		this.paramType = paramType;
	}

	public java.lang.String getParamValue() {
		return paramValue;
	}

	public void setParamValue(java.lang.String paramValue) {
		this.paramValue = paramValue;
	}
    
    public String toString() {
        return new ToStringBuilder(this)
            .append("logId", getLogId())
            .toString();
    }

    public boolean equals(Object other) {
        if ( !(other instanceof StorageParamLogVO) ) return false;
        StorageParamLogVO castOther = (StorageParamLogVO) other;
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

