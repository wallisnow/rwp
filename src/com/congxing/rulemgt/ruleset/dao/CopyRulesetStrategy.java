package com.congxing.rulemgt.ruleset.dao;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;

import com.congxing.core.hibernate.DAOFactory;
import com.congxing.core.hibernate.HibernateDAO;
import com.congxing.core.hibernate.Strategy;
import com.congxing.core.utils.LogUtils;
import com.congxing.core.web.constant.Constants;
import com.congxing.core.web.sequence.Sequence;
import com.congxing.rulemgt.reader.model.ReaderParamLogVO;
import com.congxing.rulemgt.reader.model.ReaderParamVO;
import com.congxing.rulemgt.reader.model.ReaderVO;
import com.congxing.rulemgt.reader.model.ReaderValueLogVO;
import com.congxing.rulemgt.reader.model.ReaderValueVO;
import com.congxing.rulemgt.ruleset.model.RuleLogVO;
import com.congxing.rulemgt.ruleset.model.RuleVO;
import com.congxing.rulemgt.ruleset.model.RulesetBoLogVO;
import com.congxing.rulemgt.ruleset.model.RulesetBoVO;
import com.congxing.rulemgt.ruleset.model.RulesetVO;
import com.congxing.rulemgt.storage.model.StorageParamLogVO;
import com.congxing.rulemgt.storage.model.StorageParamVO;
import com.congxing.rulemgt.storage.model.StorageVO;
import com.congxing.system.user.model.UserVO;

@SuppressWarnings("serial")
public class CopyRulesetStrategy implements Strategy {
	
	private RulesetVO rulesetVO;
	private UserVO userVO;
	private String versionDesc;
	
	private Map<String, String> boIds = new HashMap<String, String>();
	

	public CopyRulesetStrategy(RulesetVO rulesetVO, UserVO userVO, String versionDesc) {
		this.rulesetVO = rulesetVO;
		this.userVO = userVO;
		this.versionDesc = versionDesc;
	}
	
	public Object process() throws Exception {
		
		RulesetVO newRuleset = this.copyRulesetVO();
		//this.copyRuleSetFun(newRuleset);
		this.copyRuleBo(newRuleset);
		this.copyRule(newRuleset);
		this.copyReader(newRuleset);
		this.copyStorage(newRuleset);
		
		return rulesetVO;
	}
	
	private RulesetVO copyRulesetVO() throws Exception{
		HibernateDAO rulesetDao = DAOFactory.getInstance().createHibernateDAO(RulesetVO.class);
		RulesetVO newRuleset = new RulesetVO();
		BeanUtils.copyProperties(newRuleset, rulesetVO);
		//newRuleset.setRulesetId(Sequence.getSequence());
		newRuleset.setRulesetVersion(VersionHleper.getNewVersion(rulesetVO.getRulesetId()));
		newRuleset.setStatus(Constants.RULESET_STATUS_DRAFT);
		newRuleset.setCreateTime(new Date());
		newRuleset.setCreator(userVO.getUserId());
		newRuleset.setVersionDesc(versionDesc);
		rulesetDao.save(newRuleset);
		
		return newRuleset;
	}
	
	@SuppressWarnings("unchecked")
	private void copyRule(RulesetVO newRuleset) throws Exception{
		HibernateDAO ruleDao = DAOFactory.getInstance().createHibernateDAO(RuleVO.class);
		HibernateDAO ruleLogDao = DAOFactory.getInstance().createHibernateDAO(RuleLogVO.class);
		String hql = "from RuleVO where rulesetId = ? and rulesetVersion = ?";
		List<RuleVO> list = (List<RuleVO>)ruleDao.findByHQL(hql, new Object[]{rulesetVO.getRulesetId(), rulesetVO.getRulesetVersion()});
		for(RuleVO ruleVO : list){
			RuleVO newRuleVO = new RuleVO();
			BeanUtils.copyProperties(newRuleVO, ruleVO);
			newRuleVO.setRulesetId(newRuleset.getRulesetId());
			newRuleVO.setRulesetVersion(newRuleset.getRulesetVersion());
			newRuleVO.setRuleId(Sequence.getSequence());
			ruleDao.save(newRuleVO);
			ruleLogDao.save(LogUtils.createLogs(newRuleVO, RuleLogVO.class, Constants.OPRTYPE_ADD, userVO.getUserId()));
		}
	}
	
