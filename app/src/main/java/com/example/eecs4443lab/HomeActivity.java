package com.example.eecs4443lab;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.eecs4443lab.databinding.ActivityHomeBinding;

public class HomeActivity extends AppCompatActivity {
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        com.example.eecs4443lab.databinding.ActivityHomeBinding binding = ActivityHomeBinding.inflate(getLayoutInflater());
        try {
            setContentView(binding.getRoot());
        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        }
        TextView usernameTextView = findViewById(R.id.username_home);
        String username = getIntent().getStringExtra("username");
        if (username != null) {
            usernameTextView.setText(username);
        }
    }
}
