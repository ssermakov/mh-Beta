package ru.ssermakov.newrecycler.logic;

import android.os.AsyncTask;
import android.widget.Toast;

import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutionException;

import ru.ssermakov.newrecycler.app.App;
import ru.ssermakov.newrecycler.data.room.dao.CaseDao;
import ru.ssermakov.newrecycler.data.room.dao.PatientDao;
import ru.ssermakov.newrecycler.data.room.entity.Case;
import ru.ssermakov.newrecycler.data.room.entity.Patient;

public class EndIllnessController {

    private PatientDao patientDao;
    private CaseDao caseDao;
    private Date endDate;

    public EndIllnessController() {
        patientDao = App.getInstance().getDb().patientDao();
        caseDao = App.getInstance().getDb().caseDao();
    }

    public void togglePatientState(int id) {
        TogglePatientStateTask task = new TogglePatientStateTask();
        task.execute(id);
    }

    public void setEndDateOfIllness(int id, Date date) throws ExecutionException, InterruptedException {
        endDate = date;
        GetIdOpenCaseTask getIdOpenCaseTask = new GetIdOpenCaseTask();
        getIdOpenCaseTask.execute(id);
        List<Integer> idOpenCaseList = getIdOpenCaseTask.get();
        String s = "goal";
        SetEndDateTask task = new SetEndDateTask();
        task.execute(idOpenCaseList);
    }

    private class TogglePatientStateTask extends AsyncTask<Integer, Void, Void>{
        @Override
        protected Void doInBackground(Integer... integers) {
            Patient patient = patientDao.getById(integers[0]);
            patient.setState("не болеет");
            patientDao.update(patient);
            return null;
        }
    }

    private class GetIdOpenCaseTask extends AsyncTask<Integer, Void, List<Integer>> {
        @Override
        protected List<Integer> doInBackground(Integer... integers) {
            return caseDao.selectOpenCaseId(integers[0]);
        }

        @Override
        protected void onPostExecute(List<Integer> integers) {
            super.onPostExecute(integers);
        }
    }

    private class SetEndDateTask extends AsyncTask<List<Integer>, Void, Void> {
        @Override
        protected Void doInBackground(List<Integer>... lists) {
            Case fact = caseDao.getById(lists[0].get(0));
            fact.setEndDate(endDate);
            caseDao.update(fact);
            return null;
        }
    }
}
