package ru.ssermakov.newrecycler.view.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import ru.ssermakov.newrecycler.R;
import ru.ssermakov.newrecycler.data.room.entity.Case;
import ru.ssermakov.newrecycler.logic.FragmentController;
import ru.ssermakov.newrecycler.logic.MainController;
import ru.ssermakov.newrecycler.view.BeginIllnessActivity;
import ru.ssermakov.newrecycler.view.HistoryIllnessActivity;
import ru.ssermakov.newrecycler.view.MainActivity;
import ru.ssermakov.newrecycler.view.PersonDetailActivity;
import studio.carbonylgroup.textfieldboxes.ExtendedEditText;
import studio.carbonylgroup.textfieldboxes.TextFieldBoxes;

/**
 * Created by btb_wild on 13.03.2018.
 */

public class PlanFragment extends AbstractTabFragment implements View.OnClickListener {


    FloatingActionButton button;

    TextFieldBoxes textFieldBoxesPlans;
    ExtendedEditText extendedEditTextPlans;
    private LayoutInflater layoutinflater;
    private Long caseId;
    public static final String KEY_POSITION = "POSITION";
    public static CustomPlansAdapter adapter;


    public PlanFragment() {
        fragmentController = new FragmentController(this, this);
        Intent i = BeginIllnessActivity.intent;
        id = i.getIntExtra(MainController.EXTRA_ID, -1);
        position = i.getIntExtra(MainController.EXTRA_POSITION, -1);
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


        button = view.findViewById(R.id.floatingActionButton);

        if (savedInstanceState != null && savedInstanceState.containsKey(KEY_LIST_OF_PLANS)) {
            listOfPlans = savedInstanceState.getStringArrayList(KEY_LIST_OF_PLANS);
        }

        RecyclerView plansRecycler = view.findViewById(R.id.plans_recycler);
        layoutinflater = getLayoutInflater();
        plansRecycler.setLayoutManager(new LinearLayoutManager(getContext()));

        textFieldBoxesPlans.getEndIconImageButton().setOnClickListener(this);

        adapter = new PlanFragment.CustomPlansAdapter();
        plansRecycler.setAdapter(adapter);

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (SymptomsFragment.listOfSymptoms.size() == 0) {
                    BeginIllnessActivity.viewPager.setCurrentItem(1, true);
                } else {
                    if (BeginIllnessActivity.aCase == null) {
                        createNewCase();
                    } else {
                        editCase(BeginIllnessActivity.aCase);
                    }
                }

            }
        });
    }

    private void editCase(Case aCase) {
        String illnessName = extendedEditTextIllnessName.getText().toString().trim().toLowerCase();

        try {
            fragmentController.createIllness(illnessName);
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }

        try {
            aCase.setIllnessId((long)fragmentController.getIllnessIdFromDb(illnessName));
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
        aCase.setStartDate(fragmentController.convertStringToDate(
                startDateTextView.getText().toString()
        ));

        fragmentController.updateCase(aCase);
        fragmentController.updateSymptoms(aCase.getId(), (ArrayList<String>) SymptomsFragment.listOfSymptoms);
        fragmentController.updatePlans(aCase.getId(), (ArrayList<String>) listOfPlans);

        Intent i = new Intent(getContext(), HistoryIllnessActivity.class);
        i.putExtra(PersonDetailActivity.KEY_CASE_ID, aCase.getId());
        i.putExtra(PersonDetailActivity.KEY_PATIENT_ID, (int)(long) aCase.getPatientId());
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(i);

    }


    private void createNewCase() {
        String illnessName = extendedEditTextIllnessName.getText().toString().trim().toLowerCase();

        try {
            fragmentController.createIllness(illnessName);
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }

        try {
            caseId = fragmentController.createCase(
                    id,
                    fragmentController.getIllnessIdFromDb(illnessName),
                    startDateTextView.getText().toString()
            );
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }

        fragmentController.togglePatientState(id);


        fragmentController.createSymptoms(caseId, (ArrayList<String>) SymptomsFragment.listOfSymptoms);
        fragmentController.createPlans(caseId, (ArrayList<String>) listOfPlans);

        Intent i = new Intent(getContext(), MainActivity.class);
        i.putExtra(KEY_POSITION, position);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(i);
    }


    @Override
    public void onClick(View v) {
        listOfPlans.add(extendedEditTextPlans.getText().toString().trim());
        extendedEditTextPlans.setText("");
    }

    public static Fragment getInstance(Context context) {
        Bundle args = new Bundle();
        PlanFragment fragment = new PlanFragment();
        fragment.setArguments(args);
        fragment.setContext(context);
        fragment.setTitle(context.getString(R.string.tab_item_plan));

        return fragment;
    }

    private static final String KEY_LIST_OF_PLANS = "LIST_OF_PLANS";
    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        if (!listOfPlans.isEmpty()) {
            outState.putStringArrayList(KEY_LIST_OF_PLANS, (ArrayList<String>) listOfPlans);
        }
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public class CustomPlansAdapter extends
            RecyclerView.Adapter<CustomPlansAdapter.CustomPlansViewHolder> {

        @Override
        public CustomPlansViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View v = layoutinflater.inflate(R.layout.plan_item_recycler_layout, parent, false);
            return new CustomPlansViewHolder(v);
        }

        @Override
        public void onBindViewHolder(final CustomPlansViewHolder holder, final int position) {
            if (listOfPlans.isEmpty()) {

                holder.planText.setVisibility(View.GONE);
                holder.editImage.setVisibility(View.GONE);

            } else {
                holder.planText.setVisibility(View.VISIBLE);
                holder.editImage.setVisibility(View.VISIBLE);

                String symptom = listOfPlans.get(position);

                holder.planText.setText(symptom);
            }

            View.OnClickListener onClickListenerContainer = new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    fragmentController.onPlanItemClick(position, holder.planText.getText().toString());
                }
            };
            holder.rootContainer.setOnClickListener(onClickListenerContainer);
        }

        @Override
        public int getItemCount() {
            return listOfPlans.size();
        }

        class CustomPlansViewHolder extends RecyclerView.ViewHolder {

            private TextView planText;
            private ImageView editImage;
            private ConstraintLayout rootContainer;

            CustomPlansViewHolder(View itemView) {
                super(itemView);

                rootContainer = itemView.findViewById(R.id.rootContainer);
                planText = itemView.findViewById(R.id.planText);
                editImage = itemView.findViewById(R.id.editImage);
            }
        }

    }

}
