package com.example.ar_application;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class RAMQuizActivity extends AppCompatActivity {

    private RadioGroup radioGroup1, radioGroup2, radioGroup3, radioGroup4, radioGroup6, radioGroup8, radioGroup9;
    private EditText editRamFull, editDDR, editRamGaming;
    private TextView resultText;
    private Button btnSubmit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ramquiz);

        // Initialize Views
        radioGroup1 = findViewById(R.id.radioGroup1);
        radioGroup2 = findViewById(R.id.radioGroup2);
        radioGroup3 = findViewById(R.id.radioGroup3);
        radioGroup4 = findViewById(R.id.radioGroup4);
        radioGroup6 = findViewById(R.id.radioGroup6);
        radioGroup8 = findViewById(R.id.radioGroup8);
        radioGroup9 = findViewById(R.id.radioGroup9);
        editRamFull = findViewById(R.id.edit_ram_full);
        editDDR = findViewById(R.id.edit_ddr);
        editRamGaming = findViewById(R.id.edit_ram_gaming);
        resultText = findViewById(R.id.resultText);
        btnSubmit = findViewById(R.id.btn_submit);

        // Submit Button Click Listener
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

        // Check Radio Button Answers
        if (isChecked(radioGroup1, R.id.radio_ram1)) score++;
        else feedback.append("\n1. Correct Answer: Random Access Memory");

        if (isChecked(radioGroup2, R.id.radio_volatile)) score++;
        else feedback.append("\n2. Correct Answer: Volatile");

        if (isChecked(radioGroup3, R.id.radio_ddr4)) score++;
        else feedback.append("\n3. Correct Answer: DDR4");

        if (isChecked(radioGroup4, R.id.radio_cpu)) score++;
        else feedback.append("\n4. Correct Answer: CPU");

        if (isChecked(radioGroup6, R.id.radio_mhz)) score++;
        else feedback.append("\n5. Correct Answer: MHz");

        if (isChecked(radioGroup8, R.id.radio_dimm)) score++;
        else feedback.append("\n6. Correct Answer: DIMM slot");

        if (isChecked(radioGroup9, R.id.radio_speed)) score++;
        else feedback.append("\n7. Correct Answer: System Speed");

        // Check EditText Answers
        if (editRamFull.getText().toString().trim().equalsIgnoreCase("Swap memory is used")) score++;
        else feedback.append("\n8. Correct Answer: Swap memory is used");

        if (editDDR.getText().toString().trim().equalsIgnoreCase("Double Data Rate")) score++;
        else feedback.append("\n9. Correct Answer: Double Data Rate");

        if (editRamGaming.getText().toString().trim().equalsIgnoreCase("16GB")) score++;
        else feedback.append("\n10. Correct Answer: 16GB");

        // Display score and only incorrect answers
        String resultMessage = "You scored: " + score + "/10";
        if (feedback.length() > 0) {
            resultMessage += "\nReview the following answers:" + feedback.toString();
        } else {
            resultMessage += "\nPerfect Score! 🎯";
        }

        resultText.setText(resultMessage);
    }

    private boolean isChecked(RadioGroup group, int correctAnswerId) {
        int selectedId = group.getCheckedRadioButtonId();
        return selectedId == correctAnswerId;
    }
}
