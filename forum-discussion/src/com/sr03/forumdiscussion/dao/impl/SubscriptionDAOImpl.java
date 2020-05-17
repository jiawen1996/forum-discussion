package com.sr03.forumdiscussion.dao.impl;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import com.sr03.forumdiscussion.dao.ISubscriptionDAO;
import com.sr03.forumdiscussion.model.Forum;
import com.sr03.forumdiscussion.model.Subscription;

public class SubscriptionDAOImpl implements ISubscriptionDAO<Subscription>{
	private static SessionFactory factory = new Configuration().configure().buildSessionFactory();
	private static String _query = "from Subscription"; // for findAll static Method
	
	@Override
	public Integer _insert(Integer idUser, Integer idForum) {
		Session session = factory.openSession();
		Transaction tx = null;
		Integer SubscriptionId = null;
		try {
			tx = session.beginTransaction();
			Subscription newSubscription = new Subscription(idUser, idForum);
			SubscriptionId = (Integer) session.save(newSubscription);
			tx.commit();
		} catch (HibernateException e) {
			if (tx != null)
				tx.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}
		return SubscriptionId;
	}

	@Override
	public void _update(Subscription s) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void _delete(Subscription s) {
		// TODO Auto-generated method stub
		
	}

	

}
