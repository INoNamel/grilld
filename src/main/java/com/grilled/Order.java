package com.grilled;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.time.LocalDateTime;

public class Order {
    private DateTimeFormatter day = DateTimeFormatter.ofPattern("MMMM d EEEE"), time = DateTimeFormatter.ofPattern("HH:mm");

    private int id, status = 0;
    private ArrayList<Dish> orders;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime order_time;

    private Client client;


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

    public ArrayList<Dish> getOrder() {
        return orders;
    }

    public void setOrder(ArrayList<Dish> order) {
        this.orders = order;
    }

    public LocalDateTime getOrder_time() {
        return order_time;
    }

    public void setOrder_time(LocalDateTime order_time) {
        this.order_time = order_time;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
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
                status_ref = "under review";
                break;
            case (1):
                status_ref = "cooking";
                break;

            case (2):
                status_ref = "ready for pickup";
                break;

            case (3):
                status_ref = "completed";
                break;
        }
        return status_ref;
    }
}
