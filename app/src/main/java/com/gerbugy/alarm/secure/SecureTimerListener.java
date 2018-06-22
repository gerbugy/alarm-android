package com.gerbugy.alarm.secure;

public interface SecureTimerListener {

    void onSecureCounting(int seconds);

    void onSecureWarning(int seconds);

    void onSecureFinished();
}