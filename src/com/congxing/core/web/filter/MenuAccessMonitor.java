package com.congxing.core.web.filter;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.congxing.core.service.CommonService;
import com.congxing.core.utils.JsonUtils;
import com.congxing.core.utils.ParamsBuilder;
import com.congxing.core.utils.SpringContextManager;
import com.congxing.core.web.constant.Constants;
import com.congxing.core.web.sequence.Sequence;
import com.congxing.system.logindetstat.dao.LoginDetStrategy;
import com.congxing.system.menu.model.MenuVO;
import com.congxing.system.menumonitorconfig.model.MenuMonitorConfigVO;
import com.congxing.system.menumonitordet.model.MenuMonitorDetVO;
import com.congxing.system.menuvisitdet.model.MenuVisitDetVO;
import com.congxing.system.user.model.UserVO;

/**
 * 
 * 菜单接入监控
 * 
 * @author Administrator
 *
 */
public class MenuAccessMonitor implements Filter {

	public static boolean IS_MENU_MONITOR = true;

	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(MenuAccessMonitor.class);

	private FilterConfig filterConfig;

	private static final String IS_MENU_LOG = "menuLog";

	private static final String LOGIN_IN_URL = "/system/login.action";
	private static final String LOGIN_OUT_URL = "/system/loginout.action";

	private CommonService service;

	private static Map<String, String> menuMap = new HashMap<String, String>();

	public MenuAccessMonitor() {
	}

	public void init(FilterConfig filterConfig) throws ServletException {
		this.filterConfig = filterConfig;
		this.service = SpringContextManager.getBean("commonServiceImpl");
		String menuLog = this.filterConfig.getInitParameter(IS_MENU_LOG);
		if (StringUtils.isNotBlank(menuLog)) {
			IS_MENU_MONITOR = Boolean.valueOf(menuLog);
		}
		if (null == menuMap || menuMap.size() == 0) {
			try {
				initMenuMap();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public void doFilter(ServletRequest req, ServletResponse response, FilterChain filterChain)
			throws IOException, ServletException {
		/*
		 * 1. 允许设置是否记录菜单访问日志 2. 非http请求不予处理
		 */
		if (!(IS_MENU_MONITOR && req instanceof HttpServletRequest)) {
			filterChain.doFilter(req, response);
			return;
		}

		HttpServletRequest request = (HttpServletRequest) req;

		RequestInfo requestInfo = new RequestInfo(request);
		String info = requestInfo.getInfo();

		try {
			log2file(info, "Begin");
			Date beginTime = new Date();
			recordAdviceLog(requestInfo);
			filterChain.doFilter(req, response);
			Date endTime = new Date();

			/* 记录菜单查询日志信息 */
			recordMenuMonitorDetVO(request, beginTime, endTime);

			log2file(info, "End");

		} catch (ServletException sx) {
			log2file(info, "End-ExpServ");
			throw sx;
		} catch (IOException iox) {
			log2file(info, "End-ExpIO");
			throw iox;
		}
	}

	private void log2file(String info, String type) {
		StringBuffer buffer = new StringBuffer();
		buffer.append(type).append("|").append(info);
		logger.info(buffer.toString());
	}

	public void destroy() {
	}

	@SuppressWarnings("unchecked")
	private void initMenuMap() throws Exception {
		List<MenuVO> menus = (List<MenuVO>) this.getService().doFindALL(MenuVO.class);
		for (MenuVO menuVO : menus) {
			if (StringUtils.isNotBlank(menuVO.getMenuUrl())) {
				menuMap.put(menuVO.getMenuUrl(), menuVO.getMenuId());
			}
		}
	}

	/**
	 * 记录访问日志信息及登录日志信息
	 * 
	 * @param requestInfo
	 * @throws Exception
	 */
	private void recordAdviceLog(RequestInfo requestInfo) {
		try {
			if (StringUtils.isNotBlank(requestInfo.getCurrentURL()) || StringUtils.isBlank(requestInfo.getUserId())) {
				String currentUrl = requestInfo.getCurrentURL();
				if (menuMap.containsKey(currentUrl)) {
					String menuId = menuMap.get(currentUrl);
					MenuVisitDetVO detVO = new MenuVisitDetVO();
					detVO.setSeqId(Sequence.getSequence());
					detVO.setMenuId(menuId);
					detVO.setUserId(requestInfo.getUserId());
					detVO.setUserIp(requestInfo.getRemoteIP());
					detVO.setVisitTime(new java.util.Date());
					this.getService().doCreate(MenuVisitDetVO.class, detVO, null);
				} else if (currentUrl.indexOf(LOGIN_IN_URL) >= 0) {// 登录操作

					this.getService().doProcess(new LoginDetStrategy(requestInfo, false));

				} else if (currentUrl.indexOf(LOGIN_OUT_URL) >= 0) {// 退出操作

					this.getService().doProcess(new LoginDetStrategy(requestInfo, true));

				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	/**
	 * 记录查询日志信息
	 * 
	 * @param request
	 * @param beginTime
	 * @param endTime
	 */
	private void recordMenuMonitorDetVO(HttpServletRequest request, Date beginTime, Date endTime) {
		try {
			MenuMonitorConfigVO configVO = this.getMonitorConfigVOByQueryURL(request);

			if (null != configVO) {// 只针对有配置的菜单进行监控
				UserVO user = (UserVO) request.getSession().getAttribute(Constants.SESSION_USER);
				MenuMonitorDetVO detVO = new MenuMonitorDetVO();
				detVO.setSeqId(Sequence.getSequence());
				detVO.setUserId(user.getUserId());
				detVO.setUserIp(request.getRemoteHost());
				detVO.setMonitorId(configVO.getMonitorId());
				detVO.setOprParams(JsonUtils.toJson(ParamsBuilder.buildMapFromHttpRequest(request)));
				detVO.setBeginTime(beginTime);
				detVO.setEndTime(endTime);
				this.getService().doUpdate(MenuMonitorDetVO.class, detVO, null);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	/**
	 * 查询菜单监控配置信息
	 * 
	 * @param request
	 * @return
	 */
	private MenuMonitorConfigVO getMonitorConfigVOByQueryURL(HttpServletRequest request) {
		String contextPath = request.getContextPath();
		String currentURI = request.getRequestURI();
		String currentURL = currentURI.replaceFirst(contextPath, "");
		List<?> datas = this.getService().doFindByProperty(MenuMonitorConfigVO.class, "monitorUrl", currentURL);
		if (null != datas && datas.size() > 0) {
			return (MenuMonitorConfigVO) datas.get(0);
		}
		return null;
	}

	public CommonService getService() {
		return service;
	}

	public void setService(CommonService service) {
		this.service = service;
	}

}
