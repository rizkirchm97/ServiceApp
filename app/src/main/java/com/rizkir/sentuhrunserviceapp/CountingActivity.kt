package com.rizkir.sentuhrunserviceapp

import android.content.ComponentName
import android.content.Intent
import android.content.ServiceConnection
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.IBinder
import com.rizkir.sentuhrunserviceapp.databinding.ActivityCountingBinding
import com.rizkir.sentuhrunserviceapp.services.BoundService
import com.rizkir.sentuhrunserviceapp.utils.CallbackListener

class CountingActivity : AppCompatActivity(), BoundService.ServiceCallback {

    companion object {
        var EXTRA_INTENT = "Service"
    }


    private lateinit var binding: ActivityCountingBinding
    private var boundStatus = false
    private lateinit var boundService: BoundService
    private val connection = object : ServiceConnection {
        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
            val binder = service as BoundService.MyBinder
            boundService = binder.getService()
            boundStatus = true
            boundService.setCallback(this@CountingActivity)
        }

        override fun onServiceDisconnected(name: ComponentName?) {
            boundStatus = false
        }

    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCountingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val serviceIntent = Intent(this, BoundService::class.java)
        serviceIntent.putExtra(EXTRA_INTENT, "Bound Service")
        bindService(serviceIntent, connection, BIND_AUTO_CREATE)


        binding.countingBtn.setOnClickListener {
            unbindService(connection)
        }
    }


    override fun onStop() {
        super.onStop()
        if (boundStatus) {
            unbindService(connection)
            boundStatus = false
        }
    }

    override fun onServiceCallback(data: String) {
        binding.countingTv.text = data
    }

}