/**
 * auto-generated code
 * 2011-10-17 00:51:05
 */
package com.congxing.system.sysdp.model;

import java.io.Serializable;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * <p>
 * Title: SysDpVO <br/>
 * Description: Mapping Object for table ST_SYS_DP <br/>
 * Copyright: Copyright (c) 2006 <br/>
 * Company: Revenco Tech Ltd. <br/>
 * </p>
 * 
 * @author liukangjin
 * @version 1.0
 * @since 2011-10-17
 */
public class SysDpVO implements Serializable {

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
    private java.lang.String dpId;
    
    /** nullable persistent field */
    private java.lang.String dpName;
    
    /** nullable persistent field */
    private java.lang.String parentDpId;
    
    /** nullable persistent field */
    private java.lang.String dpFullName;
    
    /** nullable persistent field */
    private java.lang.String dpCode;
    
    /** nullable persistent field */
    private java.lang.String isTmpDp;
    
    /** nullable persistent field */
    private java.lang.String orderNo;
    
    /** full constructor */
    public SysDpVO(java.lang.String dpId,java.lang.String dpName,java.lang.String parentDpId,java.lang.String dpFullName,java.lang.String dpCode,java.lang.String isTmpDp,java.lang.String orderNo) {
        this.dpId = dpId;
        this.dpName = dpName;
        this.parentDpId = parentDpId;
        this.dpFullName = dpFullName;
        this.dpCode = dpCode;
        this.isTmpDp = isTmpDp;
        this.orderNo = orderNo;
    }

    /** minimal constructor */
    public SysDpVO(java.lang.String dpId) {
        this.dpId = dpId;
    }
    
    /** default constructor */
    public SysDpVO() {
    }
    
    public java.lang.String getDpId() {
        return this.dpId;
    }

    public void setDpId(java.lang.String dpId) {
        this.dpId = dpId;
    }
    
    public java.lang.String getDpName() {
        return this.dpName;
    }

    public void setDpName(java.lang.String dpName) {
        this.dpName = dpName;
    }
    
    public java.lang.String getParentDpId() {
        return this.parentDpId;
    }

    public void setParentDpId(java.lang.String parentDpId) {
        this.parentDpId = parentDpId;
    }
    
    public java.lang.String getDpFullName() {
        return this.dpFullName;
    }

    public void setDpFullName(java.lang.String dpFullName) {
        this.dpFullName = dpFullName;
    }
    
    public java.lang.String getDpCode() {
        return this.dpCode;
    }

    public void setDpCode(java.lang.String dpCode) {
        this.dpCode = dpCode;
    }
    
    public java.lang.String getIsTmpDp() {
        return this.isTmpDp;
    }

    public void setIsTmpDp(java.lang.String isTmpDp) {
        this.isTmpDp = isTmpDp;
    }
    
    public java.lang.String getOrderNo() {
        return this.orderNo;
    }

    public void setOrderNo(java.lang.String orderNo) {
        this.orderNo = orderNo;
    }
    
    public String toString() {
        return new ToStringBuilder(this)
            .append("dpId", getDpId())
            .toString();
    }

    public boolean equals(Object other) {
        if ( !(other instanceof SysDpVO) ) return false;
        SysDpVO castOther = (SysDpVO) other;
        return new EqualsBuilder()
            .append(this.getDpId(), castOther.getDpId())
            .isEquals();
    }
    
    public int hashCode() {
        return new HashCodeBuilder()
            .append(getDpId())
            .toHashCode();
    }

}

