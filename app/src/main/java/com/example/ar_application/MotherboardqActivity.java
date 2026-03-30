package com.example.ar_application;

import android.os.Bundle;
import android.view.*;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;


public class MotherboardqActivity extends AppCompatActivity {

    private TextView resultText;
    private RadioGroup radioGroup1, radioGroup4, radioGroup5, radioGroup7, radioGroup9;
    private CheckBox checkRAM, checkHardDisk, checkGPU;
    private EditText editBios, editSSD, editUSB, editEthernet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_motherboardq);

        resultText = findViewById(R.id.resultText);

        radioGroup1 = findViewById(R.id.radioGroup1);
        checkRAM = findViewById(R.id.check_ram);
        checkHardDisk = findViewById(R.id.check_harddisk);
        checkGPU = findViewById(R.id.check_gpu);

        editBios = findViewById(R.id.edit_bios);
        editSSD = findViewById(R.id.edit_ssd);
        editUSB = findViewById(R.id.edit_usb);
        editEthernet = findViewById(R.id.edit_ethernet);

        radioGroup4 = findViewById(R.id.radioGroup4);
        radioGroup5 = findViewById(R.id.radioGroup5);
        radioGroup7 = findViewById(R.id.radioGroup7);
        radioGroup9 = findViewById(R.id.radioGroup9);

        findViewById(R.id.btn_submit).setOnClickListener(v -> evaluateAnswers());
    }

    private void evaluateAnswers() {
        int score = 0;
        StringBuilder result = new StringBuilder();

        // Question 1 - CPU
        if (radioGroup1.getCheckedRadioButtonId() == R.id.radio_cpu) {
            score++;
            result.append("1. ✅ CPU (Correct)\n");
        } else {
            result.append("1. ❌ CPU (Correct)\n");
        }

        // Question 2 - RAM & GPU
        if (checkRAM.isChecked() && checkGPU.isChecked() && !checkHardDisk.isChecked()) {
            score++;
            result.append("2. ✅ RAM, GPU (Correct)\n");
        } else {
            result.append("2. ❌ RAM, GPU (Correct)\n");
        }

        // Question 3 - BIOS
        if (editBios.getText().toString().trim().equalsIgnoreCase("Basic Input Output System")) {
            score++;
            result.append("3. ✅ Basic Input Output System (Correct)\n");
        } else {
            result.append("3. ❌ Basic Input Output System (Correct)\n");
        }

        // Question 4 - PCIe
        if (radioGroup4.getCheckedRadioButtonId() == R.id.radio_pci) {
            score++;
            result.append("4. ✅ PCIe (Correct)\n");
        } else {
            result.append("4. ❌ PCIe (Correct)\n");
        }

        // Question 5 - RAM
        if (radioGroup5.getCheckedRadioButtonId() == R.id.radio_ram2) {
            score++;
            result.append("5. ✅ RAM (Correct)\n");
        } else {
            result.append("5. ❌ RAM (Correct)\n");
        }

        // Question 6 - SSD
        if (editSSD.getText().toString().trim().equalsIgnoreCase("Solid State Drive")) {
            score++;
            result.append("6. ✅ Solid State Drive (Correct)\n");
        } else {
            result.append("6. ❌ Solid State Drive (Correct)\n");
        }

        // Question 7 - CPU
        if (radioGroup7.getCheckedRadioButtonId() == R.id.radio_cpu2) {
            score++;
            result.append("7. ✅ CPU (Correct)\n");
        } else {
            result.append("7. ❌ CPU (Correct)\n");
        }

        // Question 8 - USB
        if (editUSB.getText().toString().trim().equalsIgnoreCase("USB")) {
            score++;
            result.append("8. ✅ USB (Correct)\n");
        } else {
            result.append("8. ❌ USB (Correct)\n");
        }

        // Question 9 - HDD
        if (radioGroup9.getCheckedRadioButtonId() == R.id.radio_hdd) {
            score++;
            result.append("9. ✅ HDD (Correct)\n");
        } else {
            result.append("9. ❌ HDD (Correct)\n");
        }

        // Question 10 - Internet Connection
        if (editEthernet.getText().toString().trim().equalsIgnoreCase("Internet Connection")) {
            score++;
            result.append("10. ✅ Internet Connection (Correct)\n");
        } else {
            result.append("10. ❌ Internet Connection (Correct)\n");
        }

        // Display the final score and answers
        resultText.setText("Your Score: " + score + "/10\n\nCorrect Answers:\n" + result.toString());
    }

}
