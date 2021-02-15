package com.udacity.controller;

import java.util.Optional;
import java.util.stream.IntStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.udacity.model.Cart;
import com.udacity.model.Item;
import com.udacity.model.User;
import com.udacity.repository.CartRepository;
import com.udacity.repository.ItemRepository;
import com.udacity.repository.UserRepository;
import com.udacity.request.ModifyCartRequest;

@RestController
@RequestMapping("/api/cart")
public class CartController {

	private static final Logger log = LoggerFactory.getLogger(CartController.class);

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private ItemRepository itemRepository;

    @PostMapping("/addToCart")
    public ResponseEntity<Cart> addToCart(@RequestBody ModifyCartRequest request) {
        User user = userRepository.findByUsername(request.getUsername());

        log.info(String.format("Finding user %s", request.getUsername()));

        if (user == null) {

        	log.warn(String.format("User %s not found", request.getUsername()));

        	return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        Optional<Item> item = itemRepository.findById(request.getItemId());

		log.info(String.format("Finding item with id %s", request.getItemId()));

		if (!item.isPresent()) {

        	log.warn(String.format("Item with id %s not found", request.getItemId()));

			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        Cart cart = user.getCart();
        IntStream.range(0, request.getQuantity())
                .forEach(i -> cart.addItem(item.get()));
        cartRepository.save(cart);

		log.info(String.format("Item with id %s added to cart for user %s", request.getItemId(), user));

		return ResponseEntity.ok(cart);
    }

    @PostMapping("/removeFromCart")
    public ResponseEntity<Cart> removeFromCart(@RequestBody ModifyCartRequest request) {
        User user = userRepository.findByUsername(request.getUsername());

		log.info(String.format("Finding user %s", request.getUsername()));

		if (user == null) {

			log.warn(String.format("User %s not found", request.getUsername()));

			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        Optional<Item> item = itemRepository.findById(request.getItemId());
        if (!item.isPresent()) {

        	log.warn(String.format("Item with id %s not found", request.getItemId()));

			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        Cart cart = user.getCart();
        IntStream.range(0, request.getQuantity())
                .forEach(i -> cart.removeItem(item.get()));
        cartRepository.save(cart);

		log.info(String.format("Item with id %s removed from cart for user %s", request.getItemId(), user));

		return ResponseEntity.ok(cart);
    }
}
