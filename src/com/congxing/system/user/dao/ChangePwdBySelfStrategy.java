package com.congxing.system.user.dao;

import java.util.Date;

import org.apache.commons.beanutils.BeanUtils;

import com.congxing.core.hibernate.DAOFactory;
import com.congxing.core.hibernate.HibernateDAO;
import com.congxing.core.hibernate.Strategy;
import com.congxing.core.utils.MD5Encrypt;
import com.congxing.core.web.constant.Constants;
import com.congxing.core.web.sequence.Sequence;
import com.congxing.core.web.struts2.JsonResult;
import com.congxing.system.user.model.UserHisPwdVO;
import com.congxing.system.user.model.UserLogVO;
import com.congxing.system.user.model.UserVO;
import com.opensymphony.xwork2.ActionSupport;

@SuppressWarnings("serial")
public class ChangePwdBySelfStrategy implements Strategy {
	
	/* 用户ID */
	private String userId;
	
	/* 原始密码 */
	private String oldPwd;
	
	/* 新密码 */
	private String newPwd;
	
	public ChangePwdBySelfStrategy(String userId, String oldPwd, String newPwd) {
		this.userId = userId;
		this.oldPwd = oldPwd;
		this.newPwd = newPwd;
	}

	public Object process() throws Exception {
		HibernateDAO userDAO = DAOFactory.getInstance().createHibernateDAO(UserVO.class);
		HibernateDAO userLogDAO = DAOFactory.getInstance().createHibernateDAO(UserLogVO.class);
		HibernateDAO hisPwdDAO = DAOFactory.getInstance().createHibernateDAO(UserHisPwdVO.class);
		UserVO curUserVO = (UserVO)userDAO.findByPK(userId);
		
		JsonResult result = null;
		
		if(MD5Encrypt.getMD5Password(oldPwd).equals(curUserVO.getPassword())){			
			
			UserHisPwdVO hisPwdVO = (UserHisPwdVO) hisPwdDAO.findByPK(new UserHisPwdVO(userId, MD5Encrypt.getMD5Password(newPwd)));
			
			if(null != hisPwdVO){
				result = new JsonResult(ActionSupport.ERROR, "当前修改密码为历史使用密码,请重新修改!");
			}else{
				curUserVO.setPassword(MD5Encrypt.getMD5Password(newPwd));
				curUserVO.setInitPwd(Constants.TYPE_NO);//非初始密码
				curUserVO.setModifyTime(new Date());
				
				hisPwdVO = new UserHisPwdVO();
				hisPwdVO.setUserId(userId);
				hisPwdVO.setPassword(MD5Encrypt.getMD5Password(newPwd));
				hisPwdVO.setModifyTime(new Date());
				
				UserLogVO logVO = new UserLogVO();
				logVO.setLogId(Sequence.getSequence());
				logVO.setOprCode(userId);
				logVO.setOprType(Constants.OPRTYPE_MODIFY);
				logVO.setOprTime(new Date());
				BeanUtils.copyProperties(logVO, curUserVO);
				
				
				userLogDAO.save(logVO);
				userDAO.saveOrUpdate(curUserVO);
				hisPwdDAO.save(hisPwdVO);
				
				result = new JsonResult(ActionSupport.SUCCESS, "密码修改成功!");
			}
		}else{
			result = new JsonResult(ActionSupport.ERROR, "原始密码有误,密码修改失败!");
		}
		
		return result;
	}
}
