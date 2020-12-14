package com.example.softec.PojoClasses;

import java.io.Serializable;

public class Deal implements Serializable {

    private Integer id;
    private String name;
    private Integer category_id;
    private String price;
    private String description;
    private String startingDate;
    private String endingDate;
    private String startTime;
    private String endTime;
    private String title;
    private Integer no_of_toppings;
    private Integer no_of_sauce;
    private Integer no_of_drinks;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCategory_id() {
        return category_id;
    }

    public void setCategory_id(Integer category_id) {
        this.category_id = category_id;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStartingDate() {
        return startingDate;
    }

    public void setStartingDate(String startingDate) {
        this.startingDate = startingDate;
    }

    public String getEndingDate() {
        return endingDate;
    }

    public void setEndingDate(String endingDate) {
        this.endingDate = endingDate;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getNo_of_toppings() {
        return no_of_toppings;
    }

    public void setNo_of_toppings(Integer no_of_toppings) {
        this.no_of_toppings = no_of_toppings;
    }

    public Integer getNo_of_sauce() {
        return no_of_sauce;
    }

    public void setNo_of_sauce(Integer no_of_sauce) {
        this.no_of_sauce = no_of_sauce;
    }

    public Integer getNo_of_drinks() {
        return no_of_drinks;
    }

    public void setNo_of_drinks(Integer no_of_drinks) {
        this.no_of_drinks = no_of_drinks;
    }
}