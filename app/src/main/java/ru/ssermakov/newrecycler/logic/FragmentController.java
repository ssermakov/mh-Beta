package ru.ssermakov.newrecycler.logic;

import android.widget.Toast;

import ru.ssermakov.newrecycler.data.DataSourceInterface;
import ru.ssermakov.newrecycler.view.fragments.AbstractTabFragment;

/**
 * Created by btb_wild on 15.03.2018.
 */

public class FragmentController {

    private DataSourceInterface dataSource;
    private AbstractTabFragment abstractTabFragment;

    public FragmentController(DataSourceInterface dataSource, AbstractTabFragment abstractTabFragment) {
        this.dataSource = dataSource;
        this.abstractTabFragment = abstractTabFragment;
    }


    public void onButtonSendClick() {
        abstractTabFragment.testToast();
    }
}
