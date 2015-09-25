package com.congxing.core.web.taglib.component;

import java.io.Writer;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.components.Component;

import com.congxing.core.web.constant.Constants;
import com.opensymphony.xwork2.util.ValueStack;


public class MsgComponent extends Component {
	
	private HttpServletRequest request;
	
	private String key;
	
	private static final String ERROR_MSG_HTML =  "<span style=\"background-color: #E92538; color:white; font-weight: bold; text-align:left; margin-left:20px;\"><font font-size=\"12px\" font-family=\"Arial, sans-serif\">#message#</font></span>";
	private static final String SUCCESS_MSG_HTML = "<span style=\"background-color: #2E982E; color:white; font-weight: bold; text-align:left; margin-left:20px;\"><font font-size=\"12px\" font-family=\"Arial, sans-serif\">#message#</font></span>";

	public MsgComponent(ValueStack stack, HttpServletRequest request) {
		super(stack);
		this.request = request;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public boolean start(Writer writer) {
		boolean result = super.start(writer); 
		try {
			if(StringUtils.isNotBlank(key)){
				String message = (String)request.getAttribute(key);
				if(StringUtils.isNotBlank(message)){
					writer.write(message);
				}
				return true;
			}
			/**
			 * 向页面输出错误信息
			 */
			List<String> errorMsgList = (List<String>)request.getAttribute(Constants.ERROR_MESSAGE);
			if(null != errorMsgList && errorMsgList.size() > 0){
				StringBuffer errorMsgBuf = new StringBuffer("");
				for(String errorMsg : errorMsgList){
					errorMsgBuf.append(StringUtils.replace(ERROR_MSG_HTML, "#message#", errorMsg));
				}
				writer.write(errorMsgBuf.toString());
			}
			/**
			 * 向页面输出正确信息
			 */
			List<String> successMsgList = (List<String>)request.getAttribute(Constants.DEFAULT_MESSAGE);
			if(null != successMsgList && successMsgList.size() > 0){
				StringBuffer successMsgBuf = new StringBuffer("");
				for(String successMsg : successMsgList){
					successMsgBuf.append(StringUtils.replace(SUCCESS_MSG_HTML, "#message#", successMsg));
				}
				writer.write(successMsgBuf.toString());
			}
		}catch(Exception ex){
			ex.printStackTrace();
		}
        return result;
    }

	public HttpServletRequest getRequest() {
		return request;
	}

	public void setRequest(HttpServletRequest request) {
		this.request = request;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

}
