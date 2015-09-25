package com.congxing.core.web.taglib.component;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.struts2.components.Form;
import org.apache.struts2.components.TextField;
import org.apache.struts2.util.TextProviderHelper;

import com.congxing.core.utils.DateFormatFactory;
import com.congxing.core.web.translate.TranslateUtils;
import com.opensymphony.xwork2.util.ValueStack;

public class CommonTextFieldComponent extends TextField {
	
	static Logger logger = Logger.getLogger(TranslateUtils.class);
	
	private String format;
	private String length;

	public CommonTextFieldComponent(ValueStack stack, HttpServletRequest request, HttpServletResponse response) {
		super(stack, request, response);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public void evaluateParams() {
		addParameter("templateDir", getTemplateDir());
        addParameter("theme", getTheme());
        addParameter("dynamicAttributes", dynamicAttributes);

        String name = null;
        String providedLabel = null;

        if (this.key != null) {

            if(this.name == null) {
                this.name = key;
            }

            if(this.label == null) {
                // lookup the label from a TextProvider (default value is the key)
                providedLabel = TextProviderHelper.getText(key, key, stack);
            }

        }

        if (this.name != null) {
            name = findString(this.name);
            addParameter("name", name);
        }

        if (label != null) {
            addParameter("label", findString(label));
        } else {
            if (providedLabel != null) {
                // label found via a TextProvider
                addParameter("label", providedLabel);
            }
        }

        if (labelSeparator != null) {
            addParameter("labelseparator", findString(labelSeparator));
        }

        if (labelPosition != null) {
            addParameter("labelposition", findString(labelPosition));
        }

        if (requiredPosition != null) {
            addParameter("requiredPosition", findString(requiredPosition));
        }

        if (errorPosition != null) {
            addParameter("errorposition", findString(errorPosition));
        }
        
        if (requiredLabel != null) {
            addParameter("required", findValue(requiredLabel, Boolean.class));
        }

        if (disabled != null) {
            addParameter("disabled", findValue(disabled, Boolean.class));
        }

        if (tabindex != null) {
            addParameter("tabindex", findString(tabindex));
        }

        if (onclick != null) {
            addParameter("onclick", findString(onclick));
        }

        if (ondblclick != null) {
            addParameter("ondblclick", findString(ondblclick));
        }

        if (onmousedown != null) {
            addParameter("onmousedown", findString(onmousedown));
        }

        if (onmouseup != null) {
            addParameter("onmouseup", findString(onmouseup));
        }

        if (onmouseover != null) {
            addParameter("onmouseover", findString(onmouseover));
        }

        if (onmousemove != null) {
            addParameter("onmousemove", findString(onmousemove));
        }

        if (onmouseout != null) {
            addParameter("onmouseout", findString(onmouseout));
        }

        if (onfocus != null) {
            addParameter("onfocus", findString(onfocus));
        }

        if (onblur != null) {
            addParameter("onblur", findString(onblur));
        }

        if (onkeypress != null) {
            addParameter("onkeypress", findString(onkeypress));
        }

        if (onkeydown != null) {
            addParameter("onkeydown", findString(onkeydown));
        }

        if (onkeyup != null) {
            addParameter("onkeyup", findString(onkeyup));
        }

        if (onselect != null) {
            addParameter("onselect", findString(onselect));
        }

        if (onchange != null) {
            addParameter("onchange", findString(onchange));
        }

        if (accesskey != null) {
            addParameter("accesskey", findString(accesskey));
        }

        if (cssClass != null) {
            addParameter("cssClass", findString(cssClass));
        }

        if (cssStyle != null) {
            addParameter("cssStyle", findString(cssStyle));
        }

        if (cssErrorClass != null) {
            addParameter("cssErrorClass", findString(cssErrorClass));
        }

        if (cssErrorStyle != null) {
            addParameter("cssErrorStyle", findString(cssErrorStyle));
        }

        if (title != null) {
            addParameter("title", findString(title));
        }


        // see if the value was specified as a parameter already
        if (parameters.containsKey("value")) {
            parameters.put("nameValue", parameters.get("value"));
        } else {
            if (evaluateNameValue()) {
                final Class valueClazz = getValueClassType();

                if (valueClazz != null) {
                    if (value != null) {
                    	String nameValue = (String) findValue(value, valueClazz);
                    	if(StringUtils.isNotBlank(format) && StringUtils.isNotBlank(nameValue) && nameValue.trim().length() == 19 ){
                    		try{
	                    		Date date = DateFormatFactory.getDefaultFormat().parse(nameValue);
	                    		if(null != date){
	                    			this.addParameter("nameValue", DateFormatFactory.getInstance(format).format(date));
	                    		}
                    		}catch(Exception ex){
                    			logger.error("The formt[" + format + "] for value[" + nameValue + "] is error", ex);
                    		}
                    	}else if(StringUtils.isNotBlank(length) && StringUtils.isNotBlank(nameValue) && nameValue.trim().length() == 19 ){
                    		try{
                            	if(StringUtils.isNotBlank(length) && StringUtils.isNotBlank(nameValue)){
                            		this.addParameter("nameValue", nameValue.trim().subSequence(0, Integer.parseInt(length)));
                            	}
                        	}catch(Exception ex){
                        		logger.error("The length[" + length + "] for value[" + nameValue + "] is error", ex);
                        	}
                    	}else{ 
                    		addParameter("nameValue", nameValue);
                    	}
                    } else if (name != null) {
                        String expr = completeExpressionIfAltSyntax(name);

                        addParameter("nameValue", findValue(expr, valueClazz));
                    }
                } else {
                    if (value != null) {
                        addParameter("nameValue", findValue(value));
                    } else if (name != null) {
                        addParameter("nameValue", findValue(name));
                    }
                }
            }
        }

        final Form form = (Form) findAncestor(Form.class);

        // create HTML id element
        populateComponentHtmlId(form);

        if (form != null ) {
            addParameter("form", form.getParameters());

            if ( name != null ) {
                // list should have been created by the form component
                List<String> tags = (List<String>) form.getParameters().get("tagNames");
                tags.add(name);
            }
        }


        // tooltip & tooltipConfig
        if (tooltipConfig != null) {
            addParameter("tooltipConfig", findValue(tooltipConfig));
        }
        if (tooltip != null) {
            addParameter("tooltip", findString(tooltip));

            Map tooltipConfigMap = getTooltipConfig(this);

            if (form != null) { // inform the containing form that we need tooltip javascript included
                form.addParameter("hasTooltip", Boolean.TRUE);

                // tooltipConfig defined in component itseilf will take precedence
                // over those defined in the containing form
                Map overallTooltipConfigMap = getTooltipConfig(form);
                overallTooltipConfigMap.putAll(tooltipConfigMap); // override parent form's tooltip config

                for (Object o : overallTooltipConfigMap.entrySet()) {
                    Map.Entry entry = (Map.Entry) o;
                    addParameter((String) entry.getKey(), entry.getValue());
                }
            }
            else {
                //if (LOG.isWarnEnabled()) {
                //    LOG.warn("No ancestor Form found, javascript based tooltip will not work, however standard HTML tooltip using alt and title attribute will still work ");
                //}
            }

            //TODO: this is to keep backward compatibility, remove once when tooltipConfig is dropped
            String  jsTooltipEnabled = (String) getParameters().get("jsTooltipEnabled");
            if (jsTooltipEnabled != null)
                this.javascriptTooltip = jsTooltipEnabled;

            //TODO: this is to keep backward compatibility, remove once when tooltipConfig is dropped
            String tooltipIcon = (String) getParameters().get("tooltipIcon");
            if (tooltipIcon != null)
                this.addParameter("tooltipIconPath", tooltipIcon);
            if (this.tooltipIconPath != null)
                this.addParameter("tooltipIconPath", findString(this.tooltipIconPath));

            //TODO: this is to keep backward compatibility, remove once when tooltipConfig is dropped
            String tooltipDelayParam = (String) getParameters().get("tooltipDelay");
            if (tooltipDelayParam != null)
                this.addParameter("tooltipDelay", tooltipDelayParam);
            if (this.tooltipDelay != null)
                this.addParameter("tooltipDelay", findString(this.tooltipDelay));

            if (this.javascriptTooltip != null) {
                Boolean jsTooltips = (Boolean) findValue(this.javascriptTooltip, Boolean.class);
                //TODO use a Boolean model when tooltipConfig is dropped
                this.addParameter("jsTooltipEnabled", jsTooltips.toString());

                if (form != null)
                    form.addParameter("hasTooltip", jsTooltips);
                if (this.tooltipCssClass != null)
                    this.addParameter("tooltipCssClass", findString(this.tooltipCssClass));
            }


        }

        evaluateExtraParams();
    }

	public String getFormat() {
		return format;
	}

	public void setFormat(String format) {
		this.format = format;
	}

	public String getLength() {
		return length;
	}

	public void setLength(String length) {
		this.length = length;
	}

}
