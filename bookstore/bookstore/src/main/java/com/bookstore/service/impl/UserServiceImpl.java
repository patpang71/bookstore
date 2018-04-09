package com.bookstore.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bookstore.domain.User;
import com.bookstore.domain.UserBilling;
import com.bookstore.domain.UserPayment;
import com.bookstore.domain.security.UserRole;
import com.bookstore.repository.RoleRepository;
import com.bookstore.repository.UserBillingRepository;
import com.bookstore.repository.UserPaymentRepository;
import com.bookstore.repository.UserRepository;
import com.bookstore.service.UserService;

@Service
public class UserServiceImpl implements UserService {
	
	private static final Logger LOG = LoggerFactory.getLogger(UserService.class);

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private RoleRepository roleRepository;
	
	@Autowired
	private UserBillingRepository userBillingRepository;
	
	@Autowired
	private UserPaymentRepository userPaymentRepository;
	
	
	@Transactional
	public User createUser(User user, Set<UserRole> userRoles) {
		User localUser = userRepository.findByUsername(user.getUsername());
		if (localUser!=null) {
			LOG.info("User with username {} already exits. Notthing will be done. ", user.getUsername());
		}
		else {
			for (UserRole ur: userRoles) {
				roleRepository.save(ur.getRole());
			}
			user.getUserRoles().addAll(userRoles);
			user.setUserPaymentList(new ArrayList<UserPayment>());
			localUser = userRepository.save(user);
		}
		return localUser;
	}
	
	@Override
	public User save(User user) {
		return userRepository.save(user);
	}
	
	@Override
	public User findById(Long id) {
		return userRepository.findOne(id);
	}
	
	@Override
	public User findByUsername(String username) {
		return userRepository.findByUsername(username);
	}
	
	@Override
	public User findByEmail(String email) {
		return userRepository.findByEmail(email);
	}
	
	@Override
	public void updateUserPaymentInfo(UserBilling userBilling, 
			UserPayment userPayment, User user) {
		//save(user);
		userPayment.setUserBilling(userBilling);
		//user.getUserPaymentList().add(userPayment);
		for (UserPayment up : user.getUserPaymentList()) {
			if (up.getId()==userPayment.getId()) {
				up.setCardName(userPayment.getCardName());
				up.setCardNumber(userPayment.getCardNumber());
				up.setCvc(userPayment.getCvc());
				up.setExpiryMonth(userPayment.getExpiryMonth());
				up.setExpiryYear(userPayment.getExpiryYear());
				up.setHolderName(userPayment.getHolderName());
				up.setType(userPayment.getType());
			}
		}
		userBillingRepository.save(userBilling);
		userPaymentRepository.save(userPayment);	

		save(user);
	}
	
	@Override
	public void updateUserBilling(UserBilling userBilling, 
			UserPayment userPayment, User user) {
		userPayment.setUser(user);
		userPayment.setUserBilling(userBilling);
		userPayment.setDefaultPayment(true);
		userBilling.setUserPayment(userPayment);
		user.getUserPaymentList().add(userPayment);
		save(user);
	}
	
	@Override
	public void setUserDefaultPayment(Long userPaymentId, User user) {
		List<UserPayment> userPaymentList = (List<UserPayment>) userPaymentRepository.findAll();
		
		for (UserPayment userPayment : userPaymentList) {
			if (userPayment.getId()== userPaymentId) {
				userPayment.setDefaultPayment(true);
				userPaymentRepository.save(userPayment);
			}
			else {
				userPayment.setDefaultPayment(false);
				userPaymentRepository.save(userPayment);
			}
		}
	}
}
