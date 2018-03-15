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

import ru.ssermakov.newrecycler.R;
import ru.ssermakov.newrecycler.data.DBHelper;
import ru.ssermakov.newrecycler.data.DataSource;
import ru.ssermakov.newrecycler.logic.FragmentController;
import ru.ssermakov.newrecycler.view.BeginIllnessActivity;
import ru.ssermakov.newrecycler.view.MainActivity;
import studio.carbonylgroup.textfieldboxes.TextFieldBoxes;

/**
 * Created by btb_wild on 13.03.2018.
 */

public class PlanFragment extends AbstractTabFragment {

    Button button;
    private TextView textView;
    private TextFieldBoxes textFieldBoxes;

    public PlanFragment() {
        fragmentController = new FragmentController(
                new DataSource(
                        new DBHelper(getContext())
                ),
                this
        );
        Intent i = BeginIllnessActivity.intent;
        id = i.getIntExtra(MainActivity.EXTRA_ID, -1);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_plan, container, false);
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();

        textView = getActivity().findViewById(R.id.date_hint);
        textFieldBoxes = getActivity().findViewById(R.id.text_field_boxes);
        button = view.findViewById(R.id.button);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                fragmentController.onButtonSendClick();
                Intent i = new Intent(getContext(), MainActivity.class);
                String date = textView.getText().toString();
                String hint = textFieldBoxes.getLabelText().toString();
                i.putExtra("date", date);
                i.putExtra("hint", hint);
                startActivity(i);
            }
        });
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
