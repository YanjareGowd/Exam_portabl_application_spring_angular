package com.yg.exam.service;

import java.util.List;


import com.yg.exam.entity.User;
import com.yg.exam.entity.UserRole;

public interface UserService {
	
	//creating user
	
	User createUser(User user, List<UserRole> userRoles) throws Exception;
	//get user by username
	
	public User getUser(String username);
	
	//get all users
	public List<User> getAllUser();
	
	//delete user by id
	public void deleteUser(Long userId);
	
	//update user
	User updateUser(User user,Long userId);

}
