package ru.ssermakov.newrecycler.view.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import ru.ssermakov.newrecycler.R;
import studio.carbonylgroup.textfieldboxes.ExtendedEditText;
import studio.carbonylgroup.textfieldboxes.TextFieldBoxes;

/**
 * Created by btb_wild on 13.03.2018.
 */

public class SymptomsFragment extends AbstractTabFragment{

    TextFieldBoxes textFieldBoxesSymptoms;
    private RecyclerView symptomsRecycler;
    ExtendedEditText extendedEditTextSymptoms;
    private LayoutInflater layoutinflater;
    private List<String> listOfSymptoms;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_symptoms, container, false);

        textFieldBoxesSymptoms = view.findViewById(R.id.text_field_boxes);
        extendedEditTextSymptoms = view.findViewById(R.id.extended_edit_text);
        symptomsListTextView = view.findViewById(R.id.symptomsListTextView);


        symptomsRecycler = view.findViewById(R.id.symptoms_recycler);
        layoutinflater = getLayoutInflater();

        listOfSymptoms = new ArrayList<>();


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

    private class CustomSymptomsAdapter extends
            RecyclerView.Adapter<CustomSymptomsAdapter.CustomSymptomsViewHolder> {

        @Override
        public CustomSymptomsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View v = layoutinflater.inflate(R.layout.symptom_item_recycler_layout, parent, false);
            return new CustomSymptomsViewHolder(v);
        }

        @Override
        public void onBindViewHolder(CustomSymptomsViewHolder holder, int position) {
            if (listOfSymptoms.isEmpty()) {

                holder.symptomText.setVisibility(View.GONE);
                holder.editImage.setVisibility(View.GONE);

            } else {
                String symptom = listOfSymptoms.get(position);

                holder.symptomText.setText(symptom);
            }
        }

        @Override
        public int getItemCount() {
            return listOfSymptoms.size();
        }

        class CustomSymptomsViewHolder extends RecyclerView.ViewHolder {

            private TextView symptomText;
            private ImageView editImage;

            CustomSymptomsViewHolder(View itemView) {
                super(itemView);

                symptomText = itemView.findViewById(R.id.symptomText);
                editImage = itemView.findViewById(R.id.editImage);
            }
        }

    }
}
