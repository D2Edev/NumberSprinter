package org.home.d2e.numbersprinter;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

public class GameEndActivity extends AppCompatActivity {
    private final String tag = "TAG_GameEndActivity ";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_end);
        //defining buttons
        Button btn_NewGame = (Button) findViewById(R.id.btn_NewGame);
        Button btn_GoMainScreen = (Button) findViewById(R.id.btn_GoMainScreen);

        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                switch ((v.getId())) {
                    case R.id.btn_NewGame:
                        Log.d(tag, "NEW GAME pressed");
                        startGameActivity();
                        break;
                    case R.id.btn_GoMainScreen:
                        Log.d(tag, "Main Screen pressed");
                        startMainActivity();
                        break;
                }
            }

        };
        //setting listeners
        btn_NewGame.setOnClickListener(listener);
        btn_GoMainScreen.setOnClickListener(listener);
    }
    //Starting New Game
    private void startGameActivity() {
        Intent intent = new Intent(getBaseContext(),GameActivity.class);
        startActivity(intent);
    }
    //Starting Main Screen
    private void startMainActivity() {
        Intent intent = new Intent(getBaseContext(),MainActivity.class);
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

