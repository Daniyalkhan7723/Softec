package com.example.softec.PojoClasses;

public class Loyality {

    private Integer id;
    private Integer no_of_orders;
    private Integer discount_amount;
    private Integer status;
    private String created_at;
    private String updated_at;
    private Integer free_order;

    public Integer getFree_order() {
        return free_order;
    }

    public void setFree_order(Integer free_order) {
        this.free_order = free_order;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getNo_of_orders() {
        return no_of_orders;
    }

    public void setNo_of_orders(Integer no_of_orders) {
        this.no_of_orders = no_of_orders;
    }

    public Integer getDiscount_amount() {
        return discount_amount;
    }

    public void setDiscount_amount(Integer discount_amount) {
        this.discount_amount = discount_amount;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
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
}