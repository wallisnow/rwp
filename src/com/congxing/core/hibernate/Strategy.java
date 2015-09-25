package com.congxing.core.hibernate;

import java.io.Serializable;


public interface Strategy extends Serializable{
	
	public Object process()throws Exception;

}
