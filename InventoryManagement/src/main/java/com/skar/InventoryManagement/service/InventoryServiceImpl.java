package com.skar.InventoryManagement.service;

import com.skar.InventoryManagement.model.Item;
import com.skar.InventoryManagement.repository.InventoryRepository;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import configration.ItemType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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


        ResponseEntity<String> response1 = getResponseEntity(item);
        if (response1 != null) return response1;

        item.setCreationDate(setTodayDateTime());
        item.setLastUpdatedDate(setTodayDateTime());
        item.setStatus();

        repository.save(item);

        Map<String,Long> response = new HashMap<>();
        response.put("id",item.getId());


        return new ResponseEntity<>(response.toString(), HttpStatus.OK);
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


   //UPDATE//
    @Override
    public ResponseEntity<String> updateInventory(Long id, Item item) {
        Optional<Item> existingItemOptional = repository.findById(id);

        if (existingItemOptional.isPresent()) {
            Item existingItem = existingItemOptional.get();

            ResponseEntity<String> response = getResponseEntity(item);
            if (response != null) return response;


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


    @Override
    public ResponseEntity<String> updateStatus(Long id, String status) {
        Optional<Item> existingItemOptional = repository.findById(id);

        if (existingItemOptional.isPresent()) {
            Item existingItem = existingItemOptional.get();
            existingItem.setStatus(status);
            existingItem.setLastUpdatedDate(setTodayDateTime());

            repository.save(existingItem);

            return new ResponseEntity<>("Status updated", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Item not found", HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public ResponseEntity<String> updatePricing(Long id, long costPrice, long sellingPrice) {
        Optional<Item> existingItemOptional = repository.findById(id);

        if (existingItemOptional.isPresent()) {
            Item existingItem = existingItemOptional.get();
            existingItem.setCostPrice(costPrice);
            existingItem.setSellingPrice(sellingPrice);
            existingItem.setLastUpdatedDate(setTodayDateTime());

            repository.save(existingItem);

            return new ResponseEntity<>("Pricing updated", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Item not found", HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public ResponseEntity<String> updateAttribute(Long id, String attribute) {
        Optional<Item> existingItemOptional = repository.findById(id);

        if (existingItemOptional.isPresent()) {
            Item existingItem = existingItemOptional.get();

            try {
                ObjectMapper objectMapper = new ObjectMapper();
                JsonNode jsonNode = objectMapper.readTree(attribute);
                existingItem.setAttribute(jsonNode);
                existingItem.setLastUpdatedDate(setTodayDateTime());

                repository.save(existingItem);

                return new ResponseEntity<>("Attribute updated", HttpStatus.OK);
            } catch (Exception e) {
                return new ResponseEntity<>("Invalid attribute JSON", HttpStatus.BAD_REQUEST);
            }
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

    private ResponseEntity<String> getResponseEntity(Item item) {
        if (!validType(item.getType())) {
            String response = item.getType() + " is not a valid type of item";
            return new ResponseEntity<>(response, HttpStatus.FORBIDDEN);
        }

        if (!item.getLocation().matches("[a-zA-Z]+")) {
            return new ResponseEntity<>("Invalid Location", HttpStatus.BAD_REQUEST);
        }
        return null;
    }

    String setTodayDateTime(){
        return LocalDateTime.now().toString();
    }



}
