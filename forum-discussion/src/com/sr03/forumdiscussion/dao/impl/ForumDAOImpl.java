package com.sr03.forumdiscussion.dao.impl;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

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
		Session session = factory.openSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			int forumId = f.getId();

			Forum forum = (Forum) session.get(Forum.class, forumId);
			forum.setTitle(f.getTitle());
			forum.setDescription(f.getDescription());
			forum.setOwner(f.getOwner());

			session.update(forum);
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

	@Override
	public void _delete(Forum f) {
		Session session = factory.openSession();
		Transaction tx = null;

		try {
			tx = session.beginTransaction();
			int forumId = f.getId();
			Forum forum = (Forum) session.get(Forum.class, forumId);
			session.delete(forum);
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

	public static List<Forum> FindAll() throws IOException, ClassNotFoundException, SQLException {
		Session session = factory.openSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			String hql = _query;
			Query<Forum> query = session.createQuery(hql, Forum.class);
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
	
	public static List<Forum> FindByOwner(User owner) throws IOException, ClassNotFoundException, SQLException {
		Session session = factory.openSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			String hql = "from Forum where owner = ?";
			Query<Forum> query = session.createQuery(hql, Forum.class);
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

	public static List<Forum> FindById(Integer id) throws IOException, ClassNotFoundException, SQLException {
		Session session = factory.openSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			String hql = "from Forum where id = ?";
			Query<Forum> query = session.createQuery(hql, Forum.class);
			query.setParameter(0, id);
			List<Forum> res = query.getResultList();
			tx.commit();
			return res;
		} catch (HibernateException e) {
			if (tx != null)
				tx.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}
		return null;
	}

	public List<User> getListUsers(Integer id) {
		Session session = factory.openSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			String hql = "select users from Forum where id = ?";
			Query query = session.createQuery(hql, Forum.class);
			query.setParameter(0, id);
			List<User> listUsers = query.getResultList();
			tx.commit();
			return listUsers;
		} catch (HibernateException e) {
			if (tx != null)
				tx.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}
		return null;
	}

	public boolean updateUsers(Integer idForum, Integer idUser) {
		Session session = factory.openSession();
		Transaction tx = null;

		try {
			
			tx = session.beginTransaction();
			Forum forum = (Forum)session.load(Forum.class, idForum); 
			User user = (User)session.load(User.class, idUser);
			forum.getUsers().add(user);
			session.save(forum);
			tx.commit();
			return true;
		} catch (HibernateException e) {
			if (tx != null)
				tx.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}
		return false;
	}

}
