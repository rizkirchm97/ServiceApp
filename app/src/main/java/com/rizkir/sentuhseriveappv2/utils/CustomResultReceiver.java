package com.rizkir.sentuhseriveappv2.utils;

import android.os.Bundle;
import android.os.Handler;
import android.os.ResultReceiver;

/**
 * created by RIZKI RACHMANUDIN on 28/10/2022
 */
public class CustomResultReceiver extends ResultReceiver {
    private Receiver receiver;
    public CustomResultReceiver(Handler handler, Receiver receiver) {
        super(handler);
        this.receiver = receiver;
    }

    @Override
    protected void onReceiveResult(int resultCode, Bundle resultData) {
//        super.onReceiveResult(resultCode, resultData);
        if (receiver != null) {
            receiver.onReceiveResult(resultCode, resultData);
        }

    }

    public interface Receiver {
        void onReceiveResult(int resultCode, Bundle resultData);
    }
}
