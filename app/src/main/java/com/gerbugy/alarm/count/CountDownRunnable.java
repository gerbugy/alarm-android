package com.gerbugy.alarm.count;

public class CountDownRunnable implements Runnable {

    private final int mSecondsInFinished;
    private final int mSecondsInWarning;

    private final CountDownListener mListener;

    private boolean mCancelled;

    public CountDownRunnable(int secondsUntilFinished, int secondsInWarning, CountDownListener listener) {
        mSecondsInFinished = secondsUntilFinished;
        mSecondsInWarning = secondsInWarning;
        mListener = listener;
    }

    public void start() {
        new Thread(this).start();
    }

    public synchronized void cancel() {
        mCancelled = true;
    }

    @Override
    public void run() {
        for (int seconds = mSecondsInFinished; seconds > 0; seconds--) {
            if (mCancelled) {
                return;
            }
            mListener.onCountDown(seconds);
            if (seconds <= mSecondsInWarning)
                mListener.onCountWarning(seconds);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        mListener.onCountFinished();
    }
}