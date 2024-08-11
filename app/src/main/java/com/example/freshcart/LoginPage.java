package com.example.freshcart;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.widget.TintableCompoundDrawablesView;


import com.example.freshcart.databinding.ActivityLoginPageBinding;
import com.google.firebase.auth.FirebaseAuth;

public class LoginPage extends AppCompatActivity {

    private FirebaseAuth mAuth;

    private ActivityLoginPageBinding binding;
    private boolean isPasswordVisible = false;

    @SuppressLint("ClickableViewAccessibility")
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

        mAuth = FirebaseAuth.getInstance();


        binding.loginBtn.setOnClickListener(v -> {

            binding.progressBar.setVisibility(View.VISIBLE);
            String mEmail = binding.email.getText().toString().trim();
            String mPass = binding.password.getText().toString().trim();

            if (validateInput(mEmail, mPass)) {
                login(mEmail, mPass);
            } else {
                binding.progressBar.setVisibility(View.GONE);
            }
        });


        binding.password.setOnTouchListener((v, event) -> {
            final int DRAWABLE_RIGHT = 2; // Right drawable index

            if (event.getAction() == MotionEvent.ACTION_UP) {
                if (event.getRawX() >= (binding.password.getRight() - binding.password.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {
                    // Toggle the visibility of the password
                    if (isPasswordVisible) {
                        // Hide Password
                        binding.password.setTransformationMethod(PasswordTransformationMethod.getInstance());
                        binding.password.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_eye_close, 0);
                    } else {
                        // Show Password
                        binding.password.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                        binding.password.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_eye, 0);
                    }
                    isPasswordVisible = !isPasswordVisible;
                    // Move cursor to the end of the text
                    binding.password.setSelection(binding.password.getText().length());
                    return true; // Indicate that the event has been handled
                }
            }
            return false;
        });

    }

    private void login(String email, String password) {
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        startActivity(new Intent(LoginPage.this, HomePage.class));
                        finish();
                    } else {
                        binding.progressBar.setVisibility(View.GONE);
                        Toast.makeText(LoginPage.this, "password wrong", Toast.LENGTH_LONG).show();
                    }
                });

    }

    private boolean validateInput(String email, String pass) {
        if (email.isEmpty()) {
            Toast.makeText(LoginPage.this, "Enter email", Toast.LENGTH_LONG).show();
            return false;
        } else if (pass.isEmpty()) {
            Toast.makeText(LoginPage.this, "Enter Password", Toast.LENGTH_LONG).show();
            return false;
        } else {
            return true;
        }
    }
}