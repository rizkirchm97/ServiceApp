package com.rizkir.sentuhseriveappv2.utils;

import android.os.Handler;
import android.os.Message;

import androidx.annotation.NonNull;

/**
 * created by RIZKI RACHMANUDIN on 28/10/2022
 */
public class CustomHandler extends Handler {

    public static final int REPORT_MSG = 1;

    @Override
    public void handleMessage(@NonNull Message msg) {
        switch (msg.what) {
            case REPORT_MSG : {

            }
        }
        super.handleMessage(msg);
    }
}
