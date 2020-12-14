package com.example.softec.MenuModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class MenuOrderData implements Serializable {

    private int id;
    private int id_sum;
    private String name;
    private String type;
    private int quantity;
    private double actual_price;
    private String description = "";
    private double total_price;
    @SerializedName("products")
    @Expose
    private List<MenuDealProduct> menu_deal_product_list = new ArrayList<>();
    private List<MenuExtraAddings> menu_extra_addings_list  = new ArrayList<>();

    @SerializedName("product_size")
    @Expose
    private List<MenuProductSize> menu_product_size_list = new ArrayList<>();
    public MenuOrderData(int id, int id_sum, String name, String type, int quantity, double actual_price, double total_price, List<MenuDealProduct> menu_deal_product_list, List<MenuExtraAddings> menu_extra_addings_list, List<MenuProductSize> menu_product_size_list) {
        this.id = id;
        this.id_sum = id_sum;
        this.name = name;
        this.type = type;
        this.quantity = quantity;
        this.actual_price = actual_price;
        this.total_price = total_price;
        this.menu_deal_product_list = menu_deal_product_list;
        this.menu_extra_addings_list = menu_extra_addings_list;
        this.menu_product_size_list = menu_product_size_list;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId_sum() {
        return id_sum;
    }

    public void setId_sum(int id_sum) {
        this.id_sum = id_sum;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getActual_price() {
        return actual_price;
    }

    public void setActual_price(double actual_price) {
        this.actual_price = actual_price;
    }

    public double getTotal_price() {
        return total_price;
    }

    public void setTotal_price(double total_price) {
        this.total_price = total_price;
    }

    public List<MenuDealProduct> getMenu_deal_product_list() {
        return menu_deal_product_list;
    }

    public void setMenu_deal_product_list(List<MenuDealProduct> menu_deal_product_list) {
        this.menu_deal_product_list = menu_deal_product_list;
    }

    public List<MenuExtraAddings> getMenu_extra_addings_list() {
        return menu_extra_addings_list;
    }

    public void setMenu_extra_addings_list(List<MenuExtraAddings> menu_extra_addings_list) {
        this.menu_extra_addings_list = menu_extra_addings_list;
    }

    public List<MenuProductSize> getMenu_product_size_list() {
        return menu_product_size_list;
    }

    public void setMenu_product_size_list(List<MenuProductSize> menu_product_size_list) {
        this.menu_product_size_list = menu_product_size_list;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
