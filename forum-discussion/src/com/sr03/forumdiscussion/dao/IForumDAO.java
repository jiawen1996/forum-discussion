package com.sr03.forumdiscussion.dao;

public interface IForumDAO<Forum> {
	public Integer _insert(String title,String description, Integer owner);

	public void _update(Forum f);

	public void _delete(Forum f);
}
