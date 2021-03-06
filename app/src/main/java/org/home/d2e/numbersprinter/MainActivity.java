package org.home.d2e.numbersprinter;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.res.Configuration;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Vibrator;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Display;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.Surface;
import android.view.View;
import android.widget.Toast;

import org.home.d2e.numbersprinter.Core.DBHelper;
import org.home.d2e.numbersprinter.Core.DataRetainFragment;
import org.home.d2e.numbersprinter.Core.OnBackPressedListener;
import org.home.d2e.numbersprinter.Core.OnFragmentListener;
import org.home.d2e.numbersprinter.Core.PrefKeys;
import org.home.d2e.numbersprinter.Core.UserTable;

public class MainActivity extends AppCompatActivity implements OnFragmentListener {
    private final String TAG = "TAG_MainActivity ";
    private DataRetainFragment dataRetainFragment;
    private FragmentManager manager;
    private DBHelper dbHelper;
    private SQLiteDatabase db;
    private Cursor userListCursor;
    private Vibrator vibrator;

    public static final String GAME_FRAGMENT_TAG = "GAME_FRAGMENT";
    public static final String GAME_OVER_FRAGMENT_TAG = "GAME_OVER_FRAGMENT";
    public static final String LOGIN_FRAGMENT_TAG = "LOGIN_FRAGMENT";
    public static final String RESULTS_FRAGMENT_TAG = "RESULTS_FRAGMENT";
    public static final String RULES_FRAGMENT_TAG = "RULES_FRAGMENT";
    public static final String START_FRAGMENT_TAG = "START_FRAGMENT";
    public static final String SIGN_UP_FRAGMENT_TAG = "SIGN_UP_FRAGMENT";
    public static final String RETAIN_FRAGMENT_TAG = "RETAIN_FRAGMENT";
    public static final String PREF_FRAGMENT_TAG = "PREF_FRAGMENT";
    public static final String BACK_STACK_TAG = "BACK_STACK";


