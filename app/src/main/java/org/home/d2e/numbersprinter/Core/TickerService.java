package org.home.d2e.numbersprinter.Core;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;

/**
 * Created by druzhyni on 13.07.2015.
 */
public class TickerService extends IntentService {
    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     * <p/>
     * param name Used to name the worker thread, important only for debugging.
     */
    public static final String TAG = "TAG_TickerService_ ";
    volatile int counter;
    public boolean isActive;


    private boolean doTickI;
    private boolean doSendTickI;

    public static final String TICK_UPDATE = "TickerService.UPDATE";
    MyApp.OnTickListener onTickListener;

    @Override
    public void onCreate() {
        super.onCreate();
        doTickI = true;
        doSendTickI = true;


    }

    public TickerService() {
        super("TickerService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {

        onTickListener = ((MyApp) getApplication()).getOnTickListener();
        ((MyApp) getApplication()).setOnTickModeListener(new MyApp.OnTickModeListener() {
            @Override
            public void doTickSend(boolean doTick, boolean doSendTick) {
                Log.d(TAG, "received: " + doTick + " " + doSendTick);
                if (!doTick) {
                    doSendTickI = doTick;
                }//if we stop chrono, than we must stop send chrono data as well
                doTickI = doTick;

            }

        });
        counter = 0;
        while (doTickI) {
            try {
                Thread.sleep(100L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            counter++;
            if (doSendTickI) {
                if (onTickListener != null) {
                    onTickListener.onNextTick(counter);
                }
            }
            Log.d(TAG, "in ticker: " + doTickI + " " + doSendTickI);
        }

    }


}
