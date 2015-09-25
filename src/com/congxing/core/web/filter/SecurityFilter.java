package com.congxing.core.web.filter;

import java.io.IOException;
import java.net.URLEncoder;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import com.congxing.core.service.CommonService;
import com.congxing.core.utils.SpringContextManager;
import com.congxing.core.web.constant.Constants;
import com.congxing.system.menu.dao.MenuPurChkStrategy;
import com.congxing.system.user.model.UserVO;

/**
 * <p>
 * Title: SecurityFilter
 * </p>
 * 
 * <p>
 * Description: SecurityFilter
 * </p>
 * 
 * <p>
 * Copyright: Copyright (c) Revenco
 * </p>
 * 
 * <p>
 * Company: Sunrise Tech Ltd.
 * </p>
 * 
 * @author Lai Weilong
 * @version 1.0
 * @since 2010-10-29
 * 
 */
public class SecurityFilter extends HttpServlet implements Filter {
	
	private static final long serialVersionUID = 1L;

	private static Logger log = Logger.getLogger(SecurityFilter.class);
	
	private CommonService service;

	private static final String RESIGN_PAGE = "/errorpage/require_login.jsp?LoginUrl=";

	private static final String PERMISSION_DENY_PAGE = "/errorpage/permission_deny.jsp";
	
	private static String[] localLoginPages = new String[]{
		"/",
		"/login.jsp"
	};
	
	private static String[] loginURI = new String[]{
		"/system/login.action",
		"/enterpwd.action",
		"/authentertoken.action"
	};
	
	private static String[] freePages = new String[]{
		"/system/logout.action",
		"/encode.jsp",
		"/errorpage/require_login.jsp",
		"/errorpage/permission_deny.jsp"
	};
	
	private FilterConfig filterConfig;
	
	/**
	 * 以下参数可以通过web.xml中的init-param来配置
	 */
	private String local_login_url;	

	public SecurityFilter() throws Exception {
	}
	
	public void init(FilterConfig filterConfig) {
		this.filterConfig = filterConfig;
		this.local_login_url = this.filterConfig.getInitParameter("local_login_url");
		this.service = SpringContextManager.getBean("commonServiceImpl");
	}
	
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) {
		try {
			HttpServletRequest hreq = (HttpServletRequest) request;
			HttpServletResponse hres = (HttpServletResponse) response;
			HttpSession session = hreq.getSession();
			String currentURI = hreq.getRequestURI();
			
			if (currentURI != null) {
				
				String contextPath = hreq.getContextPath();
				
				currentURI = currentURI.replaceFirst(contextPath, "");
				
				String currentURL = currentURI;
				
				if (hreq.getQueryString() != null) {
					currentURL = currentURL + "?" + hreq.getQueryString();
				}

				/*
				 * 1. 免检查的资源  -->  直接通过，展现请求的资源
				 */
				if (isProtectedResource(currentURI)) {
					
					UserVO user = (UserVO) session.getAttribute(Constants.SESSION_USER);
					
					/* 当条件检查不通过时的跳转URL */
					StringBuffer redirectURL = new StringBuffer();
					
					/* 重登录地址 */
					StringBuffer loginURL = null;
					
					if(isLoginURI(currentURI) || this.isLoginPage(currentURI)){
						/*
						 * 2. 请求登录页面或者登录action --> 直接通过，展现请求的资源
						 * 
						 */
						redirectURL = null;
						
					} else if(user == null){
						
						/* 无用户信息 */
						log.warn("user is null, who do it? the request is " + currentURL);

						redirectURL.append(contextPath).append(RESIGN_PAGE);

						loginURL = new StringBuffer();

						/*
						 * 4. 请求其它页面 --> 提示未登录，点击后可转至本地登录界面
						 * 
						 * 1) 因为登录地址有可能包含本地和远程，为了前端页面处理方便以及统一格式，
						 * 此处的本地登录地址就包含了contextPath，构成全路径的形式
						 * 
						 * 2) 将currentURL保存至session，当用户用同一窗口登录跳转回来时，
						 * 可以以此展现其原请求资源，此举用于提高客户感知
						 */
						loginURL.append(contextPath).append(local_login_url);
						session.setAttribute(Constants.SESSION_EXPECTURL, currentURL);
						
					} else if (!validatePermission(user, currentURL)) {
						/*
						 * 5. 用户信息存在，则检查权限  -->  无当前URI访问权限则跳转至无权限提示界面
						 */
						redirectURL.append(contextPath).append(PERMISSION_DENY_PAGE);						
					}
					
					if(redirectURL != null && redirectURL.length() > 0){						
						if(loginURL != null && loginURL.length() > 0){
							/*
							 * 附加重登录地址，因为其以URL参数形式存在，所以需要使用encode编码后再传输
							 */
							redirectURL.append(URLEncoder.encode(loginURL.toString(), "UTF-8"));
						}
						hres.sendRedirect(redirectURL.toString());
						return;
					}
				}
			}

			filterChain.doFilter(request, response);
		} catch (ServletException sx) {
			filterConfig.getServletContext().log(sx.getMessage(), sx);
		} catch (IOException iox) {
			filterConfig.getServletContext().log(iox.getMessage(), iox);
		} catch (Exception ex) {
			filterConfig.getServletContext().log(ex.getMessage(), ex);
		}
	}
	
	public void destroy() {
		filterConfig = null;
		local_login_url = null;
	}
	
	/**
	 * 是否受保护资源，判断逻辑以不带queryString的URI为依据
	 * 
	 * @param currentURI
	 * @return
	 * @throws Exception
	 */
	private boolean isProtectedResource(String currentURI) throws Exception {
		for(int i = 0; i < freePages.length; i++){
			String key = freePages[i];
			if(currentURI.compareTo(key) == 0){
				return false;
			}
		}
		return true;
	}
	
	/**
	 * 是否本地登录界面
	 * 
	 * 简单来说，就是登录用的jsp定义，包括默认定义"/"
	 * 
	 * @param currentURI
	 * @return
	 * @throws Exception
	 */
	private boolean isLoginPage(String currentURI) throws Exception {
		for(int i = 0; i < localLoginPages.length; i++){
			String key = localLoginPages[i];
			if(currentURI.compareTo(key) == 0){
				return true;
			}
		}		
		return false;
	}
	
	/**
	 * 是否本地登录流程中的操作节点
	 * 
	 * 简单来说，就是处理类是否"com.maywide.commons.component.loginout.view.LoginAction"的struts action定义
	 * 
	 * @param currentURI
	 * @return
	 * @throws Exception
	 */
	private boolean isLoginURI(String currentURI) throws Exception {
		for(int i = 0; i < loginURI.length; i++){
			String key = loginURI[i];
			if(currentURI.compareTo(key) == 0){
				return true;
			}
		}		
		return false;
	}	

	/**
	 * 判断当前操作员对请求的url是否有访问权限
	 * 
	 * @param user
	 * @param currentURL
	 * @return
	 * @throws Exception
	 */
	private boolean validatePermission(UserVO user, String currentURL) throws Exception {
		try {
			MenuPurChkStrategy strategy = new MenuPurChkStrategy(user, currentURL);
			Boolean result = (Boolean) this.getService().doProcess(strategy);
			return result.booleanValue();
		} catch (Exception ex) {
			return false;
		}
	}

	public CommonService getService() {
		return service;
	}

	public void setService(CommonService service) {
		this.service = service;
	}

}
