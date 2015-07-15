package org.home.d2e.numbersprinter;


import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.home.d2e.numbersprinter.Core.DBHelper;
import org.home.d2e.numbersprinter.Core.DataRetainFragment;
import org.home.d2e.numbersprinter.Core.OnFragmentListener;
import org.home.d2e.numbersprinter.Core.Person;
import org.home.d2e.numbersprinter.Core.UserTable;


/**
 * A simple {@link Fragment} subclass.
 */
public class GameOverFragment extends Fragment {

    private DBHelper dbHelper;
    private SQLiteDatabase db;
    private Person person;
    private Cursor cursor;
    private DataRetainFragment dataRetainFragment;
    private int max_score;
    TextView tvPlayer;
    TextView tvTime;
    TextView tvScoreHeader;
    TextView tvScore;


    OnFragmentListener listener;

    public GameOverFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_game_over, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        tvPlayer = (TextView) view.findViewById(R.id.tvPlayerGO);
        tvTime = (TextView) view.findViewById(R.id.tvTimeGO);
        tvScoreHeader = (TextView) view.findViewById(R.id.tvScoreHeaderGO);
        tvScore = (TextView) view.findViewById(R.id.tvScoreGO);
        dataRetainFragment = (DataRetainFragment) getFragmentManager().findFragmentByTag("retain");

        if (dataRetainFragment != null) {
            person = dataRetainFragment.getPerson();
        }

        tvPlayer.setText(person.getName());
        tvTime.setText(Integer.toString(600 - person.getScoreMax()));
        tvScore.setText(Integer.toString(person.getScoreMax()));

        if (person != null) {
            dbHelper = new DBHelper(getActivity());
            db = dbHelper.getReadableDatabase();

            cursor = db.query(UserTable.TABLE, null, UserTable.Columns.NAME + " = ?",new String[]{person.getName()} , null, null, null);

            if (cursor.getCount() > 0) {
                max_score = cursor.getInt(cursor.getColumnIndex(UserTable.Columns.SCORE_MAX));
            }
            if (max_score < person.getScoreMax()) {
                max_score = person.getScoreMax();
                tvScoreHeader.setText(getString(R.string.tScoreRecord));
            } else {
                tvScoreHeader.setText(getString(R.string.tScore));
            }
            person.setScoreMax(max_score);
            db.close();
            //saveUserToDB();
        }

        if (dataRetainFragment != null) {
            dataRetainFragment.setPerson(person);
        }


    }

    private void saveUserToDB() {
        db = dbHelper.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(UserTable.Columns.NAME, person.getName());
        cv.put(UserTable.Columns.PASSWORD, person.getPassword());
        cv.put(UserTable.Columns.SCORE_TOTAL, 0);
        cv.put(UserTable.Columns.SCORE_MAX, 0);
        cv.put(UserTable.Columns.GAMES_PLAYED, 0);
        //db.insert(UserTable.TABLE, null, cv);
        db.replace(UserTable.TABLE, UserTable.Columns.NAME + "=" + person.getName(), cv);
        db.close();
    }
}
