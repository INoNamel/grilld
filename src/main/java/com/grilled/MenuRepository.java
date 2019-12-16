package com.grilled;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Repository;

import javax.validation.constraints.Null;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.time.LocalTime;
import java.util.List;
import java.util.ArrayList;
import java.util.Objects;

@Repository
public class MenuRepository {

    @Autowired
    private JdbcTemplate jdbc;

    Dish findDish(int id) {
        try {
            String query = ("SELECT dish_list.id as id, meal_type.id as meal_id, name, type, description, price, available " +
                    "FROM dish_list " +
                    "INNER JOIN meal_type ON dish_list.meal_ref = meal_type.id " +
                    "WHERE id = "+id+" ");
            SqlRowSet rs = jdbc.queryForRowSet(query);
            Dish dish = new Dish();

            while (rs.next()) {
                Meal meal = new Meal();
                meal.setId(rs.getInt("meal_id"));
                meal.setType(rs.getString("type"));

                dish.setId(rs.getInt("id"));
                dish.setName(rs.getString("name"));
                dish.setMeal(meal);
                dish.setDesc(rs.getString("description"));
                dish.setPrice(rs.getDouble("price"));
                dish.setAvailable(rs.getBoolean("available"));
            }

            return dish;
        } catch (Exception e) {
            return null;
        }
    }

    List<Dish> findAllDishes(boolean display, @Nullable String like) {
        try {

            String show = "";
            String search = "";

            if(display) {
                show = " WHERE available IS true ";
            }
            if(like != null) {
                search = " AND type LIKE '%"+like+"%' ";
            }

            String query = ("SELECT dish_list.id as id, meal_type.id as meal_id, name, type, description, price, available " +
                    "FROM dish_list " +
                    "INNER JOIN meal_type ON dish_list.meal_ref = meal_type.id " + show + search +
                    "ORDER BY dish_list.id DESC");
            SqlRowSet rs = jdbc.queryForRowSet(query);

            List<Dish> dishList = new ArrayList<>();

            while (rs.next()) {
                Dish dish = new Dish();
                Meal meal = new Meal();
                meal.setId(rs.getInt("meal_id"));
                meal.setType(rs.getString("type"));

                dish.setId(rs.getInt("id"));
                dish.setName(rs.getString("name"));
                dish.setMeal(meal);
                dish.setDesc(rs.getString("description"));
                dish.setPrice(rs.getDouble("price"));
                dish.setAvailable(rs.getBoolean("available"));
                dishList.add(dish);
            }

        return dishList;

        } catch (Exception e) {
            return null;
        }
    }

    void deleteDish(int id) {
        jdbc.update("DELETE FROM dish_list WHERE id = " +id +" ");
    }

    void addDish(Dish dish) {
        jdbc.update(
                "INSERT INTO dish_list " +
                    "(name, meal_ref, description, price) " +
                    "VALUES ('" + dish.getName() + "', " + dish.getMeal().getId() + ", '" + dish.getDesc() + "', " + dish.getPrice() + ")");
    }

    void updateDish(Dish dish) {
        jdbc.update("UPDATE dish_list SET " +
                "name='" + dish.getName() + "', " +
                "meal_ref='" + dish.getMeal() + "', " +
                "description=" + dish.getDesc() + ", " +
                "price=" + dish.getPrice() + ", " +
                "available=" + dish.isAvailable() + ", " +
                "WHERE id = " + dish.getId()+ " ");
    }

    List<Meal> findAllMeals() {
        try {
            String query = ("SELECT * FROM meal_type ");
            SqlRowSet rs = jdbc.queryForRowSet(query);

            List<Meal> mealList = new ArrayList<>();
            while (rs.next()) {
                Meal meal = new Meal();
                meal.setId(rs.getInt("id"));
                meal.setType(rs.getString("type"));
                mealList.add(meal);
            }
        return mealList;
        }  catch (Exception e) {
            return null;
        }
    }

