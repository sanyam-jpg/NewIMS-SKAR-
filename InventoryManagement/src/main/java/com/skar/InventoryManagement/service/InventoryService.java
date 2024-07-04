package com.skar.InventoryManagement.service;

import com.skar.InventoryManagement.model.Item;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public interface InventoryService {

    public ResponseEntity<String> create(Item item);

    public ResponseEntity<String> updateInventory(Long id, Item item);

    public ResponseEntity<String> updateStatus(Long id, String status);

    public ResponseEntity<String> updatePricing(Long id, Long costPrice, Long sellingPrice);

    public ResponseEntity<String> updateAttribute(Long id, String attribute);


    public ResponseEntity<String> getAll();
    public ResponseEntity<String> get(Long id);
    public Page<Item> getPage(Pageable pageable);
    public ResponseEntity<String> delete(Long id);

}
