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
            String query = ("SELECT * " +
                    "FROM dish_list " +
                    "WHERE id = '"+id+"' ");
            SqlRowSet rs = jdbc.queryForRowSet(query);
            Dish dish = new Dish();

            while (rs.next()) {
                dish.setId(rs.getInt("id"));
                dish.setName(rs.getString("name"));
                dish.setDesc(rs.getString("description"));
                dish.setPrice(rs.getDouble("price"));
                String[] ingredients = rs.getString("ingredients").split(";");
                dish.setIngredients(ingredients);
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
                search = " AND ingredients LIKE '%"+like+"%' ";
            }

            String query = ("SELECT * FROM dish_list " + show + search +
                    " ORDER BY id DESC");
            SqlRowSet rs = jdbc.queryForRowSet(query);

            List<Dish> dishList = new ArrayList<>();

            while (rs.next()) {
                Dish dish = new Dish();
                dish.setId(rs.getInt("id"));
                dish.setName(rs.getString("name"));
                dish.setDesc(rs.getString("description"));
                dish.setPrice(rs.getDouble("price"));
                String[] ingredients = rs.getString("ingredients").split(";");
                dish.setIngredients(ingredients);
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

    void updateDish(Dish dish) {
        String ingredients = String.join(";", dish.getIngredients());
        jdbc.update("UPDATE dish_list SET " +
                "name='" + dish.getName() + "', " +
                "description=" + dish.getDesc() + ", " +
                "price=" + dish.getPrice() + ", " +
                "ingredients=" + ingredients + ", " +
                "available=" + dish.isAvailable() + ", " +
                "WHERE id = " + dish.getId()+ " ");
    }
}
