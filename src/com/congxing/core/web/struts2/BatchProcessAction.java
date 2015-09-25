package com.congxing.core.web.struts2;

import org.apache.struts2.ServletActionContext;

import com.congxing.core.batch.BaseBatchTaskBean;
import com.congxing.core.utils.ParamsBuilder;
import com.congxing.core.web.constant.Constants;
import com.congxing.system.user.model.UserVO;
import com.opensymphony.xwork2.ActionContext;

public class BatchProcessAction extends BaseBatchAction {

	private static final long serialVersionUID = 5780132890243115252L;

	public final static String TASK_BEAN_NAME = "taskBeanName";

	private String batchBeanName;

	public String getBatchBeanName() {
		return batchBeanName;
	}

	public void setBatchBeanName(String batchBeanName) {
		this.batchBeanName = batchBeanName;
	}

	public String execute() throws Exception {

		// 获取批处理类的类全名
		String batchBeanClassName = getBatchBeanClassName();

		// 获取批处理类
		BaseBatchTaskBean task = (BaseBatchTaskBean) Class.forName(
				batchBeanClassName).newInstance();

		// 优先从参数获取task对象保留名
		String taskBeanName = (String) ActionContext.getContext()
				.getParameters().get(TASK_BEAN_NAME);

		if (taskBeanName == null || taskBeanName.trim().length() < 1) {

			taskBeanName = task.getTaskname();

			if (taskBeanName == null || taskBeanName.trim().length() < 1) {
				taskBeanName = batchBeanClassName;
			}

		}

		paramsMap = ParamsBuilder.buildMapFromHttpRequest();

		this.setParamsMapToTargetObject(paramsMap, batchFileInfo);

		// 为task预置需要的属性值，也可以是task中需要预先处理的方法
		preTaskSet(task);

		// 计算处理总数
		task.setCountRecord();

		// 开始批量处理
		new Thread(task).start();

		// 将task对象的引用保存在session中，方便页面直接调用
		ActionContext.getContext().getSession().put(taskBeanName, task);

		// 为与缺省status.jsp配合，需保存批处理类的保留名称以供后用
		ServletActionContext.getRequest().setAttribute(TASK_BEAN_NAME,
				taskBeanName);

		return SUCCESS;
	}

	/**
	 * 子类拓展点，用于为批处理类设置额外属性，
	 *
	 * 也可以用于执行批处理类中需要预处理的方法
	 *
	 * @param task
	 *
	 * @since 2010-01-18
	 * @author Lai Weilong
	 */
	protected void preTaskSet(BaseBatchTaskBean task) throws Exception {
		UserVO user = (UserVO) ActionContext.getContext().getSession().get(Constants.SESSION_USER);
		task.setUser(user);
		task.setRunning(true);
		task.setFilename(batchFileInfo.getSaveAsFullPath());
		task.setQueryParam(prepareBatchParam());
		
		String fileName = task.getFilename().toLowerCase();
		if(fileName.endsWith(".xls")){
			task.setTxtFile(false);
		}else if(fileName.endsWith(".xlsx")){
			task.setTxtFile(false);
		}else if(fileName.endsWith(".txt")){
			task.setTxtFile(true);
		}else{
			throw new Exception("文件格式错误");
		}
		
	}

	protected String getBatchBeanClassName() {
		return this.batchBeanName;
	}
}
