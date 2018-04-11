package ru.ssermakov.newrecycler.view.fragments;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.view.View;
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

/**
 * Created by btb_wild on 13.03.2018.
 */

public class AbstractTabFragment extends Fragment implements FragmentInterface {

    protected Context context;
    private String title;
    protected View view;
    protected FragmentController fragmentController;
    protected int id;
    protected ArrayList<String> symptoms;
    protected static TextView startDateTextView;
    protected  TextView symptomsList;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void testToast() {
        Toast.makeText(context, "Test Toast!", Toast.LENGTH_SHORT).show();
    }


}
