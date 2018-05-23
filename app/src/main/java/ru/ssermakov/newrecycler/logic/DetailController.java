package ru.ssermakov.newrecycler.logic;

import android.os.AsyncTask;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutionException;

import ru.ssermakov.newrecycler.app.App;
import ru.ssermakov.newrecycler.data.room.dao.CaseDao;
import ru.ssermakov.newrecycler.data.room.dao.IllnessDao;
import ru.ssermakov.newrecycler.data.room.dao.PatientDao;
import ru.ssermakov.newrecycler.data.room.entity.Case;
import ru.ssermakov.newrecycler.data.room.entity.Illness;
import ru.ssermakov.newrecycler.data.room.entity.Patient;
import ru.ssermakov.newrecycler.view.Interfaces.DetailActivityInterface;
import ru.ssermakov.newrecycler.view.PersonDetailActivity;

public class DetailController {

    private DetailActivityInterface detailActivityView;
    private CaseDao caseDao;
    private PatientDao patientDao;
    private IllnessDao illnessDao;

    public DetailController(DetailActivityInterface detailActivityView) {
        this.detailActivityView = detailActivityView;
        caseDao = App.getInstance().getDb().caseDao();
        patientDao = App.getInstance().getDb().patientDao();
        illnessDao = App.getInstance().getDb().illnessDao();


        getListCasesFromDataSource();
        setAge();
        setFAB();
    }

    private void setFAB() {
        detailActivityView.setEditFAB();
    }

    private void setAge() {
        try {
            detailActivityView.setAge(
                    MainController.formatAge(
                            getAge(PersonDetailActivity.id)
                    )
            );
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void getListCasesFromDataSource() {
        GetCasesByPatientIdTask task = new GetCasesByPatientIdTask();
        task.execute(PersonDetailActivity.id);
        try {
            detailActivityView.setUpAdapterAndView(task.get());
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
    }

    private Long getAge(Integer patientId) throws ExecutionException, InterruptedException {
        GetPatientAgeTask task = new GetPatientAgeTask();
        task.execute(patientId);
        return task.get();
    }

    public String convertDateToString(Date startDate) {
        if (startDate == null) {
            return "";
        }
        DateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        return format.format(startDate);
    }

    public Illness getIllnessNameById(Long id) throws ExecutionException, InterruptedException {
        GetIllnessByIdTask task = new GetIllnessByIdTask();
        task.execute(id);
        return task.get();
    }

    public Patient getPatientById(Long id) throws ExecutionException, InterruptedException {
        GetPatientByIdTask task = new GetPatientByIdTask();
        task.execute(id);
        return task.get();
    }

    private class GetCasesByPatientIdTask extends AsyncTask<Integer, Void, List<Case>> {

        @Override
        protected List<Case> doInBackground(Integer... integers) {
            return caseDao.getAllByPatientId(integers[0]);
        }
    }

    private class GetIllnessByIdTask extends AsyncTask<Long, Void, Illness> {

        @Override
        protected Illness doInBackground(Long... longs) {
            return illnessDao.getById(longs[0]);
        }
    }

    private class GetPatientByIdTask extends AsyncTask<Long, Void, Patient> {

        @Override
        protected Patient doInBackground(Long... longs) {
            return patientDao.getById(longs[0]);
        }
    }

    private class GetPatientAgeTask extends AsyncTask<Integer, Void, Long> {
        @Override
        protected Long doInBackground(Integer... integers) {
            Long dateOfBirth = patientDao.getDateOfBirth(integers[0]);
            Timestamp timestamp = new Timestamp(System.currentTimeMillis());
            return timestamp.getTime() - dateOfBirth;
        }
    }
}
