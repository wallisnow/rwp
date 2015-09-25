/**
 * auto-generated code
 * 2013-05-30 07:17:18
 */
package com.congxing.system.logindetstat.model;

import com.congxing.core.web.struts2.BaseListVO;


/**
 * <p>
 * Title: LoginDetStatListVO <br/>
 * Description: Query Params Object for LoginDetStatVO <br/>
 * Copyright: Copyright (c) 2006 <br/>
 * Company: Revenco Tech Ltd. <br/>
 * </p>
 * 
 * @author LIUKANGJIN
 * @version 1.0
 * @since 2013-05-30
 */
public class LoginDetStatListVO extends BaseListVO {
	
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
    
    private String _sk_userId;
    
    private String _sge_statDay;
    
    private String _sle_statDay;

	public String get_sk_userId() {
		return _sk_userId;
	}

	public void set_sk_userId(String _sk_userId) {
		this._sk_userId = _sk_userId;
	}

	public String get_sge_statDay() {
		return _sge_statDay;
	}

	public void set_sge_statDay(String _sge_statDay) {
		this._sge_statDay = _sge_statDay;
	}

	public String get_sle_statDay() {
		return _sle_statDay;
	}

	public void set_sle_statDay(String _sle_statDay) {
		this._sle_statDay = _sle_statDay;
	}
    
    public String getVoClassName() {
        return LoginDetStatVO.class.getName();
    }
    
}

