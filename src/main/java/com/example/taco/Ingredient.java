package com.example.taco;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Data
@RequiredArgsConstructor

public class Ingredient {
    private final String id;
    private final String name;
    private final Type type;
    public  enum Type {
        WRAP, PROTEIN
    }

}