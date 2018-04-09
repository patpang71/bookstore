package com.bookstore.service;

import java.util.Set;

import com.bookstore.domain.User;
import com.bookstore.domain.UserBilling;
import com.bookstore.domain.UserPayment;
import com.bookstore.domain.security.UserRole;

public interface UserService {
	
	public User createUser(User user, Set<UserRole> userRoles);
	
	public User findByUsername(String username);
	
	public User findByEmail(String email);
	
	public User save(User user);
	
	public User findById(Long id);
	
	public void updateUserPaymentInfo(UserBilling userBilling, 
			UserPayment userPayment, User user);

	public void updateUserBilling(UserBilling userBilling, 
			UserPayment userPayment, User user);

	void setUserDefaultPayment(Long userPaymentId, User user);
	
}
