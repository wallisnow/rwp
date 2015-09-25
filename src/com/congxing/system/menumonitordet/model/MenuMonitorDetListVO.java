/**
 * auto-generated code
 * 2013-05-30 07:28:46
 */
package com.congxing.system.menumonitordet.model;

import com.congxing.core.web.struts2.BaseListVO;


/**
 * <p>
 * Title: MenuMonitorDetListVO <br/>
 * Description: Query Params Object for MenuMonitorDetVO <br/>
 * Copyright: Copyright (c) 2006 <br/>
 * Company: Revenco Tech Ltd. <br/>
 * </p>
 * 
 * @author LIUKANGJIN
 * @version 1.0
 * @since 2013-05-30
 */
public class MenuMonitorDetListVO extends BaseListVO {
	
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
    
    private String _dge_beginTime;
	
	private String _dle_endTime;

	public String get_sk_userId() {
		return _sk_userId;
	}

	public void set_sk_userId(String _sk_userId) {
		this._sk_userId = _sk_userId;
	}

	public String get_dge_beginTime() {
		return _dge_beginTime;
	}

	public void set_dge_beginTime(String _dge_beginTime) {
		this._dge_beginTime = _dge_beginTime;
	}

	public String get_dle_endTime() {
		return _dle_endTime;
	}

	public void set_dle_endTime(String _dle_endTime) {
		this._dle_endTime = _dle_endTime;
	}
    
    public String getVoClassName() {
        return com.congxing.system.menumonitordet.model.MenuMonitorDetVO.class.getName();
    }
    
}

