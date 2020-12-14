package com.example.softec.MenuModel;

public interface ExtraAddingsCallBack {
    void addExtraAdding(MenuExtraAddings menuExtraAddings,boolean add);
    void addProductSize(MenuProductSize menuProductSize);
    void moveToNextLay();
    void moveToPreviousLay();
    void multipleExtraAddings(MenuExtraAddings menuExtraAddings,boolean add,boolean remove);

    void replaceExtraAddings(MenuExtraAddings menuExtraAddings,boolean add,boolean remove);
}
