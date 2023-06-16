package com.example.finalproject.DbHelber;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.finalproject.Classes.Admin;
import com.example.finalproject.Classes.Presence;
import com.example.finalproject.Classes.StudentSubject;
import com.example.finalproject.Classes.Students;
import com.example.finalproject.Classes.Subject;

import java.util.ArrayList;

public class DpHelper extends SQLiteOpenHelper {

    public DpHelper(@Nullable Context context) {
        super(context, "Admin", null, 2);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(Admin.CREATE_TABLE);
        db.execSQL(Subject.CREATE_TABLE);
        db.execSQL(Students.CREATE_TABLE);
        db.execSQL(StudentSubject.CREATE_TABLE);
        db.execSQL(Presence.CREATE_TABLE);

        db.execSQL("PRAGMA foreign_keys =ON;");
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + Admin.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS "+ Subject.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS "+ Students.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS "+ StudentSubject.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS "+ Presence.TABLE_NAME);
        onCreate(db);
    }
    public boolean insertAdmin(String username, String email, String password) {
        SQLiteDatabase db1 = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Admin.COL_USERNAME, username);
        values.put(Admin.COL_EMAIL, email);
        values.put(Admin.COL_PASSWORD, password);


        long row = db1.insert(Admin.TABLE_NAME, null, values);
        return row > 0;

    }


    public boolean updateAdmin(String id, String username, String email, String password) {
        SQLiteDatabase dp1 = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Admin.COL_USERNAME, username);
        values.put(Admin.COL_EMAIL, email);
        values.put(Admin.COL_PASSWORD, password);

        int rowID = dp1.update(Admin.TABLE_NAME, values, Admin.COL_ID + "=?", new String[]{id});
        return rowID > 0;

    }





    public ArrayList<Admin> getAllAdminData() {
        SQLiteDatabase db = getReadableDatabase();
        ArrayList<Admin> dataAdmin = new ArrayList<>();

        String query = "SELECT * FROM " + Admin.TABLE_NAME;

        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(cursor.getColumnIndexOrThrow(Admin.COL_ID));
                String username = cursor.getString(cursor.getColumnIndexOrThrow(Admin.COL_USERNAME));
                String email = cursor.getString(cursor.getColumnIndexOrThrow(Admin.COL_EMAIL));
                String password = cursor.getString(cursor.getColumnIndexOrThrow(Admin.COL_PASSWORD));

                Admin admin = new Admin(id, username, email, password);
                dataAdmin.add(admin);


            } while (cursor.moveToNext());
            cursor.close();
        }
        return dataAdmin;
    }

    public boolean insertSubject(String subjectName){
        SQLiteDatabase db=getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put(Subject.COL_SUBJECT_NAME,subjectName);
        long row=db.insert(Subject.TABLE_NAME,null,values);
        return row>0;
    }


    public ArrayList<Subject> getAllSubjectData(){
        SQLiteDatabase db=getReadableDatabase();
        ArrayList<Subject> data=new ArrayList<>();

        String query="SELECT * FROM "+Subject.TABLE_NAME;
        Cursor cursor=db.rawQuery(query,null);

        if (cursor.moveToFirst()){
            do {
                int id=cursor.getInt(cursor.getColumnIndexOrThrow(Subject.COL_ID));
                String subjectName=cursor.getString(cursor.getColumnIndexOrThrow(Subject.COL_SUBJECT_NAME));


                Subject subject=new Subject(subjectName,id);
                data.add(subject);

            }while (cursor.moveToNext());
            cursor.close();
        }


        return data;
    }



    public boolean insertStudent(String firstName, String lastName, int age, ArrayList<Subject> subjects,String parthDay) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Students.COL_FIRST_NAME, firstName);
        values.put(Students.COL_LAST_NAME, lastName);
        values.put(Students.COL_PARTH_DAY, parthDay);
        values.put(Students.COL_AGE, age);
        long rowId = db.insert(Students.TABLE_NAME, null, values);

        if (rowId > -1) {
            int studentId = (int) rowId;
            if (subjects != null && !subjects.isEmpty()) {
                for (Subject subject : subjects) {
                    ContentValues subjectValues = new ContentValues();
                    subjectValues.put(StudentSubject.COL_STUDENT_ID, studentId);
                    subjectValues.put(StudentSubject.COL_SUBJECT_ID, subject.getId());
                    db.insert(StudentSubject.TABLE_NAME, null, subjectValues);
                }
            }
            return true;
        } else {
            return false;
        }
    }




    public ArrayList<Students> getAllStudentsData(){
        SQLiteDatabase db=getReadableDatabase();
        ArrayList<Students> data=new ArrayList<>();

        String query="SELECT * FROM "+Students.TABLE_NAME;

        Cursor cursor=db.rawQuery(query,null);

        if (cursor.moveToFirst()){
            do {
                int id=cursor.getInt(cursor.getColumnIndexOrThrow(Students.COL_ID));
                String firstName=cursor.getString(cursor.getColumnIndexOrThrow(Students.COL_FIRST_NAME));
                String lastName=cursor.getString(cursor.getColumnIndexOrThrow(Students.COL_LAST_NAME));
                String parthDay=cursor.getString(cursor.getColumnIndexOrThrow(Students.COL_PARTH_DAY));
                int age=cursor.getInt(cursor.getColumnIndexOrThrow(Students.COL_AGE));

                Students students=new Students(id,firstName,lastName,age,parthDay);
                data.add(students);

            }while (cursor.moveToNext());
            cursor.close();
        }
        return data;
    }

    @SuppressLint("Range")
    public Students getStudentId(int id) {
        SQLiteDatabase db = getReadableDatabase();

        String[] projection = {
                Students.COL_FIRST_NAME,
                Students.COL_LAST_NAME,
                Students.COL_PARTH_DAY,
                Students.COL_AGE
        };

        String selection = Students.COL_ID + " = ?";
        String[] selectionArgs = { String.valueOf(id) };

        Cursor cursor = db.query(
                Students.TABLE_NAME,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                null
        );

        Students student = null;
        if (cursor.moveToFirst()) {
            String firstName = cursor.getString(cursor.getColumnIndex(Students.COL_FIRST_NAME));
            String lastName = cursor.getString(cursor.getColumnIndex(Students.COL_LAST_NAME));
            String parthDay = cursor.getString(cursor.getColumnIndex(Students.COL_PARTH_DAY));
            int age = cursor.getInt(cursor.getColumnIndex(Students.COL_AGE));

            student = new Students(id, firstName, lastName, age, parthDay);
        }

        cursor.close();
        return student;
    }

    public ArrayList<Subject> getSubjectsStudentId(int studentId) {
        SQLiteDatabase db = getReadableDatabase();
        ArrayList<Subject> subjects = new ArrayList<>();

        String query = "SELECT * FROM " + Subject.TABLE_NAME + " INNER JOIN " + StudentSubject.TABLE_NAME +
                " ON " + Subject.TABLE_NAME + "." + Subject.COL_ID + " = " + StudentSubject.TABLE_NAME + "." + StudentSubject.COL_SUBJECT_ID +
                " WHERE " + StudentSubject.TABLE_NAME + "." + StudentSubject.COL_STUDENT_ID + " = ?";

        String[] selectionArgs = { String.valueOf(studentId) };

        Cursor cursor = db.rawQuery(query, selectionArgs);

        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(cursor.getColumnIndexOrThrow(Subject.COL_ID));
                String name = cursor.getString(cursor.getColumnIndexOrThrow(Subject.COL_SUBJECT_NAME));

                Subject subject = new Subject(name,id);
                subjects.add(subject);
            } while (cursor.moveToNext());
        }

        cursor.close();
        return subjects;
    }
    public ArrayList<Students> getStudentsSubject(int subjectId) {
        ArrayList<Students> studentsList = new ArrayList<>();

        SQLiteDatabase db = getReadableDatabase();

        String query = "SELECT * FROM " + Students.TABLE_NAME +
                " INNER JOIN " + StudentSubject.TABLE_NAME +
                " ON " + Students.TABLE_NAME + "." + Students.COL_ID + " = " + StudentSubject.TABLE_NAME + "." + StudentSubject.COL_STUDENT_ID +
                " WHERE " + StudentSubject.TABLE_NAME + "." + StudentSubject.COL_SUBJECT_ID + " = " + subjectId;

        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(cursor.getColumnIndexOrThrow(Students.COL_ID));
                String firstName = cursor.getString(cursor.getColumnIndexOrThrow(Students.COL_FIRST_NAME));
                String lastName = cursor.getString(cursor.getColumnIndexOrThrow(Students.COL_LAST_NAME));
                String parthDay = cursor.getString(cursor.getColumnIndexOrThrow(Students.COL_PARTH_DAY));
                int age = cursor.getInt(cursor.getColumnIndexOrThrow(Students.COL_AGE));

                Students student = new Students(id, firstName, lastName, age,parthDay);
                studentsList.add(student);

            } while (cursor.moveToNext());
        }

        cursor.close();
        return studentsList;
    }


    public ArrayList<Subject> getSubjects() {
        SQLiteDatabase db = getReadableDatabase();
        ArrayList<Subject> subjects = new ArrayList<>();

        String query = "SELECT * FROM " + StudentSubject.TABLE_NAME +
                " WHERE " + StudentSubject.COL_STUDENT_ID + " = " + Students.COL_ID;

        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            do {
                int subjectId = cursor.getInt(cursor.getColumnIndexOrThrow(StudentSubject.COL_SUBJECT_ID));
                String subjectName=cursor.getString(cursor.getColumnIndexOrThrow(Subject.COL_SUBJECT_NAME));


                Subject subject = new Subject(subjectName,subjectId);
                subjects.add(subject);
            } while (cursor.moveToNext());
        }

        cursor.close();
        return subjects;
    }

    public boolean insertPresence(Presence presence) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Presence.COL_MONTH, presence.getMonth());
        values.put(Presence.COL_DAY, presence.getDay());
        values.put(Presence.COL_STUDENT_ID, presence.getStudent_id());
        values.put(Presence.COL_SUBJECT_ID, presence.getSubject_id());
        values.put(Presence.COL_PRESENT, presence.isPresent() ? 1 : 0);

        long row = db.insert(Presence.TABLE_NAME, null, values);
        return row > 0;
    }



    public double getAttendancePercentage(int subjectId, int studentId) {
        SQLiteDatabase db = getReadableDatabase();
        String attendedQuery = "SELECT COUNT(*) FROM " + Presence.TABLE_NAME + " WHERE " + Presence.COL_SUBJECT_ID + " = ? AND " + Presence.COL_STUDENT_ID + " = ? AND " + Presence.COL_PRESENT + " = 1";
        String[] attendedArgs = {String.valueOf(subjectId), String.valueOf(studentId)};
        Cursor attendedCursor = db.rawQuery(attendedQuery, attendedArgs);
        attendedCursor.moveToFirst();
        int attendedDays = attendedCursor.getInt(0);
        attendedCursor.close();
        String totalQuery = "SELECT COUNT(*) FROM " + Presence.TABLE_NAME + " WHERE " + Presence.COL_SUBJECT_ID + " = ?";
        String[] totalArgs = {String.valueOf(subjectId)};
        Cursor totalCursor = db.rawQuery(totalQuery, totalArgs);
        totalCursor.moveToFirst();
        int totalDays = 29;
        totalCursor.close();
        double percentage = (double) attendedDays / (double) totalDays * 100.0;
        return percentage;
    }

    public boolean deleteAdmin(String id) {
        SQLiteDatabase db1 = getWritableDatabase();
        int rowID = db1.delete(Admin.TABLE_NAME, Admin.COL_ID + "=?", new String[]{id});
        return rowID > 0;
    }

    public boolean deleteSubject(String id) {
        SQLiteDatabase db = getWritableDatabase();
        int rowID = db.delete(Subject.TABLE_NAME, Subject.COL_ID + "=?", new String[]{id});

        return rowID > 0;
    }

    public boolean deleteStudents(String id){
        SQLiteDatabase db=getWritableDatabase();
        int rowID = db.delete(Students.TABLE_NAME, Students.COL_ID + "=?", new String[]{id});
        db.delete(StudentSubject.TABLE_NAME, StudentSubject.COL_STUDENT_ID + "=?", new String[]{id});

        return rowID>0;
    }

}
