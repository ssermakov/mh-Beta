package ru.ssermakov.newrecycler.logic;

import ru.ssermakov.newrecycler.data.DataSourceInterface;
import ru.ssermakov.newrecycler.data.Person;
import ru.ssermakov.newrecycler.view.Interfaces.MainActivityViewInterface;


/**
 * Created by btb_wild on 19.02.2018.
 */

public class MainController {

    private MainActivityViewInterface mainActivityView;
    private DataSourceInterface dataSource;

    public MainController(MainActivityViewInterface view, DataSourceInterface dataSource) {
        this.mainActivityView = view;
        this.dataSource = dataSource;

        getListFromDataSource();
    }


    private void getListFromDataSource() {
        mainActivityView.setUpAdapterAndView(dataSource.getListOfData());
    }

    public void onPersonNameClick(Person person) {
        mainActivityView.startPersonDetailActivity(
                person.getName(),/*
                person.getBackgroundColor(),*/
                person.getImage()
        );
    }

    public void onAddPersonClick() {
        mainActivityView.startAddPersonActivity();
//        mainActivityView.addPersonToView(AddPersonController.person);
    }

    public void onPersonSwipedToChangeState(int position, int id) {
        dataSource.toggleState(id);
        mainActivityView.toggleState(position, id);
    }

    public void onPersonSwipedToDelete(int position, int id) {
        dataSource.deletePerson(id);
        mainActivityView.deletePersonAt(position);
    }
}
