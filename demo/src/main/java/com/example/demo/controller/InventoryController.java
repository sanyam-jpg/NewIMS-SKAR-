package com.example.demo.controller;

import com.example.demo.model.Item;
import com.example.demo.service.InventoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

@Component
@RestController("/inventories")
public class InventoryController {

    @Autowired
    private InventoryService inventoryService;



    @GetMapping("/getAll")
    public String getItems(){
        return inventoryService.getAllInventory();
    }



    @PostMapping("/create")
    public ResponseEntity<String> createInventory(@RequestBody Item item){

        return inventoryService.createInventory(item);

    }

    @PutMapping("/update/{id}")
    public ResponseEntity<String> updateInventory(@PathVariable Long id, @RequestBody Item item) {
        return inventoryService.updateInventory(id, item);
    }
}
