package com.congxing.core.pack;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.Serializable;

@SuppressWarnings("serial")
public class PackInfo implements Serializable {
	
	private Header header;//包头控制信息
	
	private Body body;

	public PackInfo(Header header){
		this.header = header;
		body = new DefaultBody();
	}

	public PackInfo(Header header, Body body) {
		this.header = header;
		this.body = body;
	}

	public void setPackInfo(byte[] bytes) throws Exception{
		try {
			ByteArrayInputStream bis = new ByteArrayInputStream(bytes);
			DataInputStream dos = new DataInputStream(bis);
			byte[] buff = new byte[header.getHeaderLength()];
			dos.read(buff);
			header.setHeader(buff);
			buff = new byte[header.getTotalLength() - header.getHeaderLength()];
			dos.read(buff);
			body.setBody(buff);
		} catch (Exception ex) {
			// 传入的byte数组不能解折，格式错误
			throw new Exception(ex.getMessage());
		}
	}

	public byte[] getBytes() throws Exception{
		ByteArrayOutputStream bos = new ByteArrayOutputStream(1024);
		DataOutputStream dos = new DataOutputStream(bos);
		byte[] bodyBuff = body.getBytes();
		header.setTotalLength(bodyBuff.length + header.getHeaderLength());
		dos.write(header.getBytes());
		dos.write(bodyBuff);
		return bos.toByteArray();
	}

	public Header getHeader() {
		return header;
	}

	public void setHeader(Header header) {
		this.header = header;
	}

	public Body getBody() {
		return body;
	}

	public void setBody(Body body) {
		this.body = body;
	}

}
