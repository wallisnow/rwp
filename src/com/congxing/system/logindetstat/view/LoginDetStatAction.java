/**
 * auto-generated code
 * 2013-05-30 07:17:18
 */
package com.congxing.system.logindetstat.view;

import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.congxing.core.utils.ParamsBuilder;
import com.congxing.core.web.constant.Constants;
import com.congxing.core.web.struts2.BaseAction;
import com.congxing.system.logindetstat.model.LoginDetStatListVO;
import com.congxing.system.logindetstat.model.LoginDetStatVO;
import com.congxing.system.user.model.UserVO;
import com.opensymphony.xwork2.ActionSupport;

/**
 * <p>
 * Title: LoginDetStatAction <br/>
 * Description: Struts main control Class for LoginDetStat <br/>
 * Copyright: Copyright (c) 2006 <br/>
 * Company: Revenco Tech Ltd. <br/>
 * </p>
 * 
 * @author LIUKANGJIN
 * @version 1.0
 * @since 2013-05-30
 */
@SuppressWarnings("serial")
public class LoginDetStatAction extends BaseAction {
    
    public LoginDetStatAction() {
        this.voClass = LoginDetStatVO.class;
        this.pkNameArray = new String[] { "userId","statDay" };        
    }
    
    @SuppressWarnings("unchecked")
	public String list(){
		try{
			paramsMap = ParamsBuilder.buildMapFromHttpRequest();
			LoginDetStatListVO listVO = new LoginDetStatListVO();
			this.setParamsMapToTargetObject(paramsMap, listVO);
			listVO.setOrderBy(" statDay ");
			listVO.setOrder(" asc ");
			
			page = this.getService().doQuery(LoginDetStatVO.class, listVO);
			List<LoginDetStatVO> datas = (List<LoginDetStatVO>) page.getDatas();
			for(LoginDetStatVO statVO : datas){
				if(StringUtils.isBlank(statVO.getUserId())){
					statVO.setLoginNum(0);
					continue;
				}
				statVO.setUserVO((UserVO)this.getService().doFindByPK(UserVO.class, statVO.getUserId()));
				Object visitNum = this.getService().doFindUniqueByHQL("select sum(visitNum) from MenuVisitDetStatVO where statDay = ? and userId =?  ", new Object[]{statVO.getStatDay(), statVO.getUserId()});
				if(null != visitNum){
					statVO.setVisitNum(Integer.valueOf(String.valueOf(visitNum)));
				}else{
					statVO.setVisitNum(0);
				}				
			}
		}catch(Exception ex){
			ex.printStackTrace();
			this.addErrorMessage(ex.getMessage());
			return ActionSupport.ERROR;
		}
		return ActionSupport.SUCCESS;
	}
	
	@SuppressWarnings("unchecked")
	public String excel(){
		try{
			paramsMap = ParamsBuilder.buildMapFromHttpRequest();
			LoginDetStatListVO listVO = new LoginDetStatListVO();
			this.setParamsMapToTargetObject(paramsMap, listVO);
			listVO.setPageNo(Constants.DEFAULT_PAGENO);
			listVO.setPageSize(Constants.DEFAULT_MAX_EXCEL);
			listVO.setOrderBy(" statDay ");
			listVO.setOrder(" asc ");
			page = this.getService().doQuery(LoginDetStatVO.class, listVO);
			List<LoginDetStatVO> datas = (List<LoginDetStatVO>) page.getDatas();
			for(LoginDetStatVO statVO : datas){
				if(StringUtils.isBlank(statVO.getUserId())){
					statVO.setLoginNum(0);
					continue;
				}
				statVO.setUserVO((UserVO)this.getService().doFindByPK(UserVO.class, statVO.getUserId()));
				Object visitNum = this.getService().doFindUniqueByHQL("select sum(visitNum) from MenuVisitDetStatVO where statDay = ? and userId =?  ", new Object[]{statVO.getStatDay(), statVO.getUserId()});
				if(null != visitNum){
					statVO.setVisitNum(Integer.valueOf(String.valueOf(visitNum)));
				}else{
					statVO.setVisitNum(0);
				}				
			}
		}catch(Exception ex){
			ex.printStackTrace();
			this.addErrorMessage(ex.getMessage());
			return ActionSupport.ERROR;
		}
		return ActionSupport.SUCCESS;
	}
	
}

