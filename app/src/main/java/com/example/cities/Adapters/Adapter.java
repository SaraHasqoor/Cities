package com.example.cities.Adapters;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cities.AddNewplace;
import com.example.cities.MainActivity2;
import com.example.cities.Model.CitiesModel;
import com.example.cities.Utils.DatabaseHandler;
import com.example.cities.R;



import java.util.List;

public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> {

    private List<CitiesModel> citiesList;
    private DatabaseHandler db;
    private MainActivity2 activity;


    public Adapter(DatabaseHandler db, MainActivity2 activity) {
        this.db = db;
        this.activity = activity;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.place_layout, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        db.openDatabase();

        final CitiesModel item = citiesList.get(position);
        holder.place.setText(item.getplace());
        holder.place.setChecked(toBoolean(item.getStatus()));
        holder.place.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    db.updateStatus(item.getId(), 1);
                } else {
                    db.updateStatus(item.getId(), 0);
                }
            }
        });
    }

    private boolean toBoolean(int n) {

        return n != 0;
    }

    @Override
    public int getItemCount() {
        return citiesList.size();
    }

    public Context getContext() {
        return activity;
    }

    public void setplace(List<CitiesModel> citiesList) {
        this.citiesList = citiesList;
        notifyDataSetChanged();
    }

    public void deleteItem(int position) {
        CitiesModel item = citiesList.get(position);
        db.deleteplace(item.getId());
        citiesList.remove(position);
        notifyItemRemoved(position);
    }

    public void editItem(int position) {
        CitiesModel item = citiesList.get(position);
        Bundle bundle = new Bundle();
        bundle.putInt("id", item.getId());
        bundle.putString("place", item.getplace());
        AddNewplace fragment = new AddNewplace();
        fragment.setArguments(bundle);
        fragment.show(activity.getSupportFragmentManager(), AddNewplace.TAG);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        CheckBox place;

        ViewHolder(View view) {
            super(view);
            place = view.findViewById(R.id.citiesCheckBox);
        }
    }
}
