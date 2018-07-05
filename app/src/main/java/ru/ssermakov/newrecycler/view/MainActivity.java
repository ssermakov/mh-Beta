package ru.ssermakov.newrecycler.view;

import android.Manifest;
import android.app.Activity;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import ru.ssermakov.newrecycler.R;
import ru.ssermakov.newrecycler.Utils.Utils;
import ru.ssermakov.newrecycler.data.room.entity.Patient;
import ru.ssermakov.newrecycler.logic.MainController;
import ru.ssermakov.newrecycler.view.Interfaces.MainActivityViewInterface;

public class MainActivity extends AppCompatActivity implements MainActivityViewInterface {

    private static final int REQUEST_ID = 100;
    private static final int MY_PERMISSIONS_REQUEST_READ_MEDIA = 23;
    public static final String EXTRA_ID = "ID";
    public static final String EXTRA_POSITION = "POSITION";
    private List<Patient> listOfPatientsFiltered;
    private List<Patient> listOfPatients;

    private LayoutInflater layoutInflater;
    private RecyclerView recyclerView;
    private CustomAdapter adapter;

    private MainController mainController;
    private Long id;
    private SearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        recyclerView = findViewById(R.id.f_recycler);
        layoutInflater = getLayoutInflater();

        mainController = new MainController(this);

        Intent i = getIntent();
        id = i.getLongExtra(AddPersonActivity.PERSON_ID, 0);
        recyclerView.smoothScrollToPosition(id.intValue());

