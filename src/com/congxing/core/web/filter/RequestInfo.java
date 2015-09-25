package com.congxing.core.web.filter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.congxing.core.web.constant.Constants;
import com.congxing.system.user.model.UserVO;

public class RequestInfo {
	
	private String sessionId;
	private String userId;
	private String remoteIP;
	private String currentURL;
	
	public RequestInfo(HttpServletRequest request){
		remoteIP = request.getRemoteHost();
		currentURL = request.getRequestURI();
		
		if (request.getQueryString() != null) {
			currentURL = currentURL + "?" + request.getQueryString();
		}

		if (currentURL != null) {
			String contextPath = request.getContextPath();
			currentURL = currentURL.replaceFirst(contextPath, "");
		}

		HttpSession session = request.getSession();

		if (session != null) {
			sessionId = session.getId();

			UserVO user = (UserVO) session.getAttribute(Constants.SESSION_USER);

			if (null != user) {
				userId = user.getUserId();
			}else{
				userId = (String)request.getParameter("userId");
			}
		}
	}
	
	public String getInfo(){
		StringBuffer buffer = new StringBuffer(String.valueOf(System.currentTimeMillis())).append("|");
		buffer.append(userId).append("|").append(sessionId).append("|").append(remoteIP).append("|").append(currentURL);
		return buffer.toString();
	}
	
	public String getSessionId() {
		return sessionId;
	}
	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getRemoteIP() {
		return remoteIP;
	}
	public void setRemoteIP(String remoteIP) {
		this.remoteIP = remoteIP;
	}
	public String getCurrentURL() {
		return currentURL;
	}
	public void setCurrentURL(String currentURL) {
		this.currentURL = currentURL;
	}

}
