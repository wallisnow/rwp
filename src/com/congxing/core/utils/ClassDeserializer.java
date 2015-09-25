package com.congxing.core.utils;

import java.lang.reflect.Type;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

public class ClassDeserializer implements JsonDeserializer<Class<?>>{

	public Class<?> deserialize(JsonElement json, Type arg1, JsonDeserializationContext arg2) throws JsonParseException {
		try {
			return Class.forName(json.getAsJsonPrimitive().getAsString());
		} catch (ClassNotFoundException ex) {
			ex.printStackTrace();
			throw new JsonParseException("数据转换对象出错,出错数据[" + json.getAsJsonPrimitive().getAsString() + "], 出错信息[" + ex.getMessage() + "]");
		}
	}



}
