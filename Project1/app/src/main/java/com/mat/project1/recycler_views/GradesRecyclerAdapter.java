package com.mat.project1.recycler_views;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mat.project1.R;
import com.mat.project1.models.GradeModel;

import java.util.List;

public class GradesRecyclerAdapter extends RecyclerView.Adapter<GradesRecyclerAdapter.GradesViewHolder> {

    private final List<GradeModel> mGradeList;
    private final LayoutInflater mInflater;

    public GradesRecyclerAdapter(Activity context, List<GradeModel> mGradeList) {
        this.mInflater = context.getLayoutInflater();
        this.mGradeList = mGradeList;
    }

    @NonNull
    @Override
    public GradesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View row = mInflater.inflate(R.layout.grade_row, parent, false);
        return new GradesViewHolder(row);
    }

    @Override
    public void onBindViewHolder(@NonNull GradesViewHolder holder, int position) {
        GradeModel gradeModel = mGradeList.get(position);
        holder.subjectNameTextView.setText(gradeModel.getName());
        holder.radioGroup.check(R.id.gradeRadio2);
        holder.radioGroup.setTag(gradeModel);
        holder.radioGroup.setOnCheckedChangeListener(holder);
    }

    @Override
    public int getItemCount() {
        return mGradeList.size();
    }

    static class GradesViewHolder extends RecyclerView.ViewHolder implements RadioGroup.OnCheckedChangeListener {

        TextView subjectNameTextView;
        RadioGroup radioGroup;

        public GradesViewHolder(@NonNull View itemView) {
            super(itemView);
            subjectNameTextView = itemView.findViewById(R.id.subjectLabel);
            radioGroup = itemView.findViewById(R.id.radioGroup);
        }

        @Override
        public void onCheckedChanged(RadioGroup radioGroup, int i) {
            GradeModel gradeModel = (GradeModel) radioGroup.getTag();
            for (int j = 0; j < radioGroup.getChildCount(); j++) {
                RadioButton radioButton = (RadioButton) radioGroup.getChildAt(j);
                if (radioButton.getId() == i)
                    gradeModel.setGrade(Integer.parseInt(radioButton.getText().toString()));
            }
        }
    }
}