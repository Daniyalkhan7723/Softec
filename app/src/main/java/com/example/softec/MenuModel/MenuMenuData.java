package com.example.softec.MenuModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class MenuMenuData implements Serializable {

    private boolean opened;
    public boolean isOpened() {
        return opened;
    }

    public void setOpened(boolean opened) {
        this.opened = opened;
    }

    private boolean visibleItem = false;

    public boolean isVisibleItem() {
        return visibleItem;
    }

    public void setVisibleItem(boolean visibleItem) {
        this.visibleItem = visibleItem;
    }

    @SerializedName("id")
    @Expose
    private Integer id;

    @SerializedName("name")
    @Expose
    private String name;

    @SerializedName("type")
    @Expose
    private String type;

    @SerializedName("deal_list")
    @Expose
    private List<MenuDealList> dealList = null;

    @SerializedName("product_list")
    @Expose
    private List<MenuProductList> productList = null;

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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<MenuDealList> getDealList() {
        return dealList;
    }

    public void setDealList(List<MenuDealList> dealList) {
        this.dealList = dealList;
    }

    public List<MenuProductList> getProductList() {
        return productList;
    }

    public void setProductList(List<MenuProductList> productList) {
        this.productList = productList;
    }
}
