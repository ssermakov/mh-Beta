package ru.ssermakov.newrecycler.logic;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutionException;

import ru.ssermakov.newrecycler.app.App;
import ru.ssermakov.newrecycler.data.room.MedicalHistoryDatabase;
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
import ru.ssermakov.newrecycler.view.BeginIllnessActivity;
import ru.ssermakov.newrecycler.view.Interfaces.FragmentInterface;
import ru.ssermakov.newrecycler.view.fragments.AbstractTabFragment;

/**
 * Created by btb_wild on 15.03.2018.
 */

public class FragmentController extends AppCompatActivity {

    private MedicalHistoryDatabase db;
    private AbstractTabFragment abstractTabFragment;
    private IllnessDao illnessDao;
    private CaseDao caseDao;
    private SymptomDao symptomDao;
    private PlanDao planDao;
    private PatientDao patientDao;

    private ArrayList<Integer> listOfSelectedItems;

    private FragmentInterface fragmentInterface;

    public FragmentController(AbstractTabFragment abstractTabFragment, FragmentInterface fragmentInterface) {
        illnessDao = App.getInstance().getDb().illnessDao();
        caseDao = App.getInstance().getDb().caseDao();
        symptomDao = App.getInstance().getDb().symptomDao();
        planDao = App.getInstance().getDb().planDao();
        patientDao = App.getInstance().getDb().patientDao();
        this.abstractTabFragment = abstractTabFragment;
        this.fragmentInterface = fragmentInterface;

        listOfSelectedItems = new ArrayList<>();
    }


    public void createIllness(String s) throws ExecutionException, InterruptedException {
        if (s.equals("")) {
            s = BeginIllnessActivity.no_illness;
        }
        IsIllnessExistTask task = new IsIllnessExistTask();
        task.execute(s);
        if (task.get().size() > 0) {
        } else {
            Illness illness = new Illness(s);
            new insertIllnessToDbTask().execute(illness);
        }
    }

    public int getIllnessIdFromDb(String illnessName) throws ExecutionException, InterruptedException {
        if (illnessName.equals("")) {
            illnessName = BeginIllnessActivity.no_illness;
        }
        getIllnessIdFromDbTask task = new getIllnessIdFromDbTask();
        task.execute(illnessName);
        return task.get();
    }

    public Long createCase(int patientId, int illnessIdFromDb, String startDate) throws ExecutionException, InterruptedException {
        Case aCase = new Case(
                (long) patientId,
                (long) illnessIdFromDb,
                convertStringToDate(startDate),
                null
        );
        CreateCaseTask task = new CreateCaseTask();
        task.execute(aCase);
        return task.get();
    }

    public void updateCase(Case aCase) {
        UpdateCaseTask task = new UpdateCaseTask();
        task.execute(aCase);
    }

    public void createSymptoms(Long caseId, ArrayList<String> symptoms) {
        for (String symptomString : symptoms) {
            Symptom symptom = new Symptom(caseId, symptomString.toLowerCase().trim());
            CreateSymptomTask task = new CreateSymptomTask();
            task.execute(symptom);

        }
    }

    public void updateSymptoms(Long caseId, ArrayList<String> listOfSymptoms) {
        HistoryIllnessController controller = new HistoryIllnessController(caseId);
        List<String> symptomsList = null;
        try {
            symptomsList = controller.getListOfSymptoms();
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }

        if (symptomsList != null) {
            for (long i = 0; i < symptomsList.size(); i++) {
                DeleteSymptomsByCaseId deleteTask = new DeleteSymptomsByCaseId();
                deleteTask.execute(caseId);
            }
        }

        for (String symptomString : listOfSymptoms) {
            Symptom symptom = new Symptom(caseId, symptomString.toLowerCase().trim());
            CreateSymptomTask task = new CreateSymptomTask();
            task.execute(symptom);
        }
    }

    public void createPlans(Long caseId, ArrayList<String> plans) {
        if (plans.size() == 0) {
            Plan plan = new Plan(caseId, null);
            CreatePlanTask task = new CreatePlanTask();
            task.execute(plan);
        } else {
            for (String planString : plans) {
                Plan plan = new Plan(caseId, planString.toLowerCase().trim());
                CreatePlanTask task = new CreatePlanTask();
                task.execute(plan);
            }
        }
    }

