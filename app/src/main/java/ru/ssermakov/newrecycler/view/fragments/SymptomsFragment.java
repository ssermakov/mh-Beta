package ru.ssermakov.newrecycler.view.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import ru.ssermakov.newrecycler.R;
import studio.carbonylgroup.textfieldboxes.TextFieldBoxes;

/**
 * Created by btb_wild on 13.03.2018.
 */

public class SymptomsFragment extends AbstractTabFragment {

    TextFieldBoxes textFieldBoxes;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_symptoms, container, false);
        return view;
    }

    public static Fragment getInstance(Context context) {
        Bundle args = new Bundle();
        SymptomsFragment fragment = new SymptomsFragment();
        fragment.setArguments(args);
        fragment.setContext(context);
        fragment.setTitle(context.getString(R.string.tab_item_symptoms));

        return fragment;
    }

    public void setContext(Context context) {
        this.context = context;
    }

}
