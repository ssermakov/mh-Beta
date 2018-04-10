package ru.ssermakov.newrecycler.logic;

import android.os.AsyncTask;

import java.util.concurrent.ExecutionException;

import ru.ssermakov.newrecycler.app.App;
import ru.ssermakov.newrecycler.data.DataSourceInterface;
import ru.ssermakov.newrecycler.data.room.MedicalHistoryDatabase;
import ru.ssermakov.newrecycler.data.room.entity.Patient;
import ru.ssermakov.newrecycler.data.room.dao.PatientDao;
import ru.ssermakov.newrecycler.view.Interfaces.PersonActivityInterface;

/**
 * Created by btb_wild on 22.02.2018.
 */

public class AddPersonController {
    DataSourceInterface dataSource;
    PersonActivityInterface addPersonView;

    PatientDao patientDao;

    public AddPersonController() {
        MedicalHistoryDatabase db = App.getInstance().getDb();
        this.patientDao = db.patientDao();
    }

    public AddPersonController(DataSourceInterface dataSource, PersonActivityInterface addPersonView) {
        this.dataSource = dataSource;
        this.addPersonView = addPersonView;
    }

    public Long addPatient(Patient patient) throws ExecutionException, InterruptedException {
        AddPatientTask task = new AddPatientTask();
        task.execute(patient);
        return task.get();

    }

    private class AddPatientTask extends AsyncTask<Patient, Void, Long> {

        @Override
        protected Long doInBackground(Patient... patients) {
            Long lastPatientId = patientDao.insert(patients[0]);
            return lastPatientId;
        }
    }

}
