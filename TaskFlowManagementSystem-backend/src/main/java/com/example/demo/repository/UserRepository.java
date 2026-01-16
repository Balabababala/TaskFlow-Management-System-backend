package com.example.demo.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.demo.model.entity.User;



public interface UserRepository extends JpaRepository<User, Long>{
	    Optional<User> findByUsername(String username);
	    Optional<User> findById(Long id);
	    Optional<User> findByEmail(String email);
	    
	 // 查使用者及其角色
	    @Query(value = "SELECT u.id AS user_id, u.username, u.email, u.password, " +
	                   "r.id AS role_id, r.role_name " +
	                   "FROM users u " +
	                   "JOIN role r ON u.role_id = r.id " +
	                   "WHERE u.id = :userId",
	           nativeQuery = true)
	    Object findUserWithRoleById(@Param("userId") Long userId);
	    
	    boolean existsByUsername(String username);
	    boolean existsByEmail(String Email);
}
