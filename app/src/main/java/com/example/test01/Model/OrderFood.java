package com.example.test01.Model;

public class OrderFood {
    private String description;
    private String foodName;
    private String foodid;
    private String price;
    private String quantity;

    public OrderFood(String description, String foodName, String foodid, String price, String quantity) {
        this.description = description;
        this.foodName = foodName;
        this.foodid = foodid;
        this.price = price;
        this.quantity = quantity;
    }

    public OrderFood() {

    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getFoodName() {
        return foodName;
    }

    public void setFoodName(String foodName) {
        this.foodName = foodName;
    }

    public String getFoodid() {
        return foodid;
    }

    public void setFoodid(String foodid) {
        this.foodid = foodid;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }
}
