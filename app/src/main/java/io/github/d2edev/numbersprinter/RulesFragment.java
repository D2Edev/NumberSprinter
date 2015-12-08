package io.github.d2edev.numbersprinter;

import android.app.Activity;
import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.app.Fragment;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import io.github.d2edev.numbersprinter.R;

import io.github.d2edev.numbersprinter.Core.DBHelper;
import io.github.d2edev.numbersprinter.Core.DataRetainFragment;
import io.github.d2edev.numbersprinter.Core.Person;
import io.github.d2edev.numbersprinter.Core.UserTable;

import java.util.ArrayList;
import java.util.List;


public class RulesFragment extends Fragment implements View.OnClickListener {

    private ImageView ivLogo;
    private TextView tvRules;
    private DBHelper dbHelper;
    private SQLiteDatabase db;
    DataRetainFragment dataRetainFragment;
    private final static int SIZE_DIVIDER=20;

    private int unitSize;

    public RulesFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_rules, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        unitSize=((MainActivity)getActivity()).currSideLimit();
        tvRules = (TextView) view.findViewById(R.id.tvRules);
        tvRules.setTextSize(TypedValue.COMPLEX_UNIT_PX,unitSize/SIZE_DIVIDER);
        ivLogo = (ImageView) view.findViewById(R.id.ivLogo);

        ivLogo.setOnClickListener(RulesFragment.this);
        tvRules.setOnClickListener(RulesFragment.this);
        dataRetainFragment = (DataRetainFragment) getFragmentManager().findFragmentByTag(MainActivity.RETAIN_FRAGMENT_TAG);
        if (dataRetainFragment != null) {
            dataRetainFragment.setCurrFragTag(MainActivity.RULES_FRAGMENT_TAG);
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

    }

    @Override
    public void onDetach() {
        super.onDetach();

    }


    @Override
    public void onClick(View v) {
        if (BuildConfig.DEBUG) {
            switch (v.getId()) {

                case R.id.ivLogo:
                    dbHelper = new DBHelper(getActivity());
                    db = dbHelper.getWritableDatabase();
                    db.execSQL("DELETE FROM " + UserTable.TABLE);
                    db.close();
                    Toast.makeText(getActivity(), "Records deleted!", Toast.LENGTH_SHORT).show();
                    break;
                case R.id.tvRules:
                    dbStub();
                    Toast.makeText(getActivity(), "Stub generated!", Toast.LENGTH_SHORT).show();
                    break;
            }
        }

    }

    private void dbStub() {
        List<Person> persons;
        persons = new ArrayList<>();
        persons.add(new Person("Cat", 0, 45, 45, 1));
        persons.add(new Person("Voivod", 0, 22, 22, 1));
        persons.add(new Person("James", 0, 47, 47, 1));
        persons.add(new Person("God", 0, 118, 119, 27));
        persons.add(new Person("Slayer", 0, 117, 118, 12));
        persons.add(new Person("Saddam", 0, 24, 24, 1));
        persons.add(new Person("Horse", 0, 87, 87, 1));
        persons.add(new Person("Jack", 0, 62, 119, 2));
        persons.add(new Person("Johnny", 0, 65, 65, 1));
        persons.add(new Person("Dima", 0, 38, 38, 1));
        persons.add(new Person("Chippy", 0, 83, 83, 1));
        persons.add(new Person("Sad", 0, 108, 45, 23));
        persons.add(new Person("George",0, 33, 33, 1));
        persons.add(new Person("Happy", 0, 48, 48, 1));
        dbHelper = new DBHelper(getActivity());
        db = dbHelper.getWritableDatabase();
        for (Person person : persons) {
            ContentValues cv = new ContentValues();
            cv.put(UserTable.Columns.NAME, person.getName());
            cv.put(UserTable.Columns.PASSWORD, person.getRoundTime());
            cv.put(UserTable.Columns.SCORE_TOTAL, person.getScoreTotal());
            cv.put(UserTable.Columns.SCORE_MAX, person.getScoreMax());
            cv.put(UserTable.Columns.GAMES_PLAYED, person.getGamesPlayed());
            db.insert(UserTable.TABLE, null, cv);
        }
        db.close();
    }
}

