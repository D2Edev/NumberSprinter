package org.home.d2e.numbersprinter;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

public class GameActivity extends AppCompatActivity {
    private final String tag = "TAG_GameActivity ";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        //defining buttons
        Button btn_Cancel = (Button) findViewById(R.id.btn_Cancel);

        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                switch ((v.getId())) {
                    case R.id.btn_Cancel:
                        Log.d(tag, "STOP pressed");

                        startGameEndActivity();
                        break;
                }
            }
        };
        //setting listeners
        btn_Cancel.setOnClickListener(listener);
    }

    //Starting Player Selection/add Screen
    private void startGameEndActivity() {
        Intent intent = new Intent(getBaseContext(),GameEndActivity.class);
        startActivity(intent);
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
}
