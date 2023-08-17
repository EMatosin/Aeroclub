package com.example.aeroclubapp.fragments.basicservice;

import android.os.Bundle;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.aeroclubapp.ListBasicServiceAdapter;
import com.example.aeroclubapp.ListBasicServiceData;
import com.example.aeroclubapp.R;
import com.example.aeroclubapp.databinding.FragmentBasicServiceBinding;
import java.util.ArrayList;

public class BasicServiceFragment extends Fragment {
    private FragmentBasicServiceBinding binding2;
    private ListBasicServiceAdapter listAdapter;
    private ArrayList<ListBasicServiceData> dataArrayList = new ArrayList<>();
    private ListBasicServiceData listData;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding2 = FragmentBasicServiceBinding.inflate(inflater, container, false);
        return binding2.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        int[] imageList = {R.drawable.stationnement, R.drawable.ravitaillement, R.drawable.nettoyage, R.drawable.meteo};
        int[] descList = {R.string.stationnement, R.string.ravitaillement, R.string.nettoyage, R.string.meteo};
        String[] nameList = {"Stationnement", "Ravitaillement", "Nettoyage intérieur", "Informations météorologiques"};

        for (int i = 0; i < imageList.length; i++) {
            listData = new ListBasicServiceData(nameList[i], descList[i], imageList[i]);
            dataArrayList.add(listData);
        }
        listAdapter = new ListBasicServiceAdapter(requireActivity(), dataArrayList);
        binding2.listView.setAdapter(listAdapter);
        binding2.listView.setClickable(true);

        binding2.listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(requireActivity(), DetailedBasicServiceActivity.class);
                intent.putExtra("name", nameList[i]);
                intent.putExtra("desc", descList[i]);
                intent.putExtra("image", imageList[i]);
                startActivity(intent);
            }
        });
    }
}