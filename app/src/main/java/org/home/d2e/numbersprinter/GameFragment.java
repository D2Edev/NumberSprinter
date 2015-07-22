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


    public GameFragment() {
        // Required empty public constructor
    }

    @Override
    public void onStop() {
        if (isBound) {
            this.getActivity().unbindService(connection);
            isBound = false;
        }
        super.onStop();
    }

    @Override
    public void onStart() {
        super.onStart();
        if (!dataRetainFragment.isTickerON()) {
            Intent intentStartTickerService = new Intent(this.getActivity().getApplication(), TickerService.class);
            this.getActivity().startService(intentStartTickerService);
            dataRetainFragment.setTickerON(true);
        }
        Intent intent = new Intent(this.getActivity(), TickerService.class);
        getActivity().bindService(intent, connection, getActivity().getBaseContext().BIND_AUTO_CREATE);

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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        Log.d(TAG, "onCreateView");
        return inflater.inflate(R.layout.fragment_game, container, false);
    }


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        Log.d(TAG, "onAttach");

        ((MainActivity) activity).setOnBackPressedListener(this);
        dataRetainFragment = (DataRetainFragment) getFragmentManager().findFragmentByTag(MainActivity.RETAIN_FRAGMENT_TAG);

        if (activity instanceof OnFragmentListener) {
            onFragmentListener = (OnFragmentListener) activity;

        } else {
            throw new IllegalStateException("Activity not OnMainFragmentListener! ");
        }


    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Log.d(TAG, "onViewCreated");
        //init variables and controls
        tvPlayer = (TextView) view.findViewById(R.id.tvPlayer);
        tvElapsedTime = (TextView) view.findViewById(R.id.tvElapsedTime);
        gvGameField = (GridView) view.findViewById(R.id.gvGameField);
        gameFields = getGameFields();
        gvGameField.setAdapter(new GameFieldAdapter(view.getContext(), gameFields));
        //start service

        if (dataRetainFragment != null) {
            person = dataRetainFragment.getPerson();
            tvPlayer.setText(getString(R.string.tCurrentPlayer) + " " + person.getName());
        }
        Log.d(TAG, "person" + person);

        //verify counter
        fieldCounter = 1;
        //call time refresh in textview
        refreshTimeField();

//set onFragmentListener on gridview

        gvGameField.setOnItemClickListener(this);


    }

    @Override
    public void onDetach() {
        super.onDetach();
        Log.d(TAG, "onDetach");

    }

    @Override
    public void onPause() {
        super.onPause();
        //if
        try {
            Thread.sleep(100L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        ;

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.d(TAG, "onDestroyView");

    }

    private void refreshTimeField() {

        ((MyApp)getActivity().getApplication()).setOnTickListener(new MyApp.OnTickListener() {
            @Override
            public void onNextTick(final int counter) {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (tvElapsedTime != null) {
                            tvElapsedTime.setText(getString(R.string.tElapsedTime) + counter);
                            counterG = counter;
                        }
                    }
                });
            }
        });

    }

    @Override
    public void backIsPressed() {
        Log.d(TAG, "backIsPressed");


        tickerService.stopTick();
        dataRetainFragment.setTickerON(false);
        if (dataRetainFragment != null) {
            dataRetainFragment.setGameFields(null);
        }
        ((MainActivity) getActivity()).setOnBackPressedListener(null);


    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        //get clicked game field datd
        gameField = (GameField) parent.getItemAtPosition(position);
        //Toast.makeText(parent.getContext(), "Click to " + gameField.getFieldNumber(), Toast.LENGTH_SHORT).show();
        //checking if correct game field is pressed and it's not the last
        if (gameField.getFieldNumber() == fieldCounter && fieldCounter < 26) {

            Log.d(TAG, "gameField  " + gameField.getFieldNumber() + " color " + gameField.getFieldColor());
            //if condition ok - black the field
            gameField.setFieldColor(Color.rgb(0, 0, 0));
            Log.d(TAG, "gameField  " + gameField.getFieldNumber() + " color " + gameField.getFieldColor());
            Log.d(TAG, "from List: gameField  " + gameFields.get(fieldCounter - 1).getFieldNumber() + " color " + gameFields.get(fieldCounter - 1).getFieldColor());

            gameFields.set(position, gameField);
            gvGameField.setAdapter(new GameFieldAdapter(view.getContext(), gameFields));
            //move check to next field
            fieldCounter++;
            if (fieldCounter == 26) {
                //if all fields processed - send STOP to chrono
                tickerService.stopTick();
                dataRetainFragment.setTickerON(false);
                dataRetainFragment.setGameFields(null);
                dataRetainFragment.setListener(null);
                saveUserData();
                                onFragmentListener.startGameOverFragment();


            }
        }
    }

    private void saveUserData() {

        person.setGamesPlayed(person.getGamesPlayed() + 1);
        person.setScoreTotal(person.getScoreTotal() + 600 - counterG);
        person.setScoreMax(600 - counterG);
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


}
