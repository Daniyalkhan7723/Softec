package com.example.softec.NewApis.ModelClasses;

import android.content.Intent;

import com.example.softec.MultipleProductSelection;

import java.io.Serializable;
import java.util.List;

public class SubProducts implements Serializable {

    private Integer id;
    private String name;
    private double price;
    private Integer quantity;
    private Integer quantity_check;
    private Integer level;
    private Integer parent_product = 0;
    private double total_price;
    private boolean current_selected;
    private boolean selected;
    private int is_multiple;
    private List<SubProducts> sub_products = null;

    public Integer getQuantity_check() {
        return quantity_check;
    }

    public void setQuantity_check(Integer quantity_check) {
        this.quantity_check = quantity_check;
    }

    public int getIs_multiple() {
        return is_multiple;
    }

    public void setIs_multiple(int is_multiple) {
        this.is_multiple = is_multiple;
    }

    public double getTotal_price() {
        return total_price;
    }

    public void setTotal_price(double total_price) {
        this.total_price = total_price;
    }

    public boolean isCurrent_selected() {
        return current_selected;
    }

    public void setCurrent_selected(boolean current_selected) {
        this.current_selected = current_selected;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public Integer getParent_product() {
        return parent_product;
    }

    public void setParent_product(Integer parent_product) {
        this.parent_product = parent_product;
    }


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

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public List<SubProducts> getSub_products() {
        return sub_products;
    }

    public void setSub_products(List<SubProducts> sub_products) {
        this.sub_products = sub_products;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }
}
