package com.example.softec.PojoClasses;

import java.util.List;

public class PreviousOrders {

    private String status;
    private List<PreviousOrderData> data = null;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<PreviousOrderData> getData() {
        return data;
    }

    public void setData(List<PreviousOrderData> data) {
        this.data = data;
    }
}