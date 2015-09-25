/**
 * auto-generated code
 * 2013-05-30 07:21:01
 */
package com.congxing.system.menufav.model;

import com.congxing.core.web.struts2.BaseListVO;


/**
 * <p>
 * Title: MenuFavListVO <br/>
 * Description: Query Params Object for MenuFavVO <br/>
 * Copyright: Copyright (c) 2006 <br/>
 * Company: Revenco Tech Ltd. <br/>
 * </p>
 * 
 * @author LIUKANGJIN
 * @version 1.0
 * @since 2013-05-30
 */
public class MenuFavListVO extends BaseListVO {
	
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
    
	private String _leq_seqId;
	private String _sk_menuId;
	private String _sk_menuName;
	private String _seq_status;
	private String _seq_userId;
    
    public String getVoClassName() {
        return com.congxing.system.menufav.model.MenuFavVO.class.getName();
    }
    
	public String get_leq_seqId() {
		return _leq_seqId;
	}
	public void set_leq_seqId(String _leq_seqId) {
		this._leq_seqId = _leq_seqId;
	}
	public String get_sk_menuId() {
		return _sk_menuId;
	}
	public void set_sk_menuId(String _sk_menuId) {
		this._sk_menuId = _sk_menuId;
	}
	public String get_sk_menuName() {
		return _sk_menuName;
	}
	public void set_sk_menuName(String _sk_menuName) {
		this._sk_menuName = _sk_menuName;
	}
	public String get_seq_status() {
		return _seq_status;
	}
	public void set_seq_status(String _seq_status) {
		this._seq_status = _seq_status;
	}
	public String get_seq_userId() {
		return _seq_userId;
	}
	public void set_seq_userId(String _seq_userId) {
		this._seq_userId = _seq_userId;
	}
    
}

