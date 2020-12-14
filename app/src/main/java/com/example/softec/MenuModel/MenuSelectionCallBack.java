package com.example.softec.MenuModel;

public interface MenuSelectionCallBack {

    void addDeal(MenuDealList menuDealList);
    void addProduct(MenuProductList menuProductList);
    void openNewActivityForDeal(MenuDealList menuDealList);
    void openNewActivityForProduct(MenuProductList productList);

}
