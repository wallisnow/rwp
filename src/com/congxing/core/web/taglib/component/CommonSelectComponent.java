package com.congxing.core.web.taglib.component;

import java.lang.reflect.Array;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.struts2.components.Select;
import org.apache.struts2.util.MakeIterator;

import com.congxing.core.web.translate.TranslateUtils;
import com.congxing.core.web.translate.TranslateUtilsFactory;
import com.congxing.core.web.translate.TranslateVO;
import com.opensymphony.xwork2.util.ValueStack;

public class CommonSelectComponent extends Select{
	
	static Logger logger = Logger.getLogger(TranslateUtils.class);

	public CommonSelectComponent(ValueStack stack, HttpServletRequest request, HttpServletResponse response) {
		super(stack, request, response);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public void evaluateExtraParams() {
        Object value = null;

        if (list == null) {
            list = parameters.get("list");
        }

        if (list instanceof String) {
        	String definition = (String) list;
        	if (StringUtils.isNotBlank(definition) 
        			&& (definition.startsWith(TranslateUtils.SYSCODE_PREFIX) || definition.startsWith(TranslateUtils.REFLECT_PREFIX))
        			&& definition.indexOf(TranslateUtils.BRACES) < 0){
        		this.setListKey("code");
        		this.setListValue("name");
        		List<TranslateVO> translateList = this.getOutputMessage();
        		if("true".equalsIgnoreCase(this.emptyOption)){
        			TranslateVO translateVO = new TranslateVO();
        			if(StringUtils.isNotBlank(this.headerKey)){
        				translateVO.setCode(headerKey);
        			}else{
        				translateVO.setCode("");
        			}
        			if(StringUtils.isNotBlank(this.headerValue)){
        				translateVO.setName(headerValue);
        			}else{
        				translateVO.setName("请选择");
        			}
        			translateList.add(0, translateVO);
        		}
    			value = translateList;
    		}else{
    			 value = findValue(definition);
    		}
        } else if (list instanceof Collection) {
            value = list;
        } else if (MakeIterator.isIterable(list)) {
            value = MakeIterator.convert(list);
        }
        if (value == null) {
            if (throwExceptionOnNullValueAttribute) {
                // will throw an exception if not found
                value = findValue((list == null) ? (String) list : list.toString(), "list",
                    "The requested list key '" + list + "' could not be resolved as a collection/array/map/enumeration/iterator type. " +
                    "Example: people or people.{name}");
            }
            else {
                // ww-1010, allows value with null value to be compatible with ww
                // 2.1.7 behaviour
                value = findValue((list == null)?(String) list:list.toString());
            }
        }

        if (value instanceof Collection) {
            addParameter("list", value);
        } else {
            addParameter("list", MakeIterator.convert(value));
        }

        if (value instanceof Collection) {
            addParameter("listSize", Integer.valueOf(((Collection) value).size()));
        } else if (value instanceof Map) {
            addParameter("listSize", Integer.valueOf(((Map) value).size()));
        } else if (value != null && value.getClass().isArray()) {
            addParameter("listSize", Integer.valueOf(Array.getLength(value)));
        }

        if (listKey != null) {
        	listKey = stripExpressionIfAltSyntax(listKey);
            addParameter("listKey", listKey);
        } else if (value instanceof Map) {
            addParameter("listKey", "key");
        }

        if (listValue != null) {
        	listValue = stripExpressionIfAltSyntax(listValue);
            addParameter("listValue", listValue);
        } else if (value instanceof Map) {
            addParameter("listValue", "value");
        }
    }
	
	public List<TranslateVO> getOutputMessage(){
		try{
			TranslateUtils translateUtils = TranslateUtilsFactory.getInstance();
			if(null == translateUtils) {
				logger.error("TranslateUtils[definition=" + list + "]");
			}
			return translateUtils.getCollection((String)list);
		}catch(Exception ex){
			logger.error("TranslateUtils[definition=" + list + "]");
			logger.error(ex.getMessage());
		}
		return null;
	}

}
