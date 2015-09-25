package com.congxing.core.pack;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import org.apache.commons.lang.StringUtils;

@SuppressWarnings("serial")
public class DefaultHeader implements Header {
	
	public static final int CTLINFO_LEN = 14;
	public static final int CMD_ID_LEN = 10;
	
	private int totalLength;//信息包总长度,4个字节
	private String cmdId;//10字节

	public int getHeaderLength() {
		return CTLINFO_LEN;
	}

	public int getTotalLength() {
		return totalLength;
	}
	
	public void setTotalLength(int totalLength) {
		this.totalLength = totalLength;
	}

	public void setHeader(byte[] bytes) throws IOException {
		ByteArrayInputStream bis = new ByteArrayInputStream(bytes);
		DataInputStream dos = new DataInputStream(bis);
		totalLength = dos.readInt();
		byte[] buff = new byte[CMD_ID_LEN];
		dos.read(buff, 0, CMD_ID_LEN);
		cmdId = new String(buff).trim();
	}

	public byte[] getBytes() throws IOException {
		ByteArrayOutputStream bos = new ByteArrayOutputStream(100);
		DataOutputStream dos = new DataOutputStream(bos);
		dos.writeInt(totalLength);
		byte[] buff = new byte[CMD_ID_LEN];
		if(StringUtils.isBlank(cmdId)){
			cmdId = "";
		}
		System.arraycopy(cmdId.getBytes(), 0, buff, 0, cmdId.getBytes().length >= CMD_ID_LEN ? CMD_ID_LEN - 1 : cmdId.getBytes().length);
		dos.write(buff, 0, CMD_ID_LEN);
		return bos.toByteArray();
	}

	public String getCmdId() {
		return cmdId;
	}

	public void setCmdId(String cmdId) {
		this.cmdId = cmdId;
	}
	
}
