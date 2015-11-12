/**
 * auto-generated code
 * 2013-06-05 03:16:38
 */
package com.congxing.rulemgt.ruleset.view;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import com.congxing.core.hibernate.Page;
import com.congxing.core.pack.DefaultBody;
import com.congxing.core.pack.DefaultHeader;
import com.congxing.core.pack.PackInfo;
import com.congxing.core.utils.DateFormatFactory;
import com.congxing.core.utils.JsonUtils;
import com.congxing.core.utils.ParamsBuilder;
import com.congxing.core.utils.PkUtils;
import com.congxing.core.utils.Struts2Utils;
import com.congxing.core.web.constant.Constants;
import com.congxing.core.web.sequence.Sequence;
import com.congxing.core.web.struts2.BaseAction;
import com.congxing.core.web.struts2.JsonResult;
import com.congxing.rulemgt.ruleset.dao.CopyRulesetStrategy;
import com.congxing.rulemgt.ruleset.dao.DeleteRuleStrategy;
import com.congxing.rulemgt.ruleset.dao.QueryRulesetStrategy;
import com.congxing.rulemgt.ruleset.dao.RulesetStrategy;
import com.congxing.rulemgt.ruleset.dao.SaveRuleStrategy;
import com.congxing.rulemgt.ruleset.dao.VersionHleper;
import com.congxing.rulemgt.ruleset.model.RuleExchangeVO;
import com.congxing.rulemgt.ruleset.model.RuleListVO;
import com.congxing.rulemgt.ruleset.model.RuleVO;
import com.congxing.rulemgt.ruleset.model.RulesetBoListVO;
import com.congxing.rulemgt.ruleset.model.RulesetBoVO;
import com.congxing.rulemgt.ruleset.model.RulesetFunListVO;
import com.congxing.rulemgt.ruleset.model.RulesetFunVO;
import com.congxing.rulemgt.ruleset.model.RulesetListVO;
import com.congxing.rulemgt.ruleset.model.RulesetVO;
import com.congxing.rulemgt.ruleset.socket.SocketPool;
import com.congxing.rulemgt.ruleset.socket.SocketPoolFactory;
import com.opensymphony.xwork2.ActionSupport;

/**
 * <p>
 * Title: RulesetAction <br/>
 * Description: Struts maipreviewRulesetJsonn control Class for Ruleset <br/>
 * Copyright: Copyright (c) 2006 <br/>
 * Company: Revenco Tech Ltd. <br/>
 * </p>
 * 
 * @author LIUKANGJIN
 * @version 1.0
 * @since 2013-06-05
 */
@SuppressWarnings("serial")
public class RulesetAction extends BaseAction {

	static Logger logger = Logger.getLogger(RulesetAction.class);

	public RulesetAction() {
		this.voClass = RulesetVO.class;
		this.pkNameArray = new String[] { "rulesetId", "rulesetVersion" };
	}

