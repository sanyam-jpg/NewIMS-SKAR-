package com.example.demo.service;

import com.example.demo.model.Item;
import com.example.demo.repository.InventoryRepository;
import configration.ItemType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class CreateCreateInventoryServiceImpl implements CreateInventoryService {

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

        item.setCreationDate(setTodayDateTime());
        item.setLastUpdatedDate(setTodayDateTime());

        repository.save(item);

        return new ResponseEntity<>(Long.toString(item.getId()), HttpStatus.OK);
    }

    //TODO : validate attribute can hold only json





    // helper functions

    boolean validType(String type){
        if(type==null || type.isEmpty()){
            return false;
        }

        for(ItemType itemtype : ItemType.values() ){
            if(type.toUpperCase().equals(itemtype.name())) return true;
        }

        return false;
    }

    String setTodayDateTime(){
        return LocalDateTime.now().toString();
    }



}
