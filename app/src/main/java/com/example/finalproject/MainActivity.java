package com.example.finalproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.finalproject.Classes.Admin;
import com.example.finalproject.Classes.Subject;
import com.example.finalproject.DbHelber.DpHelper;
import com.example.finalproject.adapter.SupjectAdapter;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    SupjectAdapter supAdapter;
    LinearLayout layout;
    DpHelper dpHelper;
    RecyclerView rv_supject;
    TextView tv_name_home;
    TextView tv_email_home;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tv_name_home=findViewById(R.id.tv_name_home);
        tv_email_home=findViewById(R.id.tv_email_home);

        String email = getIntent().getStringExtra("email");
        String name = getIntent().getStringExtra("name");
        String password=getIntent().getStringExtra("password");





        LinearLayout btnUpdate=findViewById(R.id.layout);
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Admin admin=new Admin(1,password,email,name);
                Intent intent=new Intent(getApplicationContext(),register.class);
                intent.putExtra("admin",admin);
                startActivity(intent);
            }
        });

        tv_name_home.setText(email);
        tv_email_home.setText(name);


        rv_supject=findViewById(R.id.rv_supject);
        ArrayList<Subject> data =new ArrayList<>();

        rv_supject.setAdapter(supAdapter);
        GridLayoutManager manager=new GridLayoutManager(this,2);
        rv_supject.setLayoutManager(manager);


        Button btn_show_all_student=findViewById(R.id.btn_show_all_student);
        btn_show_all_student.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(),allStudents.class);
                startActivity(intent);
            }
        });

        LinearLayout add_studentLayout = (LinearLayout) findViewById(R.id.add_student);
        add_studentLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(),addStudent.class);
                startActivity(intent);
            }
        });

        LinearLayout add_subjectLayout = (LinearLayout) findViewById(R.id.add_subject);
        add_subjectLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(),addSubject.class);
                startActivity(intent);
            }
        });
    }


    @Override
    protected void onResume() {
        super.onResume();
        dpHelper=new DpHelper(this);
        ArrayList<Admin> adminList = dpHelper.getAllAdminData();

        if (!adminList.isEmpty()) {
            Admin admin = adminList.get(0);
            tv_name_home.setText(admin.getUsername());
            tv_email_home.setText(admin.getEmail());
        }

        ArrayList<Subject> data=dpHelper.getAllSubjectData();
        supAdapter = new SupjectAdapter(this, data, new SupjectAdapter.onItemClickListener() {
            @Override
            public void onItemDelete(int id, int position) {
                if (dpHelper.deleteSubject(String.valueOf(id))){
                    data.remove(position);
                    supAdapter.notifyItemRemoved(position);
                }
            }

            @Override
            public void onItemClick(Subject subject, int position) {
                Subject selectedSubject = data.get(position);

                Intent intent = new Intent(getApplicationContext(), showMonth.class);
                intent.putExtra("subjectId", selectedSubject.getId());
                startActivity(intent);
            }


        }) {

        };
        rv_supject.setAdapter(supAdapter);
        GridLayoutManager manager=new GridLayoutManager(this,2);
        rv_supject.setLayoutManager(manager);

    }
}