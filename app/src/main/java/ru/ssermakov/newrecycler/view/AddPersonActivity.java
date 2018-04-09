package ru.ssermakov.newrecycler.view;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.OpenableColumns;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.ExecutionException;

import ru.ssermakov.newrecycler.R;
import ru.ssermakov.newrecycler.data.DBHelper;
import ru.ssermakov.newrecycler.data.DataSource;
import ru.ssermakov.newrecycler.data.room.Patient;
import ru.ssermakov.newrecycler.logic.AddPersonController;
import ru.ssermakov.newrecycler.view.Interfaces.PersonActivityInterface;

public class AddPersonActivity extends AppCompatActivity implements PersonActivityInterface, View.OnClickListener {


    private static final int GALLERY_REQUEST = 1;
    private EditText editTextName;
    private ImageView imageViewPhoto;
    private AddPersonController addPersonController;
    private AddPersonController addPatientController;
    private int personId;
    FloatingActionButton floatingActionButton;
    private String filePath;
    public final static String PERSON_ID = "PERSON_ID";
    private static TextView age;
    Long id;

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

        addPatientController = new AddPersonController();
    }


    @Override
    public void onClick(View view) {
        int viewId = view.getId();
        if (viewId == R.id.fabCreateNewPerson) {
            String personName = editTextName.getText().toString().trim();
            String personState = getResources().getString(R.string.good_state);
            Date dateOfBirth = convertStringToDate(age.getText().toString());

//            new db
            Patient patient = new Patient(personName, dateOfBirth, personState, filePath);
            try {
                this.id = addPatientController.addPatient(patient);
            } catch (ExecutionException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }


            Intent i = new Intent(this, MainActivity.class);
            i.putExtra(PERSON_ID, id);
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
                String fileName = getFileName(selectedImage);
                String filePath = getRealPathFromURI(this, selectedImage);
                File fileSource = new File(filePath);

                File fileDst = new File(this.getFilesDir(), fileName);
                copyFile (fileSource, fileDst);
                Bitmap bm = BitmapFactory.decodeFile(fileDst.getPath());
                imageViewPhoto.setImageBitmap(bm);
                this.filePath = fileDst.getPath();

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
    private String getFileName(Uri selectedImage) {
        String fileName = null;
        if (selectedImage.getScheme().equals("content")) {
            Cursor cursor = getContentResolver().query(
                    selectedImage,
                    null,
                    null,
                    null,
                    null
            );
            if (cursor != null && cursor.moveToFirst()) {
                try {
                    fileName = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));
                } finally {
                    cursor.close();
                }
            }
            if (fileName == null) {

                fileName = selectedImage.getPath();
                int cut = fileName.lastIndexOf("/");
                if (cut != -1) {
                    fileName = fileName.substring(cut + 1);
                }
            }
        }
        return fileName;
    }
    private void copyFile(File fileSource, File fileDst) {
        try {
            InputStream is = new FileInputStream(fileSource);
            OutputStream os = new FileOutputStream(fileDst);
            byte[] buf = new byte[1024];
            int len;
            while ((len = is.read(buf)) > 0) {
                os.write(buf, 0, len);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public String getRealPathFromURI(Context context, Uri contentUri) {
        Cursor cursor = null;
        try {
            String[] proj = { MediaStore.Images.Media.DATA };
            cursor = context.getContentResolver().query(contentUri,  proj, null, null, null);
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            return cursor.getString(column_index);
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
    }
    private Date convertStringToDate(String string) {
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        Date date = null;
        try {
            date = dateFormat.parse(string);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }
}
