package ru.ssermakov.newrecycler.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import java.util.List;

import ru.ssermakov.newrecycler.R;
import ru.ssermakov.newrecycler.logic.HistoryIllnessController;
import ru.ssermakov.newrecycler.view.Interfaces.HistoryIllnessInterface;

public class HistoryIllnessActivity extends AppCompatActivity implements HistoryIllnessInterface {

    private Long caseId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history_illness);

        getIncomingIntent();
        new HistoryIllnessController(this, caseId);
    }

    private void getIncomingIntent() {
        if (getIntent().hasExtra(PersonDetailActivity.KEY_CASE_ID)) {
            caseId = getIntent().getLongExtra(PersonDetailActivity.KEY_CASE_ID, -1);
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
}
