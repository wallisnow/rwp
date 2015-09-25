/**
 * auto-generated code
 * 2013-05-30 07:34:50
 */
package com.congxing.system.menuvisitdetstat.model;

import com.congxing.core.web.struts2.BaseListVO;


/**
 * <p>
 * Title: MenuVisitDetStatListVO <br/>
 * Description: Query Params Object for MenuVisitDetStatVO <br/>
 * Copyright: Copyright (c) 2006 <br/>
 * Company: Revenco Tech Ltd. <br/>
 * </p>
 * 
 * @author LIUKANGJIN
 * @version 1.0
 * @since 2013-05-30
 */
public class MenuVisitDetStatListVO extends BaseListVO {
	
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
    
    private String _seq_userId;
    
    private String _seq_menuId;
    
    private String _sle_statDay;
    
    private String _sge_statDay;
    
    public String get_seq_userId() {
        return _seq_userId;
    }

    public void set_seq_userId(String _seq_userId) {
        this._seq_userId = _seq_userId;
    }
    
    public String get_seq_menuId() {
        return _seq_menuId;
    }

    public void set_seq_menuId(String _seq_menuId) {
        this._seq_menuId = _seq_menuId;
    }
    
    public String get_sle_statDay() {
        return _sle_statDay;
    }

    public void set_sle_statDay(String _sle_statDay) {
        this._sle_statDay = _sle_statDay;
    }
    
    public String get_sge_statDay() {
        return _sge_statDay;
    }

    public void set_sge_statDay(String _sge_statDay) {
        this._sge_statDay = _sge_statDay;
    }
    
    public String getVoClassName() {
        return com.congxing.system.menuvisitdetstat.model.MenuVisitDetStatVO.class.getName();
    }
    
}

