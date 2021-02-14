package com.udacity.controller;

import com.udacity.model.Cart;
import com.udacity.model.Item;
import com.udacity.model.User;
import com.udacity.repository.CartRepository;
import com.udacity.repository.ItemRepository;
import com.udacity.repository.UserRepository;
import com.udacity.request.ModifyCartRequest;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)

public class CartControllerTest {

    Item item = new Item();

    User user = new User();

    @InjectMocks
    private CartController cartController;

    @Mock
    private UserRepository userRepository;

    @Mock
    private CartRepository cartRepository;

    @Mock
    private ItemRepository itemRepository;

    @Before
    public void setup() {
        Mockito.when(userRepository.findByUsername("Snoopy")).thenReturn(user);
        Mockito.when(itemRepository.findById(1L)).thenReturn(Optional.of(item));
    }

    @Test
    public void canAddItemToCartTest() {
        ModifyCartRequest request = new ModifyCartRequest();
        request.setUsername("Snoopy");
        request.setItemId(1);
        request.setQuantity(2);

        ResponseEntity<Cart> response = cartController.addTocart(request);
        assertEquals(200, response.getStatusCodeValue());

        Cart cart = response.getBody();

        assertNotNull(cart);
        assertEquals("Snoopy", cart.getUser().getUsername());
        assertEquals(2, cart.getItems().size());
        assertEquals(item, cart.getItems().get(0));
        assertEquals(BigDecimal.valueOf(6), cart.getTotal());
        verify(cartRepository, times(1)).save(cart);
    }

    @Test
    public void canRemoveItemFromCartTest() {

    }

    @Test
    public void returns404WhenUsernameNotFoundTest() {

    }
}
