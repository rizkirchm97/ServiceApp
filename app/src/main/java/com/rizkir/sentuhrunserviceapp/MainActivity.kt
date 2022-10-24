package com.rizkir.sentuhrunserviceapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.rizkir.sentuhrunserviceapp.databinding.ActivityMainBinding
import com.rizkir.sentuhrunserviceapp.services.BoundService
import com.rizkir.sentuhrunserviceapp.services.NotificationService

class MainActivity : AppCompatActivity(), BoundService.ServiceCallback {
    companion object {
        var EXTRA_INTENT = "Service"
    }
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.mainBtn.setOnClickListener {
            val intent = Intent(this, CountingActivity::class.java)
            startActivity(intent)
        }


    }

    override fun onServiceCallback(data: String) {

    }

}