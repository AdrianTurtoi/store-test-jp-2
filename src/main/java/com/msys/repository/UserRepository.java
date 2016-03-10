package com.msys.repository;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import com.msys.entity.User;

@Transactional
public interface UserRepository extends JpaRepository<User, Long> {
	// CrudRepository<RequestStatus, Long> {

	public User findByEmail(String email);
}
