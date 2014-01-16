package com.wizecommerce.cts.utils;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import java.util.Iterator;

/**
 * This class will handle transactions with local DB using hibernate
 * @author panand
 */
public class Hibernate {
	static SessionFactory factory;
	Session session = null;
	Transaction tx = null;
	
	@SuppressWarnings("deprecation")
	public Hibernate() throws HibernateException {
			factory = new Configuration().configure().buildSessionFactory();
			session = factory.openSession();
	}
	
	
	public Iterator<?> executeSelectQuery(String query, boolean commit) throws HibernateException{
		
		this.tx = session.beginTransaction();
		Iterator<?> records = session.createQuery(query).list().iterator();
		if(commit)
			session.getTransaction().commit();
		
		return records;
	}
	
	public Iterator<?> executeSelectQuery(String query, int firstResult, int maxLimit) throws HibernateException{
		
		this.tx = session.beginTransaction();
		Iterator<?> records = session.createQuery(query).setFirstResult(firstResult).setMaxResults(maxLimit).list().iterator();
		session.getTransaction().commit();
		return records;
	}
	
	public void executeInsertQuery(Object className) throws HibernateException{
		
		this.tx = session.beginTransaction();
		session.save(className);
		session.getTransaction().commit();
	}
	
	public int executeUpdateQuery(String query) {
		this.tx = session.beginTransaction();
		Query query_ = session.createQuery(query);
		int result = query_.executeUpdate();
		session.getTransaction().commit();
		return result;
	}

	public void terminateSession(){
		if(session.isConnected())
			session.close();
		if(!factory.isClosed())
			factory.close();
	}
}
