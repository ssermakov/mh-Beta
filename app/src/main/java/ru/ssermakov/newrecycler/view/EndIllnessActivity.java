package ru.ssermakov.newrecycler.view;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.ExecutionException;

import ru.ssermakov.newrecycler.R;
import ru.ssermakov.newrecycler.logic.EndIllnessController;

public class EndIllnessActivity extends AppCompatActivity implements View.OnClickListener {

    private int id;
    private int position;
    private static TextView endDateTextView;
    private TextView endDateHint;
    private FloatingActionButton confirmFab;
    public static final String KEY_EXTRA_ID = "EXTRA_ID";
    public static final String KEY_EXTRA_POSITION = "EXTRA_POSITION";
    private EndIllnessController endIllnessController;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_end_illness);

        Intent i = getIntent();
        id = i.getIntExtra(MainActivity.EXTRA_ID, -1);
        position = i.getIntExtra(MainActivity.EXTRA_POSITION, -1);
        endIllnessController = new EndIllnessController();

        endDateTextView = findViewById(R.id.end_date);
        endDateHint = findViewById(R.id.end_date_hint);
        confirmFab = findViewById(R.id.fabConfirm);


        endDateTextView.setText(setDefaultDate());

        endDateTextView.setOnClickListener(this);
        endDateHint.setOnClickListener(this);
        confirmFab.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {
        int viewId = v.getId();
        if (viewId == R.id.end_date || viewId == R.id.end_date_hint) {
            showDatePickerDialog();
        }
        if (viewId == R.id.fabConfirm) {
            endIllnessController.togglePatientState(id);
            try {
                endIllnessController.setEndDateOfIllness(
                        id,
                        convertStringToDate(endDateTextView.getText().toString()
                        ));
            } catch (ExecutionException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            startMainActivity();
        }
    }

    @Override
    public void onBackPressed() {
        startMainActivity();
    }

    private Date convertStringToDate(String string) {
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        Date date = null;
        try {
            date = dateFormat.parse(string);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    private void startMainActivity() {
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
        int month = c.get(Calendar.MONTH) + 1;
        int day = c.get(Calendar.DAY_OF_MONTH);
        return day + "/" + month + "/" + year;
    }
}
