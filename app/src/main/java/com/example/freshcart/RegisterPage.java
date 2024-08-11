package com.example.freshcart;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;


import com.example.freshcart.databinding.ActivityRegisterPageBinding;
import com.google.firebase.auth.FirebaseAuth;

public class RegisterPage extends AppCompatActivity {

    private ActivityRegisterPageBinding binding;

    private FirebaseAuth mAuth;

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

        mAuth = FirebaseAuth.getInstance();

        binding.registerBtn.setOnClickListener(v -> {
            String mEmail = binding.email.getText().toString().trim();
            String mPassword = binding.password.getText().toString().trim();
            String mConfirmPass = binding.confirmPass.getText().toString().trim();

            if (validateDetails(mEmail, mPassword, mConfirmPass)) {
                signUp(mEmail, mPassword);
            }
        });

    }

    private void signUp(String email, String pass) {
        mAuth.createUserWithEmailAndPassword(email, pass).addOnCompleteListener(this, task -> {
            if (task.isSuccessful()) {
                startActivity(new Intent(this, HomePage.class));
                finish();
            } else {
                Toast.makeText(RegisterPage.this, "Wrong credietnails", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private boolean validateDetails(String email, String pass, String confrimPass) {
        if (email.isEmpty() || pass.isEmpty() || confrimPass.isEmpty()){
            Toast.makeText(RegisterPage.this, "Empty Credentials", Toast.LENGTH_LONG).show();
            return false;
        }else if (!pass.matches(confrimPass)){
            Toast.makeText(RegisterPage.this, "Password doesn't Match", Toast.LENGTH_LONG).show();
            return false;
        }else{
            return true;
        }

    }

}