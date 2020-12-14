package com.example.softec.MenuModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class MenuDealList implements Serializable {

    @SerializedName("id")
    @Expose
    private Integer id;

    @SerializedName("name")
    @Expose
    private String name;

    @SerializedName("price")
    @Expose
    private double price;

    @SerializedName("description")
    @Expose
    private String description;

    @SerializedName("deal_products")
    @Expose
    private List<MenuDealProduct> dealProducts = null;


    @SerializedName("extra_addings")
    @Expose
    private List<MenuExtraAddings> extraAddings = null;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<MenuDealProduct> getDealProducts() {
        return dealProducts;
    }

    public void setDealProducts(List<MenuDealProduct> dealProducts) {
        this.dealProducts = dealProducts;
    }

    public List<MenuExtraAddings> getExtraAddings() {
        return extraAddings;
    }

    public void setExtraAddings(List<MenuExtraAddings> extraAddings) {
        this.extraAddings = extraAddings;
    }
}
