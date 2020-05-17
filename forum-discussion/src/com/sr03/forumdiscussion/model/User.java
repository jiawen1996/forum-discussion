package com.sr03.forumdiscussion.model;

import java.util.Objects;
import java.util.Set;

/**
 *
 *
 * @author Jiawen
 * @author Linh
 */

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;

public class User {
	private Integer id;
	private String lastName;
	private String firstName;
	private String login; // mail adress
	private String gender;
	private String pwd;

	// Un Byte correspond à la colonne is_Admin
	private Byte isAdmin;
	private Role role = Role.Other;
	private Set<Forum> forumSubscriptions;

	private enum Role {
		Other, Admin
	};

	// constructeur par défaut
	public User() {
		this.forumSubscriptions = new HashSet<Forum>();

	}

	public User(String lastName, String firstName, String login, Byte isAdmin, String gender, String password) {
		this.lastName = lastName;
		this.firstName = firstName;
		this.login = login;
		this.gender = gender;
		this.pwd = password;
		this.isAdmin = isAdmin;

	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getLogin() {
		return login;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getGender() {
		return gender;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getId() {
		return id;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}

	public String getPwd() {
		return pwd;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public Byte getIsAdmin() {
		return isAdmin;
	}

	public void setIsAdmin(Byte isAdmin) {
		this.isAdmin = isAdmin;
		// le rôle doit correspondre à l'attribut de isAdmin
		setRole();
	}

	private void setRole() {
		this.role = Role.values()[isAdmin == 1 ? 1 : 0];
	}

	public String getRole() {
		return role.toString();
	}

	public Set<Forum> getForumSubscriptions() {
		return forumSubscriptions;
	}


	@Override
	public int hashCode() {
		int hash = 3;
		hash = 97 * hash + Objects.hashCode(this.lastName);
		hash = 97 * hash + Objects.hashCode(this.firstName);
		return hash;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		final User other = (User) obj;
		if (!Objects.equals(this.lastName, other.lastName)) {
			return false;
		}
		if (!Objects.equals(this.firstName, other.firstName)) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "User{" + "lastName=" + lastName + ", firstName=" + firstName + "" + ", login=" + login + ", gender="
				+ gender + "," + " pwd=" + pwd + '}';
	}

}
