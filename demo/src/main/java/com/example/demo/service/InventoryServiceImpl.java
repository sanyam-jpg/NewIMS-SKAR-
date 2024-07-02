package com.example.demo.service;

import com.example.demo.model.Item;
import com.example.demo.repository.InventoryRepository;
import configration.ItemType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class InventoryServiceImpl implements InventoryService {

    @Autowired
    private InventoryRepository repository;


    //// CREATE///////
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
        item.setStatus();

        repository.save(item);

        return new ResponseEntity<>(Long.toString(item.getId()), HttpStatus.OK);
    }

    //TODO : validate attribute can hold only json


    //GET//

    @Override
    public String getAllInventory(){
        return repository.findAll().toString();
    }

    @Override
    public String getInventory(Long id){
        return "";
    }


   //UPDATE//
    @Override
    public ResponseEntity<String> updateInventory(Long id, Item item) {
        Optional<Item> existingItemOptional = repository.findById(id);

        if (existingItemOptional.isPresent()) {
            Item existingItem = existingItemOptional.get();

            if (!validType(item.getType())) {
                String response = item.getType() + " is not a valid type of item";
                return new ResponseEntity<>(response, HttpStatus.FORBIDDEN);
            }

            if (!item.getLocation().matches("[a-zA-Z]+")) {
                return new ResponseEntity<>("Invalid Location", HttpStatus.BAD_REQUEST);
            }


            existingItem.setType(item.getType());
            existingItem.setLocation(item.getLocation());
            existingItem.setCostPrice(item.getCostPrice());
            existingItem.setSellingPrice(item.getSellingPrice());
            existingItem.setLastUpdatedDate(setTodayDateTime());
            existingItem.setAttribute(item.getAttribute());
            existingItem.setStatus();

            repository.save(existingItem);

            return new ResponseEntity<>("Item updated", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Item not found", HttpStatus.NOT_FOUND);
        }
    }



    // HELPER FUNCTION///

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
