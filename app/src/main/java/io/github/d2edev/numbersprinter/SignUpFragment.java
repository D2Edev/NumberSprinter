package io.github.d2edev.numbersprinter;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.app.Fragment;
import android.os.Vibrator;
import android.preference.PreferenceManager;
import android.text.Editable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import io.github.d2edev.numbersprinter.Core.DBHelper;
import io.github.d2edev.numbersprinter.Core.DataRetainFragment;
import io.github.d2edev.numbersprinter.Core.OnFragmentListener;
import io.github.d2edev.numbersprinter.Core.PrefKeys;
import io.github.d2edev.numbersprinter.Core.UserTable;


public class SignUpFragment extends Fragment implements View.OnClickListener {
    private static final String TAG = "TAG_SignUpFragment_";
    OnFragmentListener listener;
    private Button btSignUp;
    private EditText etName;
    private String[] names;
    private Cursor cursor;
    private DBHelper dbHelper;
    private SQLiteDatabase db;
    private int recNumber;
    DataRetainFragment dataRetainFragment;
    Vibrator vibrator;


    public SignUpFragment() {
        // Required empty public constructor
    }


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
            vibrator = (Vibrator) getActivity().getBaseContext().getSystemService(Context.VIBRATOR_SERVICE);
        btSignUp = (Button) view.findViewById(R.id.btSignup);
        etName = (EditText) view.findViewById(R.id.etName);
        btSignUp.setOnClickListener(SignUpFragment.this);
        dbHelper = new DBHelper(getActivity());
        db = dbHelper.getReadableDatabase();
        cursor = db.query(UserTable.TABLE, new String[]{UserTable.Columns.NAME}, null, null, null, null, null);
        recNumber = cursor.getCount();
        if (recNumber > 0) {
            names = new String[recNumber];
            cursor.moveToFirst();
            for (int i = 0; i < recNumber; i++) {
                names[i] = cursor.getString(0);

                cursor.moveToNext();

            }
        }
        db.close();
        dataRetainFragment = (DataRetainFragment) getFragmentManager().findFragmentByTag(MainActivity.RETAIN_FRAGMENT_TAG);
        if (dataRetainFragment != null) {
            dataRetainFragment.setCurrFragTag(MainActivity.SIGN_UP_FRAGMENT_TAG);
        }
    }


    @Override
    public void onClick(View v) {
        doVibrate(isVibraEnabled());
        switch (v.getId()) {
            case R.id.btSignup:
                validateCredentials(v);
                break;
            default:
                break;
        }
    }

    private void validateCredentials(View v) {
        Editable name = etName.getText();


        if (TextUtils.isEmpty(name)) {

            Toast.makeText(v.getContext(), getString(R.string.tEmptyName), Toast.LENGTH_SHORT).show();
            etName.setError(getString(R.string.tError));

        }  else if (isNameDubbed(name)) {
            Toast.makeText(getActivity(), getString(R.string.tDubbedUser), Toast.LENGTH_SHORT).show();

        } else {

            addUser();
            //Toast.makeText(v.getContext(),getString(R.string.btnSignup)+ " "+ getString(R.string.tOK),Toast.LENGTH_SHORT).show();
            listener.startLoginFragment();

        }


    }

    private void addUser() {

        db = dbHelper.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(UserTable.Columns.NAME, String.valueOf(etName.getText()));
        cv.put(UserTable.Columns.SCORE_TOTAL, 0);
        cv.put(UserTable.Columns.SCORE_MAX, 0);
        cv.put(UserTable.Columns.GAMES_PLAYED, 0);
        db.insert(UserTable.TABLE, null, cv);
        db.close();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_sign_up, container, false);
    }

    // TODO: Rename method, update argument and hook method into UI event


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        listener = (OnFragmentListener) activity;

    }

    @Override
    public void onDetach() {
        super.onDetach();


    }


    public boolean isNameDubbed(Editable name) {
        boolean isDubbed = false;
        for (int i = 0; i < recNumber; i++) {

            if (names[i].equals(String.valueOf(name))) {
                isDubbed = true;
                break;
            }
        }

        return isDubbed;
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