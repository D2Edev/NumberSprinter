package org.home.d2e.numbersprinter;


import android.app.Activity;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.Toast;

import org.home.d2e.numbersprinter.Core.NameComparator;
import org.home.d2e.numbersprinter.Core.OnFragmentListener;
import org.home.d2e.numbersprinter.Core.Person;
import org.home.d2e.numbersprinter.adapter.MyListNameScoreAdapter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class LoginListFragment extends Fragment {
    private static final String TAG = "TAG_LoginListFragment_";
    OnFragmentListener listener;
    private List<Person> persons;
    private Person person;
    private ListView lvPlayerList;


    public LoginListFragment() {
        // Required empty public constructor
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Log.d(TAG,"onViewCreated");
        persons = new ArrayList<>();
        persons.add(new Person("Cat", 45));
        persons.add(new Person("Voivod", 22));
        persons.add(new Person("James", 47));
        persons.add(new Person("God", 5000));
        persons.add(new Person("Slayer", 1000));
        persons.add(new Person("Saddam", 24));
        persons.add(new Person("Horse", 87));
        persons.add(new Person("Jack", 122));
        persons.add(new Person("Johnny", 65));
        persons.add(new Person("Dima", 38));
        persons.add(new Person("Chippy", 83));
        persons.add(new Person("Sad", 345));
        persons.add(new Person("George", 122));
        persons.add(new Person("Happy", 129));
        Collections.sort(persons, new NameComparator());

        lvPlayerList = (ListView) view.findViewById(R.id.lvPlayers);
        // создаем адаптер

        BaseAdapter baseAdapter = new MyListNameScoreAdapter(view.getContext(), persons);
        // присваиваем адаптер списку
        lvPlayerList.setAdapter(baseAdapter);
        lvPlayerList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                person = (Person) parent.getItemAtPosition(position);
                Toast.makeText(parent.getContext(), "Click to " + person.getName(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_login_list, container, false);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        Log.d(TAG, "onAttach");
        if (activity instanceof OnFragmentListener) {

        } else {
            throw new IllegalStateException("Activity not OnMainFragmentListener! ");
        }

    }

    @Override
    public void onDetach() {
        super.onDetach();
    }
}
