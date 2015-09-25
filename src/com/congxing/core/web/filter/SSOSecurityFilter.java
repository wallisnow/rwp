package com.congxing.core.web.filter;

import java.io.IOException;
import java.util.List;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.congxing.core.service.CommonService;
import com.congxing.core.utils.SpringContextManager;
import com.congxing.core.web.constant.Constants;
import com.congxing.system.index.dao.QueryUserOfRoleStrategy;
import com.congxing.system.user.model.UserVO;

public class SSOSecurityFilter implements Filter {
	
	private static Logger logger = Logger.getLogger(SSOSecurityFilter.class);
	
	private FilterConfig filterConfig;
	
	private CommonService service;

	public void init(FilterConfig filterConfig) throws ServletException {
		this.filterConfig = filterConfig;
		this.service = SpringContextManager.getBean("commonServiceImpl");
	}

	@SuppressWarnings("unchecked")
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException, ServletException {
		try{
		
			HttpServletRequest hreq = (HttpServletRequest) request;
			HttpSession session = hreq.getSession();
			
			UserVO userVO = (UserVO) session.getAttribute(Constants.SESSION_USER);
			
			if(null == userVO){
				
				logger.info("try to get user information from cookie");
				
				Cookie []cookies = hreq.getCookies();
				String cookieValue = null;
				if(null != cookies && cookies.length > 0){
					for(Cookie cookie : cookies){
						if(Constants.HTTP_COOKIE_NAME.equals(cookie.getName())){
							cookieValue = cookie.getValue();
							break;
						}
					}
				}
				logger.info("[cookieValue=" + cookieValue + "]");
				
				if(StringUtils.isNotBlank(cookieValue)){
					String userId = cookieValue;
					if(StringUtils.isNotBlank(userId)){				
						userVO = (UserVO) this.getService().doFindFirstByHQL("FROM UserVO where userId = ? ", new Object[]{userId});
						if(null != userVO){
							QueryUserOfRoleStrategy strategy = new QueryUserOfRoleStrategy(userVO);
							List<String> roleIdList = (List<String>) this.getService().doProcess(strategy);
							userVO.setRoleIdList(roleIdList);
							hreq.getSession().setAttribute(Constants.SESSION_USER, userVO);
						}
					}
				}
			}
			
			filterChain.doFilter(request, response);
		}catch(Exception ex){
			filterConfig.getServletContext().log(ex.getMessage(), ex);
		}
	}
	
	public void destroy() {
		filterConfig = null;
	}

	public CommonService getService() {
		return service;
	}

	public void setService(CommonService service) {
		this.service = service;
	}

	public FilterConfig getFilterConfig() {
		return filterConfig;
	}

	public void setFilterConfig(FilterConfig filterConfig) {
		this.filterConfig = filterConfig;
	}

}
