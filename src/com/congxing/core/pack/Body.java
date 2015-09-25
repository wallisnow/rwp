package com.congxing.core.pack;

import java.io.IOException;
import java.io.Serializable;


public interface Body extends Serializable{
	
	public void setBody(byte[] bytes) throws IOException;
	
	public byte[] getBytes() throws IOException;

}
