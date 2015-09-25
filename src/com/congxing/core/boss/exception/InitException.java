package com.congxing.core.boss.exception;

/**
 * <p>Title: 初始化服务器失败异常</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: Maywide Tech Ltd.</p>
 * @author not attributable
 * @version 1.0
 */
@SuppressWarnings("serial")
public class InitException extends Exception {
  public InitException() {
    super();
  }

  public InitException(String msg) {
    super(msg);
  }
}
