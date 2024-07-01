package com.example.demo.service;

import com.example.demo.model.Item;
import org.springframework.stereotype.Service;

@Service
public interface InventoryService {

    String createInventory(Item item);

    String getAllInventory();
}