	/*@SuppressWarnings("unchecked")
	private void copyRuleSetFun(RulesetVO newRuleset) throws Exception{
		HibernateDAO funDao = DAOFactory.getInstance().createHibernateDAO(RulesetFunVO.class);
		HibernateDAO funLogDao = DAOFactory.getInstance().createHibernateDAO(RulesetFunLogVO.class);
		String hql = "from RulesetFunVO where rulesetId = ? and rulesetVersion = ?";
		List<RulesetFunVO> list = (List<RulesetFunVO>)funDao.findByHQL(hql, new Object[]{rulesetVO.getRulesetId(), rulesetVO.getRulesetVersion()});
		for(RulesetFunVO funVO : list){
			RulesetFunVO newFunVO = new RulesetFunVO();
			BeanUtils.copyProperties(newFunVO, funVO);
			newFunVO.setRulesetId(newRuleset.getRulesetId());
			newFunVO.setRulesetVersion(newRuleset.getRulesetVersion());
			newFunVO.setFunId(Sequence.getSequence());
			funDao.save(newFunVO);
			funLogDao.save(LogUtils.createLogs(newFunVO, RulesetFunLogVO.class, Constants.OPRTYPE_ADD, userVO.getUserId()));
		}
	}*/
	
	@SuppressWarnings("unchecked")
	private void copyRuleBo(RulesetVO newRuleset) throws Exception{
		HibernateDAO boDao = DAOFactory.getInstance().createHibernateDAO(RulesetBoVO.class);
		HibernateDAO boLogDao = DAOFactory.getInstance().createHibernateDAO(RulesetBoLogVO.class);;
		String hql = "from RulesetBoVO where rulesetId = ? and rulesetVersion = ?";
		List<RulesetBoVO> list = (List<RulesetBoVO>)boDao.findByHQL(hql, new Object[]{rulesetVO.getRulesetId(), rulesetVO.getRulesetVersion()});
		for(RulesetBoVO boVO : list){
			RulesetBoVO newBoVO = new RulesetBoVO();
			BeanUtils.copyProperties(newBoVO, boVO);
			newBoVO.setRulesetId(newRuleset.getRulesetId());
			newBoVO.setRulesetVersion(newRuleset.getRulesetVersion());
			newBoVO.setBoId(Sequence.getSequence());
			boIds.put(String.valueOf(boVO.getBoId()), String.valueOf(newBoVO.getBoId()));
			boDao.save(newBoVO);
			boLogDao.save(LogUtils.createLogs(newBoVO, RulesetBoLogVO.class, Constants.OPRTYPE_ADD, userVO.getUserId()));
		}
	}
	
	@SuppressWarnings("unchecked")
	private void copyReader(RulesetVO newRuleset) throws Exception{
		HibernateDAO readerDao = DAOFactory.getInstance().createHibernateDAO(ReaderVO.class);
		
		String hql = "from ReaderVO where rulesetId = ? and rulesetVersion = ?";
		List<ReaderVO> list = (List<ReaderVO>)readerDao.findByHQL(hql, new Object[]{rulesetVO.getRulesetId(), rulesetVO.getRulesetVersion()});
		for(ReaderVO readerVO : list){
			
			ReaderVO newReaderVO = new ReaderVO();
			BeanUtils.copyProperties(newReaderVO, readerVO);
			newReaderVO.setRulesetId(newRuleset.getRulesetId());
			newReaderVO.setRulesetVersion(newRuleset.getRulesetVersion());
			newReaderVO.setReaderId(Sequence.getSequence());
			readerDao.save(newReaderVO);
			
			this.copyReaderValue(readerVO, newReaderVO);
			this.copyReaderParam(readerVO, newReaderVO);
		}
	}
	
	@SuppressWarnings("unchecked")
	private void copyReaderValue(ReaderVO oldReaderVO, ReaderVO newReaderVO ) throws Exception{
		HibernateDAO readerValueDao = DAOFactory.getInstance().createHibernateDAO(ReaderValueVO.class);
		HibernateDAO readerValueLogDao = DAOFactory.getInstance().createHibernateDAO(ReaderValueLogVO.class);
		String hql = "from ReaderValueVO where readerId = ?";
		List<ReaderValueVO> list = (List<ReaderValueVO>)readerValueDao.findByHQL(hql, new Object[]{oldReaderVO.getReaderId()});
		for(ReaderValueVO valueVO : list){
			ReaderValueVO newValueVO = new ReaderValueVO();
			BeanUtils.copyProperties(newValueVO, valueVO);
			newValueVO.setReaderId(newReaderVO.getReaderId());
			newValueVO.setValueId(Sequence.getSequence());
			if(null != newValueVO.getBoId()){
				String oldBoId = String.valueOf(newValueVO.getBoId());
				String newBoId = boIds.get(oldBoId);
				newValueVO.setBoId(new Long(newBoId));
			}
			readerValueDao.save(newValueVO);
			readerValueLogDao.save(LogUtils.createLogs(newValueVO, ReaderValueLogVO.class, Constants.OPRTYPE_ADD, userVO.getUserId()));
		}
	}
	
