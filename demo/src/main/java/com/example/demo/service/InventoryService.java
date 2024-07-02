package com.example.demo.service;

import com.example.demo.model.Item;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public interface InventoryService {

    ResponseEntity<String> createInventory(Item item);
    public String getAllInventory();
    public String getInventoryById(Long id);
    Page<Item> getInventoryByPage(Pageable pageable);

}
