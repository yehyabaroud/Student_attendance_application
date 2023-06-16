package com.example.finalproject.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.finalproject.Classes.Subject;
import com.example.finalproject.DbHelber.DpHelper;
import com.example.finalproject.R;

import java.util.ArrayList;

public class Subject_StudentIdAdapter extends RecyclerView.Adapter<Subject_StudentIdAdapter.MyHolder>{

    Context context;
    ArrayList<Subject> data;
    DpHelper dpHelper;
    private int studentId;

    public Subject_StudentIdAdapter(Context context, ArrayList<Subject> data, int studentId) {
        this.context = context;
        this.data = data;
        this.studentId = studentId;
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_attendance_rate, parent, false);
        return new MyHolder(view, studentId);
    }

    public void onBindViewHolder(@NonNull MyHolder holder, int position) {
        Subject subject = data.get(position);
        holder.subjectName.setText(subject.getSubjectName());
        dpHelper = new DpHelper(context);
        double attendancePercentage = dpHelper.getAttendancePercentage(studentId, subject.getId());
        String attendanceText = "" + attendancePercentage + "%";
        holder.attendancePercentage.setText(attendanceText);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class MyHolder extends RecyclerView.ViewHolder {
        TextView subjectName;
        TextView attendancePercentage;

        public MyHolder(@NonNull View itemView, int studentId) {
            super(itemView);
            subjectName = itemView.findViewById(R.id.SubjectName);
            attendancePercentage = itemView.findViewById(R.id.percentage_of_student_attendance);

        }
    }

}
