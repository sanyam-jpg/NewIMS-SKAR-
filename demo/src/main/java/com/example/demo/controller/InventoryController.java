package com.example.demo.controller;

import com.example.demo.model.Item;
import com.example.demo.service.InventoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Component
@RestController("/inventories")
public class InventoryController {

    @Autowired
    private InventoryService inventoryService;


    @GetMapping("/")
    public String getItems(){
        return inventoryService.getAllInventory();
    }



    @PostMapping("/create")
    public String createInventory(@RequestBody Item item){
//        System.out.println(item.getLocation());
        return inventoryService.createInventory(item);
    }
}
