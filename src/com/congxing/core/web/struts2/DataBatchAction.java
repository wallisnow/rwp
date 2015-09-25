/**
 *
 */
package com.congxing.core.web.struts2;

import com.congxing.core.batch.BaseBatchTaskBean;
import com.congxing.core.utils.FilenameUtil;

/**
 * <p>
 * Title:
 * </p>
 *
 * <p>
 * Description: DataBatchAction
 * </p>
 *
 * <p>
 * Copyright: Copyright (c) Revenco
 * </p>
 *
 * <p>
 * Company: Sunrise Tech Ltd.
 * </p>
 *
 * @author laiweilong
 *
 * @version 1.0
 *
 * @since 2009-10-28
 */
public class DataBatchAction extends BatchProcessAction {

	private static final long serialVersionUID = -7151638669915457976L;

	protected void preTaskSet(BaseBatchTaskBean task)
			throws Exception {
		super.preTaskSet(task);
		/*
		 * 因为当前批处理类型处理过程中无需导入文件，
		 *
		 * 所以在父类中设置导入文件名的操作是无效的，
		 *
		 * 而结果文件名却需要从导入文件名中转换得到，
		 *
		 * 故需为其生成一个与操作员、时间相关的随机文件名
		 *
		 */
		task.setFilename(createResultFilename());
	}

	private String createResultFilename() throws Exception {

		StringBuffer buffer = new StringBuffer();

		String location = FilenameUtil.getWebAppRelatedUploadLocation();

		String filename = FilenameUtil.nameByTiming(batchFileInfo.getRenamePrefix());

		if(!filename.toLowerCase().endsWith(".txt")) {
			filename = filename + ".txt";
		}

		buffer.append(location).append(filename);

		return buffer.toString();
	}

}
