package com.example.test01.Model;

import java.util.List;

public class Request {

    private String phne;
    private String name;
    private String price;
    private String status;
    private List<Order> foods;
    private String description;

    public Request(String phne, String name, String total, String status, List<Order> foods,String description) {
        this.phne = phne;
        this.name = name;
        this.price = price;
        this.status = status;
        this.foods = foods;
        this.description = description;
    }
    public Request(){
        this.description = "無特別需求";
    }

    public String getphone() {
        return phne;
    }

    public void setPhone(String phne) {
        this.phne = phne;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String total) {
        this.price = total;
    }

    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }

    public List<Order> getFoods() {
        return foods;
    }

    public void setFoods(List<Order> foods) {
        this.foods = foods;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}

