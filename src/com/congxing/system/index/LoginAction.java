package com.congxing.system.index;

import java.util.List;

import javax.servlet.http.Cookie;

import org.apache.commons.lang.StringUtils;

import com.congxing.core.utils.MD5Encrypt;
import com.congxing.core.utils.ParamsBuilder;
import com.congxing.core.utils.Struts2Utils;
import com.congxing.core.web.constant.Constants;
import com.congxing.core.web.struts2.BaseAction;
import com.congxing.system.index.dao.QueryUserOfRoleStrategy;
import com.congxing.system.user.model.UserVO;
import com.opensymphony.xwork2.ActionSupport;

@SuppressWarnings("serial")
public class LoginAction extends BaseAction {
	
	public final static String PAGE_USER_ID = "userId";
	public final static String PAGE_USER_PASSWORD = "password";

	@SuppressWarnings("unchecked")
	public String execute() {
		try{
			paramsMap = ParamsBuilder.buildMapFromHttpRequest();
			String userId = (String) paramsMap.get(PAGE_USER_ID);
			String password = (String) paramsMap.get(PAGE_USER_PASSWORD);
			
			/**
			 * 第一步直接查询本地数据库用户信息,确认用户loginId
			 */
			List<UserVO> userList = (List<UserVO>)this.getService().doFindByProperty(UserVO.class, "userId", userId);
			UserVO userVO = null;
			if(null != userList && userList.size() > 0){
				userVO = userList.get(0);
			}
			
			if(null == userVO){//本地和远程都无该用户信息,用户不合法
				this.addActionError("用户信息不存在");
				return ActionSupport.ERROR;
			}
			
			/**
			 * 检查用户是否本地用户
			 */
			if(Constants.USER_TYPE_LOCAL.equals(userVO.getUserType())){
				String pwdMd5 = MD5Encrypt.getMD5Password(password);
				if(!pwdMd5.equals(userVO.getPassword())){
					this.addErrorMessage("请检查登录信息是否正确");
					return ActionSupport.ERROR;
				}
			}else{
				boolean loginValid = true;//WSDLFactory.LoginCheck(userVO.getUserId(), password);
				if(!loginValid){
					this.addErrorMessage("请检查登录信息是否正确");
					return ActionSupport.ERROR;
				}
			}
			
			/**
			 * 查询用户角色
			 */
			QueryUserOfRoleStrategy strategy = new QueryUserOfRoleStrategy(userVO);
			List<String> roleIdList = (List<String>) this.getService().doProcess(strategy);
			
			if(roleIdList.size() < 1){
				throw new Exception("用户无角色信息配置,无法使用系统,请联系系统管理员。");
			}
			userVO.setRoleIdList(roleIdList);
			
			Struts2Utils.getSession().setAttribute(Constants.SESSION_USER, userVO);
			
			String cookieEncryptValue = userVO.getUserId();
			
			if(StringUtils.isNotBlank(cookieEncryptValue)){
				Cookie cookie = new Cookie(Constants.HTTP_COOKIE_NAME, cookieEncryptValue);
				cookie.setDomain(Constants.HTTP_COOKIE_DOMAIN);
				cookie.setPath("/");
				//cookie.setMaxAge(60*60*8);//Cookie生命周期为8个小时
				Struts2Utils.getResponse().addCookie(cookie);
			}
			
			return ActionSupport.SUCCESS;
			
		}catch(Exception ex){
			ex.printStackTrace();
			this.addErrorMessage(ex.getMessage());
			return ActionSupport.ERROR;
		}
	}
}
