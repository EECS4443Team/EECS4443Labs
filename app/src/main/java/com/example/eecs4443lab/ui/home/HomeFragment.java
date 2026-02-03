package com.example.eecs4443lab.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.eecs4443lab.databinding.FragmentHomeBinding;

public class HomeFragment extends Fragment {

    private static final String ARG_USERNAME = "arg_username";
    private FragmentHomeBinding binding;

    public static HomeFragment newInstance(String username) {
        HomeFragment fragmentHome = new HomeFragment();
        Bundle b = new Bundle();
        b.putString(ARG_USERNAME, username);
        fragmentHome.setArguments(b);
        return fragmentHome;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        String username = null;
        if (getArguments() != null) {
            username = getArguments().getString(ARG_USERNAME);
        }
        if (username != null) {
            binding.usernameHome.setText(username);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
