package com.sr03.forumdiscussion.dao.impl;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;

import com.sr03.forumdiscussion.dao.IUserDAO;
import com.sr03.forumdiscussion.model.User;

public class UserDAOImpl implements IUserDAO<User> {
	private static SessionFactory factory = new Configuration().configure().buildSessionFactory();
	private static String _query = "from User"; // for findAll static Method

	@Override
	public Integer _insert(String lastName, String firstName, String login, Byte isAdmin, String gender,
			String password) {
		Session session = factory.openSession();
		Transaction tx = null;
		Integer UserId = null;
		try {
			tx = session.beginTransaction();
			User newUser = new User(lastName, firstName, login, isAdmin, gender, password);
			UserId = (Integer) session.save(newUser);
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
	public void _update(User u) {
		Session session = factory.openSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			int userId = u.getId();
			User user = (User) session.get(User.class, userId);
			user.setFirstName(u.getFirstName());
			session.update(user);
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
	public void _delete(User u) {

	}

	public static List<User> FindById(Integer id) throws IOException, ClassNotFoundException, SQLException {
		Session session = factory.openSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			String hql = "from User where id = ?";
			Query query = session.createQuery(hql, User.class);
			query.setParameter(0, id);
			List<User> res = query.getResultList();
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

	public static List<User> FindByloginAndPwd(String login, String pwd)
			throws IOException, ClassNotFoundException, SQLException {
		Session session = factory.openSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			String hql = "from User where login = ? and pwd = ?";
			Query query = session.createQuery(hql, User.class);
			query.setParameter(0, login);
			query.setParameter(1, pwd);
			List<User> res = query.getResultList();
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

	public static List<User> FindAll() throws IOException, ClassNotFoundException, SQLException {
		List<User> listUsers = new ArrayList<User>();
		Session session = factory.openSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			String hql = _query;
			Query query = session.createQuery(hql, User.class);
			listUsers = query.getResultList();
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

	public static List<User> FindByLastAndFirstName(String fname, String lname)
			throws IOException, ClassNotFoundException, SQLException {
		Session session = factory.openSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			String hql = "from User where fname = ? and lname = ?";
			Query query = session.createQuery(hql, User.class);
			query.setParameter(0, fname);
			query.setParameter(1, lname);
			List<User> res = query.getResultList();
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

	public void LoadForumSubscriptions() {

	}

	public void addForumSubscription() {

	}

	public void updateForumSubscriptions() {

	}
}