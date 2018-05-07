package ru.ssermakov.newrecycler.view;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;
import java.util.concurrent.ExecutionException;

import ru.ssermakov.newrecycler.R;
import ru.ssermakov.newrecycler.data.room.entity.Case;
import ru.ssermakov.newrecycler.data.room.entity.Illness;
import ru.ssermakov.newrecycler.data.room.entity.Patient;
import ru.ssermakov.newrecycler.logic.DetailController;
import ru.ssermakov.newrecycler.view.Interfaces.DetailActivityInterface;

public class PersonDetailActivity extends AppCompatActivity implements DetailActivityInterface {

    private TextView name;
    private TextView textViewHistory;
    private ImageView imageViewDetailPhoto;
    private RecyclerView recyclerView;
    private List<Case> listOfCases;
    private LayoutInflater layoutInflater;
    private CustomPersonAdapter adapter;
    private DetailController detailController;
    public static int id;
    public static Patient patient;
    public static final String KEY_CASE_ID = "CASE_ID";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person_detail);
        Intent i = getIntent();
        id = i.getIntExtra(MainActivity.EXTRA_ID, -1);

        textViewHistory = findViewById(R.id.textViewHistory);

        recyclerView = findViewById(R.id.illnessRecycler);
        layoutInflater = getLayoutInflater();


        detailController = new DetailController(this);

        try {
            patient = detailController.getPatientById((long) id);
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        name = findViewById(R.id.name);
        name.setText(patient.getName());

        imageViewDetailPhoto = findViewById(R.id.imageViewDetailPhoto);
        Bitmap bm = BitmapFactory.decodeFile(patient.getImageUrl());
        imageViewDetailPhoto.setImageBitmap(bm);
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

    private class CustomPersonAdapter extends
            RecyclerView.Adapter<CustomPersonAdapter.CustomPersonViewHolder> {

        @Override
        public CustomPersonAdapter.CustomPersonViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View v = layoutInflater.inflate(R.layout.person_detail_recycler_layout, parent, false);
            return new CustomPersonViewHolder(v);
        }

        @Override
        public void onBindViewHolder(CustomPersonAdapter.CustomPersonViewHolder holder, int position) {
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
                illness = detailController.getIllnessNameById(aCase.getIllnessId());
            } catch (ExecutionException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            holder.illnessName.setText(
                    illness.getName()
            );

            holder.layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Long id =aCase.getId();

                    Intent intent = new Intent(PersonDetailActivity.this, HistoryIllnessActivity.class);
                    intent.putExtra(KEY_CASE_ID, id);
                    startActivity(intent);
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
