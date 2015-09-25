package com.congxing.system.attach.dao;

import com.congxing.core.hibernate.DAOFactory;
import com.congxing.core.hibernate.HibernateDAO;
import com.congxing.core.hibernate.Strategy;
import com.congxing.system.attach.model.AttachVO;

@SuppressWarnings("serial")
public class DeleteAttachStrategy implements Strategy {
	
	public Class<?> entityClass;
	private Object entityVO;
	private String attachId;
	
	public DeleteAttachStrategy(Class<?> entityClass, Object entityVO, String attachId) {
		super();
		this.entityClass = entityClass;
		this.entityVO = entityVO;
		this.attachId = attachId;
	}
	

	public Object process() throws Exception {
		HibernateDAO entityDAO = DAOFactory.getInstance().createHibernateDAO(entityClass);
		HibernateDAO attachDAO = DAOFactory.getInstance().createHibernateDAO(AttachVO.class);
		entityDAO.saveOrUpdate(entityVO);
		attachDAO.deleteByPK(attachId);
		return null;
	}
	
	public Object getEntityVO() {
		return entityVO;
	}
	public void setEntityVO(Object entityVO) {
		this.entityVO = entityVO;
	}
	public String getAttachId() {
		return attachId;
	}
	public void setAttachId(String attachId) {
		this.attachId = attachId;
	}
	public Class<?> getEntityClass() {
		return entityClass;
	}
	public void setEntityClass(Class<?> entityClass) {
		this.entityClass = entityClass;
	}

}
