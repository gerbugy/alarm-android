package com.gerbugy.alarm.count;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.support.annotation.Nullable;

public class CountDownService extends Service {

    // 앱이 종료된 상태에서도 카운팅이 계속 진행되는 현상 ㅠㅠ
    
    private final Messenger mMessenger = new Messenger(new CountDownServiceHandler(this));

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return mMessenger.getBinder();
    }

    public void handleStart(Message msg) {
        final Messenger replyTo = msg.replyTo;
        CountDownRunnable runnable = new CountDownRunnable(msg.arg1, msg.arg2, new CountDownListener() {

            @Override
            public void onCountDown(int seconds) {
                send(replyTo, Message.obtain(null, CountDownContext.MSG_COUNT_DOWN, seconds, 0));
            }

            @Override
            public void onCountWarning(int seconds) {
                send(replyTo, Message.obtain(null, CountDownContext.MSG_COUNT_WARNING, seconds, 0));
            }

            @Override
            public void onCountFinished() {
                send(replyTo, Message.obtain(null, CountDownContext.MSG_COUNT_FINISHED));
            }
        });
        runnable.start();
        CountDownRunnableManager.getsInstance().add(runnable);
    }

    private void send(Messenger messenger, Message msg) {
        try {
            messenger.send(msg);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }
}