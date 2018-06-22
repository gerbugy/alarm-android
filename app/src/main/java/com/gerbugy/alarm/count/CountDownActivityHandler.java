package com.gerbugy.alarm.count;

import android.os.Handler;
import android.os.Message;

public class CountDownActivityHandler extends Handler implements CountDownContext {

    private final CountDownListener mListener;

    public CountDownActivityHandler(CountDownListener listener) {
        mListener = listener;
    }

    @Override
    public void handleMessage(Message msg) {
        switch (msg.what) {
            case MSG_COUNT_DOWN:
                mListener.onCountDown(msg.arg1);
                break;
            case MSG_COUNT_WARNING:
                mListener.onCountWarning(msg.arg1);
                break;
            case MSG_COUNT_FINISHED:
                mListener.onCountFinished();
                break;
            default:
                super.handleMessage(msg);
        }
    }
}