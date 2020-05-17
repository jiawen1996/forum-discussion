package com.sr03.forumdiscussion.dao;

public interface IUserDAO<User> {

	public Integer _insert(String lastName, String firstName, String login, Byte isAdmin, String gender, String password);

	public void _update(User u);

	public void _delete(User u);

}
