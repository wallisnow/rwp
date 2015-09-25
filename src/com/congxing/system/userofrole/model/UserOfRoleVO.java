/**
 * auto-generated code
 * 2013-05-30 07:50:33
 */
package com.congxing.system.userofrole.model;

import java.io.Serializable;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

import com.congxing.core.web.struts2.BatchParam;

/**
 * <p>
 * Title: UserOfRoleVO <br/>
 * Description: Mapping Object for table SYS_USER_OF_ROLE <br/>
 * Copyright: Copyright (c) 2013 <br/>
 * Company: Revenco Tech Ltd. <br/>
 * </p>
 * 
 * @author LIUKANGJIN
 * @version 1.0
 * @since 2013-05-30
 */
public class UserOfRoleVO implements Serializable, BatchParam {

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
    private java.lang.Long urId;
    
    /** nullable persistent field */
    private java.lang.String userId;
    
    /** nullable persistent field */
    private java.lang.String roleId;
    
    /** nullable persistent field */
    private java.lang.String status;
    
    /** nullable persistent field */
    private java.util.Date beginTime;
    
    /** nullable persistent field */
    private java.util.Date endTime;
    
    /** full constructor */
    public UserOfRoleVO(java.lang.Long urId,java.lang.String userId,java.lang.String roleId,java.lang.String status,java.util.Date beginTime,java.util.Date endTime) {
        this.urId = urId;
        this.userId = userId;
        this.roleId = roleId;
        this.status = status;
        this.beginTime = beginTime;
        this.endTime = endTime;
    }

    /** minimal constructor */
    public UserOfRoleVO(java.lang.Long urId) {
        this.urId = urId;
    }
    
    /** default constructor */
    public UserOfRoleVO() {
    }
    
    public java.lang.Long getUrId() {
        return this.urId;
    }

    public void setUrId(java.lang.Long urId) {
        this.urId = urId;
    }
    
    public java.lang.String getUserId() {
        return this.userId;
    }

    public void setUserId(java.lang.String userId) {
        this.userId = userId;
    }
    
    public java.lang.String getRoleId() {
        return this.roleId;
    }

    public void setRoleId(java.lang.String roleId) {
        this.roleId = roleId;
    }
    
    public java.lang.String getStatus() {
        return this.status;
    }

    public void setStatus(java.lang.String status) {
        this.status = status;
    }
    
    public java.util.Date getBeginTime() {
        return this.beginTime;
    }

    public void setBeginTime(java.util.Date beginTime) {
        this.beginTime = beginTime;
    }
    
    public java.util.Date getEndTime() {
        return this.endTime;
    }

    public void setEndTime(java.util.Date endTime) {
        this.endTime = endTime;
    }
    
    public String toString() {
        return new ToStringBuilder(this)
            .append("urId", getUrId())
            .toString();
    }

    public boolean equals(Object other) {
        if ( !(other instanceof UserOfRoleVO) ) return false;
        UserOfRoleVO castOther = (UserOfRoleVO) other;
        return new EqualsBuilder()
            .append(this.getUrId(), castOther.getUrId())
            .isEquals();
    }
    
    public int hashCode() {
        return new HashCodeBuilder()
            .append(getUrId())
            .toHashCode();
    }

}

