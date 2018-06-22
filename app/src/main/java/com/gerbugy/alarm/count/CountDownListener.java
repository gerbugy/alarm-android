package com.gerbugy.alarm.count;

public interface CountDownListener {

    void onCountDown(int seconds);

    void onCountWarning(int seconds);

    void onCountFinished();
}