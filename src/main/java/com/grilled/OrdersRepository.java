package com.grilled;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Repository
public class OrdersRepository {
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.S");

    @Autowired
    private JdbcTemplate jdbc;

    List<Order> findAllOrders() {
        try {
            String query = ("SELECT order_invoice.id as order_id, date_time, status, tlf FROM order_invoice "+
                    "INNER JOIN client_list ON order_invoice.client_ref = client_list.id"+
                    " ORDER BY order_id DESC");
            SqlRowSet rs = jdbc.queryForRowSet(query);

            List<Order> orderList = new ArrayList<>();

            while (rs.next()) {
                Order order = new Order();
                Client client = new Client();
                client.setTlf(rs.getString("tlf"));
                order.setId(rs.getInt("order_id"));
                order.setClient(client);
                order.setOrder_time(LocalDateTime.parse(rs.getString("date_time"), formatter));
                order.setStatus(rs.getInt("status"));
                orderList.add(order);
            }
            return orderList;
        } catch (Exception e) {
            return null;
        }
    }

    void deleteOrder(int id) {
        jdbc.update("DELETE FROM order_invoice WHERE id = " +id +" ");
    }

    void updateOrder(Order order) {
        jdbc.update("UPDATE order_invoice SET " +
                "date_time='" + order.getOrder_time() + "', " +
                "client_ref=" + order.getClient().getId() + ", " +
                "status=" + order.getStatus() + ", " +
                "WHERE id = " + order.getId()+ " ");
    }
}
