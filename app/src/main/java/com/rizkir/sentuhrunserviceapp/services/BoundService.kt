package com.rizkir.sentuhrunserviceapp.services

import android.app.Service
import android.content.Intent
import android.os.Binder
import android.os.IBinder
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.rizkir.sentuhrunserviceapp.CountingActivity.Companion.EXTRA_INTENT
import kotlinx.coroutines.*

class BoundService : Service() {

    companion object {
        private val TAG = BoundService::class.java.simpleName
        private var serviceCallback: ServiceCallback? = null
    }

    private var binder = MyBinder()

    private val serviceJob = Job()
    private val serviceScope = CoroutineScope(Dispatchers.Main + serviceJob)

    override fun onBind(intent: Intent): IBinder {
        Log.d(TAG, "onBind: ")
        serviceScope.launch {
            intent.getStringExtra(EXTRA_INTENT)?.let { serviceCallback?.onServiceCallback(it) }
        }

        return binder
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "onDestroy: ")
    }

    override fun onUnbind(intent: Intent?): Boolean {
        Log.d(TAG, "onUnbind: ")
        serviceJob.cancel()
        return super.onUnbind(intent)

    }

    override fun onRebind(intent: Intent?) {
        super.onRebind(intent)
        Log.d(TAG, "onRebind: ")
    }


    fun setCallback(callback: ServiceCallback) {
        serviceCallback = callback
    }

    internal inner class MyBinder: Binder() {
        fun getService(): BoundService = this@BoundService
    }

    interface ServiceCallback {
        fun onServiceCallback(data: String)
    }
}