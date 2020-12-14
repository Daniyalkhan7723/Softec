package com.example.softec.PojoClasses;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class CollectionList implements Serializable {

    private String name;//fanta
    private double price;//50
    private int quantity;//2
    private int item_id;//1
    private int value_type;//56
    private String description;//1.5 ltr
    private double total_price;//100
    private String type;//45

    private int sub_menu; //45
    private int sub_product_size_id; //

    // menu, id, number, collection
    private ArrayList<Integer> toppings_id = new ArrayList<>();
    private ArrayList<Integer> drink_id = new ArrayList<>();
    private ArrayList<Integer> extra_adding_id = new ArrayList<>();
    private ArrayList<Integer> pizza_sauce = new ArrayList<>();

    public CollectionList(String name,String description, double price, int quantity, int item_id, int value_type,String type,int sub_menu
    ,int sub_product_size_id,ArrayList<Integer> toppings_id,ArrayList<Integer> drink_id,ArrayList<Integer> extra_adding_id,ArrayList<Integer> pizza_sauce) {

        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.description = description;
        this.item_id = item_id;
        this.value_type = value_type;
        this.type = type;
        this.sub_menu = sub_menu;
        this.sub_product_size_id = sub_product_size_id;

        this.toppings_id = toppings_id;
        this.drink_id = drink_id;
        this.extra_adding_id= extra_adding_id;
        this.pizza_sauce = pizza_sauce;

    }

    public ArrayList<Integer> getToppings_id() {
        return toppings_id;
    }

    public void setToppings_id(ArrayList<Integer> toppings_id) {
        this.toppings_id = toppings_id;
    }

    public ArrayList<Integer> getDrink_id() {
        return drink_id;
    }

    public void setDrink_id(ArrayList<Integer> drink_id) {
        this.drink_id = drink_id;
    }

    public ArrayList<Integer> getExtra_adding_id() {
        return extra_adding_id;
    }

    public void setExtra_adding_id(ArrayList<Integer> extra_adding_id) {
        this.extra_adding_id = extra_adding_id;
    }

    public ArrayList<Integer> getPizza_sauce() {
        return pizza_sauce;
    }

    public void setPizza_sauce(ArrayList<Integer> pizza_sauce) {
        this.pizza_sauce = pizza_sauce;
    }

    public int getSub_product_size_id() {
        return sub_product_size_id;
    }

    public void setSub_product_size_id(int sub_product_size_id) {
        this.sub_product_size_id = sub_product_size_id;
    }

    public int getSub_menu() {
        return sub_menu;
    }

    public void setSub_menu(int sub_menu) {
        this.sub_menu = sub_menu;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public double getTotal_price() {
        return total_price;
    }

    public void setTotal_price(double total_price) {
        this.total_price = total_price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getItem_id() {
        return item_id;
    }

    public void setItem_id(int item_id) {
        this.item_id = item_id;
    }

    public int getValue_type() {
        return value_type;
    }

    public void setValue_type(int value_type) {
        this.value_type = value_type;
    }
}
