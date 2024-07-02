package com.example.demo.model;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import configration.ItemStatus;
import jakarta.persistence.*;
import lombok.Data;


@Data
@Entity
public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column private String location;

    @Column private long createdBy;

    @Column private String Type;

    @Column private long costPrice;

    @Column private long sellingPrice;

    @Column private String creationDate;

    @Column private String lastUpdatedDate;

    @Column private String attribute;

    @Column private String status;



    public void setAttribute(JsonNode attribute) {

        try{
            ObjectMapper objectMapper = new ObjectMapper();
            this.attribute = objectMapper.writeValueAsString(attribute);
        }
        catch(Exception e){
            throw new RuntimeException("unable to serialize JSON");
        }
    }

    public JsonNode getAttribute() {

        try{
            ObjectMapper objectMapper = new ObjectMapper();

            return objectMapper.readTree(this.attribute);
        }
        catch (Exception e){
            throw new RuntimeException("unable to deserialize JSON");
        }
    }

    public void setStatus(){
        this.status = ItemStatus.CREATED.toString();
    }

}
