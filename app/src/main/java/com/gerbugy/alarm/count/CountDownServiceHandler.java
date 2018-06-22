package com.gerbugy.alarm.count;

import android.os.Handler;
import android.os.Message;

import java.lang.ref.WeakReference;

public class CountDownServiceHandler extends Handler implements CountDownContext {

    private final WeakReference<CountDownService> mReference;

    public CountDownServiceHandler(CountDownService service) {
        mReference = new WeakReference<>(service);
    }

    @Override
    public void handleMessage(Message msg) {
        CountDownService service = mReference.get();
        switch (msg.what) {
            case MSG_START:
                if (service != null) {
                    service.handleStart(msg);
                }
                break;
            default:
                super.handleMessage(msg);
        }
    }
}