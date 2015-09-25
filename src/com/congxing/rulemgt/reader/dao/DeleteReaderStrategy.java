package com.congxing.rulemgt.reader.dao;

import java.util.Date;
import java.util.List;

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
public class DeleteReaderStrategy implements Strategy {
	
	
	private String[] readerIds;;
	
	private UserVO userVO;

	public DeleteReaderStrategy(String[] readerIds, UserVO userVO) {
		this.readerIds = readerIds;
		this.userVO = userVO;
	}

	@SuppressWarnings("unchecked")
	public Object process() throws Exception {
		HibernateDAO readerDao = DAOFactory.getInstance().createHibernateDAO(ReaderVO.class);
		
		HibernateDAO valueDao = DAOFactory.getInstance().createHibernateDAO(ReaderValueVO.class);
		HibernateDAO valueLogDao = DAOFactory.getInstance().createHibernateDAO(ReaderValueLogVO.class);
		
		HibernateDAO paramDao = DAOFactory.getInstance().createHibernateDAO(ReaderParamVO.class);
		HibernateDAO paramLogDao = DAOFactory.getInstance().createHibernateDAO(ReaderParamLogVO.class);
		
		for(String readerId : readerIds){
			ReaderVO readerVO = (ReaderVO) readerDao.findByPK(new Long(readerId));
			if(null != readerVO){
				
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
			readerDao.deleteByVO(readerVO);
		}
		return null;
	}
	
	public String[] getReaderIds() {
		return readerIds;
	}

	public void setReaderIds(String[] readerIds) {
		this.readerIds = readerIds;
	}

	public UserVO getUserVO() {
		return userVO;
	}

	public void setUserVO(UserVO userVO) {
		this.userVO = userVO;
	}

}
