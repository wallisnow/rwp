package com.congxing.system.user.view;

import java.util.List;

import jxl.Cell;

import com.congxing.core.batch.BaseBatchTaskBean;
import com.congxing.core.excel.ExcelRowUtils;
import com.congxing.core.excel.config.ColumnModel;
import com.congxing.core.excel.config.ExcelModelBuilder;
import com.congxing.core.excel.config.RowModel;
import com.congxing.core.model.CommonResultObject;
import com.congxing.core.service.CommonService;
import com.congxing.core.utils.MD5Encrypt;
import com.congxing.core.utils.SpringContextManager;
import com.congxing.core.web.constant.Constants;
import com.congxing.system.user.dao.UserStrategy;
import com.congxing.system.user.model.UserVO;

@SuppressWarnings("serial")
public class UserBatchBean extends BaseBatchTaskBean {

	private CommonService service;
	private RowModel rowModel;
	private ExcelRowUtils excelRowUtils;
	
	private static String defaultPassword = "123456";

	public UserBatchBean() {
		this.service = SpringContextManager.getBean("commonServiceImpl");
		try{
			rowModel = ExcelModelBuilder.getInstance().buildRowModel(UserVO.class.getName());
			excelRowUtils = new ExcelRowUtils(rowModel);
		}catch(Exception ex){
		}
	}

	@Override
	protected String getTitle() {
		StringBuffer buffer = new StringBuffer();
		List<ColumnModel> columnList = rowModel.getColumnList();
		buffer.append("序号").append(OUTPUT_FIELD_SEPERATOR);
		for (ColumnModel colModel : columnList) {
			buffer.append(colModel.getTitleName());
			buffer.append(OUTPUT_FIELD_SEPERATOR);
		}
		buffer.append("备注");
		return buffer.toString();
	}

	@Override
	protected String makeStr(CommonResultObject resultVO, Object line, int i) {
		StringBuffer resultStr = new StringBuffer();
		List<ColumnModel> columnList = rowModel.getColumnList();
		resultStr.append(i).append(OUTPUT_FIELD_SEPERATOR);
		Cell []cells = (Cell[])line;
		for (ColumnModel columnModel : columnList) {
			resultStr.append(excelRowUtils.getCellContent(cells, columnModel));
			resultStr.append(OUTPUT_FIELD_SEPERATOR);
		}
		resultStr.append(resultVO.getResultString());
		return resultStr.toString();
	}

	@Override
	protected CommonResultObject updateOneRecord(Object line) {
		UserVO userVO = new UserVO();
		try {
			if(null == rowModel){
				throw new Exception("RowModel is null");
			}
			excelRowUtils.copy(userVO, line);
			userVO.setStatus(Constants.TYPE_YES);
			userVO.setUserType(Constants.USER_TYPE_LOCAL);
			userVO.setPassword(MD5Encrypt.getMD5Password(userVO.getPassword()));
			
			if("用户登录号".equals(userVO.getUserId())){
				return new CommonResultObject(CommonResultObject.FAIL, "失败：标题行" , line);
			}
			
			UserStrategy strategy = new UserStrategy(userVO, this.getUser());
			
			this.getService().doProcess(strategy);
		} catch (Exception e) {
			return new CommonResultObject(CommonResultObject.FAIL, "失败：" + e.getMessage(), line);
		}
		return new CommonResultObject(CommonResultObject.SUCCESS, "成功", line);
	}

	public CommonService getService() {
		return service;
	}

	public void setService(CommonService service) {
		this.service = service;
	}

	public RowModel getRowModel() {
		return rowModel;
	}

	public void setRowModel(RowModel rowModel) {
		this.rowModel = rowModel;
	}

	public ExcelRowUtils getExcelRowUtils() {
		return excelRowUtils;
	}

	public void setExcelRowUtils(ExcelRowUtils excelRowUtils) {
		this.excelRowUtils = excelRowUtils;
	}
	
	public static void main(String []args){
		System.out.println(MD5Encrypt.getMD5Password(defaultPassword));
	}
	
}
