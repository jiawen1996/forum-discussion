package com.sr03.forumdiscussion.dao.impl;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;

import com.sr03.forumdiscussion.dao.IForumDAO;
import com.sr03.forumdiscussion.model.Forum;
import com.sr03.forumdiscussion.model.User;

public class ForumDAOImpl implements IForumDAO<Forum> {
	private static SessionFactory factory = new Configuration().configure().buildSessionFactory();
	private static String _query = "from Forum"; // for findAll static Method
	
	
	
	
	
	@Override
	public Integer _insert(String title, String description, User owner) {
		// TODO Auto-generated method stub
		Session session = factory.openSession();
		Transaction tx = null;
		Integer ForumId = null;
		try {
			tx = session.beginTransaction();
			Forum newForum = new Forum(title, description, owner);
			ForumId = (Integer) session.save(newForum);
			tx.commit();
		} catch (HibernateException e) {
			if (tx != null)
				tx.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}
		return ForumId;
	}

	@Override
	public void _update(Forum f) {
		// TODO Auto-generated method stub

	}

	@Override
	public void _delete(Forum f) {
		// TODO Auto-generated method stub

	}
	
	public static List<Forum> FindAll(User owner) throws IOException, ClassNotFoundException, SQLException {
		Session session = factory.openSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			String hql = "from Forum where owner = ?";
			Query query = session.createQuery(hql, Forum.class);
			query.setParameter(0, owner);
			List<Forum> listForums = query.getResultList();
			tx.commit();
			return listForums;
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
