package com.congxing.system.syscode.view;

import java.io.IOException;
import java.io.Serializable;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.congxing.core.utils.ParamsBuilder;
import com.congxing.core.utils.PkUtils;
import com.congxing.core.web.constant.Constants;
import com.congxing.core.web.sequence.Sequence;
import com.congxing.core.web.struts2.BaseAction;
import com.congxing.core.web.struts2.Business;
import com.congxing.core.web.struts2.JsonResult;
import com.congxing.system.businesslog.model.BusinessLogVO;
import com.congxing.system.syscode.model.SysCodeListVO;
import com.congxing.system.syscode.model.SysCodeVO;
import com.congxing.system.syscode.service.SysCodeService;
import com.opensymphony.xwork2.ActionSupport;

@SuppressWarnings("serial")
public class SysCodeAction extends BaseAction {
	
	private SysCodeService sysCodeService;
	
	public SysCodeAction(){
		this.voClass = SysCodeVO.class;
		this.pkNameArray = new String[]{"seqId"};
	}
	
	public SysCodeService getSysCodeService() {
		return sysCodeService;
	}
	@Autowired @Qualifier("sysCodeServiceImpl")
	public void setSyscodeService(SysCodeService sysCodeService) {
		this.sysCodeService = sysCodeService;
	}
	
	public String syscodemgr(){
		return ActionSupport.SUCCESS;
	}
	
	/**
	 * 业务参数树
	 * @return
	 */
	public String tree(){
		try{
			paramsMap = ParamsBuilder.buildMapFromHttpRequest();
			SysCodeListVO listVO = new SysCodeListVO();
			this.setParamsMapToTargetObject(paramsMap, listVO);
			listVO.set_seq_kind("*");
			listVO.setFindAll(true);
			page = this.getService().doQuery(voClass, listVO);
		}catch(Exception ex){
			ex.printStackTrace();
			this.addActionMessage(ex.getMessage());
			return ActionSupport.ERROR;
		}
		return ActionSupport.SUCCESS;
	}
	
	/**
	 * 业务参数大类列表展示
	 * @return
	 */
	public String typeshow(){
		return ActionSupport.SUCCESS;
	}
	
	/**
	 * 业务参数大类列表查询
	 * @return
	 */
	public String typelist(){
		try{
			paramsMap = ParamsBuilder.buildMapFromHttpRequest();
			this.setParamsMapToTargetObject(paramsMap, this.getEntityListVO());
			SysCodeListVO listVO = (SysCodeListVO) this.getEntityListVO();
			listVO.set_seq_kind("*");
			page = this.getService().doQuery(voClass, this.getEntityListVO());
		}catch(Exception ex){
			ex.printStackTrace();
			this.addActionMessage(ex.getMessage());
			return ActionSupport.ERROR;
		}
		return ActionSupport.SUCCESS;
	}
	
	/**
	 * 增加业务参数大类
	 * @return
	 */
	public String typeadd(){
		paramsMap = ParamsBuilder.buildMapFromHttpRequest();
		paramsMap.put("seqId", Sequence.getSequence());
		paramsMap.put("kind", "*");
		this.setNew(true);
		this.setEdit(true);
		return ActionSupport.SUCCESS;
	}
	
	/**
	 * 保存业务参数大类
	 * @return
	 */
	public String typesave(){
		return this.save();
	}
	
	/**
	 * 删除业务参数大类
	 * @return
	 */
	public String typedelete(){
		return this.delete();
	}
	
	/**
	 * 编辑业务参数大类
	 * @return
	 */
	public String typeedit(){
		return this.edit();
	}
	
	/**
	 * 增加业务大类子项
	 */
	public String add(){
		try{
			paramsMap = ParamsBuilder.buildMapFromHttpRequest();
			String _seq_type = (String) paramsMap.get("_seq_type");
			if(StringUtils.isNotBlank(_seq_type)){
				SysCodeVO syscodeVO = this.getSysCodeService().doFindSysCodeVOByTypeAndKind(_seq_type, "*");
				syscodeVO.setKind("");
				syscodeVO.setSeqId(Sequence.getSequence());//新建对象需要重新定义主键
				this.entityVO = syscodeVO;
			}
			this.setTargetObjectToParamsMap(this.getEntityVO(), paramsMap);
			this.setNew(true);
			this.setEdit(true);
		}catch(Exception ex){
			ex.printStackTrace();
			this.addErrorMessage(ex.getMessage());
			return ActionSupport.ERROR;
		}
		return ActionSupport.SUCCESS;
	}
	
	/**
	 * 业务大类子项列表查询
	 */
	public String list(){
		try{
			paramsMap = ParamsBuilder.buildMapFromHttpRequest();
			this.setParamsMapToTargetObject(paramsMap, this.getEntityListVO());
			BeanUtils.setProperty(this.getEntityListVO(), "_sne_kind", "*");
			page = this.getService().doQuery(voClass, this.getEntityListVO());
		}catch(Exception ex){
			ex.printStackTrace();
			this.addErrorMessage(ex.getMessage());
			return ActionSupport.ERROR;
		}
		return ActionSupport.SUCCESS;
	}
	
	/**
	 * 保存业务参数定义
	 */
	public String save(){
		try{
			paramsMap = ParamsBuilder.buildMapFromHttpRequest();
			String isNew = (String) paramsMap.get(Constants.PAGE_ISNEW);
			this.setParamsMapToTargetObject(paramsMap, this.getEntityVO());
			if(Constants.STRING_TRUE.equals(isNew)){
				SysCodeVO syscodeVO = (SysCodeVO) this.getEntityVO();
				SysCodeVO orgVO = this.getSysCodeService().doFindSysCodeVOByTypeAndKind(syscodeVO.getType(), syscodeVO.getKind());
				if(null != orgVO){
					throw new Exception("已经存在业务参数定义");
				}
				BusinessLogVO logVO = BusinessLogVO.create(userVO, Constants.OPRTYPE_ADD, (Business)syscodeVO);
				entityVO = this.getService().doCreate(voClass, syscodeVO, BusinessLogVO.class, logVO);
				this.addInfoMessage("保存成功");
			} else {
				SysCodeVO sysCodeVO = (SysCodeVO) this.getEntityVO();
				SysCodeVO orgVO = this.getSysCodeService().doFindSysCodeVOByTypeAndKind(sysCodeVO.getType(), sysCodeVO.getKind());
				if(null != orgVO && orgVO.getSeqId().longValue() != sysCodeVO.getSeqId().longValue()){
					throw new Exception("已经存在业务参数定义");
				}
				
				BusinessLogVO logVO = BusinessLogVO.create(userVO, Constants.OPRTYPE_MODIFY, (Business)sysCodeVO);
				entityVO = this.getService().doUpdate(voClass, sysCodeVO, BusinessLogVO.class, logVO);
				
				this.addInfoMessage("修改成功");
			}
			this.setTargetObjectToParamsMap(entityVO, paramsMap);
			this.setEdit(false);
			this.setNew(false);
		}catch(Exception ex){
			ex.printStackTrace();
			this.addErrorMessage("操作失败,失败原因:" +  ex.getMessage());
			return ActionSupport.ERROR;
		}
		return ActionSupport.SUCCESS;
	}

	public String syscodetree(){
		return ActionSupport.SUCCESS;
	}
	
}
