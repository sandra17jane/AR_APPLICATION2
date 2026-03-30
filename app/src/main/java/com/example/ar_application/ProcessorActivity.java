package com.example.ar_application;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class ProcessorActivity extends AppCompatActivity {

    private TextView resultText;
    private Button btnSubmit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_processor);

        resultText = findViewById(R.id.resultText);
        btnSubmit = findViewById(R.id.btn_submit);

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAnswers();
            }
        });
    }

    private void checkAnswers() {
        int score = 0;
        StringBuilder feedback = new StringBuilder();

        // Question 1
        RadioButton radioCalculation = findViewById(R.id.radio_calculation);
        if (radioCalculation.isChecked()) {
            score++;
        } else {
            feedback.append("\n1. Correct Answer: Perform calculations.");
        }

        // Question 2
        EditText editCpu = findViewById(R.id.edit_cpu);
        if (editCpu.getText().toString().trim().equalsIgnoreCase("Central Processing Unit")) {
            score++;
        } else {
            feedback.append("\n2. Correct Answer: Central Processing Unit.");
        }

        // Question 3 (Multiple Choice)
        CheckBox checkALU = findViewById(R.id.check_alu);
        CheckBox checkControl = findViewById(R.id.check_control);
        CheckBox checkRAM = findViewById(R.id.check_ram);
        if (checkALU.isChecked() && checkControl.isChecked() && !checkRAM.isChecked()) {
            score++;
        } else {
            feedback.append("\n3. Correct Answer: ALU (Arithmetic Logic Unit) & Control Unit.");
        }

        // Question 4
        RadioButton radioIntel = findViewById(R.id.radio_intel);
        if (radioIntel.isChecked()) {
            score++;
        } else {
            feedback.append("\n4. Correct Answer: Intel.");
        }

        // Question 5
        EditText editGhz = findViewById(R.id.edit_ghz);
        if (editGhz.getText().toString().trim().equalsIgnoreCase("Clock Speed")) {
            score++;
        } else {
            feedback.append("\n5. Correct Answer: Clock Speed.");
        }

        // Question 6
        RadioButton radioI9 = findViewById(R.id.radio_i9);
        if (radioI9.isChecked()) {
            score++;
        } else {
            feedback.append("\n6. Correct Answer: Intel Core i9.");
        }

        // Question 7
        EditText editCores = findViewById(R.id.edit_cores);
        if (editCores.getText().toString().trim().equalsIgnoreCase("Core Count")) {
            score++;
        } else {
            feedback.append("\n7. Correct Answer: Core Count.");
        }

        // Question 8
        RadioButton radioCache = findViewById(R.id.radio_cache);
        if (radioCache.isChecked()) {
            score++;
        } else {
            feedback.append("\n8. Correct Answer: Cache.");
        }

        // Question 9
        EditText editOverclocking = findViewById(R.id.edit_overclocking);
        if (editOverclocking.getText().toString().trim().equalsIgnoreCase("Increasing CPU speed beyond factory limits")) {
            score++;
        } else {
            feedback.append("\n9. Correct Answer: Increasing CPU speed beyond factory limits.");
        }

        // Question 10
        RadioButton radioFPU = findViewById(R.id.radio_fpu);
        if (radioFPU.isChecked()) {
            score++;
        } else {
            feedback.append("\n10. Correct Answer: FPU (Floating Point Unit).");
        }

        // Display score and correct answers
        resultText.setText("You scored " + score + "/10\n" + feedback.toString());
    }
}
