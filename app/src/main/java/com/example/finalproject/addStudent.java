package com.example.finalproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.example.finalproject.Classes.Subject;
import com.example.finalproject.DbHelber.DpHelper;
import com.example.finalproject.adapter.SubjectSelectedAdapter;

import java.util.ArrayList;
import java.util.Calendar;

public class addStudent extends AppCompatActivity {

     EditText te_first_name;
     EditText te_last_name;
     EditText date;
     Button addButton;
     RecyclerView recyclerview_add_student;
     DpHelper dbHelper;
     SubjectSelectedAdapter subjectSelectedAdapter;
    private ArrayList<Subject> subjectsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_student);

        te_first_name = findViewById(R.id.te_first_name);
        te_last_name = findViewById(R.id.te_last_name);
        date = findViewById(R.id.date);
        addButton = findViewById(R.id.addButton);
        recyclerview_add_student = findViewById(R.id.recyclerview_add_student);
        dbHelper = new DpHelper(getApplicationContext());
        subjectsList = new ArrayList<>();

        subjectSelectedAdapter = new SubjectSelectedAdapter(this, dbHelper.getAllSubjectData(), subjectsList);
        recyclerview_add_student.setAdapter(subjectSelectedAdapter);
        LinearLayoutManager manager=new LinearLayoutManager(getApplicationContext(),RecyclerView.VERTICAL,false);
        recyclerview_add_student.setLayoutManager(manager);

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String firstName = te_first_name.getText().toString();
                String lastName = te_last_name.getText().toString();
                String ageString = date.getText().toString();

                if (firstName.isEmpty() ||lastName.isEmpty() || ageString.isEmpty()) {
                    Toast.makeText(addStudent.this, "يوجد حقل فارغ", Toast.LENGTH_SHORT).show();
                } else {

                    int year, monthOfYear, dayOfMonth;

                    Calendar dob = Calendar.getInstance();
                    dob.setTimeInMillis(0);

                    String[] dateParts = ageString.split("/");

                    if (dateParts.length == 3) {
                        dayOfMonth = Integer.parseInt(dateParts[0]);
                        monthOfYear = Integer.parseInt(dateParts[1]) - 1;
                        year = Integer.parseInt(dateParts[2]);
                        dob.set(year, monthOfYear, dayOfMonth);
                        Calendar today = Calendar.getInstance();
                        int age = today.get(Calendar.YEAR) - dob.get(Calendar.YEAR);

                        if (today.get(Calendar.DAY_OF_YEAR) < dob.get(Calendar.DAY_OF_YEAR)){
                            age--;
                        }
                        int studentAge = age;

                        ArrayList<Subject> Subjectsselected = subjectSelectedAdapter.getSelectedSubjects();

                        dbHelper = new DpHelper(getApplicationContext());
                        boolean inserted = dbHelper.insertStudent(firstName, lastName, studentAge, subjectSelectedAdapter.getSelectedSubjects(),ageString);
                        
                        if (inserted) {
                            Toast.makeText(getApplicationContext(), "تمت إضافة الطالب" , Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(getApplicationContext(), "حدث خطأ أثناء إضافة الطالب", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                            Toast.makeText(addStudent.this, "يرجى إدخال التاريخ بهذا الشكل (DD/MM/YYYY)", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDatePickerDialog();
            }
        });


    }

    private void showDatePickerDialog() {
        final Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(addStudent.this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int monthOfYear, int dayOfMonth) {
                String selectedDate = dayOfMonth + "/" + (monthOfYear + 1) + "/" + year;
                date.setText(selectedDate);
            }
        }, year, month, dayOfMonth);

        datePickerDialog.show();
    }

}