package com.congxing.rulemgt.ruleset.dao;

import java.util.Date;
import java.util.List;

import org.springframework.beans.BeanUtils;

import com.congxing.core.hibernate.DAOFactory;
import com.congxing.core.hibernate.HibernateDAO;
import com.congxing.core.hibernate.Strategy;
import com.congxing.core.web.constant.Constants;
import com.congxing.core.web.sequence.Sequence;
import com.congxing.rulemgt.ruleset.model.RuleLogVO;
import com.congxing.rulemgt.ruleset.model.RuleVO;
import com.congxing.system.user.model.UserVO;

@SuppressWarnings("serial")
public class SaveRuleStrategy implements Strategy {

	private RuleVO ruleVO;
	private UserVO userVO;
	private String oprType;

	public SaveRuleStrategy(RuleVO ruleVO, UserVO userVO) {
		this.ruleVO = ruleVO;
		this.userVO = userVO;
	}

	@SuppressWarnings("unchecked")
	public Object process() throws Exception {
		HibernateDAO ruleDao = DAOFactory.getInstance().createHibernateDAO(RuleVO.class);
		HibernateDAO ruleLogDao = DAOFactory.getInstance().createHibernateDAO(RuleLogVO.class);
		
		RuleLogVO logVO = new RuleLogVO();
		logVO.setLogId(Sequence.getSequence());
		logVO.setOprCode(userVO.getUserId());
		logVO.setOprTime(new Date());
		logVO.setOprType(oprType);
		
		BeanUtils.copyProperties(ruleVO, logVO);
		
		List<RuleVO> existDatas = (List<RuleVO>) ruleDao.findByProperty("ruleEnName", ruleVO.getRuleEnName());
		ruleDao.getSession().clear();
		if(Constants.OPRTYPE_ADD.equals(oprType)){
			if(null != existDatas && existDatas.size() > 0){
				throw new Exception("规则名称已经存在,请重新命名规则信息");
			}
		}else{
			for(RuleVO thisVO : existDatas){
				if(thisVO.getRuleId().longValue() != ruleVO.getRuleId().longValue()){
					throw new Exception("规则名称已经存在,请重新命名规则信息");
				}
			}
		}

		ruleLogDao.save(logVO);
		ruleVO = (RuleVO) ruleDao.saveOrUpdate(ruleVO);
		return ruleVO;
	}

	public RuleVO getRuleVO() {
		return ruleVO;
	}

	public void setRuleVO(RuleVO ruleVO) {
		this.ruleVO = ruleVO;
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
