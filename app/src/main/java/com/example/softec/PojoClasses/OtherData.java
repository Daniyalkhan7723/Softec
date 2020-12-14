package com.example.softec.PojoClasses;

import java.io.Serializable;

public class OtherData implements Serializable {

    private String user_id;
    private String longitude;
    private String latitude;
    private String collection_time;
    private String processing_fees;
    private String coupon;
    private String coupon_discount_percent;
    private String after_discount_price;
    private String total_price;
    private String special_note;
    private String payment_type;
    private String stripe_token_key;
    private String final_amount;
    private String loyalty_check;
    private String loyalty_amount;
    private String coupon_id;

    public String getLoyalty_check() {
        return loyalty_check;
    }

    public void setLoyalty_check(String loyalty_check) {
        this.loyalty_check = loyalty_check;
    }

    public String getLoyalty_amount() {
        return loyalty_amount;
    }

    public void setLoyalty_amount(String loyalty_amount) {
        this.loyalty_amount = loyalty_amount;
    }

    public String getCoupon_id() {
        return coupon_id;
    }

    public void setCoupon_id(String coupon_id) {
        this.coupon_id = coupon_id;
    }

    public OtherData(String user_id, String longitude, String latitude, String collection_time, String processing_fees,
                     String coupon, String coupon_id, String coupon_discount_percent, String after_discount_price, String total_price,
                     String special_note, String payment_type, String stripe_token_key, String loyalty_check, String loyalty_amount, String final_amount) {
        this.user_id = user_id;
        this.longitude = longitude;
        this.latitude = latitude;
        this.collection_time = collection_time;
        this.processing_fees = processing_fees;
        this.coupon = coupon;
        this.coupon_discount_percent = coupon_discount_percent;
        this.after_discount_price = after_discount_price;
        this.total_price = total_price;
        this.special_note = special_note;
        this.payment_type = payment_type;
        this.stripe_token_key = stripe_token_key;
        this.final_amount = final_amount;
        this.loyalty_amount = loyalty_amount;
        this.loyalty_check = loyalty_check;
        this.coupon_id = coupon_id;
    }


    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getCollection_time() {
        return collection_time;
    }

    public void setCollection_time(String collection_time) {
        this.collection_time = collection_time;
    }

    public String getProcessing_fees() {
        return processing_fees;
    }

    public void setProcessing_fees(String processing_fees) {
        this.processing_fees = processing_fees;
    }

    public String getCoupon() {
        return coupon;
    }

    public void setCoupon(String coupon) {
        this.coupon = coupon;
    }

    public String getCoupon_discount_percent() {
        return coupon_discount_percent;
    }

    public void setCoupon_discount_percent(String coupon_discount_percent) {
        this.coupon_discount_percent = coupon_discount_percent;
    }

    public String getAfter_discount_price() {
        return after_discount_price;
    }

    public void setAfter_discount_price(String after_discount_price) {
        this.after_discount_price = after_discount_price;
    }

    public String getTotal_price() {
        return total_price;
    }

    public void setTotal_price(String total_price) {
        this.total_price = total_price;
    }

    public String getSpecial_note() {
        return special_note;
    }

    public void setSpecial_note(String special_note) {
        this.special_note = special_note;
    }

    public String getPayment_type() {
        return payment_type;
    }

    public void setPayment_type(String payment_type) {
        this.payment_type = payment_type;
    }

    public String getStripe_token_key() {
        return stripe_token_key;
    }

    public void setStripe_token_key(String stripe_token_key) {
        this.stripe_token_key = stripe_token_key;
    }

    public String getFinal_amount() {
        return final_amount;
    }

    public void setFinal_amount(String final_amount) {
        this.final_amount = final_amount;
    }
}