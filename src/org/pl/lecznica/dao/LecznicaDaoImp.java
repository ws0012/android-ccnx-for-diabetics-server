/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.pl.lecznica.dao;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.pl.lecznica.Wynik;
import org.pl.lecznica.db.NewHibernateUtil;


/**
 *
 * @author Wojtek
 */
public class LecznicaDaoImp {
    
    protected final Session getCurrentSession(){
       return NewHibernateUtil.currentSession();
       }
    
    public List get2Property(Class persistentClass, String propertyName, Object propertyValue) {
        Criteria cr = getCurrentSession().createCriteria(persistentClass);
        cr.add(Restrictions.eq(propertyName, propertyValue));
        return cr.list();    
    }
 
    public List get2PropertyList(Class persistentClass, List <Entry<String, Object>> propertyList) {
        
        Criteria cr = getCurrentSession().createCriteria(persistentClass);
        for(Entry<String, Object> entry : propertyList) {
            if(persistentClass.equals(Wynik.class) && entry.getKey().equals("dataBadania"))
                cr.add(Restrictions.ge("dataBadania", (Date)entry.getValue()));
            else
                cr.add(Restrictions.eq(entry.getKey(), entry.getValue()));
        }
        return cr.list();    
    }
    
    public List getList(Class persistentClass, int limit) {
        Criteria ct = getCurrentSession().createCriteria(persistentClass);
/*        if(persistentClass.equals(Kontakt.class))
            ct.add( Restrictions.eq("status", true));
*/
        ct.addOrder(Order.asc("id"));
        
        if(limit > 0)
            ct.setMaxResults(limit);

        return ct.list();
    }
    
    public List saveList(List list) {
        Transaction tx = null;
        List result = new ArrayList();
        try{
            tx = getCurrentSession().beginTransaction();
            for(Object t : list){
                Object res = (Object) getCurrentSession().save(t); 
                result.add(res);
            }
            tx.commit();
            return result;
        } catch (HibernateException e) {
            System.err.println(e.toString());
	    if (tx != null) tx.rollback();
	    throw e; 
        }
    }

    public List mergeList(List list) {
        Transaction tx = null;
        List result = new ArrayList();
        try{
            tx = getCurrentSession().beginTransaction();
            for(Object t : list){
                Object res = (Object) getCurrentSession().merge(t); 
                result.add(res);
            }
            tx.commit();
            return result;
        } catch (HibernateException e) {
            System.err.println(e.toString());
	    if (tx != null) tx.rollback();
	    throw e; 
        }
    }
    
    public Object save(Object t) {
        Object res;
        Transaction tx = null;  
        try {
            tx = getCurrentSession().beginTransaction();
            res = (Object) getCurrentSession().save(t);
            tx.commit();
            return res;
        } catch (HibernateException e) {
            System.err.println(e.toString());
	    if (tx != null) tx.rollback();
	    throw e; 
        }
            
    }
    public Object merge(Object t) {
        Object res;
        Transaction tx = null; 
        try {
            tx = getCurrentSession().beginTransaction();
            res = (Object) getCurrentSession().merge(t);
            tx.commit();
            return res;
        } catch (HibernateException e) {
            System.err.println(e.toString());
	    if (tx != null) tx.rollback();
	    throw e; 
        }
    }
    public void remove(Object t) {
        Transaction tx = null; 
        try {
            tx = getCurrentSession().beginTransaction();
            getCurrentSession().delete(t);
            tx.commit();
	} catch (HibernateException e) {
            System.err.println(e.toString());
	    if (tx != null) tx.rollback();
	    throw e; 
            }
    }
}
