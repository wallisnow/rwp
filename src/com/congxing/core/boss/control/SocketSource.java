package com.congxing.core.boss.control;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Properties;

import org.apache.log4j.Logger;

import EDU.oswego.cs.dl.util.concurrent.BoundedBuffer;

import com.congxing.core.boss.exception.InitException;
import com.congxing.core.boss.exception.SystemBusyException;
import com.congxing.core.utils.ConfigReader;

/**
 * <p>
 * Title: Socket连接池
 * </p>
 * <p>
 * Description: 调用方法如下：
 * </p>
 * 
 * <pre>
 * SocketSource.getInstance().get() 取得一个Socket
 * SocketSource.getInstance().put(Socket) 归还一个Socket
 * 当有Socket异常的情况下，如断开等，可用SocketSource.getInstance().newHandler()重建一个连接
 * </pre>
 * 
 * @author not attributable
 * @version 1.0
 */
public class SocketSource {

	private static Logger log = Logger.getLogger(SocketSource.class);
	private int pool_size;
	private int pool_timeout; // in milliseconds

	private String server_address;
	private int server_port;
	private int socket_timeout; // in milliseconds

	private BoundedBuffer pool;

	private static SocketSource me;

	private SocketSource() throws Exception {
		try {
			Properties p = ConfigReader.readConfigFile("boss.properties");

			pool_size = Integer.parseInt(p.getProperty("pool_size", "10"));
			pool_timeout = Integer.parseInt(p.getProperty("pool_timeout", "30000"));
			server_address = p.getProperty("server_address");
			server_port = Integer.parseInt(p.getProperty("server_port"));
			socket_timeout = Integer.parseInt(p.getProperty("socket_timeout", "180000"));

			pool = new BoundedBuffer(pool_size);
			for (int i = 0; i < pool_size; i++) {
				newHandler();
			}
			log.info("初始化BOSS接口成功");
		} catch (Exception ex) {
			log.error("初始化BOSS接口出错", ex);
			throw new InitException(ex.getMessage());
		}
	}

	/**
	 * 获取SocketSource的实例
	 * 
	 * @return SocketSource
	 */
	public static synchronized SocketSource getInstance() throws Exception {
		if (me == null) {
			me = new SocketSource();
		}
		return me;
	}

	/**
	 * 从池中取得一个Socket
	 * 
	 * @param t
	 *            int
	 * @return Channel
	 */
	public Socket get() throws SystemBusyException {
		try {
			Socket s = (Socket) pool.poll(pool_timeout);
			if (s == null) {
				// 在等待一段长时间后，无法取得Socket，有可能系统忙或者所有连接已断，尝试建新的连接
				try {
					newHandler();
				} catch (UnknownHostException ex1) {
				} catch (IOException ex1) {
				}
				throw new SystemBusyException("系统忙，无法获取BOSS接口连接");
			}
			return s;
		} catch (InterruptedException ex) {
			log.error("系统忙，无法获取Socket连接", ex);
			throw new SystemBusyException(ex.getMessage());
		}
	}

	/**
	 * 回放一个Socket到池中
	 * 
	 * @param s
	 *            Socket
	 * @param t
	 *            int
	 */
	public void put(Socket s) {
		try {
			if (pool.size() < pool_size) {
				pool.offer(s, pool_timeout);
			} else {
				// Socket池已满，关闭无用的Socket
				try {
					if (s != null) {
						s.close();
					}
				} catch (IOException ex1) {
				}
				s = null;
			}
		} catch (InterruptedException ex) {
			// 不能回放Socket，关闭它
			try {
				if (s != null) {
					s.close();
				}
			} catch (IOException ex1) {
			}
			s = null;
			log.error("系统忙，无法归还Socket资料", ex);
		}
	}

	/**
	 * 生成新的服务线程
	 */
	public void newHandler() throws UnknownHostException, IOException {
		try {
			if (pool.size() < pool_size) {
				Socket sock = new Socket(server_address, server_port);
				sock.setSoTimeout(socket_timeout);
				sock.setSendBufferSize(1024);
				put(sock);
				log.info("成功与BOSS接口机建立连接：" + sock);
				log.info("server_address:" + server_address + ", server_port:" + server_port);
			}
		} catch (UnknownHostException ex) {
			log.error("无法识别的BOSS接口机", ex);
			throw ex;
		} catch (IOException ex) {
			log.error("无法与BOSS接口机建立连接");
			log.error("server_address:" + server_address + ", server_port:"
					+ server_port);
			log.error(ex);
			throw ex;
		}
	}
}
