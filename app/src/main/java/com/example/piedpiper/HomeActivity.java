package com.example.piedpiper;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.piedpiper.databinding.ActivityHomeBinding;

public class HomeActivity extends AppCompatActivity {

    ActivityHomeBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityHomeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        replaceFragment(new HomeFragment());

        String username = getIntent().getStringExtra("username");

        // Save username in SharedPreferences
        SharedPreferences prefs = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("username", username);
        editor.apply();

        binding.bottomNavigationView.setOnItemSelectedListener(item -> {
            int itemId = item.getItemId();
            if (itemId == R.id.homepage) {
                replaceFragment(new HomeFragment());
            } else if (itemId == R.id.friendlikes) {
                replaceFragment(new FriendsFragment());
            } else if (itemId == R.id.addrecipe) {
                replaceFragment(new addrecipeFragment());
            } else if (itemId == R.id.mealplans) {
                replaceFragment(new mealplanFragment());
            } else if (itemId == R.id.profilepage) {
                replaceFragment(new profileFragment());
            }
            return true;
        });


    }

    private void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frameLayout2, fragment);
        fragmentTransaction.commit();
    }
    @Override
    public void onBackPressed() {
        // Handle back button press here (if needed)
        // In this example, we let the back button behave normally (go back to previous screen)
        super.onBackPressed();
    }
}
