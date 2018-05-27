package ru.ssermakov.newrecycler.view.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import ru.ssermakov.newrecycler.logic.FragmentController;
import ru.ssermakov.newrecycler.view.Interfaces.FragmentInterface;
import studio.carbonylgroup.textfieldboxes.ExtendedEditText;

/**
 * Created by btb_wild on 13.03.2018.
 */

public class AbstractTabFragment extends Fragment implements FragmentInterface {


    public interface ItemContentPassListener {
        void passData(String data);
    }

    protected Context context;
    private String title;
    protected View view;
    protected static FragmentController fragmentController;
    protected int id;
    protected int position;
    protected static ArrayList<String> symptoms, plans;
    protected static TextView startDateTextView;
    protected TextView dateHint;
    protected TextView plansListTextView;
    protected static String dateFromDatePicker;

    protected static ExtendedEditText extendedEditTextIllnessName;

    protected static android.support.v4.app.DialogFragment itemChangeDialog;
    public static int currentItemPosition;
    protected static FragmentManager fragmentManager;
    protected static ItemContentPassListener mCallback;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        itemChangeDialog = new ItemChangeDialogFragment();
        fragmentManager = getFragmentManager();

        try {
            mCallback = (ItemContentPassListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + " must implement ItemContentPassListener");
        }
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

    @Override
    public void startDialogToChangeItemContent(int i, String s) {
        currentItemPosition = i;
        showItemChangeDialog(s);
    }

    private void showItemChangeDialog(String s) {
        mCallback.passData(s);
        itemChangeDialog.show(fragmentManager, "ta");
    }

}
