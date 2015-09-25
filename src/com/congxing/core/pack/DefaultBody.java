package com.congxing.core.pack;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import org.apache.commons.lang.StringUtils;

@SuppressWarnings("serial")
public class DefaultBody implements Body {
	
	private short resultcode;//返回码,2个字节
	
	private String datatrans;// 传送内容
	
	public void setBody(byte[] bytes) throws IOException {
		ByteArrayInputStream bis = new ByteArrayInputStream(bytes);
		DataInputStream dos = new DataInputStream(bis);
		resultcode = dos.readShort();
		byte[] buff = new byte[bytes.length - 2];
		dos.read(buff, 0, buff.length);
		datatrans = new String(buff, "gbk").trim();
	}
	
	public byte[] getBytes() throws IOException{
		ByteArrayOutputStream bos = new ByteArrayOutputStream(1024);
		DataOutputStream dos = new DataOutputStream(bos);
		dos.writeShort(resultcode);
		if(StringUtils.isNotBlank(datatrans)){
			dos.write(this.getDatatrans().getBytes("gbk"));
		}
		return bos.toByteArray();
	}
	
	public short getResultcode() {
		return resultcode;
	}
	
	public void setResultcode(short resultcode) {
		this.resultcode = resultcode;
	}

	public String getDatatrans() {
		return datatrans;
	}

	public void setDatatrans(String datatrans) {
		this.datatrans = datatrans;
	}

}
