package com.example.softec.PojoClasses;

public class OrderPlaceModel {

    private String status;
    private String msg;
    private Loyality loyality;
    private Integer  loyality_check;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Loyality getLoyality() {
        return loyality;
    }

    public void setLoyality(Loyality loyality) {
        this.loyality = loyality;
    }

    public Integer getLoyality_check() {
        return loyality_check;
    }

    public void setLoyality_check(Integer loyality_check) {
        this.loyality_check = loyality_check;
    }
}
