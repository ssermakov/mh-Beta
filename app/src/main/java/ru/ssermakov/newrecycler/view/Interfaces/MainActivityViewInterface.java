package ru.ssermakov.newrecycler.view.Interfaces;

import android.net.Uri;
import android.widget.ImageView;

import java.util.List;

import ru.ssermakov.newrecycler.data.PersonItem;

/**
 * Created by btb_wild on 19.02.2018.
 */

public interface MainActivityViewInterface {

    void startPersonDetailActivity(String name, int backgroundColor, Uri image);

    void setUpAdapterAndView (List<PersonItem> listOfData);

    void startAddPersonActivity();

    void deletePersonAt(int position);

    void toggleState(int position, int id);

//    void takePhotoFromGallery (PersonItem person, int position);
}

