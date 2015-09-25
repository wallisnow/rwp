package com.congxing.core.boss.exception;

/**
 * <p>Title: 非法的服务功能代码异常</p>
 * <p>Description: 当在修改服务功能时，未有找到正确的服务功能代码则抛出此异常</p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: Maywide Tech Ltd.</p>
 * @author not attributable
 * @version 1.0
 */
@SuppressWarnings("serial")
public class InvalidServfuncCode extends Exception {
  public InvalidServfuncCode() {
    super();
  }

  public InvalidServfuncCode(String msg) {
    super(msg);
  }
}
