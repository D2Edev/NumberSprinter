package io.github.d2edev.numbersprinter.Core;

import android.app.Application;


public class MyApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
    }

    private OnTickListener tickListener;

    public OnTickListener getTickListener() {
        return tickListener;
    }

    public void setTickListener(OnTickListener tickListener) {
        this.tickListener = tickListener;
    }
}
