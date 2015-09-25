package com.congxing.core.hibernate;

import java.io.Serializable;

import org.hibernate.SessionFactory;


@SuppressWarnings("serial")
public class DAOFactory implements Serializable{
	
	public static DAOFactory instance;
	
	private SessionFactory sessionFactory;
	
	public DAOFactory(SessionFactory sessionFactory){
		this.sessionFactory = sessionFactory;
	}
	
	public static DAOFactory init(SessionFactory sessionFactory){
		if(null == instance){
			instance = new DAOFactory(sessionFactory);
		}
		return instance;
	}
	
	public static DAOFactory getInstance() {
		return instance;
	}

	public static void setInstance(DAOFactory instance) {
		DAOFactory.instance = instance;
	}

	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
	
	public HibernateDAO createHibernateDAO(Class<?> entityClass){
		return new HibernateDAO(sessionFactory, entityClass);
	}
	
}
