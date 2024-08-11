package com.example.freshcart;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;


import com.example.freshcart.databinding.ActivityLoginPageBinding;

public class LoginPage extends AppCompatActivity {

    private ActivityLoginPageBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginPageBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

    }

    
}