    OnBackPressedListener onBackPressedListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Log.d(TAG, "onCreate");
        manager = getFragmentManager();
        //hideActBarInLandscape();
        startRetainFragment();
        firstStart();


    }

    private void firstStart() {
        FragmentTransaction transaction = manager.beginTransaction();
        if (dataRetainFragment == null) {
            transaction.add(R.id.container_main, new StartFragment(), START_FRAGMENT_TAG);
        }
        transaction.commit();
    }


    @Override
    protected void onStart() {
        super.onStart();
        //Log.d(TAG, "onStart");

    }

    @Override
    protected void onStop() {
        super.onStop();
        //Log.d(TAG, "onStop");
    }

    @Override
    public void onBackPressed() {
        //Log.d(TAG, "onBackPressed");

        doVibrate(isVibraEnabled());
        //pass event to onFragmentListener
        if (onBackPressedListener != null) {
            onBackPressedListener.backIsPressed();
        }
        dataRetainFragment = (DataRetainFragment) manager.findFragmentByTag(RETAIN_FRAGMENT_TAG);
        if (dataRetainFragment != null) {
            //Log.d(TAG, dataRetainFragment.getCurrFragTag());
            switch (dataRetainFragment.getCurrFragTag()) {
                case GAME_FRAGMENT_TAG:
                    startLoginFragment();
                    break;
                case GAME_OVER_FRAGMENT_TAG:
                    startStartFragment();
                    break;
                case LOGIN_FRAGMENT_TAG:
                    startStartFragment();
                    break;
                case RESULTS_FRAGMENT_TAG:
                    startStartFragment();
                    break;
                case RULES_FRAGMENT_TAG:
                    startStartFragment();
                    break;
                case SIGN_UP_FRAGMENT_TAG:
                    if (dbContainsData()) {
                        startLoginFragment();
                    } else {
                        startStartFragment();
                    }
                    break;
                case START_FRAGMENT_TAG:
                    super.onBackPressed();
                    break;
                case PREF_FRAGMENT_TAG:
                    if (manager.getBackStackEntryCount() > 0) {
                        manager.popBackStack();
                    }
            }
        } else {
            super.onBackPressed();
        }

    }


    @Override
    protected void onDestroy() {

        super.onDestroy();
        //Log.d(TAG, "onDestroy");
    }


    public void startLoginFragment() {
        //check if there's data in users table
        if (dbContainsData()) {
            //if yes - open login fragment
            fragStart(new LoginFragment());
        } else {
            //if not - open signup to add first user
            startSignUpFragment();
        }


    }


    public void startResultsFragment() {
        if (dbContainsData()) {
            fragStart(new ResultsFragment());
        } else {
            Toast.makeText(this, R.string.tNoRecords, Toast.LENGTH_LONG).show();
        }
    }


    public void startSignUpFragment() {
        fragStart(new SignUpFragment());

    }


    public void startStartFragment() {
        fragStart(new StartFragment());

    }

    public void startRetainFragment() {
        dataRetainFragment = (DataRetainFragment) manager.findFragmentByTag(RETAIN_FRAGMENT_TAG);
        //if reatain fragment does not exist - create it
        if (dataRetainFragment == null) {
            FragmentTransaction transaction = manager.beginTransaction();
            //dataRetainFragment.setFirstLaunch(true);
            transaction.add(new DataRetainFragment(), RETAIN_FRAGMENT_TAG);
            transaction.commit();
        }
    }

    public void startGameFragment() {
        fragStart(new GameFragment());
    }


    public void startRulesFragment() {
        fragStart(new RulesFragment());
    }


    @Override
    public void startGameOverFragment() {
        fragStart(new GameOverFragment());


    }

    private boolean dbContainsData() {
        boolean dbContainsData = false;
        dbHelper = new DBHelper(this);
        db = dbHelper.getReadableDatabase();
        userListCursor = db.query(UserTable.TABLE, new String[]{UserTable.Columns.NAME}, null, null, null, null, null);
        if (userListCursor.getCount() > 0) {
            dbContainsData = true;
        }
        db.close();
        return dbContainsData;
    }

    @Override
    public View onCreateView(View parent, String name, Context context, AttributeSet attrs) {
        return super.onCreateView(parent, name, context, attrs);
    }

    public void setOnBackPressedListener(OnBackPressedListener onBackPressedListener) {
        this.onBackPressedListener = onBackPressedListener;
    }

    public void hideActBarInLandscape() {
        ActionBar actionBar = getSupportActionBar();
        Display getOrient = this.getWindowManager().getDefaultDisplay();
        int orientation = Configuration.ORIENTATION_UNDEFINED;
        if (getOrient.getRotation() != Surface.ROTATION_0) {
            actionBar.hide();
        }
    }

    public void fragStart(Fragment fragment) {
        //begin opening fragment
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.setCustomAnimations(R.animator.enter_anim, R.animator.exit_anim);
        transaction.replace(R.id.container_main, fragment);
        //end opening fragment
        transaction.commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.action_settings:
                dataRetainFragment = (DataRetainFragment) manager.findFragmentByTag(RETAIN_FRAGMENT_TAG);
                if (dataRetainFragment.getCurrFragTag() == GAME_FRAGMENT_TAG) {
                    Toast.makeText(this, getString(R.string.tCantChangeSettings), Toast.LENGTH_SHORT).show();
                    ;
                } else {
                    dataRetainFragment.setCurrFragTag(MainActivity.PREF_FRAGMENT_TAG);
                    //begin opening fragment
                    FragmentTransaction transaction = manager.beginTransaction();
                    transaction.setCustomAnimations(R.animator.enter_anim, R.animator.exit_anim);
                    transaction.replace(R.id.container_main, new PrefFragment());
                    transaction.addToBackStack(BACK_STACK_TAG);
                    //end opening fragment
                    transaction.commit();
                }
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private boolean isVibraEnabled() {

        //boolean enb=getActivity().getSharedPreferences(PrefKeys.NAME, Context.MODE_PRIVATE).getBoolean(PrefKeys.VIBRATE,false);
        boolean enb = PreferenceManager.getDefaultSharedPreferences(this).getBoolean(PrefKeys.VIBRATE, false);
        return enb;

    }

    private void doVibrate(boolean doVibrate) {
        if (doVibrate) {
            vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
            vibrator.vibrate(PrefKeys.VIB_LENGTH);
        }

    }
}
