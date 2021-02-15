package com.udacity;

import com.udacity.model.Cart;
import com.udacity.model.Item;
import com.udacity.model.User;
import com.udacity.request.ModifyCartRequest;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TestUtils {

    private static final String USERNAME = "test";
    private static final String PASSWORD = "testPassword";
    private static final String DESCRIPTION = "test description";
    private static final String NAME = "test name";


    public static void injectObject(Object target, String fieldName, Object toInject) {

        boolean wasPrivate = false;

        try {
            Field f = target.getClass().getDeclaredField(fieldName);

            if (!f.isAccessible()) {
                f.setAccessible(true);
                wasPrivate = true;
            }
            f.set(target, toInject);

            if (wasPrivate) {
                f.setAccessible(false);
            }

        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    public static User createUser() {
        User user = new User();
        user.setId(1L);
        user.setUsername(USERNAME);
        user.setPassword(PASSWORD);
        Cart cart = new Cart();
        cart.setId(1L);
        cart.setUser(user);
        List<Item> items = new ArrayList<>(
                Arrays.asList(createItem(), createItem())
        );
        cart.setItems(items);
        user.setCart(cart);
        return user;
    }

    public static Item createItem() {
        Item item = new Item();
        item.setId(1L);
        item.setDescription(DESCRIPTION);
        item.setName(NAME);
        item.setPrice(BigDecimal.valueOf(1.99));
        return item;
    }

    public static ModifyCartRequest createModifyCartRequest() {
        ModifyCartRequest modifyCartRequest = new ModifyCartRequest();
        modifyCartRequest.setItemId(1L);
        modifyCartRequest.setQuantity(3);
        modifyCartRequest.setUsername(USERNAME);
        return modifyCartRequest;
    }
}
