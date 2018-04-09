package com.bookstore;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.bookstore.config.SecurityUtility;
import com.bookstore.domain.User;
import com.bookstore.domain.UserBilling;
import com.bookstore.domain.UserPayment;
import com.bookstore.domain.security.Role;
import com.bookstore.domain.security.UserRole;
import com.bookstore.service.UserPaymentService;
import com.bookstore.service.UserService;

@SpringBootApplication
public class BookstoreApplication implements CommandLineRunner {

	@Autowired
	private UserService userService;
	
	@Autowired
	private UserPaymentService userPaymentService;
	
	public static void main(String[] args) {
		SpringApplication.run(BookstoreApplication.class, args);
	}
	
	@Override
	public void run(String... args) throws Exception {
		User user1 = new User();
		user1.setFirstName("John");
		user1.setLastName("Adams");
		user1.setUsername("jadams");
		user1.setPassword(SecurityUtility.passwordEncoder().encode("welcome1"));
		user1.setEmail("jadams@gmail.com");
		Set<UserRole> userRoles = new HashSet<>();
		Role role1 = new Role();
		role1.setRoleId(1);
		role1.setName("ROLE_USER");
		userRoles.add(new UserRole(user1, role1));
		
		/*
		UserPayment userPayment = new UserPayment();
		userPayment.setCardName("card1");
		userPayment.setCardNumber("4242424242424242");
		
		UserBilling userBilling = new UserBilling();
		userBilling.setUserBillingCity("Fremont");
		userBilling.setUserBillingStreet1("1234 Andrea Way");
		*/
		//userPayment.setUserBilling(userBilling);
				
		userService.createUser(user1, userRoles);
		
		//userService.updateUserPaymentInfo(userBilling, userPayment, user1);
		
		userRoles.clear();
		
		User user2 = new User();
		user2.setFirstName("Betty");
		user2.setLastName("Lynch");
		user2.setUsername("blynch");
		user2.setPassword(SecurityUtility.passwordEncoder().encode("welcome1"));
		user2.setEmail("blynch@gmail.com");
		userRoles = new HashSet<>();
		Role role2 = new Role();
		role2.setRoleId(2);
		role2.setName("ROLE_ADMIN");
		userRoles.add(new UserRole(user2, role2));
		
		userService.createUser(user2, userRoles);		
	}
}
