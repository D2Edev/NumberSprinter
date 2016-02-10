package io.github.d2edev.numbersprinter.Core;

import android.app.IntentService;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

/**
 * Created by druzhyni on 13.07.2015
 */
public class TickerService extends IntentService {

    public static final String TAG = "TAG_TickerService_ ";

    private boolean doTick = true;
    private boolean doSendTick = true;
    private final IBinder tickBinder =new TickBinder();
    private int timeLimit= 6000;
    private OnTickListener onTickListener;

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
         if(((MyApp)getApplication())!=null){
            onTickListener= ((MyApp)getApplication()).getTickListener();
         }
    }

    @Override
    public boolean onUnbind(Intent intent) {
        doSendTick = false;
        Log.d(TAG, "Unbound");
        onTickListener=null;
        return true;
    }

    @Override
    public void onRebind(Intent intent) {
        doSendTick = true;
        Log.d(TAG, "Rebound");
        if(((MyApp)getApplication())!=null){
            onTickListener= ((MyApp)getApplication()).getTickListener();
        }
        super.onRebind(intent);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        int counter;
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


    public class TickBinder extends Binder{
        public TickerService getService(){
            return TickerService.this;
        }
    }

    private void sendNewTick(int counter){
        if(onTickListener!=null){
            Log.d(TAG,""+counter);
            onTickListener.sendTick();
        }
        Intent intent = new Intent("new_tick");
        intent.putExtra("counter", counter);
        LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
        //Log.d(TAG, "Tick sent");
    }

    public void setOnTickListener(OnTickListener onTickListener) {
        this.onTickListener = onTickListener;
    }
}
