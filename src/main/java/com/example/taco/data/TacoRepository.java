package com.example.taco.data;

import com.example.taco.Taco;

import java.sql.SQLException;

public interface TacoRepository {
    Taco save(Taco taco) throws SQLException;
}
