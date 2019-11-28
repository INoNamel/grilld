package com.grilled;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;

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
        /*try {*/

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

        /*} catch (Exception e) {
            return null;
        }*/
    }

    void deleteDish(int id) {
        jdbc.update("DELETE FROM dish_list WHERE id = " +id +" ");
    }

    void addDish(Dish dish) {
        jdbc.update(
                "INSERT INTO dish_list " +
                    "(name, meal_ref, description, price) " +
                    "VALUES ('" + dish.getName() + "', " + dish.getMeal().getId() + ", " + dish.getDesc() + ", " + dish.getPrice() + ")");
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
            String query = ("SELECT * " +
                    "FROM meal_type ");
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
}
