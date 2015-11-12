package com.congxing.core.utils;

import java.net.InetAddress;
import java.net.UnknownHostException;

import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.engine.spi.SessionFactoryImplementor;
import org.hibernate.engine.spi.SessionImplementor;
import org.hibernate.internal.CriteriaImpl;
import org.hibernate.loader.criteria.CriteriaJoinWalker;
import org.hibernate.loader.criteria.CriteriaQueryTranslator;
import org.hibernate.metadata.ClassMetadata;
import org.hibernate.persister.entity.AbstractEntityPersister;
import org.hibernate.persister.entity.OuterJoinLoadable;

public class LogInfoUtils {

	// 获取当前sql
	public static String getCurrentSql(Criteria criteria) {
		CriteriaImpl criteriaImpl = (CriteriaImpl) criteria;
		SessionImplementor session = criteriaImpl.getSession();
		SessionFactoryImplementor factory = session.getFactory();
		CriteriaQueryTranslator translator = new CriteriaQueryTranslator(factory, criteriaImpl,
				criteriaImpl.getEntityOrClassName(), CriteriaQueryTranslator.ROOT_SQL_ALIAS);
		String[] implementors = factory.getImplementors(criteriaImpl.getEntityOrClassName());

		CriteriaJoinWalker walker = new CriteriaJoinWalker(
				(OuterJoinLoadable) factory.getEntityPersister(implementors[0]), translator, factory, criteriaImpl,
				criteriaImpl.getEntityOrClassName(), session.getLoadQueryInfluencers());
		String sql = walker.getSQLString();
		return sql;
	}

	// 获取当前表名
	public static String getTableName(SessionFactory sessionFactory, String clazz) {
		ClassMetadata hibernateMetadata = sessionFactory.getClassMetadata(clazz);
		String tableName = null;
		if (hibernateMetadata == null) {
			return tableName;
		}

		if (hibernateMetadata instanceof AbstractEntityPersister) {
			AbstractEntityPersister persister = (AbstractEntityPersister) hibernateMetadata;
			tableName = persister.getTableName();
			// String[] columnNames = persister.getKeyColumnNames();
		}
		return tableName;
	}

	//获取当前IP
	public static String getUserIp() {
		String userIp = null;
		try {
			InetAddress ip = InetAddress.getLocalHost();
			userIp = ip.toString();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
		return userIp;
	}

}
