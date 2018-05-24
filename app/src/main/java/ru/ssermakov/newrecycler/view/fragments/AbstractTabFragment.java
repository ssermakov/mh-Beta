package ru.ssermakov.newrecycler.view.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import ru.ssermakov.newrecycler.data.DBHelper;
import ru.ssermakov.newrecycler.data.DataSource;
import ru.ssermakov.newrecycler.data.room.entity.Case;
import ru.ssermakov.newrecycler.logic.FragmentController;
import ru.ssermakov.newrecycler.view.BeginIllnessActivity;
import ru.ssermakov.newrecycler.view.Interfaces.FragmentInterface;
import ru.ssermakov.newrecycler.view.MainActivity;
import studio.carbonylgroup.textfieldboxes.ExtendedEditText;

/**
 * Created by btb_wild on 13.03.2018.
 */

public class AbstractTabFragment extends Fragment implements FragmentInterface {

    protected Context context;
    private String title;
    protected View view;
    protected FragmentController fragmentController;
    protected int id;
    protected int position;
    protected static ArrayList<String> symptoms, plans;
    protected static TextView startDateTextView;
    protected  TextView dateHint;
    protected  TextView symptomsListTextView, plansListTextView;
    protected static String dateFromDatePicker;

    protected static ExtendedEditText extendedEditTextIllnessName;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        plans = new ArrayList<>();
        symptoms = new ArrayList<>();



    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

}
