package com.example.aeroclubapp.fragments.aeroclub;

import android.os.Bundle;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.aeroclubapp.ListAeroclubAdapter;
import com.example.aeroclubapp.ListAeroclubData;
import com.example.aeroclubapp.R;
import com.example.aeroclubapp.databinding.FragmentAeroclubBinding;
import java.util.ArrayList;

public class AeroclubFragment extends Fragment {
    private FragmentAeroclubBinding binding2;
    private ListAeroclubAdapter listAdapter;
    private ArrayList<ListAeroclubData> dataArrayList = new ArrayList<>();
    private ListAeroclubData listData;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding2 = FragmentAeroclubBinding.inflate(inflater, container, false);
        return binding2.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        int[] imageList = {R.drawable.parachutisme, R.drawable.ulm, R.drawable.bapteme, R.drawable.lecon};
        int[] descList = {R.string.parachutisme, R.string.ulm, R.string.bapteme, R.string.pilotage};
        String[] nameList = {"Parachutisme", "ULM", "Baptêmes de l'air", "Leçons de pilotage"};

        for (int i = 0; i < imageList.length; i++) {
            listData = new ListAeroclubData(nameList[i], descList[i], imageList[i]);
            dataArrayList.add(listData);
        }
        listAdapter = new ListAeroclubAdapter(requireActivity(), dataArrayList);
        binding2.listView.setAdapter(listAdapter);
        binding2.listView.setClickable(true);

        binding2.listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(requireActivity(), DetailedAeroclubActivity.class);
                intent.putExtra("name", nameList[i]);
                intent.putExtra("desc", descList[i]);
                intent.putExtra("image", imageList[i]);
                startActivity(intent);
            }
        });
    }
}