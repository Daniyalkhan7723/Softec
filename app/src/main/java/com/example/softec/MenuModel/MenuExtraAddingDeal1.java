package com.example.softec.MenuModel;

import com.example.softec.adapter.ExtraAddingAdapter;

import java.util.List;

public class MenuExtraAddingDeal1 {

    String name;
    int counter;
    boolean open;
    int category_id;
    List<MenuExtraAddings> list_MenuExtraAddings;

    public MenuExtraAddingDeal1(String name, int counter, boolean open,int category_id,List<MenuExtraAddings> list_MenuExtraAddings) {
        this.name = name;
        this.counter = counter;
        this.open = open;
        this.category_id = category_id;
        this.list_MenuExtraAddings = list_MenuExtraAddings;
    }

    public List<MenuExtraAddings> getList_MenuExtraAddings() {
        return list_MenuExtraAddings;
    }

    public void setList_MenuExtraAddings(List<MenuExtraAddings> list_MenuExtraAddings) {
        this.list_MenuExtraAddings = list_MenuExtraAddings;
    }

    public int getCategory_id() {
        return category_id;
    }

    public void setCategory_id(int category_id) {
        this.category_id = category_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCounter() {
        return counter;
    }

    public void setCounter(int counter) {
        this.counter = counter;
    }

    public boolean isOpen() {
        return open;
    }

    public void setOpen(boolean open) {
        this.open = open;
    }
}
