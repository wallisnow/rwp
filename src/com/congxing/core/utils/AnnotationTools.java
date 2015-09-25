package com.congxing.core.utils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;


public class AnnotationTools {
	
	public static <T extends Annotation> T findActionAnnotation(Class<?> klass, Class<T> annotationClass) {
        T ann = klass.getAnnotation(annotationClass);
        while (ann == null && klass != null) {
            ann = klass.getAnnotation(annotationClass);
            if (ann == null)
                ann = klass.getPackage().getAnnotation(annotationClass);
            if (ann == null) {
                klass = klass.getSuperclass();
                if (klass != null ) {
                    ann = klass.getAnnotation(annotationClass);
                }
            }
        }
        return ann;
    }
	
	public static <T extends Annotation> T findMethodAnnotation(Method method, Class<T> annotationClass) {
		T ann = method.getAnnotation(annotationClass);
        return ann;
    }

}
