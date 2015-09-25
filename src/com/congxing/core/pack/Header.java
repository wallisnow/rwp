package com.congxing.core.pack;

import java.io.IOException;
import java.io.Serializable;

public interface Header extends Serializable{
	
	/* 取包头控制信息长度 */
	public int getHeaderLength();
	
	/* 取信息包总长度*/
	public int getTotalLength();
	
	/*设置包信息长度*/
	public void setTotalLength(int totalLength);
	
	/* 取包头控制字节数组 */
	public byte[] getBytes() throws IOException;
	
	/* 设置包头信息 */
	public void setHeader(byte []bytes) throws IOException;

}
