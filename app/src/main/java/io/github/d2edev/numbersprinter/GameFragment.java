package io.github.d2edev.numbersprinter;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.graphics.Color;
import android.os.Bundle;
import android.app.Fragment;
import android.os.IBinder;
import android.os.Vibrator;
import android.preference.PreferenceManager;
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
import io.github.d2edev.numbersprinter.Core.MyApp;
import io.github.d2edev.numbersprinter.Core.OnBackPressedListener;
import io.github.d2edev.numbersprinter.Core.OnFragmentListener;
import io.github.d2edev.numbersprinter.Core.OnTickListener;
import io.github.d2edev.numbersprinter.Core.Person;
import io.github.d2edev.numbersprinter.Core.PrefKeys;
import io.github.d2edev.numbersprinter.Core.TickerService;
import io.github.d2edev.numbersprinter.adapter.GameFieldAdapter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class GameFragment extends Fragment implements OnBackPressedListener, AdapterView.OnItemClickListener, OnTickListener {
    private final String TAG = "TAG_GameFragment ";
    private GridView gvGameField;
    private List<GameField> gameFields;
    private GameField gameField;
    private DataRetainFragment dataRetainFragment;
    private Person person;
    private TextView tvPlayer;
    private TextView tvElapsedTime;
    private int msTimeCounter;
    private int nextNumberToClick;
    private final int BLACK = 0;
    private final int WHITE = 1;
    private final int RANDOM = 2;
    private final int RANDOM_LOW = 3;
    private final int RANDOM_HIGH = 4;
    private int mxSize;
    private int unitSize;

    private OnFragmentListener onFragmentListener;
    private TickerService tickerService;
    boolean isBound;
    boolean saveData;
    boolean isTickerON;
    private int maxScore;
    private Vibrator vibrator;
    private GameFieldAdapter gfAdapter;

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
        onFragmentListener = (OnFragmentListener) getActivity();
        //init  controls
        tvPlayer = (TextView) view.findViewById(R.id.tvPlayer);
        tvElapsedTime = (TextView) view.findViewById(R.id.tvElapsedTime);
        tvElapsedTime.setTextSize(TypedValue.COMPLEX_UNIT_PX, ((MainActivity) getActivity()).currSideLimit() / 5);
        gvGameField = (GridView) view.findViewById(R.id.gvGameField);
        mxSize = getMatrixSize();
        gvGameField.setNumColumns(mxSize);
        gameFields = getGameFields();
        unitSize = ((MainActivity) getActivity()).currSideLimit() / (3 * (3 * mxSize + 1));
        gfAdapter = new GameFieldAdapter(view.getContext(), gameFields, unitSize);
        gvGameField.setAdapter(gfAdapter);
        //set listener on gridview
        gvGameField.setOnItemClickListener(this);
    }

    @Override
    public void onResume() {


        //set interface from main activity - informing fragment Back was pressed
        ((MainActivity) getActivity()).setOnBackPressedListener(this);


        if (dataRetainFragment != null) {
            //will save data if fragment closed? default=true
            saveData = true;
            //extract current person
            person = dataRetainFragment.getPerson();
            tvPlayer.setText(getString(R.string.tPlayer) + " " + person.getName());
            dataRetainFragment.setCurrFragTag(MainActivity.GAME_FRAGMENT_TAG);
            // check if previously counter was used
            if (dataRetainFragment.getNextNumberToClick() > 1) {
                nextNumberToClick = dataRetainFragment.getNextNumberToClick();
            } else {
                nextNumberToClick = 1;
            }
            //defime max possible score for game based on complexity
            maxScore = (int) Math.pow(2, mxSize) * 10;
            if (dataRetainFragment.getHardMode()) {
                maxScore = (int) (maxScore * 1.5);
            }
            //retrieve counter value
            msTimeCounter =dataRetainFragment.getMsTimeCounter();
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
            ((MyApp) getActivity().getApplication()).setTickListener(this);
        }
//        LocalBroadcastManager.getInstance(getActivity()).registerReceiver(tickReceiver, new IntentFilter("new_tick"));

        super.onResume();

    }

    @Override
    public void onPause() {
//        release listener for Back pressed
        ((MainActivity) getActivity()).setOnBackPressedListener(null);
//        LocalBroadcastManager.getInstance(getActivity()).unregisterReceiver(tickReceiver);
//        release Tick listener
        ((MyApp) getActivity().getApplication()).setTickListener(null);
        this.getActivity().unbindService(connection);
        if (dataRetainFragment != null) {
            dataRetainFragment.setTickerON(isTickerON);
            //save game fields data
            dataRetainFragment.setGameFields(gameFields);
            //save counter data in fragment
            dataRetainFragment.setNextNumberToClick(nextNumberToClick);
            //save person data to retain fragment
            dataRetainFragment.setPerson(person);
            //
            dataRetainFragment.setMsTimeCounter(msTimeCounter);
        }
        super.onPause();
    }


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
        nextNumberToClick = 1;
        //reset time counter
        msTimeCounter=0;


        //onFragmentListener.startGameOverFragment();


    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        //get clicked game field data
        gameField = (GameField) parent.getItemAtPosition(position);
        //check if correct game field is pressed
        Log.d(TAG, "counter " + nextNumberToClick + " field " + gameField.getFieldNumber());
        if (gameField.getFieldNumber() == nextNumberToClick) {
            //if condition ok - black the field
            gameField.setFieldColor(Color.rgb(0, 0, 0));
            gameField.setFieldTextColor(Color.rgb(0, 0, 0));
            //insert updated field to data set
            gameFields.set(position, gameField);
            //update adapter
            gfAdapter.notifyDataSetChanged();
            doVibrate(isVibraEnabled());
            //if the field is last
            if (nextNumberToClick == mxSize * mxSize) {
                //send STOP to chrono
                tickerService.stopTick();
                //set flag ticker is stopped
                isTickerON = false;
                //clear game fields data
                gameFields = null;
                //clear counter data
                nextNumberToClick = 1;
                doVibrate(isVibraEnabled());
                //increase number of games played
                person.setGamesPlayed(person.getGamesPlayed() + 1);
                //update calculated scores for person
                person.setScoreMax(calcRoundScore(maxScore, msTimeCounter));
                person.setRoundTime(msTimeCounter);
                //call GameOver fragment
                onFragmentListener.startGameOverFragment();
                return;
            }
            //move to next field
            nextNumberToClick++;
        }
    }


    private List<GameField> getGameFields() {
        List<GameField> tempGFs = new ArrayList<>();
        //restore GameField from DataRetainFragment if exists
        if (dataRetainFragment != null) {
            if (dataRetainFragment.getGameFields() == null) {
                if (dataRetainFragment.getHardMode()) {
                    //generate random colored fields
                    for (int i = 1; i < mxSize * mxSize + 1; i++) {
                        int colorBck = generateRGB(RANDOM);
                        int colorFore = generateRGB(RANDOM);
                        while (colorFore == colorBck) {
                            colorFore = generateRGB(RANDOM);
                        }
                        ;
                        tempGFs.add(new GameField(i, colorBck, colorFore));
                    }
                } else {
                    for (int i = 1; i < mxSize * mxSize + 1; i++) {
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

//    private BroadcastReceiver tickReceiver = new BroadcastReceiver() {
//        @Override
//        public void onReceive(Context context, Intent intent) {
//            msTimeCounter = intent.getIntExtra("counter", 1);
//            if (getActivity() != null) {
//                getActivity().runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() {
//                      tvElapsedTime.setText(timeNumToText(msTimeCounter));
//                    }
//                });
//            }
//        }
//    };

    private boolean isVibraEnabled() {
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
//              use "safe" colors
            case RANDOM:
                colour = Color.rgb(51 + (int) (Math.random() * 5) * 51, 51 + (int) (Math.random() * 5) * 51, 51 + (int) (Math.random() * 5) * 51);
                break;
            case RANDOM_LOW:
                colour = Color.rgb((int) (Math.random() * 3) * 51, (int) (Math.random() * 3) * 51, (int) (Math.random() * 3) * 51);
                break;
            case RANDOM_HIGH:
                colour = Color.rgb(153 + (int) (Math.random() * 3) * 51, 153 + (int) (Math.random() * 3) * 51, 153 + (int) (Math.random() * 3) * 51);
                break;
            default:
                colour = Color.rgb(0, 0, 0);
                break;
        }
        return colour;
    }

    private int calcRoundScore(int maxScore, int counterG) {
        int calcScore;
        if (counterG > maxScore) {
            calcScore = 0;
        } else {
            calcScore = (int) Math.sqrt(Math.pow(maxScore, 2) - Math.pow(counterG, 2));
        }
        return calcScore;

    }

    @Override
    public void nextTick() {
        msTimeCounter++;
        if (getActivity() != null) {
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    tvElapsedTime.setText(timeNumToText(msTimeCounter));
                }
            });
        }
    }
}
