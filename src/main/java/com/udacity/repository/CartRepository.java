package com.udacity.repository;

import com.udacity.model.Cart;
import com.udacity.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartRepository extends JpaRepository<Cart, Long> {
	Cart findByUser(User user);
}
