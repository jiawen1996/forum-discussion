package com.sr03.forumdiscussion.model;

public class Subscription {
	private User user;
	private Forum forum;

	// constructeur par défaut
	public Subscription() {

	}

	public Subscription(User u, Forum f) {
		this.user = u;
		this.forum = f;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Forum getForum() {
		return forum;
	}

	public void setForum(Forum forum) {
		this.forum = forum;
	}

}
