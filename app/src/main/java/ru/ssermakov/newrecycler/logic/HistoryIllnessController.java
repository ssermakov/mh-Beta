package ru.ssermakov.newrecycler.logic;

import android.os.AsyncTask;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutionException;

import ru.ssermakov.newrecycler.app.App;
import ru.ssermakov.newrecycler.data.room.dao.CaseDao;
import ru.ssermakov.newrecycler.data.room.dao.IllnessDao;
import ru.ssermakov.newrecycler.data.room.dao.PlanDao;
import ru.ssermakov.newrecycler.data.room.dao.SymptomDao;
import ru.ssermakov.newrecycler.data.room.entity.Case;
import ru.ssermakov.newrecycler.view.Interfaces.HistoryIllnessInterface;

public class HistoryIllnessController {

    private HistoryIllnessInterface historyView;
    private String startDate;
    private String endDate;
    private String illnessName;
    private String plansList;
    private String symptomsString;

    private CaseDao caseDao;
    private IllnessDao illnessDao;
    private PlanDao planDao;
    private SymptomDao symptomDao;

    private Long caseId;


    public HistoryIllnessController(Long caseId) {
        this.caseId = caseId;

        caseDao = App.getInstance().getDb().caseDao();
        illnessDao = App.getInstance().getDb().illnessDao();
        planDao = App.getInstance().getDb().planDao();
        symptomDao = App.getInstance().getDb().symptomDao();
    }

    public HistoryIllnessController(HistoryIllnessInterface historyIllnessInterface, Long caseId) {
        historyView = historyIllnessInterface;
        this.caseId = caseId;

        caseDao = App.getInstance().getDb().caseDao();
        illnessDao = App.getInstance().getDb().illnessDao();
        planDao = App.getInstance().getDb().planDao();
        symptomDao = App.getInstance().getDb().symptomDao();



        try {
            startDate = getStartDate();
            endDate = getEndDate();
            illnessName = getIllnessName();
            plansList = getStringOfPlans();
            symptomsString = getStringOfSymptoms();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


        setView();
    }

    private String getStringOfSymptoms() throws ExecutionException, InterruptedException {
        GetSymptomsListTask task = new GetSymptomsListTask();
        task.execute(caseId);
        List<String> list = task.get();
        return formatString(list);
    }

    public List<String> getListOfSymptoms() throws ExecutionException, InterruptedException {
        GetSymptomsListTask task = new GetSymptomsListTask();
        task.execute(caseId);
        return task.get();
    }

    private String getStringOfPlans() throws ExecutionException, InterruptedException {
        GetPlansListTask task = new GetPlansListTask();
        task.execute(caseId);
        List<String> list = task.get();
        return formatString(list);
    }

    public List<String> getListOfPlans() throws ExecutionException, InterruptedException {
        GetPlansListTask task = new GetPlansListTask();
        task.execute(caseId);
        return task.get();
    }

    private String formatString(List<String> list) {
        int k = 1;
        String formattedString = "";
        for (String i : list) {
            if (list.get(0) == null) {
               return "no plans";
            } else {
                formattedString = formattedString + k + ". " + i + "\n";
                k++;
            }
        }
        return formattedString;
    }

    public String getIllnessName() throws ExecutionException, InterruptedException {
        GetIllnessNameTask task = new GetIllnessNameTask();
        task.execute(caseId);
        return task.get();
    }


    public String getStartDate() throws ExecutionException, InterruptedException {
        GetStartDateTask task = new GetStartDateTask();
        task.execute(caseId);
        return task.get();
    }

    private String getEndDate() throws ExecutionException, InterruptedException {
        GetEndDateTask task = new GetEndDateTask();
        task.execute(caseId);
        return task.get();
    }

    private void setView() {
        historyView.setStartDate(startDate);
        historyView.setEndDate(endDate);
        historyView.setIllnessName(illnessName);
        historyView.setPlans(plansList);
        historyView.setSymptoms(symptomsString);
    }

    private class GetStartDateTask extends AsyncTask<Long, Void, String> {
        @Override
        protected String doInBackground(Long... longs) {
            Case aCase = caseDao.getById(longs[0]);
            DateFormat format = new SimpleDateFormat("dd/MM/yyyy");
            return format.format(aCase.getStartDate());
        }
    }

    private class GetEndDateTask extends AsyncTask<Long, Void, String> {
        @Override
        protected String doInBackground(Long... longs) {
            Case aCase = caseDao.getById(longs[0]);
            Date date = aCase.getEndDate();
            if (date == null) {
                return "";
            }
            DateFormat format = new SimpleDateFormat("dd/MM/yyyy");
            return format.format(date);
        }
    }

    private class GetIllnessNameTask extends AsyncTask<Long, Void, String> {
        @Override
        protected String doInBackground(Long... longs) {
            Case aCase = caseDao.getById(longs[0]);
            return illnessDao.getById(aCase.getIllnessId()).getName();
        }
    }

    private class GetPlansListTask extends AsyncTask<Long, Void, List<String>> {
        @Override
        protected List<String> doInBackground(Long... longs) {
            return  planDao.selectPlansByCaseId(longs[0]);
        }
    }

    private class GetSymptomsListTask extends AsyncTask<Long, Void, List<String>> {
        @Override
        protected List<String> doInBackground(Long... longs) {
            return  symptomDao.selectSymptomsByCaseId(longs[0]);
        }
    }
}
