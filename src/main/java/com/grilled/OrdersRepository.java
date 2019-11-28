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

    List<Takeaway> findAllTakeaways(int restaurant) {
        try {
            String query = ("SELECT order_takeaway.id as order_id, ordered_on, status, tlf FROM order_takeaway "+
                    "INNER JOIN client_list ON order_takeaway.client_ref = client_list.id " +
                    "INNER JOIN restaurant_list ON order_takeaway.restaurant_ref = restaurant_list.id " +
                    "WHERE order_takeaway.restaurant_ref = "+restaurant+
                    " ORDER BY order_id DESC");
            SqlRowSet rs = jdbc.queryForRowSet(query);

            List<Takeaway> takeawayList = new ArrayList<>();

            while (rs.next()) {
                Takeaway takeaway = new Takeaway();
                Client client = new Client();
                client.setTlf(rs.getString("tlf"));
                takeaway.setId(rs.getInt("order_id"));
                takeaway.setClient(client);
                takeaway.setOrder_time(LocalDateTime.parse(rs.getString("ordered_on"), formatter));
                takeaway.setStatus(rs.getInt("status"));
                takeawayList.add(takeaway);
            }
            return takeawayList;
        } catch (Exception e) {
            return null;
        }
    }

    void deleteTakeaway(int id) {
        jdbc.update("DELETE FROM order_takeaway WHERE id = " +id +" ");
    }

    void updateTakeaway(Takeaway takeaway) {
        jdbc.update("UPDATE order_takeaway SET " +
                "ordered_on='" + takeaway.getOrder_time() + "', " +
                "restaurant_ref='" + takeaway.getRestaurant().getId() + "', " +
                "client_ref=" + takeaway.getClient().getId() + ", " +
                "status=" + takeaway.getStatus() + ", " +
                "WHERE id = " + takeaway.getId()+ " ");
    }

    List<Reservation> findAllReservations(int restaurant) {
        try {
            String query = ("SELECT order_table.id as order_id, ordered_on, guests_amount, ordered_for, tlf FROM order_takeaway "+
                    "INNER JOIN client_list ON order_table.client_ref = client_list.id " +
                    "INNER JOIN restaurant_list ON order_table.restaurant_ref = restaurant_list.id " +
                    "WHERE order_table.restaurant_ref = "+restaurant+
                    " ORDER BY order_id DESC");
            SqlRowSet rs = jdbc.queryForRowSet(query);

            List<Reservation> reservationList = new ArrayList<>();

            return reservationList;
        } catch (Exception e) {
            return null;
        }


    }

    void deleteReservation(int id) {
        jdbc.update("DELETE FROM order_table WHERE id = " +id +" ");
    }

    void updateReservation(Reservation reservation) {
        jdbc.update("UPDATE order_takeaway SET " +
                "ordered_for='" + reservation.getOrder_for() + "', " +
                "restaurant_ref='" + reservation.getRestaurant().getId() + "', " +
                "client_ref=" + reservation.getClient().getId() + ", " +
                "guests_amount=" + reservation.getGuests() + ", " +
                "WHERE id = " + reservation.getId()+ " ");
    }
}
