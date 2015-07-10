package org.home.d2e.numbersprinter.Core;

import android.app.Fragment;
import android.os.Bundle;

import java.util.List;

/**
 * Created by druzhyni on 05.07.2015.
 */
public class DataRetainFragment extends Fragment {

    // data object we want to retain

    private List<GameField> gameFields;
    private Person person;

    public void setPerson(Person person) {
        this.person = person;
    }

    public Person getPerson() {

        return person;
    }

    public void setGameFields(List<GameField> gameFields) {

        this.gameFields = gameFields;
    }

    public List<GameField> getGameFields() {

        return gameFields;
    }




    // this method is only called once for this fragment
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // retain this fragment
        setRetainInstance(true);

    }
}
