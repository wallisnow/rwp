/**
 * auto-generated code
 * 2013-12-24 19:11:30
 */
package com.congxing.rulemgt.storage.model;

import java.io.Serializable;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * <p>
 * Title: RuleStorageParamVO <br/>
 * Description: Mapping Object for table ST_RULE_STORAGE_PARAM <br/>
 * Copyright: Copyright (c) 2013 <br/>
 * Company: Revenco Tech Ltd. <br/>
 * </p>
 * 
 * @author jinjiebin
 * @version 1.0
 * @since 2013-12-24
 */
public class StorageParamVO implements Serializable {

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
    private java.lang.Long paramId;
    
    /** nullable persistent field */
    private java.lang.Long storageId;
    
    /** nullable persistent field */
    private java.lang.String colName;
    
    
    /** nullable persistent field */
    private java.lang.String paramType;
    
    /** nullable persistent field */
    private java.lang.String paramValue;

	public StorageParamVO(Long paramId, Long storageId, String colName,
			String paramType, String paramValue) {
		super();
		this.paramId = paramId;
		this.storageId = storageId;
		this.colName = colName;
		this.paramType = paramType;
		this.paramValue = paramValue;
	}

	/** minimal constructor */
    public StorageParamVO(java.lang.Long paramId) {
        this.paramId = paramId;
    }
    
    /** default constructor */
    public StorageParamVO() {
    }
    
    public java.lang.Long getParamId() {
        return this.paramId;
    }

    public void setParamId(java.lang.Long paramId) {
        this.paramId = paramId;
    }
    
    public java.lang.Long getStorageId() {
        return this.storageId;
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
            .append("paramId", getParamId())
            .toString();
    }

    public boolean equals(Object other) {
        if ( !(other instanceof StorageParamVO) ) return false;
        StorageParamVO castOther = (StorageParamVO) other;
        return new EqualsBuilder()
            .append(this.getParamId(), castOther.getParamId())
            .isEquals();
    }
    
    public int hashCode() {
        return new HashCodeBuilder()
            .append(getParamId())
            .toHashCode();
    }

}

