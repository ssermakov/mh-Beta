package ru.ssermakov.newrecycler.logic;

import android.os.AsyncTask;

import java.util.List;
import java.util.concurrent.ExecutionException;

import ru.ssermakov.newrecycler.app.App;
import ru.ssermakov.newrecycler.data.DataSourceInterface;
import ru.ssermakov.newrecycler.data.room.MedicalHistoryDatabase;
import ru.ssermakov.newrecycler.data.room.Patient;
import ru.ssermakov.newrecycler.data.room.PatientDao;
import ru.ssermakov.newrecycler.view.Interfaces.MainActivityViewInterface;


/**
 * Created by btb_wild on 19.02.2018.
 */

public class MainController {

    private MainActivityViewInterface mainActivityView;
    private DataSourceInterface dataSource;

    private PatientDao patientDao;

    public MainController(MainActivityViewInterface view/*, DataSourceInterface dataSource*/) {
        this.mainActivityView = view;
//        this.dataSource = dataSource;
        MedicalHistoryDatabase db = App.getInstance().getDb();
        this.patientDao = db.patientDao();

        getListFromDataSource();
    }


    private void getListFromDataSource() {
//        mainActivityView.setUpAdapterAndView(dataSource.getListOfData());
        GetListFromDataSourceTask task = new GetListFromDataSourceTask();
        task.execute();
        try {
           List<Patient> patients =task.get();
           mainActivityView.setUpAdapterAndView(task.get());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

    }

    //    public void onPersonNameClick(Person person) {
//        mainActivityView.startPersonDetailActivity(
//                person.getName(),
//                person.getImage()
//        );
//    }
    public void onPersonNameClick(Patient patient) {
        mainActivityView.startPersonDetailActivity(
                patient.getName(),
                patient.getImage()
        );
    }

    public void onAddPersonClick() {
        mainActivityView.startAddPersonActivity();
    }

    public void onPersonSwipedToChangeState(int position, Patient patient) throws ExecutionException, InterruptedException {
        patient = toggleState(patient);
        TogglePatientStateTask task = new TogglePatientStateTask();
        task.execute(patient);
        if (task.get()) {
            mainActivityView.toggleState(position, patient.getId());
        }
    }

    public void onPersonSwipedToDelete(int position, Patient patient) {
        new DeletePatientTask().execute(patient);
        mainActivityView.deletePersonAt(position);
    }

    private Patient toggleState(Patient patient) {
        if (patient.getState().equalsIgnoreCase("не болеет")) {
            patient.setState("болеет");
        } else patient.setState("не болеет");
        return patient;
    }

    private class GetListFromDataSourceTask extends AsyncTask<Void, Void, List<Patient>> {

        @Override
        protected List<Patient> doInBackground(Void... voids) {
            List<Patient> patientList = patientDao.getAll();
            return patientList;
        }

        @Override
        protected void onPostExecute(List<Patient> patients) {
            super.onPostExecute(patients);
        }
    }
    private class DeletePatientTask extends AsyncTask<Patient, Void, Void> {

        @Override
        protected Void doInBackground(Patient... patients) {
            patientDao.delete(patients[0]);
            return null;
        }
    }
    private class TogglePatientStateTask extends AsyncTask<Patient, Void, Boolean> {

        @Override
        protected Boolean doInBackground(Patient... patients) {
            patientDao.update(patients[0]);
            return true;
        }
    }
}
