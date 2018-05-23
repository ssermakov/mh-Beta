package ru.ssermakov.newrecycler.logic;

import android.os.AsyncTask;

import java.util.Date;
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
    private Long patientId;
    private PersonActivityInterface addPersonView;
    private PatientDao patientDao;
    private Patient patient;

    public AddPersonController(PersonActivityInterface addPersonView) {
        MedicalHistoryDatabase db = App.getInstance().getDb();
        this.patientDao = db.patientDao();
        this.addPersonView = addPersonView;
    }

    public AddPersonController(PersonActivityInterface addPersonView, int patientId) {
        MedicalHistoryDatabase db = App.getInstance().getDb();
        this.patientDao = db.patientDao();
        this.addPersonView = addPersonView;
        this.patientId = (long) patientId;

        try {
            this.patient = getPatientById(this.patientId);
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    private Patient getPatientById(Long id) throws ExecutionException, InterruptedException {
        GetPatientByIdTask task = new GetPatientByIdTask();
        task.execute(id);
        return task.get();
    }


    public Long addPatient(Patient patient) throws ExecutionException, InterruptedException {
        AddPatientTask task = new AddPatientTask();
        task.execute(patient);
        return task.get();

    }

    public void setImage() {
        addPersonView.setImage(patient);
    }

    public void setName() {
        addPersonView.setName(patient);
    }

    public void setDate() {
        addPersonView.setDate(patient);
    }

    public void updatePatient(String patientName, Date dateOfBirth, String filePath) {
        UpdatePatientTask task = new UpdatePatientTask();
        patient.setName(patientName);
        patient.setDateOfBirth(dateOfBirth);
        if (filePath != null) {
            patient.setImageUrl(filePath);
        }
        task.execute(patient);
    }

    private class AddPatientTask extends AsyncTask<Patient, Void, Long> {

        @Override
        protected Long doInBackground(Patient... patients) {
            return patientDao.insert(patients[0]);
        }
    }

    private class GetPatientByIdTask extends AsyncTask<Long, Void, Patient> {

        @Override
        protected Patient doInBackground(Long... longs) {
            return patientDao.getById(longs[0]);
        }
    }

    private class UpdatePatientTask extends AsyncTask<Patient, Void, Void> {
        @Override
        protected Void doInBackground(Patient... patients) {
            patientDao.update(patients[0]);
            return null;
        }
    }
}
