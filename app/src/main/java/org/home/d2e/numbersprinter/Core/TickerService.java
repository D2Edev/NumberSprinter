package org.home.d2e.numbersprinter.Core;

import android.app.IntentService;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
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
    private boolean doTick = true;
    private boolean doSendTick = true;
    private final IBinder tickBinder =new TickBinder();
    MyApp.OnTickListener onTickListener;

    public TickerService() {
        super("TickerService");
    }

    @Override
    public IBinder onBind(Intent intent) {
        doSendTick = true;
        return tickBinder;
    }



    @Override
    public boolean onUnbind(Intent intent) {
        doSendTick = false;
        Log.d(TAG, "Unbound" + counter);
        return super.onUnbind(intent);
    }

    @Override
    public void onRebind(Intent intent) {
        Log.d(TAG, "ReBound" + counter);
        doSendTick = true;
        super.onRebind(intent);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        doTick = true;
        doSendTick = true;
    }


    @Override
    protected void onHandleIntent(Intent intent) {
        onTickListener = ((MyApp) getApplication()).getOnTickListener();
        counter = 0;
        int timeLimit= 6000;
        while (doTick&&counter<timeLimit) {
            try {
                Thread.sleep(100L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            counter++;
            if (doSendTick) {
                //Log.d(TAG, "in ticker: "+counter);
                if (onTickListener != null) {
                    onTickListener.onNextTick(counter);
                }
            }

        }
    }

    public void stopTick() {
        doTick = false;
        Log.d(TAG, "stop received! ");
    }

    public int getCounter() {
        return counter;
    }

    public class TickBinder extends Binder{
        public TickerService getService(){
            return TickerService.this;
        }
    }
}
