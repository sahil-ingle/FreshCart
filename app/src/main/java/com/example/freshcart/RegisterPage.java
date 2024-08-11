package com.example.freshcart;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;


import com.example.freshcart.databinding.ActivityRegisterPageBinding;

public class RegisterPage extends AppCompatActivity {

    private ActivityRegisterPageBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRegisterPageBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

    }
}