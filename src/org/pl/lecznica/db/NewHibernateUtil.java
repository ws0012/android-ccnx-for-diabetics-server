/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.pl.lecznica.db;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.cfg.AnnotationConfiguration;
import org.hibernate.SessionFactory;

/**
 * Hibernate Utility class with a convenient method to get Session Factory
 * object.
 *
 * @author Wojtek
 */
public class NewHibernateUtil {

    private static final SessionFactory sessionFactory;
    public static final ThreadLocal SESSION = new ThreadLocal();
    
    static {
        try {
            // Create the SessionFactory from standard (hibernate.cfg.xml) 
            // config file.
            AnnotationConfiguration configuration = new AnnotationConfiguration().configure("/org/pl/lecznica/db/hibernate.cfg.xml");
            sessionFactory = configuration.buildSessionFactory();
        } catch (Throwable ex) {
            // Log the exception. 
            System.err.println("Initial SessionFactory creation failed." + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }

    public static Session currentSession() throws HibernateException {
        Session s = (Session)SESSION.get();
        if (s == null) {
            s = sessionFactory.openSession();
            SESSION.set(s);
        }
        return s;
    }
    public static void closeSession() throws HibernateException {
        Session s = (Session)SESSION.get();
        SESSION.set(null);
        if (s != null) s.close();
    }
    
    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }
}
