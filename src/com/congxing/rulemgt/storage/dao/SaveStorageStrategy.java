package com.congxing.rulemgt.storage.dao;

import java.util.Date;
import java.util.List;

import org.springframework.beans.BeanUtils;

import com.congxing.core.hibernate.DAOFactory;
import com.congxing.core.hibernate.HibernateDAO;
import com.congxing.core.hibernate.Strategy;
import com.congxing.core.web.constant.Constants;
import com.congxing.core.web.sequence.Sequence;
import com.congxing.rulemgt.storage.model.StorageParamLogVO;
import com.congxing.rulemgt.storage.model.StorageParamVO;
import com.congxing.rulemgt.storage.model.StorageVO;
import com.congxing.system.user.model.UserVO;

@SuppressWarnings("serial")
public class SaveStorageStrategy implements Strategy {

	private StorageVO storageVO;
	private UserVO userVO;
	private String oprType;

	public SaveStorageStrategy(StorageVO storageVO, UserVO userVO) {
		this.storageVO = storageVO;
		this.userVO = userVO;
	}

	@SuppressWarnings("unchecked")
	public Object process() throws Exception {
		HibernateDAO storageDao = DAOFactory.getInstance().createHibernateDAO(StorageVO.class);
		HibernateDAO paramDao = DAOFactory.getInstance().createHibernateDAO(StorageParamVO.class);
		HibernateDAO paramLogDao = DAOFactory.getInstance().createHibernateDAO(StorageParamLogVO.class);
		
		if(Constants.OPRTYPE_MODIFY.equals(oprType)){// 修改，先删除原来的数据再新增
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
		}else{
			storageVO.setSubCode(userVO.getUserId());
			storageVO.setSubDate(new Date());
		}
		
		//新增参数配置
		for(StorageParamVO newVO : storageVO.getParamDatas()){
			newVO.setParamId(Sequence.getSequence());
			newVO.setStorageId(storageVO.getStorageId());
			
			StorageParamLogVO logVO = new StorageParamLogVO();
			BeanUtils.copyProperties(newVO, logVO);
			logVO.setLogId(Sequence.getSequence());
			logVO.setOprCode(userVO.getUserId());
			logVO.setOprTime(new Date());
			logVO.setOprType(Constants.OPRTYPE_ADD);
			
			paramLogDao.save(logVO);
			paramDao.save(newVO);
			
		}
		
		storageDao.saveOrUpdate(storageVO);
		return storageVO;
	}

	public StorageVO getStorageVO() {
		return storageVO;
	}

	public void setStorageVO(StorageVO storageVO) {
		this.storageVO = storageVO;
	}

	public String getOprType() {
		return oprType;
	}

	public void setOprType(String oprType) {
		this.oprType = oprType;
	}

	public UserVO getUserVO() {
		return userVO;
	}

	public void setUserVO(UserVO userVO) {
		this.userVO = userVO;
	}

}