        // requesting permissions
        int permissionCheck = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE);

        if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, MY_PERMISSIONS_REQUEST_READ_MEDIA);
        } else {
//            Toast.makeText(this, "You have permissions", Toast.LENGTH_SHORT).show();
        }
    }

    public static int getActionBarHeight(Activity activity) {
        TypedValue typedValue = new TypedValue();

        int attributeResourceId = android.R.attr.actionBarSize;
        if (activity instanceof AppCompatActivity) {
            attributeResourceId = R.attr.actionBarSize;
        }

        if (activity.getTheme().resolveAttribute(attributeResourceId, typedValue, true)) {
            return TypedValue.complexToDimensionPixelSize(typedValue.data, activity.getResources().getDisplayMetrics());
        }

        return 0;
    }

    @Override
    public void startPersonDetailActivity(int id) {
        Intent i = new Intent(this, PersonDetailActivity.class);
        i.putExtra(EXTRA_ID, id);
        startActivity(i);
    }

    @Override
    public void setUpAdapterAndView(List<Patient> listOfData) {
        this.listOfPatientsFiltered = listOfData;
        this.listOfPatients = listOfData;
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new CustomAdapter();
        recyclerView.setAdapter(adapter);

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(createHelperCallback());
        itemTouchHelper.attachToRecyclerView(recyclerView);
    }

    private ItemTouchHelper.Callback createHelperCallback() {
        ItemTouchHelper.SimpleCallback simpleItemTouchCallback = new ItemTouchHelper.SimpleCallback(
                0,
                ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT
        ) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }


            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                int position = viewHolder.getAdapterPosition();
                if (direction == 4) {

                    mainController.onPersonSwipedToChangeState(
                            position,
                            listOfPatientsFiltered.get(position),
                            getBaseContext());

                } else {
                    mainController.onPersonSwipedToDelete(
                            position,
                            listOfPatientsFiltered.get(position));

                }
            }

            @Override
            public void onChildDraw(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
                if (actionState == ItemTouchHelper.ACTION_STATE_SWIPE) {
                    final float alpha = 1.0f - Math.abs(dX) / (float) viewHolder.itemView.getWidth();
                    viewHolder.itemView.setAlpha(alpha);
                    viewHolder.itemView.setTranslationX(dX);
                } else {
                    super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
                }
            }
        };
        return simpleItemTouchCallback;
    }


    @Override
    public void startAddPersonActivity() {
        Intent i = new Intent(this, AddPersonActivity.class);
        startActivityForResult(i, REQUEST_ID);
    }

    @Override
    public void deletePersonAt(int position) {
        listOfPatientsFiltered.remove(position);
        adapter.notifyItemRemoved(position);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.first_menu, menu);

        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        searchView = (SearchView) menu.findItem(R.id.fm_search_person).getActionView();
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                adapter.getFilter().filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.getFilter().filter(newText);
                return false;
            }
        });

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.fm_add_person) {
            mainController.onAddPersonClick();
        }
        if (item.getItemId() == R.id.fm_search_person) {


        }
        return super.onOptionsItemSelected(item);
    }

    private class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.CustomViewHolder>
            implements Filterable {


        @Override
        public CustomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            if (viewType == 1) {
                View v = layoutInflater.inflate(R.layout.f_recycler_one_element_layout, parent, false);
                return new CustomViewHolder(v);
            }
            View v = layoutInflater.inflate(R.layout.f_recycler_layout, parent, false);
            return new CustomViewHolder(v);
        }

        @Override
        public void onBindViewHolder(final CustomViewHolder holder, final int position) {
            Patient patient = listOfPatientsFiltered.get(position);

            holder.name.setText(
                    patient.getName()
            );

            try {
                holder.textViewAge.setText(
                        mainController.formatAge(
                                mainController.getAge(patient)
                        )
                );
            } catch (ExecutionException | InterruptedException e) {
                e.printStackTrace();
            }

            if (patient.getState().equals(getResources().getString(R.string.good_state))) {
                holder.cardView.setCardBackgroundColor(getResources().getColor(R.color.colorGood));
                holder.illTextView.setText(getResources().getString(R.string.good_state));
                holder.schema.setText(getResources().getText(R.string.bless_you));
            } else {
                holder.cardView.setCardBackgroundColor(getResources().getColor(R.color.colorBad));

                holder.schema.setText(mainController.setSchemaString(patient, MainActivity.this));

                try {
                    holder.illTextView.setText(mainController.createResultString(
                            mainController.getDurationOfIllness(patient)
                    ));
                } catch (ExecutionException | InterruptedException e) {
                    e.printStackTrace();
                }
            }

            Bitmap bm = BitmapFactory.decodeFile(patient.getImageUrl());
            holder.image.setImageBitmap(bm);

            View.OnClickListener onClickListenerContainer = new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Patient patient = listOfPatientsFiltered.get(holder.getAdapterPosition());
                    mainController.onPersonNameClick(patient);
                }
            };
            holder.constraintLayout.setOnClickListener(onClickListenerContainer);

        }

        @Override
        public int getItemViewType(int position) {
            if (listOfPatientsFiltered.size() == 1) {
                return 1;
            }
            return 0;
        }


        @Override
        public int getItemCount() {
            return listOfPatientsFiltered.size();
        }

        @Override
        public Filter getFilter() {
            return new Filter() {
                @Override
                protected FilterResults performFiltering(CharSequence constraint) {
                    String charString = constraint.toString();
                    if (charString.isEmpty()) {
                        listOfPatientsFiltered = listOfPatients;
                    } else {
                        List<Patient> filteredList = new ArrayList<>();
                        for (Patient patient : listOfPatients) {
                            if (patient.getName().toLowerCase().contains(charString.toLowerCase())) {
                                filteredList.add(patient);
                            }
                        }
                        listOfPatientsFiltered = filteredList;
                    }
                    FilterResults results = new FilterResults();
                    results.values = listOfPatientsFiltered;
                    return results;
                }

                @Override
                protected void publishResults(CharSequence constraint, FilterResults results) {
                    listOfPatientsFiltered = (List<Patient>) results.values;
                    notifyDataSetChanged();
                }
            };
        }

        class CustomViewHolder extends RecyclerView.ViewHolder {


            private ImageView image;
            private TextView name;
            private ConstraintLayout constraintLayout;
            private CardView cardView;
            private TextView illTextView;
            private TextView schema;
            private TextView textViewAge;

            public CustomViewHolder(View itemView) {
                super(itemView);


                constraintLayout = itemView.findViewById(R.id.root_list_item);
                int screenHeight = Utils.getScreenHeight(itemView.getContext());
                int actionBarHeight = getActionBarHeight(MainActivity.this);
                DisplayMetrics metrics = new DisplayMetrics();
                getWindowManager().getDefaultDisplay().getMetrics(metrics);
                int valueToSubtract = Utils.getValueToSubtract(metrics.densityDpi);
                if (getItemCount() > 1) {
                    constraintLayout.setMinHeight(
                            ((screenHeight - actionBarHeight - valueToSubtract) / 2)
                    );
                } else {
                    constraintLayout.setMinHeight(
                            ((screenHeight - actionBarHeight - valueToSubtract))
                    );
                }


                this.image = itemView.findViewById(R.id.img);
                this.name = itemView.findViewById(R.id.textViewName);
                this.cardView = itemView.findViewById(R.id.card_view);
                this.illTextView = itemView.findViewById(R.id.textViewStatus);
                this.schema = itemView.findViewById(R.id.has_cure);
                this.textViewAge = itemView.findViewById(R.id.textViewAge);

            }

        }

    }

}
