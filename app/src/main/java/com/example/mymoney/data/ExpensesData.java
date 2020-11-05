package com.example.mymoney.data;

public class ExpensesData {

    private String name;
    private String cost;
    private String date;
    private String type;

    public ExpensesData(String name, String cost, String date, String type) {
        this.name = name;
        this.cost = cost;
        this.date = date;
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public String getPrise() {
        return cost;
    }

    public String getDate() {
        return date;
    }

    public String getType() {
        return type;
    }
}
