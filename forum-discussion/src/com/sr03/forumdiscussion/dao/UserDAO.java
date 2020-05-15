package com.sr03.forumdiscussion.dao;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.TypedQuery;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;

import com.sr03.forumdiscussion.model.MyConnectionClass;
import com.sr03.forumdiscussion.model.User;

public class UserDAO implements DAO<User> {
	private static SessionFactory factory = new Configuration().configure().buildSessionFactory();

	@Override
	public void _insert(User u) {
		Session session = factory.openSession();
		Transaction tx = null;
		Integer UserId = null;
		try {
			tx = session.beginTransaction();
			UserId = (Integer) session.save(u);
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
	public void _update(User u, String[] params) {
		Session session = factory.openSession();
		Transaction tx = null;
		Integer UserId = null;
		try {
			tx = session.beginTransaction();
			int userId = u.getId();
			User user = (User)session.get(User.class, userId);
			user.setFirstName(u.getFirstName());
			user.set
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

	public static User FindByID(int id) {
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
		List<User> listUser = new ArrayList<User>();

		// TODO

		return listUser;
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
