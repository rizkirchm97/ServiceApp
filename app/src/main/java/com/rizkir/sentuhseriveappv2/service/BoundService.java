package com.rizkir.sentuhseriveappv2.service;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Parcelable;
import android.os.ResultReceiver;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.lifecycle.MutableLiveData;

import com.rizkir.sentuhseriveappv2.utils.CustomResultReceiver;

import kotlinx.coroutines.flow.MutableStateFlow;

/**
 * created by RIZKI RACHMANUDIN on 25/10/2022
 */
public class BoundService extends Service {
    private ServiceCallback serviceCallback;

    public BoundService() {
        super();
    }

    public static final String TAG = BoundService.class.getSimpleName();



    public MutableLiveData<String> message = new MutableLiveData<>();



    private final MyBinder binder = new MyBinder();

    private Thread thread;


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        thread = new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 10; i++) {
                    try {
                        Thread.sleep(1000);
                            message.postValue("Result: " + i);

                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

//                    if (serviceCallback != null) {
                        serviceCallback.onServiceCallback(message);
//                    }

                }
            }
        });

        thread.start();
        return binder;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy: ");
    }

    @Override
    public boolean onUnbind(Intent intent) {
        Log.d(TAG, "onUnbind: ");
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return super.onUnbind(intent);
    }

    @Override
    public void onRebind(Intent intent) {
        super.onRebind(intent);
        Log.d(TAG, "onRebind: ");
    }
    public void setCallback(ServiceCallback serviceCallback) {
        this.serviceCallback = serviceCallback;
    }

    public class MyBinder extends android.os.Binder {
        public BoundService getService() {
            return BoundService.this;
        }
    }

    public interface ServiceCallback {
        void onServiceCallback(MutableLiveData<String> message);
        void onIntentCallback(Context conte);
    }
}
