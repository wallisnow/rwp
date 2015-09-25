package com.congxing.core.boss.exception;

/**
 * <p>Title: 系统忙异常</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: Maywide Tech Ltd.</p>
 * @author not attributable
 * @version 1.0
 */
@SuppressWarnings("serial")
public class SystemBusyException extends Exception {
  public SystemBusyException() {
    super();
  }

  public SystemBusyException(String msg) {
    super(msg);
  }
}
