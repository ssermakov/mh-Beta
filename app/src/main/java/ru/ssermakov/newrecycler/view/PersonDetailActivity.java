package ru.ssermakov.newrecycler.view;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutionException;

import ru.ssermakov.newrecycler.R;
import ru.ssermakov.newrecycler.Utils.Utils;
import ru.ssermakov.newrecycler.data.room.entity.Case;
import ru.ssermakov.newrecycler.data.room.entity.Illness;
import ru.ssermakov.newrecycler.data.room.entity.Patient;
import ru.ssermakov.newrecycler.logic.DetailController;
import ru.ssermakov.newrecycler.view.Interfaces.DetailActivityInterface;

public class PersonDetailActivity extends AppCompatActivity implements DetailActivityInterface, View.OnClickListener {

    private TextView name;
    private TextView textViewHistory;
    private ImageView imageViewDetailPhoto;
    private RecyclerView recyclerView;
    private List<Case> listOfCases;
    private LayoutInflater layoutInflater;
    private CustomPersonAdapter adapter;
    private DetailController detailController;
    private FloatingActionButton fab;
    public static int id;
    public static Patient patient;
    public static final String KEY_CASE_ID = "CASE_ID";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person_detail);

        getIncomingIntent();

        textViewHistory = findViewById(R.id.textViewHistory);

        recyclerView = findViewById(R.id.illnessRecycler);
        layoutInflater = getLayoutInflater();

        fab = findViewById(R.id.editFAB);
        fab.setOnClickListener(this);


        detailController = new DetailController(this);

        try {
            patient = detailController.getPatientById((long) id);
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }

        name = findViewById(R.id.name);
        name.setText(patient.getName());

        imageViewDetailPhoto = findViewById(R.id.imageViewDetailPhoto);
        Bitmap bm = BitmapFactory.decodeFile(patient.getImageUrl());
        imageViewDetailPhoto.setImageBitmap(bm);

    }

    private void getIncomingIntent() {
        Intent i = getIntent();
        if (i.hasExtra(MainActivity.EXTRA_ID)) {
            id = i.getIntExtra(MainActivity.EXTRA_ID, -1);
        }
        if (i.hasExtra(PersonDetailActivity.KEY_PATIENT_ID)) {
            id = i.getIntExtra(PersonDetailActivity.KEY_PATIENT_ID, -1);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (!detailController.getListOfSelectedItems().isEmpty()) {
            getMenuInflater().inflate(R.menu.detail_activity_menu, menu);
            adapter.notifyDataSetChanged();
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.sm_delete_person) {
            Collections.sort(detailController.getListOfSelectedItems(), Collections.<Integer>reverseOrder());
            for (int i = 0; i < detailController.getListOfSelectedItems().size(); i++) {
                int k = detailController.getListOfSelectedItems().get(i);
                Case currentCase = listOfCases.get(k);
                detailController.deleteCaseFromDb(currentCase);
                listOfCases.remove(k);
                if (currentCase.getEndDate() == null) {
                    detailController.setPatientStateAtNotIll(currentCase);
                }
            }
            adapter.notifyDataSetChanged();
            invalidateOptionsMenu();
            detailController.getListOfSelectedItems().clear();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent i = new Intent(this, MainActivity.class);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(i);
    }

    @Override
    public void setUpAdapterAndView(List<Case> listOfCases) {
        this.listOfCases = listOfCases;
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new CustomPersonAdapter();
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void setAge(String ageString) {
        TextView age = findViewById(R.id.tvAge);
        age.setText(ageString);
    }

    @Override
    public void setEditFAB() {
        Utils.scale(fab, 400);
    }

    public static final String KEY_PATIENT_ID = "PATIENT_ID";

    @Override
    public void onClick(View v) {
        int viewId = v.getId();
        if (viewId == R.id.editFAB) {
            Intent i = new Intent(this, AddPersonActivity.class);
            i.putExtra(KEY_PATIENT_ID, patient.getId());
            startActivity(i);
        }
    }

    private class CustomPersonAdapter extends
            RecyclerView.Adapter<CustomPersonAdapter.CustomPersonViewHolder> {

        @Override
        public CustomPersonAdapter.CustomPersonViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            if (viewType == 1) {
                View v = layoutInflater.inflate(R.layout.person_detail_recycler_selected_layout, parent, false);
                return new CustomPersonViewHolder(v);
            }
            View v = layoutInflater.inflate(R.layout.person_detail_recycler_layout, parent, false);
            return new CustomPersonViewHolder(v);
        }

        @Override
        public int getItemViewType(int position) {
          for (int i = 0; i < detailController.getListOfSelectedItems().size(); i++) {
              if (position == detailController.getListOfSelectedItems().get(i)) {
                  return 1;
              }
          }
            return 0;
        }

        @Override
        public void onBindViewHolder(final CustomPersonAdapter.CustomPersonViewHolder holder, final int position) {
            final Case aCase = listOfCases.get(position);

            holder.startDate.setText(
                    detailController.convertDateToString(
                            aCase.getStartDate()
                    )
            );


            if (aCase != null) {
                holder.endDate.setText(
                        detailController.convertDateToString(
                                aCase.getEndDate()
                        )
                );
            }

            Illness illness = null;
            try {
                illness = detailController.getIllnessById(aCase.getIllnessId());
            } catch (ExecutionException | InterruptedException e) {
                e.printStackTrace();
            }

            holder.illnessName.setText(
                    illness != null ? illness.getName() : null
            );


            holder.layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (detailController.getListOfSelectedItems().isEmpty()) {
                        Intent intent = new Intent(PersonDetailActivity.this, HistoryIllnessActivity.class);
                        intent.putExtra(KEY_CASE_ID, aCase.getId());
                        intent.putExtra(KEY_PATIENT_ID, patient.getId());
                        startActivity(intent);
                    } else {
                        if (detailController.getListOfSelectedItems().contains(holder.getAdapterPosition())) {
                            detailController.removeItemFromSelected(holder.getAdapterPosition());
                            notifyDataSetChanged();
                            invalidateOptionsMenu();
                        } else {
                            detailController.selectItem(holder.getAdapterPosition());
                            notifyDataSetChanged();
                        }
                    }
                }
            });

            holder.layout.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    detailController.selectItem(holder.getAdapterPosition());
                    notifyDataSetChanged();
                    invalidateOptionsMenu();
                    return true;
                }
            });

        }

        @Override
        public int getItemCount() {
            return listOfCases.size();
        }




        class CustomPersonViewHolder extends RecyclerView.ViewHolder {

            private TextView startDate;
            private TextView endDate;
            private TextView illnessName;
            private ConstraintLayout layout;

            public CustomPersonViewHolder(View itemView) {
                super(itemView);

                endDate = itemView.findViewById(R.id.textViewEndDate);
                startDate = itemView.findViewById(R.id.textViewStartDate);
                illnessName = itemView.findViewById(R.id.textViewIllnessName);
                layout = itemView.findViewById(R.id.rootLayout);
            }
        }
    }
}
