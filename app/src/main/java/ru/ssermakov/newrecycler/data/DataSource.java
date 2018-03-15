package ru.ssermakov.newrecycler.data;

import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;

import java.util.ArrayList;
import java.util.List;

import ru.ssermakov.newrecycler.R;

/**
 * Created by btb_wild on 19.02.2018.
 */

public class DataSource implements DataSourceInterface {

    private DBHelper dbHelper;

    public DataSource(DBHelper dbHelper) {
        this.dbHelper = dbHelper;

    }


    @Override
    public List<PersonItem> getListOfData() {
        ArrayList<PersonItem> listOfData = new ArrayList<>();
        PersonItem person;
        Cursor cursor;
        cursor = dbHelper.getReadableDatabase().query(
                PatientsTable.TABLE_PATIENTS,
                null,
                null,
                null,
                null,
                null,
                null
        );

        while (cursor.moveToNext()) {
            person = new PersonItem(
                    cursor.getInt(cursor.getColumnIndex(PatientsTable.COLUMN_ID)),
                    cursor.getString(cursor.getColumnIndex(PatientsTable.COLUMN_NAME)),
                    R.color.colorGood,
                    Uri.parse(cursor.getString(cursor.getColumnIndex(PatientsTable.COLUMN_IMAGE_URI))),
                    cursor.getString(cursor.getColumnIndex(PatientsTable.COLUMN_CURRENT_STATE))
            );
            listOfData.add(person);

        }
        cursor.close();
        return listOfData;
    }

    @Override
    public void addPersonToDb(String personName, Uri imageUri, String state) {
        ContentValues cv = new ContentValues();

        cv.put(PatientsTable.COLUMN_NAME, personName);
        cv.put(PatientsTable.COLUMN_IMAGE_URI, imageUri.toString());
        cv.put(PatientsTable.COLUMN_CURRENT_STATE, state);

        dbHelper.getWritableDatabase().insert(PatientsTable.TABLE_PATIENTS, null, cv);




    }

    @Override
    public int getLastPersonId() {
        List<PersonItem> data = getListOfData();
        int size = data.size();
        PersonItem last = data.get(size - 1);
        return last.getId();
    }

    @Override
    public void deletePerson(int id) {

        dbHelper.getWritableDatabase().delete(
                PatientsTable.TABLE_PATIENTS,
                PatientsTable.COLUMN_ID + "=" + id,
                null
                );
    }

    @Override
    public void toggleState(int id) {
        ContentValues cv = new ContentValues();

        if (isSick(id)) {
            cv.put(PatientsTable.COLUMN_CURRENT_STATE, "не болеет");
        } else {
            cv.put(PatientsTable.COLUMN_CURRENT_STATE, "болеет");
        }

        dbHelper.getWritableDatabase().update(PatientsTable.TABLE_PATIENTS,
                cv,
                PatientsTable.COLUMN_ID + "=" + id,
                null);
    }

    private boolean isSick(int id){
        Cursor cursor = dbHelper.getReadableDatabase().query(
                PatientsTable.TABLE_PATIENTS,
                null,
                PatientsTable.COLUMN_ID + "=" + id,
                null,
                null,
                null,
                null
        );
        boolean state = false;
        while (cursor.moveToNext()) {
            if (cursor.getString(cursor.getColumnIndex(PatientsTable.COLUMN_CURRENT_STATE)).equals("болеет")) {
                state = true;
            }
        }
        cursor.close();
        return state;
    }



}
