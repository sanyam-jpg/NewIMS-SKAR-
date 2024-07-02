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



    @GetMapping("/getAll")
    public ResponseEntity<String> getItems(){
        return inventoryService.getAllInventory();
    }

    @GetMapping("/getById/{id}")
    public ResponseEntity<String> getInventory(@PathVariable Long id){
        return inventoryService.getInventoryById(id);
    }

    @GetMapping("/{pageNum}")
    public ResponseEntity<Page<Item>> getInventoryByPage(@PathVariable int pageNum) {
        try {
            Pageable pageable = PageRequest.of(pageNum, Constants.itemOnEachPage);
            Page<Item> page = inventoryService.getInventoryByPage(pageable);

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
