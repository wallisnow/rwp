package com.congxing.system.generalconfig.view;

import java.io.IOException;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URLDecoder;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.cfg.annotations.ResultsetMappingSecondPass;
import org.hibernate.jdbc.ReturningWork;

import com.congxing.core.dbtool.DBConnectionUtils;
import com.congxing.core.hibernate.Page;
import com.congxing.core.utils.JsonUtils;
import com.congxing.core.utils.ParamsBuilder;
import com.congxing.core.utils.PkUtils;
import com.congxing.core.utils.UserDefinedFormUtils;
import com.congxing.core.web.constant.Constants;
import com.congxing.core.web.sequence.Sequence;
import com.congxing.core.web.struts2.BaseAction;
import com.congxing.core.web.struts2.JsonResult;
import com.congxing.core.web.tree.TreeBuilder;
import com.congxing.rulemgt.dbconfig.model.DbConfigVO;
import com.congxing.system.generalconfig.dao.UserConfigFormStrategy;
import com.congxing.system.generalconfig.model.FieldNameVO;
import com.congxing.system.generalconfig.model.FieldVO;
import com.congxing.system.generalconfig.model.GeneralConfigVO;
import com.congxing.system.generalconfig.model.PreviewConfigVO;
import com.opensymphony.xwork2.ActionSupport;

/**
 * <p>
 * 次类对应体通用报表功能
 * </p>
 * 
 * @author WU JIANG
 * @version 1.0
 */
