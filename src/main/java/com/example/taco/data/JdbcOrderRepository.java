package com.example.taco.data;

import com.example.taco.Order;
import com.example.taco.Taco;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
@Repository
public class JdbcOrderRepository implements OrderRepository {
    private SimpleJdbcInsert orderInsert;
    private SimpleJdbcInsert orderTacoInsert;
    private ObjectMapper objectMapper;
    @Autowired
    public JdbcOrderRepository(JdbcTemplate jdbc) {
        this.orderInsert = new SimpleJdbcInsert(jdbc).withTableName("Taco_Order").usingGeneratedKeyColumns("id");
        this.orderTacoInsert = new SimpleJdbcInsert(jdbc).withTableName("Taco_Order_Tacos");
        this.objectMapper = new ObjectMapper();
    }

    @Override
    public Order save(Order order) {
        order.setPlaceAt(new Date());
        long orderId = saveOrderDetails(order);
        order.setId(orderId);
        List<Taco> tacos = order.getTacos();
        for (Taco taco : tacos) {
            saveTacoToOrders(taco, orderId);
        }
        return order;
    }

    private void saveTacoToOrders(Taco taco, long orderId) {
        Map<String, Object> values = new HashMap<>();
        values.put("tacoOrder",orderId);
        values.put("taco", taco.getId());
      orderTacoInsert.execute(values);
    }

    private long saveOrderDetails(Order order) {
        @SuppressWarnings("unchecked")
        Map<String, Object> values = objectMapper.convertValue(order, Map.class);
        values.put("placeAt", order.getPlaceAt());
        values.put("dName",order.getDName());
        values.put("dStreet",order.getDStreet());
        long orderId = orderInsert.executeAndReturnKey(values).longValue();
        return orderId;
    }

}
