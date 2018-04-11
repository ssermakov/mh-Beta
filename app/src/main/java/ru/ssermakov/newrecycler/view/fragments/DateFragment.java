package ru.ssermakov.newrecycler.view.fragments;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import ru.ssermakov.newrecycler.R;
import ru.ssermakov.newrecycler.view.AddPersonActivity;
import ru.ssermakov.newrecycler.view.BeginIllnessActivity;

/**
 * Created by btb_wild on 13.03.2018.
 */

public class DateFragment extends AbstractTabFragment {


    private static final String KEY_START_DATA = "START_DATA";

/*    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }*/

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_date, container, false);
        startDateTextView = view.findViewById(R.id.start_date);
        if (savedInstanceState != null) {
            startDateTextView.setText(savedInstanceState.getString(KEY_START_DATA));
        }
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        startDateTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog();
            }

            private void showDatePickerDialog() {
                DialogFragment fragment = new StartDatePickerFragment();
                fragment.show(getFragmentManager(), "startDatePicker");
            }
        });
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(KEY_START_DATA, startDateTextView.getText().toString());
    }

    public static Fragment getInstance(Context context) {
        Bundle args = new Bundle();
        DateFragment fragment = new DateFragment();
        fragment.setArguments(args);
        fragment.setContext(context);
        fragment.setTitle(context.getString(R.string.tab_item_start_date));

        return fragment;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public static class StartDatePickerFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {


        @NonNull
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the current date as the default date in the picker
            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);

            // Create a new instance of DatePickerDialog and return it
            return new DatePickerDialog(getActivity(), AlertDialog.THEME_HOLO_LIGHT, this, year, month, day);
        }

        @Override
        public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
            i1 = i1 + 1;
           startDateTextView.setText(i2 + "/" + i1 + "/" + i);
        }
    }
}
