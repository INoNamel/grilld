package com.grilled;

import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

public class Login {
    private int id;
    private String name, tlf_type, password;

    @DateTimeFormat(pattern = "dd-MM-yyyy")
    private LocalDate joined;


    Login(){}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTlf_type() {
        return tlf_type;
    }

    public void setTlf_type(String tlf_type) {
        this.tlf_type = tlf_type;
    }

    public LocalDate getJoined() {
        return joined;
    }

    public void setJoined(LocalDate joined) {
        this.joined = joined;
    }
}
