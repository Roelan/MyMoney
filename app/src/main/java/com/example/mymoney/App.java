package com.example.mymoney;

import android.app.Application;

import com.example.mymoney.di.AppComponent;
import com.example.mymoney.di.AppModule;
import com.example.mymoney.di.DaggerAppComponent;

public class App extends Application {

    public AppComponent appComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        appComponent = DaggerAppComponent.builder()
                .appModule(new AppModule(this))
                .build();

    }

    public AppComponent getAppComponent() {
        return appComponent;
    }
}
