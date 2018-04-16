package ru.ssermakov.newrecycler.logic;

import android.app.Activity;
import android.os.AsyncTask;
import android.widget.Toast;

import java.util.List;
import java.util.concurrent.ExecutionException;

import ru.ssermakov.newrecycler.app.App;
import ru.ssermakov.newrecycler.data.room.MedicalHistoryDatabase;
import ru.ssermakov.newrecycler.data.room.dao.IllnessDao;
import ru.ssermakov.newrecycler.data.room.entity.Illness;
import ru.ssermakov.newrecycler.view.fragments.AbstractTabFragment;

/**
 * Created by btb_wild on 15.03.2018.
 */

public class FragmentController {

    private MedicalHistoryDatabase db;
    private AbstractTabFragment abstractTabFragment;
    private IllnessDao illnessDao;

    public FragmentController(AbstractTabFragment abstractTabFragment) {
        illnessDao = App.getInstance().getDb().illnessDao();
        this.abstractTabFragment = abstractTabFragment;
    }


    public void onButtonSendClick() {
        abstractTabFragment.testToast();
    }

    public void sendIllnessToDb(String s) throws ExecutionException, InterruptedException {
        IsIllnessExist task = new IsIllnessExist();
        task.execute(s);
        if (task.get().size() > 0) {
        } else {
            Illness illness = new Illness(s);
            new insertIllnessToDbTask().execute(illness);
        }
    }

    private class insertIllnessToDbTask extends AsyncTask<Illness, Void, Void> {
        @Override
        protected Void doInBackground(Illness... illnesses) {
            illnessDao.insert(illnesses[0]);
            return null;
        }
    }

    private class IsIllnessExist extends AsyncTask<String, Void, List<Illness>> {
        @Override
        protected List<Illness> doInBackground(String... strings) {
            return illnessDao.getAllByIllness(strings[0]);
        }
    }
}
