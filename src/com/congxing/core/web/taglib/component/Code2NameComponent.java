package com.congxing.core.web.taglib.component;

import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.struts2.components.Component;

import com.congxing.core.web.translate.TranslateUtils;
import com.congxing.core.web.translate.TranslateUtilsFactory;
import com.opensymphony.xwork2.util.ValueStack;


public class Code2NameComponent extends Component{
	
	static Logger logger = Logger.getLogger(TranslateUtils.class);
	
	private String definition;
	private Object value;
	private Map<String, Object> params;

	public Code2NameComponent(ValueStack stack) {
		super(stack);
	}
	
	@Override
	public boolean start(Writer writer) {
		boolean result = super.start(writer); 
		try {
			String targetName = this.getOutputMessage();
			if(StringUtils.isNotBlank(targetName)){
				writer.write(targetName);
			}
		}catch(Exception ex){
			ex.printStackTrace();
		}
        return result;
    }
	
	public String getOutputMessage(){
		String targetName = "";
		try{
			TranslateUtils tu = TranslateUtilsFactory.getInstance();
			if(null == tu) {
				targetName = String.valueOf(this.getValue());
			}else{
				targetName = tu.getName(definition, value, params);
			}
		}catch(Exception ex){
			logger.error("TranslateUtils[definition=" + definition + "|value=" + value + "]", ex);
			logger.error(ex.getMessage());
			targetName = String.valueOf(this.getValue());
		}
		return targetName;
	}

	public String getDefinition() {
		return definition;
	}

	public void setDefinition(String definition) {
		this.definition = definition;
	}

	public Object getValue() {
		return value;
	}

	public void setValue(Object value) {
		this.value = value;
	}

	public Map<String, Object> getParams() {
		return params;
	}

	public void setParams(String params) {
		
		if(StringUtils.isNotBlank(params)){
			String []paramsArray = StringUtils.split(params, "\\|");
			Map<String, Object> inParams = new HashMap<String, Object>();
			for(String param : paramsArray){
				String []inParam = StringUtils.split(param, "=");
				if(inParam.length == 2 && StringUtils.isNotBlank(inParam[0]) && StringUtils.isNotBlank(inParam[1])){
					inParams.put(StringUtils.trim(inParam[0]), StringUtils.trim(inParam[1]));
				}
			}
			this.params = inParams;
		}
	}

}
