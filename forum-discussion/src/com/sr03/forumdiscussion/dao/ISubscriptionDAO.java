package com.sr03.forumdiscussion.dao;

public interface ISubscriptionDAO<Subscription> {
	public Integer _insert(Integer idUser,Integer idForum);

	public void _update(Subscription s);

	public void _delete(Subscription s);
}
