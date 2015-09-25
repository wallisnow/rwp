package com.congxing.core.web.taglib.component;

import java.io.Writer;
import java.util.Map;

import org.apache.struts2.components.Component;

import com.opensymphony.xwork2.util.ValueStack;


public class CommonMapComponent extends Component {
	
	private Map<String, Object> map;
	private String key;

	public CommonMapComponent(ValueStack stack) {
		super(stack);
	}
	
	@Override
	public boolean start(Writer writer) {
		boolean result = super.start(writer); 
		try {
			if(null != map && null != key && !"".equals(key.trim())){
				if(map.containsKey(key)){
					writer.write(String.valueOf(map.get(key)));
				}
			}
		}catch(Exception ex){
			ex.printStackTrace();
		}
        return result;
    }
	
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public Map<String, Object> getMap() {
		return map;
	}
	public void setMap(Map<String, Object> map) {
		this.map = map;
	}	
}
