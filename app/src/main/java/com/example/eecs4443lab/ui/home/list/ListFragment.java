package com.example.eecs4443lab.ui.home.list;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.eecs4443lab.data.list.ItemFactory;
import com.example.eecs4443lab.data.list.ListItem;
import com.example.eecs4443lab.databinding.FragmentListBinding;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

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

        ItemFactory factory = new ItemFactory();
        List<ListItem> itemList = new ArrayList<>();
        Random random = new Random();
        for (int i = 0; i < 20; i++) {
            ListItem item = (random.nextInt(2) == 0) ? factory.getBook() : factory.getMovie();
            itemList.add(item);
        }

        binding.recycler.setLayoutManager(new LinearLayoutManager(requireContext()));
        binding.recycler.setAdapter(new MyListAdapter(itemList));
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
