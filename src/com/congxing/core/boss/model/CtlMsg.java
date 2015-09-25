package com.congxing.core.boss.model;

import java.io.*;
import java.util.*;

@SuppressWarnings("serial")
public class CtlMsg implements Serializable {
	public static final int CTLMSG_LEN = 55;

	public static final int CMD_ID_LEN = 10;
	public static final int REC_SEPERATOR_LEN = 5;
	public static final int FIELD_SEPERATOR_LEN = 5;

	// 属性对应于原C的结构，类型有所变化

	private int len; // 包的总长度，为包控制信息和实际传送内容长度之和
	private int factorycode; // 厂商编码
	private int progid; // 进程号，由客服系统产生
	private int morepkt; // 是否还有后续包，1有，0无
	private String cmd_id; // 命令字，最大长度为9
	private int start_num; // 起始记录号(从0开始)
	private int end_num; // 终止记录号(为0时表示其余全部数据)
	private int request_id; // 请求ID，客服系统生成
	private int answer_id; // 应答ID，用于计费系统使用，也可不用。
	private int sequence; // 包序号
	private String rec_seperator = Constant.REC_SEPERATOR; // 记录分隔符，最大长度为4
	private String field_seperator = Constant.BOSSFIELD_SEPERATOR; // 字段分隔符，最大长度为4
	private String bossfield_seperator = Constant.BOSSFIELD_SEPERATOR;// 营销方案受理字段分隔符，最大长度为4
	private int reserved1; // 保留
	private int reserved2; // 保留

	public CtlMsg() {
		Random rand = new Random();
		progid = rand.nextInt(256); // byte
	}

	public CtlMsg(byte[] bytes) throws Exception {
		ByteArrayInputStream bis = new ByteArrayInputStream(bytes);
		DataInputStream dos = new DataInputStream(bis);
		len = dos.readInt();
		factorycode = dos.readUnsignedByte();
		progid = dos.readUnsignedByte();
		morepkt = dos.readUnsignedByte();
		byte[] buff = new byte[CMD_ID_LEN];
		dos.read(buff, 0, CMD_ID_LEN);
		cmd_id = new String(buff).trim();
		start_num = dos.readInt();
		end_num = dos.readInt();
		request_id = dos.readInt();
		answer_id = dos.readInt();
		sequence = dos.readInt();
		buff = new byte[REC_SEPERATOR_LEN];
		dos.read(buff, 0, REC_SEPERATOR_LEN);
		rec_seperator = new String(buff).trim();
		buff = new byte[FIELD_SEPERATOR_LEN];
		dos.read(buff, 0, FIELD_SEPERATOR_LEN);
		field_seperator = new String(buff).trim();
		reserved1 = dos.readInt();
		reserved2 = dos.readInt();
	}

	public byte[] getBytes() throws Exception {
		ByteArrayOutputStream bos = new ByteArrayOutputStream(100);
		DataOutputStream dos = new DataOutputStream(bos);
		dos.writeInt(len);
		dos.writeByte(factorycode);
		dos.writeByte(progid);
		dos.writeByte(morepkt);
		byte[] buff = new byte[CMD_ID_LEN];
		System.arraycopy(cmd_id.getBytes(), 0, buff, 0,
				cmd_id.getBytes().length >= CMD_ID_LEN ? CMD_ID_LEN - 1 : cmd_id.getBytes().length);
		dos.write(buff, 0, CMD_ID_LEN);
		dos.writeInt(start_num);
		dos.writeInt(end_num);
		dos.writeInt(request_id);
		dos.writeInt(answer_id);
		dos.writeInt(sequence);
		buff = new byte[REC_SEPERATOR_LEN];
		System.arraycopy(rec_seperator.getBytes(), 0, buff, 0, 
				rec_seperator.getBytes().length >= REC_SEPERATOR_LEN ? REC_SEPERATOR_LEN - 1 : rec_seperator.getBytes().length);
		dos.write(buff, 0, REC_SEPERATOR_LEN);
		buff = new byte[FIELD_SEPERATOR_LEN];
		System.arraycopy(field_seperator.getBytes(), 0, buff, 0,
						field_seperator.getBytes().length >= FIELD_SEPERATOR_LEN ? FIELD_SEPERATOR_LEN - 1 : field_seperator.getBytes().length);
		dos.write(buff, 0, FIELD_SEPERATOR_LEN);
		dos.writeInt(reserved1);
		dos.writeInt(reserved2);
		return bos.toByteArray();
	}

	public int getAnswer_id() {
		return answer_id;
	}

	public void setAnswer_id(int answer_id) {
		this.answer_id = answer_id;
	}

	public String getCmd_id() {
		return cmd_id;
	}

	public void setCmd_id(String cmd_id) {
		this.cmd_id = cmd_id;
	}

	public void setEnd_num(int end_num) {
		this.end_num = end_num;
	}

	public int getFactorycode() {
		return factorycode;
	}

	public void setFactorycode(int factorycode) {
		this.factorycode = factorycode;
	}

	public String getField_seperator() {
		return field_seperator;
	}

	public void setField_seperator(String field_seperator) {
		this.field_seperator = field_seperator;
	}

	public int getLen() {
		return len;
	}

	public void setLen(int len) {
		this.len = len;
	}

	public int getMorepkt() {
		return morepkt;
	}

	public void setMorepkt(int morepkt) {
		this.morepkt = morepkt;
	}

	public int getProgid() {
		return progid;
	}

	public void setProgid(int progid) {
		this.progid = progid;
	}

	public String getRec_seperator() {
		return rec_seperator;
	}

	public void setRec_seperator(String rec_seperator) {
		this.rec_seperator = rec_seperator;
	}

	public int getRequest_id() {
		return request_id;
	}

	public void setRequest_id(int request_id) {
		this.request_id = request_id;
	}

	public int getReserved1() {
		return reserved1;
	}

	public void setReserved1(int reserved1) {
		this.reserved1 = reserved1;
	}

	public int getReserved2() {
		return reserved2;
	}

	public void setReserved2(int reserved2) {
		this.reserved2 = reserved2;
	}

	public int getSequence() {
		return sequence;
	}

	public void setSequence(int sequence) {
		this.sequence = sequence;
	}

	public int getStart_num() {
		return start_num;
	}

	public String getBossfield_seperator() {
		return bossfield_seperator;
	}

	public void setStart_num(int start_num) {
		this.start_num = start_num;
	}

	public void setBossfield_seperator(String bossfield_seperator) {
		this.bossfield_seperator = bossfield_seperator;
	}
}
