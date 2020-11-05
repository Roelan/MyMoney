package com.example.mymoney.ui.dashboard;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;

import com.example.data.room.ExpensesDataRoom;
import com.example.mymoney.App;
import com.example.mymoney.R;
import com.example.mymoney.RoomDataProvider;
import com.example.mymoney.ui.MainActivity;
import com.example.mymoney.ui.expenses.ExpensesFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.List;

import javax.inject.Inject;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DashboardFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DashboardFragment extends Fragment {

    private static final String TAG = "DashboardFragment";

    private TextView tvTotalExpenses;
    private TextView tvFoodExpenses;
    private TextView tvBillsExpenses;
    private TextView tvTransportExpenses;
    private TextView tvShoppingExpenses;

    public DashboardFragment() {
        // Required empty public constructor
    }

    public static DashboardFragment newInstance() {
        return new DashboardFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_dashboard, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        tvTotalExpenses = view.findViewById(R.id.text_total_expenses);
        tvBillsExpenses = view.findViewById(R.id.text_view_bills_expenses);
        tvFoodExpenses = view.findViewById(R.id.text_view_food_expenses);
        tvTransportExpenses = view.findViewById(R.id.text_view_transport_expenses);
        tvShoppingExpenses = view.findViewById(R.id.text_view_shopping_expenses);
        LinearLayout transportLayout = view.findViewById(R.id.layout_transport);

        transportLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openExpensesFragment();
            }
        });
        getAllExpensesData();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    private void getAllExpensesData() {
        Log.d(TAG, "getAllExpensesData: start");
        RoomDataProvider roomDataProvider = new RoomDataProvider(getContext());
        roomDataProvider.getAllExpenses().observe(getViewLifecycleOwner(), new Observer<List<ExpensesDataRoom>>() {
            @Override
            public void onChanged(List<ExpensesDataRoom> expensesDataRoom) {
                Log.d(TAG, "getAllExpensesData: get price to the view");
                double amountOfExpenses = 0;
                double amountOfFood = 0;
                double amountOfBills = 0;
                double amountOfShopping = 0;
                double amountOfTransport = 0;

                for (ExpensesDataRoom e : expensesDataRoom) {
                    Log.d(TAG, "onChanged: " + e.getType() + " : " + e.getCost());
                    amountOfExpenses = amountOfExpenses + Double.parseDouble(e.getCost());
                    switch (e.getType()) {
                        case "Food":
                            amountOfFood += Double.parseDouble(e.getCost());
                            Log.d(TAG, "switch case - Food: " + e.getCost());
                            break;
                        case "Bills":
                            amountOfBills += Double.parseDouble(e.getCost());
                            break;
                        case "Shopping":
                            amountOfShopping += Double.parseDouble(e.getCost());
                            break;
                        case "Transport":
                            amountOfTransport += Double.parseDouble(e.getCost());
                            break;
                    }
                }
                tvTotalExpenses.setText(("€ " + amountOfExpenses));
                tvFoodExpenses.setText(("€ " + amountOfFood));
                tvBillsExpenses.setText(("€ " + amountOfBills));
                tvTransportExpenses.setText(("€ " + amountOfTransport));
                tvShoppingExpenses.setText(("€ " + amountOfShopping));
            }
        });
    }

    private void openExpensesFragment() {
        ExpensesFragment expensesFragment = ExpensesFragment.newInstance();
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.setCustomAnimations(R.anim.enter_from_top, R.anim.exit_from_top, R.anim.enter_from_top, R.anim.exit_from_top);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.replace(R.id.fragment_container_calculator, expensesFragment, "expenses_fragment");
        fragmentTransaction.commit();
    }
}