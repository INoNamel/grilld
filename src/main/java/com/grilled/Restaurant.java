package com.grilled;

import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalTime;

public class Restaurant {
    private int id, guests_max;
    private String address_town, address_street;

    @DateTimeFormat(pattern = "HH:mm")
    private LocalTime workday_open;

    @DateTimeFormat(pattern = "HH:mm")
    private LocalTime workday_closed;

    @DateTimeFormat(pattern = "HH:mm")
    private LocalTime weekend_open;

    @DateTimeFormat(pattern = "HH:mm")
    private LocalTime weekend_closed;

    Restaurant() {}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAddress_town() {
        return address_town;
    }

    public void setAddress_town(String address_town) {
        this.address_town = address_town;
    }

    public String getAddress_street() {
        return address_street;
    }

    public void setAddress_street(String address_street) {
        this.address_street = address_street;
    }

    public int getGuests_max() {
        return guests_max;
    }

    public void setGuests_max(int guests_max) {
        this.guests_max = guests_max;
    }

    public LocalTime getWorkday_open() {
        return workday_open;
    }

    public void setWorkday_open(LocalTime workday_open) {
        this.workday_open = workday_open;
    }

    public LocalTime getWorkday_closed() {
        return workday_closed;
    }

    public void setWorkday_closed(LocalTime workday_closed) {
        this.workday_closed = workday_closed;
    }

    public LocalTime getWeekend_open() {
        return weekend_open;
    }

    public void setWeekend_open(LocalTime weekend_open) {
        this.weekend_open = weekend_open;
    }

    public LocalTime getWeekend_closed() {
        return weekend_closed;
    }

    public void setWeekend_closed(LocalTime weekend_closed) {
        this.weekend_closed = weekend_closed;
    }
}
