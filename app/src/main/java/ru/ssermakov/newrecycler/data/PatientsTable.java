package ru.ssermakov.newrecycler.data;

/**
 * Created by btb_wild on 19.02.2018.
 */

import android.database.sqlite.SQLiteDatabase;

class PatientsTable {


    public static final String TABLE_PATIENTS = "patients";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_CURRENT_STATE = "current_state";
    public static final String COLUMN_IMAGE_URI = "image_url";

    public static final String PATIENTS_CREATE = "CREATE TABLE IF NOT EXISTS patients (_id integer primary key autoincrement, name, date_of_birth, image_url, current_state)";


    public static void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(PATIENTS_CREATE);
        populate(sqLiteDatabase);
    }

    private static void populate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("   INSERT INTO patients (name, date_of_birth, image_url, current_state) VALUES ('Кротя', '2018/05/19', 'no image', 'болеет')   "   );
    }


    public static void onUpgrade(SQLiteDatabase sqLiteDatabase) {
    }
}
