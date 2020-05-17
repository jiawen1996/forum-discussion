package com.sr03.forumdiscussion.model;

public class Subscription {
	private Integer idUser;
	private Integer idForum;

	// constructeur par défaut
	public Subscription() {

	}

	public Subscription(Integer idUser, Integer idForum) {
		this.idUser = idUser;
		this.idForum = idForum;
	}

	public Integer getIdUser() {
		return idUser;
	}

	public void setIdUser(Integer idUser) {
		this.idUser = idUser;
	}

	public Integer getIdForum() {
		return idForum;
	}

	public void setIdForum(Integer idForum) {
		this.idForum = idForum;
	}

}
