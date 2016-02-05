package io.github.d2edev.numbersprinter;


import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.app.Fragment;
import android.os.Vibrator;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.TextView;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import io.github.d2edev.numbersprinter.Core.DBHelper;
import io.github.d2edev.numbersprinter.Core.DataRetainFragment;
import io.github.d2edev.numbersprinter.Core.OnFragmentListener;
import io.github.d2edev.numbersprinter.Core.Person;
import io.github.d2edev.numbersprinter.Core.PrefKeys;
import io.github.d2edev.numbersprinter.Core.UserTable;
import io.github.d2edev.numbersprinter.adapter.MyListNameScoreAdapter;
import io.github.d2edev.numbersprinter.adapter.UserCursorAdapter;


/**
 * A simple {@link Fragment} subclass.
 */
public class LoginFragment extends Fragment{
    private static final String TAG = "TAG_LogintFragment_";
    private ListView lvPlayers;
    private Button btnOK;
    private Button btnNew;
    private Person person;
    private List<Person> persons;
    private DataRetainFragment dataRetainFragment;
    private CheckBox cbHard;
    private Vibrator vibrator;

    OnFragmentListener listener;

    public LoginFragment() {
        // Required empty public constructor
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        vibrator = (Vibrator) getActivity().getBaseContext().getSystemService(Context.VIBRATOR_SERVICE);
        btnOK = (Button) view.findViewById(R.id.btnPlayerOK);
        btnOK.setEnabled(false);
        btnNew = (Button) view.findViewById(R.id.btnPlayerNew);
        btnOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                retainUserData();
                listener.startGameFragment();
            }
        });
        btnNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.startSignUpFragment();
            }
        });
        cbHard = (CheckBox) view.findViewById(R.id.cbHard);
        dataRetainFragment = (DataRetainFragment) getFragmentManager().findFragmentByTag(MainActivity.RETAIN_FRAGMENT_TAG);
        if (dataRetainFragment != null) {
            cbHard.setChecked(dataRetainFragment.getHardMode());
            dataRetainFragment.setCurrFragTag(MainActivity.LOGIN_FRAGMENT_TAG);
            if (dataRetainFragment.getPersons() != null) {
                persons = dataRetainFragment.getPersons();
                Collections.sort(persons, new Comparator<Person>() {
                    @Override
                    public int compare(Person first, Person second) {
                        return first.getName().compareTo(second.getName());
                    }
                });
                final MyListNameScoreAdapter adapter = new MyListNameScoreAdapter(getActivity().getBaseContext(), persons);
                lvPlayers = (ListView) view.findViewById(R.id.lvPlayers);
                lvPlayers.setAdapter(adapter);
                adapter.setSelectionColor(Color.BLUE);
                lvPlayers.setOnItemClickListener(new AdapterView.OnItemClickListener() {
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
                        person=adapter.getItem(position);
                        btnOK.setEnabled(true);
                        doVibrate(isVibraEnabled());
                    }
                });
            }


        }
        cbHard.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                dataRetainFragment.setHardMode(isChecked);
            }
        });


    }

    private void retainUserData() {
        if (dataRetainFragment != null) {
            dataRetainFragment.setPerson(person);
            dataRetainFragment.setHardMode(cbHard.isChecked());
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_login, container, false);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        // Log.d(TAG, "onAttach");
        if (activity instanceof OnFragmentListener) {
            listener = (OnFragmentListener) activity;

        } else {
            throw new IllegalStateException("Activity not OnMainFragmentListener! ");
        }

    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }



    private boolean isVibraEnabled() {

        //boolean enb=getActivity().getSharedPreferences(PrefKeys.NAME, Context.MODE_PRIVATE).getBoolean(PrefKeys.VIBRATE,false);
        boolean enb = PreferenceManager.getDefaultSharedPreferences(getActivity()).getBoolean(PrefKeys.VIBRATE, false);
        return enb;

    }

    private void doVibrate(boolean doVibrate) {
        if (doVibrate) {
            vibrator.vibrate(PrefKeys.VIB_LENGTH);
        }
    }
}

