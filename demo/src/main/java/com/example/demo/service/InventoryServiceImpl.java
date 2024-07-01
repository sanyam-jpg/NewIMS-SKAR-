package com.example.demo.service;

import com.example.demo.model.Item;
import com.example.demo.repository.InventoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class InventoryServiceImpl implements InventoryService {

    @Autowired
    private InventoryRepository repository;

    @Override
    public String createInventory(Item item){
        repository.save(item);

        return "Sucess";
    }

    @Override
    public String getAllInventory(){
        return repository.findAll().toString();
    }
}
