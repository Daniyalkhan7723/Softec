package com.example.softec.PojoClasses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Check_Coupon implements Serializable {
    @SerializedName("status")
    @Expose
    private String status;

    private String message;
    private CouponData coupon;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public CouponData getCoupon() {
        return coupon;
    }

    public void setCoupon(CouponData coupon) {
        this.coupon = coupon;
    }
}

