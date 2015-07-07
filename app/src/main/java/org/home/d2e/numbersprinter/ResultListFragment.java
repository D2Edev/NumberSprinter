package org.home.d2e.numbersprinter;

import android.app.Activity;
import android.app.Fragment;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.Toast;

import org.home.d2e.numbersprinter.Core.DBHelper;
import org.home.d2e.numbersprinter.Core.Person;
import org.home.d2e.numbersprinter.Core.ScoreComparator;
import org.home.d2e.numbersprinter.Core.UserTable;
import org.home.d2e.numbersprinter.adapter.MyListNameScoreAdapter;
import org.home.d2e.numbersprinter.adapter.UserCursorAdapter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class ResultListFragment extends Fragment {
    private final String TAG = "TAG_ResultListFragemnt ";
    private ListView lvAchievers;
    private List<Person> persons;
    private Person person;
    private DBHelper dbHelper;
    private SQLiteDatabase db;
    private Cursor userListCursor;
    private UserCursorAdapter userCursorAdapter;

    public ResultListFragment() {
        // Required empty public constructor
    }


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        persons = new ArrayList<>();
        persons.add(new Person("Cat","Cat", 45,45,1));
        persons.add(new Person("Voivod", "Cat", 22,22,1));
        persons.add(new Person("James", "Cat", 47,47,1));
        persons.add(new Person("God", "Cat", 118,119,27));
        persons.add(new Person("Slayer", "Cat", 117,118,12));
        persons.add(new Person("Saddam", "Cat", 24,24,1));
        persons.add(new Person("Horse", "Cat", 87,87,1));
        persons.add(new Person("Jack", "Cat", 62,1119,2));
        persons.add(new Person("Johnny", "Cat", 65,65,1));
        persons.add(new Person("Dima", "Cat", 38,38,1));
        persons.add(new Person("Chippy", "Cat", 83,83,1));
        persons.add(new Person("Sad", "Cat", 108,45,23));
        persons.add(new Person("George", "Cat", 33,33,1));
        persons.add(new Person("Happy", "Cat", 48,48,1));
        //Collections.sort(persons, new ScoreComparator());
        //Collections.reverse(persons);
        dbHelper = new DBHelper(getActivity());
        db = dbHelper.getReadableDatabase();
        userListCursor=db.query(UserTable.TABLE,null,null,null,null,null,UserTable.Columns.SCORE_TOTAL+" DESC;");
        userCursorAdapter = new UserCursorAdapter(getActivity(),userListCursor);

        lvAchievers = (ListView) view.findViewById(R.id.lvAchievers);
        // создаем адаптер
        BaseAdapter baseAdapter = new MyListNameScoreAdapter(view.getContext(), persons);
        //ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_list_item_1, imena);
        // присваиваем адаптер списку
        lvAchievers.setAdapter(userCursorAdapter);
        lvAchievers.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                person = (Person) parent.getItemAtPosition(position);
                Toast.makeText(parent.getContext(), "Click to " + person.getName(), Toast.LENGTH_SHORT).show();
                if (person.getName()=="God"){dbStub();}
            }
        });

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_result_list, container, false);

    }


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

    }

    @Override
    public void onDetach() {
        super.onDetach();
    }


    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */

    private void dbStub(){

        db = dbHelper.getWritableDatabase();
        for (Person person : persons) {
            ContentValues cv = new ContentValues();
            cv.put(UserTable.Columns.NAME,person.getName());
            cv.put(UserTable.Columns.PASSWORD,person.getPassword());
            cv.put(UserTable.Columns.SCORE_TOTAL, person.getScoreTotal());
            cv.put(UserTable.Columns.SCORE_LAST, person.getScoreLast());
            cv.put(UserTable.Columns.GAMES_PLAYED, person.getGamesPlayed());
            db.insert(UserTable.TABLE,null,cv);
        }
        db.close();
    }

}
