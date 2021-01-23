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
    public Taco save(Taco taco) throws SQLException {
        long tacoId = saveTacoInfo(taco);
        taco.setId(tacoId);
        for (String ingredient : taco.getIngredients()) {
            saveIngredientToTaco(ingredient,tacoId);
        }
        return taco;
    }

    private void saveIngredientToTaco(String ingredient,long tacoId) {
            jdbc.update("insert into Taco_Ingredients (id,ingredient) values (?,?)",tacoId,ingredient);
    }

    private long saveTacoInfo(Taco taco) throws SQLException {
        taco.setCreatedAt(new Date());
        String url = "jdbc:mysql://localhost:3306/taco";
        String user = "root";
        String password = "abcd123s";
        Connection con = DriverManager.getConnection(url, user, password);

                PreparedStatement ps = con.prepareStatement("insert into Taco (name, createdAt) values (?, ?)", Statement.RETURN_GENERATED_KEYS);

                ps.setString(1,taco.getName());
                ps.setTimestamp(2,new Timestamp(taco.getCreatedAt().getTime()));
                ps.execute();
                ResultSet rs = ps.getGeneratedKeys();
        int idValue = 0;
                if (rs.next()) {
                   idValue = rs.getInt(1);}
                   return idValue;

    }
}
