package ru.ssermakov.newrecycler.view.Interfaces;

import android.net.Uri;

import java.util.List;

import ru.ssermakov.newrecycler.data.Person;
import ru.ssermakov.newrecycler.data.room.Patient;

/**
 * Created by btb_wild on 19.02.2018.
 */

public interface MainActivityViewInterface {

    void startPersonDetailActivity(String name, String image);

//    void setUpAdapterAndView (List<Person> listOfData);
    void setUpAdapterAndView (List<Patient> listOfData);

    void startAddPersonActivity();

    void deletePersonAt(int position);

    void toggleState(int position, int id);

//    void takePhotoFromGallery (Person person, int position);
}

