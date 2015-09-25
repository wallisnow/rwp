package com.congxing.core.boss.control;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

import org.apache.log4j.Logger;

import com.congxing.core.boss.model.MobileZoneRetMsg;
import com.congxing.core.boss.model.PktMsg;

/**
 * <p>
 * Title: BOSS接口
 * </p>
 * <p>
 * Description:
 * </p>
 * <p>
 * Copyright: Copyright (c) 2004
 * </p>
 * <p>
 * Company:
 * </p>
 * 
 * @author not attributable
 * @version 1.0
 */
public class BOSS {
	private static Logger log = Logger.getLogger(BOSS.class);

	public BOSS() throws Exception {
	}

	/************************************************************
	 * 业务方法
	 ************************************************************/

	/**
	 * @Desc 查询号码归属区域
	 * @param mobileno
	 * @param opercode
	 * @param rtFormat
	 * @return
	 * @throws Exception
	 */
	public MobileZoneRetMsg queryMobileZone(String mobileno, String opercode, String rtFormat) throws Exception {
		log.info("查询号码归属区域查询");
		String[] args = new String[] { mobileno, opercode, rtFormat };
		PktMsg pktmsg = process("20101", args);
		MobileZoneRetMsg ret = new MobileZoneRetMsg(pktmsg.getDatatrans());
		return ret;
	}

	/************************************************************
	 * 私有方法
	 ************************************************************/

	/**
	 * 将参数组成Datatrans所要求的格式
	 * 
	 * @param args
	 *            String[]
	 * @return String
	 */
	protected String buildDatatransStr(String[] args, String field_seperator, String rec_seperator) {
		StringBuffer buff = new StringBuffer(args[0]);
		for (int i = 1; i < args.length; i++) {
			buff = buff.append(field_seperator);
			if (args[i] != null) {
				buff = buff.append(args[i]);
			}
		}
		buff = buff.append(rec_seperator);
		return buff.toString();
	}

	/**
	 * 将命令发送至BOSS并获取返回，只适用于查询命令
	 * 
	 * @param cmd_id
	 *            String 命令字
	 * @param args
	 *            String[] 参数数组
	 * @throws Exception
	 * @return PktMsg
	 */
	protected PktMsg process(String cmd_id, String[] args) throws Exception {
		PktMsg pktmsg = new PktMsg();
		pktmsg.getCtlmsg().setCmd_id(cmd_id);
		pktmsg.setDatatrans(buildDatatransStr(args, pktmsg.getCtlmsg().getField_seperator(),
				pktmsg.getCtlmsg().getRec_seperator()));
		PktMsg ret = process(pktmsg);
		return ret;
	}

	/**
	 * 将命令发送至BOSS并获取返回
	 * 
	 * @param input
	 *            PktMsg
	 * @throws Exception
	 * @return PktMsg
	 */
	protected PktMsg process(PktMsg input) throws Exception {
		byte[] buff = input.getBytes();

		Socket socket = null;
		try {
			socket = SocketSource.getInstance().get();
			DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
			DataInputStream dis = new DataInputStream(socket.getInputStream());

			log.info("发送数据：" + new String(buff, "gbk"));

			// 发送信息时清空缓冲
			dis.skip(dis.available());

			dos.write(buff);
			dos.flush();

			byte begin = dis.readByte();
			buff = new byte[dis.available()];
			buff[0] = begin;
			dis.readFully(buff, 1, buff.length - 1);

			// 收到信息后清空缓冲
			dis.skip(dis.available());

			SocketSource.getInstance().put(socket);

			log.info("接收数据：" + new String(buff, "gbk"));
			return new PktMsg(buff);
		} catch (IOException ex) {
			if (socket != null) {
				try {
					socket.close();
				} catch (Exception ex1) {
				}
				socket = null;
			}
			try {
				SocketSource.getInstance().newHandler();
			} catch (Exception e) {
			}
			throw ex;
		}
	}

}
