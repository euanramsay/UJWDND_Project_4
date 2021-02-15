package com.udacity.controller;

import com.udacity.model.Cart;
import com.udacity.repository.CartRepository;
import com.udacity.repository.ItemRepository;
import com.udacity.repository.UserRepository;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

import static com.udacity.TestUtils.createItem;
import static com.udacity.TestUtils.createModifyCartRequest;
import static com.udacity.TestUtils.createUser;
import static com.udacity.TestUtils.injectObject;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class CartControllerTest {

    private static final String USERNAME = "test";

    private CartController cartController;
    private final ItemRepository itemRepo = mock(ItemRepository.class);
    private final CartRepository cartRepo = mock(CartRepository.class);
    private final UserRepository userRepo = mock(UserRepository.class);

    @Before
    public void setUp() {
        cartController = new CartController();
        injectObject(cartController, "itemRepository", itemRepo);
        injectObject(cartController, "cartRepository", cartRepo);
        injectObject(cartController, "userRepository", userRepo);
    }

    @Test
    public void canAddToCartTest() {
        when(userRepo.findByUsername(USERNAME)).thenReturn(createUser());
        when(itemRepo.findById(1L)).thenReturn(Optional.of(createItem()));

        ResponseEntity<Cart> response = cartController.addToCart(createModifyCartRequest());
        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());

        Cart actualCart = response.getBody();
        assertNotNull(actualCart);
        assertEquals(USERNAME, actualCart.getUser().getUsername());
    }

    @Test
    public void canRemoveFromCartTest() {
        when(userRepo.findByUsername(USERNAME)).thenReturn(createUser());
        when(itemRepo.findById(1L)).thenReturn(Optional.of(createItem()));

        ResponseEntity<Cart> response = cartController.removeFromCart(createModifyCartRequest());
        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());

        Cart actualCart = response.getBody();
        assertNotNull(actualCart);
        assertEquals(USERNAME, actualCart.getUser().getUsername());
    }
}
