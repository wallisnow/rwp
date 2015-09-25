/**
 *
 */
package com.congxing.core.model;

import java.io.Serializable;

/**
 * <p>Title: CommonResultObject</p>
 *
 * <p>Description: CommonResultObject</p>
 *
 * <p>Copyright: Copyright (c) Revenco</p>
 *
 * <p>Company: Sunrise Tech Ltd.</p>
 *
 * @author laiweilong
 *
 * @version 1.0
 *
 * @since 2010-1-27
 */
public class CommonResultObject implements Serializable {

	private static final long serialVersionUID = -1889147909652410023L;

	public static final int SUCCESS = 1;

	public static final int FAIL = 2;

	public static final int EXCEPTION = 3;

	protected int resultCode = -1;

	protected Object resultInstance;

	protected Message message;

	public CommonResultObject(int code, String info, Object instance) {
		super();
		this.resultCode = code;
		this.resultInstance = instance;
		setMessage(info);
	}

	public CommonResultObject(int code, String info) {
		this(code, info, null);
	}

	public CommonResultObject(int code) {
		this(code, null, null);
	}

	public CommonResultObject() {
		super();
	}

	public void setResult(int code, String info, Object instance) {
		this.resultCode = code;
		this.resultInstance = instance;
		setMessage(info);
	}

	public void setResult(int code, String info) {
		this.resultCode = code;
		setMessage(info);
	}

	public void setResult(int code) {
		this.resultCode = code;
	}

	public boolean isSuccess(){
		return resultCode == SUCCESS;
	}

	public boolean isFail(){
		return resultCode == FAIL;
	}

	public boolean isException(){
		return resultCode == EXCEPTION;
	}

	public int getResultCode() {
		return resultCode;
	}

	public void setResultCode(int resultCode) {
		this.resultCode = resultCode;
	}

	public Object getResultInstance() {
		return resultInstance;
	}

	public void setResultInstance(Object resultInstance) {
		this.resultInstance = resultInstance;
	}

	public String getResultString() {
		return this.message != null ? this.message.getKey() : null;
	}

	public Message getMessage() {
		return message;
	}

	public void setMessage(Message message) {
		this.message = message;
	}

	public void setMessage(String key) {
		if(key == null){
			this.message = null;
		} else {
			setMessage(new Message(key));
		}
	}

	public void setMessage(String key, Object[] arguments) {
		setMessage(new Message(key, arguments));
	}

	public void setMessage(String key, Object arg0) {
		setMessage(key, new Object[] { arg0 });
	}

	public void setMessage(String key, Object arg0, Object arg1) {
		setMessage(key, new Object[] { arg0, arg1 });
	}

	public void setMessage(String key, Object arg0, Object arg1, Object arg2) {
		setMessage(key, new Object[] { arg0, arg1, arg2 });
	}

	public void setMessage(String key, Object arg0, Object arg1, Object arg2,
			Object arg3) {
		setMessage(key, new Object[] { arg0, arg1, arg2, arg3 });
	}

}
