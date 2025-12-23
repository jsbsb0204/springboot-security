package com.springboot.security.data.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.springboot.security.data.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {
   
	User getByUid(String uid);

}
