package org.home.d2e.numbersprinter.Core;

import android.app.IntentService;
import android.content.Intent;

/**
 * Created by druzhyni on 13.07.2015.
 */
public class TickerService extends IntentService {
    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     *
     * param name Used to name the worker thread, important only for debugging.
     */
    public static final String TAG = "TAG_TickerService_ ";
    private boolean stopTick;
    public static final String TICK_UPDATE="TickerService.UPDATE";
    MyApp.OnTickListener onTickListener;

    @Override
    public void onCreate() {
        super.onCreate();



    }

    public TickerService() {
        super("TickerService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        stopTick=false;
        onTickListener = ((MyApp)getApplication()).getOnTickListener();
        ((MyApp)getApplication()).setOnStopTickListener(new MyApp.OnStopTickListener() {
            @Override
            public void onStopTick(boolean doStop) {
                stopTick=doStop;
            }
        });
        int counter = 0;
        while(!stopTick){
            try {
                Thread.sleep(100L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            counter++;
            onTickListener.onNextTick(counter);
        }

    }




}
