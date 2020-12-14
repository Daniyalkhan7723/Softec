package com.example.softec.PojoClasses;

public class User {

    private Integer id;
    private String user_name;
    private Object email;
    private Object password;
    private String phone;
    private Integer code;
    private String tip;
    private String promotion;
    private Integer tip_status;
    private String updated_at;
    private String created_at;

    private Integer restaurant_id;
    private String stripe_uuid;
    private String subscription_till;
    private Integer subscription_active;
    private String membership_id;

    public Integer getTip_status() {
        return tip_status;
    }

    public void setTip_status(Integer tip_status) {
        this.tip_status = tip_status;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public Object getEmail() {
        return email;
    }

    public void setEmail(Object email) {
        this.email = email;
    }

    public Object getPassword() {
        return password;
    }

    public void setPassword(Object password) {
        this.password = password;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getTip() {
        return tip;
    }

    public void setTip(String tip) {
        this.tip = tip;
    }

    public String getPromotion() {
        return promotion;
    }

    public void setPromotion(String promotion) {
        this.promotion = promotion;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public Integer getRestaurant_id() {
        return restaurant_id;
    }

    public void setRestaurant_id(Integer restaurant_id) {
        this.restaurant_id = restaurant_id;
    }

    public String getStripe_uuid() {
        return stripe_uuid;
    }

    public void setStripe_uuid(String stripe_uuid) {
        this.stripe_uuid = stripe_uuid;
    }

    public String getSubscription_till() {
        return subscription_till;
    }

    public void setSubscription_till(String subscription_till) {
        this.subscription_till = subscription_till;
    }

    public Integer getSubscription_active() {
        return subscription_active;
    }

    public void setSubscription_active(Integer subscription_active) {
        this.subscription_active = subscription_active;
    }

    public String getMembership_id() {
        return membership_id;
    }

    public void setMembership_id(String membership_id) {
        this.membership_id = membership_id;
    }
}
