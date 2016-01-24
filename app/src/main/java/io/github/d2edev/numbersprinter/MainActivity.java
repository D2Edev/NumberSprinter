package io.github.d2edev.numbersprinter;

import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Configuration;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Vibrator;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.Display;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.Surface;
import android.view.View;
import android.widget.Toast;

import io.github.d2edev.numbersprinter.Core.DBHelper;
import io.github.d2edev.numbersprinter.Core.DataRetainFragment;
import io.github.d2edev.numbersprinter.Core.OnBackPressedListener;
import io.github.d2edev.numbersprinter.Core.OnFragmentListener;
import io.github.d2edev.numbersprinter.Core.OnUIModeChangeListener;
import io.github.d2edev.numbersprinter.Core.PrefKeys;
import io.github.d2edev.numbersprinter.Core.UserTable;

public class MainActivity extends AppCompatActivity implements OnFragmentListener, OnUIModeChangeListener {
    //no passwd version
    private final String TAG = "TAG_MainActivity ";
    private DataRetainFragment dataRetainFragment;
    private FragmentManager manager;
    private DBHelper dbHelper;
    private SQLiteDatabase db;
    private Cursor userListCursor;
    private Vibrator vibrator;
    private int themeID;

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
        setUIMode();
        setContentView(R.layout.activity_main);
        //Log.d(TAG, "onCreate");
        manager = getFragmentManager();
        //hideActBarInLandscape();
        startRetainFragment();
        firstStart();
        displayMetrics();


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
            case R.id.action_about:
                showAboutInfo();
            default:
                return super.onOptionsItemSelected(item);
        }
    }



    private boolean isVibraEnabled() {

        //boolean enb=getActivity().getSharedPreferences(PrefKeys.NAME, Context.MODE_PRIVATE).getBoolean(PrefKeys.VIBRATE,false);
        boolean enb = PreferenceManager.getDefaultSharedPreferences(this).getBoolean(PrefKeys.VIBRATE, false);
        return enb;

    }

    private void showAboutInfo() {
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setTitle(getString(R.string.action_about))
                .setMessage(getString(R.string.action_about_info))
                .setIcon(R.drawable.logo_small)
                .setCancelable(false)
                .setNegativeButton("ОК",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });
        AlertDialog alert = builder.create();
        alert.show();
    }

    private void doVibrate(boolean doVibrate) {
        if (doVibrate) {
            vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
           vibrator.vibrate(PrefKeys.VIB_LENGTH);
        }

    }

    private void displayMetrics() {
        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        Log.d(TAG, "height " + metrics.heightPixels);
        Log.d(TAG, "width " + metrics.widthPixels);
        Log.d(TAG, "xdpi " + metrics.xdpi);
        Log.d(TAG, "ydpi " + metrics.ydpi);
        Log.d(TAG, "density " + metrics.density);
        Log.d(TAG, "densityDpi " + metrics.densityDpi);

    }

    public int currSideLimit() {
        int sideLimit;
        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        sideLimit = metrics.widthPixels;
        if (sideLimit > metrics.heightPixels) {
            sideLimit = metrics.heightPixels - getActionBarHeight();
        }
        //Log.d(TAG, " " + metrics.heightPixels + " "+ metrics.widthPixels);
        Log.d(TAG, " " + sideLimit);
        //Log.d(TAG, " " +getActionBarHeight());

        return sideLimit;
    }

    private int getActionBarHeight() {
        int actionBarHeight = 0;
        TypedValue tv = new TypedValue();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            if (getTheme().resolveAttribute(android.R.attr.actionBarSize, tv,
                    true))
                actionBarHeight = TypedValue.complexToDimensionPixelSize(
                        tv.data, getResources().getDisplayMetrics());
        } else {
            actionBarHeight = TypedValue.complexToDimensionPixelSize(tv.data,
                    getResources().getDisplayMetrics());
        }
        return actionBarHeight;
    }

    @Override
    public void setUIMode() {
        if (PreferenceManager.getDefaultSharedPreferences(this).getBoolean(PrefKeys.NIGHT_MODE, false)) {
            themeID = R.style.AppTheme_Dark;
            //Toast.makeText(this, "NIGHT", Toast.LENGTH_SHORT).show();
        } else {
            themeID = R.style.AppTheme_Light;
            //Toast.makeText(this, "DAY", Toast.LENGTH_SHORT).show();
        }

        this.setTheme(themeID);
        if (dataRetainFragment != null) {
            this.recreate();
        }


    }


}
