package ru.ssermakov.newrecycler.view;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TextView;

import java.util.Calendar;

import ru.ssermakov.newrecycler.R;

public class EndIllnessActivity extends AppCompatActivity implements View.OnClickListener {

    private int id;
    private int position;
    private static TextView endDateTextView;
    private TextView endDateHint;
    public static final String KEY_EXTRA_ID = "EXTRA_ID";
    public static final String KEY_EXTRA_POSITION = "EXTRA_POSITION";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_end_illness);

        Intent i = getIntent();
        id = i.getIntExtra(MainActivity.EXTRA_ID, -1);
        position = i.getIntExtra(MainActivity.EXTRA_POSITION, -1);

        endDateTextView = findViewById(R.id.end_date);
        endDateHint = findViewById(R.id.end_date_hint);

        endDateTextView.setText(setDefaultDate());

        endDateTextView.setOnClickListener(this);
        endDateHint.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        int viewId = v.getId();
        if (viewId == R.id.end_date || viewId == R.id.end_date_hint) {
            showDatePickerDialog();
        }
    }

    @Override
    public void onBackPressed() {
        Intent i = new Intent(this, MainActivity.class);
        i.putExtra(KEY_EXTRA_POSITION, position);
        i.putExtra(KEY_EXTRA_ID, id);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(i);
    }

    private void showDatePickerDialog() {
        DialogFragment fragment = new DatePickerFragment();
        fragment.show(getSupportFragmentManager(), "startDatePicker");
    }

    public static class DatePickerFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {


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
            endDateTextView.setText(i2 + "/" + i1 + "/" + i);
        }
    }

    private String setDefaultDate() {
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);
        return day + "/" + month + "/" + year;
    }
}
