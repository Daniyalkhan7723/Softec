package com.example.softec.MenuModel;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class MenuProductList implements Serializable {

    @SerializedName("id")
    private int id;

    @SerializedName("name")
    private String name;

    @SerializedName("price")
    private double price;

    @SerializedName("product_size")
    List<MenuProductSize> menuProductSizeList = new ArrayList<>();

    public List<MenuProductSize> getMenuProductSizeList() {
        return menuProductSizeList;
    }

    public void setMenuProductSizeList(List<MenuProductSize> menuProductSizeList) {
        this.menuProductSizeList = menuProductSizeList;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
