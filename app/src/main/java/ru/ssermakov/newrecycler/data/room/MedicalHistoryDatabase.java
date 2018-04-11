package ru.ssermakov.newrecycler.data.room;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;

import ru.ssermakov.newrecycler.data.room.converter.DateConverter;
import ru.ssermakov.newrecycler.data.room.dao.CaseDao;
import ru.ssermakov.newrecycler.data.room.dao.IllnessDao;
import ru.ssermakov.newrecycler.data.room.dao.PatientDao;
import ru.ssermakov.newrecycler.data.room.dao.PlanDao;
import ru.ssermakov.newrecycler.data.room.dao.SymptomDao;
import ru.ssermakov.newrecycler.data.room.entity.Case;
import ru.ssermakov.newrecycler.data.room.entity.Illness;
import ru.ssermakov.newrecycler.data.room.entity.Patient;
import ru.ssermakov.newrecycler.data.room.entity.Plan;
import ru.ssermakov.newrecycler.data.room.entity.Symptom;

/**
 * Created by btb_wild on 26.03.2018.
 */

@Database(entities = {Patient.class,
        Illness.class,
        Case.class,
        Plan.class,
        Symptom.class}, version = 1)
@TypeConverters(DateConverter.class)
public abstract class MedicalHistoryDatabase extends RoomDatabase {
    public abstract PatientDao patientDao();
    public abstract CaseDao caseDao();
    public abstract IllnessDao illnessDao();
    public abstract PlanDao planDao();
    public abstract SymptomDao symptomDao();
}
