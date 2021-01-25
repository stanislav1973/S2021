package com.example.taco;

import lombok.Data;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
public class Order{
    private Long id;
    private String names;
    private String street;
    private Date createdAt;
    private List<Taco> tacos = new ArrayList<>();
    public void addDesign(Taco design) {
        this.tacos.add(design);
    }
//    private String city;
//    private String stage;
//    private String zip;
//    private String ccNumber;
//    private String ccExpiration;
//    private String ccCVV;


}