package com.gerbugy.alarm.count;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.MotionEvent;

public abstract class CountDownActivity extends AppCompatActivity implements CountDownListener {

    private Messenger mService;

    private final Messenger mMessenger = new Messenger(new CountDownActivityHandler(this));

    private int mSecondsInFinished;
    private int mSecondsInWarning;
    private AlertDialog mDialog;

    private ServiceConnection mConnection = new ServiceConnection() {

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            mService = new Messenger(service);
            sendStart();
        }

        @Override
        public void onServiceDisconnected(ComponentName name) { // 서비스가 crashed 또는 killed 상태일 경우 호출됩니다.
            mService = null;
        }
    };

    protected void setCountDown(int secondsUntilFinished, int secondsInWarning) {
        mSecondsInFinished = secondsUntilFinished;
        mSecondsInWarning = secondsInWarning;
    }

    @Override
    protected void onStart() {
        super.onStart();
        bindService(new Intent(this, CountDownService.class), mConnection, BIND_AUTO_CREATE);
    }

    @Override
    protected void onStop() {
        if (mService != null) {
            unbindService(mConnection); // unbindService()를 호출한다고 onServiceDisconnected()가 호출되지 않습니다.
            mService = null;
        }
        super.onStop();
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            sendStart();
        }
        return super.dispatchTouchEvent(event);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        sendStart();
        return super.onKeyDown(keyCode, event);
    }

    private void sendStart() {
        Message msg = Message.obtain(null, CountDownContext.MSG_START, mSecondsInFinished, mSecondsInWarning);
        msg.replyTo = mMessenger;
        try {
            mService.send(msg);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onCountDown(int seconds) {

    }

    @Override
    public void onCountWarning(int seconds) {

    }

    @Override
    public void onCountFinished() {
        finishAffinity();
    }
}
