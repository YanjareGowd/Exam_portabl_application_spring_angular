package com.yg.exam;

import java.util.ArrayList;
import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.yg.exam.entity.Role;
import com.yg.exam.entity.User;
import com.yg.exam.entity.UserRole;
import com.yg.exam.service.UserService;

@SpringBootApplication
public class SpringBootExamPortalAppApplication implements CommandLineRunner{
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private PasswordEncoder passwordEncoder;

	public static void main(String[] args) {
		
		SpringApplication.run(SpringBootExamPortalAppApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		
		
		System.out.println(this.passwordEncoder.encode("testuser"));
		System.out.println("start..");
		
		 
		//create initial user
	/*	
		User user=new User();
		
		user.setFirstName("Yanjare");
		user.setLastName("Gowda");
		user.setUsername("yanjare36369");
		user.setPassword("abc1234");
		user.setEmail("yanjare @gmail.com");
		//user.setEnabled(true);
		user.setPhoneNumber("9380674281");
		user.setProfile("default.png");
		
		//create role
		Role role1=new Role();
		role1.setRoleId(44L);
		role1.setRolename("ADMIN");
		
		List<UserRole> userRoleSet=new ArrayList<>();
		
		UserRole userRole=new UserRole();
		
		userRole.setRole(role1);
		userRole.setUser(user);
		
		userRoleSet.add(userRole);
		
		User user1 = this.userService.createUser(user, userRoleSet);
		System.out.println(user1.getUsername());
		
		
		*/
		
	}

}

