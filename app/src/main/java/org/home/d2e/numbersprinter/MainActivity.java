package org.home.d2e.numbersprinter;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import org.home.d2e.numbersprinter.Core.DBHelper;
import org.home.d2e.numbersprinter.Core.DataRetainFragment;
import org.home.d2e.numbersprinter.Core.OnFragmentListener;
import org.home.d2e.numbersprinter.Core.UserTable;

public class MainActivity extends AppCompatActivity implements OnFragmentListener {
    private final String tag = "TAG_MainActivity ";
    private DataRetainFragment dataRetainFragment;
    private FragmentManager manager;
    private DBHelper dbHelper;
    private SQLiteDatabase db;
    private Cursor userListCursor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d(tag, "onCreate");
        manager = getFragmentManager();
        startRetainFragment();
        if (savedInstanceState == null) {
            startStartFragment();

        }
        ;

    }


    @Override
    protected void onStart() {
        super.onStart();
        Log.d(tag, "onStart");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(tag, "onStop");
    }

    @Override
    public void onBackPressed() {
        Log.d(tag, "onBackPressed");
        if (getFragmentManager().getBackStackEntryCount() > 0) {
            getFragmentManager().popBackStack();
        } else {
            super.onBackPressed();

        }
    }


    @Override
    protected void onDestroy() {

        super.onDestroy();
        Log.d(tag, "onDestroy");
    }


    public void startLoginFragment() {
        if (dbContainsData()) {
            //begin opening fragment
            FragmentTransaction transaction = manager.beginTransaction();
            transaction.replace(R.id.container_main, new LoginFragment(), "login");
            transaction.addToBackStack("main");
            //end opening fragment
            transaction.commit();
        } else {
            startSignUpFragment();
        }


    }


    public void startResultsFragment() {
        if (dbContainsData()) {
            //begin opening fragment
            FragmentTransaction transaction = manager.beginTransaction();
            transaction.replace(R.id.container_main, new ResultsFragment(), "results");
            transaction.addToBackStack("main");
            //end opening fragment
            transaction.commit();
        } else {
            Toast.makeText(this, R.string.tNoRecords, Toast.LENGTH_SHORT).show();
        }
    }


    public void startSignUpFragment() {

        //begin opening fragment
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.container_main, new SignUpFragment(), "sign_up");
        transaction.addToBackStack("main");
        //end opening fragment
        transaction.commit();

    }


    public void startStartFragment() {

        StartFragment stF = (StartFragment) manager.findFragmentByTag("start");
        //checking if fragment has been created already
        if (stF == null || !stF.isInLayout()) {
            //if exists
            FragmentTransaction transaction = manager.beginTransaction();
            transaction.add(R.id.container_main, new StartFragment(), "start");
            transaction.commit();

        } else {

            //if not
            Log.d(tag, "Start Fragment exists");
            FragmentTransaction transaction = manager.beginTransaction();
            transaction.replace(R.id.container_main, stF);
            transaction.addToBackStack("main");
            transaction.commit();
        }
    }

    public void startRetainFragment() {
        dataRetainFragment = (DataRetainFragment) manager.findFragmentByTag("retain");
        if (dataRetainFragment == null) {
            FragmentTransaction transaction = manager.beginTransaction();
            dataRetainFragment = new DataRetainFragment();
            transaction.add(dataRetainFragment, "retain");
            transaction.commit();
        }
    }

    public void startGameFragment() {

        //begin opening fragment

        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.container_main, new GameFragment());
        transaction.addToBackStack("main");
        //end opening fragment
        transaction.commit();
    }


    public void startRulesFragment() {
        //begin opening fragment
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.container_main, new RulesFragment(), "rules");
        transaction.addToBackStack("main");
        //end opening fragment
        transaction.commit();
    }

    private boolean dbContainsData() {
        boolean dbContainsData = false;
        dbHelper = new DBHelper(this);
        db = dbHelper.getReadableDatabase();
        userListCursor = db.query(UserTable.TABLE, null, null, null, null, null, UserTable.Columns.SCORE_TOTAL + " DESC;");
        if (userListCursor.getCount() > 0) {
            dbContainsData = true;
        }
        return dbContainsData;
    }
}
