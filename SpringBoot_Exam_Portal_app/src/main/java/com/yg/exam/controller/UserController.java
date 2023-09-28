package com.yg.exam.controller;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.yg.exam.entity.Role;
import com.yg.exam.entity.User;
import com.yg.exam.entity.UserRole;
import com.yg.exam.service.UserService;



@RestController
@RequestMapping("/user")
@CrossOrigin("*")
public class UserController {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	
	//creating user
	@PostMapping("/")
	public User createUser(@RequestBody User user) throws Exception
	{
		
		
		//encoding password
		user.setPassword(this.passwordEncoder.encode(user.getPassword()));
		
		
		List<UserRole> roles=new ArrayList<>();
		
		Role role=new Role();
		role.setRoleId(45L);
		role.setRolename("NORMAL");		
		
		
		UserRole userRole= new UserRole();
		userRole.setUser(user);
		userRole.setRole(role);
		roles.add(userRole);
		
		return this.userService.createUser(user, roles);
	}
	
	//get users
	@GetMapping("/{username}")
	public User getUser(@PathVariable("username") String username) {
		
		return this.userService.getUser(username);
		
	}
	
	//get all users
	@GetMapping("/all")
	public List<User> getAllUsers()
	{
		return this.userService.getAllUser();
	}
	
	//delete the user by id	
	@DeleteMapping("/delete/{userId}")
	public void deletUser(@PathVariable("userId") Long userId)
	{
		this.userService.deleteUser(userId);
	}
	
	//update users
	@PutMapping("/update/{userId}")
	public User updateUser(@PathVariable Long userId, @RequestBody User user) {
		
		return this.userService.updateUser(user, userId);
	}

}
