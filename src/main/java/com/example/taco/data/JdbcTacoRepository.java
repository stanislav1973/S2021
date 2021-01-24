package com.example.taco.data;

import com.example.taco.Ingredient;
import com.example.taco.Taco;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.PreparedStatementCreatorFactory;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Repository
public class JdbcTacoRepository implements TacoRepository {
    private JdbcTemplate jdbc;


    public JdbcTacoRepository(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    @Override
    public Taco save(Taco taco){
        Number tacoId = saveTacoInfo(taco);
        taco.setId(tacoId);
        for (String ingredient : taco.getIngredients()) {
            saveIngredientToTaco(ingredient, tacoId);
        }
        return taco;
    }

    private void saveIngredientToTaco(String ingredient, Number tacoId) {
        jdbc.update("insert into Taco_Ingredients (id,ingredient) values (?,?)", tacoId, ingredient);
    }

    private Number saveTacoInfo(Taco taco){
        taco.setCreatedAt(new Date());
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbc.update(con -> {
            PreparedStatement ps = con.prepareStatement("insert into Taco (name, createdAt) values (?, ?)", Statement.RETURN_GENERATED_KEYS);

            ps.setString(1, taco.getName());
            ps.setTimestamp(2, new Timestamp(taco.getCreatedAt().getTime()));
            return ps;

        }, keyHolder);
        return keyHolder.getKey();
    }
}