package com.rizkir.sentuhseriveappv2.utils;

import static android.content.Context.BIND_AUTO_CREATE;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.MutableLiveData;

import com.rizkir.sentuhseriveappv2.databinding.CustomDialogFragmentBinding;
import com.rizkir.sentuhseriveappv2.service.BoundService;

import java.util.Objects;


/**
 * created by RIZKI RACHMANUDIN on 26/10/2022
 */
public class CustomDialogFragment extends DialogFragment implements BoundService.ServiceCallback {

    public static CustomDialogFragment newInstance() {
        return new CustomDialogFragment();
    }

    public CustomDialogFragment() {
        super();
    }
    private Boolean boundStatus = false;
    private BoundService boundService;
    private final ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            BoundService.MyBinder binder = (BoundService.MyBinder) service;
            boundService = binder.getService();
            boundStatus = true;
            boundService.setCallback(CustomDialogFragment.this);
//            getDataFromCallback();
//            getData();
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            boundStatus = false;
        }
    };

    private Callback callback;
    private CustomDialogFragmentBinding binding;
    private static BoundService.ServiceCallback serviceCallback;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = CustomDialogFragmentBinding.inflate(inflater, container, false);

        final Intent serviceIntent = new Intent(requireContext(), BoundService.class);
        requireContext().bindService(serviceIntent, serviceConnection, BIND_AUTO_CREATE);

        Objects.requireNonNull(getDialog()).getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        Objects.requireNonNull(getDialog()).setCanceledOnTouchOutside(false);

        binding.dialogBtn.setOnClickListener(v -> {
            String valueText = binding.tvDialog.getText().toString();
            if (callback !=null) {
                callback.onActionClick(valueText);
            }
            requireContext().unbindService(serviceConnection);
            dismiss();
        });

        return binding.getRoot();



    }

    @Override
    public void onStart() {
        super.onStart();
        Window window = Objects.requireNonNull(getDialog()).getWindow();
        window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        window.setGravity(Gravity.CENTER);

        ConstraintLayout.LayoutParams params = (ConstraintLayout.LayoutParams) binding.llMain.getLayoutParams();
        params.rightToRight = ConstraintLayout.LayoutParams.PARENT_ID;
        params.leftToLeft = ConstraintLayout.LayoutParams.PARENT_ID;
        params.bottomToBottom = ConstraintLayout.LayoutParams.PARENT_ID;
        params.topToTop = ConstraintLayout.LayoutParams.PARENT_ID;
        binding.llMain.setLayoutParams(params);
//        params.setLayoutDirection();

    }

    @Override
    public void onStop() {
        super.onStop();
//        if (boundStatus) {
//            requireContext().unbindService(serviceConnection);
//            boundStatus = false;
//        }
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            callback = (Callback) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + " must implement Callback");
//            callback = null;
        }
    }

    @Override
    public void onServiceCallback(MutableLiveData<String> message) {
        requireActivity().runOnUiThread(() -> {
            message.observe(CustomDialogFragment.this, s -> {
                binding.tvDialog.setText(s);
            });
        });

    }

    @Override
    public void onIntentCallback(Context conte) {

    }

    public interface Callback {
        public void onActionClick(String data);
    }
}