	/*
	 * 只查版本一的记录
	 * 
	 */
	public String list() {
		try {
			paramsMap = ParamsBuilder.buildMapFromHttpRequest();
			RulesetListVO listVO = new RulesetListVO();
			StringBuffer sql = new StringBuffer(
					"select ruleset_id rulesetId,ruleset_name rulesetName,ruleset_type rulesetType, ruleset_desc rulesetDesc  from RM_RULESET where 1=1 ");
			String[] param = null;
			this.setParamsMapToTargetObject(paramsMap, listVO);
			if (StringUtils.isNotBlank(listVO.get_seq_rulesetType())
					&& StringUtils.isNotBlank(listVO.get_sk_rulesetName())) {
				sql.append(" AND ruleset_type = ? AND ruleset_name like ?");
				param = new String[] { listVO.get_seq_rulesetType(), "%" + listVO.get_sk_rulesetName() + "%" };
			} else if (StringUtils.isNotBlank(listVO.get_seq_rulesetType())) {
				sql.append(" AND ruleset_type = ?");
				param = new String[] { listVO.get_seq_rulesetType() };
			} else if (StringUtils.isNotBlank(listVO.get_sk_rulesetName())) {
				sql.append(" AND ruleset_name like ?");
				param = new String[] { "%" + listVO.get_sk_rulesetName() + "%" };
			}

			sql.append(" GROUP BY ruleset_id,ruleset_name,ruleset_type, ruleset_desc ORDER BY RULESET_ID DESC ");
			String statSql = "select count(1) from (" + sql.toString() + " ) a";
			listVO.set_sk_rulesetVersion(VersionHleper.DEF_VERSION_SUF);
			List<RulesetVO> datas = this.getService().doQueryList(sql.toString(), RulesetVO.class,
					listVO.getPageNo() - 1, listVO.getPageSize(), param);
			Map<String, Object> params = null;
			for (RulesetVO vo : datas) {
				params = new HashMap<String, Object>();
				params.put("rulesetName", vo.getRulesetName());
				RulesetVO temp = (RulesetVO) this.getService().doFindFirstByProperties(voClass, params);
				if (temp != null) {
					vo.setRulesetVersion(temp.getRulesetVersion());
				}
			}
			int count = (int) this.getService().doStat(statSql, param);
			page = new Page();
			page.setDatas(datas);
			page.setPageNo(listVO.getPageNo());
			page.setPageSize(listVO.getPageSize());
			page.setTotalCount(count);
		} catch (Exception ex) {
			ex.printStackTrace();
			this.addErrorMessage(ex.getMessage());
			return ActionSupport.ERROR;
		}
		return ActionSupport.SUCCESS;
	}

	public String select() {
		try {
			paramsMap = ParamsBuilder.buildMapFromHttpRequest();
			RulesetListVO listVO = new RulesetListVO();
			this.setParamsMapToTargetObject(paramsMap, listVO);
			page = this.getService().doQuery(voClass, listVO);
		} catch (Exception ex) {
			ex.printStackTrace();
			this.addErrorMessage(ex.getMessage());
			return ActionSupport.ERROR;
		}
		return ActionSupport.SUCCESS;
	}

	public String listdetail() {
		try {
			paramsMap = ParamsBuilder.buildMapFromHttpRequest();
			RulesetListVO listVO = new RulesetListVO();
			this.setParamsMapToTargetObject(paramsMap, listVO);
			if (StringUtils.isNotBlank(listVO.get_dle_createTime())) {
				listVO.set_dle_createTime(listVO.get_dle_createTime() + " 23:59:59");
			}
			page = this.getService().doQuery(voClass, listVO);
		} catch (Exception ex) {
			ex.printStackTrace();
			this.addErrorMessage(ex.getMessage());
			return ActionSupport.ERROR;
		}
		return ActionSupport.SUCCESS;
	}

	public String add() {
		try {
			paramsMap = ParamsBuilder.buildMapFromHttpRequest();
			this.setParamsMapToTargetObject(paramsMap, this.getEntityVO());
			this.setAddEntityVO(this.getEntityVO(), paramsMap, userVO);
			RulesetVO rulesetVO = (RulesetVO) this.getEntityVO();
			rulesetVO.setRulesetId(Sequence.getSequence());
			this.setTargetObjectToParamsMap(this.getEntityVO(), paramsMap);
			this.setNew(true);
			this.setEdit(true);
		} catch (Exception ex) {
			ex.printStackTrace();
			this.addErrorMessage(ex.getMessage());
			return ActionSupport.ERROR;
		}
		return ActionSupport.SUCCESS;
	}

