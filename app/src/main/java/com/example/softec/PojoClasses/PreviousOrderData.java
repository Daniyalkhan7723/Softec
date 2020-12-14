package com.example.softec.PojoClasses;

public class PreviousOrderData {

    private String created_at;
    private Object other_data;
    private Object order_data;

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public Object getOther_data() {
        return other_data;
    }

    public void setOther_data(Object other_data) {
        this.other_data = other_data;
    }

    public Object getOrder_data() {
        return order_data;
    }

    public void setOrder_data(Object order_data) {
        this.order_data = order_data;
    }
}