package ru.ssermakov.newrecycler.logic;

import android.os.AsyncTask;

import java.util.concurrent.ExecutionException;

import ru.ssermakov.newrecycler.app.App;
import ru.ssermakov.newrecycler.data.room.dao.CaseDao;
import ru.ssermakov.newrecycler.data.room.entity.Case;
import ru.ssermakov.newrecycler.view.Interfaces.BeginIllnessActivityInterface;

public class BeginIllnessActivityController extends HistoryIllnessController {

//    BeginIllnessActivityInterface beginIllnessActivityView;
    private CaseDao caseDao;

    public BeginIllnessActivityController(Long caseId) {
        super(caseId);
        caseDao = App.getInstance().getDb().caseDao();
    }


    public Case getCase(long caseId) throws ExecutionException, InterruptedException {
        GetCaseTask task = new GetCaseTask();
        task.execute(caseId);
        return task.get();
    }

    private class GetCaseTask extends AsyncTask<Long, Void, Case> {

        @Override
        protected Case doInBackground(Long... longs) {
            return caseDao.getById(longs[0]);
        }
    }
}

