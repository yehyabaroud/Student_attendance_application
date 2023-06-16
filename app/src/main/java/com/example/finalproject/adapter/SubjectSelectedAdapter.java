package com.example.finalproject.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.finalproject.Classes.Subject;
import com.example.finalproject.DbHelber.DpHelper;
import com.example.finalproject.R;

import java.util.ArrayList;

public class SubjectSelectedAdapter extends RecyclerView.Adapter<SubjectSelectedAdapter.MyHolder> {

    Context context;
    ArrayList<Subject> data;
    private ArrayList<Subject> subjectsList;
    DpHelper dpHelper;


    public SubjectSelectedAdapter(Context context, ArrayList<Subject> data, ArrayList<Subject> subjectsList) {
        this.context = context;
        this.data = data;
        this.subjectsList = subjectsList;
        dpHelper = new DpHelper(context);
        this.subjectsList.clear();
        this.subjectsList.addAll(dpHelper.getAllSubjectData());
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_subject_choice, parent, false);
        return new MyHolder(view, subjectsList);
    }



    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, @SuppressLint("RecyclerView") int i) {
        Subject subject = subjectsList.get(i);
        holder.supjectNameCheck.setText(subject.getSubjectName());

        holder.supjectNameCheck.setChecked(subject.isSelected());

        holder.supjectNameCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isSelected = !subject.isSelected();
                subject.setSelected(isSelected);
                holder.supjectNameCheck.setSelected(isSelected);

                data.get(i).setSelected(isSelected);
            }
        });

    }
    @Override
    public int getItemCount() {
        return data.size();
    }

    public class MyHolder extends RecyclerView.ViewHolder {
        CheckBox supjectNameCheck;
        ArrayList<Subject> subjectsList;

        public MyHolder(@NonNull View itemView, ArrayList<Subject> subjectsList) {
            super(itemView);
            supjectNameCheck = itemView.findViewById(R.id.tv_subject_name);
            this.subjectsList = subjectsList;
        }
    }

    public interface onItemClickListener {

    }


    public ArrayList<Subject> getSelectedSubjects() {
        ArrayList<Subject>selectedSubjects = new ArrayList<>();

        for (int i = 0; i < data.size(); i++) {
            if (data.get(i).isSelected()) {
                selectedSubjects.add(data.get(i));
            }
        }

        return selectedSubjects;
    }

}
