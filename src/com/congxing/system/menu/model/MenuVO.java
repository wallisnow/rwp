/**
 * auto-generated code
 * 2011-09-28 01:10:09
 */
package com.congxing.system.menu.model;

import java.io.Serializable;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * <p>
 * Title: MenuVO <br/>
 * Description: Mapping Object for table ST_SYS_MENU <br/>
 * Copyright: Copyright (c) 2006 <br/>
 * Company: Revenco Tech Ltd. <br/>
 * </p>
 * 
 * @author liukangjin
 * @version 1.0
 * @since 2011-09-28
 */
public class MenuVO implements Serializable {

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
    
    /** full constructor */
    public MenuVO(java.lang.Long seqId,java.lang.String menuId,java.lang.String parentMenuId,java.lang.String menuName,java.lang.String menuType,java.lang.String menuUrl,java.lang.String menuDesc,java.lang.String status) {
        this.seqId = seqId;
        this.menuId = menuId;
        this.parentMenuId = parentMenuId;
        this.menuName = menuName;
        this.menuType = menuType;
        this.menuUrl = menuUrl;
        this.menuDesc = menuDesc;
        this.status = status;
    }

    /** minimal constructor */
    public MenuVO(java.lang.Long seqId) {
        this.seqId = seqId;
    }
    
    /** default constructor */
    public MenuVO() {
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
    
    public String toString() {
        return new ToStringBuilder(this)
            .append("seqId", getSeqId())
            .toString();
    }

    public boolean equals(Object other) {
        if ( !(other instanceof MenuVO) ) return false;
        MenuVO castOther = (MenuVO) other;
        return new EqualsBuilder()
            .append(this.getSeqId(), castOther.getSeqId())
            .isEquals();
    }
    
    public int hashCode() {
        return new HashCodeBuilder()
            .append(getSeqId())
            .toHashCode();
    }
    
    //角色是否拥有该菜单
    private boolean chked = false;

	public boolean isChked() {
		return chked;
	}

	public void setChked(boolean chked) {
		this.chked = chked;
	}
	
}

