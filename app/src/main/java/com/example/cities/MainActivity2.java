package com.example.cities;


import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;

import com.example.cities.Adapters.Adapter;
import com.example.cities.Model.CitiesModel;
import com.example.cities.Utils.DatabaseHandler;
import com.google.android.material.floatingactionbutton.FloatingActionButton;


import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class MainActivity2 extends AppCompatActivity implements DialogCloseListener{

    private DatabaseHandler db;

    private RecyclerView placeRecyclerView;
    private Adapter placeAdapter;
    private FloatingActionButton fab;

    private List<CitiesModel>placeList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        Objects.requireNonNull(getSupportActionBar()).hide();

        db = new DatabaseHandler(this);
        db.openDatabase();

        placeRecyclerView = findViewById(R.id.tasksRecyclerView);
        placeRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        placeAdapter =new Adapter(db,MainActivity2.this);
        placeRecyclerView.setAdapter(placeAdapter);

        ItemTouchHelper itemTouchHelper = new
                ItemTouchHelper(new RecyclerItemTouchHelper(placeAdapter));
        itemTouchHelper.attachToRecyclerView(placeRecyclerView);

        fab = findViewById(R.id.fab);

        placeList = db.getAllPlaces();
        Collections.reverse(placeList);

        placeAdapter.setplace(placeList);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddNewplace.newInstance().show(getSupportFragmentManager(), AddNewplace.TAG);
            }
        });
    }

    @Override
    public void handleDialogClose(DialogInterface dialog){
        placeList = db.getAllPlaces();
        Collections.reverse(placeList);
        placeAdapter.setplace(placeList);
        placeAdapter.notifyDataSetChanged();
    }
    }
