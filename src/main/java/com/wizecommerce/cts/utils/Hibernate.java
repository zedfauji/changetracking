package com.wizecommerce.cts.utils;

import org.hibernate.HibernateException;
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
	}
	
	
	public Iterator<?> executeSelectQuery(String query) throws HibernateException{
		
		session = factory.openSession();
		tx = session.beginTransaction();
		Iterator<?> records = session.createQuery(query).setMaxResults(10).list().iterator();
		
		return records;
	}
	
	public Iterator<?> executeSelectQuery(String query, int firstResult, int maxLimit) throws HibernateException{
		
		session = factory.openSession();
		tx = session.beginTransaction();
		Iterator<?> records = session.createQuery(query).setFirstResult(firstResult).setMaxResults(maxLimit).list().iterator();
		
		return records;
	}
	
	public void executeInsertQuery(Object className) throws HibernateException{
		
		session = factory.openSession();
		tx = session.beginTransaction();
		session.save(className);
		session.getTransaction().commit();
	}
	
	public void terminateSession(){
		if(session.isConnected())
			session.close();
	}
}
