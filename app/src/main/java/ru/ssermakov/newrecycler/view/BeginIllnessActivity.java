package ru.ssermakov.newrecycler.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import ru.ssermakov.newrecycler.R;
import ru.ssermakov.newrecycler.logic.MainController;
import ru.ssermakov.newrecycler.logic.adapters.TabsPagerFragmentAdapter;

public class BeginIllnessActivity extends AppCompatActivity {

    private TabLayout tabLayout;
    public static ViewPager viewPager;
    public static Intent intent;
    public static final String KEY_EXTRA_ID = "EXTRA_ID";
    public static final String KEY_EXTRA_POSITION = "EXTRA_POSITION";
    private int position;
    private int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_illness);

        intent = getIntent();
        id = intent.getIntExtra(MainController.EXTRA_ID, -1);
        position = intent.getIntExtra(MainController.EXTRA_POSITION, -1);
        initTabs();
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
        TabsPagerFragmentAdapter adapter = new TabsPagerFragmentAdapter(this, getSupportFragmentManager());
        viewPager.setAdapter(adapter);


        tabLayout = findViewById(R.id.tabLayout);
        tabLayout.setupWithViewPager(viewPager);
    }

}
