package com.example.demo.controller;

import com.example.demo.model.Item;
import com.example.demo.service.CreateInventoryService;
import com.example.demo.service.GetInventoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Component
@RestController("/inventories")
public class InventoryController {

    @Autowired
    private CreateInventoryService createInventoryService;

    @Autowired
    private GetInventoryService getInventoryService;




    @GetMapping("/getAll")
    public String getItems(){
        return getInventoryService.getAllInventory();
    }



    @PostMapping("/create")
    public ResponseEntity<String> createInventory(@RequestBody Item item){

        return createInventoryService.createInventory(item);

    }
}
