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
    public ResponseEntity<String> create(Item item){

        //null response indicate that item passed validation checks else we get the error response to send back
        ResponseEntity<String> checkValidity = getResponseEntity(item);
        if (checkValidity != null) return checkValidity;

        item.setCreationDate(setTodayDateTime());
        item.setLastUpdatedDate(setTodayDateTime());
        item.setStatus();

        try{
            repository.save(item);
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        }

        String response = "{id: " + item.getId() + ",status: " +200L + "}";

        return new ResponseEntity<>(response, HttpStatus.OK);
    }


    //GET//

    @Override
    public ResponseEntity<String> getAll(){
        try{
            List<Item> inventory =  repository.findAll();
            String jsonResponse = objectMapper.writeValueAsString(inventory);
            return ResponseEntity.ok(jsonResponse);
        }
        catch (Exception e){
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        }
    }

    @Override
    public ResponseEntity<String> get(Long id){
        Optional<Item> item = repository.findById(id);
        try{
            if (item.isPresent()) {
                String jsonResponse = objectMapper.writeValueAsString(item.get());
                return ResponseEntity.ok(jsonResponse);
            }
            else {
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


        if (id == null || id <= 0) {
            return new ResponseEntity<>("Invalid ID. ID must be greater than 0.", HttpStatus.BAD_REQUEST);
        }




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

            JsonNode jsonNode = null;
            if (item.getAttribute() != null) {
                try {
                    ObjectMapper objectMapper = new ObjectMapper();
                    jsonNode = objectMapper.readTree(item.getAttribute().toString());
                } catch (Exception e) {
                    return new ResponseEntity<>("Invalid attribute JSON", HttpStatus.BAD_REQUEST);
                }
            }
            if(jsonNode != null)existingItem.setAttribute(item.getAttribute());

            existingItem.setStatus();

            repository.save(existingItem);

            return new ResponseEntity<>("Item updated", HttpStatus.OK);
        }
        else {
            return new ResponseEntity<>("Item not found", HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public ResponseEntity<String> updateStatus(Long id, String status) {
        if(!"BOOKED".equalsIgnoreCase(status) && !"SOLD".equalsIgnoreCase(status)){
            return new ResponseEntity<>("Invalid status.Status can only be updated to BOOKED or SOLD.", HttpStatus.BAD_REQUEST);
        }


        Optional<Item> existingItemOptional = repository.findById(id);

        if (existingItemOptional.isEmpty()) {
            return new ResponseEntity<>("Item not found", HttpStatus.NOT_FOUND);
        }

        Item existingItem = existingItemOptional.get();
        existingItem.setStatus(status);
        existingItem.setLastUpdatedDate(setTodayDateTime());

        try{
            repository.save(existingItem);
        }
        catch(Exception e){
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        }

        return new ResponseEntity<>("Status updated", HttpStatus.OK);

    }

    @Override
    public ResponseEntity<String> updatePricing(Long id, Long costPrice, Long sellingPrice) {

        if((costPrice != null && costPrice <= 1) || (sellingPrice != null && sellingPrice <= 1)){
            return new ResponseEntity<>("costPrice and sellingPrice must be greater than 0", HttpStatus.BAD_REQUEST);
        }


        Optional<Item> existingItemOptional = repository.findById(id);


        if (existingItemOptional.isEmpty()) {
           return new ResponseEntity<>("Item not found", HttpStatus.NOT_FOUND);
        }
        else {
          Item existingItem = existingItemOptional.get();
          if (costPrice != null)existingItem.setCostPrice(costPrice);
          if (sellingPrice != null)existingItem.setSellingPrice(sellingPrice);
          existingItem.setLastUpdatedDate(setTodayDateTime());

          repository.save(existingItem);

          return new ResponseEntity<>("Pricing updated", HttpStatus.OK);
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

        if(item.getCostPrice()<=0 || item.getSellingPrice()<=0){
            return new ResponseEntity<>("Invalid Cost Price", HttpStatus.BAD_REQUEST);
        }

        if(item.getAttribute().toString().isEmpty()){
            return new ResponseEntity<>("Invalid Attribute", HttpStatus.BAD_REQUEST);
        }
        if(!"CREATED".equalsIgnoreCase(item.getStatus()) && !"BOOKED".equalsIgnoreCase(item.getStatus()) && !"SOLD".equalsIgnoreCase(item.getStatus())){
            return new ResponseEntity<>("Invalid status.Status can only be updated to BOOKED or SOLD.", HttpStatus.BAD_REQUEST);
        }

        return null;
    }

    String setTodayDateTime(){

        return LocalDateTime.now().toString();
    }



}
