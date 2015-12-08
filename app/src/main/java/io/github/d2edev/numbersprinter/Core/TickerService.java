package io.github.d2edev.numbersprinter.Core;

import android.app.IntentService;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.support.v4.content.LocalBroadcastManager;
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
    private int counter;
    private boolean doTick = true;
    private boolean doSendTick = true;
    private final IBinder tickBinder =new TickBinder();
    private int timeLimit= 6000;

    public TickerService() {
        super("TickerService");
    }

    @Override
    public IBinder onBind(Intent intent) {

        doSendTick = true;
        Log.d(TAG, "Bound");
        return tickBinder;
    }



     @Override
    public void onCreate() {
        super.onCreate();
        doTick = true;
        doSendTick = true;
    }

    @Override
    public boolean onUnbind(Intent intent) {
        doSendTick = false;
        Log.d(TAG, "Unbound");
        return true;
    }

    @Override
    public void onRebind(Intent intent) {
        doSendTick = true;
        Log.d(TAG, "Rebound");
        super.onRebind(intent);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        counter = 0;

        while (doTick&&counter<timeLimit) {
            try {
                Thread.sleep(100L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            counter++;
            //Log.d(TAG, "Tick generated");
            if (doSendTick) {sendNewTick(counter);}
            }

        }


    public void stopTick() {
        doTick = false;
        Log.d(TAG, "stop received! ");
    }

public void SendTick(boolean doSendTick){
    this.doSendTick=doSendTick;
}

    public class TickBinder extends Binder{
        public TickerService getService(){
            return TickerService.this;
        }
    }

    private void sendNewTick(int counter){
        Intent intent = new Intent("new_tick");
        intent.putExtra("counter", counter);
        LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
        //Log.d(TAG, "Tick sent");
    }
}
