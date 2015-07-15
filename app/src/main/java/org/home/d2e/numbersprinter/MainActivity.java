package org.home.d2e.numbersprinter;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import org.home.d2e.numbersprinter.Core.DBHelper;
import org.home.d2e.numbersprinter.Core.DataRetainFragment;
import org.home.d2e.numbersprinter.Core.OnBackPressedListener;
import org.home.d2e.numbersprinter.Core.OnFragmentListener;
import org.home.d2e.numbersprinter.Core.UserTable;

public class MainActivity extends AppCompatActivity implements OnFragmentListener {
    private final String TAG = "TAG_MainActivity ";
    private DataRetainFragment dataRetainFragment;
    private FragmentManager manager;
    private DBHelper dbHelper;
    private SQLiteDatabase db;
    private Cursor userListCursor;
    OnBackPressedListener onBackPressedListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d(TAG, "onCreate");
        manager = getFragmentManager();
        startRetainFragment();

        if (savedInstanceState == null) {
            startStartFragment();

        }


    }


    @Override
    protected void onStart() {
        super.onStart();

        Log.d(TAG, "onStart");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG, "onStop");
    }

    @Override
    public void onBackPressed() {
        Log.d(TAG, "onBackPressed");
        if (getFragmentManager().getBackStackEntryCount() > 0) {
            getFragmentManager().popBackStack();
            //pass event to listener
            if (onBackPressedListener != null) {
                onBackPressedListener.backIsPressed();
            }
        } else {
            super.onBackPressed();

        }
    }


    @Override
    protected void onDestroy() {

        super.onDestroy();
        Log.d(TAG, "onDestroy");
    }


    public void startLoginFragment() {
        //check if there's data in users table
        if (dbContainsData()) {
            //if yes - open login fragment
            FragmentTransaction transaction = manager.beginTransaction();
            transaction.replace(R.id.container_main, new LoginFragment(), "login");
            transaction.addToBackStack("main");
            //end opening fragment
            transaction.commit();
        } else {
            //if not - open signup to add first user
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
            Log.d(TAG, "Start Fragment exists");
            FragmentTransaction transaction = manager.beginTransaction();
            transaction.replace(R.id.container_main, stF);
            transaction.addToBackStack("main");
            transaction.commit();
        }
    }

    public void startRetainFragment() {
        dataRetainFragment = (DataRetainFragment) manager.findFragmentByTag("retain");
        //if reatain fragment does not exist - create it
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

    @Override
    public void startGameOverFragment() {
        //begin opening fragment
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.container_main, new GameOverFragment(), "game_over");
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

    @Override
    public View onCreateView(View parent, String name, Context context, AttributeSet attrs) {
        return super.onCreateView(parent, name, context, attrs);
    }

    public void setOnBackPressedListener(OnBackPressedListener onBackPressedListener) {
        this.onBackPressedListener = onBackPressedListener;
    }
}
