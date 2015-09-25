package com.congxing.core.boss.model;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;

import com.congxing.core.boss.exception.PkgFormatException;

@SuppressWarnings("serial")
public class PktMsg implements Serializable {

	private CtlMsg ctlmsg; // 包控制信息
	private short errorcode; // 返回码
	private String datatrans; // 传送内容，从datatrans开始依次为数据内容

	public PktMsg() {
		ctlmsg = new CtlMsg();
	}

	public PktMsg(byte[] bytes) throws Exception {
		try {
			ByteArrayInputStream bis = new ByteArrayInputStream(bytes);
			DataInputStream dos = new DataInputStream(bis);
			byte[] buff = new byte[CtlMsg.CTLMSG_LEN];
			dos.read(buff);
			ctlmsg = new CtlMsg(buff);
			errorcode = dos.readShort();
			buff = new byte[(int) (ctlmsg.getLen() - CtlMsg.CTLMSG_LEN)];
			dos.read(buff);
			datatrans = new String(buff, "gbk").trim();
		} catch (Exception ex) {
			// 传入的byte数组不能解折，格式错误
			throw new PkgFormatException(ex.getMessage());
		}
	}

	public byte[] getBytes() throws Exception {
		ByteArrayOutputStream bos = new ByteArrayOutputStream(1024);
		DataOutputStream dos = new DataOutputStream(bos);
		byte[] buff = ctlmsg.getBytes();
		dos.write(buff);
		dos.writeShort(errorcode);
		buff = datatrans.getBytes("gbk");
		dos.write(buff);
		return bos.toByteArray();
	}

	public CtlMsg getCtlmsg() {
		return ctlmsg;
	}

	public void setCtlmsg(CtlMsg ctlmsg) {
		this.ctlmsg = ctlmsg;
		if (datatrans != null) {
			ctlmsg.setLen(CtlMsg.CTLMSG_LEN + 2 + datatrans.getBytes().length);
		}
	}

	public String getDatatrans() {
		return datatrans;
	}

	public void setDatatrans(String datatrans) {
		this.datatrans = datatrans;
		if (ctlmsg != null) {
			try {
				ctlmsg.setLen(CtlMsg.CTLMSG_LEN + 2 + datatrans.getBytes("gbk").length);
			} catch (UnsupportedEncodingException ex) {
			}
		}
	}

	public short getErrorcode() {
		return errorcode;
	}

	public void setErrorcode(short errorcode) {
		this.errorcode = errorcode;
	}
}
