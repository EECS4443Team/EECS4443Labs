package com.example.eecs4443lab.ui.home;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.eecs4443lab.R;
import com.example.eecs4443lab.databinding.ActivityHomeBinding;
import com.example.eecs4443lab.ui.home.list.ListFragment;

public class HomeActivity extends AppCompatActivity {

    private ActivityHomeBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityHomeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        String username = getIntent().getStringExtra("username");

        if (savedInstanceState == null) {
            moveTo(HomeFragment.newInstance(username));
            binding.bottomNav.setSelectedItemId(R.id.nav_home);
        }

        binding.bottomNav.setOnItemSelectedListener(item -> {
            int id = item.getItemId();
            if (id == R.id.nav_home) {
                moveTo(HomeFragment.newInstance(username));
                return true;
            }
            if (id == R.id.nav_list) {
                moveTo(ListFragment.newInstance("List"));
                return true;
            }
            return false;
        });
    }

    private void moveTo(Fragment fragment) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.nav_host_container, fragment)
                .commit();
    }
}
