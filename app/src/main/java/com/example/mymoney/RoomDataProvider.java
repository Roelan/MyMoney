package com.example.mymoney;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

import com.example.data.room.ExpensesDataRoom;
import com.example.data.room.ExpensesRepository;
import com.example.mymoney.data.ExpensesData;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class RoomDataProvider {

    private static final String TAG = "RoomDataProvider";

    @Inject
    public ExpensesRepository repository;
    LiveData<List<ExpensesDataRoom>> expensesDataRoom;

    public RoomDataProvider(Context context) {
        ((App) context.getApplicationContext()).getAppComponent().inject(this);
        expensesDataRoom = repository.getAllExpenses();
    }

    public LiveData<List<ExpensesDataRoom>> getAllExpenses() {
        Log.d(TAG, "getAllExpenses: get all notes");
        return expensesDataRoom;
    }

    public void insert(ExpensesData expensesData) {
        ExpensesDataRoom expensesDataRoom = new ExpensesDataRoom(
                expensesData.getName(),
                expensesData.getPrise(),
                expensesData.getDate(),
                expensesData.getType());
        repository.insert(expensesDataRoom);
        Log.d(TAG, "insert: " + expensesDataRoom.getName());
    }

    public void update(ExpensesData expensesData) {
        ExpensesDataRoom expensesDataRoom = new ExpensesDataRoom(
                expensesData.getName(),
                expensesData.getPrise(),
                expensesData.getDate(),
                expensesData.getType());
        repository.update(expensesDataRoom);
        Log.d(TAG, "update: " + expensesDataRoom.getName());
    }

    public void delete(ExpensesData expensesData) {
        ExpensesDataRoom expensesDataRoom = new ExpensesDataRoom(
                expensesData.getName(),
                expensesData.getPrise(),
                expensesData.getDate(),
                expensesData.getType());
        repository.delete(expensesDataRoom);
        Log.d(TAG, "delete: " + expensesDataRoom.getName());
    }

    public void deleteAll() {
        repository.deleteAllExpenses();
        Log.d(TAG, "deleteAll: delete all expenses");
    }

    public List<ExpensesData> convertRoomDataToList(@NonNull List<ExpensesDataRoom> roomData) {
        List<ExpensesData> expensesData = new ArrayList<>();
        Log.d(TAG, "convertRoomDataToList: Convert data from room list to the main module data class: ExpensesData");
        for (ExpensesDataRoom e : roomData) {
            Log.d(TAG, "convertRoomDataToList: converting - " + e.getName());
            expensesData.add(new ExpensesData(e.getName(), e.getCost(), e.getDate(), e.getType()));
        }
        Log.d(TAG, "convertRoomDataToList: finish");
        return expensesData;
    }
}
