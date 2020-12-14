package com.example.softec.PojoClasses;

import java.util.List;

public class GetAvailableSeatsModel {

    private Integer id;
    private String name;
    private String address;
    private String city;
    private String contact;
    private String status;
    private Integer number_of_seats;
    private Integer available_seats;
    private String created_at;
    private String business_type;
    private String toggle_booking;
    private String current_date;
    private List<GetAvailableBookingDays> bookings = null;

    public String getToggle_booking() {
        return toggle_booking;
    }

    public void setToggle_booking(String toggle_booking) {
        this.toggle_booking = toggle_booking;
    }

    public String getCurrent_date() {
        return current_date;
    }

    public void setCurrent_date(String current_date) {
        this.current_date = current_date;
    }

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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getBusiness_type() {
        return business_type;
    }

    public void setBusiness_type(String business_type) {
        this.business_type = business_type;
    }

    public List<GetAvailableBookingDays> getBookings() {
        return bookings;
    }

    public void setBookings(List<GetAvailableBookingDays> bookings) {
        this.bookings = bookings;
    }
}
