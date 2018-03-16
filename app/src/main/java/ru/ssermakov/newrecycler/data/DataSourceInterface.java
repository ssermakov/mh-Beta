package ru.ssermakov.newrecycler.data;

import android.net.Uri;

import java.util.List;

/**
 * Created by btb_wild on 19.02.2018.
 */

public interface DataSourceInterface {

    List<Person> getListOfData();


    void addPersonToDb(String personName, Uri imageUri, String state);

    int getLastPersonId();

    void deletePerson(int id);

    void toggleState(int id);
}
