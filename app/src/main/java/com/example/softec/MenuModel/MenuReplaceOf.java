package com.example.softec.MenuModel;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class MenuReplaceOf implements Serializable {

    @SerializedName("id")
    private int replace_of;

    @SerializedName("name")
    private String name;

    public int getReplace_of() {
        return replace_of;
    }

    public void setReplace_of(int replace_of) {
        this.replace_of = replace_of;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
