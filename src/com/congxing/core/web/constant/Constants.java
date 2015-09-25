package com.congxing.core.web.constant;

public class Constants {
	
	/** 角色ID起始编码 */
	public static final String ROLE_ID_START = "10000";
	
	/** 系统菜单根节点 */
	public static final String ROOT_MENU_ID = "00";
	
	/** 我的收藏菜单ID */
	public static final String ROOT_FAVORITIES_MENU_ID = "0099";
	
	/** 我的收藏菜单名称 */
	public static final String ROOT_FAVORITIES_MENU_NAME = "我的收藏";
	
	/** 系统管理员角色 */
	public static final String SYSTEM_MANAGER_ROLE_ID = "10000";
	
	/** 系统编码格式 */
	public final static String CHARACTER_ENCODING_UTF_8 = "UTF-8";
	
	/** Session保存用户对象名称 */
	public final static String SESSION_USER = "USER";
	
	/** 权限检查出错前的用户预期访问页面 */
	public final static String SESSION_EXPECTURL = "ExpectUrl";
	
	/** 用户定制首页 */
	public final static String SESSION_INDEXPAGE = "IndexPage";
	
	/** 默认第一页 */
	public final static int DEFAULT_PAGENO = 1;
	
	/** 默认的每页记录数: 20条 */
	public final static int DEFAULT_PAGESIZE = 20;
	
	/** EXCEL最大导出行数 */
	public final static int DEFAULT_MAX_EXCEL = 65000;
	
	/** 页面属于前缀 */
	public final static String PROPERTY_PREFIX = "_";
	
	/** 对象请求主键名 */
	public final static String REQUEST_PK = "PK";
	
	/** 是否新增页面标识 */
	public final static String PAGE_ISNEW = "ISNEW";
	
	/** 是否可编辑页面标识 */
	public final static String PAGE_ISEDIT = "ISEDIT";
	
	/** 字符串：真 */
	public final static String STRING_TRUE = "TRUE";
	
	/** 字符串：假 */
	public final static String STRING_FALSE = "FALSE";
	
	/** 操作类型：增加 */
	public final static String OPRTYPE_ADD = "A";//增加
	
	/** 操作类型：修改 */
	public final static String OPRTYPE_MODIFY = "M";//修改
	
	/** 操作类型：删除 */
	public final static String OPRTYPE_DELETE = "D";//删除
	
	/** 是否参数：是  */
	public final static String TYPE_YES = "Y";
	
	/** 是否参数：否*/
	public final static String TYPE_NO = "N";
	
	/** 是否参数：是  */
	public final static String TYPE_YES_NUM = "1";
	
	/** 是否参数：否*/
	public final static String TYPE_NO_NUM = "0";
	
	/** 真假参数(用于真、有效情况)：是, 有效 */
	public final static String TYPE_TRUE = "T";
	
	/** 真假参数(用于假、无效情况)：是, 无效 */
	public final static String TYPE_FALSE = "F";
	
	/** 成功失败参数：成功  */
	public final static String TYPE_SUC = "SUC";
	
	/** 成功失败参数：失败*/
	public final static String TYPE_FAIL = "FAIL";
	
	/** 菜单类型： 菜单 */
	public final static String MENU_TYPE_PAGE = "P";
	
	/** 菜单类型：界面控制点 */
	public final static String MENU_TYPE_CONTROL = "C";
	
	/** 错误信息标识 */
	public final static String ERROR_MESSAGE = "errorMessage";//错误信息标识
	
	/** 默认信息标识 */
	public final static String DEFAULT_MESSAGE = "defaultMessage";//默认信息标识
	
	/** 逗号分隔符 */
	public final static String COMMA_SEPARATOR = ",";
	
	/** 会话Cookie名称 */
	public final static String HTTP_COOKIE_NAME = "__LOGIN_PASSPORT__";
	
	/** 会话Cookie域 */
	public final static String HTTP_COOKIE_DOMAIN = "localhost";
	
	/** 用户类型：Portal用户 */
	public final static String USER_TYPE_PORTAL = "P";
	
	/** 用户类型：本地用户 */
	public final static String USER_TYPE_LOCAL = "L";
	
	public final static String SYSTEM_USER_ID = "CXCS01";
	
	/** 规则集状态: 草稿**/
	public final static String RULESET_STATUS_DRAFT = "DRAFT";
	
	/** 规则集状态: 正使用**/
	public final static String RULESET_STATUS_USING = "USING";
	
	/** 规则集状态: 作废**/
	public final static String RULESET_STATUS_REVOKE = "REVOKE";
	
	/** 任务状态: 草稿**/
	public final static String TASK_STATUS_DRAFT = "DRAFT";
	
	/** 任务状态: 待执行**/
	public final static String TASK_STATUS_TODO = "TODO";
	
	/** 任务状态: 执行中**/
	public final static String TASK_STATUS_DOING = "DOING";
	
	/** 任务状态: 已完成**/
	public final static String TASK_STATUS_DONE = "DONE";
	
	/** 参数类型: 常量**/
	public static final String PARAMTYPE_CONSTANT = "CONSTANT";
	
	/** 参数类型: 规则因子**/
	public static final String PARAMTYPE_FACTOR = "FACTOR";
	
	/** 参数类型: 变量**/
	public static final String PARAMTYPE_VARIABLE = "VARIABLE";
	
	public static final String MONITOR_TYPE_LAST = "LAST";
	
	public static final String MONITOR_TYPE_CURRENT = "CUR";
	
	public static final String MONITOR_WAY_MONTH = "EVERYMONTH";
	
	public static final String MONITOR_WAY_DAY = "EVERYDAY";
	
	/** 参数类型: 序列号主键**/
	public static final String PARAMTYPE_ID = "ID";

	public final static String SESSION_ATTRIBUTE_USER = "USER";            // 用户对象
	
	/** 酬金管理员角色ID**/
	public static final String RW_MANAGE_ROLE_ID  = "100011";
	
	/** 市场部酬金角色ID**/
	public static final String RW_MARKET_ROLE_ID  = "100012";
	
	/** 澄海分公司角色ID**/
	public static final String RW_CH_ROLE_ID  = "100013";
	
	/** 潮南分公司角色ID**/
	public static final String RW_CN_ROLE_ID  = "100014";
	
	/** 南澳分公司角色ID**/
	public static final String RW_NA_ROLE_ID  = "100015";
	
	/** 龙湖分公司角色ID**/
	public static final String RW_LH_ROLE_ID  = "100016";
	
	/** 潮阳分公司角色ID**/
	public static final String RW_CY_ROLE_ID  = "100017";
	
	/** 金平分公司角色ID**/
	public static final String RW_JP_ROLE_ID  = "100018";
	
	/** 渠道经理角色ID**/
	public static final String RW_DEPT_MANAGER_ROLE_ID  = "100023";
}
