package com.omni.component.hibernate;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.service.ServiceRegistryBuilder;

public final class HibernateUtil {

	private  final static SessionFactory sessionFactory = buildSessionFactory();
	
	
	private final synchronized static SessionFactory buildSessionFactory() {
		
		try {
			
			System.out.println("Building Session Factory............");
			
			SessionFactory sFactory= null;
			
			Configuration configuration = new Configuration();
			
		    configuration.configure();
		    
		    final  ServiceRegistry serviceRegistry = new ServiceRegistryBuilder().applySettings(configuration.getProperties()).buildServiceRegistry();
		    
		    sFactory = configuration.buildSessionFactory(serviceRegistry);
		    
		    System.out.println("Created Session Factory.");
		    
		    return sFactory;

		} catch (Throwable ex) {
			
			System.err.println("Initial SessionFactory creation failed." + ex);
			
			throw new ExceptionInInitializerError(ex);
			
		}
		
	}
	
	/**
	 * This method returns the Application level instance of session factory
	 * @return SessionFactory
	 */
	public static SessionFactory getSessionFactory() {
		
		return sessionFactory;
		
	}

}
