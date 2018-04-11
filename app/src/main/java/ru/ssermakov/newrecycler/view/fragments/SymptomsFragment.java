package ru.ssermakov.newrecycler.view.fragments;

import android.arch.persistence.room.util.StringUtil;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;

import ru.ssermakov.newrecycler.R;
import studio.carbonylgroup.textfieldboxes.ExtendedEditText;
import studio.carbonylgroup.textfieldboxes.SimpleTextChangedWatcher;
import studio.carbonylgroup.textfieldboxes.TextFieldBoxes;

/**
 * Created by btb_wild on 13.03.2018.
 */

public class SymptomsFragment extends AbstractTabFragment implements View.OnClickListener, SimpleTextChangedWatcher {

    TextFieldBoxes textFieldBoxes;
    ExtendedEditText extendedEditText;
    private String allText;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_symptoms, container, false);

        textFieldBoxes = view.findViewById(R.id.text_field_boxes);
        extendedEditText = view.findViewById(R.id.extended_edit_text);
        symptomsList = view.findViewById(R.id.symptomsListTextView);

        symptoms = new ArrayList<>();
        textFieldBoxes.setOnClickListener(this);
        textFieldBoxes.getEndIconImageButton().setOnClickListener(this);
        textFieldBoxes.setSimpleTextChangeWatcher(this);
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

    @Override
    public void onClick(View v) {
        int viewId = v.getId();
        if (viewId == R.id.text_field_boxes) {
            allText = "1. ";
            symptomsList.setText(allText);
        }

        String s = extendedEditText.getText().toString().trim();
        if (!s.equals("")) {
            symptoms.add(s);
            if (allText == null) {
                allText = String.valueOf(symptoms.size()) + ". " + s + "\n" + String.valueOf(symptoms.size() + 1) + ". ";
            } else {
                allText += s + "\n" + String.valueOf(symptoms.size() + 1) + ". ";
            }
        }
        extendedEditText.setText("");
    }


    @Override
    public void onTextChanged(String s, boolean b) {
        String temp= allText;
        if (s.equals("")) {
            if (allText != null) {
                temp = temp.substring(0, temp.length() - 3);
                symptomsList.setText(temp);
            }
        }
        if (allText == null) {
            symptomsList.setText(s);
        } else {
            symptomsList.setText(temp + s);
        }
    }
}
