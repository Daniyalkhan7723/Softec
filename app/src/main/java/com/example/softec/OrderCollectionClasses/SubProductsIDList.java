package com.example.softec.OrderCollectionClasses;

import com.example.softec.NewApis.ModelClasses.SubProducts;

import java.io.Serializable;

public class SubProductsIDList implements Serializable {

    private SubProducts subProducts;

    public SubProductsIDList(SubProducts subProducts) {
        this.subProducts = subProducts;
    }

    public SubProducts getSubProducts() {
        return subProducts;
    }
}
