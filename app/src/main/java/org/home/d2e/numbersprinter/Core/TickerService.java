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
    volatile int counter;
    public boolean isActive;


    private boolean stopTickI;
    private boolean stopSendTickI;

    public static final String TICK_UPDATE="TickerService.UPDATE";
    MyApp.OnTickListener onTickListener;

    @Override
    public void onCreate() {
        super.onCreate();
        stopTickI=false;
        stopSendTickI=false;


    }

    public TickerService() {
        super("TickerService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {

        onTickListener = ((MyApp)getApplication()).getOnTickListener();
        ((MyApp)getApplication()).setOnTickModeListener(new MyApp.OnTickModeListener() {
            @Override
            public void setTickMode(boolean stopTick, boolean stopSendTick) {
                if (stopTick){stopSendTickI=stopTick;}//if we stop chrono, than we stop send chrono data as well
                stopTickI=stopTick;
            }

       });
        counter = 0;
        while(!stopTickI){
            try {
                Thread.sleep(100L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            counter++;
            if(!stopSendTickI){onTickListener.onNextTick(counter);}
        }

    }




}
