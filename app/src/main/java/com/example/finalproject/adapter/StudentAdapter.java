package com.example.finalproject.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.finalproject.Classes.Students;
import com.example.finalproject.R;

import java.util.ArrayList;

public class StudentAdapter extends RecyclerView.Adapter<StudentAdapter.MyHolder>{

     Context context;
     ArrayList<Students> studentsList;
    private ArrayList<Boolean> attendanceList;

    public StudentAdapter(Context context, ArrayList<Students> studentsList, ArrayList<Boolean> attendanceList) {
        this.context = context;
        this.studentsList = studentsList;
        this.attendanceList = attendanceList;
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_students_choice, parent, false);
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int position) {
        Students student = studentsList.get(position);
        holder.tvName.setText(student.getFirstName());
        holder.cbPresent.setChecked(attendanceList.get(position));

        holder.cbPresent.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                attendanceList.set(holder.getAdapterPosition(), isChecked);
            }
        });
    }

    @Override
    public int getItemCount() {
        return studentsList.size();
    }

    public static class MyHolder extends RecyclerView.ViewHolder {
        TextView tvName;
        CheckBox cbPresent;

        public MyHolder(@NonNull View itemView) {
            super(itemView);

            tvName = itemView.findViewById(R.id.tv_student_name);
            cbPresent = itemView.findViewById(R.id.cb_attendance);
        }
    }

}
