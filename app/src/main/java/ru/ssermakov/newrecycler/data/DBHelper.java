package ru.ssermakov.newrecycler.data;

/**
 * Created by btb_wild on 19.02.2018.
 *///

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper{

    public static final String DB_NAME = "medicalhistory.db";
    public static final int DB_VERSION = 1;
    private Context context;

    public DBHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
        this.context = context;
//        context.deleteDatabase(DB_NAME);
    }


    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        PatientsTable.onCreate(sqLiteDatabase);
        IllnessesTable.onCreate(sqLiteDatabase);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        PatientsTable.onUpgrade(sqLiteDatabase);
    }
}
