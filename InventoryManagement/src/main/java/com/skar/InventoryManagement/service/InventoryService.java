package com.skar.InventoryManagement.service;

import com.skar.InventoryManagement.model.Item;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public interface InventoryService {

    public ResponseEntity<String> createInventory(Item item);
    public String getAllInventory();
    public String getInventory(Long id);
    public ResponseEntity<String> updateInventory(Long id, Item item);

    ResponseEntity<String> updateStatus(Long id, String status);

    ResponseEntity<String> updatePricing(Long id, long costPrice, long sellingPrice);

    ResponseEntity<String> updateAttribute(Long id, String attribute);

}
