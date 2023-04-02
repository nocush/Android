package com.example.a1;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class SecondActivity extends AppCompatActivity {
    private ArrayList<ModelOceny> oceny;
    private RecyclerView recyclerView;

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        Button button = findViewById(R.id.button);
        outState.putInt("button_visibility",button.getVisibility());
        super.onSaveInstanceState(outState);
    }
    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        Button button = findViewById(R.id.button);
        button.setVisibility(savedInstanceState.getInt("button_visibility"));
        super.onRestoreInstanceState(savedInstanceState);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        oceny = new ArrayList<>();
        recyclerView = findViewById(R.id.lista_ocen_rv);
        Button button = findViewById(R.id.calculateMeanButton);
        button.setOnClickListener(view -> {
            int sum = oceny.stream().mapToInt(ModelOceny::getOcena).reduce(0, Integer::sum);
            double mean = (double) sum / (double) oceny.size();
            Intent intent = new Intent(this, MainActivity.class);
            intent.putExtra("srednia", mean);
            setResult(RESULT_OK, intent);
            finish();
            }
        );
        setOceny();
        setAdapter();
    }
    private void setOceny() {
        Bundle bundle = getIntent().getExtras();
        int subjectCount = bundle.getInt("ileOcen");
        String[] subjectNames = getResources().getStringArray(R.array.subjects);
        for (int i = 0; i < subjectNames.length && i < subjectCount; i++) {
            oceny.add(new ModelOceny(subjectNames[i], 2));
        }
    }
    private void setAdapter() {
        InteraktywnyAdapterTabllicy ocenyAdapter = new InteraktywnyAdapterTabllicy(this,oceny);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(ocenyAdapter);
    }
}
