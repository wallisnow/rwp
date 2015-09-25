/**
 * auto-generated code
 * 2013-05-30 07:43:13
 */
package com.congxing.system.role.model;

import java.io.Serializable;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * <p>
 * Title: RoleOfMenuVO <br/>
 * Description: Mapping Object for table SYS_ROLE_OF_MENU <br/>
 * Copyright: Copyright (c) 2013 <br/>
 * Company: Revenco Tech Ltd. <br/>
 * </p>
 * 
 * @author LIUKANGJIN
 * @version 1.0
 * @since 2013-05-30
 */
public class RoleOfMenuVO implements Serializable {

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
    
    /** identifier field */
    private java.lang.String menuId;
    
    /** full constructor */
    public RoleOfMenuVO(java.lang.String roleId,java.lang.String menuId) {
        this.roleId = roleId;
        this.menuId = menuId;
    }
    
    /** default constructor */
    public RoleOfMenuVO() {
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
            .append("roleId", getRoleId())
            .append("menuId", getMenuId())
            .toString();
    }

    public boolean equals(Object other) {
        if ( !(other instanceof RoleOfMenuVO) ) return false;
        RoleOfMenuVO castOther = (RoleOfMenuVO) other;
        return new EqualsBuilder()
            .append(this.getRoleId(), castOther.getRoleId())
            .append(this.getMenuId(), castOther.getMenuId())
            .isEquals();
    }
    
    public int hashCode() {
        return new HashCodeBuilder()
            .append(getRoleId())
            .append(getMenuId())
            .toHashCode();
    }

}

