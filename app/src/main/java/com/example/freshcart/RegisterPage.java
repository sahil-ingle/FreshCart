package com.example.freshcart;

import android.content.Intent;
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

        binding.signInBtn.setOnClickListener(v -> {
            startActivity(new Intent(RegisterPage.this, LoginPage.class));
            overridePendingTransition(0, 0);
            finish();
        });
    }
}