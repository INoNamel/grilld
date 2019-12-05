package com.grilled;

import javax.validation.constraints.Max;
import java.util.Map;

public class TakeawayOrder {

    private Map<Integer,@Max(5)Integer> list;
    private Restaurant restaurant;

    TakeawayOrder(){}


    public Map<Integer, Integer> getList() {
        return list;
    }

    public void setList(Map<Integer, Integer> list) {
        this.list = list;
    }

    public Restaurant getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
    }
}
