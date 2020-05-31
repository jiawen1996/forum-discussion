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
	private static String _query = "from Message"; // for findAll static Method

	@Override
	public MessageId _insert(String content, Integer idUser, Integer idForum) {
		Session session = factory.openSession();
		Transaction tx = null;
		MessageId messageId = null;
		try {
			tx = session.beginTransaction();
			User user = session.load(User.class, idUser);
			Forum forum = session.load(Forum.class, idForum);
			Message newMesaage = new Message(content, user, forum);
			MessageId id = new MessageId(idForum);
			newMesaage.setMessageId(id);
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

	}

	@Override
	public void _delete(Message m) {
		Session session = factory.openSession();
		Transaction tx = null;

		try {
			tx = session.beginTransaction();
			int msgId = m.getMessageId().getId();
			Message message = (Message) session.get(Message.class, msgId);

			session.delete(message);
			tx.commit();
		} catch (HibernateException e) {
			if (tx != null)
				tx.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}
		return;
	}

	public static List<Message> FindAllByForum(Forum forum) throws IOException, ClassNotFoundException, SQLException {
		Session session = factory.openSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			
			String hql = "from Message where destination = ?";
			Query<Message> query = session.createQuery(hql, Message.class);
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
