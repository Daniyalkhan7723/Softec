package com.example.softec.NewApis;

import android.content.Intent;

import com.example.softec.MenuModel.MenuOrderData;
import com.example.softec.NewApis.ModelClasses.SubProducts;

public interface OrderListListener {

    void openItem(MenuOrderData menuOrderData,int index);
    void addPrice(int index);
    void subPrice(int index);
    void deleteExistingItem(int index);

}
