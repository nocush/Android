package com.mat.project1.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.lavertis.project1.R;

public class MainActivity extends AppCompatActivity {

    private EditText name;
    private EditText lastName;
    private EditText gradeCount;
    private Button button;
    private TextView meanTextView;
    private double mean;

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
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && data != null) {
            mean = (double) data.getExtras().get("mean");
            double roundOff = Math.round(mean * 100.0) / 100.0;
            String res = "Twoja średnia to: " + roundOff;
            meanTextView.setText(res);
            meanTextView.setVisibility(View.VISIBLE);

            String buttonText;
            if (mean >= 3)
                buttonText = "Super :)";
            else
                buttonText = "Tym razem mi nie poszło";
            button.setText(buttonText);

            button.setOnClickListener(view -> {
                String msg;
                if (mean >= 3)
                    msg = "Gratulacje! Otrzymujesz zaliczenie!";
                else
                    msg = "Wysyłam podanie o zaliczenie warunkowe";
                Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
                finish();
            });
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button = findViewById(R.id.button);
        name = findViewById(R.id.nameEditText);
        lastName = findViewById(R.id.lastNameEditText);
        gradeCount = findViewById(R.id.marksEditText);
        meanTextView = findViewById(R.id.meanTextView);

        name.setOnFocusChangeListener((view, b) -> {
            if (b) return;

            if (name.getText().toString().isEmpty()) {
                String errorMsg = "Imię nie może być puste";
                name.setError(errorMsg);
                Toast.makeText(this, errorMsg, Toast.LENGTH_SHORT).show();
            }
            changeButtonVisibility();
        });

        lastName.setOnFocusChangeListener((view, b) -> {
            if (b) return;

            if (lastName.getText().toString().isEmpty()) {
                String errorMsg = "Nazwisko nie może być puste";
                lastName.setError(errorMsg);
                Toast.makeText(this, errorMsg, Toast.LENGTH_SHORT).show();
            }
            changeButtonVisibility();
        });

        gradeCount.setOnFocusChangeListener((view, b) -> {
            if (b) return;

            String marksCountAsStr = gradeCount.getText().toString();
            if (marksCountAsStr.isEmpty()) {
                String errorMsg = "Liczba ocen nie może być pusta";
                gradeCount.setError(errorMsg);
                Toast.makeText(this, errorMsg, Toast.LENGTH_SHORT).show();
                changeButtonVisibility();
                return;
            }

            int marksCount = Integer.parseInt(marksCountAsStr);
            if (marksCount < 5 || marksCount > 15) {
                String errorMsg = "Liczba ocen musi zawierać się w przedziale (5-15)";
                gradeCount.setError(errorMsg);
                Toast.makeText(this, errorMsg, Toast.LENGTH_SHORT).show();
                changeButtonVisibility();
                return;
            }

            changeButtonVisibility();
        });

        ActivityResultLauncher<Intent> someActivityResultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == RESULT_OK) {
                        // There are no request codes
                        Intent data = result.getData();
                        if (data != null) {
                            mean = (double) data.getExtras().get("mean");
                            double roundOff = Math.round(mean * 100.0) / 100.0;
                            String res = "Twoja średnia to: " + roundOff;
                            meanTextView.setText(res);
                            meanTextView.setVisibility(View.VISIBLE);

                            String buttonText;
                            String msg;
                            if (mean >= 3) {
                                buttonText = "Super :)";
                                msg = "Gratulacje! Otrzymujesz zaliczenie!";
                            } else {
                                buttonText = "Tym razem mi nie poszło";
                                msg = "Wysyłam podanie o zaliczenie warunkowe";
                            }
                            button.setText(buttonText);

                            button.setOnClickListener(view -> {
                                Toast.makeText(MainActivity.this, msg, Toast.LENGTH_SHORT).show();
                                finish();
                            });
                        }
                    }
                });

        Button button = findViewById(R.id.button);
        button.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, GradesInputActivity.class);
            int subjectCount = Integer.parseInt(gradeCount.getText().toString());
            intent.putExtra("subject_count", subjectCount);
            someActivityResultLauncher.launch(intent);
        });
    }

    public void changeButtonVisibility() {
        EditText name = findViewById(R.id.nameEditText);
        EditText lastName = findViewById(R.id.lastNameEditText);
        EditText marks = findViewById(R.id.marksEditText);
        Button button = findViewById(R.id.button);

        if (name.getText().toString().isEmpty() ||
                lastName.getText().toString().isEmpty() ||
                marks.getText().toString().isEmpty()) {
            button.setVisibility(View.INVISIBLE);
            return;
        }

        String marksCountAsStr = marks.getText().toString();
        if (marksCountAsStr.isEmpty()) {
            button.setVisibility(View.INVISIBLE);
            return;
        }

        int marksCount = Integer.parseInt(marksCountAsStr);
        if (marksCount < 5 || marksCount > 15) {
            button.setVisibility(View.INVISIBLE);
            return;
        }

        button.setVisibility(View.VISIBLE);
    }
}