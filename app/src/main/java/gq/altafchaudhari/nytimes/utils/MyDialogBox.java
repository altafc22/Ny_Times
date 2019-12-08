package gq.altafchaudhari.nytimes.utils;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDialogFragment;

import gq.altafchaudhari.nytimes.R;


public class MyDialogBox extends AppCompatDialogFragment {
    private Spinner sectionSpinner;
    private EditText et_period;
    private MyDialogListener listener;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.layout_dialogbox, null);

        builder.setView(view)
                .setTitle("Login")
                .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                })
                .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if(et_period.length()>0)
                        {
                            String username = sectionSpinner.getSelectedItem().toString();
                            String password = et_period.getText().toString();
                            listener.applyTexts(username, password);
                        }
                        else
                        {
                            et_period.setError("Please Enter number of days to get result");
                        }
                    }
                });

        sectionSpinner = view.findViewById(R.id.spinner_section);
        et_period = view.findViewById(R.id.et_period);

        return builder.create();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try {
            listener = (MyDialogListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() +
                    "must implement ExampleDialogListener");
        }
    }

    public interface MyDialogListener {
        void applyTexts(String section, String period);
    }
}