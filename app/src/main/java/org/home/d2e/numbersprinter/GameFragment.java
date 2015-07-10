package org.home.d2e.numbersprinter;

import android.app.Activity;
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
import org.home.d2e.numbersprinter.Core.Person;
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
        tvPlayer = (TextView) view.findViewById(R.id.tvPlayer);
        tvPlayer.setText(getString(R.string.tCurrentPlayer) + " " + person.getName());
        gvGameField = (GridView) view.findViewById(R.id.gvGameField);
        gvGameField.setAdapter(new GameFieldAdapter(view.getContext(), gameFields));
        //gvGameField.setAdapter(new ArrayAdapter<Integer>(view.getContext(),R.layout.item_grid_element, gameFields));
        gvGameField.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                gameField = (GameField) parent.getItemAtPosition(position);
                Toast.makeText(parent.getContext(), "Click to " + gameField.getFieldNumber(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public void onDetach() {
        super.onDetach();
        Log.d(TAG, "onDetach");

    }


}