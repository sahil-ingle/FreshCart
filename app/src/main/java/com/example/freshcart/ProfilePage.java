package com.example.freshcart;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;


import androidx.appcompat.app.AppCompatActivity;

import com.example.freshcart.databinding.ActivityProfilePageBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Source;

import java.util.HashMap;
import java.util.Map;

public class ProfilePage extends AppCompatActivity {

    private ActivityProfilePageBinding binding;

    private FirebaseFirestore db;
    private FirebaseAuth mAuth;
    private String UserId;

    private String name;
    private String phoneNumber;
    private String address;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityProfilePageBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        UserId = mAuth.getCurrentUser().getUid();

        loadUserInfo();

        checkTextChange(binding.name);
        checkTextChange(binding.phoneNumber);
        checkTextChange(binding.address);

        binding.backBtn.setOnClickListener(v -> {
            Intent intent = new Intent(this, HomePage.class);
            startActivity(intent);
        });


        binding.saveBtn.setOnClickListener(v -> {
            String updatedName = binding.name.getText().toString().trim();
            String updatedPhone = binding.phoneNumber.getText().toString().trim();
            String updatedAddress = binding.address.getText().toString().trim();
            updateUserInfo(updatedName, updatedPhone, updatedAddress);
        });

        binding.logoutBtn.setOnClickListener(v -> {
            mAuth.signOut();

            if (mAuth.getCurrentUser() == null) {
                startActivity(new Intent(this, LoginPage.class));
                finish();
            } else {
                Toast.makeText(ProfilePage.this, "Something gone wrong", Toast.LENGTH_SHORT).show();
            }
        });


    }

    private void loadUserInfo() {
        Source source = Source.CACHE;
        db.collection(UserId).document("UserInfo").get(source).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                DocumentSnapshot doc = task.getResult();

                name = doc.getString("Name");
                phoneNumber = doc.getString("PhoneNumber");
                address = doc.getString("Address");

                binding.name.setText(name);
                binding.phoneNumber.setText(phoneNumber);
                binding.address.setText(address);
            }
        }).addOnFailureListener(e -> Toast.makeText(ProfilePage.this, e.getMessage(), Toast.LENGTH_SHORT).show());
    }


    private void updateUserInfo(String name, String phone, String address) {

        Map<String, Object> data = new HashMap<>();
        data.put("Name", name);
        data.put("Phone", phone);
        data.put("Address", address);

        db.collection(UserId).document("UserInfo").update(data).addOnSuccessListener(unused -> {
            Toast.makeText(ProfilePage.this, "User Info Updated", Toast.LENGTH_SHORT).show();
            binding.saveBtn.setVisibility(View.GONE);
        }).addOnFailureListener(e -> Toast.makeText(ProfilePage.this, e.getMessage(), Toast.LENGTH_SHORT).show());
    }

    private void checkTextChange(EditText inputField) {
        inputField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String updatedName = binding.name.getText().toString().trim();
                String updatedPhone = binding.phoneNumber.getText().toString().trim();
                String updatedAddress = binding.address.getText().toString().trim();

                if (!updatedName.equals(name) || !updatedPhone.equals(phoneNumber) || !updatedAddress.equals(address)) {
                    binding.saveBtn.setVisibility(View.VISIBLE);
                } else {
                    binding.saveBtn.setVisibility(View.GONE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }
}