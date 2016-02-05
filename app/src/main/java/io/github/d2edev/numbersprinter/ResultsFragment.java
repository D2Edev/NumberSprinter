package io.github.d2edev.numbersprinter;

import android.app.Activity;
import android.app.Fragment;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import io.github.d2edev.numbersprinter.Core.DataRetainFragment;
import io.github.d2edev.numbersprinter.Core.Person;
import io.github.d2edev.numbersprinter.adapter.MyListNameScoreAdapter;


public class ResultsFragment extends Fragment {
    private final String TAG = "TAG_ResultListFragemnt ";
    private ListView lvAchievers;
    private List<Person> persons;
    private DataRetainFragment dataRetainFragment;

    public ResultsFragment() {
        // Required empty public constructor
    }


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        dataRetainFragment = (DataRetainFragment) getFragmentManager().findFragmentByTag(MainActivity.RETAIN_FRAGMENT_TAG);
        if (dataRetainFragment != null) {
            dataRetainFragment.setCurrFragTag(MainActivity.RESULTS_FRAGMENT_TAG);
            if (dataRetainFragment.getPersons() != null) {
                persons = dataRetainFragment.getPersons();
                Collections.sort(persons, new Comparator<Person>() {
                    @Override
                    public int compare(Person first, Person second) {
                        return second.getScoreTotal() - first.getScoreTotal();
                    }
                });
                final MyListNameScoreAdapter adapter = new MyListNameScoreAdapter(getActivity().getBaseContext(), persons);
                adapter.setSelectionColor(Color.BLUE);
                lvAchievers = (ListView) getActivity().findViewById(R.id.lvAchievers);
                lvAchievers.setAdapter(adapter);
                lvAchievers.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        if (!adapter.isBckColorDEFINED()) {
                            adapter.setDefaultColor(((ColorDrawable) view.getBackground()).getColor());
                        }
                        if(adapter.getSelectedID()!=position){
                            if(adapter.getPrevView()!=null){
                                adapter.getPrevView().setBackgroundColor(adapter.getDefaultColor());
                            }
                            view.setBackgroundColor(adapter.getSelectionColor());
                            adapter.setSelectedID(position);
                            adapter.setPrevView(view);
                        }
                        Person person = persons.get(position);
                        Toast.makeText(getActivity().getBaseContext(),
                                getString(R.string.tPlayer)+ person.getName() + "\n"+
                                getString(R.string.tGamesPlayed) + person.getGamesPlayed()+"\n"+
                                getString(R.string.tMaxScore) + person.getScoreMax()
                                ,Toast.LENGTH_SHORT).show();

                    }
                });
            }
        }


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_results, container, false);

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
