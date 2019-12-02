package com.grilled;

public class Login {
    private int id;
    private String name, tlf_type, password;

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
}