	@SuppressWarnings("unchecked")
	public String save() {
		try {
			paramsMap = ParamsBuilder.buildMapFromHttpRequest();
			String isNew = (String) paramsMap.get(Constants.PAGE_ISNEW);
			RulesetVO vo = new RulesetVO();
			this.setParamsMapToTargetObject(paramsMap, vo);
			if (Constants.STRING_TRUE.equals(isNew)) {
				vo.setStatus(Constants.RULESET_STATUS_DRAFT);
				vo.setRulesetVersion(VersionHleper.getNewVersion(null));
				vo.setCreateTime(new Date());
				vo.setCreator(userVO.getUserId());
				entityVO = this.getService().doCreate(voClass, vo, userVO);
				this.addInfoMessage("保存成功");
			} else {
				List<RulesetVO> oldList = (List<RulesetVO>) this.getService().doFindByProperty(voClass, "rulesetId",
						vo.getRulesetId());
				for (RulesetVO oldVO : oldList) {
					oldVO.setRulesetName(vo.getRulesetName());
					oldVO.setRulesetDesc(vo.getRulesetDesc());
					oldVO.setRulesetType(vo.getRulesetType());
					entityVO = this.getService().doUpdate(voClass, oldVO, userVO);
				}
				this.addInfoMessage("修改成功");
			}
			this.setTargetObjectToParamsMap(entityVO, paramsMap);
			this.setEdit(false);
			this.setNew(false);
		} catch (Exception ex) {
			ex.printStackTrace();
			this.addErrorMessage("操作失败,失败原因:" + ex.getMessage());
			return ActionSupport.ERROR;
		}
		return ActionSupport.SUCCESS;
	}

	public void publicJson() throws IOException {
		try {
			paramsMap = ParamsBuilder.buildMapFromHttpRequest();
			if (!paramsMap.containsKey("PK")) {
				this.sendJsonMessage(new JsonResult(ActionSupport.ERROR, "请选择记录"));
			}
			String[] pkValues = ((String) paramsMap.get("PK")).split("\\,");
			if (null != pkValues && pkValues.length > 0) {
				for (int index = 0; index < pkValues.length; index++) {
					RulesetVO rulesetVO = (RulesetVO) this.getService().doFindByPK(RulesetVO.class,
							PkUtils.getPkVO(RulesetVO.class, pkNameArray, pkValues[index]));
					rulesetVO.setStatus(Constants.RULESET_STATUS_USING);
					this.getService().doUpdate(RulesetVO.class, rulesetVO,userVO);
				}
			}
			this.sendJsonMessage(new JsonResult(ActionSupport.SUCCESS, "操作成功"));
		} catch (Exception ex) {
			ex.printStackTrace();
			this.sendJsonMessage(new JsonResult(ActionSupport.ERROR, ex.getMessage()));
		}
	}

	public void copyJson() throws IOException {
		try {
			paramsMap = ParamsBuilder.buildMapFromHttpRequest();
			if (!paramsMap.containsKey("PK")) {
				this.sendJsonMessage(new JsonResult(ActionSupport.ERROR, "请选择记录"));
			}
			String[] pkValues = ((String) paramsMap.get("PK")).split("\\,");
			String versionDesc = (String) paramsMap.get("versionDesc");
			if (null != pkValues && pkValues.length > 0) {
				for (int index = 0; index < pkValues.length; index++) {
					RulesetVO rulesetVO = (RulesetVO) this.getService().doFindByPK(RulesetVO.class,
							PkUtils.getPkVO(RulesetVO.class, pkNameArray, pkValues[index]));
					if (null != rulesetVO) {
						CopyRulesetStrategy strategy = new CopyRulesetStrategy(rulesetVO, userVO, versionDesc);
						this.getService().doProcess(strategy);
					}
				}
			}
			this.sendJsonMessage(new JsonResult(ActionSupport.SUCCESS, "操作成功"));
		} catch (Exception ex) {
			ex.printStackTrace();
			this.sendJsonMessage(new JsonResult(ActionSupport.ERROR, ex.getMessage()));
		}
	}

	public void overdueJson() throws IOException {
		try {
			paramsMap = ParamsBuilder.buildMapFromHttpRequest();
			if (!paramsMap.containsKey("PK")) {
				this.sendJsonMessage(new JsonResult(ActionSupport.ERROR, "请选择记录"));
			}
			String[] pkValues = ((String) paramsMap.get("PK")).split("\\,");
			if (null != pkValues && pkValues.length > 0) {
				for (int index = 0; index < pkValues.length; index++) {
					RulesetVO rulesetVO = (RulesetVO) this.getService().doFindByPK(RulesetVO.class,
							PkUtils.getPkVO(RulesetVO.class, pkNameArray, pkValues[index]));
					rulesetVO.setStatus(Constants.RULESET_STATUS_REVOKE);
					this.getService().doUpdate(RulesetVO.class, rulesetVO, userVO);
				}
			}
			this.sendJsonMessage(new JsonResult(ActionSupport.SUCCESS, "操作成功"));
		} catch (Exception ex) {
			ex.printStackTrace();
			this.sendJsonMessage(new JsonResult(ActionSupport.ERROR, ex.getMessage()));
		}
	}

