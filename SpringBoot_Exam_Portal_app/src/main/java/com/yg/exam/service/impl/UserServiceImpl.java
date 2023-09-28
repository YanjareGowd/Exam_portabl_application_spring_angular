package com.yg.exam.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yg.exam.entity.User;
import com.yg.exam.entity.UserRole;
import com.yg.exam.helper.UserFoundException;
import com.yg.exam.repository.RoleRepository;
import com.yg.exam.repository.UserRepository;
import com.yg.exam.service.UserService;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private RoleRepository roleRepository;
	
	//creating user
	
	@Override
    public User createUser(User user, List<UserRole> userRoles) throws Exception {
		
        User existingUser = userRepository.findByUsername(user.getUsername());

        if (existingUser != null) {
        	
            System.out.println("User already present !!");
            throw new UserFoundException();
            
        } else {
        	
            // Save user roles first
            for (UserRole ur : userRoles) {
            	
                roleRepository.save(ur.getRole());
            }

            // Set user roles and save the user
            user.getUserRoles().addAll(userRoles);
            User newUser = userRepository.save(user);
            return newUser;
        }
    }

	//get user by username
	@Override
	public User getUser(String username) {
		
		return this.userRepository.findByUsername(username);
	}

	//get all users
	@Override
	public List<User> getAllUser() {
		
		return this.userRepository.findAll();
	}
	
	//delete user by id

	@Override
	public void deleteUser(Long userId) {
		
		this.userRepository.deleteById(userId);
	}

	//update user 
	@Override
	public User updateUser(User user, Long userId) {

		User localUser=this.userRepository.findById(userId).get();
				
		localUser.setEmail(user.getEmail());
		localUser.setFirstName(user.getFirstName());
		localUser.setLastName(user.getLastName());
		localUser.setPassword(user.getPassword());
		localUser.setPhoneNumber(user.getPhoneNumber());
		localUser.setProfile(user.getProfile());
		localUser.setUsername(user.getUsername());
		
		User updatedUser=this.userRepository.save(localUser);
		
		
		return updatedUser;
	}

}
