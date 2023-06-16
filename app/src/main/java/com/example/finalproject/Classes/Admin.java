package com.example.finalproject.Classes;

import java.io.Serializable;

public class Admin implements Serializable {

    private int id;
    private String userName;
    private String email;
    private String password;

    public static final String TABLE_NAME="Admin";
    public static final String COL_ID="id";
    public static final String COL_USERNAME="userName";
    public static final String COL_EMAIL="email";
    public static final String COL_PASSWORD="password";

    public static final String CREATE_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " ("
            + COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + COL_USERNAME + " TEXT, "
            + COL_EMAIL + " TEXT, "
            + COL_PASSWORD + " TEXT)";




    public Admin(int id, String userName, String email, String password) {
        this.id = id;
        this.userName = userName;
        this.email = email;
        this.password = password;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return userName;
    }

    public void setUsername(String userName) {
        this.userName = userName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
