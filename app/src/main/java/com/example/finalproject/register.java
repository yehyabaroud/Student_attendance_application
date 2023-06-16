package com.example.finalproject;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.finalproject.Classes.Admin;
import com.example.finalproject.DbHelber.DpHelper;

public class register extends AppCompatActivity {

    DpHelper dpHelper;
    Admin admin;
    EditText et_name, et_email, et_password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);


        et_name = findViewById(R.id.et_name);
        et_email = findViewById(R.id.et_email);
        et_password = findViewById(R.id.et_password);
        Button btn_register = findViewById(R.id.btn_register);
        TextView tv_update = findViewById(R.id.tv_update);


        dpHelper = new DpHelper(this);


        if (getIntent().getExtras() != null) {


            admin = (Admin) getIntent().getExtras().getSerializable("admin");
            if (admin != null) {
                et_name.setText(admin.getUsername());
                et_email.setText(admin.getEmail());
                et_password.setText(admin.getPassword());
                btn_register.setText("Update");
                tv_update.setText("تحديث الحساب");
            }
        }


        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String te_nameUpdate = et_name.getText().toString();
                String te_emailUpdate = et_email.getText().toString();
                String te_passwordUpdate = et_password.getText().toString();

                if (admin != null) {
                    String id = String.valueOf(admin.getId());
                    boolean isSuccesfulyUpdate = dpHelper.updateAdmin(id, te_nameUpdate, te_emailUpdate, te_passwordUpdate);
                    if (isSuccesfulyUpdate) {
                        finish();
                    } else {
                        Toast.makeText(getApplicationContext(), "يرجى إدخال قيم صحيحة", Toast.LENGTH_SHORT).show();
                    }
                } else {


                    boolean isSucssefly = dpHelper.insertAdmin(te_nameUpdate, te_emailUpdate, te_passwordUpdate);
                    if (isSucssefly) {
                        Toast.makeText(getApplicationContext(), "تمت إضافة الحساب بنجاح", Toast.LENGTH_SHORT).show();
                        finish();
                    } else {
                        Toast.makeText(getApplicationContext(), "حدث خطأ أثناء إضافة الحساب", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });


    }
}