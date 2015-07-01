package org.home.d2e.numbersprinter;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

public class PlayerActivity extends AppCompatActivity {
    private final String tag = "TAG_PlayerActivity ";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player);
        Button btn_NewPlr = (Button) findViewById(R.id.btn_NewPlr);
        Button btn_Start = (Button) findViewById(R.id.btn_Start);

        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                switch ((v.getId())) {
                    case R.id.btn_NewPlr:
                        Log.d(tag, "NEW PLAYER pressed");

                        break;
                    case R.id.btn_Start:
                        Log.d(tag, "GAME pressed");

                        break;
                }
            }
        };
        //setting listeners
        btn_NewPlr.setOnClickListener(listener);
        btn_Start.setOnClickListener(listener);
    }
    //Starting New Player add Screen
    //Starting Game Screen

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
}
