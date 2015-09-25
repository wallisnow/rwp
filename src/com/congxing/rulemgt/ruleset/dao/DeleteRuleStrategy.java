package com.congxing.rulemgt.ruleset.dao;

import java.util.Date;

import com.congxing.core.hibernate.DAOFactory;
import com.congxing.core.hibernate.HibernateDAO;
import com.congxing.core.hibernate.Strategy;
import com.congxing.core.utils.PkUtils;
import com.congxing.core.web.constant.Constants;
import com.congxing.core.web.sequence.Sequence;
import com.congxing.rulemgt.ruleset.model.RuleLogVO;
import com.congxing.rulemgt.ruleset.model.RuleVO;
import com.congxing.system.user.model.UserVO;

@SuppressWarnings("serial")
public class DeleteRuleStrategy implements Strategy {
	
	
	private String[] ruleIds;;
	
	private UserVO userVO;

	public DeleteRuleStrategy(String[] ruleIds, UserVO userVO) {
		this.ruleIds = ruleIds;
		this.userVO = userVO;
	}

	public Object process() throws Exception {
		HibernateDAO ruleDao = DAOFactory.getInstance().createHibernateDAO(RuleVO.class);
		HibernateDAO ruleLogDao = DAOFactory.getInstance().createHibernateDAO(RuleLogVO.class);
		
		for(String ruleId : ruleIds){
			RuleVO ruleVO = (RuleVO) ruleDao.findByPK(PkUtils.getPkVO(RuleVO.class, "ruleId", ruleId));
			if(null != ruleVO){
				RuleLogVO ruleLogVO = new RuleLogVO();
				ruleLogVO.setLogId(Sequence.getSequence());
				ruleLogVO.setOprCode(userVO.getUserId());
				ruleLogVO.setOprType(Constants.OPRTYPE_DELETE);
				ruleLogVO.setOprTime(new Date());
				
				ruleLogDao.save(ruleLogVO);
				ruleDao.deleteByVO(ruleVO);
			}
		}
		return null;
	}
	
	public String[] getRuleIds() {
		return ruleIds;
	}

	public void setRuleIds(String[] ruleIds) {
		this.ruleIds = ruleIds;
	}

	public UserVO getUserVO() {
		return userVO;
	}

	public void setUserVO(UserVO userVO) {
		this.userVO = userVO;
	}

}
