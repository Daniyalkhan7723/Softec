package com.example.softec.Listeners;

public interface ProductSizeListener {


    void getPrice(boolean selected,double price,String description,int sub_product_size);

    void getDealClick(boolean selected,String itemName);

    void addToppingId(int id);
    void addDrinkId(int id);
    void addSauceId(int id);
    void addExtraAddingId(int id,String name);
    void removeExtraAddingId(int id);
    void addExtraAddingPrice(double price);
    void removeExtraAddingPrice(double price);

}
