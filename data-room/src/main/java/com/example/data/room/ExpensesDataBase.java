package com.example.data.room;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

@Database(entities = {ExpensesDataRoom.class}, version = 1)
public abstract class ExpensesDataBase extends RoomDatabase {

    private static ExpensesDataBase instance;

    public abstract ExpensesDao expensesDao();

    public static synchronized ExpensesDataBase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                    ExpensesDataBase.class, "expenses_database")
                    .fallbackToDestructiveMigration()
                    .addCallback(roomCallback)
                    .build();
        }
        return instance;
    }

    private static RoomDatabase.Callback roomCallback = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            new PopulateDbAsyncTask(instance).execute();
        }
    };

    private static class PopulateDbAsyncTask extends AsyncTask<Void, Void, Void> {
        private ExpensesDao expensesDao;

        private PopulateDbAsyncTask(ExpensesDataBase db) {
            expensesDao = db.expensesDao();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            expensesDao.insert(new ExpensesDataRoom("Petrol", "50", "1.1.2020", "Transport"));
            expensesDao.insert(new ExpensesDataRoom("Wheels", "100", "1.1.2020", "Transport"));
            expensesDao.insert(new ExpensesDataRoom("Apple", "150", "1.1.2020", "Food"));
            expensesDao.insert(new ExpensesDataRoom("Rent", "220", "1.1.2020", "Bills"));
            expensesDao.insert(new ExpensesDataRoom("Playstation", "300", "1.1.2020", "Shopping"));
            expensesDao.insert(new ExpensesDataRoom("T-Short", "111", "1.1.2020", "Shopping"));
            return null;
        }
    }
}


