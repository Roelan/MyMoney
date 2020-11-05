package com.example.mymoney.ui.expenses;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDialogFragment;

import com.example.mymoney.R;

public class EditNameDialog extends AppCompatDialogFragment {

    private EditText etName;
    private EditNameDialogListener listener;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
//            listener = (EditNameDialogListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() +
                    " must implement EditNameDialogListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        listener = null;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.edit_text_layout, null);

        builder.setView(view)
                .setTitle("Add Name")
                .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        CharSequence name = etName.getText();
                        listener.applyText(name);
                    }
                });
        etName = view.findViewById(R.id.edit_text_name);
        return builder.create();
    }

    public void updateName(CharSequence name) {
        etName.setText(name);
    }

    public interface EditNameDialogListener {
        void applyText(CharSequence name);
    }
}
