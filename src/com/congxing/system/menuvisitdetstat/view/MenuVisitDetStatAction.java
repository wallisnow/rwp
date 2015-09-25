/**
 * auto-generated code
 * 2013-05-30 07:34:50
 */
package com.congxing.system.menuvisitdetstat.view;

import com.congxing.core.web.struts2.BaseAction;
import com.congxing.system.menuvisitdetstat.model.MenuVisitDetStatVO;

/**
 * <p>
 * Title: MenuVisitDetStatAction <br/>
 * Description: Struts main control Class for MenuVisitDetStat <br/>
 * Copyright: Copyright (c) 2006 <br/>
 * Company: Revenco Tech Ltd. <br/>
 * </p>
 * 
 * @author LIUKANGJIN
 * @version 1.0
 * @since 2013-05-30
 */
@SuppressWarnings("serial")
public class MenuVisitDetStatAction extends BaseAction {
    
    public MenuVisitDetStatAction() {
        this.voClass = MenuVisitDetStatVO.class;
        this.pkNameArray = new String[] { "userId","menuId","statDay" };        
    }
}

