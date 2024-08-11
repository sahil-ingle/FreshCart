package com.example.freshcart;

import android.content.Intent;
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

        binding.signUpBtn.setOnClickListener(v1 -> {
            startActivity(new Intent(LoginPage.this, RegisterPage.class));
            overridePendingTransition(0, 0);
            finish();
        });

    }


}