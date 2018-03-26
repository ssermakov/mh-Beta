package ru.ssermakov.newrecycler.app;

import android.app.Application;
import android.arch.persistence.room.Room;

import ru.ssermakov.newrecycler.data.room.MedicalHistoryDatabase;

/**
 * Created by btb_wild on 26.03.2018.
 */

public class App extends Application {

    public static App instance;

    private MedicalHistoryDatabase db;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        db = Room.databaseBuilder(this, MedicalHistoryDatabase.class, "mh")
                .build();
    }

    public static App getInstance() {
        return instance;
    }

    public MedicalHistoryDatabase getDb() {
        return db;
    }
}
