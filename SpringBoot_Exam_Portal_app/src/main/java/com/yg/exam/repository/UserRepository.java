package com.yg.exam.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.yg.exam.entity.User;

public interface UserRepository extends JpaRepository<User, Long>{

	public User findByUsername(String username);

}
