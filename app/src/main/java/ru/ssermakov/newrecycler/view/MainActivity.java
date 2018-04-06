package ru.ssermakov.newrecycler.view;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import ru.ssermakov.newrecycler.R;
import ru.ssermakov.newrecycler.data.DBHelper;
import ru.ssermakov.newrecycler.data.DataSource;
import ru.ssermakov.newrecycler.data.Person;
import ru.ssermakov.newrecycler.logic.MainController;
import ru.ssermakov.newrecycler.view.Interfaces.MainActivityViewInterface;

public class MainActivity extends AppCompatActivity implements MainActivityViewInterface {

    private static final String EXTRA_NAME = "EXTRA_NAME";
    private static final String EXTRA_BACKGROUNDCOLOR = "EXTRA_BACKGROUNDCOLOR";
    private static final String EXTRA_IMAGE = "EXTRA_IMAGE";
    private static final int REQUEST_ID = 100;
    private static final int MY_PERMISSIONS_REQUEST_READ_MEDIA = 23;
    public static final String EXTRA_ID = "ID";

    private List<Person> listOfData;

    private LayoutInflater layoutInflater;
    private RecyclerView recyclerView;
    private CustomAdapter adapter;

    private MainController mainController;


    private Bitmap selectedImage = null;
    int currentId;
    private int currentPosition;
    private Uri imageUri;
    private Person currentPerson;
    private ImageView currentImageView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.f_recycler);
        layoutInflater = getLayoutInflater();

        mainController = new MainController(
                this,
                new DataSource(
                        new DBHelper(this)
                )
        );

        Intent i = getIntent();
        int id = i.getIntExtra(AddPersonActivity.PERSON_ID, 0);
        String date = i.getStringExtra("date");
        String hint = i.getStringExtra("hint");
        recyclerView.smoothScrollToPosition(id);
        showToast("Create");

        // requesting permissions
        int permissionCheck = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE);

        if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, MY_PERMISSIONS_REQUEST_READ_MEDIA);
        } else {
            Toast.makeText(this, "You have permissions", Toast.LENGTH_SHORT).show();

        }
    }


    @Override
    public void startPersonDetailActivity(String name, String image) {
        Intent i = new Intent(this, PersonDetailActivity.class);
        i.putExtra(EXTRA_NAME, name);
        i.putExtra(EXTRA_IMAGE, image.toString());

        startActivity(i);
    }

    @Override
    public void setUpAdapterAndView(List<Person> listOfData) {
        this.listOfData = listOfData;
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
                            listOfData.get(position).getId());
                } else {
                    mainController.onPersonSwipedToDelete(
                            position,
                            listOfData.get(position).getId());
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
        listOfData.remove(position);

        adapter.notifyItemRemoved(position);

    }

    @Override
    public void toggleState(int position, int id) {
        if (listOfData.get(position).getState().equals("не болеет")) {
            startBeginIllnessActivity(id);
            listOfData.get(position).setState("болеет");
        } else {
            listOfData.get(position).setState("не болеет");
        }
        adapter.notifyItemChanged(position);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.first_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.fm_add_person) {
//            Open new activity for create new Person
            mainController.onAddPersonClick();
            showToast("Add Person");
        }
        if (item.getItemId() == R.id.fm_search_person) {
            showToast("Search");
        }
        return super.onOptionsItemSelected(item);
    }

    private void startBeginIllnessActivity(int id) {
        Intent i = new Intent(this, BeginIllnessActivity.class);
        i.putExtra(EXTRA_ID, id);
        startActivity(i);
    }


    void showToast(String s) {
        Toast.makeText(this, s, Toast.LENGTH_LONG).show();
    }

    private class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.CustomViewHolder> {

        @Override
        public CustomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View v = layoutInflater.inflate(R.layout.f_recycler_layout, parent, false);
            return new CustomViewHolder(v);
        }

        @Override
        public void onBindViewHolder(final CustomViewHolder holder, final int position) {
            Person person = listOfData.get(position);

            holder.name.setText(
                    person.getName()
            );





            if (person.getState().equals("не болеет")) {
                holder.cardView.setCardBackgroundColor(getResources().getColor(R.color.colorGood));
                holder.illTextView.setText("Не болеет");
                holder.schema.setText(getResources().getText(R.string.bless_you));
            } else {
                holder.cardView.setCardBackgroundColor(getResources().getColor(R.color.colorBad));
                holder.schema.setText(getResources().getText(R.string.no_schema));
            }

            Bitmap bm = BitmapFactory.decodeFile(person.getImage());
            holder.image.setImageBitmap(bm);
//            Uri uri = person.getImage();
//            String[] filePathColumn = {MediaStore.Images.Media.DATA};
//
//            Cursor cursor = getContentResolver().query(
//                    uri,
//                    filePathColumn,
//                    null,
//                    null,
//                    null
//            );
//            if (cursor != null) {
//                cursor.moveToFirst();
//
//                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
//                String filePath = cursor.getString(columnIndex);
//                cursor.close();
//                Bitmap yourSelectedImage = BitmapFactory.decodeFile(filePath);
//                holder.image.setImageBitmap(yourSelectedImage);
//                }



            View.OnClickListener oclConteiner = new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Person person = listOfData.get(position);
                    mainController.onPersonNameClick(person);
                }
            };
            holder.container.setOnClickListener(oclConteiner);

        }

        @Override
        public int getItemCount() {
            return listOfData.size();
        }

        class CustomViewHolder extends RecyclerView.ViewHolder {

            private ImageView image;
            private TextView name;
            private ViewGroup container;
            private CardView cardView;
            private TextView illTextView;
            private TextView schema;

            public CustomViewHolder(View itemView) {
                super(itemView);

                this.image = itemView.findViewById(R.id.img);
                this.name = itemView.findViewById(R.id.textViewName);
                this.container = itemView.findViewById(R.id.root_list_item);
                this.cardView = itemView.findViewById(R.id.card_view);
                this.illTextView = itemView.findViewById(R.id.textViewStatus);
                this.schema = itemView.findViewById(R.id.has_cure);

            }

        }
    }


}
