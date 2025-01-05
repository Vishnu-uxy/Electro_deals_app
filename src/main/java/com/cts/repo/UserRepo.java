package com.cts.repo;

import com.cts.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepo extends JpaRepository<User, Long> {
	boolean existsByEmail(String email);

	User findByEmail(String userName);
}
