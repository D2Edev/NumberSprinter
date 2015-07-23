package org.home.d2e.numbersprinter;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.graphics.Color;
import android.os.Bundle;
import android.app.Fragment;
import android.os.IBinder;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;

import org.home.d2e.numbersprinter.Core.GameField;
import org.home.d2e.numbersprinter.Core.DataRetainFragment;
import org.home.d2e.numbersprinter.Core.MyApp;
import org.home.d2e.numbersprinter.Core.OnBackPressedListener;
import org.home.d2e.numbersprinter.Core.OnFragmentListener;
import org.home.d2e.numbersprinter.Core.Person;
import org.home.d2e.numbersprinter.Core.TickerService;
import org.home.d2e.numbersprinter.adapter.GameFieldAdapter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class GameFragment extends Fragment implements OnBackPressedListener, AdapterView.OnItemClickListener {
    private final String TAG = "TAG_GameGridFragment ";
    private GridView gvGameField;
    private List<GameField> gameFields;
    private GameField gameField;
    private int clr_r;
    private int clr_g;
    private int clr_b;
    private int clr_r1;
    private int clr_g1;
    private int clr_b1;
    private DataRetainFragment dataRetainFragment;
    private Person person;
    private TextView tvPlayer;
    private TextView tvElapsedTime;
    private int counterG;
    private int fieldCounter;
    OnFragmentListener onFragmentListener;
    TickerService tickerService;
    MyApp.OnTickListener onTickListener;
    boolean isBound;
    boolean saveData;
    boolean isTickerON;
    private int maxScore;


    public GameFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        Log.d(TAG, "onCreateView");
        return inflater.inflate(R.layout.fragment_game, container, false);
    }


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Log.d(TAG, "onViewCreated");
        //set interface from main activity - informing fragment in Back was pressed
        ((MainActivity) getActivity()).setOnBackPressedListener(this);
        //call fragment keeping data
        dataRetainFragment = (DataRetainFragment) getFragmentManager().findFragmentByTag(MainActivity.RETAIN_FRAGMENT_TAG);

        if (getActivity() instanceof OnFragmentListener) {
            //set interface to main activity - to call further actions
            onFragmentListener = (OnFragmentListener) getActivity();

        } else {
            throw new IllegalStateException("Activity not OnMainFragmentListener! ");
        }
        //init  controls
        tvPlayer = (TextView) view.findViewById(R.id.tvPlayer);
        tvElapsedTime = (TextView) view.findViewById(R.id.tvElapsedTime);
        gvGameField = (GridView) view.findViewById(R.id.gvGameField);
        gameFields = getGameFields();
        gvGameField.setAdapter(new GameFieldAdapter(view.getContext(), gameFields));
        //set listener on gridview
        gvGameField.setOnItemClickListener(this);
    }


    @Override
    public void onStart() {
        super.onStart();
        //will save data if fragment closed? default=true
        saveData = true;
        //if flag for ticker is FALSE
        if (!dataRetainFragment.isTickerON()) {
            //then start tick service
            Intent intentStartTickerService = new Intent(this.getActivity().getApplication(), TickerService.class);
            this.getActivity().startService(intentStartTickerService);
            //set tick flag TRUE
            isTickerON=true;
        }
        //bind to tick service
        Intent intent = new Intent(this.getActivity(), TickerService.class);
        getActivity().bindService(intent, connection, getActivity().getBaseContext().BIND_AUTO_CREATE);

        //defime max possible score for game based on complexity
        maxScore = 600;
        if (dataRetainFragment.getHardMode()) {
            maxScore = maxScore * 2;
        }
        // check if previously counter was used
        fieldCounter = 1;
        if (dataRetainFragment.getCounter() > 1) {
            fieldCounter = dataRetainFragment.getCounter();
        }

        if (dataRetainFragment != null) {
            person = dataRetainFragment.getPerson();
            tvPlayer.setText(getString(R.string.tCurrentPlayer) + " " + person.getName());
        }
        // Log.d(TAG, "person" + person);
        //call time refresh in textview
        refreshTimeField();
    }

    @Override
    public void onStop() {
        ((MyApp) getActivity().getApplication()).setOnTickListener(null);
        this.getActivity().unbindService(connection);
        dataRetainFragment.setTickerON(isTickerON);
        //clear game fields data
        dataRetainFragment.setGameFields(gameFields);
        //clear counter data in fragment
        dataRetainFragment.setCounter(fieldCounter);
        //copy person data to retain fragment
        dataRetainFragment.setPerson(person);
        //release listener
        ((MainActivity) getActivity()).setOnBackPressedListener(null);
        super.onStop();
    }


    private ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            TickerService.TickBinder binder = (TickerService.TickBinder) service;
            tickerService = binder.getService();
            isBound = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            isBound = false;
        }
    };


    private void refreshTimeField() {

        ((MyApp) getActivity().getApplication()).setOnTickListener(new MyApp.OnTickListener() {
            @Override
            public void onNextTick(final int counter) {
                if (getActivity() != null) {
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {

                            tvElapsedTime.setText(getString(R.string.tElapsedTime) + timeNumToText(counter));
                            counterG = counter;

                        }
                    });
                }

            }
        });

    }

    @Override
    public void backIsPressed() {
        Log.d(TAG, "backIsPressed");
        //stop Chrono
        tickerService.stopTick();
        //set flag tick is stopped
        isTickerON = false;
        //clear game fields data
        gameFields = null;
        //increase number of games played
        person.setGamesPlayed(person.getGamesPlayed() + 1);
        //reset passed game field counter
        fieldCounter = 1;

        //onFragmentListener.startGameOverFragment();


    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        //get clicked game field data
        gameField = (GameField) parent.getItemAtPosition(position);
        //check if correct game field is pressed
        Log.d(TAG, "counter " + fieldCounter + " field " + gameField.getFieldNumber());
        if (gameField.getFieldNumber() == fieldCounter) {
            //if condition ok - black the field
            gameField.setFieldColor(Color.rgb(0, 0, 0));
            gameField.setFieldTextColor(Color.rgb(0, 0, 0));
            //insert updated field to data set
            gameFields.set(position, gameField);
            //give new data to adapter
            gvGameField.setAdapter(new GameFieldAdapter(view.getContext(), gameFields));
            //if the field is last
            if (fieldCounter == 25) {
                //send STOP to chrono
                tickerService.stopTick();
                //set flag ticker is stopped
                isTickerON = false;
                //clear game fields data
                gameFields = null;
                //clear counter data
                fieldCounter = 1;
                //increase number of games played
                person.setGamesPlayed(person.getGamesPlayed() + 1);
                //update calculated scores for person
                person.setScoreTotal(person.getScoreTotal() + maxScore - counterG);
                person.setScoreMax(maxScore - counterG);
                //call GameOver fragment
                onFragmentListener.startGameOverFragment();
                return;
            }
            //move to next field
            fieldCounter++;
        }
    }


    private List<GameField> getGameFields() {
        //restore GameField from DataRetainFragment if exists

        List<GameField> tempGFs = new ArrayList<>();

        if (dataRetainFragment == null) {

        } else {
            if (dataRetainFragment.getGameFields() == null) {
                if (dataRetainFragment.getHardMode()) {
                    for (int i = 1; i < 26; i++) {
                        clr_r = (int) (Math.random() * 235 + 20);
                        clr_g = (int) (Math.random() * 235 + 20);
                        clr_b = (int) (Math.random() * 235 + 20);
                        clr_r1 = (int) (Math.random() * 235 + 20);
                        clr_g1 = (int) (Math.random() * 235 + 20);
                        clr_b1 = (int) (Math.random() * 235 + 20);
                        Log.d(TAG, "" + clr_r + " " + clr_g + " " + clr_b + " " + Integer.toHexString(Color.rgb(clr_r, clr_g, clr_b)));
                        tempGFs.add(new GameField(i, Color.rgb(clr_r, clr_g, clr_b), Color.rgb(clr_r1, clr_g1, clr_b1)));
                    }
                } else {
                    for (int i = 1; i < 26; i++) {
                        tempGFs.add(new GameField(i, Color.rgb(255, 255, 255), Color.rgb(0, 0, 0)));
                    }
                }


                Collections.shuffle(tempGFs);
                dataRetainFragment.setGameFields(tempGFs);
            } else {
                tempGFs = dataRetainFragment.getGameFields();
            }

        }
        return tempGFs;
    }
private String timeNumToText(int num){
String timeNumToText;
    int m;
    int s;
    int ms;
    m=num/600;
    timeNumToText=" "+Integer.toString(m)+"\" ";
    s=(num-m*600)/10;
    timeNumToText=timeNumToText+Integer.toString(s)+".";
    Log.d(TAG,""+num);
   ms=num-m*600-s*10;
    timeNumToText=timeNumToText+ms+"'";

    return timeNumToText;
}

}
