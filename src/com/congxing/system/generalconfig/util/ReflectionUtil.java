package com.congxing.system.generalconfig.util;

import java.lang.reflect.Field;

public class ReflectionUtil {

	public static void main(String[] args) {
		Test test = new Test();
		ReflectionUtil.isUnSettedProperty(Test.class, "a");
	}

	public static boolean isUnSettedProperty(Class<?> clazz, String propertyName) {

		try {
			Field field = clazz.getDeclaredField(propertyName);
			if (field.getType().getName().equals("java.lang.String")) {
				System.out.println("ok");
			} else {
				System.out.println("dsfs");
			}

		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (NoSuchFieldException e) {
			e.printStackTrace();
		}
		return false;
	}

	static class Test {
		private String a;
		private String b;

		public Test() {
		}

		public Test(String a, String b) {
			super();
			this.a = a;
			this.b = b;
		}

		public String getA() {
			return a;
		}

		public void setA(String a) {
			this.a = a;
		}

		public String getB() {
			return b;
		}

		public void setB(String b) {
			this.b = b;
		}
	}

}
