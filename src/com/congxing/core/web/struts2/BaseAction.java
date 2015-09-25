package com.congxing.core.web.struts2;

import java.io.IOException;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.congxing.core.hibernate.Page;
import com.congxing.core.service.CommonService;
import com.congxing.core.utils.ConvertUtils;
import com.congxing.core.utils.DateFormatFactory;
import com.congxing.core.utils.JsonUtils;
import com.congxing.core.utils.ParamsBuilder;
import com.congxing.core.utils.PkUtils;
import com.congxing.core.utils.ReflectionUtils;
import com.congxing.core.utils.Struts2Utils;
import com.congxing.core.web.constant.Constants;
import com.congxing.system.businesslog.model.BusinessLogVO;
import com.congxing.system.user.model.UserVO;
import com.opensymphony.xwork2.ActionSupport;

/**
 * 
 * <p>
 * 
 * @Description: Action基类
 *               </p>
 *
 *               <p>
 * @author: LIUKANGJIN
 *          </p>
 *
 *          <p>
 * @date: 2013-12-18
 *        <p>
 *
 *        <p>
 * @version: V.1.0
 *           <p>
 *
 */
@SuppressWarnings("serial")
public abstract class BaseAction extends ActionSupport {

	private CommonService service;

	protected Page page;

	protected Object entityVO;

	protected Object entityListVO;

	protected String[] pkNameArray;

	protected Class<?> voClass;

	protected UserVO userVO;

	public Map<String, Object> paramsMap;

	/**
	 * <p>
	 * Title: BaseAction
	 * </p>
	 * <p>
	 * Description: Action基类构造函数
	 * </p>
	 */
	public BaseAction() {
		/**
		 * 需要初始化voClass和pkNameArray
		 */
		userVO = (UserVO) Struts2Utils.getSessionAttribute(Constants.SESSION_USER);
	}

	protected CommonService getService() {
		return service;
	}

	@Autowired
	@Qualifier("commonServiceImpl")
	protected void setService(CommonService service) {
		this.service = service;
	}

	/**
	 * @Title: show
	 * @Description: 进入查询列表页面
	 * @return String
	 */
	public String show() {
		try {
			paramsMap = ParamsBuilder.buildMapFromHttpRequest();
			this.setParamsMapToTargetObject(paramsMap, this.getEntityListVO());
			this.setShowEntityListVO(this.getEntityListVO(), paramsMap, userVO);
			this.setTargetObjectToParamsMap(this.getEntityListVO(), paramsMap);
		} catch (Exception ex) {
			this.addErrorMessage(ex.getMessage());
			ex.printStackTrace();
			return ActionSupport.ERROR;
		}
		return ActionSupport.SUCCESS;
	}

	/**
	 * @Title: list
	 * @Description: 查询列表信息
	 * @return String
	 */
	public String list() {
		try {
			paramsMap = ParamsBuilder.buildMapFromHttpRequest();
			this.setParamsMapToTargetObject(paramsMap, this.getEntityListVO());
			this.setListEntityListVO(this.getEntityListVO(), paramsMap, userVO);
			page = this.getService().doQuery(voClass, this.getEntityListVO());
		} catch (Exception ex) {
			ex.printStackTrace();
			this.addErrorMessage(ex.getMessage());
			return ActionSupport.ERROR;
		}
		return ActionSupport.SUCCESS;
	}

	/**
	 * @Title: listJson
	 * @Description: 查询列表信息
	 * @return void
	 * @throws IOException
	 */
	public void listJson() throws IOException {
		try {
			paramsMap = ParamsBuilder.buildMapFromHttpRequest();
			this.setParamsMapToTargetObject(paramsMap, this.getEntityListVO());
			this.setListEntityListVO(this.getEntityListVO(), paramsMap, userVO);
			page = this.getService().doQuery(voClass, this.getEntityListVO());
			this.sendJsonMessage(JsonResult.create(ActionSupport.SUCCESS, JsonUtils.toJson(page)));
		} catch (Exception ex) {
			ex.printStackTrace();
			this.sendJsonMessage(JsonResult.create(ActionSupport.ERROR, ex.getMessage()));
		}
	}

	/**
	 * @Title: add
	 * @Description: 新增操作
	 * @return String
	 */
	public String add() {
		try {
			paramsMap = ParamsBuilder.buildMapFromHttpRequest();
			this.setParamsMapToTargetObject(paramsMap, this.getEntityVO());
			this.setAddEntityVO(this.getEntityVO(), paramsMap, userVO);
			this.setTargetObjectToParamsMap(this.getEntityVO(), paramsMap);
			this.setEdit(true);
			this.setNew(true);
		} catch (Exception ex) {
			ex.printStackTrace();
			this.addErrorMessage(ex.getMessage());
			return ActionSupport.ERROR;
		}
		return ActionSupport.SUCCESS;
	}

