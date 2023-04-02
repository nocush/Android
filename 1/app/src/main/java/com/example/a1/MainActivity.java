package com.example.a1;

import static androidx.activity.result.ActivityResultCallerKt.registerForActivityResult;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.Button;
import android.view.View;
import android.content.Intent;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {

    private EditText name;
    private EditText lastname;
    private EditText ileOcen;
    private Button button;
    private TextView meanTextView;
    private double mean;

    public void openNewActivity(int gradesNumber) {
        Intent intent = new Intent(this, SecondActivity.class);
        intent.putExtra("ileOcen", gradesNumber);
        startActivity(intent);
    }

    public void changeButton() {
        EditText name = (EditText) findViewById(R.id.editTextTextPersonName);
        EditText lastname = (EditText) findViewById(R.id.editTextTextPersonName2);
        EditText grades = (EditText) findViewById(R.id.editTextNumber);
        Button button = (Button) findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openNewActivity(Integer.parseInt(grades.getText().toString()));
            }
        });
        if (name.getText().toString().isEmpty() || lastname.getText().toString().isEmpty() || grades.getText().toString().isEmpty()) {
            button.setVisibility(View.GONE);
            return;
        }
        String gradesAsStr = grades.getText().toString();
        if (gradesAsStr.isEmpty()) {
            button.setVisibility(View.GONE);
            return;
        }
        int gradesCnt = Integer.parseInt(gradesAsStr);
        if (gradesCnt < 5 || gradesCnt > 15) {
            button.setVisibility(View.GONE);
            return;
        }
        button.setVisibility(View.VISIBLE);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        button = findViewById(R.id.button);
        name = findViewById(R.id.editTextTextPersonName);
        lastname = findViewById(R.id.editTextTextPersonName2);
        ileOcen = findViewById(R.id.editTextNumber);
        meanTextView = findViewById(R.id.meanTextt);
    }


    protected void onStart() {
        super.onStart();
        EditText name = (EditText) findViewById(R.id.editTextTextPersonName);
        EditText lastname = (EditText) findViewById(R.id.editTextTextPersonName2);
        EditText grades = (EditText) findViewById(R.id.editTextNumber);


        name.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (name.getText().length() == 0) {
                    name.setError("Pole nie może być puste");
                }
                changeButton();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        lastname.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (lastname.getText().length() == 0) {
                    lastname.setError("Pole nie może być puste");
                }
                changeButton();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        grades.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (grades.getText().length() != 0) {
                    int x = Integer.parseInt(grades.getText().toString());
                    if (x < 5 || x > 15) {
                        grades.setError("Liczba ocen spoza zakresu");
                    }
                } else {
                    grades.setError("Puste pole");
                }
                changeButton();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        EditText name = (EditText) findViewById(R.id.editTextTextPersonName);
        EditText lastname = (EditText) findViewById(R.id.editTextTextPersonName2);
        EditText grades = (EditText) findViewById(R.id.editTextNumber);
        if (name.getText().toString().length() == 0) {
            outState.putString("errorname", name.getText().toString());
        }
        if (lastname.getText().toString().length() == 0) {
            outState.putString("errorlastname", lastname.getText().toString());
        }
        if (grades.getText().toString().length() == 0) {
            outState.putString("errorgrades", grades.getText().toString());
        }
        super.onSaveInstanceState(outState);

    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        EditText name = (EditText) findViewById(R.id.editTextTextPersonName);
        EditText lastname = (EditText) findViewById(R.id.editTextTextPersonName2);
        EditText grades = (EditText) findViewById(R.id.editTextNumber);
        if (name.getText().toString().length() == 0) {
            name.setText(savedInstanceState.getString("errorname"));
        }
        if (lastname.getText().toString().length() == 0) {
            lastname.setText(savedInstanceState.getString("errorlastname"));
        }
        if (grades.getText().toString().length() == 0) {
            grades.setText(savedInstanceState.getString("errorgrades"));
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && data != null) {
            mean = (double) data.getExtras().get("srednia");
            double roundOff = Math.round(mean * 100.0) / 100.0;
            String res = "Twoja średnia to: " + roundOff;
            meanTextView.setText(res);
            meanTextView.setVisibility(View.VISIBLE);

            String buttonText;
            if (mean >= 3.0) {
                buttonText = "Zaliczony";
            } else {
                buttonText = "Niezaliczony";
            }
            button.setOnClickListener(view -> {
                String msg;
                if (mean >= 3.0) {
                    msg = "Gratulacje! Zaliczyłeś przedmiot!";
                } else {
                    msg = "Wysyłam podanie o zaliczenie warunkowe";
                }
                Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
                finish();
            });

        }
    }

}
