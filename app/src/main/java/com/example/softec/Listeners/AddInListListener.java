package com.example.softec.Listeners;

import android.content.Intent;

public interface AddInListListener {
     void add(String name,double price,int quantity,int item_id, int item_type,String description,String type,int seb_menu,int sub_product_size);
     void startJActivity(Intent intent);
}
