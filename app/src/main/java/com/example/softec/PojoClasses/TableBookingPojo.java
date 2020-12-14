package com.example.softec.PojoClasses;

public class TableBookingPojo {

    private String status;
    private String message;
    private TableBookingAvailability data;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public TableBookingAvailability getData() {
        return data;
    }

    public void setData(TableBookingAvailability data) {
        this.data = data;
    }
}