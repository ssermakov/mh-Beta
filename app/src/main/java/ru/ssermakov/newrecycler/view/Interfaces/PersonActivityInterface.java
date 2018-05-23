package ru.ssermakov.newrecycler.view.Interfaces;

import ru.ssermakov.newrecycler.data.room.entity.Patient;

/**
 * Created by btb_wild on 21.02.2018.
 */

public interface PersonActivityInterface {



    void setImage(Patient patient);

    void setName(Patient patient);

    void setDate(Patient patient);
}
