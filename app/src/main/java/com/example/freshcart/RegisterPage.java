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


import com.example.freshcart.databinding.ActivityRegisterPageBinding;
import com.google.firebase.auth.FirebaseAuth;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegisterPage extends AppCompatActivity {

    private ActivityRegisterPageBinding binding;

    private FirebaseAuth mAuth;

    private boolean isPasswordVisible = false;
    private boolean isConfirmPasswordVisible = false;

    @SuppressLint("ClickableViewAccessibility")
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
            binding.progressBar.setVisibility(View.VISIBLE);

            String mEmail = binding.email.getText().toString().trim();
            String mPassword = binding.password.getText().toString().trim();
            String mConfirmPass = binding.confirmPass.getText().toString().trim();

            if (validateDetails(mEmail, mPassword, mConfirmPass)) {
                signUp(mEmail, mPassword);
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

        binding.confirmPass.setOnTouchListener((v, event) -> {
            final int DRAWABLE_RIGHT = 2; // Right drawable index

            if (event.getAction() == MotionEvent.ACTION_UP) {
                if (event.getRawX() >= (binding.confirmPass.getRight() - binding.confirmPass.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {
                    // Toggle the visibility of the password
                    if (isConfirmPasswordVisible) {
                        // Hide Password
                        binding.confirmPass.setTransformationMethod(PasswordTransformationMethod.getInstance());
                        binding.confirmPass.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_eye_close, 0);
                    } else {
                        // Show Password
                        binding.confirmPass.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                        binding.confirmPass.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_eye, 0);
                    }
                    isConfirmPasswordVisible = !isConfirmPasswordVisible;
                    // Move cursor to the end of the text
                    binding.confirmPass.setSelection(binding.confirmPass.getText().length());
                    return true; // Indicate that the event has been handled
                }
            }
            return false;
        });

    }

    private void signUp(String email, String pass) {
        mAuth.createUserWithEmailAndPassword(email, pass).addOnCompleteListener(this, task -> {
            if (task.isSuccessful()) {
                startActivity(new Intent(this, HomePage.class));
                finish();
            }
        }).addOnFailureListener(e -> {
            binding.progressBar.setVisibility(View.GONE);
            Toast.makeText(RegisterPage.this, e.getMessage(), Toast.LENGTH_SHORT).show();
        });
    }

    private boolean validateDetails(String email, String pass, String confrimPass) {
        if (email.isEmpty()) {
            Toast.makeText(RegisterPage.this, "Enter Email", Toast.LENGTH_LONG).show();
            return false;
        } else if (pass.isEmpty()) {
            Toast.makeText(RegisterPage.this, "Enter Password", Toast.LENGTH_LONG).show();
            return false;
        } else if (confrimPass.isEmpty()) {
            Toast.makeText(RegisterPage.this, "Enter Confirm Password", Toast.LENGTH_LONG).show();
            return false;
        } else if (pass.length() < 8) {
            Toast.makeText(RegisterPage.this, "Password Length must be Greater than 8", Toast.LENGTH_LONG).show();
            return false;
        } else if (!isValidPassword(pass)) {
            Toast.makeText(RegisterPage.this, "Password Must contain at least an Upper/Lower letter, number and symbol", Toast.LENGTH_LONG).show();
            return false;
        } else if (!pass.equals(confrimPass)) {
            Toast.makeText(RegisterPage.this, "Password doesn't Match", Toast.LENGTH_LONG).show();
            return false;
        } else {
            return true;
        }

    }

    public static boolean isValidPassword(final String password) {

        Pattern pattern;
        Matcher matcher;
        final String PASSWORD_PATTERN = "^(?=.*[0-9])(?=.*[A-Z])(?=.*[@#$%^&+=!])(?=\\S+$).{4,}$";
        pattern = Pattern.compile(PASSWORD_PATTERN);
        matcher = pattern.matcher(password);

        return matcher.matches();

    }

}