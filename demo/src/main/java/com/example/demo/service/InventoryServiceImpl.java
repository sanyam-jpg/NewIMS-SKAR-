package com.example.demo.service;

import com.example.demo.model.Item;
import com.example.demo.repository.InventoryRepository;
import configration.ItemType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class InventoryServiceImpl implements InventoryService {

    @Autowired
    private InventoryRepository repository;

    @Override
    public ResponseEntity<String> createInventory(Item item){


        if(!validType(item.getType())){
            String response = item.getType() + " is not a valid type of item";
            return new ResponseEntity<>(response,HttpStatus.FORBIDDEN);
        }

        if(!item.getLocation().matches("[a-zA-Z]+")){
            return new ResponseEntity<>("Invalid Location",HttpStatus.BAD_REQUEST);
        }





        repository.save(item);

        return new ResponseEntity<>(Long.toString(item.getId()), HttpStatus.OK);
    }

    @Override
    public String getAllInventory(){
        return repository.findAll().toString();
    }



    // helper functions to


    boolean validType(String type){
        if(type==null || type.isEmpty()){
            return false;
        }

        for(ItemType itemtype : ItemType.values() ){
            if(type.toUpperCase().equals(itemtype.name())) return true;
        }

        return false;
    }


}