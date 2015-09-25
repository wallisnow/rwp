package com.congxing.rulemgt.ruleset.dao;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.BeanUtils;

import com.congxing.core.hibernate.DAOFactory;
import com.congxing.core.hibernate.HibernateDAO;
import com.congxing.core.hibernate.Strategy;
import com.congxing.core.web.constant.Constants;
import com.congxing.core.web.sequence.Sequence;
import com.congxing.rulemgt.ruleset.model.RulesetBoLogVO;
import com.congxing.rulemgt.ruleset.model.RulesetBoVO;
import com.congxing.rulemgt.ruleset.model.RulesetFunLogVO;
import com.congxing.rulemgt.ruleset.model.RulesetFunVO;
import com.congxing.rulemgt.ruleset.model.RulesetVO;
import com.congxing.system.user.model.UserVO;

@SuppressWarnings("serial")
public class RulesetStrategy implements Strategy {
	
	private RulesetVO rulesetVO;
	private UserVO userVO;

	public RulesetStrategy(RulesetVO rulesetVO, UserVO userVO) {
		this.rulesetVO = rulesetVO;
		this.userVO = userVO;
	}
	
	@SuppressWarnings("unchecked")
	public Object process() throws Exception {
		HibernateDAO boDao = DAOFactory.getInstance().createHibernateDAO(RulesetBoVO.class);
		HibernateDAO boLogDao = DAOFactory.getInstance().createHibernateDAO(RulesetBoLogVO.class);
		HibernateDAO funDao = DAOFactory.getInstance().createHibernateDAO(RulesetFunVO.class);
		HibernateDAO funLogDao = DAOFactory.getInstance().createHibernateDAO(RulesetFunLogVO.class);
		
		/* 删除原有业务对象 */
		String hql = "from RulesetBoVO where rulesetId = ? and rulesetVersion = ?";
		List<RulesetBoVO> boDatas = (List<RulesetBoVO>)boDao.findByHQL(hql, new Object[]{rulesetVO.getRulesetId(), rulesetVO.getRulesetVersion()});
		
		Map<String,Long> oldSeqMap = new HashMap<String,Long>();
		
		for(RulesetBoVO boVO : boDatas){
			oldSeqMap.put(boVO.getBoName(), boVO.getBoId());
			RulesetBoLogVO boLogVO = new RulesetBoLogVO();
			boLogVO.setLogId(Sequence.getSequence());
			boLogVO.setOprCode(userVO.getUserId());
			boLogVO.setOprType(Constants.OPRTYPE_DELETE);
			boLogVO.setOprTime(new Date());
			BeanUtils.copyProperties(boVO, boLogVO);
			boLogDao.save(boLogVO);
			boDao.deleteByVO(boVO);
		}
		
		/* 删除原有函数对象 */
		String hql2 = "from RulesetFunVO where rulesetId = ? and rulesetVersion = ?";
		List<RulesetFunVO> funDatas = (List<RulesetFunVO>)funDao.findByHQL(hql2, new Object[]{rulesetVO.getRulesetId(), rulesetVO.getRulesetVersion()});
		
		for(RulesetFunVO funVO : funDatas){
			RulesetFunLogVO funLogVO = new RulesetFunLogVO();
			funLogVO.setLogId(Sequence.getSequence());
			funLogVO.setOprCode(userVO.getUserId());
			funLogVO.setOprType(Constants.OPRTYPE_DELETE);
			funLogVO.setOprTime(new Date());
			BeanUtils.copyProperties(funVO, funLogVO);
			funLogDao.save(funLogVO);
			funDao.deleteByVO(funVO);
		}
		
		for(RulesetBoVO boVO : rulesetVO.getBoDatas()){
			Long seqId = oldSeqMap.get(boVO.getBoName());
			if(seqId == null){
				seqId = Sequence.getSequence();
			}
			boVO.setBoId(seqId);
			
			RulesetBoLogVO boLogVO = new RulesetBoLogVO();
			boLogVO.setLogId(Sequence.getSequence());
			boLogVO.setOprCode(userVO.getUserId());
			boLogVO.setOprType(Constants.OPRTYPE_DELETE);
			boLogVO.setOprTime(new Date());
			BeanUtils.copyProperties(boVO, boLogVO);
			boLogDao.save(boLogVO);
			boDao.save(boVO);
		}
		
		for(RulesetFunVO funVO : rulesetVO.getFunDatas()){
			funVO.setFunId(Sequence.getSequence());
			
			RulesetFunLogVO funLogVO = new RulesetFunLogVO();
			funLogVO.setLogId(Sequence.getSequence());
			funLogVO.setOprCode(userVO.getUserId());
			funLogVO.setOprType(Constants.OPRTYPE_DELETE);
			funLogVO.setOprTime(new Date());
			BeanUtils.copyProperties(funVO, funLogVO);
			funLogDao.save(funLogVO);
			funDao.save(funVO);
		}
		
		return rulesetVO;
	}

	public RulesetVO getRulesetVO() {
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
