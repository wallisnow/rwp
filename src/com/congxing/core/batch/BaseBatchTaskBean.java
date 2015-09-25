package com.congxing.core.batch;

import java.io.File;
import java.io.RandomAccessFile;
import java.io.Serializable;
import java.util.Locale;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;

import org.apache.log4j.Logger;

import com.congxing.core.model.CommonResultObject;
import com.congxing.core.model.Message;
import com.congxing.core.utils.BeanNameUtil;
import com.congxing.core.utils.CharsetUtils;
import com.congxing.core.utils.I18nMessage;
import com.congxing.system.user.model.UserVO;

public abstract class BaseBatchTaskBean implements Runnable, Serializable {
	
	static Logger logger = Logger.getLogger(BaseBatchTaskBean.class);

	private static final long serialVersionUID = 7378128845205399617L;

	public static final String FIELD_SEPERATOR = "\\|";

	public static final String OUTPUT_FIELD_SEPERATOR = " | ";

	protected int countrecord;

	protected boolean started = false;

	protected boolean running = false;

	protected boolean start_end;

	protected String filename;

	protected String resultFile;

	protected int currentrecord = 0;

	protected int fail;

	protected int ok;

	protected UserVO user;

	protected Short oprtype;

	protected String taskname;

	protected String i18nBundle;

	protected Locale userLocale;

	protected Object queryParam;
	
	protected boolean txtFile = true;

	public void setCountRecord() {
		countrecord = getRecordNumber(filename);
	}

	public void work() {
		this.start_end = true;
		try {
			doProcessFile();
		} catch (InterruptedException ex) {
			setRunning(false);
		} catch (Exception ex) {
			ex.printStackTrace();
			setRunning(false);
		}
	}

	public void doProcessFile() throws Exception {
		if(null == filename || "".equals(filename))throw new Exception("文件为空");
		
		String resultName = "";
		if(filename.endsWith(".xls")){
			resultName = filename.replaceFirst("\\.xls$", "_result.txt");
		}else if(filename.endsWith(".xlsx")){
			resultName = filename.replaceFirst("\\.xlsx$", "_result.txt");
		}else if(filename.endsWith(".txt")){
			resultName = filename.replaceFirst("\\.txt$", "_result.txt");
		}else{
			throw new Exception("文件格式错误");
		}
		
		RandomAccessFile rin = null;
		jxl.Workbook rwb = null;
		RandomAccessFile resultFile = null;

		try {
			int ok_count = 0, fail_count = 0;
			CommonResultObject result = new CommonResultObject();

			resultFile = new RandomAccessFile(new java.io.File(resultName), "rw");
			
			resultFile.writeBytes(CharsetUtils.toByteBasedString(getTitle()));
			resultFile.writeBytes("\r\n");
			
			if(this.isTxtFile()){
				rin = new RandomAccessFile(filename, "r");
				long length = rin.length();
				long filePointer = 0;

				String line = "";
				int count = 0;
				while (filePointer < length) {
					line = rin.readLine();
					line = CharsetUtils.fromByteBasedString(line);
					if (null != line && line.trim().length() > 0) {
						result = updateOneRecord(line);
						++count;
						if (result.isSuccess()) {
							++ok_count;
						} else {
							++fail_count;
						}
						String resultStr = makeStr(result, line, count);
						resultFile.writeBytes(CharsetUtils.toByteBasedString(resultStr));
						resultFile.writeBytes("\r\n");

						currentrecord++;
					}
					filePointer = rin.getFilePointer();
				}
			}else{
				rwb = Workbook.getWorkbook(new File(filename));
				Sheet sheet = rwb.getSheet(0);

				int rowIndex = 0;
				int count = 0;

				while(rowIndex < this.getCountrecord()){
					Cell []cells = sheet.getRow(rowIndex++);
					result = updateOneRecord(cells);
					++count;
					if (result.isSuccess()) {
						++ok_count;
					} else {
						++fail_count;
					}
					String resultStr = makeStr(result, cells, count);
					resultFile.writeBytes(CharsetUtils.toByteBasedString(resultStr));
					resultFile.writeBytes("\r\n");
					
					currentrecord++;
				}
			}

			this.resultFile = resultName;
			this.fail = fail_count;
			this.ok = ok_count;

		} catch (Exception ex) {
			throw ex;
		} finally {
			if (rin != null) {
				rin.close();
			}
			if (resultFile != null) {
				resultFile.close();
			}
			if(null != rwb){
				rwb.close();
			}
			deleteFile_(filename);
		}
	}

