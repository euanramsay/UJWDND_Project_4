package com.udacity.controller;

import com.udacity.model.Item;
import com.udacity.repository.ItemRepository;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static com.udacity.TestUtils.createItem;
import static com.udacity.TestUtils.createTwoItems;
import static com.udacity.TestUtils.injectObject;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ItemControllerTest {

    private static final String NAME = "test name";

    private ItemController itemController;
    private final ItemRepository itemRepo = mock(ItemRepository.class);

    @Before
    public void setUp() {
        itemController = new ItemController();
        injectObject(itemController, "itemRepository", itemRepo);
    }

    @Test
    public void canGetItemByIdTest() {
        Item item = createItem();
        when(itemRepo.findById(1L)).thenReturn(Optional.of(item));

        final ResponseEntity<Item> response = itemController.getItemById(1L);

        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());

        Item actualItem = response.getBody();
        assertNotNull(actualItem);
        assertEquals(NAME, actualItem.getName());
    }

    @Test
    public void canGetItemsByNameTest() {
        Item item = createItem();
        List<Item> items = new ArrayList<>(Arrays.asList(item, item));
        when(itemRepo.findByName(NAME)).thenReturn(items);

        final ResponseEntity<List<Item>> response = itemController.getItemsByName(NAME);

        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());

        List<Item> actualItems = response.getBody();
        assertNotNull(actualItems);
        assertEquals(NAME, actualItems.get(0).getName());
    }

    @Test
    public void canGetAllItems() {
        List<Item> items = createTwoItems();
        when(itemRepo.findAll()).thenReturn(items);

        final ResponseEntity<List<Item>> response = itemController.getItems();

        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());

        List<Item> actualItems = response.getBody();
        assertNotNull(actualItems);
        assertEquals(2, actualItems.size());
        assertEquals(items.get(1).getPrice(), actualItems.get(1).getPrice());
    }
}
