package io.github.d2edev.numbersprinter;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.graphics.Color;
import android.os.Bundle;
import android.app.Fragment;
import android.os.IBinder;
import android.os.Vibrator;
import android.preference.PreferenceManager;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;

import io.github.d2edev.numbersprinter.Core.GameField;
import io.github.d2edev.numbersprinter.Core.DataRetainFragment;
import io.github.d2edev.numbersprinter.Core.OnBackPressedListener;
import io.github.d2edev.numbersprinter.Core.OnFragmentListener;
import io.github.d2edev.numbersprinter.Core.Person;
import io.github.d2edev.numbersprinter.Core.PrefKeys;
import io.github.d2edev.numbersprinter.Core.TickerService;
import io.github.d2edev.numbersprinter.adapter.GameFieldAdapter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class GameFragment extends Fragment implements OnBackPressedListener, AdapterView.OnItemClickListener {
    private final String TAG = "TAG_GameFragment ";
    private GridView gvGameField;
    private List<GameField> gameFields;
    private GameField gameField;
    private DataRetainFragment dataRetainFragment;
    private Person person;
    private TextView tvPlayer;
    private TextView tvElapsedTime;
    private int counterG;
    private int fieldCounter;
    private final int BLACK = 0;
    private final int WHITE = 1;
    private final int RANDOM = 2;
    private int mxSize;
    private int unitSize;

    OnFragmentListener onFragmentListener;
    TickerService tickerService;
    boolean isBound;
    boolean saveData;
    boolean isTickerON;
    private int maxScore;
    Vibrator vibrator;
    GameFieldAdapter gfAdapter;


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
        vibrator = (Vibrator) getActivity().getBaseContext().getSystemService(Context.VIBRATOR_SERVICE);
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

        tvElapsedTime.setTextSize(TypedValue.COMPLEX_UNIT_PX,((MainActivity) getActivity()).currSideLimit()/5);
        gvGameField = (GridView) view.findViewById(R.id.gvGameField);
        mxSize = getMatrixSize();
        gvGameField.setNumColumns(mxSize);
        gameFields = getGameFields();
        unitSize = ((MainActivity)getActivity()).currSideLimit() / (3 * (3 * mxSize + 1));
        gfAdapter = new GameFieldAdapter(view.getContext(), gameFields, unitSize);
        gvGameField.setAdapter(gfAdapter);
        //set listener on gridview
        gvGameField.setOnItemClickListener(this);
    }

    @Override
    public void onResume() {

        super.onResume();
        //set interface from main activity - informing fragment Back was pressed
        ((MainActivity) getActivity()).setOnBackPressedListener(this);
        //will save data if fragment closed? default=true
        saveData = true;
        //if flag for ticker is FALSE
        if (!dataRetainFragment.isTickerON()) {
            //then start tick service
            Intent intentStartTickerService = new Intent(this.getActivity().getApplication(), TickerService.class);
            this.getActivity().startService(intentStartTickerService);
            //set tick flag TRUE
            isTickerON = true;
        }
        //bind to tick service
        Intent intent = new Intent(this.getActivity(), TickerService.class);
        getActivity().bindService(intent, connection, getActivity().getBaseContext().BIND_AUTO_CREATE);

        //defime max possible score for game based on complexity
        maxScore = (int) Math.pow(2,mxSize)*10;
        if (dataRetainFragment.getHardMode()) {
            maxScore = (int) (maxScore*1.5);
        }
        // check if previously counter was used
        fieldCounter = 1;
        if (dataRetainFragment.getCounter() > 1) {
            fieldCounter = dataRetainFragment.getCounter();
        }
//extract current person
        if (dataRetainFragment != null) {
            person = dataRetainFragment.getPerson();
            tvPlayer.setText(getString(R.string.tCurrentPlayer) + " " + person.getName());
            dataRetainFragment.setCurrFragTag(MainActivity.GAME_FRAGMENT_TAG);
        }
        LocalBroadcastManager.getInstance(getActivity()).registerReceiver(tickReceiver, new IntentFilter("new_tick"));
        //resume transmitting ticks
        // tickerService.SendTick(true);


    }

    @Override
    public void onPause() {

        LocalBroadcastManager.getInstance(getActivity()).unregisterReceiver(tickReceiver);
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
        super.onPause();
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
    public void backIsPressed() {
        Log.d(TAG, "backIsPressed");
        //stop tick
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
            //update adapter
            gfAdapter.notifyDataSetChanged();
            doVibrate(isVibraEnabled());
            //if the field is last
            if (fieldCounter == mxSize*mxSize) {
                //send STOP to chrono
                tickerService.stopTick();
                //set flag ticker is stopped
                isTickerON = false;
                //clear game fields data
                gameFields = null;
                //clear counter data
                fieldCounter = 1;
                doVibrate(isVibraEnabled());
                //increase number of games played
                person.setGamesPlayed(person.getGamesPlayed() + 1);
                //update calculated scores for person
                person.setScoreMax(calcRoundScore(maxScore, counterG));
                person.setRoundTime(counterG);
                //call GameOver fragment
                onFragmentListener.startGameOverFragment();
                return;
            }
            //move to next field
            fieldCounter++;
        }
    }


    private List<GameField> getGameFields() {


        List<GameField> tempGFs = new ArrayList<>();
        //restore GameField from DataRetainFragment if exists
        if (dataRetainFragment == null) {

        } else {
            if (dataRetainFragment.getGameFields() == null) {
                if (dataRetainFragment.getHardMode()) {
                    //generate random colored fields
                    for (int i = 1; i < mxSize*mxSize + 1; i++) {
                        tempGFs.add(new GameField(i, generateRGB(RANDOM), generateRGB(RANDOM)));
                    }
                } else {
                    for (int i = 1; i < mxSize*mxSize + 1; i++) {
                        tempGFs.add(new GameField(i, generateRGB(WHITE), generateRGB(BLACK)));
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

    private String timeNumToText(int num) {
        String timeNumToText;
        int m;
        int s;
        int ms;
        m = num / 600;
        timeNumToText = " " + Integer.toString(m) + "' ";
        s = (num - m * 600) / 10;
        timeNumToText = timeNumToText + Integer.toString(s) + ".";
        //Log.d(TAG, "" + num);
        ms = num - m * 600 - s * 10;
        timeNumToText = timeNumToText + ms + "\"";
        return timeNumToText;
    }

    private BroadcastReceiver tickReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            counterG = intent.getIntExtra("counter", 1);
            if (getActivity() != null) {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        tvElapsedTime.setText(timeNumToText(counterG));
                    }
                });
            }


        }
    };

    private boolean isVibraEnabled() {

        //boolean enb=getActivity().getSharedPreferences(PrefKeys.NAME, Context.MODE_PRIVATE).getBoolean(PrefKeys.VIBRATE,false);
        boolean enb = PreferenceManager.getDefaultSharedPreferences(getActivity()).getBoolean(PrefKeys.VIBRATE, false);
        Log.d(TAG, "Vibra enabled: " + enb);
        return enb;

    }

    private int getMatrixSize() {
        int msize = Integer.parseInt(PreferenceManager.getDefaultSharedPreferences(getActivity()).getString(PrefKeys.MATRIX_SIZE, "5"));
        return msize;
    }

    private void doVibrate(boolean doVibrate) {
        if (doVibrate) {
            Log.d(TAG, "vibra_called");
            vibrator.vibrate(PrefKeys.VIB_LENGTH);
        }

    }

        private int generateRGB(int which) {
        int colour;
        switch (which) {
            case BLACK:
                colour = Color.rgb(0, 0, 0);
                break;
            case WHITE:
                colour = Color.rgb(255, 255, 255);
                break;
            case RANDOM:
//                colour = Color.rgb((int) (Math.random() * 235 + 20), (int) (Math.random() * 235 + 20), (int) (Math.random() * 235 + 20));
//                use "safe" colors
                colour = Color.rgb(51+(int) (Math.random() * 5)*51, 51+(int) (Math.random() * 5)*51, 51+(int) (Math.random() * 5)*51);
                break;
            default:
                colour = Color.rgb(0, 0, 0);
                break;
        }
        return colour;
    }

    private int calcRoundScore(int maxScore, int counterG){
        int calcScore;
        if(counterG>maxScore){
            calcScore=0;
        }else{
            calcScore= (int) Math.sqrt(Math.pow(maxScore,2)-Math.pow(counterG,2));
        }
        return calcScore;

    }
}