    public void updatePlans(Long caseId, ArrayList<String> listOfPlans) {
        HistoryIllnessController controller = new HistoryIllnessController(caseId);
        List<String> plansList = null;
        try {
            plansList = controller.getListOfPlans();
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }

        if (plansList != null) {
            for (long i = 0; i < plansList.size(); i++) {
                DeletePlansByCaseId deleteTask = new DeletePlansByCaseId();
                deleteTask.execute(caseId);
            }
        }

        if (listOfPlans.size() == 0 || listOfPlans.get(0) == null) {
            Plan plan = new Plan(caseId, null);
            CreatePlanTask task = new CreatePlanTask();
            task.execute(plan);
        } else {
            for (String planString : listOfPlans) {
                Plan plan = new Plan(caseId, planString.toLowerCase().trim());
                CreatePlanTask task = new CreatePlanTask();
                task.execute(plan);
            }
        }
    }

    public void togglePatientState(int id) {
        TogglePatientStateTask task = new TogglePatientStateTask();
        task.execute(id);
    }

    public void onPlanItemClick(int position, String string) {
        fragmentInterface.startDialogToChangePlanContent(position, string);
    }

    public void onSymptomItemClick(int position, String string) {
        fragmentInterface.startDialogToChangeSymptomContent(position, string);
    }

    public void selectItem(int position) {
        listOfSelectedItems.add(position);
    }

    public ArrayList<Integer> getListOfSelectedItems() {
        return listOfSelectedItems;
    }

    public void removeItemFromSelected(int position) {
        for (int i = 0; i < listOfSelectedItems.size(); i++) {
            if (listOfSelectedItems.get(i) == position) {
                listOfSelectedItems.remove(listOfSelectedItems.get(i));
            }
        }
    }


    private class CreateCaseTask extends AsyncTask<Case, Void, Long> {

        @Override
        protected Long doInBackground(Case... cases) {
            return caseDao.insert(cases[0]);
        }
    }

    private class insertIllnessToDbTask extends AsyncTask<Illness, Void, Void> {

        @Override
        protected Void doInBackground(Illness... illnesses) {
            illnessDao.insert(illnesses[0]);
            return null;
        }
    }

    private class IsIllnessExistTask extends AsyncTask<String, Void, List<Illness>> {

        @Override
        protected List<Illness> doInBackground(String... strings) {
            return illnessDao.getAllByIllness(strings[0]);
        }
    }

    private class getIllnessIdFromDbTask extends AsyncTask<String, Void, Integer> {

        @Override
        protected Integer doInBackground(String... strings) {
            return illnessDao.getIllnessId(strings[0]);
        }
    }

    private class CreateSymptomTask extends AsyncTask<Symptom, Void, Void> {
        @Override
        protected Void doInBackground(Symptom... symptoms) {
            symptomDao.insert(symptoms[0]);
            return null;
        }
    }

//    private class UpdateSymptomTask extends AsyncTask<Symptom, Void, Void> {
//        @Override
//        protected Void doInBackground(Symptom... symptoms) {
//            symptomDao.update(symptoms[0]);
//            return null;
//        }
//    }

    public Date convertStringToDate(String string) {
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        Date date = null;
        try {
            date = dateFormat.parse(string);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    private class CreatePlanTask extends AsyncTask<Plan, Void, Void> {
        @Override
        protected Void doInBackground(Plan... plans) {
            planDao.insert(plans[0]);
            return null;
        }
    }

    private class UpdatePlanTask extends AsyncTask<Plan, Void, Void> {
        @Override
        protected Void doInBackground(Plan... plans) {
            planDao.update(plans[0]);
            return null;
        }
    }

    private class TogglePatientStateTask extends AsyncTask<Integer, Void, Void> {
        @Override
        protected Void doInBackground(Integer... integers) {
            Patient patient = patientDao.getById(integers[0]);
            patient.setState("болеет");
            patientDao.update(patient);
            return null;
        }
    }

    private class UpdateCaseTask extends AsyncTask<Case, Void, Void> {
        @Override
        protected Void doInBackground(Case... cases) {
            caseDao.update(cases[0]);
            return null;
        }
    }

    private class DeleteSymptomsByCaseId extends AsyncTask<Long, Void, Void> {
        @Override
        protected Void doInBackground(Long... longs) {
            symptomDao.deleteSymptomsByCaseId(longs[0]);
            return null;
        }
    }

    private class DeletePlansByCaseId extends AsyncTask<Long, Void, Void> {
        @Override
        protected Void doInBackground(Long... longs) {
            planDao.deletePlansByCaseId(longs[0]);
            return null;
        }
    }
}
