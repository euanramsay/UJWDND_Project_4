package com.udacity.repository;

import java.util.List;

import com.udacity.model.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemRepository extends JpaRepository<Item, Long> {

	List<Item> findByName(String name);
}
