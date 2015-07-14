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

   public interface OnTickModeListener {
       void setTickMode(boolean stopTick, boolean stopSendTick);
   }

    OnTickModeListener onTickModeListener;

    public OnTickModeListener getOnTickModeListener() {
        return onTickModeListener;
    }

    public void setOnTickModeListener(OnTickModeListener onTickModeListener) {
        this.onTickModeListener = onTickModeListener;
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
