package com.example.softec.PojoClasses;

public class GetAvailableSeats {

    private String status;
    private String message;
    private GetAvailableSeatsModel data;

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

    public GetAvailableSeatsModel getData() {
        return data;
    }

    public void setData(GetAvailableSeatsModel data) {
        this.data = data;
    }
}
