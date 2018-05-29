package ru.ssermakov.newrecycler.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import java.util.List;

import ru.ssermakov.newrecycler.R;
import ru.ssermakov.newrecycler.logic.HistoryIllnessController;
import ru.ssermakov.newrecycler.view.Interfaces.HistoryIllnessInterface;

public class HistoryIllnessActivity extends AppCompatActivity implements HistoryIllnessInterface {

    private Long caseId;
    private int patientId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history_illness);

        getIncomingIntent();
        new HistoryIllnessController(this, caseId);

        FloatingActionButton editButton = findViewById(R.id.fabEditIllness);
        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(HistoryIllnessActivity.this, BeginIllnessActivity.class);
                i.putExtra(PersonDetailActivity.KEY_PATIENT_ID, patientId);
                i.putExtra(PersonDetailActivity.KEY_CASE_ID, caseId);
                startActivity(i);
            }
        });

    }

    private void getIncomingIntent() {
        if (getIntent().hasExtra(PersonDetailActivity.KEY_CASE_ID) &&
                getIntent().hasExtra(PersonDetailActivity.KEY_PATIENT_ID)) {

            caseId = getIntent().getLongExtra(PersonDetailActivity.KEY_CASE_ID, -1);
            patientId = getIntent().getIntExtra(PersonDetailActivity.KEY_PATIENT_ID, -1);
        }


    }


    @Override
    public void setStartDate(String startDate) {
        TextView start = findViewById(R.id.tvStartDate);
        start.setText(startDate);
    }

    @Override
    public void setEndDate(String endDate) {
        TextView end = findViewById(R.id.tvEndDate);
        end.setText(endDate);

    }

    @Override
    public void setIllnessName(String name) {
        TextView tvName = findViewById(R.id.tvIllnessName);
        tvName.setText(name);
    }

    @Override
    public void setPlans(String string) {
        TextView tvPlans = findViewById(R.id.tvPlans);
        tvPlans.setText(string);

    }

    @Override
    public void setSymptoms(String string) {
        TextView tvSymptoms = findViewById(R.id.tvSymptoms);
        tvSymptoms.setText(string);
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent i = new Intent(this, PersonDetailActivity.class);
        i.putExtra(PersonDetailActivity.KEY_PATIENT_ID, patientId);
        startActivity(i);
    }
}
