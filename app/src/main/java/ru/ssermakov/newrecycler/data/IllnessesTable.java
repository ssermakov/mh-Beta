package ru.ssermakov.newrecycler.data;

import android.database.sqlite.SQLiteDatabase;

/**
 * Created by btb_wild on 03.03.2018.
 */


class IllnessesTable {

    public static final String TABLE_ILLNESSES = "illnesses";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_TITLE= "title";

    public static final String ILLNESSES_CREATE = "CREATE TABLE IF NOT EXISTS illnesses (_id integer primary key autoincrement, title)";

    public static void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(ILLNESSES_CREATE);
        populate(sqLiteDatabase);
    }

    private static void populate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("   INSERT INTO illnesses (title) VALUES ('грип')   "   );
        sqLiteDatabase.execSQL("   INSERT INTO illnesses (title) VALUES ('ангина')   "   );
        sqLiteDatabase.execSQL("   INSERT INTO illnesses (title) VALUES ('отит')   "   );
    }
}