public class GeneralconfigAction extends BaseAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private List<FieldNameVO> fieldList;

	// private String targetTable;
	private Map<Integer, PreviewConfigVO> configMap;

	// 所有字段名，字段注释 map
	private Map<String, String> fieldMap;

	/**
	 * Class constructor
	 */
	public GeneralconfigAction() {
		this.voClass = GeneralConfigVO.class;
		this.pkNameArray = new String[] { "generalconfigId" };
	}

	/**
	 * 用户进入自定义报表后，输入关键字后进行查询
	 * 
	 * @return
	 * @throws Exception
	 */
	public String queryByKey() throws Exception {
		paramsMap = ParamsBuilder.buildMapFromHttpRequest();

		Object pk = paramsMap.get("code");
		String formPK = pk.toString();

		GeneralConfigVO gcvo = (GeneralConfigVO) this.getService().doFindByPK(GeneralConfigVO.class,
				Long.valueOf(formPK).longValue());

		String realsql = gcvo.getRealsql();
		if (realsql == null || !StringUtils.isNotBlank(realsql)) {
			throw new Exception("sql error");
		}

		@SuppressWarnings("unchecked")
		List<PreviewConfigVO> pcvo = (List<PreviewConfigVO>) this.getService().doFindByProperty(PreviewConfigVO.class,
				"generalConfig_id", formPK);
		List<FieldNameVO> selectKeys = new ArrayList<FieldNameVO>();

		// 查询条件配置抽取
		for (PreviewConfigVO previewConfigVO : pcvo) {
			// 字段被选定为查询条件时
			if (previewConfigVO.getIsSelectedAsKey() != null) {
				if (previewConfigVO.getIsSelectedAsKey().equals("T")) {
					if (previewConfigVO.getRename() != null) {
						// 如果此字段被重命名，那么启用重命名
						FieldNameVO fnvo = new FieldNameVO(previewConfigVO.getField(), previewConfigVO.getRename());
						selectKeys.add(fnvo);
					} else if (previewConfigVO.getFieldName() != null) {
						// 如果此字段有注释，那么启用注释
						FieldNameVO fnvo = new FieldNameVO(previewConfigVO.getField(), previewConfigVO.getFieldName());
						selectKeys.add(fnvo);
					} else {
						// 如果没有注释，也没有重命名，默认取字段编码本身为字段名
						FieldNameVO fnvo = new FieldNameVO(previewConfigVO.getField(), previewConfigVO.getField());
						selectKeys.add(fnvo);
					}
				}
			}
		}

		Map<String, Object> keyMap = new HashMap<String, Object>();

		for (String key : paramsMap.keySet()) {
			if (key.startsWith("key_")) {
				keyMap.put(key, paramsMap.get(key));
			}
		}

		int pageNo = Constants.DEFAULT_PAGENO;
		int pageSize = Constants.DEFAULT_PAGESIZE;

		if (!(paramsMap.get("pageNo") == null || paramsMap.get("pageSize") == null)) {
			pageNo = Integer.valueOf((String) paramsMap.get("pageNo"));
			pageSize = Integer.valueOf((String) paramsMap.get("pageSize"));
		}

		final int currentPageNo = pageNo;
		final int currentpageSize = pageSize;

		String sql = null;

		if (!keyMap.isEmpty()) {
			StringBuffer sb = new StringBuffer();
			sb.append(realsql);
			sb.append(" where ");

			int keysMapSize = keyMap.size();
			int counter = 0;

			for (String key : keyMap.keySet()) {
				counter++;
				sb.append(key.substring(4));
				sb.append("=");
				String s = (String) keyMap.get(key);
				sb.append(" '" + s + "' ");
				if (counter < keysMapSize) {
					sb.append(" and ");
				}
			}
			sql = sb.toString();
		} else {
			sql = realsql;
		}

		// String sqlTemp = sql;

		final String newsql = sql;

		String targetTable = gcvo.getFormtitle();

		if (targetTable == null || !StringUtils.isNotBlank(targetTable)) {
			throw new Exception("this table is inaccessible.");
		}

		paramsMap.put("selectKeys", selectKeys);

		Page workpage = this.getService().doReturningWork(new ReturningWork<Page>() {
			@Override
			public Page execute(Connection connection) throws SQLException {

				Page page = new Page();

				StringBuffer sb = new StringBuffer();
				sb.append(newsql);

				// 这里将总行数记录为ct
				String sqlForCount = "select count(*) as ct from (" + newsql + ") as t;";
				PreparedStatement preparedStatementForCount = connection.prepareStatement(sqlForCount);
				ResultSet rsForCount = preparedStatementForCount.executeQuery();
				int rowCount = UserDefinedFormUtils.getResultSetSize(rsForCount);
				if (rowCount != -1) {
					sb.append(" limit ");
					sb.append((currentPageNo - 1) * currentpageSize);
					sb.append(",");
					if (rowCount > (currentPageNo * currentpageSize)) {
						sb.append(currentPageNo * currentpageSize);
						UserDefinedFormUtils.setPageInfo(page, currentPageNo, currentpageSize);
					} else {
						sb.append(rowCount);
						UserDefinedFormUtils.setPageInfo(page, currentPageNo, rowCount);
					}
				} else {
					throw new SQLException("Result set is invalid");
				}

				PreparedStatement preparedStatement = connection.prepareStatement(sb.toString());
				ResultSet rs = preparedStatement.executeQuery();

				return UserDefinedFormUtils.getResultSetPage(rs, page, rowCount);
			}
		});

		this.page = workpage;
		return ActionSupport.SUCCESS;
	}

	// 输入自定义报表地址后进行查询 暂时未使用
	public String queryUserDefinedForm() throws Exception {
		paramsMap = ParamsBuilder.buildMapFromHttpRequest();

		String formPK = (String) paramsMap.get("code");

		if (!StringUtils.isNotBlank(formPK)) {
			throw new Exception("报表异常，此报表可能不存在");
		}

		GeneralConfigVO gcvo = (GeneralConfigVO) this.getService().doFindByPK(GeneralConfigVO.class,
				Long.valueOf(formPK).longValue());

		@SuppressWarnings("unchecked")
		List<PreviewConfigVO> pcvo = (List<PreviewConfigVO>) this.getService().doFindByProperty(PreviewConfigVO.class,
				"generalConfig_id", formPK);
		List<FieldNameVO> selectKeys = new ArrayList<FieldNameVO>();

		String targetTable = gcvo.getFormtitle();
		if (targetTable == null || !StringUtils.isNotBlank(targetTable)) {
			throw new Exception("this table is inaccessible.");
		}

		final String realsql = gcvo.getRealsql();
		if (realsql == null || !StringUtils.isNotBlank(realsql)) {
			throw new Exception("sql error");
		}

		// 查询条件配置抽取
		for (PreviewConfigVO previewConfigVO : pcvo) {
			// 字段被选定为查询条件时
			if (previewConfigVO.getIsSelectedAsKey() != null) {
				if (previewConfigVO.getIsSelectedAsKey().equals("T")) {
					if (previewConfigVO.getRename() != null) {
						// 如果此字段被重命名，那么启用重命名
						FieldNameVO fnvo = new FieldNameVO(previewConfigVO.getField(), previewConfigVO.getRename());
						selectKeys.add(fnvo);
					} else if (previewConfigVO.getFieldName() != null) {
						// 如果此字段有注释，那么启用注释
						FieldNameVO fnvo = new FieldNameVO(previewConfigVO.getField(), previewConfigVO.getFieldName());
						selectKeys.add(fnvo);
					} else {
						// 如果没有注释，也没有重命名，默认取字段编码本身为字段名
						FieldNameVO fnvo = new FieldNameVO(previewConfigVO.getField(), previewConfigVO.getField());
						selectKeys.add(fnvo);
					}
				}
			}
		}

		paramsMap.put("selectKeys", selectKeys);

		int pageNo = Constants.DEFAULT_PAGENO;
		int pageSize = Constants.DEFAULT_PAGESIZE;

		if (!(paramsMap.get("pageNo") == null || paramsMap.get("pageSize") == null)) {
			pageNo = Integer.valueOf((String) paramsMap.get("pageNo"));
			pageSize = Integer.valueOf((String) paramsMap.get("pageSize"));
		}

		final int currentPageNo = pageNo;
		final int currentpageSize = pageSize;

		// final String sqlForCount = "select count(*) as numberOfRecords from "
		// + targetTable;

		Page workpage = this.getService().doReturningWork(new ReturningWork<Page>() {
			@Override
			public Page execute(Connection connection) throws SQLException {
				Page page = new Page();
				PreparedStatement preparedStatement = connection.prepareStatement(realsql);
				ResultSet rs = preparedStatement.executeQuery();

				UserDefinedFormUtils.setPageInfo(page, currentPageNo, currentpageSize);

				return UserDefinedFormUtils.getResultSetPage(rs, page);
			}
		});

		// paramsMap.put("page", workpage);
		this.page = workpage;
		return ActionSupport.SUCCESS;
	}

	// 预览自定义报表，此方法暂未使用，前端实现
	public void previewReportForm() {
		paramsMap = ParamsBuilder.buildMapFromHttpRequest();

		String cfgt = (String) paramsMap.get("cfgtable");
		// configMap = this.getConfigData(paramsMap);
		try {
			String p = URLDecoder.decode(cfgt, "UTF-8");
			System.out.println(p);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}

		List<FieldNameVO> keys = new ArrayList<FieldNameVO>();
		List<FieldNameVO> headers = new ArrayList<FieldNameVO>();

		for (PreviewConfigVO ele : configMap.values()) {
			// 将报表需要显示字段提出
			if (ele.getIsSetAsHidden() != null) {
				if (ele.getIsSetAsHidden().equals("T")) {
					if (ele.getRename() != null) {
						FieldNameVO header = new FieldNameVO(ele.getField(), ele.getRename());
						headers.add(header);
					} else {
						FieldNameVO header = new FieldNameVO(ele.getField(), ele.getField());
						headers.add(header);
					}
				}
			}

			if (ele.getIsSelectedAsKey() != null) {
				if (ele.getIsSelectedAsKey().equals("T")) {
					if (ele.getRename() != null) {
						FieldNameVO key = new FieldNameVO(ele.getField(), ele.getRename());
						keys.add(key);
					} else {
						FieldNameVO key = new FieldNameVO(ele.getField(), ele.getField());
						keys.add(key);
					}
				}
			}
		}

		// try {
		// this.sendJsonMessage(JsonUtils.toJson(keys));
		// this.sendJsonMessage(JsonUtils.toJson(headers));
		// } catch (IOException e) {
		// e.printStackTrace();
		// }
	}

	/**
	 * 生成自定义报表时主方法,包含，增加，修改两种状态
	 * 
	 * @throws Exception
	 */
	public void generateReprotForm() throws Exception {
		paramsMap = ParamsBuilder.buildMapFromHttpRequest();

		// T1总配置表 中的id
		Long generalcfgid = Sequence.getSequence();

		String configId = (String) paramsMap.get("configid");
		String formtitle = (String) paramsMap.get("formtitle");
		String formname = (String) paramsMap.get("formname");
		if (formname == null) {
			formname = formtitle;
		}
		String resource = (String) paramsMap.get("resource");
		String sqlstatement = (String) paramsMap.get("sqlstatement");
		String author = (String) paramsMap.get("author");
		String mender = (String) paramsMap.get("mender");
		String des = (String) paramsMap.get("des");
		if (des == null) {
			des = "报表描述";
		}
		String formurl = (String) paramsMap.get("formurl");
		if (formurl == null) {
			formurl = "system/generalconfig/queryUserDefinedForm.action";
		}
		Date creatingtime = null;
		// String modtime = (String) paramsMap.get("modtime");
		Date modtime = null;

		// insert data to main configuration table (T1);
		List<PreviewConfigVO> varFieldConfList = new ArrayList<PreviewConfigVO>();

		String isNew = (String) paramsMap.get("stt");
		GeneralConfigVO generalConfigVO = new GeneralConfigVO();
		UserConfigFormStrategy strategy = new UserConfigFormStrategy(generalConfigVO, varFieldConfList,
				this.getUserVO());

		if (isNew.equals("ADD")) {
			strategy.setOprType(Constants.OPRTYPE_ADD);
			if (author == null) {
				author = this.getUserVO().getUserName();
				mender = author;
			}
			if (creatingtime == null) {
				creatingtime = new Date();
			}

		} else if (isNew.equals("EDIT")) {
			strategy.setOprType(Constants.OPRTYPE_MODIFY);
			mender = this.getUserVO().getUserName();

			String creatingtimeTemp = (String) paramsMap.get("creatingtime");
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			creatingtime = formatter.parse(creatingtimeTemp);

			modtime = new Date();
		}

		// configId is null if status is 'ADD'
		if (isNew.equals("EDIT")) {
			generalConfigVO.setGeneralconfigId(Long.valueOf(configId).longValue());
			// paramsMap.put("generalconfigPK", configId);
			// strategy.setGeneralConfigVOPK(Long.valueOf(configId).longValue());
		} else if (isNew.equals("ADD")) {
			generalConfigVO.setGeneralconfigId(generalcfgid);
		}

		generalConfigVO.setFormtitle(formtitle);
		generalConfigVO.setFormname(formname);
		generalConfigVO.setResource(resource);
		generalConfigVO.setSqlstatement(sqlstatement);
		generalConfigVO.setAuthor(author);
		generalConfigVO.setMender(mender);
		generalConfigVO.setDes(des);

		String url = null;

		if (isNew.equals("EDIT")) {
			url = "system/generalconfig/queryByKey.action" + "?code=" + configId;
		} else if (isNew.equals("ADD")) {
			url = "system/generalconfig/queryByKey.action" + "?code=" + Long.toString(generalcfgid);
		}

		generalConfigVO.setUrl(url);
		generalConfigVO.setCreatingtime(creatingtime);
		generalConfigVO.setModtime(modtime);

		// 获得配置table里面的值
		configMap = this.getConfigData(paramsMap);

		// 存储所有报表要显示字段
		StringBuffer sb = new StringBuffer();

		// 将配置table中的对象存入list, 这里不可用equals判断，因为构造对象时属性并未初始化
		for (PreviewConfigVO ele : configMap.values()) {
			// 将报表需要显示字段提出
			if (ele.getIsSetAsHidden() != null) {
				if (ele.getIsSetAsHidden().equals("T")) {

					if (ele.getRename() != null) {
						sb.append(ele.getField());
						sb.append(" as '");
						sb.append(ele.getRename());
						sb.append("',");
					} else {
						sb.append(ele.getField());
						sb.append(",");
					}
				}
			}

			if (isNew.equals("ADD")) {
				Long generalConfigData_id = Sequence.getSequence();
				ele.setGeneralConfigData_id(generalConfigData_id);
				ele.setGeneralConfig_id(generalcfgid);
			}

			if (isNew.equals("EDIT")) {
				ele.setGeneralConfig_id(Long.valueOf(configId).longValue());
			}

			// T1总配置表 中的id
			ele.setAuthor(author);
			ele.setMender(mender);
			ele.setCreatingtime(creatingtime);
			ele.setModtime(modtime);
			ele.setTableName(formname);

			varFieldConfList.add(ele);
		}

		String realsql = "select " + sb.toString().substring(0, sb.toString().length() - 1) + " from " + formtitle;
		generalConfigVO.setRealsql(realsql);

		// 传入配置table (T2)
		// strategy.setVarFieldConfListPK(previewConfigVOPK);
		strategy.setVarFieldConfList(varFieldConfList);

		// 传入报表表 (T1)
		strategy.setGeneralConfigVO(generalConfigVO);

		try {
			this.getService().doProcess(strategy);
			this.sendJsonMessage("执行成功！");
		} catch (Exception e) {
			this.sendJsonMessage("执行失败，请检查SQL语句，及其他配置");
			e.getStackTrace();
		}
	}

	/**
	 * 从paramsMap中抽取用户配置报表中的配置TABLE的数据,返回配置报表的map
	 * 
	 * @param paramsMap
	 * @return configMap
	 */
	public Map<Integer, PreviewConfigVO> getConfigData(Map<String, Object> paramsMap) {

		configMap = new HashMap<Integer, PreviewConfigVO>();

		Class<?> clazz = PreviewConfigVO.class;

		for (Entry<String, Object> ele : paramsMap.entrySet()) {
			if (ele.getKey().startsWith("cfgtable")) {
				String[] temp = preseConfigTb(ele.getKey());
				if (configMap.containsKey(Integer.valueOf(temp[0]))) {
					String prop = temp[1];
					String setMethodName = "set" + String.valueOf(Character.toUpperCase(prop.charAt(0)))
							+ prop.substring(1);
					Method method = null;
					try {
						if (setMethodName.endsWith("id") || setMethodName.endsWith("Id")) {
							method = clazz.getDeclaredMethod(setMethodName, Long.class);
							method.invoke(configMap.get(Integer.valueOf(temp[0])),
									Long.valueOf((String) ele.getValue()).longValue());
						} else {
							method = clazz.getDeclaredMethod(setMethodName, String.class);
							method.invoke(configMap.get(Integer.valueOf(temp[0])), ele.getValue());
						}
					} catch (SecurityException e) {
						e.printStackTrace();
					} catch (NoSuchMethodException e) {
						e.printStackTrace();
					} catch (IllegalArgumentException e) {
						e.printStackTrace();
					} catch (IllegalAccessException e) {
						e.printStackTrace();
					} catch (InvocationTargetException e) {
						e.printStackTrace();
					}
				} else {
					String prop = temp[1];
					PreviewConfigVO pc = new PreviewConfigVO();
					String setMethodName = "set" + String.valueOf(Character.toUpperCase(prop.charAt(0)))
							+ prop.substring(1);
					Method method = null;
					try {
						if (setMethodName.endsWith("id") || setMethodName.endsWith("Id")) {
							method = clazz.getDeclaredMethod(setMethodName, Long.class);
							method.invoke(pc, Long.valueOf((String) ele.getValue()).longValue());
						} else {
							method = clazz.getDeclaredMethod(setMethodName, String.class);
							method.invoke(pc, ele.getValue());
						}
						try {

						} catch (IllegalArgumentException e) {
							e.printStackTrace();
						}
					} catch (SecurityException e) {
						e.printStackTrace();
					} catch (NoSuchMethodException e) {
						e.printStackTrace();
					} catch (NumberFormatException e) {

						e.printStackTrace();
					} catch (IllegalArgumentException e) {

						e.printStackTrace();
					} catch (IllegalAccessException e) {

						e.printStackTrace();
					} catch (InvocationTargetException e) {

						e.printStackTrace();
					}
					// content.jsp pass a json data(table), the first 5 rows
					// data is unusable
					if (Integer.valueOf(temp[0]) > 5) {
						configMap.put(Integer.valueOf(temp[0]), pc);
					}
				}
			}
		}
		return configMap;
	}

	// 保存配置信息，此方法暂未使用
	public void saveConfigInfo(Map<String, Object> paramsMap) {

		configMap = new HashMap<Integer, PreviewConfigVO>();

		Class<?> clazz = PreviewConfigVO.class;

		for (Entry<String, Object> ele : paramsMap.entrySet()) {
			// System.out.println(ele.getKey() + ele.getValue());
			if (ele.getKey().equals("t[]")) {
				// targetTable = ele.getValue().toString();
				// System.out.println(ele.getKey());
			} else {
				String[] temp = preseConfigTb(ele.getKey());

				if (configMap.containsKey(Integer.valueOf(temp[0]))) {
					String prop = temp[1];
					String setMethodName = "set" + String.valueOf(Character.toUpperCase(prop.charAt(0)))
							+ prop.substring(1);
					try {
						Method method = clazz.getDeclaredMethod(setMethodName, String.class);
						method.invoke(configMap.get(Integer.valueOf(temp[0])), ele.getValue());
					} catch (SecurityException e) {
						e.printStackTrace();
					} catch (NoSuchMethodException e) {
						e.printStackTrace();
					} catch (IllegalArgumentException e) {
						e.printStackTrace();
					} catch (IllegalAccessException e) {
						e.printStackTrace();
					} catch (InvocationTargetException e) {
						e.printStackTrace();
					}
				} else {
					String prop = temp[1];
					PreviewConfigVO pc = new PreviewConfigVO();
					String setMethodName = "set" + String.valueOf(Character.toUpperCase(prop.charAt(0)))
							+ prop.substring(1);
					try {
						Method method = clazz.getDeclaredMethod(setMethodName, String.class);
						try {
							method.invoke(pc, ele.getValue());
						} catch (IllegalArgumentException e) {
							e.printStackTrace();
						} catch (IllegalAccessException e) {
							e.printStackTrace();
						} catch (InvocationTargetException e) {
							e.printStackTrace();
						}
					} catch (SecurityException e) {
						e.printStackTrace();
					} catch (NoSuchMethodException e) {
						e.printStackTrace();
					}
					configMap.put(Integer.valueOf(temp[0]), pc);
				}
			}
		}
	}

	/**
	 * 此方法辅助解析配置TABLE中的内容 例如 : 返回d[cfgrows][15][fieldHidden] 中的 [15,fieldHidden]
	 * 
	 * @param content
	 * @return
	 */
	public String[] preseConfigTb(String content) {

		String[] splits = content.split("\\[");

		StringBuilder builder = new StringBuilder();
		for (String s : splits) {
			builder.append(s);
		}
		String[] temp = builder.toString().split("\\]");
		String[] res = new String[2];
		for (int i = 1; i < temp.length; i++) {
			res[i - 1] = temp[i];
		}
		return res;
	}

	// 原执行sql方法，暂未使用
	public void executeSql() throws IOException {
		Connection conn = null;

		try {
			paramsMap = ParamsBuilder.buildMapFromHttpRequest();
			String dbId = (String) paramsMap.get("dbId");
			String sqlStr = java.net.URLDecoder.decode(paramsMap.get("sql").toString(), "UTF-8");
			if (StringUtils.isBlank(dbId)) {
				throw new Exception("数据源不能为空");
			}

			if (StringUtils.isBlank(sqlStr)) {
				throw new Exception("SQL不能为空");
			}

			String[] sqlArr = sqlStr.split(";");

			DbConfigVO configVO = (DbConfigVO) this.getService().doFindByPK(DbConfigVO.class,
					PkUtils.getPkVO(DbConfigVO.class, new String[] { "dbId" }, dbId));

			conn = DBConnectionUtils.getConnection(configVO.getDriverClass(), configVO.getJdbcUrl(), configVO.getUser(),
					configVO.getPassword());
			conn.setAutoCommit(false);
			for (String sql : sqlArr) {
				System.out.println("SQL:" + sql.toString());
				if (StringUtils.isNotBlank(sql.toString())) {
					PreparedStatement ps = conn.prepareStatement(sql.toString());
					ps.execute();
				}
			}
			conn.commit();
			this.sendJsonMessage(JsonResult.create(ActionSupport.SUCCESS, "执行成功"));

			System.out.println(sqlStr);

		} catch (Exception ex) {
			if (conn != null) {
				try {
					conn.rollback();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			ex.printStackTrace();
			this.sendJsonMessage(JsonResult.create(ActionSupport.ERROR, ex.getMessage()));
		} finally {
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}

	}

	@Override
	public String delete() {
		try {
			paramsMap = ParamsBuilder.buildMapFromHttpRequest();
			String[] pkValues = this.getParamValueByParamsMap(paramsMap, "PK");
			if (null != pkValues && pkValues.length > 0)
				for (int index = 0; index < pkValues.length; index++) {
					Serializable serVO = PkUtils.getPkVO(voClass, pkNameArray, pkValues[index]);
					this.getService().doRemoveByPK(voClass, serVO, userVO);
					// 配置表中对应此配置报表的的相关配置信息列表
					List<?> list = this.getService().doFindByHQL(" from PreviewConfigVO where generalConfig_id = ? ",
							new Object[] { serVO });
					for (Object pcvo : list) {
						this.getService().doRemoveByPK(PreviewConfigVO.class,
								((PreviewConfigVO) pcvo).getGeneralConfigData_id(), userVO);
					}
				}

		} catch (Exception ex) {
			ex.printStackTrace();
			this.addErrorMessage(ex.getMessage());
			return ActionSupport.ERROR;
		}
		return ActionSupport.SUCCESS;
	}

	@Override
	public String edit() {
		try {
			paramsMap = ParamsBuilder.buildMapFromHttpRequest();
			String pkValue = "";
			if (paramsMap.containsKey(Constants.REQUEST_PK)) {
				pkValue = (String) paramsMap.get(Constants.REQUEST_PK);
			}
			if (StringUtils.isNotBlank(pkValue)) {
				entityVO = this.getService().doFindByPK(voClass, PkUtils.getPkVO(voClass, pkNameArray, pkValue));
			}

			// 在数据库中取得对应此报表配置
			@SuppressWarnings("unchecked")
			List<PreviewConfigVO> configList = (List<PreviewConfigVO>) this.getService()
					.doFindByProperty(PreviewConfigVO.class, "generalConfig_id", pkValue);

			paramsMap.put("configTable", configList);

			// this.sendJsonMessage(JsonUtils.toJson(configList));

			this.setTargetObjectToParamsMap(entityVO, paramsMap);
			this.setEdit(true);
			this.setNew(false);
		} catch (Exception ex) {
			ex.printStackTrace();
			this.addErrorMessage(ex.getMessage());
			return ActionSupport.ERROR;
		}
		return ActionSupport.SUCCESS;
	}

	/**
	 * 用户输入sql语句后，开始解析此语句，抽取sql中的所有字段
	 * 
	 * @throws Exception
	 */
	public void checkFields() throws Exception {
		paramsMap = ParamsBuilder.buildMapFromHttpRequest();

		String dbId = (String) paramsMap.get("dbId");

		if (StringUtils.isBlank(dbId)) {
			throw new Exception("数据源不能为空");
		}

		DbConfigVO configVO = (DbConfigVO) this.getService().doFindByPK(DbConfigVO.class,
				PkUtils.getPkVO(DbConfigVO.class, new String[] { "dbId" }, dbId));

		String currentDb = configVO.getDbName();
		String currentTable = (String) paramsMap.get("currentTable");

		List<FieldNameVO> list = new ArrayList<FieldNameVO>();

		String sql = "SELECT" + " `COLUMN_NAME`," + "`COLUMN_COMMENT`" + " FROM `INFORMATION_SCHEMA`.`COLUMNS` "
				+ "WHERE `TABLE_SCHEMA`= '" + currentDb + "' AND `TABLE_NAME`" + "='" + currentTable + "'";

		try {
			list = this.getService().doQueryList(sql, FieldNameVO.class, null);

			// json parameter to front page
			String js = null;

			// convert a list to map for checking the sql is a full select st or
			// not;
			fieldMap = new HashMap<String, String>();
			for (FieldNameVO fieldNameVO : list) {
				fieldMap.put(fieldNameVO.getCOLUMN_NAME(), fieldNameVO.getCOLUMN_COMMENT());
			}

			// get the column's name that user want to display.
			String fieldsString = (String) paramsMap.get("fieldsArray");

			// if the query is not a full(*) query.
			if (!fieldsString.equals("FULLQUERY")) {
				String[] fieldsArray = fieldsString.split(",");
				List<FieldNameVO> definedFieldList = new ArrayList<FieldNameVO>();
				for (String fieldName : fieldsArray) {
					if (fieldMap.containsKey(fieldName)) {
						definedFieldList.add(new FieldNameVO(fieldName, fieldMap.get(fieldName)));
					}
				}
				js = JsonUtils.toJson(definedFieldList);
			} else {
				js = JsonUtils.toJson(list);
			}

			if (js.equals(JsonUtils.EMPTY_JSON_ARRAY)) {
				this.sendJsonMessage(JsonResult.create(ActionSupport.ERROR, "当前表不存在,或表格为空"));
			} else {
				this.sendJsonMessage(js);
			}

		} catch (SQLException ex) {
			// this.addErrorMessage(ex.getMessage());
			this.sendJsonMessage(JsonResult.create(ActionSupport.ERROR, ex.getMessage()));
			ex.printStackTrace();
		} catch (IOException e) {
			// this.addErrorMessage(e.getMessage());
			this.sendJsonMessage(JsonResult.create(ActionSupport.ERROR, e.getMessage()));
			e.printStackTrace();
		}
	}

	/**
	 * 通过用户的sql，生成表与表中字段的树状结构
	 * 
	 * @return
	 */
	public String tbtree() {

		try {
			paramsMap = ParamsBuilder.buildMapFromHttpRequest();
			// String sql = paramsMap.get("sql").toString();
			/*
			 * MenuListVO listVO = new MenuListVO();
			 * this.setParamsMapToTargetObject(paramsMap, listVO);
			 * listVO.setFindAll(true); page =
			 * this.getService().doQuery(voClass, listVO);
			 * 
			 * StringBuffer treeXML = TreeBuilder.buildTreeXML(page.getDatas(),
			 * "menuId", "menuName", "parentMenuId", "seqId", null);
			 */

			// List<TreeNode> list = null;
			//
			// TreeNode root = new TreeNode("0", "SYS_USER", "用户表", "-1");
			//
			// TreeNode column1 = new TreeNode("1", "USER_ID", "USER_ID",
			// "SYS_USER");
			// TreeNode column2 = new TreeNode("2", "USER_NAME", "USER_NAME",
			// "SYS_USER");
			// TreeNode column3 = new TreeNode("3", "SEX", "SEX", "SYS_USER");

			List<FieldNameVO> list = new ArrayList<FieldNameVO>();

			// HibernateDAO hibernateDAO =
			// DAOFactory.getInstance().createHibernateDAO(FieldNameVO.class);
			// SQLQuery sql = hibernateDAO.getSession()
			// .createSQLQuery("SELECT" + " `COLUMN_NAME`" + " FROM
			// `INFORMATION_SCHEMA`.`COLUMNS` "
			// + "WHERE `TABLE_SCHEMA`='usersdb' " + " AND `TABLE_NAME`" +
			// "='employee'");

			String sql = "SELECT" + " `COLUMN_NAME`" + " FROM `INFORMATION_SCHEMA`.`COLUMNS` "
					+ "WHERE `TABLE_SCHEMA`='usersdb' " + " AND `TABLE_NAME`" + "='employee'";

			list = this.getService().doQueryList(sql, FieldNameVO.class, null);

			List<FieldVO> FieldTree = new ArrayList<FieldVO>();

			FieldVO root = new FieldVO("0", "0", "tableName", null);
			FieldTree.add(root);

			for (int i = 0; i < list.size(); i++) {
				FieldVO field = new FieldVO((i + 1) + "", (i + 1) + "", list.get(i).getCOLUMN_NAME(), "0");
				FieldTree.add(field);
			}

			StringBuffer treeXML = TreeBuilder.buildTreeXML(FieldTree, "fieldId", "fieldName", "pFieldId", null, null);

			paramsMap.put("treeXML", treeXML.toString());

		} catch (Exception ex) {
			ex.printStackTrace();
			this.addActionMessage(ex.getMessage());
			return ActionSupport.ERROR;
		}
		return ActionSupport.SUCCESS;

	}

	public List<FieldNameVO> getFieldList() {
		return fieldList;
	}

	public void setFieldList(List<FieldNameVO> fieldList) {
		this.fieldList = fieldList;
	}

}
