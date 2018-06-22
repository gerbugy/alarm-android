package com.gerbugy.alarm.count;

import java.util.ArrayList;
import java.util.List;

public class CountDownRunnableManager {

    private static final CountDownRunnableManager sInstance;

    private final List<CountDownRunnable> mRunnables;

    static {
        sInstance = new CountDownRunnableManager();
    }

    private CountDownRunnableManager() {
        mRunnables = new ArrayList<>();
    }

    public static CountDownRunnableManager getsInstance() {
        return sInstance;
    }

    public void add(CountDownRunnable runnable) {
        for (CountDownRunnable running : mRunnables) {
            running.cancel();
        }
        mRunnables.add(runnable);
    }
}
