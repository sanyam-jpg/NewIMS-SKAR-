package com.skar.InventoryManagement.controller;

import com.skar.InventoryManagement.model.Item;
import com.skar.InventoryManagement.service.InventoryService;
import configration.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController()
@RequestMapping("/inventories")
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

    @GetMapping("/items")
    public ResponseEntity<Page<Item>> getByPage(@RequestParam(required = false) int pageNum) {
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
    private ResponseEntity<String> createInventory(@RequestBody Item item){

        return inventoryService.create(item);

    }

    //TODO Make ALL These Patch Mapping

    @PutMapping("/update/{id}")
    public ResponseEntity<String> updateInventory(@PathVariable Long id, @RequestBody Item item) {
        return inventoryService.updateInventory(id, item);
    }

    @PutMapping("/updateStatus/{id}")
    public ResponseEntity<String> updateStatus(@PathVariable Long id, @RequestParam String status) {
        return inventoryService.updateStatus(id, status);
    }


    @PutMapping("/updatePricing/{id}")
    public ResponseEntity<String> updatePricing(@PathVariable Long id, @RequestParam(required = false) long costPrice, @RequestParam(required = false) long sellingPrice) {
        return inventoryService.updatePricing(id, costPrice, sellingPrice);
    }

    @PutMapping("/updateAttribute/{id}")
    public ResponseEntity<String> updateAttribute(@PathVariable Long id, @RequestBody String attribute) {
        return inventoryService.updateAttribute(id, attribute);
    }

}
