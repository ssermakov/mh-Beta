package ru.ssermakov.newrecycler.logic;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutionException;

import ru.ssermakov.newrecycler.R;
import ru.ssermakov.newrecycler.app.App;
import ru.ssermakov.newrecycler.data.DataSourceInterface;
import ru.ssermakov.newrecycler.data.room.MedicalHistoryDatabase;
import ru.ssermakov.newrecycler.data.room.dao.CaseDao;
import ru.ssermakov.newrecycler.data.room.dao.PatientDao;
import ru.ssermakov.newrecycler.data.room.entity.Patient;
import ru.ssermakov.newrecycler.data.room.entity.Symptom;
import ru.ssermakov.newrecycler.view.BeginIllnessActivity;
import ru.ssermakov.newrecycler.view.EndIllnessActivity;
import ru.ssermakov.newrecycler.view.Interfaces.MainActivityViewInterface;


/**
 * Created by btb_wild on 19.02.2018.
 */

public class MainController extends AppCompatActivity {

    private MainActivityViewInterface mainActivityView;

    private PatientDao patientDao;
    private CaseDao caseDao;
    private Long startDate;

    public MainController(MainActivityViewInterface view) {
        this.mainActivityView = view;
        MedicalHistoryDatabase db = App.getInstance().getDb();
        this.patientDao = db.patientDao();
        this.caseDao = db.caseDao();

        getListFromDataSource();
    }


    private void getListFromDataSource() {
        GetListFromDataSourceTask task = new GetListFromDataSourceTask();
        task.execute();
        try {
            mainActivityView.setUpAdapterAndView(task.get());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

    }


    public void onPersonNameClick(Patient patient) {
        mainActivityView.startPersonDetailActivity(
                patient.getName(),
                patient.getImage()
        );
    }

    public void onAddPersonClick() {
        mainActivityView.startAddPersonActivity();
    }

    public void onPersonSwipedToChangeState(int position, Patient patient, Context baseContext) {
        if (patient.getState().equals("не болеет")) {
            startBeginIllnessActivity(patient.getId(), position, baseContext);
        } else {
            startEndIllnessActivity(patient.getId(), position, baseContext);
        }
    }

    public static final String EXTRA_ID = "ID";
    public static final String EXTRA_POSITION = "POSITION";

    private void startBeginIllnessActivity(int id, int position, Context context) {
        Intent i = new Intent(context, BeginIllnessActivity.class);
        i.putExtra(EXTRA_POSITION, position);
        i.putExtra(EXTRA_ID, id);
        context.startActivity(i);
    }

    private void startEndIllnessActivity(int id, int position, Context context) {
        Intent i = new Intent(context, EndIllnessActivity.class);
        i.putExtra(EXTRA_ID, id);
        i.putExtra(EXTRA_POSITION, position);
        context.startActivity(i);

    }

    public void onPersonSwipedToDelete(int position, Patient patient) {
        new DeletePatientTask().execute(patient);
        mainActivityView.deletePersonAt(position);
    }

    public int getDurationOfIllness(Patient patient) throws ExecutionException, InterruptedException {
        GetDurationOfIllnessTask task = new GetDurationOfIllnessTask();
        task.execute(patient);
        int duration = (int) (task.get() / (24 * 60 * 60 * 1000));
        return duration;
    }

    public String createResultString(int durationOfIllness) {
        DateFormat format = new SimpleDateFormat("dd.MM");
        Date date = new Date(startDate);
        if (durationOfIllness > 1) {
            return "Болеет " + durationOfIllness + "-й день, с " + format.format(date);
        }
        if (durationOfIllness == 1) {
            return "Заболел вчера.";
        }
        return "Заболел сегодня.";
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

    private class GetDurationOfIllnessTask extends AsyncTask<Patient, Void, Long> {
        @Override
        protected Long doInBackground(Patient... patients) {
            startDate = caseDao.getTimestampByPatientId(patients[0].getId());
            Timestamp timestamp = new Timestamp(System.currentTimeMillis());
            Long now = timestamp.getTime();
            Long duration = now - startDate;
            return duration;
        }

        @Override
        protected void onPostExecute(Long duration) {
            super.onPostExecute(duration);
        }
    }
}
