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

    @GetMapping("/getById/{id}")
    public String getInventory(@RequestParam Long id){
        return inventoryService.getInventoryById(id);
    }

    @PostMapping("/create")
    public ResponseEntity<String> createInventory(@RequestBody Item item){

        return inventoryService.createInventory(item);

    }
}
