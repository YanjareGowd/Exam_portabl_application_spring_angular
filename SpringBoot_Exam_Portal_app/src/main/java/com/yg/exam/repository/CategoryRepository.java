package com.yg.exam.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.yg.exam.entity.exam.Category;

public interface CategoryRepository extends JpaRepository<Category, Long>{

}
