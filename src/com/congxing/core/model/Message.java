package com.congxing.core.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * <p>
 * Title: Message
 * </p>
 *
 * <p>
 * Description: Message
 * </p>
 *
 * <p>
 * Copyright: Copyright (c) Revenco
 * </p>
 *
 * <p>
 * Company: Sunrise Tech Ltd.
 * </p>
 *
 * @author Lai Weilong
 * @version 1.0
 * @since 2011-2-24
 *
 */
public class Message implements Serializable {

	private static final long serialVersionUID = -4263604435538982833L;

	private String key;

	private List<Object> arguments;

	public Message() {
		super();
	}

	public Message(String key) {
		super();
		this.key = key;
	}

	public Message(String key, Object[] arguments) {
		super();
		this.key = key;
		setArguments(arguments);
	}

	public Object[] getArguments() {
		if (this.arguments != null) {
			return arguments.toArray();
		} else {
			return null;
		}
	}

	public void setArguments(Object[] arguments) {
		if (arguments != null) {
			this.arguments = Arrays.asList(arguments);
		} else {
			this.arguments = null;
		}
	}

	public Message addArgument(Object argument) {
		if (argument != null) {
			if (this.arguments == null) {
				this.arguments = new ArrayList<Object>();
			}
			this.arguments.add(argument);
		}
		return this;
	}

	public Message removeArgument(Object argument) {
		if (argument != null && this.arguments != null) {
			this.arguments.remove(argument);
		}
		return this;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

}
