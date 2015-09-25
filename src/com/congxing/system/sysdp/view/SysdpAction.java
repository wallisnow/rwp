/**
 * auto-generated code
 * 2011-10-17 00:51:05
 */
package com.congxing.system.sysdp.view;

import com.congxing.core.utils.ParamsBuilder;
import com.congxing.core.utils.Struts2Utils;
import com.congxing.core.web.struts2.BaseAction;
import com.congxing.core.web.tree.TreeBuilder;
import com.congxing.system.sysdp.model.SysDpVO;
import com.opensymphony.xwork2.ActionSupport;


@SuppressWarnings("serial")
public class SysdpAction extends BaseAction {
    
    public SysdpAction() {
        this.voClass = SysDpVO.class;
        this.pkNameArray = new String[] { "dpId" };
    }

    public String tree(){
		try{
			paramsMap = ParamsBuilder.buildMapFromHttpRequest();
			SysDpListVO listVO = new SysDpListVO();
			this.setParamsMapToTargetObject(paramsMap, listVO);
			listVO.setFindAll(true);
			listVO.setOrderBy("orderNo");
			page = this.getService().doQuery(voClass, listVO);
			
			String nodeIdProperty = "dpId";
			String parentNodeIdProperty = "parentDpId";
			String nodeNameProperty = "dpName";
			String pkNameProperty = "dpId";
			
			StringBuffer treeXML = TreeBuilder.buildTreeXML(page.getDatas(), nodeIdProperty, nodeNameProperty, parentNodeIdProperty, pkNameProperty, null);
			Struts2Utils.getRequest().setAttribute("treeXML", treeXML.toString());
			return ActionSupport.SUCCESS;
		}catch(Exception ex){
			ex.printStackTrace();
			this.addErrorMessage(ex.getMessage());
			return ActionSupport.ERROR;
		}
	}
    
    public String nttree(){
    	return this.tree();
    }
    
    public String select(){
    	return this.list();
    }
    
}

