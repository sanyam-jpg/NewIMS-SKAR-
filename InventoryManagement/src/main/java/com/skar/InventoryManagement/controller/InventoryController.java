package com.skar.InventoryManagement.controller;

import com.skar.InventoryManagement.model.Item;
import com.skar.InventoryManagement.service.InventoryService;
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

    @PutMapping("/updateStatus/{id}")
    public ResponseEntity<String> updateStatus(@PathVariable Long id, @RequestParam String status) {
        return inventoryService.updateStatus(id, status);
    }

    @PutMapping("/updatePricing/{id}")
    public ResponseEntity<String> updatePricing(@PathVariable Long id, @RequestParam long costPrice, @RequestParam long sellingPrice) {
        return inventoryService.updatePricing(id, costPrice, sellingPrice);
    }

    @PutMapping("/updateAttribute/{id}")
    public ResponseEntity<String> updateAttribute(@PathVariable Long id, @RequestBody String attribute) {
        return inventoryService.updateAttribute(id, attribute);
    }

}
