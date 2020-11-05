package com.example.mymoney.di;

import com.example.mymoney.RoomDataProvider;
import com.example.mymoney.ui.MainActivity;
import com.example.mymoney.ui.dashboard.DashboardFragment;
import com.example.mymoney.ui.expenses.ExpensesFragment;
import com.example.mymoney.ui.transactions.TransactionsFragment;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {AppModule.class})
public interface AppComponent {

    void inject(TransactionsFragment transactionsFragment);
    void inject(RoomDataProvider roomDataProvider);
    void inject(ExpensesFragment expensesFragment);
}
