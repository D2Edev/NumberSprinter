package org.home.d2e.numbersprinter;


import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.app.Fragment;
import android.os.Vibrator;
import android.preference.PreferenceManager;
import android.text.Editable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.home.d2e.numbersprinter.Core.DBHelper;
import org.home.d2e.numbersprinter.Core.DataRetainFragment;
import org.home.d2e.numbersprinter.Core.OnFragmentListener;
import org.home.d2e.numbersprinter.Core.Person;
import org.home.d2e.numbersprinter.Core.PrefKeys;
import org.home.d2e.numbersprinter.Core.UserTable;
import org.home.d2e.numbersprinter.adapter.UserCursorAdapter;


/**
 * A simple {@link Fragment} subclass.
 */
public class LoginFragment extends Fragment implements View.OnClickListener, CompoundButton.OnCheckedChangeListener {
    private static final String TAG = "TAG_LogintFragment_";
    private ListView lvPlayers;
    private EditText etPass;
    private Button btnOK;
    private Button btnNew;
    private TextView tvSelectedPlayer;
    private DBHelper dbHelper;
    private SQLiteDatabase db;
    private Cursor userListCursor;
    private UserCursorAdapter userCursorAdapter;
    private Person person;
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
       // Log.d(TAG, "onViewCreated");
            vibrator= (Vibrator) getActivity().getBaseContext().getSystemService(Context.VIBRATOR_SERVICE);
        btnOK = (Button) view.findViewById(R.id.btnPlayerOK);
        btnOK.setEnabled(false);
        btnNew = (Button) view.findViewById(R.id.btnPlayerNew);
        btnOK.setOnClickListener(LoginFragment.this);
        btnNew.setOnClickListener(LoginFragment.this);
        etPass = (EditText) view.findViewById(R.id.etPass);
        tvSelectedPlayer = (TextView) view.findViewById(R.id.tvSelectedPlayer);
        cbHard = (CheckBox) view.findViewById(R.id.cbHard);
        dataRetainFragment = (DataRetainFragment) getFragmentManager().findFragmentByTag(MainActivity.RETAIN_FRAGMENT_TAG);
        if(dataRetainFragment!=null){
            cbHard.setChecked(dataRetainFragment.getHardMode());
            dataRetainFragment.setCurrFragTag(MainActivity.LOGIN_FRAGMENT_TAG);
        }
        cbHard.setOnCheckedChangeListener(LoginFragment.this);

        dbHelper = new DBHelper(getActivity());
        db = dbHelper.getReadableDatabase();
        userListCursor = db.query(UserTable.TABLE, null, null, null, null, null, UserTable.Columns.NAME);

        person = new Person();
        // создаем адаптер
        if (userListCursor.getCount() > 0) {
            // build adapter
            userCursorAdapter = new UserCursorAdapter(getActivity(), userListCursor);
            lvPlayers = (ListView) view.findViewById(R.id.lvPlayers);
            // attach listview to adapter
            lvPlayers.setAdapter(userCursorAdapter);
            //setting onFragmentListener on item in listview
            lvPlayers.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    //on click - getting info from cursor
                    Cursor cursor = (Cursor) lvPlayers.getItemAtPosition(position);
                    person.setName(cursor.getString(cursor.getColumnIndex(UserTable.Columns.NAME)));
                    person.setPassword(cursor.getInt(cursor.getColumnIndex(UserTable.Columns.PASSWORD)));
                    person.setGamesPlayed(cursor.getInt(cursor.getColumnIndex(UserTable.Columns.GAMES_PLAYED)));
                    person.setScoreMax(cursor.getInt(cursor.getColumnIndex(UserTable.Columns.SCORE_MAX)));
                    person.setScoreTotal(cursor.getInt(cursor.getColumnIndex(UserTable.Columns.SCORE_TOTAL)));
                    tvSelectedPlayer.setText(getString(R.string.tSelected) + " " + person.getName());
                    btnOK.setEnabled(true);
                    doVibrate(isVibraEnabled());

                }
            });
        }

    }

    @Override
    public void onClick(View v) {
        doVibrate(isVibraEnabled());
        switch (v.getId()) {

            case R.id.btnPlayerOK:
                if (passOK(v, person)) {

                    retainUserData();
                    listener.startGameFragment();
                }
                break;
            case R.id.btnPlayerNew:

                listener.startSignUpFragment();
                break;
        }

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
        etPass.setText("");
        db.close();
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    private boolean passOK(View v, Person person) {
        this.person = person;
        boolean passOK = false;

        Editable pass = etPass.getText();
        //Log.d(TAG, "" + person.getPassword() + " " + String.valueOf(pass).hashCode());
        if (person.getPassword() != String.valueOf(pass).hashCode()) {
            Toast.makeText(v.getContext(), getString(R.string.tPassWrong), Toast.LENGTH_SHORT).show();
            //etPass.setError("Error!");
        } else {
            //Toast.makeText(v.getContext(), getString(R.string.tLoginOK), Toast.LENGTH_SHORT).show();
            passOK = true;

        }
        return passOK;
    }


    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
dataRetainFragment.setHardMode(isChecked);
    }

    private boolean isVibraEnabled(){

        //boolean enb=getActivity().getSharedPreferences(PrefKeys.NAME, Context.MODE_PRIVATE).getBoolean(PrefKeys.VIBRATE,false);
        boolean enb=PreferenceManager.getDefaultSharedPreferences(getActivity()).getBoolean(PrefKeys.VIBRATE,false);
        return  enb;

    }

private void doVibrate(boolean doVibrate){
    if(doVibrate){
        vibrator.vibrate(PrefKeys.VIB_LENGTH);
    }
}
}

