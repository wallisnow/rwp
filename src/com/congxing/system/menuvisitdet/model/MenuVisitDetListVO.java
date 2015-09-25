/**
 * auto-generated code
 * 2013-05-30 07:30:43
 */
package com.congxing.system.menuvisitdet.model;

import com.congxing.core.web.struts2.BaseListVO;


/**
 * <p>
 * Title: MenuVisitDetListVO <br/>
 * Description: Query Params Object for MenuVisitDetVO <br/>
 * Copyright: Copyright (c) 2006 <br/>
 * Company: Revenco Tech Ltd. <br/>
 * </p>
 * 
 * @author LIUKANGJIN
 * @version 1.0
 * @since 2013-05-30
 */
public class MenuVisitDetListVO extends BaseListVO {
	
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
    
    public String get_seq_userId() {
        return _seq_userId;
    }

    public void set_seq_userId(String _seq_userId) {
        this._seq_userId = _seq_userId;
    }
    
    public String getVoClassName() {
        return com.congxing.system.menuvisitdet.model.MenuVisitDetVO.class.getName();
    }
    
}

