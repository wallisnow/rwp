package com.congxing.core.web.taglib.component;

import java.io.Writer;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.struts2.components.Component;

import com.congxing.core.service.CommonService;
import com.congxing.core.utils.SpringContextManager;
import com.congxing.core.web.constant.Constants;
import com.congxing.system.menu.model.MenuVO;
import com.congxing.system.role.model.RoleOfMenuVO;
import com.congxing.system.user.model.UserVO;
import com.opensymphony.xwork2.util.ValueStack;

public class PurChkComponent extends Component {
	
	private String actionUrl;
	private HttpServletRequest request;
	private HttpSession session;
	private UserVO userVO;
	

	public PurChkComponent(ValueStack stack, HttpServletRequest request) { 
		super(stack);
		this.request = request;
		if(null != request){
			session = request.getSession();
		}
		if(null != session){
			userVO = (UserVO) session.getAttribute(Constants.SESSION_USER);
		}
	}
	
	@SuppressWarnings("unchecked")
	public boolean start(Writer writer) {
		if(null == userVO)return false;
		if(userVO.isSuperUser())return true;
        CommonService service = SpringContextManager.getBean("commonServiceImpl");
        MenuVO menuVO = (MenuVO) service.doFindFirstByHQL("FROM MenuVO WHERE menuUrl = ? AND menuType = ? ", new Object[]{actionUrl, Constants.MENU_TYPE_CONTROL});
        if(null != menuVO){
        	String menuId = menuVO.getMenuId();
			List<RoleOfMenuVO> rmList = (List<RoleOfMenuVO>) service.doFindByHQL("FROM RoleOfMenuVO WHERE menuId = ?", new Object[]{menuId});
			
			if(null == rmList || rmList.size() < 1){
				return false;
			}
			for(RoleOfMenuVO rmVO : rmList){
				if(userVO.getRoleIdList().contains(rmVO.getRoleId()))return true;
			}
			return false;
        }
		return true;
    }

    public boolean end(Writer writer, String body) {
        return super.end(writer, body);
    }
	

	public String getActionUrl() {
		return actionUrl;
	}

	public void setActionUrl(String actionUrl) {
		this.actionUrl = actionUrl;
	}

	public HttpServletRequest getRequest() {
		return request;
	}

	public void setRequest(HttpServletRequest request) {
		this.request = request;
	}

}
