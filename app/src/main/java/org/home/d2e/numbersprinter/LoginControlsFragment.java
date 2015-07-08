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
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import org.home.d2e.numbersprinter.Core.NameComparator;
import org.home.d2e.numbersprinter.Core.OnFragmentListener;
import org.home.d2e.numbersprinter.Core.Person;
import org.home.d2e.numbersprinter.adapter.MyListNameScoreAdapter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class LoginControlsFragment extends Fragment implements View.OnClickListener {
    private static final String TAG = "TAG_LoginListFragment_";
    OnFragmentListener listener;
    private List<Person> persons;
    private Person person;

    private ListView lvPlayerList;
    EditText etPass;
    Button btnOK;
    Button btnNew;

    public LoginControlsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Log.d(TAG,"onViewCreated");


        btnOK = (Button) view.findViewById(R.id.btnPlayerOK);
        btnNew = (Button) view.findViewById(R.id.btnPlayerNew);
        btnOK.setOnClickListener(LoginControlsFragment.this);
        btnNew.setOnClickListener(LoginControlsFragment.this);
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
        return inflater.inflate(R.layout.fragment_login_controls, container, false);
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
