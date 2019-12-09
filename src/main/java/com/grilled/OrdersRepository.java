package com.grilled;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;

import javax.validation.constraints.Null;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Repository
public class OrdersRepository {
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.S");

    @Autowired
    private JdbcTemplate jdbc;

    Takeaway findTakeaway(int id, @Null Login login) {
        try {

            String login_tlf = "";
            if(login != null) {
                login_tlf = " HAVING tlf = "+login.getTlf_type();
            }

            String query = ("SELECT order_takeaway.id as order_id, ordered_on, status, tlf, address_town FROM order_takeaway "+
                    "INNER JOIN client_list ON order_takeaway.client_ref = client_list.tlf " +
                    "INNER JOIN restaurant_list ON order_takeaway.restaurant_ref = restaurant_list.id " +
                    "WHERE order_takeaway.id = " + id + login_tlf + " ");
            SqlRowSet rs = jdbc.queryForRowSet(query);

            Takeaway takeaway = new Takeaway();

            while (rs.next()) {
                takeaway.setId(rs.getInt("order_id"));
                takeaway.setLogin(new Login());
                takeaway.getLogin().setTlf_type(rs.getString("tlf"));
                takeaway.setRestaurant(new Restaurant());
                takeaway.getRestaurant().setAddress_town(rs.getString("address_town"));
                takeaway.setOrder_time(LocalDateTime.parse(rs.getString("ordered_on"), formatter));
                takeaway.setStatus(rs.getInt("status"));
            }
            return takeaway;
        }  catch (Exception e) {
            return null;
        }
    }

