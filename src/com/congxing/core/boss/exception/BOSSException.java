package com.congxing.core.boss.exception;

/**
 * <p>Title: BOSS接口异常</p>
 * <p>Description: BOSS操作失败时（返回代码不为0）抛出些异常</p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: Maywide Tech Ltd.</p>
 * @author not attributable
 * @version 1.0
 */
@SuppressWarnings("serial")
public class BOSSException extends Exception {
  public BOSSException() {
    super();
  }

  public BOSSException(String msg) {
    super(msg);
  }
}

