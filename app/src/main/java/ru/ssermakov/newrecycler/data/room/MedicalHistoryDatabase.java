package ru.ssermakov.newrecycler.data.room;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;

import ru.ssermakov.newrecycler.data.room.converter.DateConverter;
import ru.ssermakov.newrecycler.data.room.dao.PatientDao;
import ru.ssermakov.newrecycler.data.room.entity.Illness;
import ru.ssermakov.newrecycler.data.room.entity.Patient;

/**
 * Created by btb_wild on 26.03.2018.
 */

@Database(entities = {Patient.class, Illness.class}, version = 1)
@TypeConverters(DateConverter.class)
public abstract class MedicalHistoryDatabase extends RoomDatabase {
    public abstract PatientDao patientDao();
}