    List<Restaurant> findAllRestaurants() {
        try {
            String query = ("SELECT * FROM restaurant_list ");
            SqlRowSet rs = jdbc.queryForRowSet(query);

            List<Restaurant> restaurantList = new ArrayList<>();

            while (rs.next()) {
                Restaurant restaurant = new Restaurant();
                restaurant.setId(rs.getInt("id"));
                restaurant.setGuests_max(rs.getInt("guests_max"));
                restaurant.setAddress_town(rs.getString("address_town"));
                restaurant.setAddress_street(rs.getString("address_street"));
                restaurant.setWorkday_open(LocalTime.parse(rs.getString("workday_open")));
                restaurant.setWeekend_closed(LocalTime.parse(rs.getString("workday_closed")));
                restaurant.setWeekend_open(LocalTime.parse(rs.getString("weekend_open")));
                restaurant.setWorkday_closed(LocalTime.parse(rs.getString("weekend_closed")));
                restaurantList.add(restaurant);
            }

            return restaurantList;
        }  catch (Exception e) {
            return null;
        }
    }

    Restaurant findRestaurant(@Null String restaurant_town, int id) {
        try {

            String town_name = "";
            if(restaurant_town != null) {
                town_name = " WHERE LOWER(address_town) = '"+restaurant_town+"' ";
            }

            String restaurant_id = "";
            if(id > 0) {
                restaurant_id = " WHERE id = "+ id +" ";
            }


            String query = ("SELECT * FROM restaurant_list " +
                    town_name + restaurant_id + " ");
            SqlRowSet rs = jdbc.queryForRowSet(query);

            Restaurant restaurant = new Restaurant();
            while (rs.next()) {
                restaurant.setId(rs.getInt("id"));
                restaurant.setGuests_max(rs.getInt("guests_max"));
                restaurant.setAddress_town(rs.getString("address_town"));
                restaurant.setAddress_street(rs.getString("address_street"));
                restaurant.setWorkday_open(LocalTime.parse(rs.getString("workday_open")));
                restaurant.setWeekend_closed(LocalTime.parse(rs.getString("workday_closed")));
                restaurant.setWeekend_open(LocalTime.parse(rs.getString("weekend_open")));
                restaurant.setWorkday_closed(LocalTime.parse(rs.getString("weekend_closed")));
            }

            return restaurant;
        }  catch (Exception e) {
            return null;
        }
    }

    void addRestaurant(Restaurant restaurant) {
        /*KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbc.update(connection -> {
            PreparedStatement ps = connection.prepareStatement("INSERT INTO restaurant_list (address_town, address_street, guests_max, workday_open, workday_closed, weekend_open, weekend_closed) VALUES (?, ?, ?, ?, ?, ?, ?) ",
                    Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, restaurant.getAddress_town());
            ps.setString(2, restaurant.getAddress_street());
            ps.setInt(3, restaurant.getGuests_max());
            return ps;
        }, keyHolder);*/

        jdbc.update("INSERT INTO restaurant_list (address_town, address_street, guests_max, workday_open, workday_closed, weekend_open, weekend_closed) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?) ",
                restaurant.getAddress_town(), restaurant.getAddress_street(), restaurant.getGuests_max(), restaurant.getWorkday_open(), restaurant.getWorkday_closed(), restaurant.getWeekend_open(), restaurant.getWeekend_closed()
        );
    }

    void deleteRestaurant(int id) {
        jdbc.update("DELETE FROM restaurant_list WHERE id = " +id +" ");
    }

    void updateRestaurant(Restaurant restaurant) {
        jdbc.update("UPDATE restaurant_list SET " +
                "address_town='" + restaurant.getAddress_town() + "', " +
                "address_street='" + restaurant.getAddress_street() + "', " +
                "guests_max=" + restaurant.getGuests_max() + ", " +
                "workday_open='" + restaurant.getWorkday_open() + "', " +
                "workday_closed='" + restaurant.getWorkday_closed() + "', " +
                "weekend_open='" + restaurant.getWeekend_open() + "', " +
                "weekend_closed='" + restaurant.getWeekend_closed() + "' " +
                "WHERE id = " + restaurant.getId()+ " ");
    }
}
