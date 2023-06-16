package com.example.finalproject.Classes;

public class StudentSubject {

    private int id;
    private int student_id;
    private int subject_id;
    private boolean presented;


    public static final String TABLE_NAME="StudentSubject";
    public static final String COL_ID = "id";
    public static final String COL_STUDENT_ID = "student_id";
    public static final String COL_SUBJECT_ID = "subject_id";
    public static final String COL_PRESENT = "present";


    public static final String CREATE_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " ("
            + COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + COL_STUDENT_ID + " INTEGER, "
            + COL_SUBJECT_ID + " INTEGER, "
            + COL_PRESENT + " INTEGER, "
            + "FOREIGN KEY (" + COL_STUDENT_ID + ") REFERENCES " + Students.TABLE_NAME + "(" + Students.COL_ID + "), "
            + "FOREIGN KEY (" + COL_SUBJECT_ID + ") REFERENCES " + Subject.TABLE_NAME + "(" + Subject.COL_ID + "))";



    public StudentSubject() {}


    public StudentSubject(int id, int student_id, int subject_id, boolean presented) {
        this.id = id;
        this.student_id = student_id;
        this.subject_id = subject_id;
        this.presented = presented;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getStudentId() {
        return student_id;
    }

    public void setStudentId(int studentId) {
        this.student_id = studentId;
    }

    public int getSubjectId() {
        return subject_id;
    }

    public void setSubjectId(int subjectId) {
        this.subject_id = subjectId;
    }

    public boolean isPresent() {
        return presented;
    }

    public void setPresent(boolean present) {
        this.presented = present;
    }

    @Override
    public String toString() {
        return "Attendance{" +
                "id=" + id +
                ", studentId=" + student_id +
                ", subjectId=" + subject_id +
                ", present=" + presented +
                '}';
    }

}
