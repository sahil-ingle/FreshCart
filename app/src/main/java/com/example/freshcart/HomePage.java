package com.example.freshcart;

import android.os.Bundle;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;


import com.example.freshcart.databinding.ActivityHomePageBinding;


public class HomePage extends AppCompatActivity {

    private ActivityHomePageBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityHomePageBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
    }
}