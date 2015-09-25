package com.congxing.rulemgt.reader.dao;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;

import com.congxing.core.hibernate.DAOFactory;
import com.congxing.core.hibernate.HibernateDAO;
import com.congxing.core.hibernate.Strategy;
import com.congxing.core.web.constant.Constants;
import com.congxing.core.web.sequence.Sequence;
import com.congxing.rulemgt.reader.model.ReaderParamLogVO;
import com.congxing.rulemgt.reader.model.ReaderParamVO;
import com.congxing.rulemgt.reader.model.ReaderVO;
import com.congxing.rulemgt.reader.model.ReaderValueLogVO;
import com.congxing.rulemgt.reader.model.ReaderValueVO;
import com.congxing.system.user.model.UserVO;

@SuppressWarnings("serial")
public class SaveReaderStrategy implements Strategy {

	private ReaderVO readerVO;
	private UserVO userVO;
	private String oprType;

	public SaveReaderStrategy(ReaderVO readerVO, UserVO userVO) {
		this.readerVO = readerVO;
		this.userVO = userVO;
	}

	@SuppressWarnings("unchecked")
	public Object process() throws Exception {
		HibernateDAO readerDao = DAOFactory.getInstance().createHibernateDAO(ReaderVO.class);
		
		HibernateDAO valueDao = DAOFactory.getInstance().createHibernateDAO(ReaderValueVO.class);
		HibernateDAO valueLogDao = DAOFactory.getInstance().createHibernateDAO(ReaderValueLogVO.class);
		
		HibernateDAO paramDao = DAOFactory.getInstance().createHibernateDAO(ReaderParamVO.class);
		HibernateDAO paramLogDao = DAOFactory.getInstance().createHibernateDAO(ReaderParamLogVO.class);

		Map<String, Object> properties = new HashMap<String, Object>();
		properties.put("rulesetId", readerVO.getRulesetId());
		properties.put("rulesetVersion", readerVO.getRulesetVersion());
		properties.put("readerName", readerVO.getReaderName());
		
		List<ReaderVO> existDatas = (List<ReaderVO>) readerDao.findByProperties(properties);
		
		readerDao.getSession().clear();
		
		if(Constants.OPRTYPE_ADD.equals(oprType)){// 新增
			if(null != existDatas && existDatas.size() > 0){
				throw new Exception("取数器名称已经存在,请重新命名取数器信息");
			}
			
		}else{// 修改：先删除原来的数据再新增
			for(ReaderVO thisVO : existDatas){
				if(thisVO.getReaderId().longValue() != readerVO.getReaderId().longValue()){
					throw new Exception("取数器名称已经存在,请重新命名取数器信息");
				}
			}
			
			/**删除结果赋值信息**/
			List<ReaderValueVO> existValueDatas = (List<ReaderValueVO>) valueDao.findByProperty("readerId", readerVO.getReaderId());
			for(ReaderValueVO valueVO : existValueDatas){
				ReaderValueLogVO logVO = new ReaderValueLogVO();
				BeanUtils.copyProperties(logVO, valueVO);
				logVO.setLogId(Sequence.getSequence());
				logVO.setOprCode(userVO.getUserId());
				logVO.setOprType(Constants.OPRTYPE_DELETE);
				logVO.setOprTime(new Date());
				valueLogDao.save(logVO);
				valueDao.deleteByVO(valueVO);
			}
			/**删除参数赋值信息**/
			List<ReaderParamVO> existParamDatas = (List<ReaderParamVO>) paramDao.findByProperty("readerId", readerVO.getReaderId());
			for(ReaderParamVO paramVO : existParamDatas){
				ReaderParamLogVO logVO = new ReaderParamLogVO();
				BeanUtils.copyProperties(logVO, paramVO);
				logVO.setLogId(Sequence.getSequence());
				logVO.setOprCode(userVO.getUserId());
				logVO.setOprType(Constants.OPRTYPE_DELETE);
				logVO.setOprTime(new Date());
				paramLogDao.save(logVO);
				paramDao.deleteByVO(paramVO);
			}
		}
		
		for(ReaderValueVO valueVO : readerVO.getValueDatas()){
			ReaderValueLogVO logVO = new ReaderValueLogVO();
			BeanUtils.copyProperties(logVO, valueVO);
			logVO.setLogId(Sequence.getSequence());
			logVO.setOprCode(userVO.getUserId());
			logVO.setOprType(Constants.OPRTYPE_ADD);
			logVO.setOprTime(new Date());
			valueLogDao.save(logVO);
			valueDao.save(valueVO);
		}
		
		for(ReaderParamVO paramVO : readerVO.getParamDatas()){
			ReaderParamLogVO logVO = new ReaderParamLogVO();
			BeanUtils.copyProperties(logVO, paramVO);
			logVO.setLogId(Sequence.getSequence());
			logVO.setOprCode(userVO.getUserId());
			logVO.setOprType(Constants.OPRTYPE_ADD);
			logVO.setOprTime(new Date());
			paramLogDao.save(logVO);
			paramDao.save(paramVO);
		}

		readerDao.saveOrUpdate(readerVO);
		
		return readerVO;
	}

	public ReaderVO getReaderVO() {
		return readerVO;
	}

	public void setReaderVO(ReaderVO readerVO) {
		this.readerVO = readerVO;
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
