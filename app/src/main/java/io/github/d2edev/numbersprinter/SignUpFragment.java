package io.github.d2edev.numbersprinter;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
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

import java.util.ArrayList;
import java.util.List;

import io.github.d2edev.numbersprinter.Core.DBHelper;
import io.github.d2edev.numbersprinter.Core.DataRetainFragment;
import io.github.d2edev.numbersprinter.Core.OnFragmentListener;
import io.github.d2edev.numbersprinter.Core.Person;
import io.github.d2edev.numbersprinter.Core.PrefKeys;
import io.github.d2edev.numbersprinter.Core.UserTable;


public class SignUpFragment extends Fragment {
    private static final String TAG = "TAG_SignUpFragment_";
    private OnFragmentListener listener;
    private Button btSignUp;
    private EditText etName;
    private DBHelper dbHelper;
    private DataRetainFragment dataRetainFragment;
    private Vibrator vibrator;
    private List<Person> persons;


    public SignUpFragment() {
        // Required empty public constructor
    }


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        dbHelper = new DBHelper(getActivity());
        vibrator = (Vibrator) getActivity().getBaseContext().getSystemService(Context.VIBRATOR_SERVICE);
        btSignUp = (Button) view.findViewById(R.id.btSignup);
        btSignUp.setEnabled(false);
        btSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doVibrate(isVibraEnabled());
                if(credentialsValidated()){
                    if(persons==null){
                        persons=new ArrayList<Person>();
                        dataRetainFragment.setPersons(persons);
                    }
                    addUser();
                    listener.startLoginFragment();
                }
            }
        });
        etName = (EditText) view.findViewById(R.id.etName);
        dataRetainFragment = (DataRetainFragment) getFragmentManager().findFragmentByTag(MainActivity.RETAIN_FRAGMENT_TAG);
        if (dataRetainFragment != null) {
            dataRetainFragment.setCurrFragTag(MainActivity.SIGN_UP_FRAGMENT_TAG);
            btSignUp.setEnabled(true);
            if (dataRetainFragment.getPersons() != null) {
                persons=dataRetainFragment.getPersons();

            }
        }
    }


    private boolean credentialsValidated() {
        boolean validated=false;
        Editable name = etName.getText();
        if (TextUtils.isEmpty(name)) {
            Toast.makeText(getActivity(), getString(R.string.tEmptyName), Toast.LENGTH_SHORT).show();
            etName.setError(getString(R.string.tError));
        } else if (persons!=null&&nameAlreadyUsed(name.toString())) {
            Toast.makeText(getActivity(), getString(R.string.tDubbedUser), Toast.LENGTH_SHORT).show();
        } else {
            validated=true;
        }
        return validated;
    }

    private void addUser() {

        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(UserTable.Columns.NAME, String.valueOf(etName.getText()));
        cv.put(UserTable.Columns.SCORE_TOTAL, 0);
        cv.put(UserTable.Columns.SCORE_MAX, 0);
        cv.put(UserTable.Columns.GAMES_PLAYED, 0);
        db.insert(UserTable.TABLE, null, cv);
        db.close();
        persons.add(new Person(String.valueOf(etName.getText())));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_sign_up, container, false);
    }



    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        listener = (OnFragmentListener) activity;

    }


    private boolean isVibraEnabled() {
        boolean enb = PreferenceManager.getDefaultSharedPreferences(getActivity()).getBoolean(PrefKeys.VIBRATE, false);
        return enb;

    }

    private boolean nameAlreadyUsed(String name){
        boolean used=false;
        for (Person p :persons) {
            if(p.getName().equals(name)){
                used=true;
                break;
            }
        }
        return used;
    }

    private void doVibrate(boolean doVibrate) {
        if (doVibrate) {
            vibrator.vibrate(PrefKeys.VIB_LENGTH);
        }
    }
}
