package com.example.finalproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.TextView;

import com.example.finalproject.Classes.Students;
import com.example.finalproject.Classes.Subject;
import com.example.finalproject.DbHelber.DpHelper;
import com.example.finalproject.adapter.Subject_StudentIdAdapter;

import java.util.ArrayList;

public class studentInformation extends AppCompatActivity {

    TextView tvName, tvBarthDay, tvAge;
    RecyclerView subjectRecyclerView;
    Subject_StudentIdAdapter subject_studentIdAdapter;
    DpHelper dpHelper;
    Students student;
    ArrayList<Subject> data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_information);

        tvName = findViewById(R.id.tvName);
        tvBarthDay = findViewById(R.id.tvBarthDay);
        tvAge = findViewById(R.id.tvAge);
        int studentId = getIntent().getIntExtra("studentId", 0);


        dpHelper = new DpHelper(getApplicationContext());
        student = dpHelper.getStudentId(studentId);

        if (student != null) {
            tvName.setText(student.getFirstName() + " " + student.getLastName());
            tvBarthDay.setText(student.getParthDay());
            tvAge.setText(String.valueOf(student.getAge()));
        }

        subjectRecyclerView = findViewById(R.id.subjectRecyclerView);

        dpHelper = new DpHelper(this);
        ArrayList<Subject> data1 = dpHelper.getSubjectsStudentId(studentId);
        data=new ArrayList<>();


        subject_studentIdAdapter = new Subject_StudentIdAdapter(this, data1, studentId);



        subjectRecyclerView.setAdapter(subject_studentIdAdapter);
        LinearLayoutManager manager = new LinearLayoutManager(getApplicationContext(), RecyclerView.VERTICAL, false);
        subjectRecyclerView.setLayoutManager(manager);

    }
}