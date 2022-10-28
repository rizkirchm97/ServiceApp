package com.rizkir.sentuhseriveappv2.ui.main;


import android.os.Bundle;

import com.rizkir.sentuhseriveappv2.base.SentuhServiceBase;
import com.rizkir.sentuhseriveappv2.databinding.ActivityMainBinding;
import com.rizkir.sentuhseriveappv2.service.BoundService;
import com.rizkir.sentuhseriveappv2.utils.CustomDialogFragment;

public class MainActivity extends SentuhServiceBase implements CustomDialogFragment.Callback {

    private static final String TAG  = "CUSTOM_DIALOG";
    private ActivityMainBinding binding;
    BoundService.ServiceCallback serviceCallback;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        binding.mainBtn.setOnClickListener(v -> {

            final CustomDialogFragment dialog = CustomDialogFragment.newInstance();
            dialog.show(getSupportFragmentManager(), TAG);

        });
    }

    @Override
    public void onServiceAttached(BoundService boundService) {

    }

    @Override
    public void onActionClick(String data) {
        binding.tvMessage.setText(data);
    }
}