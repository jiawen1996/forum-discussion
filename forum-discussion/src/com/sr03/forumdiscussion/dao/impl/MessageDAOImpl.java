package com.sr03.forumdiscussion.dao.impl;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import com.sr03.forumdiscussion.dao.IMessageDAO;
import com.sr03.forumdiscussion.model.*;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;

public class MessageDAOImpl implements IMessageDAO<Message> {
	private static SessionFactory factory = new Configuration().configure().buildSessionFactory();
	private static String _query = "from Forum"; // for findAll static Method

	@Override
	public MessageId _insert(String content, User editor, Forum destination) {
		Session session = factory.openSession();
		Transaction tx = null;
		MessageId messageId = null;
		try {
			tx = session.beginTransaction();
			Message newMesaage = new Message(content, editor, destination);
			messageId = (MessageId) session.save(newMesaage);
			tx.commit();
		} catch (HibernateException e) {
			if (tx != null)
				tx.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}
		return messageId;
	}

	@Override
	public void _update(Message f) {
		// TODO Auto-generated method stub

	}

	@Override
	public void _delete(Message f) {
		// TODO Auto-generated method stub

	}

	public static List<Message> FindAllByForum(Forum forum) throws IOException, ClassNotFoundException, SQLException {
		Session session = factory.openSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			String hql = "from Message where destination = ?";
			Query query = session.createQuery(hql, Message.class);
			query.setParameter(0, forum);
			List<Message> listMessages = query.getResultList();
			tx.commit();
			return listMessages;
		} catch (HibernateException e) {
			if (tx != null)
				tx.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}
		return null;
	}

}
