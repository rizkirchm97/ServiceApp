package com.rizkir.sentuhseriveappv2.base;

import android.app.Activity;
import android.app.Application;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;

import androidx.appcompat.app.AppCompatActivity;

import com.rizkir.sentuhseriveappv2.R;
import com.rizkir.sentuhseriveappv2.service.BoundService;
import com.rizkir.sentuhseriveappv2.ui.main.MainActivity;

/**
 * created by RIZKI RACHMANUDIN on 25/10/2022
 */
public abstract class SentuhServiceBase extends AppCompatActivity{

    public static SentuhServiceBase newInstance() {
        return new SentuhServiceBase() {
            @Override
            public void onServiceAttached(BoundService boundService) {

            }
        };
    }

    protected Boolean boundStatus = false;
    private final ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            BoundService.MyBinder binder = (BoundService.MyBinder) service;
            BoundService boundService = binder.getService();
            boundStatus = true;
            onServiceAttached(boundService);
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            boundStatus = false;
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


    @Override
    protected void onStart() {
        super.onStart();
//        attachService(SentuhServiceBase.this);

    }

    @Override
    protected void onStop() {
        super.onStop();
        if (boundStatus) {
            unbindService(serviceConnection);
            boundStatus = false;
        }
    }

    protected void attachService(Context context) {
        final Intent serviceIntent = new Intent(context, BoundService.class);
        bindService(serviceIntent, serviceConnection, BIND_AUTO_CREATE);
    }

    protected void detachService(Context context) {
        final Intent serviceIntent = new Intent(context, BoundService.class);
        unbindService(serviceConnection);
    }


    public abstract void onServiceAttached(BoundService boundService);
}

