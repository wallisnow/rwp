/**
 * auto-generated code
 * 2013-05-30 07:39:09
 */
package com.congxing.system.role.model;

import java.io.Serializable;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * <p>
 * Title: RoleVO <br/>
 * Description: Mapping Object for table SYS_ROLE <br/>
 * Copyright: Copyright (c) 2013 <br/>
 * Company: Revenco Tech Ltd. <br/>
 * </p>
 * 
 * @author LIUKANGJIN
 * @version 1.0
 * @since 2013-05-30
 */
public class RoleVO implements Serializable {

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
    private java.lang.String roleId;
    
    /** notnull persistent field */
    private java.lang.String roleName;
    
    /** nullable persistent field */
    private java.lang.String roleDesc;
    
    /** nullable persistent field */
    private java.lang.String status;
    
    /** nullable persistent field */
    private java.util.Date beginTime;
    
    /** nullable persistent field */
    private java.util.Date endTime;
    
    /** full constructor */
    public RoleVO(java.lang.String roleId,java.lang.String roleName,java.lang.String roleDesc,java.lang.String status,java.util.Date beginTime,java.util.Date endTime) {
        this.roleId = roleId;
        this.roleName = roleName;
        this.roleDesc = roleDesc;
        this.status = status;
        this.beginTime = beginTime;
        this.endTime = endTime;
    }

    /** minimal constructor */
    public RoleVO(java.lang.String roleId) {
        this.roleId = roleId;
    }
    
    /** default constructor */
    public RoleVO() {
    }
    
    public java.lang.String getRoleId() {
        return this.roleId;
    }

    public void setRoleId(java.lang.String roleId) {
        this.roleId = roleId;
    }
    
    public java.lang.String getRoleName() {
        return this.roleName;
    }

    public void setRoleName(java.lang.String roleName) {
        this.roleName = roleName;
    }
    
    public java.lang.String getRoleDesc() {
        return this.roleDesc;
    }

    public void setRoleDesc(java.lang.String roleDesc) {
        this.roleDesc = roleDesc;
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
            .append("roleId", getRoleId())
            .toString();
    }

    public boolean equals(Object other) {
        if ( !(other instanceof RoleVO) ) return false;
        RoleVO castOther = (RoleVO) other;
        return new EqualsBuilder()
            .append(this.getRoleId(), castOther.getRoleId())
            .isEquals();
    }
    
    public int hashCode() {
        return new HashCodeBuilder()
            .append(getRoleId())
            .toHashCode();
    }

}

