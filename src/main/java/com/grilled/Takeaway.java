package com.grilled;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.time.LocalDateTime;

public class Takeaway {
    private DateTimeFormatter day = DateTimeFormatter.ofPattern("MMMM d EEEE"), time = DateTimeFormatter.ofPattern("HH:mm");

    private int id, status = 0;
    private List<Dish> orders;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime order_time;

    private Login login;
    private Restaurant restaurant;
    private static final String UNDER_REVIEW_MESSAGE = "under review";
    private static final String COOCKING_MESSAGE = "cooking";
    private static final String READY_FOR_PICK_UP_MESSAGE = "ready for pickup";
    private static final String COMPLETED_MESSAGE = "completed";


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public List<Dish> getOrder() {
        return orders;
    }

    public void setOrder(List<Dish> order) {
        this.orders = order;
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

    public String getDay() {
        return this.order_time.format(day);
    }

    public String getTime() {
        return this.order_time.format(time);
    }

    public String displayStatus() {
        String status_ref = "";
        switch (status) {
            case (0):
                status_ref = UNDER_REVIEW_MESSAGE;
                break;
            case (1):
                status_ref = COOCKING_MESSAGE;
                break;

            case (2):
                status_ref = READY_FOR_PICK_UP_MESSAGE;
                break;

            case (3):
                status_ref = COMPLETED_MESSAGE;
                break;
        }
        return status_ref;
    }

    public Restaurant getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
    }
}
