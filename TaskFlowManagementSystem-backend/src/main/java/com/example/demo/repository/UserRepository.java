package com.example.demo.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import com.example.demo.model.entity.User;



public interface UserRepository extends JpaRepository<User, Long>{
		@EntityGraph(attributePaths = {"role"})
	    Optional<User> findByUsername(String username);
	    @EntityGraph(attributePaths = {"role"})
	    Optional<User> findById(Long id);
	    @EntityGraph(attributePaths = {"role"})
	    Optional<User> findByEmail(String email);
	    
	    boolean existsByUsername(String username);
	    boolean existsByEmail(String Email);
}
