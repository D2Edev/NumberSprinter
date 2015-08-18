package org.home.d2e.numbersprinter;


import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.app.Fragment;
import android.os.Vibrator;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import org.home.d2e.numbersprinter.Core.DBHelper;
import org.home.d2e.numbersprinter.Core.DataRetainFragment;
import org.home.d2e.numbersprinter.Core.OnBackPressedListener;
import org.home.d2e.numbersprinter.Core.OnFragmentListener;
import org.home.d2e.numbersprinter.Core.Person;
import org.home.d2e.numbersprinter.Core.PrefKeys;
import org.home.d2e.numbersprinter.Core.UserTable;


/**
 * A simple {@link Fragment} subclass.
 */
public class GameOverFragment extends Fragment implements View.OnClickListener, OnBackPressedListener {

    private DBHelper dbHelper;
    private SQLiteDatabase db;
    private Person person;
    private Cursor cursor;
    private DataRetainFragment dataRetainFragment;
    private int maxScore = 0;
    private TextView tvTotalScore;
    private TextView tvTime;
    private TextView tvScoreHeader;
    private TextView tvScore;
    private TextView tvAvgScore;
    private TextView tvTtlGamesPlayed;
    private TextView tvRoundOver;
    private Button btnNewGame;
    Vibrator vibrator;
    boolean doVibrate = false;

    private final String TAG = "TAG_GameOverFragment_ ";
    OnFragmentListener listener;

    public GameOverFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        listener = (OnFragmentListener) activity;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_game_over, container, false);
    }

    @Override
    public void onResume() {
        ((MainActivity) getActivity()).setOnBackPressedListener((OnBackPressedListener) this);
        super.onResume();
    }

    @Override
    public void onPause() {
        ((MainActivity) getActivity()).setOnBackPressedListener(null);
        super.onPause();
    }


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        vibrator = (Vibrator) getActivity().getBaseContext().getSystemService(Context.VIBRATOR_SERVICE);
        tvTotalScore = (TextView) view.findViewById(R.id.tvTotalScoreGO);
        tvTime = (TextView) view.findViewById(R.id.tvTimeGO);
        tvScoreHeader = (TextView) view.findViewById(R.id.tvScoreHeaderGO);
        tvScore = (TextView) view.findViewById(R.id.tvScoreGO);
        tvAvgScore = (TextView) view.findViewById(R.id.tvAverageScore);
        tvTtlGamesPlayed = (TextView) view.findViewById(R.id.tvTtlGamesPlayed);
        tvRoundOver = (TextView) view.findViewById(R.id.tvRoundOverGO);
        btnNewGame = (Button) view.findViewById(R.id.btnNewGame);
        btnNewGame.setOnClickListener(GameOverFragment.this);

        dataRetainFragment = (DataRetainFragment) getFragmentManager().findFragmentByTag(MainActivity.RETAIN_FRAGMENT_TAG);

        if (dataRetainFragment != null) {
            person = dataRetainFragment.getPerson();
            dataRetainFragment.setCurrFragTag(MainActivity.GAME_OVER_FRAGMENT_TAG);
            tvRoundOver.setText(person.getName() + ", " + getString(R.string.tRoundOver));
            person.setScoreTotal(person.getScoreTotal()+person.getScoreMax());
            tvTotalScore.setText(getString(R.string.tTotalScore) + person.getScoreTotal());
            tvTime.setText(getString(R.string.tElapsedTime) + timeNumToText(person.getRoundTime()));
            tvTtlGamesPlayed.setText(getString(R.string.tGamesPlayed) + Integer.toString(person.getGamesPlayed()));
            tvAvgScore.setText(getString(R.string.tAverageScore) + Float.toString((float) (person.getScoreTotal() / person.getGamesPlayed())));
        }

        if (person != null) {
            dbHelper = new DBHelper(getActivity());
            db = dbHelper.getReadableDatabase();
            cursor = db.query(UserTable.TABLE, null, UserTable.Columns.NAME + " = ?", new String[]{person.getName()}, null, null, null);

            if (cursor.getCount() > 0) {
                cursor.moveToFirst();
                maxScore = cursor.getInt(cursor.getColumnIndex(UserTable.Columns.SCORE_MAX));
            }


            if (maxScore < person.getScoreMax()) {
                maxScore = person.getScoreMax();
                tvScoreHeader.setText(getString(R.string.tScoreRecord));
            } else {
                tvScoreHeader.setText(getString(R.string.tScore));
            }

            db.close();

        }
        tvScore.setText(Integer.toString(person.getScoreMax()));

        if (dataRetainFragment != null) {
            dataRetainFragment.setPerson(person);
        }


    }

    private void saveUserToDB() {
        db = dbHelper.getWritableDatabase();
        ContentValues cv = new ContentValues();
        //cv.put(UserTable.Columns.NAME, person.getName());
        //cv.put(UserTable.Columns.PASSWORD, person.getRoundTime());
        cv.put(UserTable.Columns.SCORE_TOTAL, person.getScoreTotal());
        cv.put(UserTable.Columns.SCORE_MAX, person.getScoreMax());
        cv.put(UserTable.Columns.GAMES_PLAYED, person.getGamesPlayed());
        //db.insert(UserTable.TABLE, null, cv);
        db.update(UserTable.TABLE, cv, UserTable.Columns.NAME + " = ?", new String[]{person.getName()});
        db.close();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnNewGame:
                doVibrate(isVibraEnabled());
                person.setScoreMax(maxScore);
                saveUserToDB();
                listener.startGameFragment();
                break;
        }

    }

    private String timeNumToText(int num) {
        String timeNumToText;
        int m;
        int s;
        int ms;
        m = num / 600;
        timeNumToText = " " + Integer.toString(m) + "'";
        s = (num - m * 600) / 10;
        timeNumToText = timeNumToText + Integer.toString(s) + ".";

        ms = num - m * 600 - s * 10;
        timeNumToText = timeNumToText + ms + "\"";

        return timeNumToText;
    }

    private boolean isVibraEnabled() {


        boolean enb = PreferenceManager.getDefaultSharedPreferences(getActivity()).getBoolean(PrefKeys.VIBRATE, false);
        return enb;

    }

    private void doVibrate(boolean doVibrate) {

        vibrator.vibrate(PrefKeys.VIB_LENGTH);
    }


    @Override
    public void backIsPressed() {
        person.setScoreMax(maxScore);
        saveUserToDB();

    }
}
