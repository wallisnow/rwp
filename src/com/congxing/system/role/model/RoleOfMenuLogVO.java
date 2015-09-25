/**
 * auto-generated code
 * 2011-10-20 10:21:43
 */
package com.congxing.system.role.model;

import java.io.Serializable;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * <p>
 * Title: RoleOfMenuLogVO <br/>
 * Description: Mapping Object for table ST_SYS_ROLE_OF_MENU_LOG <br/>
 * Copyright: Copyright (c) 2006 <br/>
 * Company: Revenco Tech Ltd. <br/>
 * </p>
 * 
 * @author liukangjin
 * @version 1.0
 * @since 2011-10-20
 */
public class RoleOfMenuLogVO implements Serializable {

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
    
    /** notnull persistent field */
    private java.lang.String oprCode;
    
    /** notnull persistent field */
    private java.util.Date oprTime;
    
    /** notnull persistent field */
    private java.lang.String oprType;
    
    /** notnull persistent field */
    private java.lang.String roleId;
    
    /** notnull persistent field */
    private java.lang.String menuId;
    
    /** full constructor */
    public RoleOfMenuLogVO(java.lang.Long logId,java.lang.String oprCode,java.util.Date oprTime,java.lang.String oprType,java.lang.String roleId,java.lang.String menuId) {
        this.logId = logId;
        this.oprCode = oprCode;
        this.oprTime = oprTime;
        this.oprType = oprType;
        this.roleId = roleId;
        this.menuId = menuId;
    }

    /** minimal constructor */
    public RoleOfMenuLogVO(java.lang.Long logId) {
        this.logId = logId;
    }
    
    /** default constructor */
    public RoleOfMenuLogVO() {
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
    
    public java.lang.String getRoleId() {
        return this.roleId;
    }

    public void setRoleId(java.lang.String roleId) {
        this.roleId = roleId;
    }
    
    public java.lang.String getMenuId() {
        return this.menuId;
    }

    public void setMenuId(java.lang.String menuId) {
        this.menuId = menuId;
    }
    
    public String toString() {
        return new ToStringBuilder(this)
            .append("logId", getLogId())
            .toString();
    }

    public boolean equals(Object other) {
        if ( !(other instanceof RoleOfMenuLogVO) ) return false;
        RoleOfMenuLogVO castOther = (RoleOfMenuLogVO) other;
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

