package com.congxing.system.user.dao;

import java.util.Date;
import java.util.List;

import org.apache.commons.beanutils.BeanUtils;

import com.congxing.core.hibernate.DAOFactory;
import com.congxing.core.hibernate.HibernateDAO;
import com.congxing.core.hibernate.Strategy;
import com.congxing.core.web.constant.Constants;
import com.congxing.core.web.sequence.Sequence;
import com.congxing.system.sysdp.model.SysDpVO;
import com.congxing.system.user.model.UserLogVO;
import com.congxing.system.user.model.UserVO;

@SuppressWarnings("serial")
public class UserStrategy implements Strategy {
	
	private UserVO curVO;
	
	private UserVO userVO;
	
	private String oprType = Constants.OPRTYPE_ADD;

	public UserStrategy(UserVO curVO, UserVO userVO) {
		this.curVO = curVO;
		this.userVO = userVO;
	}

	public Object process() throws Exception {
		HibernateDAO urerDAO = DAOFactory.getInstance().createHibernateDAO(UserVO.class);
		HibernateDAO urerLogDAO = DAOFactory.getInstance().createHibernateDAO(UserLogVO.class);
		HibernateDAO sysDpDAO = DAOFactory.getInstance().createHibernateDAO(SysDpVO.class);
		
		UserLogVO logVO = new UserLogVO();
		logVO.setLogId(Sequence.getSequence());
		logVO.setOprCode(userVO.getUserId());
		logVO.setOprTime(new Date());
		logVO.setOprType(oprType);
		
		SysDpVO dpVO = (SysDpVO) sysDpDAO.findByPK(curVO.getDpId());
		if(null == dpVO){
			throw new Exception("用户所在组织架构不存在");
		}
		
		curVO.setDpId(dpVO.getDpId());
		curVO.setDpName(dpVO.getDpName());
		
		if(Constants.OPRTYPE_ADD.equals(oprType)){
			List<?> list = urerDAO.findByHQL(" from UserVO where userId = ? ", new Object[]{curVO.getUserId()});
			if(null != list && list.size()> 0){
				throw new Exception("用户已经存在");
			}
			curVO.setModifyTime(new Date());
			curVO.setInitPwd(Constants.TYPE_YES);

			BeanUtils.copyProperties(logVO, curVO);
			urerLogDAO.save(logVO);
			
			urerDAO.save(curVO);
			
		}else if(Constants.OPRTYPE_MODIFY.equals(oprType)){
			UserVO thisVO = (UserVO) urerDAO.findByPK(curVO.getUserId());
			if(null == thisVO){
				throw new Exception("用户信息[" + curVO.getUserId() + "]不存在,修改失败");
			}else{
				thisVO.setStatus(curVO.getStatus());
				thisVO.setUserName(curVO.getUserName());
				thisVO.setMobileno(curVO.getMobileno());
				thisVO.setEmail(curVO.getEmail());
				thisVO.setDpId(curVO.getDpId());
				thisVO.setDpName(curVO.getDpName());
				
				BeanUtils.copyProperties(logVO, curVO);
				urerLogDAO.save(logVO);
				
				urerDAO.saveOrUpdate(thisVO);
			}
		}else{
			throw new Exception("操作标识错误");
		}
		return null;
	}

	public UserVO getCurVO() {
		return curVO;
	}

	public void setCurVO(UserVO curVO) {
		this.curVO = curVO;
	}

	public UserVO getUserVO() {
		return userVO;
	}

	public void setUserVO(UserVO userVO) {
		this.userVO = userVO;
	}

	public String getOprType() {
		return oprType;
	}

	public void setOprType(String oprType) {
		this.oprType = oprType;
	}

}
