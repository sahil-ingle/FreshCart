package com.example.freshcart;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;


import com.example.freshcart.databinding.ActivityLoginPageBinding;
import com.google.firebase.auth.FirebaseAuth;

public class LoginPage extends AppCompatActivity {

    private FirebaseAuth mAuth;

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

        mAuth = FirebaseAuth.getInstance();



        binding.loginBtn.setOnClickListener(v -> {
            String mEmail = binding.email.getText().toString().trim();
            String mPass = binding.password.getText().toString().trim();

            if (validateInput(mEmail,mPass)){
                login(mEmail,mPass);
            }
        });

    }

    private void login(String email, String password){
        mAuth.signInWithEmailAndPassword(email,password)
                .addOnCompleteListener(this, task ->{
            if (task.isSuccessful()){
                startActivity(new Intent(LoginPage.this, HomePage.class));
                finish();
            }else{
                Toast.makeText(LoginPage.this, "password wrong", Toast.LENGTH_LONG).show();
            }
        });

    }

    private boolean validateInput(String email,String pass){
        if (email.isEmpty()){
            Toast.makeText(LoginPage.this, "Enter email", Toast.LENGTH_LONG).show();
            return false;
        }else if(pass.isEmpty()){
            Toast.makeText(LoginPage.this, "Enter Password", Toast.LENGTH_LONG).show();
            return false;
        }else{
            return true;
        }
    }
}