package com.congxing.rulemgt.reader.model;

import java.io.Serializable;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * <p>
 * Title: ReaderValueVO <br/>
 * Description: Mapping Object for table READER_VALUE <br/>
 * Copyright: Copyright (c) 2013 <br/>
 * Company: Revenco Tech Ltd. <br/>
 * </p>
 * 
 * @author jinjiebin
 * @version 1.0
 * @since 2013-12-17
 */
public class ReaderValueVO implements Serializable {

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
    private java.lang.Long valueId;
    
    /** nullable persistent field */
    private java.lang.Long readerId;
    
    /** notnull persistent field */
    private java.lang.Long boId;
    
    /** nullable persistent field */
    private java.lang.Integer valueIdx;
    

    public ReaderValueVO(Long valueId, Long readerId, Long boId, Integer valueIdx) {
		super();
		this.valueId = valueId;
		this.readerId = readerId;
		this.boId = boId;
		this.valueIdx = valueIdx;
	}

	/** minimal constructor */
    public ReaderValueVO(java.lang.Long valueId) {
        this.valueId = valueId;
    }
    
    /** default constructor */
    public ReaderValueVO() {
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
        if ( !(other instanceof ReaderValueVO) ) return false;
        ReaderValueVO castOther = (ReaderValueVO) other;
        return new EqualsBuilder()
            .append(this.getValueId(), castOther.getValueId())
            .isEquals();
    }
    
    public int hashCode() {
        return new HashCodeBuilder()
            .append(getValueId())
            .toHashCode();
    }

}

