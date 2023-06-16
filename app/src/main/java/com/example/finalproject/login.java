package com.example.finalproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.finalproject.Classes.Admin;
import com.example.finalproject.DbHelber.DpHelper;

import java.util.ArrayList;

public class login extends AppCompatActivity {

    EditText Username;
    EditText Password;
    Button btn_login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        TextView createAccountTextView = findViewById(R.id.create_account);
        String text = "هل تريد ";
        String clickableText = "إنشاء حساب";
        SpannableString spannableStrings = new SpannableString(text + clickableText);
        ClickableSpan clickableSpan = new ClickableSpan() {
            @Override
            public void onClick(View widget) {
                Intent intent = new Intent(login.this, register.class);
                startActivity(intent);
            }

            @Override
            public void updateDrawState(TextPaint ds) {
                super.updateDrawState(ds);
                ds.setUnderlineText(false);
            }
        };


        spannableStrings.setSpan(clickableSpan, text.length(), text.length() + clickableText.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        createAccountTextView.setText(spannableStrings);
        createAccountTextView.setMovementMethod(LinkMovementMethod.getInstance());
        createAccountTextView.setHighlightColor(Color.TRANSPARENT);


        CheckBox remember = findViewById(R.id.cb_remember);

        Username = findViewById(R.id.et_name);
        Password = findViewById(R.id.et_password);
        btn_login = findViewById(R.id.btn_login);

        btn_login.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View view) {
                String username = Username.getText().toString();
                String password = Password.getText().toString();


                boolean usernameV = true;
                boolean passwordV = true;
                boolean policyAccepted = true;


                if (username.length() == 0) {
                    Username.setError("الاسم فارغ");
                    usernameV = false;
                } else if (username.length() <= 3) {
                    Username.setError("يجب أن يكون الاسم أكبر من ثلاث حروف");
                    usernameV = false;
                }

                if (password.length() == 0) {
                    Password.setError("كلمة المرور فارغة");
                    passwordV = false;
                }
                boolean t = false;
                boolean number = false;

                for (int i = 0; i < password.length(); i++) {
                    char c = password.charAt(i);
                    if (Character.isLetter(c)) {
                        t = true;
                    } else if (Character.isDigit(c)) {
                        number = true;
                    }
                }

                if (t && number) {
                } else {
                    Password.setError("يجب أن يحتوي النص على أحرف وأرقام");
                    passwordV = false;

                }
                if (remember.isChecked()) {
                    remember.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.blue));
                    policyAccepted = true;
                } else {
                    remember.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.black));
                    policyAccepted = false;


                }

                CheckBox remember = findViewById(R.id.cb_remember);
                SharedPreferences sharedPref = getSharedPreferences("myPref", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPref.edit();

                if (remember.isChecked()) {
                    policyAccepted = true;
                    editor.putBoolean("policy_accepted", true);
                    editor.putString("username", username);
                    editor.putString("password", password);
                } else {
                    policyAccepted = false;
                    editor.putBoolean("policy_accepted", false);
                }

                editor.apply();


                if (passwordV && usernameV) {
                    DpHelper dbHelper = new DpHelper(getApplicationContext());
                    ArrayList<Admin> adminData = dbHelper.getAllAdminData();
                    boolean isIdentical = false;

                    for (Admin admin : adminData) {
                        if (admin.getUsername().equals(username) && admin.getPassword().equals(password)) {
                            isIdentical = true;
                            break;
                        }
                    }

                    if (isIdentical) {
                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(intent);
                        finish();
                    } else {
                        Toast.makeText(getApplicationContext(), "اسم المسخدم أو كلمة المرور غير صالحة", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(login.this, "يرجى التأكد من القيم المدخلة", Toast.LENGTH_SHORT).show();
                }
            }
        });


    }
}