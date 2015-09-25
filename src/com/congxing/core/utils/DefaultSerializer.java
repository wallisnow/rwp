package com.congxing.core.utils;

import java.lang.reflect.Type;

import com.congxing.core.utils.DateFormatFactory;
import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

public class DefaultSerializer<T> implements JsonSerializer<T>{

	public JsonElement serialize(T src, Type typeOfSrc, JsonSerializationContext context) {
		String tName = src.getClass().getName();
		if("java.lang.Class".equalsIgnoreCase(tName)){
			return new JsonPrimitive(((Class<?>)src).getName());
		}else if(src instanceof java.util.Date){
			return new JsonPrimitive(DateFormatFactory.getDefaultFormat().format((java.util.Date)src));
		}else{
			return new JsonPrimitive(String.valueOf(src));
		}
	}

}
