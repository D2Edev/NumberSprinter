package org.home.d2e.numbersprinter;

import android.app.Activity;
import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.home.d2e.numbersprinter.Core.DBHelper;
import org.home.d2e.numbersprinter.Core.DataRetainFragment;
import org.home.d2e.numbersprinter.Core.Person;
import org.home.d2e.numbersprinter.Core.UserTable;

import java.util.ArrayList;
import java.util.List;


public class RulesFragment extends Fragment implements View.OnClickListener {

    private ImageView ivLogo;
    private TextView tvRules;
    private DBHelper dbHelper;
    private SQLiteDatabase db;
    DataRetainFragment dataRetainFragment;

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
        ivLogo = (ImageView) view.findViewById(R.id.ivLogo);
        tvRules = (TextView) view.findViewById(R.id.tvRules);
        ivLogo.setOnClickListener(RulesFragment.this);
        tvRules.setOnClickListener(RulesFragment.this);
        dataRetainFragment = (DataRetainFragment) getFragmentManager().findFragmentByTag(MainActivity.RETAIN_FRAGMENT_TAG);
        if(dataRetainFragment!=null){
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
        dbHelper = new DBHelper(getActivity());
        switch (v.getId()) {
            case R.id.ivLogo:
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

    private void dbStub() {
        List<Person> persons;
        persons = new ArrayList<>();
        persons.add(new Person("Cat", "gggggg".hashCode(), 45, 45, 1));
        persons.add(new Person("Voivod", "gggggg".hashCode(), 22, 22, 1));
        persons.add(new Person("James", "gggggg".hashCode(), 47, 47, 1));
        persons.add(new Person("God", "gggggg".hashCode(), 118, 119, 27));
        persons.add(new Person("Slayer", "gggggg".hashCode(), 117, 118, 12));
        persons.add(new Person("Saddam", "gggggg".hashCode(), 24, 24, 1));
        persons.add(new Person("Horse", "gggggg".hashCode(), 87, 87, 1));
        persons.add(new Person("Jack", "gggggg".hashCode(), 62, 119, 2));
        persons.add(new Person("Johnny", "gggggg".hashCode(), 65, 65, 1));
        persons.add(new Person("Dima", "gggggg".hashCode(), 38, 38, 1));
        persons.add(new Person("Chippy", "gggggg".hashCode(), 83, 83, 1));
        persons.add(new Person("Sad", "gggggg".hashCode(), 108, 45, 23));
        persons.add(new Person("George", "gggggg".hashCode(), 33, 33, 1));
        persons.add(new Person("Happy", "gggggg".hashCode(), 48, 48, 1));
        dbHelper = new DBHelper(getActivity());
        db = dbHelper.getWritableDatabase();
        for (Person person : persons) {
            ContentValues cv = new ContentValues();
            cv.put(UserTable.Columns.NAME, person.getName());
            cv.put(UserTable.Columns.PASSWORD, person.getPassword());
            cv.put(UserTable.Columns.SCORE_TOTAL, person.getScoreTotal());
            cv.put(UserTable.Columns.SCORE_MAX, person.getScoreMax());
            cv.put(UserTable.Columns.GAMES_PLAYED, person.getGamesPlayed());
            db.insert(UserTable.TABLE, null, cv);
        }
        db.close();
    }
}

