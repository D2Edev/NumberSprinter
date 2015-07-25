package org.home.d2e.numbersprinter;

import android.app.Activity;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.text.Editable;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.home.d2e.numbersprinter.Core.DBHelper;
import org.home.d2e.numbersprinter.Core.OnFragmentListener;
import org.home.d2e.numbersprinter.Core.UserTable;


public class SignUpFragment extends Fragment implements View.OnClickListener {
    private static final String TAG = "TAG_SignUpFragment_";
    OnFragmentListener listener;
    private Button btSignUp;
    private EditText etName;
    private EditText etPassCheck;
    private EditText etPass;
    private int hashCodeOne;
    private int hashCodeTwo;
    private String[] names;
    private Cursor cursor;
    private DBHelper dbHelper;
    private SQLiteDatabase db;
    private int recNumber;


    public SignUpFragment() {
        // Required empty public constructor
    }


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        btSignUp = (Button) view.findViewById(R.id.btSignup);
        etName = (EditText) view.findViewById(R.id.etName);
        etPass = (EditText) view.findViewById(R.id.etPass);
        etPassCheck = (EditText) view.findViewById(R.id.etPassCheck);
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
                    Log.d(TAG,"name " + names[i]+ " cursor " + cursor.getString(0) );
                    cursor.moveToNext();

                }
        }
        db.close();
    }


    @Override
    public void onClick(View v) {
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
        Editable pass = etPass.getText();
        Editable passCheck = etPassCheck.getText();
        hashCodeOne = String.valueOf(etPass.getText()).hashCode();
        hashCodeTwo = String.valueOf(etPassCheck.getText()).hashCode();


        if (TextUtils.isEmpty(name)) {

            Toast.makeText(v.getContext(), getString(R.string.tEmptyName), Toast.LENGTH_SHORT).show();
            etName.setError(getString(R.string.tError));

        } else if (TextUtils.isEmpty(pass)) {

            Toast.makeText(v.getContext(), getString(R.string.tEmptyPass), Toast.LENGTH_SHORT).show();
            etPass.setError(getString(R.string.tError));

        } else if (pass.length() < 6) {

            Toast.makeText(v.getContext(), getString(R.string.tShortPass), Toast.LENGTH_SHORT).show();
            etPass.setError(getString(R.string.tError));

        } else if (TextUtils.isEmpty(passCheck)) {

            Toast.makeText(v.getContext(), getString(R.string.tPlsRetypePass), Toast.LENGTH_SHORT).show();
            etPassCheck.setError(getString(R.string.tError));

        } else if (String.valueOf(etPass.getText()).hashCode() != String.valueOf(etPassCheck.getText()).hashCode()) {
            Toast.makeText(v.getContext(), getString(R.string.tPassMisMatch), Toast.LENGTH_SHORT).show();
            etPassCheck.setError(getString(R.string.tError));
        } else if (isNameDubbed(name)){
            Toast.makeText(getActivity(), getString(R.string.tDubbedUser), Toast.LENGTH_SHORT).show();

        }else{

            addUser();
            //Toast.makeText(v.getContext(),getString(R.string.btnSignup)+ " "+ getString(R.string.tOK),Toast.LENGTH_SHORT).show();
            listener.startLoginFragment();

        }


    }

    private void addUser() {

        db = dbHelper.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(UserTable.Columns.NAME, String.valueOf(etName.getText()));
        cv.put(UserTable.Columns.PASSWORD, String.valueOf(etPass.getText()).hashCode());
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
            Log.d(TAG,"name " + names[i]+ " cursor " + String.valueOf(name) );
            if (names[i].equals(String.valueOf(name))) {
                isDubbed = true;
                break;
            }
        }
        Log.d(TAG,"dubbed? " + isDubbed );
        return isDubbed;
    }
}
