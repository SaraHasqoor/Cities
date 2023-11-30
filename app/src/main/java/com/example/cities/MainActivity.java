package com.example.cities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ListView listView = findViewById(R.id.listView);
        setTitle("Simple City List App");
        // Create a list of cities
        List<String> cityList = new ArrayList<>();
        cityList.add("Jerusalem");


        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(
                this, android.R.layout.simple_list_item_1, cityList);

        listView.setAdapter(arrayAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                if(position==0){
                    startActivity(new Intent(MainActivity.this,MainActivity2.class));

                }else {
                    if ((position == 1)) {

                        startActivity(new Intent(MainActivity.this,MainActivity.class));

                    }
                }
            }
        });
    }
}
