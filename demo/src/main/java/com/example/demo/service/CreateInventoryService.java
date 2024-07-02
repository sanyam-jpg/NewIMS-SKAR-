package com.example.demo.service;

import com.example.demo.model.Item;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public interface CreateInventoryService {

    ResponseEntity<String> createInventory(Item item);


}
