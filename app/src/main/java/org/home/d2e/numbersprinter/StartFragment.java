package org.home.d2e.numbersprinter;

import android.app.Activity;
import android.app.Fragment;
import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Vibrator;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import org.home.d2e.numbersprinter.Core.DBHelper;
import org.home.d2e.numbersprinter.Core.DataRetainFragment;
import org.home.d2e.numbersprinter.Core.OnFragmentListener;
import org.home.d2e.numbersprinter.Core.Person;
import org.home.d2e.numbersprinter.Core.PrefKeys;
import org.home.d2e.numbersprinter.Core.UserTable;

import java.util.ArrayList;
import java.util.List;


public class StartFragment extends Fragment implements View.OnClickListener {
    private static final String TAG = "TAG_"+MainActivity.START_FRAGMENT_TAG;
    OnFragmentListener listener;
    private Button btnRules;
    private Button btnResult;
    private Button btnPlay;
    private DataRetainFragment dataRetainFragment;
    private Vibrator vibrator;


    public StartFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Log.d(TAG, "onCreate");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        //Log.d(TAG, "onCreateView");
        return inflater.inflate(R.layout.fragment_start, container, false);
    }


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        //Log.d(TAG, "onAttach");
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
        //Log.d(TAG, "onDestroy");
    }

    @Override
    public void onDetach() {
        super.onDetach();
        //Log.d(TAG, "onDetach");

    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        vibrator = (Vibrator) getActivity().getBaseContext().getSystemService(Context.VIBRATOR_SERVICE);
        //as buttons created, defining them
        btnRules = (Button) view.findViewById(R.id.btnRules);
        btnResult = (Button) view.findViewById(R.id.btnResults);
        btnPlay = (Button) view.findViewById(R.id.btnPlay);

        //setting listeners on buttons
        btnRules.setOnClickListener(StartFragment.this);
        btnResult.setOnClickListener(StartFragment.this);
        btnPlay.setOnClickListener(StartFragment.this);
        //Log.d(TAG, "onViewCreated");
        dataRetainFragment = (DataRetainFragment) getFragmentManager().findFragmentByTag(MainActivity.RETAIN_FRAGMENT_TAG);
        if(dataRetainFragment!=null){
            dataRetainFragment.setCurrFragTag(MainActivity.START_FRAGMENT_TAG);
        }
    }

    //processing clicks
    public void onClick(View v) {
        doVibrate(isVibraEnabled());
        switch (v.getId()) {
            case R.id.btnRules:
                listener.startRulesFragment();
                break;
            case R.id.btnResults:
                //onFragmentListener.startGameFragment();
                listener.startResultsFragment();
                break;
            case R.id.btnPlay:
                listener.startLoginFragment();
                //dbStub();
                break;
            default:
                Toast.makeText(v.getContext(), getString(R.string.wtf), Toast.LENGTH_LONG).show();
                break;

        }


    }
    private boolean isVibraEnabled(){

        //boolean enb=getActivity().getSharedPreferences(PrefKeys.NAME, Context.MODE_PRIVATE).getBoolean(PrefKeys.VIBRATE,false);
        boolean enb= PreferenceManager.getDefaultSharedPreferences(getActivity()).getBoolean(PrefKeys.VIBRATE,false);
        return  enb;

    }

    private void doVibrate(boolean doVibrate){
        if(doVibrate){
            vibrator.vibrate(PrefKeys.VIB_LENGTH);
        }
    }

}
