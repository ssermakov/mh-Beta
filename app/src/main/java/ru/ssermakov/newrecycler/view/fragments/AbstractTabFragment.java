package ru.ssermakov.newrecycler.view.fragments;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.Toast;

import ru.ssermakov.newrecycler.data.DBHelper;
import ru.ssermakov.newrecycler.data.DataSource;
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
