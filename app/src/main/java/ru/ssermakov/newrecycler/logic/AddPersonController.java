package ru.ssermakov.newrecycler.logic;

import android.os.AsyncTask;

import ru.ssermakov.newrecycler.app.App;
import ru.ssermakov.newrecycler.data.DataSourceInterface;
import ru.ssermakov.newrecycler.data.room.MedicalHistoryDatabase;
import ru.ssermakov.newrecycler.data.room.Patient;
import ru.ssermakov.newrecycler.data.room.PatientDao;
import ru.ssermakov.newrecycler.data.room.repository.PatientRepository;
import ru.ssermakov.newrecycler.view.Interfaces.PersonActivityInterface;

/**
 * Created by btb_wild on 22.02.2018.
 */

public class AddPersonController {
    DataSourceInterface dataSource;
    PersonActivityInterface addPersonView;

    PatientDao patientDao;
    private Long insertedPatientId;

    public AddPersonController() {
        MedicalHistoryDatabase db = App.getInstance().getDb();
        this.patientDao = db.patientDao();
    }

    public AddPersonController(DataSourceInterface dataSource, PersonActivityInterface addPersonView) {
        this.dataSource = dataSource;
        this.addPersonView = addPersonView;
    }

    public void addPerson(String personName, String filePath, String state) {
        dataSource.addPersonToDb(personName, filePath, state);
        int lastPersonId = dataSource.getLastPersonId();
        addPersonView.setPersonId(lastPersonId);
    }

    public void addPatient(Patient patient) {
        new AddPatientTask().execute(patient);
    }

    public Long getInsertedPatientId() {
        return insertedPatientId;
    }

    private class AddPatientTask extends AsyncTask<Patient, Void, Void> {

        @Override
        protected Void doInBackground(Patient... patients) {
            Long lastPatientId = patientDao.insert(patients[0]);
            insertedPatientId = lastPatientId;
            return null;
        }
    }
}
