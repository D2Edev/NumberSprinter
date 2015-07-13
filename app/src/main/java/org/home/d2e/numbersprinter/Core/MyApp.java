package org.home.d2e.numbersprinter.Core;

import android.app.Application;

/**
 * Created by druzhyni on 13.07.2015.
 */
public class MyApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
    }

   public interface OnStopTickListener{
       void onStopTick (boolean doStop);
   }

    OnStopTickListener onStopTickListener;

    public OnStopTickListener getOnStopTickListener() {
        return onStopTickListener;
    }

    public void setOnStopTickListener(OnStopTickListener onStopTickListener) {
        this.onStopTickListener = onStopTickListener;
    }

    public interface OnTickListener {
        void onNextTick(int counter);
    }

    OnTickListener onTickListener;

    public OnTickListener getOnTickListener() {
        return onTickListener;
    }

    public void setOnTickListener(OnTickListener onTickListener) {
        this.onTickListener = onTickListener;
    }
}
