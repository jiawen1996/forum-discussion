/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sr03.forumdiscussion.model;



import java.util.HashSet;
import java.util.List;
import java.util.Set;


/**
 *
 * @author lounis
 */
public class Forum {
	private Integer id;
	private String title;
	private String description;
	private Set<Message> messages;
	private User owner;
	private Set<User> followers;

	// constructeur par défaut
	public Forum() {
		this.messages = new HashSet<Message>();
		this.followers = new HashSet<User>();
	}

	public Forum(String titre, String description, User u) {
		this.messages = new HashSet<Message>();
		this.title = titre;
		this.description = description;
		this.owner = u;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	/**
	 *
	 * @return
	 */
	public Set<Message> getMessages() {
		return messages;
	}

	/**
	 *
	 * @param messages
	 */
	public void setMessages(Set<Message> messages) {
		this.messages = messages;
	}

	public User getOwner() {
		return owner;
	}

	public void setOwner(User owner) {
		this.owner = owner;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Set<Message> getFilDiscussion(String choix) {
		if ("all".equalsIgnoreCase(choix)) {
			return this.messages;
		}
		// ToDo il faut traiter d'autres choix.
		return null;
	}

	public Set<Message> LoadMessages() {
		return messages;
	}

	public void addMessage(Message msg) {
		this.messages.add(msg);
	}

	public static List<Forum> FindAll() {
		return null;
	}

	public Set<User> getFollowers() {
		return followers;
	}

	public void setFollowers(Set<User> followers) {
		this.followers = followers;
	}
}
