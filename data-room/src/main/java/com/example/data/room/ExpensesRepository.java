package com.example.data.room;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.List;

import javax.inject.Inject;

public class ExpensesRepository {
    public ExpensesDao expensesDao;

    private LiveData<List<ExpensesDataRoom>> allExpenses;

    @Inject
    public ExpensesRepository(Application application) {
        ExpensesDataBase database = ExpensesDataBase.getInstance(application);
        expensesDao = database.expensesDao();
        allExpenses = expensesDao.getAllExpenses();
    }

    public void insert(ExpensesDataRoom expenses) {
        new InsertExpensesAsyncTask(expensesDao).execute(expenses);
    }

    public void update(ExpensesDataRoom expenses) {
        new UpdateExpensesAsyncTask(expensesDao).execute(expenses);
    }

    public void delete(ExpensesDataRoom expenses) {
        new DeleteExpensesAsyncTask(expensesDao).execute(expenses);
    }

    public void deleteAllExpenses() {
        new DeleteAllExpensesAsyncTask(expensesDao).execute();
    }

    public LiveData<List<ExpensesDataRoom>> getAllExpenses() {
        return allExpenses;
    }

    private static class InsertExpensesAsyncTask extends AsyncTask<ExpensesDataRoom, Void, Void> {
        private ExpensesDao expensesDao;

        private InsertExpensesAsyncTask(ExpensesDao expensesDao) {
            this.expensesDao = expensesDao;
        }

        @Override
        protected Void doInBackground(ExpensesDataRoom... expenses) {
            expensesDao.insert(expenses[0]);
            return null;
        }
    }

    private static class UpdateExpensesAsyncTask extends AsyncTask<ExpensesDataRoom, Void, Void> {
        private ExpensesDao expensesDao;

        private UpdateExpensesAsyncTask(ExpensesDao expensesDao) {
            this.expensesDao = expensesDao;
        }

        @Override
        protected Void doInBackground(ExpensesDataRoom... expenses) {
            expensesDao.update(expenses[0]);
            return null;
        }
    }

    private static class DeleteExpensesAsyncTask extends AsyncTask<ExpensesDataRoom, Void, Void> {
        private ExpensesDao expensesDao;

        private DeleteExpensesAsyncTask(ExpensesDao expensesDao) {
            this.expensesDao = expensesDao;
        }

        @Override
        protected Void doInBackground(ExpensesDataRoom... expenses) {
            expensesDao.delete(expenses[0]);
            return null;
        }
    }

    private static class DeleteAllExpensesAsyncTask extends AsyncTask<Void, Void, Void> {
        private ExpensesDao expensesDao;

        private DeleteAllExpensesAsyncTask(ExpensesDao expensesDao) {
            this.expensesDao = expensesDao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            expensesDao.deleteAll();
            return null;
        }
    }
}