	@SuppressWarnings("unchecked")
	private void copyReaderParam(ReaderVO oldReaderVO, ReaderVO newReaderVO ) throws Exception{
		HibernateDAO readerParamDao = DAOFactory.getInstance().createHibernateDAO(ReaderParamVO.class);
		HibernateDAO readerParamLogDao = DAOFactory.getInstance().createHibernateDAO(ReaderParamLogVO.class);
		String hql = "from ReaderParamVO where readerId = ?";
		List<ReaderParamVO> list = (List<ReaderParamVO>)readerParamDao.findByHQL(hql, new Object[]{oldReaderVO.getReaderId()});
		for(ReaderParamVO paramVO : list){
			ReaderParamVO newParamVO = new ReaderParamVO();
			BeanUtils.copyProperties(newParamVO, paramVO);
			newParamVO.setReaderId(newReaderVO.getReaderId());
			newParamVO.setParamId(Sequence.getSequence());
			
			if(Constants.PARAMTYPE_FACTOR.equals(newParamVO.getParamType())){
				String oldParamValue = newParamVO.getParamValue();
				String newParamValue = boIds.get(oldParamValue);
				newParamVO.setParamValue(newParamValue);
			}
			
			readerParamDao.save(newParamVO);
			
			readerParamLogDao.save(LogUtils.createLogs(newParamVO, ReaderParamLogVO.class, Constants.OPRTYPE_ADD, userVO.getUserId()));
		}
	}
	
	@SuppressWarnings("unchecked")
	public void copyStorage(RulesetVO newRuleset) throws Exception{
		HibernateDAO storageDao = DAOFactory.getInstance().createHibernateDAO(StorageVO.class);
		String hql = "from StorageVO where rulesetId = ? and rulesetVersion = ?";
		List<StorageVO> list = (List<StorageVO>)storageDao.findByHQL(hql, new Object[]{rulesetVO.getRulesetId(), rulesetVO.getRulesetVersion()});
		for(StorageVO storageVO : list){
			StorageVO newStorageVO = new StorageVO();
			BeanUtils.copyProperties(newStorageVO, storageVO);
			newStorageVO.setStorageId(Sequence.getSequence());
			newStorageVO.setRulesetId(newRuleset.getRulesetId());
			newStorageVO.setRulesetVersion(newRuleset.getRulesetVersion());
			
			storageDao.save(newStorageVO);
			
			this.copyStorageParam(storageVO, newStorageVO);
		}
	}
	
	@SuppressWarnings("unchecked")
	private void copyStorageParam(StorageVO oldStorageVO, StorageVO newStorageVO ) throws Exception{
		HibernateDAO paramDao = DAOFactory.getInstance().createHibernateDAO(StorageParamVO.class);
		HibernateDAO paramLogDao = DAOFactory.getInstance().createHibernateDAO(StorageParamLogVO.class);
		String hql = "from StorageParamVO where storageId = ?";
		List<StorageParamVO> list = (List<StorageParamVO>)paramDao.findByHQL(hql, new Object[]{oldStorageVO.getStorageId()});
		for(StorageParamVO paramVO : list){
			StorageParamVO newParamVO = new StorageParamVO();
			BeanUtils.copyProperties(newParamVO, paramVO);
			newParamVO.setStorageId(newStorageVO.getStorageId());
			newParamVO.setParamId(Sequence.getSequence());
			
			if(Constants.PARAMTYPE_FACTOR.equals(newParamVO.getParamType())){
				String oldParamValue = newParamVO.getParamValue();
				String newParamValue = boIds.get(oldParamValue);
				newParamVO.setParamValue(newParamValue);
			}
			
			paramDao.save(newParamVO);
			
			paramLogDao.save(LogUtils.createLogs(newParamVO, StorageParamLogVO.class, Constants.OPRTYPE_ADD, userVO.getUserId()));
		}
	}

	public RulesetVO getRulesetVO(RulesetVO newRuleset) {
		return rulesetVO;
	}

	public void setRulesetVO(RulesetVO rulesetVO) {
		this.rulesetVO = rulesetVO;
	}

	public UserVO getUserVO() {
		return userVO;
	}

	public void setUserVO(UserVO userVO) {
		this.userVO = userVO;
	}
	
}
