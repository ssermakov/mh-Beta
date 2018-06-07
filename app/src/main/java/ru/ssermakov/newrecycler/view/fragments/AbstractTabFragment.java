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
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import ru.ssermakov.newrecycler.logic.BeginIllnessActivityController;
import ru.ssermakov.newrecycler.logic.FragmentController;
import ru.ssermakov.newrecycler.view.BeginIllnessActivity;
import ru.ssermakov.newrecycler.view.Interfaces.FragmentInterface;
import studio.carbonylgroup.textfieldboxes.ExtendedEditText;

/**
 * Created by btb_wild on 13.03.2018.
 */

public class AbstractTabFragment extends Fragment implements FragmentInterface {


    private BeginIllnessActivityController controller;

    public interface ItemContentPassListener {
        void passData(String data);
    }

    protected Context context;
    private String title;
    protected View view;
    protected static FragmentController fragmentController;
    protected int id;
    protected int position;
    protected static TextView startDateTextView;
    public static List<String> listOfPlans;
    protected TextView dateHint;
    protected static String dateFromDatePicker;

    protected static ExtendedEditText extendedEditTextIllnessName;

    protected static android.support.v4.app.DialogFragment symptomChangeDialog;
    protected static android.support.v4.app.DialogFragment planChangeDialog;
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
        symptomChangeDialog = new ItemChangeSymptomDialogFragment();
        planChangeDialog = new ItemChangePlanDialogFragment();
        fragmentManager = getFragmentManager();
        listOfPlans = new ArrayList<>();

        if (BeginIllnessActivity.aCase != null) {
            setListOfPlans();
        }

        try {
            mCallback = (ItemContentPassListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + " must implement ItemContentPassListener");
        }
    }

    private void setListOfPlans() {
        try {
            listOfPlans = BeginIllnessActivity.controller.getListOfPlans();
            if (listOfPlans.get(0) == null) {
                listOfPlans.remove(0);
            }
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
    }




    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void startDialogToChangeSymptomContent(int i, String s) {
        currentItemPosition = i;
        showItemChangeSymptomDialog(s);
    }

    @Override
    public void startDialogToChangePlanContent(int i, String s) {
        currentItemPosition = i;
        showItemChangePlanDialog(s);
    }

    private void showItemChangePlanDialog(String s) {
        mCallback.passData(s);
        planChangeDialog.show(fragmentManager, "planDialog");
    }

    private void showItemChangeSymptomDialog(String s) {
        mCallback.passData(s);
        symptomChangeDialog.show(fragmentManager, "symptomDialog");
    }

}
