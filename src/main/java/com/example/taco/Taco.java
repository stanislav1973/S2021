package com.example.taco;

import lombok.Data;

import java.util.Date;
import java.util.List;
@Data
public class Taco {
    private Number id;
    private Date createAt;
    private String name;
    private List<Ingredient> ingredients;
}