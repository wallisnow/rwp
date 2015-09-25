package com.congxing.system.test.model;

import java.io.Serializable;

public class TestVO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String testId;
	private String username;
	private String password;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getTestId() {
		return testId;
	}

	public void setTestId(String testId) {
		this.testId = testId;
	}

}
