package ru.ssermakov.newrecycler.view;

import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import java.util.concurrent.ExecutionException;

import ru.ssermakov.newrecycler.R;
import ru.ssermakov.newrecycler.data.room.entity.Case;
import ru.ssermakov.newrecycler.logic.BeginIllnessActivityController;
import ru.ssermakov.newrecycler.logic.MainController;
import ru.ssermakov.newrecycler.logic.adapters.TabsPagerFragmentAdapter;
import ru.ssermakov.newrecycler.view.Interfaces.BeginIllnessActivityInterface;
import ru.ssermakov.newrecycler.view.fragments.AbstractTabFragment;
import ru.ssermakov.newrecycler.view.fragments.ItemChangePlanDialogFragment;
import ru.ssermakov.newrecycler.view.fragments.ItemChangeSymptomDialogFragment;
import ru.ssermakov.newrecycler.view.fragments.PlanFragment;
import ru.ssermakov.newrecycler.view.fragments.SymptomsFragment;

public class BeginIllnessActivity extends AppCompatActivity
        implements ItemChangeSymptomDialogFragment.EventListener,
        ItemChangePlanDialogFragment.EventListener,
        AbstractTabFragment.ItemContentPassListener {

    private TabLayout tabLayout;
    public static ViewPager viewPager;
    public static Intent intent;
    public static final String KEY_EXTRA_ID = "EXTRA_ID";
    public static final String KEY_EXTRA_POSITION = "EXTRA_POSITION";
    private int position;
    private int id;
    public static FragmentManager fm;
    public static String itemContent;
    public static String no_illness;
    private int patientIdForEditIllness = -1;
    private long caseId = -1;
    public static Case aCase;
    public static BeginIllnessActivityController controller;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_illness);

        getIncomingIntent();
        initTabs();
        aCase = null;
        if (patientIdForEditIllness != -1 && caseId != -1) {
            try {
                aCase = getCaseByCaseId(caseId);
            } catch (ExecutionException | InterruptedException e) {
                e.printStackTrace();
            }
        }

        no_illness = getResources().getString(R.string.no_illness);
        fm = getFragmentManager();


    }



    private Case getCaseByCaseId(long caseId) throws ExecutionException, InterruptedException {
        controller = new BeginIllnessActivityController(caseId);
        return controller.getCase(caseId);
    }

    private void getIncomingIntent() {
        intent = getIntent();
        if (intent.hasExtra(MainController.EXTRA_ID) &&
            intent.hasExtra(MainController.EXTRA_POSITION)) {

            id = intent.getIntExtra(MainController.EXTRA_ID, -1);
            position = intent.getIntExtra(MainController.EXTRA_POSITION, -1);
        }
        if (intent.hasExtra(PersonDetailActivity.KEY_PATIENT_ID) &&
            intent.hasExtra(PersonDetailActivity.KEY_CASE_ID)) {

            patientIdForEditIllness = intent.getIntExtra(PersonDetailActivity.KEY_PATIENT_ID, -1);
            caseId = intent.getLongExtra(PersonDetailActivity.KEY_CASE_ID, -1);
        }
    }

    @Override
    public void onBackPressed() {
        Intent i = new Intent(this, MainActivity.class);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        i.putExtra(KEY_EXTRA_POSITION, position);
        i.putExtra(KEY_EXTRA_ID, id);
        startActivity(i);
    }

    private void initTabs() {
        viewPager = findViewById(R.id.view_pager);
        tabLayout = findViewById(R.id.tabLayout);
        TabsPagerFragmentAdapter adapter = new TabsPagerFragmentAdapter(this, getSupportFragmentManager());
        viewPager.setAdapter(adapter);

        tabLayout.setupWithViewPager(viewPager);

        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });


    }

    @Override
    public void editSymptom(String s) {
        SymptomsFragment.listOfSymptoms.set(AbstractTabFragment.currentItemPosition, s);
        SymptomsFragment.CustomSymptomsAdapter adapter = SymptomsFragment.adapter;
        adapter.notifyDataSetChanged();
    }

    @Override
    public void editPlan(String s) {
        PlanFragment.listOfPlans.set(AbstractTabFragment.currentItemPosition, s);
        PlanFragment.CustomPlansAdapter adapter = PlanFragment.adapter;
        adapter.notifyDataSetChanged();
    }

    @Override
    public void passData(String data) {
        itemContent = data;
    }

}
