package com.example.softec.OrderCollectionClasses;

import java.io.Serializable;
import java.util.List;

public class OrderCollection implements Serializable {

    private Integer id;
    private String name;
    private double price;
    private double total_price;
    private int quantity;
    private int child_id;
    private List<SubProductsIDList> subProductsIDLists;
    private int ProductsIdSum;

    public OrderCollection(Integer id, String name, double price, double total_price, int quantity, List<SubProductsIDList> subProductsIDLists) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.total_price = total_price;
        this.quantity = quantity;
        this.subProductsIDLists = subProductsIDLists;
    }

    public int getChild_id() {
        return child_id;
    }

    public void setChild_id(int child_id) {
        this.child_id = child_id;
    }

    public int getProductsIdSum() {
        return ProductsIdSum;
    }

    public void setProductsIdSum(int productsIdSum) {
        ProductsIdSum = productsIdSum;
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

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getTotal_price() {
        return total_price;
    }

    public void setTotal_price(double total_price) {
        this.total_price = total_price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public List<SubProductsIDList> getSubProductsIDLists() {
        return subProductsIDLists;
    }

    public void setSubProductsIDLists(List<SubProductsIDList> subProductsIDLists) {
        this.subProductsIDLists = subProductsIDLists;
    }
}
