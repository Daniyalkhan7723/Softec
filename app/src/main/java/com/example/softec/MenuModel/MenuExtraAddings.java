package com.example.softec.MenuModel;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class MenuExtraAddings implements Serializable {

    @SerializedName("id")
    private Integer id;

    @SerializedName("name")
    private String name;

    @SerializedName("price")
    private String price;

    @SerializedName("replace_of")
    private MenuReplaceOf replaceOf;

    @SerializedName("category_id")
    private int category_id;

    @SerializedName("category_name")
    private String category_name;

    @SerializedName("counter")
    private int counter;

    private int counter_selected;

    public int getCounter_selected() {
        return counter_selected;
    }

    public void setCounter_selected(int counter_selected) {
        this.counter_selected = counter_selected;
    }

    public int getCategory_id() {
        return category_id;
    }

    public void setCategory_id(int category_id) {
        this.category_id = category_id;
    }

    public String getCategory_name() {
        return category_name;
    }

    public void setCategory_name(String category_name) {
        this.category_name = category_name;
    }

    public int getCounter() {
        return counter;
    }

    public void setCounter(int counter) {
        this.counter = counter;
    }

    private boolean selected;

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public boolean isSelected() {
        return selected;
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

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public MenuReplaceOf getReplaceOf() {
        return replaceOf;
    }

    public void setReplaceOf(MenuReplaceOf replaceOf) {
        this.replaceOf = replaceOf;
    }
}
