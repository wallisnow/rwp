/**
 * auto-generated code
 * 2013-05-30 07:26:02
 */
package com.congxing.system.menumonitorconfig.view;

import java.util.Map;

import com.congxing.core.web.sequence.Sequence;
import com.congxing.core.web.struts2.BaseAction;
import com.congxing.system.menumonitorconfig.model.MenuMonitorConfigVO;
import com.congxing.system.user.model.UserVO;

/**
 * <p>
 * Title: MenuMonitorConfigAction <br/>
 * Description: Struts main control Class for MenuMonitorConfig <br/>
 * Copyright: Copyright (c) 2006 <br/>
 * Company: Revenco Tech Ltd. <br/>
 * </p>
 * 
 * @author LIUKANGJIN
 * @version 1.0
 * @since 2013-05-30
 */
@SuppressWarnings("serial")
public class MenuMonitorConfigAction extends BaseAction {
    
    public MenuMonitorConfigAction() {
        this.voClass = MenuMonitorConfigVO.class;
        this.pkNameArray = new String[] { "monitorId" };        
    }
    
    /**
	 * @Title: setAddEntityVO
	 * @Description: 用于doAdd方法，在新建记录前，为EntityVO设置某些恰当的初值
	 * @param entityVO
	 * @param paramsMap
	 * @param userVO
	 * @throws Exception
	 */
	protected void setAddEntityVO(Object entityVO, Map<String, Object> paramsMap, UserVO userVO) throws Exception {
		paramsMap.put("monitorId", Sequence.getSequence());
	}
	
}

