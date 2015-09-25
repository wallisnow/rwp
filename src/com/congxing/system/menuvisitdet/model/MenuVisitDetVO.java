/**
 * auto-generated code
 * 2013-05-30 07:30:43
 */
package com.congxing.system.menuvisitdet.model;

import java.io.Serializable;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * <p>
 * Title: MenuVisitDetVO <br/>
 * Description: Mapping Object for table SYS_MENU_VISIT_DET <br/>
 * Copyright: Copyright (c) 2013 <br/>
 * Company: Revenco Tech Ltd. <br/>
 * </p>
 * 
 * @author LIUKANGJIN
 * @version 1.0
 * @since 2013-05-30
 */
public class MenuVisitDetVO implements Serializable {

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
    
    /** nullable persistent field */
    private java.lang.String userId;
    
    /** nullable persistent field */
    private java.lang.String userIp;
    
    /** nullable persistent field */
    private java.lang.String menuId;
    
    /** nullable persistent field */
    private java.util.Date visitTime;
    
    /** full constructor */
    public MenuVisitDetVO(java.lang.Long seqId,java.lang.String userId,java.lang.String userIp,java.lang.String menuId,java.util.Date visitTime) {
        this.seqId = seqId;
        this.userId = userId;
        this.userIp = userIp;
        this.menuId = menuId;
        this.visitTime = visitTime;
    }

    /** minimal constructor */
    public MenuVisitDetVO(java.lang.Long seqId) {
        this.seqId = seqId;
    }
    
    /** default constructor */
    public MenuVisitDetVO() {
    }
    
    public java.lang.Long getSeqId() {
        return this.seqId;
    }

    public void setSeqId(java.lang.Long seqId) {
        this.seqId = seqId;
    }
    
    public java.lang.String getUserId() {
        return this.userId;
    }

    public void setUserId(java.lang.String userId) {
        this.userId = userId;
    }
    
    public java.lang.String getUserIp() {
        return this.userIp;
    }

    public void setUserIp(java.lang.String userIp) {
        this.userIp = userIp;
    }
    
    public java.lang.String getMenuId() {
        return this.menuId;
    }

    public void setMenuId(java.lang.String menuId) {
        this.menuId = menuId;
    }
    
    public java.util.Date getVisitTime() {
        return this.visitTime;
    }

    public void setVisitTime(java.util.Date visitTime) {
        this.visitTime = visitTime;
    }
    
    public String toString() {
        return new ToStringBuilder(this)
            .append("seqId", getSeqId())
            .toString();
    }

    public boolean equals(Object other) {
        if ( !(other instanceof MenuVisitDetVO) ) return false;
        MenuVisitDetVO castOther = (MenuVisitDetVO) other;
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

