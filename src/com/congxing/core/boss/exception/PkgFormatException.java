package com.congxing.core.boss.exception;

/**
 * <p>Title: 包结构异常</p>
 * <p>Description: 当从BOSS接口收到返回，解拆结构错误时抛出此异常</p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: Maywide Tech Ltd.</p>
 * @author not attributable
 * @version 1.0
 */
@SuppressWarnings("serial")
public class PkgFormatException extends Exception {
  public PkgFormatException() {
    super();
  }

  public PkgFormatException(String msg) {
    super(msg);
  }
}