    List<Dish> findTakeawayOrders(Takeaway takeaway) {
        try {
            String query = ("SELECT dish_list.name as dish_name, dish_list.id as dish_id, takeaway_ref, price FROM order_takeaway_list "+
                    "INNER JOIN order_takeaway ON order_takeaway.id = order_takeaway_list.takeaway_ref " +
                    "INNER JOIN dish_list ON dish_list.id = order_takeaway_list.dish_ref " +
                    "WHERE takeaway_ref = " + takeaway.getId() +" ");
            SqlRowSet rs = jdbc.queryForRowSet(query);

            List<Dish> dishList = new ArrayList<>();
            while (rs.next()) {
                Dish dish = new Dish();
                dish.setId(rs.getInt("dish_id"));
                dish.setName(rs.getString("dish_name"));
                dish.setPrice(rs.getDouble("price"));
                dishList.add(dish);
            }
            return dishList;

        }  catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    List<Takeaway> findAllTakeaways(@Null Restaurant restaurant, @Null Login login) {
        try {

            String restaurant_id = "";
            if(restaurant != null) {
                restaurant_id = "WHERE order_takeaway.restaurant_ref = "+restaurant.getId();
            }

            String login_tlf = "";
            if(login != null) {
                login_tlf = "WHERE tlf = "+login.getTlf_type();
            }

            String query = ("SELECT order_takeaway.id as order_id, ordered_on, status, tlf, address_town FROM order_takeaway "+
                    "INNER JOIN client_list ON order_takeaway.client_ref = client_list.tlf " +
                    "INNER JOIN restaurant_list ON order_takeaway.restaurant_ref = restaurant_list.id " +
                    login_tlf+
                    " ORDER BY ordered_on DESC");
            SqlRowSet rs = jdbc.queryForRowSet(query);

            List<Takeaway> takeawayList = new ArrayList<>();

            while (rs.next()) {
                Takeaway takeaway = new Takeaway();
                takeaway.setId(rs.getInt("order_id"));
                takeaway.setLogin(new Login());
                takeaway.getLogin().setTlf_type(rs.getString("tlf"));
                takeaway.setRestaurant(new Restaurant());
                takeaway.getRestaurant().setAddress_town(rs.getString("address_town"));
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
                "status = '" + takeaway.getStatus() + "' "+
                " WHERE id = " + takeaway.getId()+ " ");
    }

    Reservation findReservation(int id, @Null Login login) {
        try {

            String login_tlf = "";
            if(login != null) {
                login_tlf = " HAVING tlf = "+login.getTlf_type();
            }

            String query = ("SELECT order_table.id as order_id, ordered_on, guests_amount, ordered_for, tlf, address_town FROM order_table "+
                    "INNER JOIN client_list ON order_table.client_ref = client_list.tlf " +
                    "INNER JOIN restaurant_list ON order_table.restaurant_ref = restaurant_list.id " +
                    "WHERE order_table.id = " + id + login_tlf + " ");
            SqlRowSet rs = jdbc.queryForRowSet(query);

            Reservation reservation = new Reservation();
            while (rs.next()) {
                reservation.setId(rs.getInt("order_id"));
                reservation.setLogin(new Login());
                reservation.getLogin().setTlf_type(rs.getString("tlf"));
                reservation.setRestaurant(new Restaurant());
                reservation.getRestaurant().setAddress_town(rs.getString("address_town"));
                reservation.setOrder_time(LocalDateTime.parse(rs.getString("ordered_on"), formatter));
                reservation.setOrder_for(LocalDateTime.parse(rs.getString("ordered_for"), formatter));
                reservation.setGuests(rs.getInt("guests_amount"));
            }

            return reservation;
        } catch (Exception e) {
            return null;
        }
    }

    List<Reservation> findAllReservations(@Null Restaurant restaurant, @Null Login login) {
        try {

            String restaurant_id = "";
            if(restaurant != null) {
                restaurant_id = "WHERE order_table.restaurant_ref = "+restaurant.getId();
            }

            String login_tlf = "";
            if(login != null) {
                login_tlf = "WHERE tlf = "+login.getTlf_type();
            }


            String query = ("SELECT order_table.id as order_id, ordered_on, guests_amount, ordered_for, tlf, address_town FROM order_table "+
                    "INNER JOIN client_list ON order_table.client_ref = client_list.tlf " +
                    "INNER JOIN restaurant_list ON order_table.restaurant_ref = restaurant_list.id " +
                    login_tlf+

                    " ORDER BY order_id DESC");
            SqlRowSet rs = jdbc.queryForRowSet(query);

            List<Reservation> reservationList = new ArrayList<>();
            while (rs.next()) {
                Reservation reservation = new Reservation();
                reservation.setId(rs.getInt("order_id"));
                reservation.setLogin(new Login());
                reservation.getLogin().setTlf_type(rs.getString("tlf"));
                reservation.setRestaurant(new Restaurant());
                reservation.getRestaurant().setAddress_town(rs.getString("address_town"));
                reservation.setOrder_time(LocalDateTime.parse(rs.getString("ordered_on"), formatter));
                reservation.setOrder_for(LocalDateTime.parse(rs.getString("ordered_for"), formatter));
                reservation.setGuests(rs.getInt("guests_amount"));
                reservationList.add(reservation);
            }

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
                "client_ref=" + reservation.getLogin().getTlf_type() + ", " +
                "guests_amount=" + reservation.getGuests() + ", " +
                "WHERE id = " + reservation.getId()+ " ");
    }

    void addReservation(Reservation reservation) {
        jdbc.update(
                "INSERT INTO order_table (restaurant_ref, guests_amount, ordered_for, client_ref) VALUES (?, ?, ?, ?)",
                reservation.getRestaurant().getId(), reservation.getGuests(), reservation.getOrder_for(), reservation.getLogin().getTlf_type()
        );
    }

    void addTakeaway(Takeaway takeaway) {
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbc.update(connection -> {PreparedStatement ps = connection.prepareStatement("INSERT INTO order_takeaway (restaurant_ref, client_ref) VALUES (?, ?) ",
            Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1, takeaway.getRestaurant().getId());
            ps.setString(2, takeaway.getLogin().getTlf_type());
            return ps;
        }, keyHolder);

        jdbc.batchUpdate("INSERT INTO order_takeaway_list (takeaway_ref, dish_ref) VALUES (?, ?) ",
            takeaway.getOrder(), 10, (ps, dish) -> {
            ps.setInt(1, Objects.requireNonNull(keyHolder.getKey()).intValue());
            ps.setInt(2, dish.getId());
        });
    }
}

