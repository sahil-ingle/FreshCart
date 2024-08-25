package com.example.freshcart;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import com.example.freshcart.R;

import com.example.freshcart.databinding.ActivityHomePageBinding;
import com.google.firebase.auth.FirebaseAuth;


public class HomePage extends AppCompatActivity {

    private ActivityHomePageBinding binding;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityHomePageBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        mAuth = FirebaseAuth.getInstance();

        replaceFragment(new HomeFragment());


        binding.profileBtn.setOnClickListener(v -> {
            Intent intent = new Intent(this, ProfilePage.class);
            startActivity(intent);
        });

        binding.navBar.setOnItemSelectedListener( item -> {

            if (item.getItemId() == R.id.home){
                replaceFragment(new HomeFragment());
            }else if ( item.getItemId() == R.id.settings){
                replaceFragment(new SettingsFragment());
            }

            return true;
        });

    }

    private void replaceFragment(Fragment fragment){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frameLayout, fragment);
        fragmentTransaction.commit();
    }

}