package ru.ssermakov.newrecycler.data.room;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

/**
 * Created by btb_wild on 26.03.2018.
 */

@Database(entities = {Patient.class}, version = 1)
public abstract class MedicalHistoryDatabase extends RoomDatabase {
    public abstract PatientDao patientDao();
}
