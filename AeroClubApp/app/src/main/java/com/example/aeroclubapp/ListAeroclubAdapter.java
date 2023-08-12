package com.example.aeroclubapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

public class ListAeroclubAdapter extends ArrayAdapter<ListAeroclubData> {
    public ListAeroclubAdapter(@NonNull Context context, ArrayList<ListAeroclubData> dataArrayList) {
        super(context, R.layout.list_aeroclub_item, dataArrayList);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View view, @NonNull ViewGroup parent) {
        ListAeroclubData data = getItem(position);
        if (view == null) {
            view = LayoutInflater.from(getContext()).inflate(R.layout.list_aeroclub_item, parent, false);
        }

        ImageView listImage = view.findViewById(R.id.listAeroclubImage);
        TextView listName = view.findViewById(R.id.listName);

        listImage.setImageResource(data.image);
        listName.setText(data.name);

        return view;
    }
}
