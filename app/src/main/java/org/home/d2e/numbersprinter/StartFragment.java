package org.home.d2e.numbersprinter;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;


public class StartFragment extends Fragment implements View.OnClickListener{
    private static final String TAG = "TAG_StartFragment_";
    OnFragmentListener listener;
    private Button btnRules;
    private Button btnLogin;
    private Button btnResult;


    public StartFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        Log.d(TAG, "onCreateView");
        return inflater.inflate(R.layout.fragment_start, container, false);
    }


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        Log.d(TAG, "onAttach");
        if (activity instanceof OnFragmentListener) {
            //setting interface for activity
            listener = (OnFragmentListener) activity;
        } else {
            throw new IllegalStateException("Activity not OnMainFragmentListener! ");
        }
    }

    // TODO: Rename method, update argument and hook method into UI event

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy");
    }

    @Override
    public void onDetach() {
        super.onDetach();
        Log.d(TAG, "onDetach");

    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //as buttons created, defining them
        btnRules = (Button) view.findViewById(R.id.btnRules);
        btnLogin = (Button) view.findViewById(R.id.btnLogin);
        btnResult = (Button) view.findViewById(R.id.btnResults);
        //setting listeners on buttons
        btnRules.setOnClickListener(StartFragment.this);
        btnLogin.setOnClickListener(StartFragment.this);
        btnResult.setOnClickListener(StartFragment.this);
        Log.d(TAG, "onViewCreated");
    }

//processing clicks
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnRules:
                Toast.makeText(v.getContext(), getString(R.string.tRules), Toast.LENGTH_LONG).show();
                break;
            case R.id.btnLogin:
                listener.startLoginFragment();
                break;
            case R.id.btnResults:
                listener.startResultsFragment();

        }


    }
}
