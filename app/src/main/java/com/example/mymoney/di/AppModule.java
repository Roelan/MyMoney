package com.example.mymoney.di;

import android.app.Application;

import androidx.annotation.NonNull;

import com.example.data.room.ExpensesRepository;
import com.example.mymoney.RoomDataProvider;
import com.example.mymoney.ui.MainActivity;
import com.example.mymoney.ui.transactions.TransactionsFragment;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class AppModule {

    private Application applicationContext;

    public AppModule(@NonNull Application application) {
        applicationContext = application;
    }

    @Provides
    @Singleton
    Application provideApplicationContext() {
        return applicationContext;
    }

    @Provides
    @Singleton
    ExpensesRepository provideExpensesRepository(Application application) {
        return new ExpensesRepository(application);
    }

    @Provides
    @Singleton
    RoomDataProvider provideRoomDataProvider(Application application) {
        return new RoomDataProvider(application);
    }

    @Provides
    TransactionsFragment providesTransactionsFragment() {
        return new TransactionsFragment();
    }
}
