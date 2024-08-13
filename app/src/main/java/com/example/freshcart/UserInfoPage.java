package com.example.freshcart;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.freshcart.databinding.ActivityUserInfoPageBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class UserInfoPage extends AppCompatActivity {


    private ActivityUserInfoPageBinding binding;

    private FirebaseFirestore db;

    private String userID;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityUserInfoPageBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        userID = mAuth.getCurrentUser().getUid();

        binding.continueBtn.setOnClickListener(v -> {

            binding.progressBar.setVisibility(View.VISIBLE);

            String mName = binding.name.getText().toString().trim();
            String mPhone = binding.phoneNumber.getText().toString().trim();
            String mAddress = binding.address.getText().toString().trim();

            if (!mName.isEmpty() && !mPhone.isEmpty() && !mAddress.isEmpty()) {
                addInfo(mName, mPhone, mAddress);
            } else {
                binding.progressBar.setVisibility(View.GONE);
            }
        });

    }

    private void addInfo(String name, String phone, String address) {
        Map<String, Object> userInfo = new HashMap<>();
        userInfo.put("Name", name);
        userInfo.put("PhoneNumber", phone);
        userInfo.put("Address", address);

        db.collection(userID).document("UserInfo").set(userInfo).addOnSuccessListener(unused -> {
            Toast.makeText(UserInfoPage.this, "User info saved", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(UserInfoPage.this, HomePage.class));
            finish();
        }).addOnFailureListener(e -> {
            Toast.makeText(UserInfoPage.this, e.getMessage(), Toast.LENGTH_SHORT).show();
            binding.progressBar.setVisibility(View.GONE);
        });

    }
}