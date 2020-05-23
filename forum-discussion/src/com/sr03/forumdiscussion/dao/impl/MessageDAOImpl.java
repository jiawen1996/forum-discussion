package com.sr03.forumdiscussion.dao.impl;

import com.sr03.forumdiscussion.dao.IMessageDAO;
import com.sr03.forumdiscussion.model.*;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

public class MessageDAOImpl implements IMessageDAO<Message> {
	private static SessionFactory factory = new Configuration().configure().buildSessionFactory();
	private static String _query = "from Forum"; // for findAll static Method

	@Override
	public Integer _insert(String content, User editor, Forum destination) {
		Session session = factory.openSession();
		Transaction tx = null;
		Integer UserId = null;
		try {
			tx = session.beginTransaction();
			Message newMesaage = new Message(content, editor, destination);
			MessageId = (Integer) session.save(newMesaage);
			tx.commit();
		} catch (HibernateException e) {
			if (tx != null)
				tx.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}
		return UserId;
	}

	@Override
	public void _update(Message f) {
		// TODO Auto-generated method stub

	}

	@Override
	public void _delete(Message f) {
		// TODO Auto-generated method stub

	}

}
