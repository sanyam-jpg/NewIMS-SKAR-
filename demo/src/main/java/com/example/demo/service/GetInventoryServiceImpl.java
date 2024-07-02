package com.example.demo.service;

import com.example.demo.repository.InventoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GetInventoryServiceImpl implements GetInventoryService{

    @Autowired
    private InventoryRepository repository;

    @Override
    public String getAllInventory(){
        return repository.findAll().toString();
    }

    @Override
    public String getInventory(Long id){
        return "";
    }
}