	public String listEditAble() {
		try {
			paramsMap = ParamsBuilder.buildMapFromHttpRequest();
			RulesetListVO listVO = new RulesetListVO();
			this.setParamsMapToTargetObject(paramsMap, listVO);
			page = (Page) this.getService().doProcess(new QueryRulesetStrategy(listVO));
		} catch (Exception ex) {
			ex.printStackTrace();
			this.addErrorMessage(ex.getMessage());
			return ActionSupport.ERROR;
		}
		return ActionSupport.SUCCESS;
	}

	/**
	 * 进入规则集对象配置界面
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public String editRulesetCfg() {
		try {
			paramsMap = ParamsBuilder.buildMapFromHttpRequest();
			String rulesetPK = (String) paramsMap.get("rulesetPK");
			if (StringUtils.isBlank(rulesetPK)) {
				throw new Exception("非法进入");
			}
			RulesetVO rulesetVO = (RulesetVO) this.getService().doFindByPK(RulesetVO.class,
					PkUtils.getPkVO(voClass, pkNameArray, rulesetPK));
			if (null == rulesetVO) {
				throw new Exception("规则集信息不存在");
			}
			/* 查询规则集业务对象 */
			RulesetBoListVO boListVO = new RulesetBoListVO();
			boListVO.set_leq_rulesetId("" + rulesetVO.getRulesetId());
			boListVO.set_seq_rulesetVersion(rulesetVO.getRulesetVersion());
			boListVO.setFindAll(true);
			boListVO.setOrderBy("boId");
			boListVO.setOrder("asc");

			rulesetVO.setBoDatas((List<RulesetBoVO>) this.getService().dofindDatas(RulesetBoVO.class, boListVO));

			/* 查询规则集函数对象 */
			RulesetFunListVO funListVO = new RulesetFunListVO();
			funListVO.set_leq_rulesetId("" + rulesetVO.getRulesetId());
			funListVO.set_seq_rulesetVersion(rulesetVO.getRulesetVersion());
			funListVO.setFindAll(true);
			funListVO.setOrderBy("funId");
			funListVO.setOrder("asc");

			rulesetVO.setFunDatas((List<RulesetFunVO>) this.getService().dofindDatas(RulesetFunVO.class, funListVO));

