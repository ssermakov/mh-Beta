package ru.ssermakov.newrecycler.data;

import android.net.Uri;

import java.util.List;

import ru.ssermakov.newrecycler.data.room.Patient;

/**
 * Created by btb_wild on 19.02.2018.
 */

public interface DataSourceInterface {

//    List<Person> getListOfData();
//    List<Patient> getListOfData();


    void addPersonToDb(String personName, String filePath, String state);

//    int getLastPersonId();

    void deletePerson(int id);

    void toggleState(int id);
}
