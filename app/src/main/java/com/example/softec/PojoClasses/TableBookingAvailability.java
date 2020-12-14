package com.example.softec.PojoClasses;

public class TableBookingAvailability {

    private Integer id;
    private String name;
    private String booking_from;
    private String booking_to;
    private Integer number_of_seats;
    private Integer available_seats;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public Integer getNumber_of_seats() {
        return number_of_seats;
    }

    public void setNumber_of_seats(Integer number_of_seats) {
        this.number_of_seats = number_of_seats;
    }

    public Integer getAvailable_seats() {
        return available_seats;
    }

    public void setAvailable_seats(Integer available_seats) {
        this.available_seats = available_seats;
    }
}