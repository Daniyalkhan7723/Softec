package com.example.softec.PojoClasses;

public class Bookings {

    private Integer id;
    private Integer restaurant_id;
    private Integer user_d;
    private String date;
    private String time;
    private String status;
    private Integer no_of_guests;
    private String created_at;
    private String updated_at;
    private String special_requirements;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getRestaurant_id() {
        return restaurant_id;
    }

    public void setRestaurant_id(Integer restaurant_id) {
        this.restaurant_id = restaurant_id;
    }

    public Integer getUser_d() {
        return user_d;
    }

    public void setUser_d(Integer user_d) {
        this.user_d = user_d;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getNo_of_guests() {
        return no_of_guests;
    }

    public void setNo_of_guests(Integer no_of_guests) {
        this.no_of_guests = no_of_guests;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
    }

    public String getSpecial_requirements() {
        return special_requirements;
    }

    public void setSpecial_requirements(String special_requirements) {
        this.special_requirements = special_requirements;
    }
}
