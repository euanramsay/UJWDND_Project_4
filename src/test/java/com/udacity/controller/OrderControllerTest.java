package com.udacity.controller;

import com.udacity.model.User;
import com.udacity.model.UserOrder;
import com.udacity.repository.OrderRepository;
import com.udacity.repository.UserRepository;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.util.List;

import static com.udacity.TestUtils.createTwoOrders;
import static com.udacity.TestUtils.createUser;
import static com.udacity.TestUtils.injectObject;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class OrderControllerTest {

    private static final String USERNAME = "test";

    private OrderController orderController;
    private final OrderRepository orderRepo = mock(OrderRepository.class);
    private final UserRepository userRepo = mock(UserRepository.class);

    @Before
    public void setUp() {
        orderController = new OrderController();
        injectObject(orderController, "orderRepository", orderRepo);
        injectObject(orderController, "userRepository", userRepo);
    }

    @Test
    public void canSubmitOrderTest() {
        when(userRepo.findByUsername(USERNAME)).thenReturn(createUser());

        final ResponseEntity<UserOrder> response = orderController.submit(USERNAME);

        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());

        UserOrder actualOrder = response.getBody();
        assertNotNull(actualOrder);
        assertEquals(USERNAME, actualOrder.getUser().getUsername());
    }

    @Test
    public void canGetOrdersForUserTest() {
        User user = createUser();
        when(userRepo.findByUsername(USERNAME)).thenReturn(user);
        when(orderRepo.findByUser(user)).thenReturn(createTwoOrders());
        final ResponseEntity<List<UserOrder>> response = orderController.getOrdersForUser(USERNAME);

        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());

        List<UserOrder> actualOrders = response.getBody();
        assertNotNull(actualOrders);
        assertEquals(BigDecimal.valueOf(3.98), actualOrders.get(1).getTotal());
    }
}