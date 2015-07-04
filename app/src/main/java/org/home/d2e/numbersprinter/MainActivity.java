package org.home.d2e.numbersprinter;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import org.home.d2e.numbersprinter.Core.OnFragmentListener;

public class MainActivity extends AppCompatActivity implements OnFragmentListener {
    private final String tag = "TAG_MainActivity ";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d(tag, "onCreate");
        if (savedInstanceState==null){showStartFragment();};

    }

    private void showStartFragment() {
        FragmentManager manager = getFragmentManager();
        StartFragment stF = (StartFragment) manager.findFragmentByTag("start");
        //checking if fragment has been created already
                if(stF==null||stF.isInLayout()){
                    FragmentTransaction transaction = manager.beginTransaction();
                    transaction.add(R.id.container_dn, new StartFragment(), "start");
                    transaction.add(R.id.container_up, new LogoFragment());
                    transaction.commit();

                }else {

                    //begin opening fragment
                    Log.d(tag,"Start Fragment exists");
                    FragmentTransaction transaction = manager.beginTransaction();
                    transaction.replace(R.id.container_dn, stF);
                    transaction.addToBackStack("main");
                    transaction.commit();
                }

        //transaction2.commit();
        //end opening fragment

    }

    private void showResultListFragment() {
        FragmentManager manager = getFragmentManager();
        //begin opening fragment
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.container_dn, new ResultListFragment());
        transaction.addToBackStack("main");
        //end opening fragment
        transaction.commit();

    }

    private void showLoginFragment() {
        FragmentManager manager = getFragmentManager();
        LoginFragment lgF = (LoginFragment) manager.findFragmentByTag("login");
        if (lgF==null || lgF.isInLayout()){
            //begin opening fragment
            FragmentTransaction transaction = manager.beginTransaction();
            transaction.replace(R.id.container_dn, new LoginFragment(),"login");
            transaction.addToBackStack("main");
            //end opening fragment
            transaction.commit();
        }else{
            FragmentTransaction transaction = manager.beginTransaction();
            transaction.replace(R.id.container_dn, lgF,"login");
            transaction.addToBackStack("main");
            //end opening fragment
            transaction.commit();
        }


    }

    private void showSignUpFragment() {
        FragmentManager manager = getFragmentManager();
        //begin opening fragment
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.container_dn, new SignUpFragment());
        transaction.addToBackStack("main");
        //end opening fragment
        transaction.commit();
    }

    private void showGameGridFragment() {
        FragmentManager manager = getFragmentManager();
        //begin opening fragment
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.container_dn, new GameGridFragment());
        transaction.addToBackStack("main");
        //end opening fragment
        transaction.commit();

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
        if(getFragmentManager().getBackStackEntryCount()>0){
            getFragmentManager().popBackStack();
        }else{
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

        showLoginFragment();
    }

    @Override
    public void startResultsFragment() {

        showResultListFragment();
    }

    @Override
    public void startSignUpFragment(){

        showSignUpFragment();

    }



    @Override
    public void startStartFragment() {

        showStartFragment();
    }

    @Override
    public void startGridFragment() {

        showGameGridFragment();
    }

}
