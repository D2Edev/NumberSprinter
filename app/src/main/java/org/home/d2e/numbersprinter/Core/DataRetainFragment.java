package org.home.d2e.numbersprinter.Core;

import android.app.Fragment;
import android.os.Bundle;

import java.util.List;

public class DataRetainFragment extends Fragment {

    // data object we want to retain

    private List<GameField> gameFields;
    private Person person;
    private boolean hardMode;
    private boolean tickerON;
    private int counter;
    private String currFragTag;
    MyApp.OnTickListener listener;

    public String getCurrFragTag() {       return currFragTag;    }

    public void setCurrFragTag(String currFragTag) {
        this.currFragTag = currFragTag;
    }

    public int getCounter() {
        return counter;
    }

    public void setCounter(int counter) {
        this.counter = counter;
    }

    public boolean isTickerON() {
        return tickerON;
    }

    public void setTickerON(boolean tickerON) {
        this.tickerON = tickerON;
    }

    public MyApp.OnTickListener getListener() {
        return listener;
    }

    public void setListener(MyApp.OnTickListener listener) {
        this.listener = listener;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public Person getPerson() {        return person;    }

    public void setGameFields(List<GameField> gameFields) {        this.gameFields = gameFields;    }

    public List<GameField> getGameFields() {        return gameFields;    }

    public boolean getHardMode() {
        return hardMode;
    }

    public void setHardMode(boolean hardMode) {
            this.hardMode = hardMode;
    }

    // this method is only called once for this fragment
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // retain this fragment
        setRetainInstance(true);

    }
}
