package ru.ssermakov.newrecycler.view.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutionException;

import ru.ssermakov.newrecycler.R;
import ru.ssermakov.newrecycler.view.BeginIllnessActivity;
import studio.carbonylgroup.textfieldboxes.ExtendedEditText;
import studio.carbonylgroup.textfieldboxes.TextFieldBoxes;

/**
 * Created by btb_wild on 13.03.2018.
 */

public class SymptomsFragment extends AbstractTabFragment implements View.OnClickListener {



    TextFieldBoxes textFieldBoxesSymptoms;
    ExtendedEditText extendedEditTextSymptoms;
    private LayoutInflater layoutInflater;
    public static List<String> listOfSymptoms;
    public static CustomSymptomsAdapter adapter;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_symptoms, container, false);

        textFieldBoxesSymptoms = view.findViewById(R.id.text_field_boxes);
        extendedEditTextSymptoms = view.findViewById(R.id.extended_edit_text);

        listOfSymptoms = new ArrayList<>();
        setHasOptionsMenu(true);


        if (BeginIllnessActivity.aCase != null) {
            setListOfSymptoms();
        }

        if (savedInstanceState != null && savedInstanceState.containsKey(KEY_LIST_OF_SYMPTOMS)) {
            listOfSymptoms = savedInstanceState.getStringArrayList(KEY_LIST_OF_SYMPTOMS);
        }

        RecyclerView symptomsRecycler = view.findViewById(R.id.symptoms_recycler);
        layoutInflater = getLayoutInflater();
        symptomsRecycler.setLayoutManager(new LinearLayoutManager(getContext()));

        textFieldBoxesSymptoms.getEndIconImageButton().setOnClickListener(this);

        adapter = new CustomSymptomsAdapter();
        symptomsRecycler.setAdapter(adapter);


        return view;
    }

    private void setListOfSymptoms() {
        try {
            listOfSymptoms = BeginIllnessActivity.controller.getListOfSymptoms();
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
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

    private static final String KEY_LIST_OF_SYMPTOMS = "LIST_OF_SYMPTOMS";
    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        if (!listOfSymptoms.isEmpty()) {
            outState.putStringArrayList(KEY_LIST_OF_SYMPTOMS, (ArrayList<String>) listOfSymptoms);
        }
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        if (!fragmentController.getListOfSelectedItems().isEmpty()) {
            inflater.inflate(R.menu.symptoms_fragment_menu, menu);
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.fragment_recycler_delete_person) {
            Collections.sort(fragmentController.getListOfSelectedItems(), Collections.<Integer>reverseOrder());
            for (int i = 0; i < fragmentController.getListOfSelectedItems().size(); i++) {
                int k = fragmentController.getListOfSelectedItems().get(i);
                listOfSymptoms.remove(k);
            }
            adapter.notifyDataSetChanged();
            getActivity().invalidateOptionsMenu();
            fragmentController.getListOfSelectedItems().clear();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        listOfSymptoms.add(extendedEditTextSymptoms.getText().toString().trim());
        extendedEditTextSymptoms.setText("");
    }

    public class CustomSymptomsAdapter extends
            RecyclerView.Adapter<CustomSymptomsAdapter.CustomSymptomsViewHolder> {

        @Override
        public CustomSymptomsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            if (viewType == 1) {
                View v = layoutInflater.inflate(R.layout.symptom_item_recycler_selected_layout, parent, false);
                return new CustomSymptomsViewHolder(v);
            }
            View v = layoutInflater.inflate(R.layout.symptom_item_recycler_layout, parent, false);
            return new CustomSymptomsViewHolder(v);
        }

        @Override
        public void onBindViewHolder(final CustomSymptomsViewHolder holder, final int position) {
            if (listOfSymptoms.isEmpty()) {

                holder.symptomText.setVisibility(View.GONE);
                holder.editImage.setVisibility(View.GONE);

            } else {
                holder.symptomText.setVisibility(View.VISIBLE);
                holder.editImage.setVisibility(View.VISIBLE);

                String symptom = listOfSymptoms.get(holder.getAdapterPosition());

                holder.symptomText.setText(symptom);
            }

            View.OnClickListener onClickListenerContainer = new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (fragmentController.getListOfSelectedItems().isEmpty()) {
                        fragmentController.onSymptomItemClick(holder.getAdapterPosition(), holder.symptomText.getText().toString());
                    } else {
                        if (fragmentController.getListOfSelectedItems().contains(holder.getAdapterPosition())) {
                            fragmentController.removeItemFromSelected(holder.getAdapterPosition());
                            notifyDataSetChanged();
                            getActivity().invalidateOptionsMenu();
                        } else {
                            fragmentController.selectItem(holder.getAdapterPosition());
                            notifyDataSetChanged();
                        }
                    }
                }
            };

            holder.rootContainer.setOnClickListener(onClickListenerContainer);

            holder.rootContainer.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    fragmentController.selectItem(holder.getAdapterPosition());
                    notifyDataSetChanged();
                    getActivity().invalidateOptionsMenu();
                    return true;
                }
            });
        }

        @Override
        public int getItemViewType(int position) {
            for (int i = 0; i < fragmentController.getListOfSelectedItems().size(); i++) {
                if (position == fragmentController.getListOfSelectedItems().get(i)) {
                    return 1;
                }
            }
            return 0;
        }

        @Override
        public int getItemCount() {
            return listOfSymptoms.size();
        }

        class CustomSymptomsViewHolder extends RecyclerView.ViewHolder {

            private TextView symptomText;
            private ImageView editImage;
            private ConstraintLayout rootContainer;

            CustomSymptomsViewHolder(View itemView) {
                super(itemView);

                rootContainer = itemView.findViewById(R.id.rootContainer);
                symptomText = itemView.findViewById(R.id.symptomText);
                editImage = itemView.findViewById(R.id.editImage);
            }
        }

    }
}
