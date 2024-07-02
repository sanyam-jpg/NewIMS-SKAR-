package com.example.demo.service;

import com.example.demo.model.Item;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public interface InventoryService {

    ResponseEntity<String> createInventory(Item item);
    public String getAllInventory();
    public String getInventory(Long id);
    ResponseEntity<String> updateInventory(Long id, Item item);

    ResponseEntity<String> updateStatus(Long id, String status);
    ResponseEntity<String> updatePricing(Long id, long costPrice, long sellingPrice);
    ResponseEntity<String> updateAttribute(Long id, String attribute);

}
