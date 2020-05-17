package com.sr03.forumdiscussion.dao;

import com.sr03.forumdiscussion.model.User;

public interface IForumDAO<Forum> {
	public Integer _insert(String title,String description, User owner);

	public void _update(Forum f);

	public void _delete(Forum f);
}
