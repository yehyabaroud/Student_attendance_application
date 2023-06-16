package com.example.finalproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

import com.example.finalproject.Classes.Students;
import com.example.finalproject.DbHelber.DpHelper;
import com.example.finalproject.adapter.Subject_StudentIdAdapter;
import com.example.finalproject.adapter.SupjectAllStudentAdapter;

import java.util.ArrayList;

public class allStudents extends AppCompatActivity {

    RecyclerView recyclerview_all_student;
    DpHelper dpHelper;
    SupjectAllStudentAdapter supjectAllStudentAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_students);

        recyclerview_all_student=findViewById(R.id.recyclerview_all_student);

        dpHelper=new DpHelper(this);
        ArrayList<Students> data=dpHelper.getAllStudentsData();
        supjectAllStudentAdapter=new SupjectAllStudentAdapter(this, data, new SupjectAllStudentAdapter.onItemClickListener() {
            @Override
            public void onItemDelete(int id, int position) {
                if (dpHelper.deleteStudents(String.valueOf(id))){
                    data.remove(position);
                    supjectAllStudentAdapter.notifyItemRemoved(position);
                }
            }

            @Override
            public void onItemClick(Students student, int position) {
                int studentId = student.getId();

                Intent intent = new Intent(getApplicationContext(), studentInformation.class);

                intent.putExtra("studentId", studentId);
                startActivity(intent);
            }

        }) {



        };
        recyclerview_all_student.setAdapter(supjectAllStudentAdapter);
        LinearLayoutManager manager=new LinearLayoutManager(getApplicationContext(),RecyclerView.VERTICAL,false);
        recyclerview_all_student.setLayoutManager(manager);

    }
}