	/**
	 * @Title: save
	 * @Description: 保存操作
	 * @return String
	 */
	public String save() {
		try {
			paramsMap = ParamsBuilder.buildMapFromHttpRequest();
			String isNew = (String) paramsMap.get(Constants.PAGE_ISNEW);
			this.setParamsMapToTargetObject(paramsMap, this.getEntityVO());
			if (Constants.STRING_TRUE.equals(isNew)) {
				this.setSaveEntityVO(this.getEntityVO(), paramsMap, userVO);
				if (this.getEntityVO() instanceof Business) {
					BusinessLogVO logVO = BusinessLogVO.create(userVO, Constants.OPRTYPE_ADD,
							(Business) this.getEntityVO());
					entityVO = this.getService().doCreate(voClass, this.getEntityVO(), BusinessLogVO.class, logVO);
				} else {
					entityVO = this.getService().doCreate(voClass, this.getEntityVO());
				}
				this.addInfoMessage("保存成功");
			} else {
				this.setUpdateEntityVO(this.getEntityVO(), paramsMap, userVO);
				if (this.getEntityVO() instanceof Business) {
					BusinessLogVO logVO = BusinessLogVO.create(userVO, Constants.OPRTYPE_MODIFY,
							(Business) this.getEntityVO());
					entityVO = this.getService().doUpdate(voClass, this.getEntityVO(), BusinessLogVO.class, logVO);
				} else {
					entityVO = this.getService().doUpdate(voClass, this.getEntityVO());
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

	/**
	 * @Title: save
	 * @Description: 保存操作
	 * @return void
	 * @throws IOException
	 */
	public void saveJson() throws IOException {
		try {
			paramsMap = ParamsBuilder.buildMapFromHttpRequest();
			String isNew = (String) paramsMap.get(Constants.PAGE_ISNEW);
			this.setParamsMapToTargetObject(paramsMap, this.getEntityVO());
			if (Constants.STRING_TRUE.equals(isNew)) {
				this.setSaveEntityVO(this.getEntityVO(), paramsMap, userVO);
				if (this.getEntityVO() instanceof Business) {
					BusinessLogVO logVO = BusinessLogVO.create(userVO, Constants.OPRTYPE_ADD,
							(Business) this.getEntityVO());
					entityVO = this.getService().doCreate(voClass, this.getEntityVO(), BusinessLogVO.class, logVO);
				} else {
					entityVO = this.getService().doCreate(voClass, this.getEntityVO());
				}
				this.sendJsonMessage(JsonResult.create(ActionSupport.SUCCESS, "保存成功"));
			} else {
				this.setUpdateEntityVO(this.getEntityVO(), paramsMap, userVO);
				if (this.getEntityVO() instanceof Business) {
					BusinessLogVO logVO = BusinessLogVO.create(userVO, Constants.OPRTYPE_MODIFY,
							(Business) this.getEntityVO());
					entityVO = this.getService().doUpdate(voClass, this.getEntityVO(), BusinessLogVO.class, logVO);
				} else {
					entityVO = this.getService().doUpdate(voClass, this.getEntityVO());
				}
				this.sendJsonMessage(JsonResult.create(ActionSupport.SUCCESS, "修改成功"));
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			this.sendJsonMessage(JsonResult.create(ActionSupport.ERROR, ("操作失败,失败原因:" + ex.getMessage())));
		}
	}

	/**
	 * @Title: edit
	 * @Description: 编辑操作
	 * @return String
	 */
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
	 * @Title: view
	 * @Description: 查看操作
	 * @return String
	 */
	public String view() {
		try {
			paramsMap = ParamsBuilder.buildMapFromHttpRequest();
			String pkValue = "";
			if (paramsMap.containsKey(Constants.REQUEST_PK)) {
				pkValue = (String) paramsMap.get(Constants.REQUEST_PK);
			}
			if (StringUtils.isNotBlank(pkValue)) {
				entityVO = this.getService().doFindByPK(voClass, PkUtils.getPkVO(voClass, pkNameArray, pkValue));
			}
			this.setTargetObjectToParamsMap(entityVO, paramsMap);
			this.setEdit(false);
			this.setNew(false);
		} catch (Exception ex) {
			ex.printStackTrace();
			this.addErrorMessage(ex.getMessage());
			return ActionSupport.ERROR;
		}
		return ActionSupport.SUCCESS;
	}

	/**
	 * @Title: delete
	 * @Description: 删除操作
	 * @return String
	 */
	public String delete() {
		try {
			paramsMap = ParamsBuilder.buildMapFromHttpRequest();
			String[] pkValues = this.getParamValueByParamsMap(paramsMap, "PK");
			if (null != pkValues && pkValues.length > 0)
				for (int index = 0; index < pkValues.length; index++) {
					Serializable serVO = PkUtils.getPkVO(voClass, pkNameArray, pkValues[index]);
					Object entityVO = this.getService().doFindByPK(voClass, serVO);
					if (entityVO instanceof Business) {
						BusinessLogVO logVO = BusinessLogVO.create(userVO, Constants.OPRTYPE_DELETE,
								(Business) entityVO);
						this.getService().doRemoveByPK(voClass, serVO, BusinessLogVO.class, logVO);
					} else {
						this.getService().doRemoveByPK(voClass, serVO);
					}
				}
		} catch (Exception ex) {
			ex.printStackTrace();
			this.addErrorMessage(ex.getMessage());
			return ActionSupport.ERROR;
		}
		return ActionSupport.SUCCESS;
	}

	/**
	 * @Title: deleteJson
	 * @Description: 异步删除操作
	 * @return void
	 * @throws IOException
	 */
	public void deleteJson() throws IOException {
		try {
			paramsMap = ParamsBuilder.buildMapFromHttpRequest();
			String[] pkValues = this.getParamValueByParamsMap(paramsMap, "PK");
			if (null != pkValues && pkValues.length > 0)
				for (int index = 0; index < pkValues.length; index++) {
					Serializable serVO = PkUtils.getPkVO(voClass, pkNameArray, pkValues[index]);
					Object entityVO = this.getService().doFindByPK(voClass, serVO);
					if (entityVO instanceof Business) {
						BusinessLogVO logVO = BusinessLogVO.create(userVO, Constants.OPRTYPE_DELETE,
								(Business) entityVO);
						this.getService().doRemoveByPK(voClass, serVO, BusinessLogVO.class, logVO);
					} else {
						this.getService().doRemoveByPK(voClass, serVO);
					}
				}
			this.sendJsonMessage(JsonResult.create(ActionSupport.SUCCESS, "删除记录成功"));
		} catch (Exception ex) {
			ex.printStackTrace();
			this.sendJsonMessage(JsonResult.create(ActionSupport.ERROR, "删除记录失败,失败原因:" + ex.getMessage()));
		}
	}

	/**
	 * @Title: select
	 * @Description: 选择操作
	 * @return String
	 */
	public String select() {
		return this.list();
	}

	/**
	 * @Title: select
	 * @Description: 导出TXT文件操作
	 * @return String
	 */
	public String txt() {
		return ActionSupport.SUCCESS;
	}

	/**
	 * @Title: select
	 * @Description: 导出EXCEL文件操作
	 * @return String
	 */
	public String excel() {
		try {
			paramsMap = ParamsBuilder.buildMapFromHttpRequest();
			this.setParamsMapToTargetObject(paramsMap, this.getEntityListVO());
			this.setListEntityListVO(entityListVO, paramsMap, userVO);
			((BaseListVO) this.getEntityListVO()).setPageSize(Constants.DEFAULT_MAX_EXCEL);
			((BaseListVO) this.getEntityListVO()).setPageNo(Constants.DEFAULT_PAGENO);
			page = this.getService().doQuery(voClass, this.getEntityListVO());
		} catch (Exception ex) {
			ex.printStackTrace();
			this.addErrorMessage(ex.getMessage());
			return ActionSupport.ERROR;
		}
		return ActionSupport.SUCCESS;
	}

	/**
	 * @Title: setAddEntityVO
	 * @Description: 用于doAdd方法，在新建记录前，为EntityVO设置某些恰当的初值
	 * @param entityVO
	 * @param paramsMap
	 * @param userVO
	 * @throws Exception
	 */
	protected void setAddEntityVO(Object entityVO, Map<String, Object> paramsMap, UserVO userVO) throws Exception {
	}

	/**
	 * @Title: setSaveEntityVO
	 * @Description: 用于doSave方法，格式化处理VO的值，以保存到数据库，VO已初始页面输入值
	 * @param entityVO
	 * @param paramsMap
	 * @param userVO
	 * @throws Exception
	 */
	protected void setSaveEntityVO(Object entityVO, Map<String, Object> paramsMap, UserVO userVO) throws Exception {
	}

	/**
	 * @Title: setUpdateEntityVO
	 * @Description: 用于doSave方法，格式化处理VO的值，以保存到数据库，VO已初始页面输入值
	 * @param entityVO
	 * @param paramsMap
	 * @param userVO
	 * @throws Exception
	 */
	protected void setUpdateEntityVO(Object entityVO, Map<String, Object> paramsMap, UserVO userVO) throws Exception {
	}

	/**
	 * @Title: setShowEntityListVO
	 * @Description: 用于doShow方法，在进入list页面之前预设ListVO中查询条件的初值
	 * @param entityListVO
	 * @param paramsMap
	 * @param userVO
	 * @throws Exception
	 */
	protected void setShowEntityListVO(Object entityListVO, Map<String, Object> paramsMap, UserVO userVO) {
	}

	/**
	 * @Title: setListEntityListVO
	 * @Description: 用于doList、doTxt和doExcel方法，在进入list页面之前预设ListVO中查询条件的初值
	 * @param entityListVO
	 * @param paramsMap
	 * @param userVO
	 * @throws Exception
	 */
	protected void setListEntityListVO(Object entityListVO, Map<String, Object> paramsMap, UserVO userVO) {
	}

	/**
	 * @Title: getEntityListVO
	 * @Description: 猜测方式猎取ListVO对象
	 * @return
	 */
	protected Object getEntityListVO() {
		if (null != entityListVO) {
			return entityListVO;
		}
		String className = voClass.getName();
		String listVOPreFix = StringUtils.substringBeforeLast(className, "VO").replaceAll(".model.", ".view.");
		String listVOClassName = listVOPreFix + "ListVO";
		try {
			entityListVO = Class.forName(listVOClassName).newInstance();
		} catch (Exception ex) {
		}

		if (null == entityListVO) {
			listVOClassName = StringUtils.substringBeforeLast(className, "VO") + "ListVO";
			try {
				entityListVO = Class.forName(listVOClassName).newInstance();
			} catch (Exception ex) {
			}
		}
		return entityListVO;
	}

	/**
	 * @Title: getEntityListVO
	 * @Description: 按照初始化地voClass对象生成VO对象
	 * @return
	 */
	protected Object getEntityVO() throws Exception {
		try {
			if (null == entityVO) {
				entityVO = voClass.newInstance();
			}
		} catch (Exception ex) {
			throw new Exception(" voClass not fefinition or invalidate !");
		}
		return entityVO;
	}

	/**
	 * 根据目标对象设置ParamsMap值 说明：在设置过程中将目标对象值全部转为String类型保存至ParamsMap中 复杂类型转换说明：
	 * Date类型 yyyy-MM-dd HH:mm:ss 该方法不适用于对象中还有复杂实体对象
	 * 
	 * @param targetObject
	 * @param paramsMap
	 * @param Exception
	 * @return void
	 */
	protected void setTargetObjectToParamsMap(Object targetObject, Map<String, Object> paramsMap) throws Exception {
		if (null == targetObject)
			return;
		Field[] fields = ReflectionUtils.getDeclaredField(targetObject);
		for (Field field : fields) {
			String fieldName = field.getName();
			Class<?> type = field.getType();
			Object value = ReflectionUtils.getFieldValue(targetObject, fieldName);
			if (null == value)
				continue;
			if (java.util.Date.class.isAssignableFrom(type)) {
				paramsMap.put(fieldName, DateFormatFactory.getDefaultFormat().format(value));
			} else if (value instanceof java.util.Collection<?>) {
				paramsMap.put(fieldName, value);
			} else {
				paramsMap.put(fieldName, String.valueOf(value));
			}
		}
	}

	/**
	 * 根据ParamsMap设置对象值 @param paramsMap 数据源 @param targetObject 目标对象 @param
	 * Exception @return void @throws
	 */
	protected void setParamsMapToTargetObject(Map<String, Object> paramsMap, Object targetObject) throws Exception {
		Map<?, ?> props = BeanUtils.describe(targetObject);
		for (Iterator<?> iter = props.keySet().iterator(); iter.hasNext();) {
			String propertyName = (String) iter.next();
			if (paramsMap.containsKey(propertyName)) {
				String[] propertyValues = this.getParamValueByParamsMap(paramsMap, propertyName);
				Field field = ReflectionUtils.getDeclaredField(targetObject, propertyName);
				Class<?> type = field.getType();
				if (type.isArray()) {
					/**
					 * 数组类型, 先获取数组类型的具体类型
					 */
					Class<?> elementType = type.getComponentType();
					/**
					 * 在对应数组的情况下 如果页面单选时,此时传值非数组,而是字符串 如果页面为多选时,此时传值才为数组
					 */
					Object[] objValues = new Object[propertyValues.length];
					for (int i = 0; i < propertyValues.length; i++) {
						objValues[i] = ConvertUtils.convert(propertyValues[i], elementType);
					}
					BeanUtils.setProperty(targetObject, propertyName, objValues);
				} else {
					/**
					 * 非数组类型,直接取第一个值
					 */
					BeanUtils.setProperty(targetObject, propertyName, ConvertUtils.convert(propertyValues[0], type));
				}
			}
		}
	}

	/**
	 * 只负责取值,不负责判断paramsMap是否存在propertyName 另外说明：该方法返回为数组类型 @param @param
	 * paramsMap @param @param propertyName @param @return @param @throws
	 * Exception @return String[] @throws
	 */
	protected String[] getParamValueByParamsMap(Map<String, Object> paramsMap, String propertyName) throws Exception {
		Object values = paramsMap.get(propertyName);
		if (null == values)
			return new String[] {};
		if (values instanceof java.lang.String[]) {
			return (String[]) values;
		} else {
			return new String[] { (String) values };
		}
	}

	public Map<String, Object> getParamsMap() {
		return paramsMap;
	}

	public void setParamsMap(Map<String, Object> paramsMap) {
		this.paramsMap = paramsMap;
	}

	public Page getPage() {
		return page;
	}

	public void setPage(Page page) {
		this.page = page;
	}

	/**
	 * Action 错误信息 @param @param errorMessage @return void @throws
	 */
	@SuppressWarnings("unchecked")
	protected void addErrorMessage(String errorMessage) {
		if (StringUtils.isNotBlank(errorMessage)) {
			List<String> msgList = (List<String>) Struts2Utils.getRequest().getAttribute(Constants.ERROR_MESSAGE);
			if (null != msgList && msgList.size() > 0) {
				msgList.add(errorMessage);
			} else {
				msgList = new ArrayList<String>();
				msgList.add(errorMessage);
				Struts2Utils.getRequest().setAttribute(Constants.ERROR_MESSAGE, msgList);
			}
		}
	}

	/**
	 * Action 操作信息 @param @param successMessage @return void @throws
	 */
	@SuppressWarnings("unchecked")
	protected void addInfoMessage(String successMessage) {
		if (StringUtils.isNotBlank(successMessage)) {
			List<String> msgList = (List<String>) Struts2Utils.getRequest().getAttribute(Constants.DEFAULT_MESSAGE);
			if (null != msgList && msgList.size() > 0) {
				msgList.add(successMessage);
			} else {
				msgList = new ArrayList<String>();
				msgList.add(successMessage);
				Struts2Utils.getRequest().setAttribute(Constants.DEFAULT_MESSAGE, msgList);
			}
		}
	}

	public UserVO getUserVO() {
		return userVO;
	}

	public void setUserVO(UserVO userVO) {
		this.userVO = userVO;
	}

	public void setEntityVO(Object entityVO) {
		this.entityVO = entityVO;
	}

	/**
	 * 记录新增操作属性
	 * 
	 * @param isNew
	 */
	public void setNew(boolean isNew) {
		if (paramsMap == null) {
			paramsMap = new HashMap<String, Object>();
		}
		if (isNew) {
			paramsMap.put(Constants.PAGE_ISNEW, Constants.STRING_TRUE);
		} else {
			paramsMap.put(Constants.PAGE_ISNEW, Constants.STRING_FALSE);
		}
	}

	/**
	 * 记录可编辑操作属性
	 * 
	 * @param editAble
	 */
	public void setEdit(boolean isEdit) {
		if (paramsMap == null) {
			paramsMap = new HashMap<String, Object>();
		}
		if (isEdit) {
			paramsMap.put(Constants.PAGE_ISEDIT, Constants.STRING_TRUE);
		} else {
			paramsMap.put(Constants.PAGE_ISEDIT, Constants.STRING_FALSE);
		}
	}

	/**
	 * 向页面输出JSON格式数据
	 * 
	 * @param jsonMessage
	 * @throws IOException
	 */
	protected void sendJsonMessage(String jsonMessage) throws IOException {
		HttpServletResponse response = Struts2Utils.getResponse();
		response.setCharacterEncoding(Constants.CHARACTER_ENCODING_UTF_8);
		response.getWriter().write(jsonMessage);
	}

	/**
	 * 向页面输出JSON格式数据
	 * 
	 * @param jsonMessage
	 * @throws IOException
	 */
	protected void sendJsonMessage(JsonResult result) throws IOException {
		this.sendJsonMessage(result.toJson());
	}

}
