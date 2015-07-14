package org.home.d2e.numbersprinter;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import org.home.d2e.numbersprinter.Core.GameField;
import org.home.d2e.numbersprinter.Core.DataRetainFragment;
import org.home.d2e.numbersprinter.Core.MyApp;
import org.home.d2e.numbersprinter.Core.Person;
import org.home.d2e.numbersprinter.Core.TickerService;
import org.home.d2e.numbersprinter.adapter.GameFieldAdapter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class GameFragment extends Fragment {
    private final String TAG = "TAG_GameGridFragment ";
    private GridView gvGameField;
    private List<GameField> gameFields;
    private GameField gameField;
    private int clr_r;
    private int clr_g;
    private int clr_b;
    private DataRetainFragment dataRetainFragment;
    private Person person;
    private TextView tvPlayer;
    private TextView tvElapsedTime;
    private Intent intentStartTickerService;
    MyApp.OnTickModeListener onTickModeListener;
    private int fieldCounter;


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
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        Log.d(TAG, "onAttach");
        //restore GameField from DataRetainFragment
        gameFields = new ArrayList<>();
        dataRetainFragment = (DataRetainFragment) getFragmentManager().findFragmentByTag("retain");
        if (dataRetainFragment == null) {

        } else {
            if (dataRetainFragment.getGameFields() == null) {
                for (int i = 1; i < 26; i++) {
                    clr_r = (int) (Math.random() * 235 + 20);
                    clr_g = (int) (Math.random() * 235 + 20);
                    clr_b = (int) (Math.random() * 235 + 20);
                    gameFields.add(new GameField(i, Color.rgb(clr_r, clr_g, clr_b)));
                    Log.d(TAG, "" + clr_r + " " + clr_g + " " + clr_b + " " + Color.rgb(clr_r, clr_g, clr_b));
                }
                Collections.shuffle(gameFields);
                dataRetainFragment.setGameFields(gameFields);
            } else {
                gameFields = dataRetainFragment.getGameFields();
            }
            person = dataRetainFragment.getPerson();
        }

    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Log.d(TAG, "onViewCreated");
        //init variables and controls
        tvPlayer = (TextView) view.findViewById(R.id.tvPlayer);
        tvPlayer.setText(getString(R.string.tCurrentPlayer) + " " + person.getName());
        tvElapsedTime = (TextView) view.findViewById(R.id.tvElapsedTime);
        gvGameField = (GridView) view.findViewById(R.id.gvGameField);
        gvGameField.setAdapter(new GameFieldAdapter(view.getContext(), gameFields));
        intentStartTickerService = new Intent(getActivity().getApplication(), TickerService.class);
        getActivity().startService(intentStartTickerService);
        fieldCounter = 1;
        //call time refresh in textview
        refreshTimeField();

//set listener on gridview
        gvGameField.setOnItemClickListener(new AdapterView.OnItemClickListener() {
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
                    if (fieldCounter == 25) {
                        //if all fields processed - send STOP to chrono
                        onTickModeListener = ((MyApp) getActivity().getApplication()).getOnTickModeListener();
                        onTickModeListener.setTickMode(true, true);
                    }


                }
            }
        });


    }

    @Override
    public void onDetach() {
        super.onDetach();
        Log.d(TAG, "onDetach");

    }

    @Override
    public void onPause() {
        super.onPause();
        //if()
        onTickModeListener = ((MyApp) getActivity().getApplication()).getOnTickModeListener();
        onTickModeListener.setTickMode(true, true);

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.d(TAG, "onDestroyView");

    }

    private void refreshTimeField() {
        ((MyApp) getActivity().getApplication()).setOnTickListener(new MyApp.OnTickListener() {
            @Override
            public void onNextTick(final int counter) {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (tvElapsedTime != null) {
                            tvElapsedTime.setText(getString(R.string.tElapsedTime) + counter);
                        }
                    }
                });

            }
        });
    }
}
