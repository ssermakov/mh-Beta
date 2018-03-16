package ru.ssermakov.newrecycler.view;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import ru.ssermakov.newrecycler.R;

public class PersonDetailActivity extends AppCompatActivity {

    private static final String EXTRA_NAME = "EXTRA_NAME";
    private static final String EXTRA_BACKGROUNDCOLOR = "EXTRA_BACKGROUNDCOLOR";
    private static final String EXTRA_IMAGE = "EXTRA_IMAGE";

    private TextView name;
    private TextView text;
    private ImageView image;
    private ListView listView;
    private TextView quantity;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person_detail);


        Intent i = getIntent();
        String nameExtra = i.getStringExtra(EXTRA_NAME);
        Uri imageUri = Uri.parse(i.getStringExtra(EXTRA_IMAGE));

        name = findViewById(R.id.name);
        name.setText(nameExtra);

        image = findViewById(R.id.image);
        image.setImageURI(imageUri);

        text = findViewById(R.id.message_body);
        text.setText("Общая продолжительность болезни:");

        listView = findViewById(R.id.list_of_illnesses);


        quantity = findViewById(R.id.quantity);
        quantity.setText("251");


    }
}
