/**
 * auto-generated code
 * 2013-05-30 07:21:01
 */
package com.congxing.system.menufav.model;

import java.io.Serializable;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * <p>
 * Title: MenuFavVO <br/>
 * Description: Mapping Object for table SYS_MENU_FAV <br/>
 * Copyright: Copyright (c) 2013 <br/>
 * Company: Revenco Tech Ltd. <br/>
 * </p>
 * 
 * @author LIUKANGJIN
 * @version 1.0
 * @since 2013-05-30
 */
public class MenuFavVO implements Serializable {

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
    private java.lang.String menuId;
    
    /** nullable persistent field */
    private java.lang.String parentMenuId;
    
    /** nullable persistent field */
    private java.lang.String menuName;
    
    /** nullable persistent field */
    private java.lang.String menuType;
    
    /** nullable persistent field */
    private java.lang.String menuUrl;
    
    /** nullable persistent field */
    private java.lang.String menuDesc;
    
    /** nullable persistent field */
    private java.lang.String status;
    
    /** nullable persistent field */
    private java.lang.String userId;
    
    /** full constructor */
    public MenuFavVO(java.lang.Long seqId,java.lang.String menuId,java.lang.String parentMenuId,java.lang.String menuName,java.lang.String menuType,java.lang.String menuUrl,java.lang.String menuDesc,java.lang.String status,java.lang.String userId) {
        this.seqId = seqId;
        this.menuId = menuId;
        this.parentMenuId = parentMenuId;
        this.menuName = menuName;
        this.menuType = menuType;
        this.menuUrl = menuUrl;
        this.menuDesc = menuDesc;
        this.status = status;
        this.userId = userId;
    }

    /** minimal constructor */
    public MenuFavVO(java.lang.Long seqId) {
        this.seqId = seqId;
    }
    
    /** default constructor */
    public MenuFavVO() {
    }
    
    public java.lang.Long getSeqId() {
        return this.seqId;
    }

    public void setSeqId(java.lang.Long seqId) {
        this.seqId = seqId;
    }
    
    public java.lang.String getMenuId() {
        return this.menuId;
    }

    public void setMenuId(java.lang.String menuId) {
        this.menuId = menuId;
    }
    
    public java.lang.String getParentMenuId() {
        return this.parentMenuId;
    }

    public void setParentMenuId(java.lang.String parentMenuId) {
        this.parentMenuId = parentMenuId;
    }
    
    public java.lang.String getMenuName() {
        return this.menuName;
    }

    public void setMenuName(java.lang.String menuName) {
        this.menuName = menuName;
    }
    
    public java.lang.String getMenuType() {
        return this.menuType;
    }

    public void setMenuType(java.lang.String menuType) {
        this.menuType = menuType;
    }
    
    public java.lang.String getMenuUrl() {
        return this.menuUrl;
    }

    public void setMenuUrl(java.lang.String menuUrl) {
        this.menuUrl = menuUrl;
    }
    
    public java.lang.String getMenuDesc() {
        return this.menuDesc;
    }

    public void setMenuDesc(java.lang.String menuDesc) {
        this.menuDesc = menuDesc;
    }
    
    public java.lang.String getStatus() {
        return this.status;
    }

    public void setStatus(java.lang.String status) {
        this.status = status;
    }
    
    public java.lang.String getUserId() {
        return this.userId;
    }

    public void setUserId(java.lang.String userId) {
        this.userId = userId;
    }
    
    public String toString() {
        return new ToStringBuilder(this)
            .append("seqId", getSeqId())
            .toString();
    }

    public boolean equals(Object other) {
        if ( !(other instanceof MenuFavVO) ) return false;
        MenuFavVO castOther = (MenuFavVO) other;
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

