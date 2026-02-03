package com.example.eecs4443lab.ui.home.list;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.eecs4443lab.databinding.FragmentListBinding;

import java.util.ArrayList;
import java.util.List;

public class ListFragment extends Fragment {

    private static final String ARG_TITLE = "arg_title";
    private FragmentListBinding binding;

    public static ListFragment newInstance(String title) {
        ListFragment f = new ListFragment();
        Bundle b = new Bundle();
        b.putString(ARG_TITLE, title);
        f.setArguments(b);
        return f;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentListBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        String title = "List";
        if (getArguments() != null && getArguments().getString(ARG_TITLE) != null) {
            title = getArguments().getString(ARG_TITLE);
        }

        List<String> items = new ArrayList<>();
        for (int i = 1; i <= 20; i++) {
            items.add(title + " - item" + i);
        }

        binding.recycler.setLayoutManager(new LinearLayoutManager(requireContext()));
        binding.recycler.setAdapter(new MyListAdapter(items));
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
