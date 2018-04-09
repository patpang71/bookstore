package com.bookstore.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.bookstore.domain.User;

public interface UserRepository extends CrudRepository<User, Long> {
	
	public User findByUsername(String username);
	
	public User findByEmail(String email);
	
	public List<User> findAll();
}
