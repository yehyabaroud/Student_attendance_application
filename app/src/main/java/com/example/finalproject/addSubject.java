package com.example.finalproject;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.finalproject.DbHelber.DpHelper;

public class addSubject extends AppCompatActivity {

    EditText te_name;
    Button btn_add_subject;
    DpHelper dpHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_subject);

        te_name=findViewById(R.id.te_name);
        btn_add_subject=findViewById(R.id.btn_add_subject);
        dpHelper=new DpHelper(getApplicationContext());

        btn_add_subject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String addTitel1=te_name.getText().toString();

                boolean isSucssefly=dpHelper.insertSubject(addTitel1);
                if (isSucssefly){
                    Toast.makeText(addSubject.this, "تمت إضافة مادة", Toast.LENGTH_SHORT).show();
                    finish();
                }else {
                    Toast.makeText(getApplicationContext(), "يرجى إدخال قيمة صحيحة", Toast.LENGTH_SHORT).show();
                }

            }
        });


    }
}