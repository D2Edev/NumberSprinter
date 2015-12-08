package io.github.d2edev.numbersprinter.Core;

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
       void doTickSend(boolean doTick, boolean doSendTick);
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
