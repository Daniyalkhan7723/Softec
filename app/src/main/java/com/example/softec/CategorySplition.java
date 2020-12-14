package com.example.softec;

public class CategorySplition {

    int id;
    String name;
    int counter;

    public CategorySplition(int id, String name, int counter) {
        this.id = id;
        this.name = name;
        this.counter = counter;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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
}
