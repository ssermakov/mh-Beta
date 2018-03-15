package ru.ssermakov.newrecycler.view;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Calendar;

import ru.ssermakov.newrecycler.R;
import ru.ssermakov.newrecycler.data.DBHelper;
import ru.ssermakov.newrecycler.data.DataSource;
import ru.ssermakov.newrecycler.logic.AddPersonController;
import ru.ssermakov.newrecycler.view.Interfaces.PersonActivityInterface;

public class AddPersonActivity extends AppCompatActivity implements PersonActivityInterface, View.OnClickListener {


    private static final int GALLERY_REQUEST = 1;
    private EditText editTextName;
    private ImageView imageViewPhoto;
    private AddPersonController addPersonController;
    private int personId;
    FloatingActionButton floatingActionButton;
    private Uri imageUri;
    public final static String PERSON_ID = "PERSON_ID";
    private static TextView age;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_person);

        editTextName = findViewById(R.id.editTextName);
        imageViewPhoto = findViewById(R.id.imageViewPhoto);
        floatingActionButton = findViewById(R.id.fabCreateNewPerson);
        ViewGroup ageRoot = findViewById(R.id.age_root);
        age = findViewById(R.id.tv_age);

        floatingActionButton.setOnClickListener(this);
        imageViewPhoto.setOnClickListener(this);
        ageRoot.setOnClickListener(this);

        addPersonController = new AddPersonController(
                new DataSource(
                        new DBHelper(this)
                ), this
        );


    }


    @Override
    public void onClick(View view) {
        int viewId = view.getId();
        if (viewId == R.id.fabCreateNewPerson) {
            String personName = editTextName.getText().toString().trim();
            String personState = getResources().getString(R.string.good_state);
            addPersonController.addPerson(personName, imageUri, personState);

            Intent i = new Intent(this, MainActivity.class);
            i.putExtra(PERSON_ID, personId);
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

            startActivity(i);
        }

        if (viewId == R.id.imageViewPhoto) {
            Intent i = new Intent(Intent.ACTION_PICK);
            i.setType("image/*");
            startActivityForResult(i, GALLERY_REQUEST);
        }

        if (viewId == R.id.age_root) {
            showDatePickDialog();
        }

        if (viewId == R.id.tv_age) {
            showDatePickDialog();
        }

    }

    private void showDatePickDialog() {
        DialogFragment fragment = new DatePickerFragment();
        fragment.show(getSupportFragmentManager(), "datePicker");
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == GALLERY_REQUEST) {
            if (resultCode == RESULT_OK) {
                Uri selectedImage = data.getData();
                imageViewPhoto.setImageURI(selectedImage);
                this.imageUri = selectedImage;

//                Uri selectedImage = data.getData();
//                Bitmap bitmap = null;
//                try {
//                    bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), selectedImage);
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//                imageViewPhoto.setImageBitmap(bitmap);
            }
        }
    }

    @Override
    public void setPersonId(int lastPersonId) {
        personId = lastPersonId;
    }

    public static class DatePickerFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {


        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the current date as the default date in the picker
            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);

            // Create a new instance of DatePickerDialog and return it
            return new DatePickerDialog(getActivity(), AlertDialog.THEME_HOLO_LIGHT, this, year, month, day);
        }


        @Override
        public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
            i1 = i1 + 1;
            age.setText(i2 + "/" + i1 + "/" + i);
        }
    }
}
