package com.gerbugy.alarm.secure;

import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.MotionEvent;

public abstract class SecureTimerActivity extends AppCompatActivity implements SecureTimerListener {

    private int mSecondsInFinished;
    private int mSecondsInWarning;
    private SecureTimerHandler mHandler;

    protected void setSecureTimer(int secondsUntilFinished, int secondsInWarning) {
        mSecondsInFinished = secondsUntilFinished;
        mSecondsInWarning = secondsInWarning;
        mHandler = new SecureTimerHandler(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        startTimer();
    }

    @Override
    protected void onStop() {
        mHandler.sendEmptyMessage(SecureTimerContext.MSG_STOP);
        super.onStop();
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            startTimer();
        }
        return super.dispatchTouchEvent(event);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        startTimer();
        return super.onKeyDown(keyCode, event);
    }

    private void startTimer() {
        mHandler.sendMessage(Message.obtain(null, SecureTimerContext.MSG_START, mSecondsInFinished, mSecondsInWarning));
    }

    @Override
    public void onSecureCounting(int seconds) {

    }

    @Override
    public void onSecureWarning(int seconds) {

    }

    @Override
    public void onSecureFinished() {
        finishAffinity();
    }
}
