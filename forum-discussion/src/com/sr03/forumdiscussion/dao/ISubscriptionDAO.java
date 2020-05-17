package com.sr03.forumdiscussion.dao;

import com.sr03.forumdiscussion.model.Forum;
import com.sr03.forumdiscussion.model.User;

public interface ISubscriptionDAO<Subscription> {
	public Integer _insert(User u, Forum f);

	public void _update(Subscription s);

	public void _delete(Subscription s);
}
