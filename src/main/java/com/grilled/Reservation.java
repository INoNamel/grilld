package com.grilled;

import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Reservation {
    private DateTimeFormatter day = DateTimeFormatter.ofPattern("MMMM d EEEE"), time = DateTimeFormatter.ofPattern("HH:mm");
    private int id, guests;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime order_time;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime order_for;

    private Login login;
    private Restaurant restaurant;

    Reservation(){

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getGuests() {
        return guests;
    }

    public void setGuests(int guests) {
        this.guests = guests;
    }

    public LocalDateTime getOrder_for() {
        return order_for;
    }

    public void setOrder_for(LocalDateTime order_for) {
        this.order_for = order_for;
    }

    public LocalDateTime getOrder_time() {
        return order_time;
    }

    public void setOrder_time(LocalDateTime order_time) {
        this.order_time = order_time;
    }

    public Login getLogin() {
        return login;
    }

    public void setLogin(Login login) {
        this.login = login;
    }

    public Restaurant getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
    }
}
