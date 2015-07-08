package org.home.d2e.numbersprinter;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import org.home.d2e.numbersprinter.Core.GridRetainFragment;
import org.home.d2e.numbersprinter.Core.OnFragmentListener;

public class MainActivity extends AppCompatActivity implements OnFragmentListener {
    private final String tag = "TAG_MainActivity ";
    private GridRetainFragment gridRetainFragment;
    private FragmentManager manager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d(tag, "onCreate");
        manager = getFragmentManager();

        if (savedInstanceState == null) {
            startStartFragment();
            startLogoFragment();
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
        if (getFragmentManager().getBackStackEntryCount() > 0) {
            getFragmentManager().popBackStack();
        } else {
            super.onBackPressed();
            Log.d(tag, "onBackPressed");
        }
    }


    @Override
    protected void onDestroy() {

        super.onDestroy();
        Log.d(tag, "onDestroy");
    }

    @Override
    public void startLoginFragment() {

        //begin opening fragment
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.container_dn, new LoginControlsFragment(), "login_ctrl");
        transaction.replace(R.id.container_up, new LoginListFragment(), "login_list");
        transaction.addToBackStack("main");
        //end opening fragment
        transaction.commit();


    }

    @Override
    public void startResultsFragment() {

        //begin opening fragment
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.container_dn, new ResultsFragment());
        transaction.addToBackStack("main");
        //end opening fragment
        transaction.commit();
    }

    @Override
    public void startSignUpFragment() {

        //begin opening fragment
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.container_dn, new SignUpFragment());
        transaction.addToBackStack("main");
        //end opening fragment
        transaction.commit();

    }


    @Override
    public void startStartFragment() {

        StartFragment stF = (StartFragment) manager.findFragmentByTag("start");
        //checking if fragment has been created already
        if (stF == null || !stF.isInLayout()) {
            //if exists
            FragmentTransaction transaction = manager.beginTransaction();
            transaction.add(R.id.container_dn, new StartFragment(), "start");
            transaction.commit();

        } else {

            //if not
            Log.d(tag, "Start Fragment exists");
            FragmentTransaction transaction = manager.beginTransaction();
            transaction.replace(R.id.container_dn, stF);
            transaction.addToBackStack("main");
            transaction.commit();
        }
    }

    public void startLogoFragment() {

        LogoFragment lgF = (LogoFragment) manager.findFragmentByTag("logo");
        //checking if fragment has been created already
        if (lgF == null || !lgF.isInLayout()) {
            //if not exists
            FragmentTransaction transaction = manager.beginTransaction();
            transaction.add(R.id.container_up, new LogoFragment(), "logo");
            transaction.commit();

        } else {

            //if exists
            Log.d(tag, "Logo Fragment exists");
            FragmentTransaction transaction = manager.beginTransaction();
            transaction.replace(R.id.container_up, lgF);
            transaction.addToBackStack("main");
            transaction.commit();
        }
    }

    @Override
    public void startGridFragment() {

        //begin opening fragment
        gridRetainFragment = (GridRetainFragment) manager.findFragmentByTag("retain");
        FragmentTransaction transaction = manager.beginTransaction();
        if (gridRetainFragment == null) {
            gridRetainFragment = new GridRetainFragment();
            transaction.add(gridRetainFragment, "retain");
        }
        transaction.replace(R.id.container_dn, new GameGridFragment());
        transaction.addToBackStack("main");
        //end opening fragment
        transaction.commit();
    }

    @Override
    public void startRulesFragment() {
        //begin opening fragment
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.container_dn, new RulesFragment(), "rules");
        transaction.addToBackStack("main");
        //end opening fragment
        transaction.commit();
    }

}
