package com.congxing.core.web.control;

import java.io.IOException;

import org.springframework.core.type.ClassMetadata;
import org.springframework.core.type.classreading.MetadataReader;
import org.springframework.core.type.classreading.MetadataReaderFactory;
import org.springframework.core.type.filter.TypeFilter;

import com.congxing.core.web.struts2.BaseAction;

public class ActionTypeFilter implements TypeFilter {

	public boolean match(MetadataReader metadataReader, MetadataReaderFactory metadataReaderFactory) throws IOException {
		ClassMetadata metadata = metadataReader.getClassMetadata();
		String className = metadata.getClassName();
		try {
			Class<?> currentClass = Class.forName(className);
			if(BaseAction.class.isAssignableFrom(currentClass)){
				ActionNamespaceMap.addNamespaceByActionName(className);
			}
		} catch (ClassNotFoundException ex) {
			ex.printStackTrace();
		}
		return false;
	}

}
