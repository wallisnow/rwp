/**
 * auto-generated code
 * 2013-05-30 06:55:28
 */
package com.congxing.system.syscode.model;

import java.io.Serializable;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

import com.congxing.core.web.struts2.Business;

/**
 * <p>
 * Title: SysCodeVO <br/>
 * Description: Mapping Object for table SYS_CODE <br/>
 * Copyright: Copyright (c) 2013 <br/>
 * Company: Revenco Tech Ltd. <br/>
 * </p>
 * 
 * @author LIUKANGJIN
 * @version 1.0
 * @since 2013-05-30
 */
public class SysCodeVO extends Business implements Serializable {

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
    private java.lang.Long seqId;
    
    /** notnull persistent field */
    private java.lang.String type;
    
    /** notnull persistent field */
    private java.lang.String name;
    
    /** notnull persistent field */
    private java.lang.String kind;
    
    /** nullable persistent field */
    private java.lang.String kindname;
    
    /** nullable persistent field */
    private java.lang.String memo;
    
    /** full constructor */
    public SysCodeVO(java.lang.Long seqId,java.lang.String type,java.lang.String name,java.lang.String kind,java.lang.String kindname,java.lang.String memo) {
        this.seqId = seqId;
        this.type = type;
        this.name = name;
        this.kind = kind;
        this.kindname = kindname;
        this.memo = memo;
    }

    /** minimal constructor */
    public SysCodeVO(java.lang.Long seqId) {
        this.seqId = seqId;
    }
    
    /** default constructor */
    public SysCodeVO() {
    }
    
    public java.lang.Long getSeqId() {
        return this.seqId;
    }

    public void setSeqId(java.lang.Long seqId) {
        this.seqId = seqId;
    }
    
    public java.lang.String getType() {
        return this.type;
    }

    public void setType(java.lang.String type) {
        this.type = type;
    }
    
    public java.lang.String getName() {
        return this.name;
    }

    public void setName(java.lang.String name) {
        this.name = name;
    }
    
    public java.lang.String getKind() {
        return this.kind;
    }

    public void setKind(java.lang.String kind) {
        this.kind = kind;
    }
    
    public java.lang.String getKindname() {
        return this.kindname;
    }

    public void setKindname(java.lang.String kindname) {
        this.kindname = kindname;
    }
    
    public java.lang.String getMemo() {
        return this.memo;
    }

    public void setMemo(java.lang.String memo) {
        this.memo = memo;
    }
    
    public String toString() {
        return new ToStringBuilder(this)
            .append("seqId", getSeqId())
            .toString();
    }

    public boolean equals(Object other) {
        if ( !(other instanceof SysCodeVO) ) return false;
        SysCodeVO castOther = (SysCodeVO) other;
        return new EqualsBuilder()
            .append(this.getSeqId(), castOther.getSeqId())
            .isEquals();
    }
    
    public int hashCode() {
        return new HashCodeBuilder()
            .append(getSeqId())
            .toHashCode();
    }

}

