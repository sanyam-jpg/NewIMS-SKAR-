package com.example.demo.service;

import com.example.demo.model.Item;
import com.example.demo.repository.InventoryRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import configration.ItemType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class InventoryServiceImpl implements InventoryService {

    @Autowired
    private InventoryRepository repository;

    @Autowired
    private ObjectMapper objectMapper;


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
    public ResponseEntity<String> getAll(){
        try{
            List<Item> inventory =  repository.findAll();
            String jsonResponse = objectMapper.writeValueAsString(inventory);
            return ResponseEntity.ok(jsonResponse);
        }
        catch (Exception e){
            return ResponseEntity.status(500).body(e.getMessage());
        }
    }


    @Override
    public ResponseEntity<String> get(Long id){
        Optional<Item> item = repository.findById(id);
        try{
            if (item.isPresent()) {
                String jsonResponse = objectMapper.writeValueAsString(item.get());
                return ResponseEntity.ok(jsonResponse);
            } else {
                return ResponseEntity.status(404).body("Inventory not found");
            }
        }
        catch(Exception e){
            return ResponseEntity.status(500).body(e.getMessage());
        }
    }


    @Override
    public Page<Item> getPage(Pageable pageable) {
        try {
            return repository.findAll(pageable);
        } catch (Exception e) {
            throw new RuntimeException("Failed to fetch inventory", e);
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
