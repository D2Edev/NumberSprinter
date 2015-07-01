package org.home.d2e.numbersprinter;

import android.app.Activity;
import android.os.Bundle;
import android.app.Fragment;
import android.text.Editable;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import org.home.d2e.numbersprinter.Core.Person;
import org.home.d2e.numbersprinter.adapter.MyListNameScoreAdapter;

import java.util.ArrayList;
import java.util.List;


public class LoginFragment extends Fragment implements View.OnClickListener {
    private static final String TAG = "TAG_LoginListFragment_";
    OnFragmentListener listener;
    private List<Person> persons;
    private Person person;

    private String[] names = {"Иван", "Марья", "Петр", "Антон", "Даша", "Борис",
            "Костя", "Игорь", "Анна", "Денис", "Андрей"};
    private ListView lvPlayerList;
    EditText etPass;
    Button btnOK;
    Button btnNew;

    public LoginFragment() {
        // Required empty public constructor
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
        lvPlayerList = (ListView) view.findViewById(R.id.lvPlayers);
        // создаем адаптер
        //ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, names);
        BaseAdapter baseAdapter = new MyListNameScoreAdapter(view.getContext(), persons);
        // присваиваем адаптер списку
        lvPlayerList.setAdapter(baseAdapter);
        btnOK = (Button) view.findViewById(R.id.btnPlayerOK);
        btnNew = (Button) view.findViewById(R.id.btnPlayerNew);
        btnOK.setOnClickListener(LoginFragment.this);
        btnNew.setOnClickListener(LoginFragment.this);
        etPass= (EditText) view.findViewById(R.id.etPass);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnPlayerOK:
                validatePass(v);
                break;
            case R.id.btnPlayerNew:
                listener.startSignUpFragment();
                break;
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
        Log.d(TAG, "onAttach");
        if (activity instanceof OnFragmentListener) {
            listener = (OnFragmentListener) activity;
        } else {
            throw new IllegalStateException("Activity not OnMainFragmentListener! ");
        }

    }

    @Override
    public void onDetach() {
        super.onDetach();
    }




    private void validatePass(View v) {
        Editable pass = etPass.getText();
        if (TextUtils.isEmpty(pass)) {
            Toast.makeText(v.getContext(), getString(R.string.tEmptyPass), Toast.LENGTH_SHORT).show();
            ;
            etPass.setError("Error!");
        } else if (pass.length() < 6) {
            Toast.makeText(v.getContext(), getString(R.string.tShortPass), Toast.LENGTH_SHORT).show();
            ;
            etPass.setError("Error!");
        } else {
            Toast.makeText(v.getContext(), getString(R.string.tLoginOK), Toast.LENGTH_SHORT).show();
            listener.startStartFragment();
        }
    }
}
