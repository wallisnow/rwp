package com.congxing.core.utils;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;

import org.apache.log4j.FileAppender;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.PatternLayout;

public class Log2File implements Serializable {

	private static final long serialVersionUID = -5100257941392871225L;
	
	private Logger log = Logger.getLogger("dummy"); //虚拟的日志管理器

	/**
	 * 
	 */
	private Log2File() {
		super();
	}

	public static Log2File getInstance(String filePath) throws IOException {
		File file = new File(filePath);
		if(file.exists()){
			file.delete();
		}
		
		Log2File self = new Log2File();
		
		/*
		 * 构建一个日志的展现类，该展现形似于在log4j.xml做如下定义
		 * 
		 * <appender name="xxx" class="org.apache.log4j.FileAppender">
		 *     <param name="File" value="${param.filePath}"/>
		 *     <param name="Append" value="true"/>
		 *     <param name="Encoding" value="gbk"/>
		 *     <layout class="org.apache.log4j.PatternLayout">
		 *         <param name="ConversionPattern" value="%m%n"/>
		 *     </layout>
		 * </appender>
		 */
		
		//  PatternLayout.DEFAULT_CONVERSION_PATTERN = "%m%n";
		PatternLayout p = new PatternLayout(
				PatternLayout.DEFAULT_CONVERSION_PATTERN);
		/*
		 * FileAppender构造函数参数解释
		 * 
		 * (Layout layout, String filename, 
		 * boolean append, boolean bufferedIO, 
		 * int bufferSize)
		 * 
		 * layout            日志展现格式
		 * filename          日志写入的文件全路径名
		 * append=true       日志内容追加到文件尾
		 * bufferedIO=false  日志内容不缓存，直接写文件
		 * bufferSize=8192   假如日志先缓存的话，缓冲区大小的设置
		 */		
		FileAppender c = new FileAppender();

		c.setLayout(p);

		// 设置appender的编码集为gbk
		c.setEncoding("gbk");

		c.setFile(filePath, true, false, 8192);
		
		self.log.addAppender(c);
		
		self.log.setLevel(Level.INFO);
		
		return self;
	}
	
	public void info(Object param){
		log.info(param);
	}
	
	public void error(Object param){
		log.error(param);
	}
	
	public void debug(Object param){
		log.debug(param);
	}
	
	public static void main(String []args) throws Exception{
		Log2File lf = Log2File.getInstance("c:/1.log");
		lf.info("aaaa");
	}

}
