package com.rizkir.sentuhrunserviceapp.services

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.util.Log
import kotlinx.coroutines.*

class NotificationService : Service() {

    companion object {
        internal val TAG = NotificationService::class.java.simpleName
    }

    private val serviceJob = Job()
    private val serviceScope = CoroutineScope(Dispatchers.Main + serviceJob)

    override fun onBind(intent: Intent): IBinder {
        throw UnsupportedOperationException("Not yet implemented")
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Log.d(TAG, "Starting Service...")

        serviceScope.launch {
            repeat(50) {
                delay(1000L)
            }
            stopSelf()
        }
//        return super.onStartCommand(intent, flags, startId)

        return START_STICKY
    }

    override fun onDestroy() {
        super.onDestroy()
        serviceJob.cancel()
    }
}