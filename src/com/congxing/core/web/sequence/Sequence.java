package com.congxing.core.web.sequence;

import java.net.InetAddress;
import java.util.Date;

import com.congxing.core.utils.DateFormatFactory;

/**
 * <p>
 * 序列号生成器，用法如下：
 * </p>
 * 
 * <pre>
 * Sequence.getInstance().getXXX();
 * </pre>
 * 
 * @version 1.0
 */

public class Sequence {
	private static Sequence me;

	// 取IP的最后一位，假设在集群中IP的最后一位是不相同的，则取出的SEQ也不相同
	private static final int IP;
	static {
		int ipadd;
		try {
			String hostadd = InetAddress.getLocalHost().getHostAddress();
			ipadd = Integer.parseInt(hostadd.substring(hostadd.length() - 1));
		} catch (Exception e) {
			ipadd = 0;
		}
		IP = ipadd;
	}

	/**
	 * 基本序列号的基数，这个基数必须是10的N次方，一毫秒内只能取到基数减一个序列号
	 */
	private static final int base = 10;
	private static long millis = System.currentTimeMillis(), old = 0;

	/**
	 * 取得一个服务定位器的实例
	 */
	public static synchronized Sequence getInstance() throws Exception {
		if (me == null) {
			me = new Sequence();
		}
		return me;
	}

	/**
	   * 取基本序列号，序列号与时间相关，是当前的毫秒数后加上一个不重复的数构成
	   * @return 序列号
	   */
	public static synchronized long getSequence() {
		old++;
		if (old >= base) {
			while (millis == System.currentTimeMillis()) {
				try {
					Thread.sleep(1);
				} catch (InterruptedException ex) {
				}
			}
			millis = System.currentTimeMillis();
			old = 0;
		}

		return (millis * base + old) * 10 + IP;
	}

	/**
	   * 基本序列号的基数，这个基数必须是10的N次方，一毫秒内只能取到基数减一个序列号
	   */
	private static long gmillis = System.currentTimeMillis(), gold = 0;

	public static synchronized long getSequenceByBase(int mybase)
			throws SequenceException {
		long result = System.currentTimeMillis();
		if (result == gmillis) {
			gold++;
			if (gold >= (gmillis + 1) * mybase) {
				throw new SequenceException("已达到本毫秒内的最大序列号");
			}
			result = gold;
		} else {
			gmillis = result;
			result *= mybase;
			gold = result;
		}
		return result * 10 + IP;
	}

	public static long getMinSeqByTime(String strTime) {
		try {
			Date date = DateFormatFactory.getDefaultFormat().parse(strTime);
			return date.getTime() * base;
		} catch (Exception ex) {
		}

		return 0;
	}

	public static long getMaxSeqByTime(String strTime) {
		try {
			Date date = DateFormatFactory.getDefaultFormat().parse(strTime);
			return (date.getTime() + 1000) * base; //// 加一秒
		} catch (Exception ex) {
		}
		return 0;
	}

	public static void main(String[] args) throws Exception {
		System.out.println(System.currentTimeMillis());
		for (int i = 0; i < 1000; i++) {
			System.out.println(Sequence.getSequence());
		}
		System.out.println(System.currentTimeMillis());
	}
}
