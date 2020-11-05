package com.example.mymoney.ui.transactions;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.data.room.ExpensesDataRoom;
import com.example.mymoney.App;
import com.example.mymoney.R;
import com.example.mymoney.RoomDataProvider;

import java.util.List;

import javax.inject.Inject;

public class TransactionsFragment extends Fragment {

    private static final String TAG = "TransactionsFragment";

    @Inject
    public RoomDataProvider dataProvider;

    private RecyclerView rvTranscriptions;
    private final TranscriptionAdapter adapter = new TranscriptionAdapter();


    public static TransactionsFragment newInstance() {
        return new TransactionsFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        ((App) context.getApplicationContext()).getAppComponent().inject(this);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_transactions, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // Recycler view
        rvTranscriptions = view.findViewById(R.id.rv_transactions);
        rvTranscriptions.setLayoutManager(new LinearLayoutManager(getContext()));
        rvTranscriptions.setHasFixedSize(true);
        rvTranscriptions.setAdapter(adapter);

        // Set data to the recycler view
        dataProvider.getAllExpenses().observe(getViewLifecycleOwner(), new Observer<List<ExpensesDataRoom>>() {
            @Override
            public void onChanged(List<ExpensesDataRoom> expensesDataRooms) {
                adapter.setExpensesData(dataProvider.convertRoomDataToList(expensesDataRooms));
            }
        });

        // Delete one item by the left swipe
        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                dataProvider.delete(adapter.getPosition(viewHolder.getAdapterPosition()));
                Log.d(TAG, "Note at position " + viewHolder.getAdapterPosition() + "deleted");
            }
        }).attachToRecyclerView(rvTranscriptions);
    }
}
