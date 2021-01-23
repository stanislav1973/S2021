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
    public Taco save(Taco taco) {
        long tacoId = saveTacoInfo(taco);
        taco.setId(tacoId);
        for (String ingredient : taco.getIngredients()) {
            saveIngredientToTaco(ingredient, tacoId);
        }
        return taco;
    }

    private void saveIngredientToTaco(String ingredient, long tacoId) {
            jdbc.update("insert into Taco_Ingredients (id, ingredient) values (?, ?)", tacoId, ingredient);
    }

    private long saveTacoInfo(Taco taco) {
        taco.setCreatedAt(new Date());
//        PreparedStatementCreator psc =
//                new PreparedStatementCreatorFactory("insert into Taco (name, createdAt) values (?, ?)",
//                        Types.VARCHAR, Types.TIMESTAMP).newPreparedStatementCreator(
//                        Arrays.asList(taco.getName(), new Timestamp(taco.getCreatedAt().getTime())));
        KeyHolder keyHolder = new GeneratedKeyHolder();
        long key = jdbc.update(new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                PreparedStatement ps = connection.prepareStatement("insert into Taco (name, createdAt) values (?, ?)", Statement.RETURN_GENERATED_KEYS);
                ps.setString(1,taco.getName());
                ps.setTimestamp(2,new Timestamp(taco.getCreatedAt().getTime()));
                return ps;
            }
        },keyHolder);

        return key;
    }
}
