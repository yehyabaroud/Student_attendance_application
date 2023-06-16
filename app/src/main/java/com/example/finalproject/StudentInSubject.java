package com.example.finalproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.finalproject.Classes.Presence;
import com.example.finalproject.Classes.Students;
import com.example.finalproject.DbHelber.DpHelper;
import com.example.finalproject.adapter.StudentAdapter;

import java.util.ArrayList;

public class StudentInSubject extends AppCompatActivity {

    TextView tv_id;
    Button save_button;
    RecyclerView recyclerview_students;
    StudentAdapter studentAdapter;
    ArrayList<Students> studentsList;
    ArrayList<Boolean> attendanceList;
    DpHelper dpHelper;
    String monthSelected;
    String daySelected;
    int subjectId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_in_subject);

        save_button = findViewById(R.id.save_button);
        save_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveAttendance();
            }
        });

        tv_id = findViewById(R.id.tv_id);

        recyclerview_students = findViewById(R.id.recyclerview_students);
        studentsList = new ArrayList<>();
        attendanceList = new ArrayList<>();

        subjectId = getIntent().getIntExtra("subjectId", 0);
        monthSelected = getIntent().getStringExtra("selectedMonth");
        daySelected = getIntent().getStringExtra("selectedDay");


        dpHelper = new DpHelper(getApplicationContext());
        studentsList = dpHelper.getStudentsSubject(subjectId);

        for (int i = 0; i < studentsList.size(); i++) {
            attendanceList.add(false);
        }

        studentAdapter = new StudentAdapter(this, studentsList, attendanceList);
        recyclerview_students.setAdapter(studentAdapter);
        LinearLayoutManager manager = new LinearLayoutManager(getApplicationContext(), RecyclerView.VERTICAL, false);
        recyclerview_students.setLayoutManager(manager);
    }

    private void saveAttendance() {
        String selectedMonth = getIntent().getStringExtra("selectedMonth");
        String selectedDay = getIntent().getStringExtra("selectedDay");
        int subjectId = getIntent().getIntExtra("subjectId", 0);

        dpHelper = new DpHelper(getApplicationContext());

        StringBuilder attendanceData = new StringBuilder();

        for (int i = 0; i < studentsList.size(); i++) {
            Students student = studentsList.get(i);
            boolean isPresent = attendanceList.get(i);

            if (isPresent) {
                Presence presence = new Presence();
                presence.setMonth(selectedMonth);
                presence.setDay(Integer.parseInt(selectedDay));
                presence.setStudent_id(student.getId());
                presence.setSubject_id(subjectId);
                presence.setPresent(true);

                boolean result = dpHelper.insertPresence(presence);
                if (result) {
                    attendanceData.append("تم حفظ حضور الطالب: ").append(student.getFirstName()).append("\n");
                } else {
                    attendanceData.append("حدث خطأ في حفظ حضور الطالب: ").append(student.getFirstName()).append("\n");
                }
            }
        }

        Toast.makeText(this, attendanceData.toString(), Toast.LENGTH_LONG).show();

    }
}