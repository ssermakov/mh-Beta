package ru.ssermakov.newrecycler.view.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import ru.ssermakov.newrecycler.R;
import ru.ssermakov.newrecycler.app.App;
import ru.ssermakov.newrecycler.data.DBHelper;
import ru.ssermakov.newrecycler.data.DataSource;
import ru.ssermakov.newrecycler.data.room.MedicalHistoryDatabase;
import ru.ssermakov.newrecycler.data.room.dao.IllnessDao;
import ru.ssermakov.newrecycler.data.room.entity.Illness;
import ru.ssermakov.newrecycler.logic.FragmentController;
import ru.ssermakov.newrecycler.view.BeginIllnessActivity;
import ru.ssermakov.newrecycler.view.MainActivity;
import studio.carbonylgroup.textfieldboxes.ExtendedEditText;
import studio.carbonylgroup.textfieldboxes.SimpleTextChangedWatcher;
import studio.carbonylgroup.textfieldboxes.TextFieldBoxes;

/**
 * Created by btb_wild on 13.03.2018.
 */

public class PlanFragment extends AbstractTabFragment implements View.OnClickListener, SimpleTextChangedWatcher {

    Button button;

    TextFieldBoxes textFieldBoxesPlans;
    ExtendedEditText extendedEditTextPlans;
    private String allText;




    public PlanFragment() {
        fragmentController = new FragmentController(this);
        Intent i = BeginIllnessActivity.intent;
        id = i.getIntExtra(MainActivity.EXTRA_ID, -1);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_plan, container, false);

        textFieldBoxesPlans = view.findViewById(R.id.text_field_boxes_plan);
        extendedEditTextPlans = view.findViewById(R.id.extended_edit_text_plan);
        plansListTextView = view.findViewById(R.id.plansListTextView);
        button = view.findViewById(R.id.button);


        textFieldBoxesPlans.setOnClickListener(this);
        textFieldBoxesPlans.getEndIconImageButton().setOnClickListener(this);
        textFieldBoxesPlans.setSimpleTextChangeWatcher(this);
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    fragmentController.sendIllnessToDb(extendedEditTextIllnessName.getText().toString().trim().toLowerCase());
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                Intent i = new Intent(getContext(), MainActivity.class);
                startActivity(i);
            }
        });
    }


    @Override
    public void onClick(View v) {
        int viewId = v.getId();
        if (viewId == R.id.text_field_boxes_plan) {
            if (allText == null) {
                allText = "1. ";
                plansListTextView.setText(allText);
            }
        }

        String s = extendedEditTextPlans.getText().toString().trim();
        if (!s.equals("")) {
            plans.add(s);
            if (allText == null) {
                allText = String.valueOf(plans.size()) + ". " + s + "\n" + String.valueOf(plans.size() + 1) + ". ";
            } else {
                allText += s + "\n" + String.valueOf(plans.size() + 1) + ". ";
            }
        }
        extendedEditTextPlans.setText("");
    }

    @Override
    public void onTextChanged(String s, boolean b) {
        String temp = allText;
        if (s.equals("")) {
            if (allText != null) {
                temp = temp.substring(0, temp.length() - 3);
                plansListTextView.setText(temp);
            }
        }
        if (allText == null) {
            plansListTextView.setText(s);
        } else {
            plansListTextView.setText(temp + s);
        }
    }

    public static Fragment getInstance(Context context) {
        Bundle args = new Bundle();
        PlanFragment fragment = new PlanFragment();
        fragment.setArguments(args);
        fragment.setContext(context);
        fragment.setTitle(context.getString(R.string.tab_item_plan));

        return fragment;
    }

    public void setContext(Context context) {
        this.context = context;
    }

}
