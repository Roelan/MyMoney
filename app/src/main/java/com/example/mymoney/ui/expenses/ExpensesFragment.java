package com.example.mymoney.ui.expenses;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.mymoney.App;
import com.example.mymoney.R;
import com.example.mymoney.RoomDataProvider;
import com.example.mymoney.data.ExpensesData;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import net.objecthunter.exp4j.Expression;
import net.objecthunter.exp4j.ExpressionBuilder;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.inject.Inject;

public class ExpensesFragment extends Fragment implements View.OnClickListener {

    private static final String TAG = "Expenses";

    @Inject
    public RoomDataProvider dataProvider;
    private ExpensesFragmentListener listener;

    private TextView tvExpensesName;
    private TextView tvPrice;
    private TextView tvDate;
    private TextView tvType;
    private TextView tvExpression;
    private ImageButton btnDone;


    public interface ExpensesFragmentListener {
        void updateName(CharSequence name);
    }

    public static ExpensesFragment newInstance() {
        return new ExpensesFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_expenses, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        tvExpensesName = view.findViewById(R.id.text_name);
        tvPrice = view.findViewById(R.id.text_price);
        tvExpression = view.findViewById(R.id.text_expression);
        tvDate = view.findViewById(R.id.text_date);
        btnDone = view.findViewById(R.id.button_done);
        tvType = view.findViewById(R.id.text_type);
        ImageButton btnDate = view.findViewById(R.id.button_choose_date);
        ImageButton btnDelete = view.findViewById(R.id.button_delete);

        // Numbers
        Button btnOne = view.findViewById(R.id.button_one);
        Button btnTwo = view.findViewById(R.id.button_two);
        Button btnThree = view.findViewById(R.id.button_three);
        Button btnFour = view.findViewById(R.id.button_four);
        Button btnFive = view.findViewById(R.id.button_five);
        Button btnSix = view.findViewById(R.id.button_six);
        Button btnSeven = view.findViewById(R.id.button_seven);
        Button btnEight = view.findViewById(R.id.button_eight);
        Button btnNine = view.findViewById(R.id.button_nine);
        Button btnZero = view.findViewById(R.id.button_zero);
        Button btnDot = view.findViewById(R.id.button_dot);
        // Operators
        Button btnPlus = view.findViewById(R.id.button_plus);
        Button btnMinus = view.findViewById(R.id.button_minus);
        Button btnMultiply = view.findViewById(R.id.button_multiply);
        Button btnDivision = view.findViewById(R.id.button_division);
        // Numbers click listeners
        btnOne.setOnClickListener(this);
        btnTwo.setOnClickListener(this);
        btnThree.setOnClickListener(this);
        btnFour.setOnClickListener(this);
        btnFive.setOnClickListener(this);
        btnSix.setOnClickListener(this);
        btnSeven.setOnClickListener(this);
        btnEight.setOnClickListener(this);
        btnNine.setOnClickListener(this);
        btnZero.setOnClickListener(this);
        btnDot.setOnClickListener(this);
        // Operators click listeners
        btnPlus.setOnClickListener(this);
        btnMinus.setOnClickListener(this);
        btnMultiply.setOnClickListener(this);
        btnDivision.setOnClickListener(this);
        // Other
        btnDelete.setOnClickListener(this);
        btnDate.setOnClickListener(this);
        btnDone.setOnClickListener(this);
        tvExpensesName.setOnClickListener(this);

        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        Date date = new Date();
        tvDate.setText(formatter.format(date));
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        ((App) context.getApplicationContext()).getAppComponent().inject(this);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        listener = null;
    }

    private void openEditNameDialog() {
        EditNameDialog editNameDialog = new EditNameDialog();
        editNameDialog.show(getActivity().getSupportFragmentManager(), "Open edit name dialog");
    }

