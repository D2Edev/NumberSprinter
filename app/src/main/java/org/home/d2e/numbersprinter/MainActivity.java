package org.home.d2e.numbersprinter;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
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
import org.home.d2e.numbersprinter.Core.TickerService;
import org.home.d2e.numbersprinter.Core.UserTable;

public class MainActivity extends AppCompatActivity implements OnFragmentListener {
    private final String TAG = "TAG_MainActivity ";
    private DataRetainFragment dataRetainFragment;
    private FragmentManager manager;
    private DBHelper dbHelper;
    private SQLiteDatabase db;
    private Cursor userListCursor;
    private Fragment fragment;
    private Intent intentStartTickerService;
    public static final String GAME_FRAGMENT_TAG = "gf";
    public static final String GAME_OVER_FRAGMENT_TAG = "gof";
    public static final String LOGIN_FRAGMENT_TAG = "lf";
    public static final String RESULTS_FRAGMENT_TAG = "rf";
    public static final String RULES_FRAGMENT_TAG = "ruf";
    public static final String START_FRAGMENT_TAG = "sf";
    public static final String SIGN_UP_FRAGMENT_TAG = "suf";
    public static final String RETAIN_FRAGMENT_TAG = "ref";
    public static final String STACK_TAG = "main";


    OnBackPressedListener onBackPressedListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d(TAG, "onCreate");
        manager = getFragmentManager();
        startRetainFragment();
        if (manager.getBackStackEntryCount()==0){
        startStartFragment();}


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
        //pass event to onFragmentListener
        if (onBackPressedListener != null) {
            onBackPressedListener.backIsPressed();
        }
        if (getFragmentManager().getBackStackEntryCount() > 0) {
            getFragmentManager().popBackStack();


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
            transaction.replace(R.id.container_main, new LoginFragment(), LOGIN_FRAGMENT_TAG);
            transaction.addToBackStack(STACK_TAG);
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
            transaction.replace(R.id.container_main, new ResultsFragment(), RESULTS_FRAGMENT_TAG);
            transaction.addToBackStack(STACK_TAG);
            //end opening fragment
            transaction.commit();
        } else {
            Toast.makeText(this, R.string.tNoRecords, Toast.LENGTH_LONG).show();
        }
    }


    public void startSignUpFragment() {
        //begin opening fragment
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.container_main, new SignUpFragment(), SIGN_UP_FRAGMENT_TAG);
        transaction.addToBackStack(STACK_TAG);
        //end opening fragment
        transaction.commit();

    }


    public void startStartFragment() {

        fragment = manager.findFragmentByTag(START_FRAGMENT_TAG);
         //checking if fragment has been created already
        manager.popBackStack(null,FragmentManager.POP_BACK_STACK_INCLUSIVE);
        FragmentTransaction transaction = manager.beginTransaction();
        Log.d(TAG, "startStartFragment " + fragment);
        if (fragment == null) {
            //if exists
            transaction.add(R.id.container_main, new StartFragment(), START_FRAGMENT_TAG);
        } else {
            //if not
            transaction.replace(R.id.container_main, fragment);
            transaction.addToBackStack(STACK_TAG);
        }
        transaction.commit();
    }

    public void startRetainFragment() {
        dataRetainFragment = (DataRetainFragment) manager.findFragmentByTag(RETAIN_FRAGMENT_TAG);
        //if reatain fragment does not exist - create it
        if (dataRetainFragment == null) {
            FragmentTransaction transaction = manager.beginTransaction();
            dataRetainFragment = new DataRetainFragment();
            transaction.add(dataRetainFragment, RETAIN_FRAGMENT_TAG);
            transaction.commit();

        }
    }

    public void startGameFragment() {
        //begin opening fragment
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.container_main, new GameFragment(), GAME_FRAGMENT_TAG);
        transaction.addToBackStack(STACK_TAG);
        //end opening fragment
        transaction.commit();
    }


    public void startRulesFragment() {
        //begin opening fragment
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.container_main, new RulesFragment(), RULES_FRAGMENT_TAG);
        transaction.addToBackStack(STACK_TAG);
        //end opening fragment
        transaction.commit();
    }

    @Override
    public void startGameOverFragment() {
        //begin opening fragment
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.container_main, new GameOverFragment(), GAME_OVER_FRAGMENT_TAG);
        transaction.addToBackStack(STACK_TAG);
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
