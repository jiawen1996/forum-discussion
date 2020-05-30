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

import com.sr03.forumdiscussion.dao.IUserDAO;
import com.sr03.forumdiscussion.model.Forum;
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
			user.setLastName(u.getLastName());
			user.setLogin(u.getLogin());
			user.setGender(u.getGender());
			user.setIsAdmin(u.getIsAdmin());
			user.setPwd(u.getPwd());
			
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
		Session session = factory.openSession();
		Transaction tx = null;
		
		try {
			tx = session.beginTransaction();
			int userId = u.getId();
			User user = (User) session.get(User.class, userId);
			session.delete(user);
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

	public static List<User> FindById(Integer id) throws IOException, ClassNotFoundException, SQLException {
		Session session = factory.openSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			String hql = "from User where id = ?";
			Query<User> query = session.createQuery(hql, User.class);
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
			Query<User> query = session.createQuery(hql, User.class);
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
			Query<User> query = session.createQuery(hql, User.class);
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
			Query<User> query = session.createQuery(hql, User.class);
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

	//TODO
	public void loadForumSubscriptions() {

	}

	public void addForumSubscription() {

	}

	public static List<Forum> updateForumSubscriptions(User u) {
		return null;

	}

	public Set<Forum> getForumSubscriptions(Integer idUser) throws ClassNotFoundException, IOException, SQLException {
		User user = (User) FindById(idUser).get(0);
		return user.getForumSubscriptions();
	}
}
