package ru.ssermakov.newrecycler.logic;

import android.net.Uri;

import ru.ssermakov.newrecycler.data.DataSourceInterface;
import ru.ssermakov.newrecycler.view.Interfaces.PersonActivityInterface;

/**
 * Created by btb_wild on 22.02.2018.
 */

public class AddPersonController {
    DataSourceInterface dataSource;
    PersonActivityInterface addPersonView;

    public AddPersonController(DataSourceInterface dataSource, PersonActivityInterface addPersonView) {
        this.dataSource = dataSource;
        this.addPersonView = addPersonView;
    }

    public void addPerson(String personName, Uri imageUri, String state) {
        dataSource.addPersonToDb(personName, imageUri, state);
        int lastPersonId = dataSource.getLastPersonId();
        addPersonView.setPersonId(lastPersonId);
    }

}
