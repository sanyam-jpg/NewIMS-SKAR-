package com.skar.InventoryManagement.service;


import com.skar.InventoryManagement.model.Item;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import static org.junit.Assert.assertEquals;

import static org.junit.jupiter.api.Assertions.*;

public class InventoryServiceImplTest {

    InventoryServiceImpl inventoryService;

    @Before()
    public void setup(){
        inventoryService = new InventoryServiceImpl();
    }
    @Test
    public void validType_mobile() {

        String type = "Mobile";

        boolean result = inventoryService.validType(type);

        assertTrue(result);

    }

    @Test
    public void validType_car() {

        String type = "CaR";

        boolean result = inventoryService.validType(type);

        assertTrue(result);

    }

    @Test
    public void validType_bike() {

        String type = "bIKe";

        boolean result = inventoryService.validType(type);

        assertTrue(result);

    }

    @Test
    public void invalid_type_empty_String() {

        String type = "";

        boolean result = inventoryService.validType(type);

        assertFalse(result);

    }

    @Test
    public void invalid_type_null() {

        String type = "";

        boolean result = inventoryService.validType(type);

        assertFalse(result);

    }

    @Test
    public void invalid_type_arbitarary() {
        Item item = new Item();

        String type = "xyz_123";

        boolean result = inventoryService.validType(type);

        assertFalse(result);

    }


}