package com.example.demo.service;

import com.example.demo.model.Item;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public interface InventoryService {

    public ResponseEntity<String> createInventory(Item item);
    public String getAllInventory();
    public String getInventory(Long id);
    public ResponseEntity<String> updateInventory(Long id, Item item);
}
