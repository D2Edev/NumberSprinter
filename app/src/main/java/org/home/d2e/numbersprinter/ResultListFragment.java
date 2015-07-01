package org.home.d2e.numbersprinter;

import android.app.Activity;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.Toast;

import org.home.d2e.numbersprinter.Core.Person;
import org.home.d2e.numbersprinter.Core.ScoreComparator;
import org.home.d2e.numbersprinter.adapter.MyListAdapter;
import org.home.d2e.numbersprinter.adapter.MyListNameScoreAdapter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class ResultListFragment extends Fragment {
    private final String TAG = "TAG_ResultListFragemnt ";
    private ListView lvAchievers;
    private List<Person> persons;
    private Person person;



    public ResultListFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }



    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
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
        Collections.sort(persons, new ScoreComparator());
        Collections.reverse(persons);
        lvAchievers = (ListView) view.findViewById(R.id.lvAchievers);
        // создаем адаптер
        BaseAdapter baseAdapter=new MyListNameScoreAdapter(view.getContext(), persons);
        //ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_list_item_1, imena);
        // присваиваем адаптер списку

        lvAchievers.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                person = (Person) parent.getItemAtPosition(position);
                Toast.makeText(parent.getContext(), "Click to " + person.getName(), Toast.LENGTH_SHORT).show();
            }
        });
        lvAchievers.setAdapter(baseAdapter);
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


}
