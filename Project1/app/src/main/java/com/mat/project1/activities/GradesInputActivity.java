package com.mat.project1.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.mat.project1.R;
import com.mat.project1.models.GradeModel;
import com.mat.project1.recycler_views.GradesRecyclerAdapter;

import java.util.ArrayList;

public class GradesInputActivity extends AppCompatActivity {

    private ArrayList<GradeModel> grades;
    private RecyclerView recyclerView;

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        Button button = findViewById(R.id.button);
        outState.putInt("button_visibility", button.getVisibility());
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        Button button = findViewById(R.id.button);
        button.setVisibility((int) savedInstanceState.get("button_visibility"));
        super.onRestoreInstanceState(savedInstanceState);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grades_input);
        grades = new ArrayList<>();
        recyclerView = findViewById(R.id.gradeRecyclerView);
        Button button = findViewById(R.id.calculateMeanButton);
        button.setOnClickListener(view -> {
            int sum = grades.stream().mapToInt(GradeModel::getGrade).reduce(0, Integer::sum);
            double mean = (double) sum / (double) grades.size();
            Intent intent = new Intent(GradesInputActivity.this, MainActivity.class);
            intent.putExtra("mean", mean);
            setResult(RESULT_OK, intent);
            finish();
        });

        setGrades();
        setAdapter();
    }

    private void setAdapter() {
        GradesRecyclerAdapter adapter = new GradesRecyclerAdapter(this, grades);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
    }

    private void setGrades() {
        Bundle bundle = getIntent().getExtras();
        int subjectCount = bundle.getInt("subject_count");
        String[] subjectNames = getResources().getStringArray(R.array.subjects);
        for (int i = 0; i < subjectNames.length && i < subjectCount; i++) {
            grades.add(new GradeModel(subjectNames[i], 2));
        }
    }
}