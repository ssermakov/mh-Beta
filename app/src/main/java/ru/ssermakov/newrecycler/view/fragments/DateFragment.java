package ru.ssermakov.newrecycler.view.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import ru.ssermakov.newrecycler.R;

/**
 * Created by btb_wild on 13.03.2018.
 */

public class DateFragment extends AbstractTabFragment {

    TextView textView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_date, container, false);
        return view;
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

}
