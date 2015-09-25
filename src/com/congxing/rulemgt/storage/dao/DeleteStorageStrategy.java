package com.congxing.rulemgt.storage.dao;

import java.util.Date;
import java.util.List;

import org.springframework.beans.BeanUtils;

import com.congxing.core.hibernate.DAOFactory;
import com.congxing.core.hibernate.HibernateDAO;
import com.congxing.core.hibernate.Strategy;
import com.congxing.core.utils.PkUtils;
import com.congxing.core.web.constant.Constants;
import com.congxing.core.web.sequence.Sequence;
import com.congxing.rulemgt.storage.model.StorageParamLogVO;
import com.congxing.rulemgt.storage.model.StorageParamVO;
import com.congxing.rulemgt.storage.model.StorageVO;
import com.congxing.system.user.model.UserVO;

@SuppressWarnings("serial")
public class DeleteStorageStrategy implements Strategy {
	
	
	private String[] storageIds;;
	
	private UserVO userVO;

	public DeleteStorageStrategy(String[] storageIds, UserVO userVO) {
		this.storageIds = storageIds;
		this.userVO = userVO;
	}

	@SuppressWarnings("unchecked")
	public Object process() throws Exception {
		HibernateDAO storageDao = DAOFactory.getInstance().createHibernateDAO(StorageVO.class);
		HibernateDAO paramDao = DAOFactory.getInstance().createHibernateDAO(StorageParamVO.class);
		HibernateDAO paramLogDao = DAOFactory.getInstance().createHibernateDAO(StorageParamLogVO.class);
		
		
		for(String storageId : storageIds){
			StorageVO storageVO = (StorageVO) storageDao.findByPK(PkUtils.getPkVO(StorageVO.class, "storageId", storageId));
			if(null != storageVO){
				List<StorageParamVO> paramDatas = (List<StorageParamVO>) paramDao.findByProperty("storageId", storageVO.getStorageId());
				for(StorageParamVO vo: paramDatas){
					StorageParamLogVO logVO = new StorageParamLogVO();
					BeanUtils.copyProperties(vo, logVO);
					logVO.setLogId(Sequence.getSequence());
					logVO.setOprCode(userVO.getUserId());
					logVO.setOprTime(new Date());
					logVO.setOprType(Constants.OPRTYPE_DELETE);
					
					paramLogDao.save(logVO);
					paramDao.deleteByVO(vo);
				}
				storageDao.deleteByVO(storageVO);
			}
		}
		return null;
	}
	
	public String[] getStorageIds() {
		return storageIds;
	}

	public void setStorageIds(String[] storageIds) {
		this.storageIds = storageIds;
	}

	public UserVO getUserVO() {
		return userVO;
	}

	public void setUserVO(UserVO userVO) {
		this.userVO = userVO;
	}

}
