package com.example.demo.service;

import com.example.demo.model.Item;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public interface InventoryService {

    public ResponseEntity<String> createInventory(Item item);
    public ResponseEntity<String> getAllInventory();
    public ResponseEntity<String> getInventoryById(Long id);
    public Page<Item> getInventoryByPage(Pageable pageable);

}
