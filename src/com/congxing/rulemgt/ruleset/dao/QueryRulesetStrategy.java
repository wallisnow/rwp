/**  

* @Title: QueryRulesetStrategy.java

* @Package com.congxing.rulemgt.ruleset.dao

* @Description: TODO(用一句话描述该文件做什么)

* @author LIUKANGJIN

* @date 2014-1-22 上午09:40:19

* @version V1.0  

*/

package com.congxing.rulemgt.ruleset.dao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.congxing.core.hibernate.DAOFactory;
import com.congxing.core.hibernate.HibernateDAO;
import com.congxing.core.hibernate.Page;
import com.congxing.core.hibernate.Strategy;
import com.congxing.rulemgt.reader.model.ReaderParamVO;
import com.congxing.rulemgt.reader.model.ReaderVO;
import com.congxing.rulemgt.reader.model.ReaderValueVO;
import com.congxing.rulemgt.ruleset.model.RulesetBoVO;
import com.congxing.rulemgt.ruleset.model.RulesetFunVO;
import com.congxing.rulemgt.ruleset.model.RulesetListVO;
import com.congxing.rulemgt.ruleset.model.RulesetVO;
import com.congxing.rulemgt.storage.model.StorageParamVO;
import com.congxing.rulemgt.storage.model.StorageVO;

/**
 * <p>@Description: </p>
 *
 * <p>@author: LIUKANGJIN</p>
 *
 * <p>@date: 2014-1-22<p>
 *
 * <p>@version: V.1.0<p>
 *
 */
@SuppressWarnings("serial")
public class QueryRulesetStrategy implements Strategy {
	
	private RulesetListVO listVO;
	
	public QueryRulesetStrategy(RulesetListVO listVO){
		this.listVO = listVO;
	}
	
	@SuppressWarnings("unchecked")
	public Object process() throws Exception {
		HibernateDAO rulesetDao = DAOFactory.getInstance().createHibernateDAO(RulesetVO.class);
		
		HibernateDAO boDao = DAOFactory.getInstance().createHibernateDAO(RulesetBoVO.class);
		HibernateDAO funDao = DAOFactory.getInstance().createHibernateDAO(RulesetFunVO.class);
		
		HibernateDAO readerDao = DAOFactory.getInstance().createHibernateDAO(ReaderVO.class);
		HibernateDAO readerParamDao = DAOFactory.getInstance().createHibernateDAO(ReaderParamVO.class);
		HibernateDAO readerValueDao = DAOFactory.getInstance().createHibernateDAO(ReaderValueVO.class);
		
		HibernateDAO storageDao = DAOFactory.getInstance().createHibernateDAO(StorageVO.class);
		HibernateDAO storageParamDao = DAOFactory.getInstance().createHibernateDAO(StorageParamVO.class);
		
		/**以创建时间倒序**/
		listVO.setOrderBy("createTime");
		listVO.setOrder("desc");
		
		Page page = rulesetDao.query(listVO);
		
		Collection<?> datas = page.getDatas();
		
		for(Iterator<?> it = datas.iterator(); it.hasNext();){
			RulesetVO rulesetVO = (RulesetVO) it.next();
			
			Map<String, Object> properties = new HashMap<String, Object>(2);
			properties.put("rulesetId", rulesetVO.getRulesetId());
			properties.put("rulesetVersion", rulesetVO.getRulesetVersion());
			
			List<?> boDatas = boDao.findByProperties(properties);
			List<?> funDatas = funDao.findByProperties(properties);
			if(null != boDatas && boDatas.size() > 0){
				rulesetVO.setBoDatas((List<RulesetBoVO>) boDatas);
			}else{
				rulesetVO.setBoDatas(new ArrayList<RulesetBoVO>());
			}
			if(null != funDatas && funDatas.size() > 0){
				rulesetVO.setFunDatas((List<RulesetFunVO>) funDatas);
			}else{
				rulesetVO.setFunDatas(new ArrayList<RulesetFunVO>());
			}
			
			List<ReaderVO> readerDatas = (List<ReaderVO>) readerDao.findByHQL(" FROM ReaderVO WHERE rulesetId = ? AND rulesetVersion = ? ORDER BY readerPriority DESC", rulesetVO.getRulesetId(), rulesetVO.getRulesetVersion());
			for(ReaderVO readerVO : readerDatas){
				List<?> paramDatas = readerParamDao.findByProperty("readerId", readerVO.getReaderId());
				if(null != paramDatas && paramDatas.size() > 0){
					readerVO.setParamDatas((List<ReaderParamVO>) paramDatas);
				}else{
					readerVO.setParamDatas(new ArrayList<ReaderParamVO>());
				}
				
				List<?> valueDatas = readerValueDao.findByProperty("readerId", readerVO.getReaderId());
				if(null != valueDatas && valueDatas.size() > 0){
					readerVO.setValueDatas((List<ReaderValueVO>)valueDatas);
				}else{
					readerVO.setValueDatas(new ArrayList<ReaderValueVO>());
				}
			}
			rulesetVO.setReaderDatas(readerDatas);
			
			List<StorageVO> storageDatas = (List<StorageVO>) storageDao.findByProperties(properties);
			for(StorageVO storageVO : storageDatas){
				List<?> paramsDatas = storageParamDao.findByProperty("storageId", storageVO.getStorageId());
				if(null != paramsDatas && paramsDatas.size() > 0){
					storageVO.setParamDatas((List<StorageParamVO>) paramsDatas);
				}else{
					storageVO.setParamDatas(new ArrayList<StorageParamVO>());
				}
			}
			rulesetVO.setStorageDatas(storageDatas);
			
		}
		
		return page;
	}

	public RulesetListVO getListVO() {
		return listVO;
	}

	public void setListVO(RulesetListVO listVO) {
		this.listVO = listVO;
	}
}
