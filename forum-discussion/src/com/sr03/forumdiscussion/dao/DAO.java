package com.sr03.forumdiscussion.dao;

import java.util.Optional;

import antlr.collections.List;

public interface DAO<T> {
//	Optional<T> get(long id);
    
//    List<T> getAll();
     
    
	void _insert(T t);
     
    void _update(T t, String[] params);
     
    void _delete(T t);
}
