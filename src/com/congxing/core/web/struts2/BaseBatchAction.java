/**
 *
 */
package com.congxing.core.web.struts2;

import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.congxing.core.utils.ParamsBuilder;


/**
 * <p>Title: BaseBatchAction</p>
 *
 * <p>Description: BaseBatchAction</p>
 *
 * <p>Copyright: Copyright (c) Revenco</p>
 *
 * <p>Company: Sunrise Tech Ltd.</p>
 *
 * @author Lai Weilong
 *
 * @version 1.0
 *
 * @since 2011-10-21
 */
public class BaseBatchAction extends BaseAction {

	private static final long serialVersionUID = -4695141805844541799L;

	protected BatchFileInfo batchFileInfo = new BatchFileInfo();

	private BatchParam batchParam;

	private String batchParamClassName;

	public BaseBatchAction() {
	}

	public String getBatchParamClassName() {
		return batchParamClassName;
	}

	public void setBatchParamClassName(String listVOClassName) {
		this.batchParamClassName = listVOClassName;
	}

	public BatchParam prepareBatchParam() throws Exception {
		if (batchParam == null) {
			if (batchParamClassName != null
					&& batchParamClassName.trim().length() > 0) {
				try {
					batchParam = (BatchParam) Class.forName(batchParamClassName)
							.newInstance();
				} catch (Throwable e) {
					e.printStackTrace();
				}
			}
		}
		if (batchParam == null) {
			if (voClass != null && BatchParam.class.isAssignableFrom(voClass)) {
				try {
					batchParam = (BatchParam) voClass.newInstance();
				} catch (Throwable ex) {
					ex.printStackTrace();
				}
			}
		}
		if (batchParam == null) {
			if (voClass != null) {
				String className = voClass.getName();
				String listVOPreFix = StringUtils.substringBeforeLast(
						className, "VO").replaceAll(".model.", ".view.");
				String listVOClassName = listVOPreFix + "ListVO";
				try {
					Class<?> listVOClass = Class.forName(listVOClassName);
					if (BatchParam.class.isAssignableFrom(listVOClass)) {
						batchParam = (BatchParam) listVOClass.newInstance();
					}
				} catch (Throwable ex) {
					ex.printStackTrace();
				}
			}
		}
		if (batchParam != null) {
			Map<String, Object> batchParams = ParamsBuilder.buildMapFromHttpRequest();
			this.setParamsMapToTargetObject(batchParams, batchParam);
		}

		return batchParam;
	}

	public void setBatchParam(BatchParam batchParam) {
		this.batchParam = batchParam;
	}

}