	protected abstract String getTitle();

	protected abstract String makeStr(CommonResultObject resultVO, Object line, int i);

	protected abstract CommonResultObject updateOneRecord(Object line);

	protected void deleteFile_(String filename) {
		File file = new File(filename);
		if (file.exists()) {
			file.delete();
		}
	}

	private int getRecordNumber(String filename) {
		int numbers = 0;
		if(this.isTxtFile()){
			try {
				RandomAccessFile rin = new RandomAccessFile(filename, "r");
				long length = rin.length();
				long filePointer = rin.getFilePointer();
				String line;
	
				while (filePointer < length) {
					line = rin.readLine();
					filePointer = rin.getFilePointer();
					if (null != line && line.trim().length() > 0) {
						numbers++;
					}
				}
				rin.close();
			} catch (Exception ex) {
				logger.error("Batch[" + filename + "]", ex);
			}
		}else{
			jxl.Workbook rwb = null;
			try{
				rwb = Workbook.getWorkbook(new File(filename));
				numbers = rwb.getSheet(0).getRows();
				
			} catch (Exception ex) {
				logger.error("Batch[" + filename + "]", ex);
			} finally {
				if(null != rwb)rwb.close();
			}
		}
		return numbers;
	}

	public synchronized int getPercent() {
		if (countrecord == 0) {
			return 0;
		}
		int res = (currentrecord * 100) / countrecord;
		if (res == 100) {
			this.setStart_end();
		}
		return res;
	}

	public synchronized boolean isStarted() {
		return started;
	}

	public synchronized boolean isCompleted() {
		if (countrecord == 0) {
			return true;
		}
		return currentrecord == countrecord;
	}

	public synchronized boolean isRunning() {
		return running;
	}

	public synchronized void setRunning(boolean running) {
		this.running = running;
		if (running) {
			started = true;
		}
	}

	public void run() {
		try {
			setRunning(true);
			while (isRunning() && !isCompleted()) {
				work();
			}
		} finally {
			setRunning(false);
		}
	}

	public static String transException(Exception ex) {
		String message = ex.getMessage();
		if (ex.getMessage().indexOf("java.sql.BatchUpdateException: Unique constraint") >= 0) {
			message = "";
		}
		return message;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	public String getFilename() {
		return this.filename;
	}

	public void setUser(UserVO user) {
		this.user = user;
	}

	public UserVO getUser() {
		return user;
	}

	public int getFail() {
		return fail;
	}

	public String getResultFile() {
		return resultFile;
	}

	public int getOk() {
		return ok;
	}

	public int getCountrecord() {
		return countrecord;
	}

	public boolean isStart_end() {
		return start_end;
	}

	public void setStart_end() {
		this.start_end = false;
	}

	public Short getOprtype() {
		return oprtype;
	}

	public void setOprtype(Short oprtype) {
		this.oprtype = oprtype;
	}

	public String getTaskname() {
		return taskname;
	}

	public void setTaskname(String taskname) {
		this.taskname = taskname;
	}

	public Object getQueryParam() {
		return queryParam;
	}

	public void setQueryParam(Object queryParam) {
		this.queryParam = queryParam;
	}

	protected String localize(String key, Object[] arguments) {
		if (i18nBundle == null) {
			i18nBundle = BeanNameUtil.guessBundleNameByClass(this.getClass());
		}
		return I18nMessage.message(i18nBundle, userLocale, key, arguments);
	}

	protected String localize(String key) {
		return localize(key, null);
	}

	protected String localize(Message message) {
		return localize(message.getKey(), message.getArguments());
	}

	public boolean isTxtFile() {
		return txtFile;
	}

	public void setTxtFile(boolean txtFile) {
		this.txtFile = txtFile;
	}
}
