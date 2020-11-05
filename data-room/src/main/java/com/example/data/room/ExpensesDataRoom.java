package com.example.data.room;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "expenses_database")
public class ExpensesDataRoom {

    @PrimaryKey(autoGenerate = true)
    private int id;
    private String name;
    private String cost;
    private String date;
    private String type;

    public ExpensesDataRoom(String name, String cost, String date, String type) {
        this.name = name;
        this.cost = cost;
        this.date = date;
        this.type = type;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getCost() {
        return cost;
    }

    public String getDate() {
        return date;
    }

    public String getType() {
        return type;
    }
}
