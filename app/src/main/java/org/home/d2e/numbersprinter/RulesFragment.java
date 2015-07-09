package org.home.d2e.numbersprinter;

import android.app.Activity;
import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.home.d2e.numbersprinter.Core.DBHelper;
import org.home.d2e.numbersprinter.Core.Person;
import org.home.d2e.numbersprinter.Core.UserTable;

import java.util.ArrayList;
import java.util.List;


public class RulesFragment extends Fragment implements View.OnClickListener {

    private ImageView ivLogo;
    private TextView tvRules;
    private DBHelper dbHelper;
    private SQLiteDatabase db;

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
        persons.add(new Person("Cat", "Cat", 45, 45, 1));
        persons.add(new Person("Voivod", "Cat", 22, 22, 1));
        persons.add(new Person("James", "Cat", 47, 47, 1));
        persons.add(new Person("God", "Cat", 118, 119, 27));
        persons.add(new Person("Slayer", "Cat", 117, 118, 12));
        persons.add(new Person("Saddam", "Cat", 24, 24, 1));
        persons.add(new Person("Horse", "Cat", 87, 87, 1));
        persons.add(new Person("Jack", "Cat", 62, 1119, 2));
        persons.add(new Person("Johnny", "Cat", 65, 65, 1));
        persons.add(new Person("Dima", "Cat", 38, 38, 1));
        persons.add(new Person("Chippy", "Cat", 83, 83, 1));
        persons.add(new Person("Sad", "Cat", 108, 45, 23));
        persons.add(new Person("George", "Cat", 33, 33, 1));
        persons.add(new Person("Happy", "Cat", 48, 48, 1));
        dbHelper = new DBHelper(getActivity());
        db = dbHelper.getWritableDatabase();
        for (Person person : persons) {
            ContentValues cv = new ContentValues();
            cv.put(UserTable.Columns.NAME, person.getName());
            cv.put(UserTable.Columns.PASSWORD, person.getPassword());
            cv.put(UserTable.Columns.SCORE_TOTAL, person.getScoreTotal());
            cv.put(UserTable.Columns.SCORE_LAST, person.getScoreLast());
            cv.put(UserTable.Columns.GAMES_PLAYED, person.getGamesPlayed());
            db.insert(UserTable.TABLE, null, cv);
        }
        db.close();
    }
}

