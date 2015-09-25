package com.congxing.system.logindetstat.dao;

import java.util.Date;

import com.congxing.core.hibernate.DAOFactory;
import com.congxing.core.hibernate.HibernateDAO;
import com.congxing.core.hibernate.Strategy;
import com.congxing.core.web.filter.RequestInfo;
import com.congxing.core.web.sequence.Sequence;
import com.congxing.system.logindetstat.model.LoginDetVO;

@SuppressWarnings("serial")
public class LoginDetStrategy implements Strategy {
	
	private RequestInfo requestInfo;
	
	private boolean loginOut = false;
	
	/**
	 * 记录登录日志
	 * @param requestInfo	登录信息
	 * @param loginOut	是否为退出
	 */
	public LoginDetStrategy(RequestInfo requestInfo, boolean loginOut){
		this.requestInfo = requestInfo;
		this.loginOut = loginOut;
	}

	public Object process() throws Exception {
		HibernateDAO loginDetDao = DAOFactory.getInstance().createHibernateDAO(LoginDetVO.class);
		
		if(!loginOut){//登录系统
			LoginDetVO loginVO = new LoginDetVO();
			loginVO.setSeqId(Sequence.getSequence());
			loginVO.setUserIp(requestInfo.getRemoteIP());
			loginVO.setUserId(requestInfo.getUserId());
			loginVO.setLoginInTime(new Date());
			return loginDetDao.save(loginVO);
		}else{//退出系统
			LoginDetVO loginVO = (LoginDetVO) loginDetDao.findFirstByHQL(" FROM LoginDetVO WHERE userId = ? order by seqId desc ", new Object[]{requestInfo.getUserId()});
			if(null != loginVO){
				loginVO.setLoginOutTime(new Date());
				return loginDetDao.saveOrUpdate(loginVO);
			}
		}
		return null;
	}

}
