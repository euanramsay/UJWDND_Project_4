package com.udacity.repository;

import java.util.List;

import com.udacity.model.User;
import com.udacity.model.UserOrder;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<UserOrder, Long> {
	List<UserOrder> findByUser(User user);
}
