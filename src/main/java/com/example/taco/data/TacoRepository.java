package com.example.taco.data;

import com.example.taco.Taco;

import java.sql.SQLException;
import java.util.List;

public interface TacoRepository {
    Taco save(List<String> save,String name) throws SQLException;
}
