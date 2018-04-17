package ru.ssermakov.newrecycler.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import ru.ssermakov.newrecycler.R;
import ru.ssermakov.newrecycler.logic.adapters.TabsPagerFragmentAdapter;

public class BeginIllnessActivity extends AppCompatActivity {

    private TabLayout tabLayout;
    public static ViewPager viewPager;
    public static Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_illness);

        intent = getIntent();
        initTabs();

    }

    private void initTabs() {
        viewPager = findViewById(R.id.view_pager);
        TabsPagerFragmentAdapter adapter = new TabsPagerFragmentAdapter(this, getSupportFragmentManager());
        viewPager.setAdapter(adapter);


        tabLayout = findViewById(R.id.tabLayout);
        tabLayout.setupWithViewPager(viewPager);
    }

}
