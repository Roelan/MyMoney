package com.example.mymoney.ui;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.IdRes;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.mymoney.R;
import com.example.mymoney.ui.dashboard.DashboardFragment;
import com.example.mymoney.ui.transactions.TransactionsFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    private static final int FRAG_DASHBOARD = 1;
    private static final int FRAG_TRANSACTIONS = 2;

    public BottomNavigationView bottomNavigation;
    private BottomNavigationView.OnNavigationItemSelectedListener menuClickListener;
    private @IdRes int selectedId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        onCreateUI();
    }

    public void hideBottomNav() {
        bottomNavigation.setVisibility(View.INVISIBLE);
    }

    @Override
    protected void onResume() {
        super.onResume();
        bottomNavigation.setOnNavigationItemSelectedListener(menuClickListener);
    }

    private void onCreateUI() {
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
        bottomNavigation = findViewById(R.id.bottom_navigation_bar);
        menuClickListener = item -> {
            onTabClick(item.getItemId());
            return true;
        };
        onTabClick(bottomNavigation.getSelectedItemId());
    }

    private void onTabClick(@IdRes int selectedId) {
        if (selectedId != this.selectedId) {
            Fragment nextFragment;
            switch (getMenuItemType(selectedId)) {
                case FRAG_TRANSACTIONS:
                    nextFragment = TransactionsFragment.newInstance();
                    break;
                default:
                    nextFragment = DashboardFragment.newInstance();
            }
            Log.d(TAG, "onTabClick: Change layout");
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.fragment_container, nextFragment);
            fragmentTransaction.commit();
            this.selectedId = selectedId;
        } else {
            Log.d(TAG, "onTabClock: " + "Ignored menu click: " + selectedId + " is already selected");
        }
    }

    private int getMenuItemType(@IdRes int selectedId) {
        if (selectedId == R.id.nav_transactions) {
            return FRAG_TRANSACTIONS;
        } else if (selectedId == R.id.nav_categories) {
            return FRAG_DASHBOARD;
        }
        // Dashboard is selected by default
        return FRAG_DASHBOARD;
    }
}