			Struts2Utils.getRequest().setAttribute("RulesetVO", rulesetVO);
		} catch (Exception ex) {
			this.addErrorMessage(ex.getMessage());
			ex.printStackTrace();
			return ActionSupport.ERROR;
		}
		return ActionSupport.SUCCESS;
	}

	/**
	 * 保存规则集业务对象和函数对象
	 * 
	 * @return
	 */
	public String saveRulesetCfg() {
		try {
			paramsMap = ParamsBuilder.buildMapFromHttpRequest();
			String rulesetPK = (String) paramsMap.get("rulesetPK");
			if (StringUtils.isBlank(rulesetPK)) {
				throw new Exception("非法进入");
			}
			RulesetVO rulesetVO = (RulesetVO) this.getService().doFindByPK(RulesetVO.class,
					PkUtils.getPkVO(voClass, pkNameArray, rulesetPK));
			if (null == rulesetVO) {
				throw new Exception("规则集信息不存在");
			}

			String[] boNames = this.getParamValueByParamsMap(paramsMap, "boName");
			String[] boDescs = this.getParamValueByParamsMap(paramsMap, "boDesc");
			String[] dataTypes = this.getParamValueByParamsMap(paramsMap, "dataType");

			String[] funNames = this.getParamValueByParamsMap(paramsMap, "funName");
			String[] funDescs = this.getParamValueByParamsMap(paramsMap, "funDesc");

			List<RulesetBoVO> boDatas = new ArrayList<RulesetBoVO>();
			for (int idx = 0; idx < boNames.length; idx++) {
				RulesetBoVO boVO = new RulesetBoVO();
				boVO.setRulesetId(rulesetVO.getRulesetId());
				boVO.setRulesetVersion(rulesetVO.getRulesetVersion());
				boVO.setBoName(boNames[idx]);
				boVO.setBoDesc(boDescs[idx]);
				boVO.setDataType(dataTypes[idx]);
				boDatas.add(boVO);
			}
			rulesetVO.setBoDatas(boDatas);

			List<RulesetFunVO> funDatas = new ArrayList<RulesetFunVO>();
			for (int idx = 0; idx < funNames.length; idx++) {
				RulesetFunVO funVO = new RulesetFunVO();
				funVO.setRulesetId(rulesetVO.getRulesetId());
				funVO.setRulesetVersion(rulesetVO.getRulesetVersion());
				funVO.setFunName(funNames[idx]);
				funVO.setFunDesc(funDescs[idx]);
				funDatas.add(funVO);
			}
			rulesetVO.setFunDatas(funDatas);
			RulesetStrategy strategy = new RulesetStrategy(rulesetVO, userVO);

			rulesetVO = (RulesetVO) this.getService().doProcess(strategy);

			Struts2Utils.getRequest().setAttribute("RulesetVO", rulesetVO);
			this.addInfoMessage("保存成功");
		} catch (Exception ex) {
			this.addErrorMessage(ex.getMessage());
			ex.printStackTrace();
			return ActionSupport.ERROR;
		}
		return ActionSupport.SUCCESS;
	}

	/**
	 * 规则列表信息
	 * 
	 * @return
	 */
	public String listRule() {
		try {
			paramsMap = ParamsBuilder.buildMapFromHttpRequest();
			String rulesetPK = (String) paramsMap.get("rulesetPK");
			if (StringUtils.isBlank(rulesetPK)) {
				throw new Exception("非法进入");
			}
			RulesetVO rulesetVO = (RulesetVO) this.getService().doFindByPK(voClass,
					PkUtils.getPkVO(voClass, pkNameArray, rulesetPK));
			if (null == rulesetVO) {
				throw new Exception("规则集信息不存在");
			}

			RuleListVO detListVO = new RuleListVO();
			detListVO.set_leq_rulesetId("" + rulesetVO.getRulesetId());
			detListVO.set_seq_rulesetVersion(rulesetVO.getRulesetVersion());
			detListVO.setFindAll(true);
			detListVO.setOrderBy("ruleSalience");
			detListVO.setOrder("desc");

			paramsMap.put("status", rulesetVO.getStatus());
			Struts2Utils.setRequestAttribute("RuleDatas", this.getService().dofindDatas(RuleVO.class, detListVO));
		} catch (Exception ex) {
			this.addErrorMessage(ex.getMessage());
			ex.printStackTrace();
			return ActionSupport.ERROR;
		}
		return ActionSupport.SUCCESS;
	}

	public String addRule() {
		try {
			paramsMap = ParamsBuilder.buildMapFromHttpRequest();
			String rulesetPK = (String) paramsMap.get("rulesetPK");
			if (StringUtils.isBlank(rulesetPK)) {
				throw new Exception("非法进入");
			}
			RulesetVO rulesetVO = (RulesetVO) this.getService().doFindByPK(RulesetVO.class,
					PkUtils.getPkVO(voClass, pkNameArray, rulesetPK));
			if (null == rulesetVO) {
				throw new Exception("规则集信息不存在");
			}

			RuleVO ruleVO = new RuleVO();
			ruleVO.setRuleId(Sequence.getSequence());
			ruleVO.setRulesetId(rulesetVO.getRulesetId());

			// 初始化数据
			ruleVO.setRuleZhName(
					"规则_" + DateFormatFactory.getInstance("yyyyMMdd_HHmm").format(new Date()).replace("-", ""));
			ruleVO.setRuleEnName(
					"RULE_" + DateFormatFactory.getInstance("yyyyMMdd_HHmm").format(new Date()).replace("-", ""));
			ruleVO.setRuleSalience(new Long(10));

			this.setTargetObjectToParamsMap(ruleVO, paramsMap);
			Struts2Utils.setRequestAttribute("RuleVO", ruleVO);
			this.setEdit(true);
			this.setNew(true);
		} catch (Exception ex) {
			this.addErrorMessage(ex.getMessage());
			ex.printStackTrace();
			return ActionSupport.ERROR;
		}
		return ActionSupport.SUCCESS;
	}

	public String addRuleManual() {
		return this.addRule();
	}

	public String editRule() {
		try {
			paramsMap = ParamsBuilder.buildMapFromHttpRequest();
			String ruleId = (String) paramsMap.get("ruleId");
			if (StringUtils.isBlank(ruleId)) {
				throw new Exception("非法进入");
			}
			RuleVO ruleVO = (RuleVO) this.getService().doFindByPK(RuleVO.class,
					PkUtils.getPkVO(RuleVO.class, "ruleId", ruleId));
			if (null == ruleVO) {
				throw new Exception("规则集信息不存在");
			}

			this.setTargetObjectToParamsMap(ruleVO, paramsMap);
			Struts2Utils.setRequestAttribute("RuleVO", ruleVO);
			this.setEdit(true);
			this.setNew(false);
		} catch (Exception ex) {
			this.addErrorMessage(ex.getMessage());
			ex.printStackTrace();
			return ActionSupport.ERROR;
		}
		return ActionSupport.SUCCESS;
	}

	public String editRuleManual() {
		return this.editRule();
	}

	/**
	 * 删除规则信息
	 * 
	 * @throws IOException
	 */
	public void deleteRuleJson() throws IOException {
		try {
			paramsMap = ParamsBuilder.buildMapFromHttpRequest();
			if (!paramsMap.containsKey("ruleIds")) {
				throw new Exception("非法进入");
			}
			String[] ruleIds = StringUtils.split((String) paramsMap.get("ruleIds"), "\\|");
			if (ruleIds.length < 1) {
				throw new Exception("非法进入");
			}
			DeleteRuleStrategy strategy = new DeleteRuleStrategy(ruleIds, userVO);
			this.getService().doProcess(strategy);
			this.sendJsonMessage(JsonResult.create(ActionSupport.SUCCESS, "删除成功"));
		} catch (Exception ex) {
			this.addErrorMessage(ex.getMessage());
			ex.printStackTrace();
			this.sendJsonMessage(JsonResult.create(ActionSupport.ERROR, ex.getMessage()));
		}
	}

	/**
	 * 页面调用获取业务对象定义信息
	 */
	@SuppressWarnings("unchecked")
	public void listRulesetBoJson() throws IOException {
		try {
			paramsMap = ParamsBuilder.buildMapFromHttpRequest();

			String rulesetPK = (String) paramsMap.get("rulesetPK");

			if (StringUtils.isBlank(rulesetPK)) {
				throw new Exception("非法进入");
			}

			String[] items = StringUtils.split(rulesetPK, "\\|");

			/* 查询规则集业务对象 */
			RulesetBoListVO boListVO = new RulesetBoListVO();
			boListVO.set_leq_rulesetId(items[0]);
			boListVO.set_seq_rulesetVersion(items[1]);
			boListVO.setFindAll(true);
			boListVO.setOrderBy("boId");
			boListVO.setOrder("asc");

			List<RulesetBoVO> boDates = (List<RulesetBoVO>) this.getService().dofindDatas(RulesetBoVO.class, boListVO);

			this.sendJsonMessage(JsonResult.create(ActionSupport.SUCCESS, JsonUtils.toJson(boDates)));
		} catch (Exception ex) {
			this.addErrorMessage(ex.getMessage());
			ex.printStackTrace();
			this.sendJsonMessage(JsonResult.create(ActionSupport.ERROR, ex.getMessage()));
		}
	}

	/**
	 * 页面调用获取业务对象定义信息
	 */
	@SuppressWarnings("unchecked")
	public void listRulesetFunJson() throws IOException {
		try {
			paramsMap = ParamsBuilder.buildMapFromHttpRequest();

			String rulesetPK = (String) paramsMap.get("rulesetPK");

			if (StringUtils.isBlank(rulesetPK)) {
				throw new Exception("非法进入");
			}
			String[] items = StringUtils.split(rulesetPK, "\\|");

			/* 查询规则集函数对象 */
			RulesetFunListVO funListVO = new RulesetFunListVO();
			funListVO.set_leq_rulesetId(items[0]);
			funListVO.set_seq_rulesetVersion(items[1]);
			funListVO.setFindAll(true);
			funListVO.setOrderBy("funId");
			funListVO.setOrder("asc");

			List<RulesetFunVO> boDates = (List<RulesetFunVO>) this.getService().dofindDatas(RulesetFunVO.class,
					funListVO);

			this.sendJsonMessage(JsonResult.create(ActionSupport.SUCCESS, JsonUtils.toJson(boDates)));
		} catch (Exception ex) {
			this.addErrorMessage(ex.getMessage());
			ex.printStackTrace();
			this.sendJsonMessage(JsonResult.create(ActionSupport.ERROR, ex.getMessage()));
		}
	}

	public String saveRule() {
		try {
			paramsMap = ParamsBuilder.buildMapFromHttpRequest();
			RuleVO ruleVO = new RuleVO();
			this.setParamsMapToTargetObject(paramsMap, ruleVO);
			String rulesetPK = (String) paramsMap.get("rulesetPK");
			String[] rulesetPkValues = rulesetPK.split("\\|");
			ruleVO.setRulesetId(Long.valueOf(rulesetPkValues[0]));
			ruleVO.setRulesetVersion(rulesetPkValues[1]);
			SaveRuleStrategy strategy = new SaveRuleStrategy(ruleVO, userVO);
			String isNew = (String) paramsMap.get(Constants.PAGE_ISNEW);
			if (Constants.STRING_TRUE.equals(isNew)) {
				strategy.setOprType(Constants.OPRTYPE_ADD);
			} else {
				strategy.setOprType(Constants.OPRTYPE_MODIFY);
			}
			ruleVO = (RuleVO) this.getService().doProcess(strategy);
			this.setTargetObjectToParamsMap(ruleVO, paramsMap);
			this.addInfoMessage("保存成功");
			this.setEdit(false);
			this.setNew(false);
		} catch (Exception ex) {
			this.addErrorMessage(ex.getMessage());
			ex.printStackTrace();
			return ActionSupport.ERROR;
		}
		return ActionSupport.SUCCESS;
	}

	public String saveRuleManual() {
		return this.saveRule();
	}

	/**
	 * 进入配置业务对象列表页面
	 * 
	 * @return
	 */
	public String listBoAndFunDatas() {
		return this.listEditAble();
	}

	/**
	 * 进入配置业务规则列表页面
	 * 
	 * @return
	 */
	public String listRuleDatas() {
		return this.listEditAble();
	}

	/**
	 * 进入配置取数器列表页面
	 * 
	 * @return
	 */
	public String listReaderDatas() {
		return this.listEditAble();
	}

	/**
	 * 进入配置取数器列表页面
	 * 
	 * @return
	 */
	public String listStorageDatas() {
		return this.listEditAble();
	}

	public String testRuleset() {
		paramsMap = ParamsBuilder.buildMapFromHttpRequest();
		return ActionSupport.SUCCESS;
	}

	/**
	 * 配置配置测试运行方法
	 * 
	 * @throws IOException
	 */
	@SuppressWarnings("unchecked")
	public void testRulesetJson() throws IOException {
		try {
			paramsMap = ParamsBuilder.buildMapFromHttpRequest();

			String rulesetPK = (String) paramsMap.get("rulesetPK");

			if (StringUtils.isBlank(rulesetPK)) {
				throw new Exception("非法进入");
			}
			String[] pkArr = rulesetPK.split("\\|");
			String hql = "FROM RulesetBoVO WHERE rulesetId = ? AND rulesetVersion = ?";
			List<RulesetBoVO> boDatas = (List<RulesetBoVO>) this.getService().doFindByHQL(hql, new Long(pkArr[0]),
					pkArr[1]);
			Map<String, Object> factor = new HashMap<String, Object>();

			for (RulesetBoVO boVO : boDatas) {
				String key = boVO.getBoName();
				if (paramsMap.containsKey(key)) {
					factor.put(key, paramsMap.get(key));
				} else {
					factor.put(key, "");
				}
			}

			RuleExchangeVO exchangeVO = new RuleExchangeVO();
			exchangeVO.setTargetId(new Long(pkArr[0]));
			exchangeVO.setVersion(pkArr[1]);
			exchangeVO.setFactor(factor);

			DefaultHeader header = new DefaultHeader();
			header.setCmdId("10001");
			DefaultBody body = new DefaultBody();
			body.setDatatrans(JsonUtils.toJson(exchangeVO));

			PackInfo packInfo = new PackInfo(header, body);

			SocketPool socketPool = SocketPoolFactory.getSocketPool("drools_server");
			Socket client = null;
			DataOutputStream out = null;
			DataInputStream in = null;
			JsonResult result = null;

			try {
				client = socketPool.getSocket();
				out = new DataOutputStream((client.getOutputStream()));

				logger.info("RulesetAction Begin RuleExchangeVO[" + JsonUtils.toJson(packInfo) + "]");

				byte[] request = packInfo.getBytes();

				out.write(request);
				out.flush();

				in = new DataInputStream(client.getInputStream());
				byte[] reply = new byte[] {};
				byte[] buffer = new byte[1024];
				int byteread = 0;
				while ((byteread = in.read(buffer)) != -1) {
					byte[] tmpReadBuf = new byte[byteread];
					System.arraycopy(buffer, 0, tmpReadBuf, 0, byteread);
					reply = ArrayUtils.addAll(reply, tmpReadBuf);
				}
				packInfo.setPackInfo(reply);
				logger.info("RulesetAction End Result[" + JsonUtils.toJson(packInfo) + "]");
				short code = Short.parseShort(BeanUtils.getProperty(packInfo.getBody(), "resultcode"));
				String datatrans = BeanUtils.getProperty(packInfo.getBody(), "datatrans");
				if (0 == code) {
					exchangeVO = JsonUtils.fromJson(datatrans, RuleExchangeVO.class);
					result = new JsonResult(ActionSupport.SUCCESS, JsonUtils.toJson(exchangeVO.getFactor()));
				} else {
					result = new JsonResult(ActionSupport.ERROR, datatrans);
				}
				this.sendJsonMessage(result);
			} catch (Exception ex) {
				ex.printStackTrace();
				throw new Exception(ex.getMessage());
			} finally {
				if (null != in) {
					in.close();
				}
				if (null != out) {
					out.close();
				}
				if (null != client) {
					client.close();
				}
				socketPool.free(client);
			}
		} catch (Exception ex) {
			this.addErrorMessage(ex.getMessage());
			ex.printStackTrace();
			this.sendJsonMessage(JsonResult.create(ActionSupport.ERROR, ex.getMessage()));
		}
	}

	/**
	 * 规则集对应规则预览
	 * 
	 * @throws IOException
	 */
	@SuppressWarnings("unchecked")
	public void previewRulesetJson() throws IOException {
		try {
			paramsMap = ParamsBuilder.buildMapFromHttpRequest();

			String pkValue = (String) paramsMap.get("rulesetPK");

			if (StringUtils.isBlank(pkValue)) {
				throw new Exception("非法进入");
			}
			String[] pkValues = pkValue.split("\\|");
			RuleListVO detListVO = new RuleListVO();
			detListVO.set_leq_rulesetId(pkValues[0]);
			detListVO.set_seq_rulesetVersion(pkValues[1]);
			detListVO.setFindAll(true);
			detListVO.setOrderBy("ruleSalience");
			detListVO.setOrder("desc");

			List<RuleVO> ruleDatas = (List<RuleVO>) this.getService().dofindDatas(RuleVO.class, detListVO);

			this.sendJsonMessage(JsonResult.create(ActionSupport.SUCCESS, JsonUtils.toJson(ruleDatas)));
		} catch (Exception ex) {
			this.addErrorMessage(ex.getMessage());
			ex.printStackTrace();
			this.sendJsonMessage(JsonResult.create(ActionSupport.ERROR, ex.getMessage()));
		}
	}

}
