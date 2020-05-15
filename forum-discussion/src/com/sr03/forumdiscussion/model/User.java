/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sr03.forumdiscussion.model;

import java.util.Objects;

/**
 *
 * @author lounis
 */


import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 *
 * @author lounis
 */
public class User {
	private int id;
    private String lastName;
    private String firstName;
    private String login; //mail adress
    private String gender;
    private String pwd;
    private Byte isAdmin;
    private Role role = Role.Other;
    private static String _query = "select * from user"; // for findAll static Method
    private ArrayList<Forum> forumSubscriptions;

    public ArrayList<Forum> getForumSubscriptions() {
        return forumSubscriptions;
    }

    private enum Role {
        Other, Admin
    };
    
    public User() {
    	
    }

//    public User(String lastName, String firstName, String login, String gender, String pwd) {
//
//        this.lastName = lastName;
//        this.firstName = firstName;
//        this.login = login;
//        this.gender = gender;
//        this.pwd = pwd;
//    }

//    public User(ResultSet res) throws SQLException {
//        this.id = res.getInt("id");
//        this.firstName = res.getString(2);
//        this.lastName = res.getString(3);
//        this.login = res.getString(4);
//        this.gender = res.getString("gender");
//        this.role = Role.values()[res.getBoolean("is_admin") ? 1 : 0];
//    }

//    public User(int id) throws IOException, ClassNotFoundException, SQLException {
//        Connection conn = MyConnectionClass.getInstance();
//        String select_query = "select * from `db_sr03`.`user` where `id` = '" + id + "';";
//        Statement sql = null;
//        sql = conn.createStatement();
//        ResultSet res = sql.executeQuery(select_query);
//        if (res.next()) {
//            this.id = res.getInt("id");
//            this.firstName = res.getString(2);
//            this.lastName = res.getString(3);
//            this.login = res.getString(4);
//            this.gender = res.getString("gender");
//            this.role = Role.values()[res.getBoolean("is_admin") ? 1 : 0];
//        }
//
//    }

    public void setLogin(String login) {
        this.login = login;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }
    
    public void setId(int id) {
    	this.id = id;
    }


    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

	public int getId() {
		return id;
	}

	public String getGender() {
		return gender;
	}

    public String getLogin() {
        return login;
    }

    public String getPwd() {
        return pwd;
    }

    public String getLastName() {
        return lastName;
    }

    public String getFirstName() {
        return firstName;
    }

	public Byte getIsAdmin() {
		return isAdmin;
	}

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setRole() {
        this.role = Role.values()[isAdmin == 1 ? 1 : 0];
    }
    
    public void setIsAdmin(Byte isAdmin) {
    	this.isAdmin = isAdmin;
    	setRole();
    }
    

    public String getRole() {
        return role.toString();
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

    public User(String lastName, String firstName) {
        this.lastName = lastName;
        this.firstName = firstName;

    }

    @Override
    public String toString() {
        return "User{" + "lastName=" + lastName + ", firstName=" + firstName + ""
                + ", login=" + login + ", gender=" + gender + ","
                + " pwd=" + pwd + '}';
    }



}

