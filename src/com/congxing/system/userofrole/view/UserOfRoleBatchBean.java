package com.congxing.system.userofrole.view;

import java.util.List;

import jxl.Cell;

import com.congxing.core.batch.BaseBatchTaskBean;
import com.congxing.core.excel.ExcelRowUtils;
import com.congxing.core.excel.config.ColumnModel;
import com.congxing.core.excel.config.ExcelModelBuilder;
import com.congxing.core.excel.config.RowModel;
import com.congxing.core.model.CommonResultObject;
import com.congxing.core.service.CommonService;
import com.congxing.core.utils.SpringContextManager;
import com.congxing.core.web.constant.Constants;
import com.congxing.core.web.sequence.Sequence;
import com.congxing.system.userofrole.dao.UserOfRoleStrategy;
import com.congxing.system.userofrole.model.UserOfRoleVO;

@SuppressWarnings("serial")
public class UserOfRoleBatchBean extends BaseBatchTaskBean{

	private CommonService service;
	private RowModel rowModel;
	private ExcelRowUtils excelRowUtils;

	public UserOfRoleBatchBean() {
		this.service = SpringContextManager.getBean("commonServiceImpl");
		try{
			rowModel = ExcelModelBuilder.getInstance().buildRowModel(UserOfRoleVO.class.getName());
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
		UserOfRoleVO urVO = new UserOfRoleVO();
		try {
			if(null == rowModel){
				throw new Exception("RowModel is null");
			}
			excelRowUtils.copy(urVO, line);
			urVO.setUrId(Sequence.getSequence());
			urVO.setStatus(Constants.TYPE_TRUE);
			
			UserOfRoleStrategy strategy = new UserOfRoleStrategy(urVO, this.getUser());
			
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

}
