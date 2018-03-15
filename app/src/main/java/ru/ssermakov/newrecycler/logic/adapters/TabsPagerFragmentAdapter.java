package ru.ssermakov.newrecycler.logic.adapters;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.HashMap;
import java.util.Map;

import ru.ssermakov.newrecycler.view.fragments.AbstractTabFragment;
import ru.ssermakov.newrecycler.view.fragments.DateFragment;
import ru.ssermakov.newrecycler.view.fragments.PlanFragment;
import ru.ssermakov.newrecycler.view.fragments.SymptomsFragment;

/**
 * Created by btb_wild on 13.03.2018.
 */

public class TabsPagerFragmentAdapter extends FragmentPagerAdapter {

    private Map<Integer, AbstractTabFragment> tabs;
    Context context;

    public TabsPagerFragmentAdapter(Context context, FragmentManager fm) {
        super(fm);
        this.context = context;

        initTabsMap(context);
    }

    @Override
    public Fragment getItem(int position) {
        return tabs.get(position);
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return tabs.get(position).getTitle();
    }

    @Override
    public int getCount() {
        return tabs.size();
    }

    private void initTabsMap(Context context) {
        tabs = new HashMap<>();
        tabs.put(0, (AbstractTabFragment) DateFragment.getInstance(context));
        tabs.put(1, (AbstractTabFragment) SymptomsFragment.getInstance(context));
        tabs.put(2, (AbstractTabFragment) PlanFragment.getInstance(context));
    }
}
