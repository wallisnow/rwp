package com.congxing.rulemgt.reader.model;

import java.io.Serializable;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * <p>
 * Title: ReaderParamVO <br/>
 * Description: Mapping Object for table READER_PARAM <br/>
 * Copyright: Copyright (c) 2013 <br/>
 * Company: Revenco Tech Ltd. <br/>
 * </p>
 * 
 * @author jinjiebin
 * @version 1.0
 * @since 2013-12-17
 */
public class ReaderParamVO implements Serializable {

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
    private java.lang.Long readerId;
    
    /** nullable persistent field */
    private java.lang.Integer paramIdx;
    
    /** nullable persistent field */
    private java.lang.String dataType;
    
    /** nullable persistent field */
    private java.lang.String paramType;   
    
    /** nullable persistent field */
    private java.lang.String paramValue;
    

	public ReaderParamVO(Long paramId, Long readerId, Integer paramIdx,
			String dataType, String paramType, String paramValue) {
		super();
		this.paramId = paramId;
		this.readerId = readerId;
		this.paramIdx = paramIdx;
		this.dataType = dataType;
		this.paramType = paramType;
		this.paramValue = paramValue;
	}

	/** minimal constructor */
    public ReaderParamVO(java.lang.Long paramId) {
        this.paramId = paramId;
    }
    
    /** default constructor */
    public ReaderParamVO() {
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

	public java.lang.String getDataType() {
		return dataType;
	}

	public void setDataType(java.lang.String dataType) {
		this.dataType = dataType;
	}
    
    public java.lang.String getParamType() {
        return this.paramType;
    }

    public void setParamType(java.lang.String paramType) {
        this.paramType = paramType;
    }
    
    public java.lang.String getParamValue() {
        return this.paramValue;
    }

    public void setParamValue(java.lang.String paramValue) {
        this.paramValue = paramValue;
    }
    
    public java.lang.Integer getParamIdx() {
		return paramIdx;
	}

	public void setParamIdx(java.lang.Integer paramIdx) {
		this.paramIdx = paramIdx;
	}

	

	public String toString() {
        return new ToStringBuilder(this)
            .append("paramId", getParamId())
            .toString();
    }

    public boolean equals(Object other) {
        if ( !(other instanceof ReaderParamVO) ) return false;
        ReaderParamVO castOther = (ReaderParamVO) other;
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

