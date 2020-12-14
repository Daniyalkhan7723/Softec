package com.example.softec.PojoClasses;

public class GetAvailableBookingDays {

    private Integer id;
    private String day_name;
    private String booking_from;
    private String booking_to;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDay_name() {
        return day_name;
    }

    public void setDay_name(String day_name) {
        this.day_name = day_name;
    }

    public String getBooking_from() {
        return booking_from;
    }

    public void setBooking_from(String booking_from) {
        this.booking_from = booking_from;
    }

    public String getBooking_to() {
        return booking_to;
    }

    public void setBooking_to(String booking_to) {
        this.booking_to = booking_to;
    }
}
