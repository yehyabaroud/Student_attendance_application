package com.example.finalproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class showMonth extends AppCompatActivity {

    ListView list_show_all_month;
    private List<String> monthList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_month);

        list_show_all_month = findViewById(R.id.list_show_all_month);
        int subjectId = getIntent().getIntExtra("subjectId", 0);

        monthList = new ArrayList<>();
        Calendar calendar = Calendar.getInstance();
        for (int i = 0; i < 12; i++) {
            calendar.set(Calendar.MONTH, i);
            String monthName = calendar.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.getDefault());
            monthList.add(monthName);
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, monthList);
        list_show_all_month.setAdapter(adapter);

        list_show_all_month.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String selectedMonth = monthList.get(position);

                Intent intent = new Intent(showMonth.this,daye.class);
                intent.putExtra("selectedMonth", selectedMonth);
                intent.putExtra("subjectId", subjectId);
                startActivity(intent);
            }
        });

    }
}