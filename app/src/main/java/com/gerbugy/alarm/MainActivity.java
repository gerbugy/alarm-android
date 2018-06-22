package com.gerbugy.alarm;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.View;

import com.gerbugy.alarm.databinding.ActivityMainBinding;
import com.gerbugy.alarm.secure.SecureTimerActivity;

public class MainActivity extends SecureTimerActivity implements View.OnClickListener {

    private ActivityMainBinding mBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setSecureTimer(30, 10);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        mBinding.startActivity.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.start_activity:
                startActivity(new Intent(this, MainActivity.class));
                break;
        }
    }

    @Override
    public void onSecureCounting(int seconds) {
        mBinding.count.setText(String.valueOf(seconds));
    }
}