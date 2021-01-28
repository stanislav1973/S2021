package com.example.taco.data;

import com.example.taco.Ingredient;
import com.example.taco.Taco;
import org.springframework.beans.factory.annotation.Autowired;
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
    public Taco save(List<String> save,String name){
        Taco taco = new Taco();
        taco.setName(name);
        Number tacoId = saveTacoInfo(taco);
        taco.setId(tacoId);
        for (String s : save) {
            saveIngredientToTaco(s, tacoId);
        }
        return taco;
    }

    private void saveIngredientToTaco( String ingredient, Number tacoId) {
        jdbc.update("insert into Taco_Ingredients (taco,ingredient) values (?,?)", tacoId, ingredient);
    }

    private Number saveTacoInfo(Taco taco){
        taco.setCreateAt(new Date());
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbc.update(con -> {
            PreparedStatement ps = con.prepareStatement("insert into Taco (name,createAt) values (?, ?)", Statement.RETURN_GENERATED_KEYS);

            ps.setString(1, taco.getName());
            ps.setTimestamp(2, new Timestamp(taco.getCreateAt().getTime()));
            return ps;

        }, keyHolder);
        return keyHolder.getKey();
    }
}