    public void updateTextViewName(CharSequence name) {
        tvExpensesName.setText(name);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            // Numbers
            case R.id.button_one:
                appendOnExpression("1", true);
                break;
            case R.id.button_two:
                appendOnExpression("2", true);
                break;
            case R.id.button_three:
                appendOnExpression("3", true);
                break;
            case R.id.button_four:
                appendOnExpression("4", true);
                break;
            case R.id.button_five:
                appendOnExpression("5", true);
                break;
            case R.id.button_six:
                appendOnExpression("6", true);
                break;
            case R.id.button_seven:
                appendOnExpression("7", true);
                break;
            case R.id.button_eight:
                appendOnExpression("8", true);
                break;
            case R.id.button_nine:
                appendOnExpression("9", true);
                break;
            case R.id.button_zero:
                appendOnExpression("0", true);
                break;
            case R.id.button_dot:
                appendOnExpression(".", true);
                break;
            // Operators
            case R.id.button_plus:
                appendOnExpression("+", false);
                break;
            case R.id.button_minus:
                appendOnExpression("-", false);
                break;
            case R.id.button_multiply:
                appendOnExpression("*", false);
                break;
            case R.id.button_division:
                appendOnExpression("/", false);
                break;
            // Other
            case R.id.text_name:
                openEditNameDialog();
                break;
            case R.id.button_delete:
                deleteOneValue();
                break;
            case R.id.button_done:
                expressionResult();
                break;
            case R.id.button_choose_date:
                showDatePickerDialog();
                break;
        }
    }

    private void deleteOneValue() {
        String value = tvPrice.getText().toString();
        if (!value.isEmpty()) {
            tvPrice.setText(value.substring(0, value.length() - 1));
        }
    }

    public void appendOnExpression(String tmp, Boolean canClear) {
        if (!tvPrice.getText().toString().isEmpty()) {
            tvExpression.setText("");
        }
        if (canClear) {
            tvExpression.setText("");
            tvPrice.append(tmp);
        } else {
            btnDone.setImageResource(R.drawable.equal);
            tvExpression.append(tvPrice.getText());
            tvPrice.append(tmp);
            tvExpression.setText("");
        }
    }

    public void showDatePickerDialog() {
        // Process to get Current Date
        final Calendar c = Calendar.getInstance();
        Integer year = c.get(Calendar.YEAR);
        Integer month = c.get(Calendar.MONTH);
        Integer day = c.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(),
                new DatePickerDialog.OnDateSetListener() {
                    @SuppressLint("SetTextI18n")
                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {
                        tvDate.setText(dayOfMonth + "."
                                + (monthOfYear + 1) + "." + year);
                    }
                }, year, month, day);
        datePickerDialog.show();
    }

    private void expressionResult() {
        if (isNumeric(tvPrice.getText().toString())) {
            try {
                Expression expression = new ExpressionBuilder(tvPrice.getText().toString()).build();
                Double result = expression.evaluate();
                Long longResult = result.longValue();

                if (result == longResult.doubleValue()) {
                    tvExpression.setText(tvPrice.getText().toString());
                    tvPrice.setText(longResult.toString());
                } else {
                    result = Math.round(result * 100.0) / 100.0;
                    tvExpression.setText(tvPrice.getText().toString());
                    tvPrice.setText(result.toString());
                }
                btnDone.setImageResource(R.drawable.check);
            } catch (Exception e) {
                Log.d(TAG, "expressionResult: " + e.getMessage());
            }
        } else {

            dataProvider.insert(new ExpensesData(
                    "New Shit",
                    tvPrice.getText().toString(),
                    tvDate.getText().toString(),
                    tvType.getText().toString()));

            tvExpensesName.setText("");
            tvExpensesName.setHint("Set name...");
            tvPrice.setText("");
            tvExpression.setText("");
        }
    }

    private boolean isNumeric(String tmp) {
        Pattern p = Pattern.compile("[-+/*]");
        Matcher m = p.matcher(tmp);
        return m.find();
    }
}