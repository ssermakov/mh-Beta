package ru.ssermakov.newrecycler.view.fragments;

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
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;

import java.util.Calendar;
import java.util.Date;

import ru.ssermakov.newrecycler.R;
import studio.carbonylgroup.textfieldboxes.ExtendedEditText;
import studio.carbonylgroup.textfieldboxes.TextFieldBoxes;

/**
 * Created by btb_wild on 13.03.2018.
 */

public class DateFragment extends AbstractTabFragment implements View.OnClickListener {


    private static final String KEY_START_DATE = "START_DATE";
    TextFieldBoxes textFieldBoxesIllnessName;


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
        dateHint = view.findViewById(R.id.date_hint);
        //set default date
        startDateTextView.setText(setDefaultDate());

        startDateTextView.setOnClickListener(this);
        dateHint.setOnClickListener(this);

        textFieldBoxesIllnessName = view.findViewById(R.id.text_field_boxes_illness_name);
        extendedEditTextIllnessName = view.findViewById(R.id.extended_edit_text_illness_name);

        if (savedInstanceState != null) {
            startDateTextView.setText(savedInstanceState.getString(KEY_START_DATE));
        }

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onClick(View v) {
        int viewId = v.getId();
        if (viewId == R.id.start_date || viewId == R.id.date_hint) {
            showDatePickerDialog();
        }
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(KEY_START_DATE, startDateTextView.getText().toString());
    }

    @Override
    public void onResume() {
        super.onResume();
        if (dateFromDatePicker != null) {
            startDateTextView.setText(dateFromDatePicker);
        }
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onDetach() {
        super.onDetach();
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

    private void showDatePickerDialog() {
        DialogFragment fragment = new StartDatePickerFragment();
        fragment.show(getFragmentManager(), "startDatePicker");
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
            dateFromDatePicker = i2 + "/" + i1 + "/" + i;
            startDateTextView.setText(i2 + "/" + i1 + "/" + i);
        }

    }
    private String setDefaultDate() {
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH) + 1;
        int day = c.get(Calendar.DAY_OF_MONTH);
        return day + "/" + month + "/" + year;
    }
}
