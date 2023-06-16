package com.example.finalproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;

import com.example.finalproject.Classes.Presence;
import com.example.finalproject.adapter.DayesAdapter;

import java.util.ArrayList;

public class daye extends AppCompatActivity {

    RecyclerView recyclerview_all_dayes;
    DayesAdapter dayesAdapter;
    ArrayList<Presence> data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daye);

        recyclerview_all_dayes = findViewById(R.id.recyclerview_all_dayes);

        data = new ArrayList<>();
        for (int i = 1; i <= 30; i++) {
            data.add(new Presence(i));
        }

        int subjectId = getIntent().getIntExtra("subjectId", 0);
        String selectedMonth = getIntent().getStringExtra("selectedMonth");

        dayesAdapter = new DayesAdapter(this, data, new DayesAdapter.onItemClickListener() {
            @Override
            public void onItemClick(String selectedDay, int position) {
                Intent intent = new Intent(getApplicationContext(), StudentInSubject.class);
                intent.putExtra("selectedMonth", selectedMonth);
                intent.putExtra("subjectId", subjectId);
                intent.putExtra("selectedDay", selectedDay);
                startActivity(intent);
            }
        });

        recyclerview_all_dayes.setAdapter(dayesAdapter);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 3);
        recyclerview_all_dayes.setLayoutManager(gridLayoutManager);

    }
}