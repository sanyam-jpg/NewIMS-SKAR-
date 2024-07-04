package com.skar.InventoryManagement.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.skar.InventoryManagement.model.Item;
import com.skar.InventoryManagement.repository.InventoryRepository;
import com.skar.InventoryManagement.service.InventoryService;
import org.junit.jupiter.api.Test;
import org.junit.platform.engine.TestExecutionResult;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.springframework.mock.http.server.reactive.MockServerHttpRequest.post;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(InventoryController.class)
class InventoryControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    InventoryService inventoryService;

    /////CREATE_TEST/////

    @Test
    public void createInventory() throws Exception {
        Item item = new Item();
        item.setCreatedBy(2);
        Map<String,String> map = new HashMap<>();
        map.put("name","audi");
        map.put("vin","ka12333");
        JsonNode attribute = objectMapper.valueToTree(map);
        item.setAttribute(attribute);
        item.setCostPrice(200L);
        item.setSellingPrice(400L);
        item.setLocation("gurgoan");
        item.setType("Car");

        String response = "{id: " + item.getId() + ",status: " +200L + "}";
        ResponseEntity<String> res= new ResponseEntity<>(response, HttpStatus.OK);

        when(inventoryService.create(item)).thenReturn(res);

         mockMvc.perform(MockMvcRequestBuilders.post("/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(item)))
                 .andExpect(status().isOk())
                 .andExpect((ResultMatcher) content().string(response));


    }
    @Test
    void getAll() {
    }
}