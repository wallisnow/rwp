/**
 * auto-generated code
 * 2013-05-30 07:26:02
 */
package com.congxing.system.menumonitorconfig.model;

import com.congxing.core.web.struts2.BaseListVO;


/**
 * <p>
 * Title: MenuMonitorConfigListVO <br/>
 * Description: Query Params Object for MenuMonitorConfigVO <br/>
 * Copyright: Copyright (c) 2006 <br/>
 * Company: Revenco Tech Ltd. <br/>
 * </p>
 * 
 * @author LIUKANGJIN
 * @version 1.0
 * @since 2013-05-30
 */
public class MenuMonitorConfigListVO extends BaseListVO {
	
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
    
    private String _sk_monitorUrl;
    
    private String _sk_monitorName;
    
    public String get_sk_monitorUrl() {
        return _sk_monitorUrl;
    }

    public void set_sk_monitorUrl(String _sk_monitorUrl) {
        this._sk_monitorUrl = _sk_monitorUrl;
    }
    
    public String get_sk_monitorName() {
        return _sk_monitorName;
    }

    public void set_sk_monitorName(String _sk_monitorName) {
        this._sk_monitorName = _sk_monitorName;
    }
    
    public String getVoClassName() {
        return com.congxing.system.menumonitorconfig.model.MenuMonitorConfigVO.class.getName();
    }
    
}

