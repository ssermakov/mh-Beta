package ru.ssermakov.newrecycler.view.Interfaces;

import java.util.List;

import ru.ssermakov.newrecycler.data.room.entity.Case;

public interface DetailActivityInterface {

    void setUpAdapterAndView (List<Case> caseList);

    void setAge (String ageString);



}
