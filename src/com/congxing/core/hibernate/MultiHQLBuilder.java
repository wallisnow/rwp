package com.congxing.core.hibernate;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.congxing.core.web.constant.Constants;
import com.congxing.core.web.struts2.BaseListVO;

@SuppressWarnings("serial")
public class MultiHQLBuilder implements Serializable{
	
	private String selectHQL;
	
	private String countHQL;
	
	List<Condition> conditionList;
	
	private BaseListVO[] multiListVO;
	
	private int pageNo = Constants.DEFAULT_PAGENO;
	
	private int pageSize = Constants.DEFAULT_PAGESIZE;
	
	public MultiHQLBuilder(MultiParameter mutiParameter) throws Exception{
		multiListVO = mutiParameter.getMultiListVO();
		String[] voClassName = new String[multiListVO.length];
		String[] voClassAlias = new String[multiListVO.length];
		conditionList = new ArrayList<Condition>();
		
		StringBuffer orgFromHQL = new StringBuffer();
		String orgSelectHQL = " SELECT " + mutiParameter.getSelectHQL();//自动生成
		String orgJoinHQL = mutiParameter.getJoinHQL();
		
		/**
		 * orgSelectHQL like {0}, {1}
		 * orgJoinHQL 	like {0}.xx = {1}.yy
		 */
		for(int index = 0; index < multiListVO.length; index ++){
			voClassName[index] = multiListVO[index].getVoClassName();
			voClassAlias[index] = this.getAliasByVoClassName(multiListVO[index].getVoClassName(), index);

			conditionList.addAll(ConditionBuilder.build(multiListVO[index], voClassAlias[index]));
			
			orgFromHQL.append(voClassName[index]).append(" as ").append(voClassAlias[index]).append(" , ");
			orgSelectHQL = orgSelectHQL.replaceAll("\\{" + index + "\\}", voClassAlias[index]);
			orgJoinHQL = orgJoinHQL.replaceAll("\\{" + index + "\\}", voClassAlias[index]);
			/**
			 * 先不考虑多表联合查询条件：orderby、groupby
			 */
		}
		if(orgFromHQL.length() > 0){
			orgFromHQL.delete(orgFromHQL.length() - 2, orgFromHQL.length() - 1);
		}
		this.selectHQL = orgSelectHQL + " FROM " + orgFromHQL.toString() + " WHERE " + orgJoinHQL;
		this.countHQL = " SELECT COUNT(*) " + " FROM " + orgFromHQL.toString() + " WHERE " + orgJoinHQL;
		
		if(multiListVO[0].getPageNo() > 1){
			this.pageNo = multiListVO[0].getPageNo();
		}
		
		if(multiListVO[0].getPageSize() > 0){
			this.pageSize = multiListVO[0].getPageSize();
		}
		
	}
	
	/**
	 * 取类别名
	 * @param voClassName
	 * @param index
	 * @return
	 */
	protected String getAliasByVoClassName(String voClassName, int index) {		
		if (voClassName == null || voClassName.trim().length() < 1)
			return "";
		String alias = "";
		if (voClassName.lastIndexOf(".") != -1){
			alias = voClassName.substring(voClassName.lastIndexOf(".") + 1).toLowerCase();
		}else{
			alias = voClassName.toLowerCase();
		}
		alias = alias.trim() + index;//以类名加上序号名，防止表本身关联
		return alias.toString();
	}

	public String getSelectHQL() {
		return selectHQL;
	}

	public void setSelectHQL(String selectHQL) {
		this.selectHQL = selectHQL;
	}

	public String getCountHQL() {
		return countHQL;
	}

	public void setCountHQL(String countHQL) {
		this.countHQL = countHQL;
	}

	public List<Condition> getConditionList() {
		return conditionList;
	}

	public void setConditionList(List<Condition> conditionList) {
		this.conditionList = conditionList;
	}

	public BaseListVO[] getMultiListVO() {
		return multiListVO;
	}

	public void setMultiListVO(BaseListVO[] multiListVO) {
		this.multiListVO = multiListVO;
	}

	public int getPageNo() {
		return pageNo;
	}

	public void setPageNo(int pageNo) {
		this.pageNo = pageNo;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	
}
