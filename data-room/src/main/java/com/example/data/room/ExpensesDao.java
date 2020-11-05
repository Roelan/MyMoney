package com.example.data.room;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface ExpensesDao {

    @Insert
    void insert(ExpensesDataRoom expensesDataRoom);

    @Update
    void update(ExpensesDataRoom expensesDataRoom);

    @Delete
    void delete(ExpensesDataRoom expensesDataRoom);

    @Query("DELETE FROM expenses_database")
    void deleteAll();

    @Query("SELECT * FROM expenses_database")
    LiveData<List<ExpensesDataRoom>> getAllExpenses();
}
