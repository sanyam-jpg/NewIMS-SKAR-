package com.example.demo.controller;

import com.example.demo.model.Item;
import com.example.demo.service.InventoryService;
import configration.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

@Component
@RestController("/inventories")
public class InventoryController {

    @Autowired
    private InventoryService inventoryService;



    @GetMapping("/")
    public ResponseEntity<String> getAll(){
        return inventoryService.getAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<String> getById(@PathVariable Long id){
        return inventoryService.get(id);
    }

    @GetMapping("/items/{pageNum}")
    public ResponseEntity<Page<Item>> getByPage(@PathVariable int pageNum) {
        try {
            Pageable pageable = PageRequest.of(pageNum, Constants.itemOnEachPage);
            Page<Item> page = inventoryService.getPage(pageable);

            if (page.isEmpty()) {
                return ResponseEntity.notFound().build();
            }

            return ResponseEntity.ok(page);
        } catch (Exception e) {
            return ResponseEntity.status(500).build();
        }
    }

    @PostMapping("/create")
    public ResponseEntity<String> createInventory(@RequestBody Item item){

        return inventoryService.createInventory(item);

    }
}
