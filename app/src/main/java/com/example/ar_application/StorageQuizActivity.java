package com.example.ar_application;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class StorageQuizActivity extends AppCompatActivity {

    private TextView resultText;
    private Button btnSubmit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_storage_quiz);

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
        RadioButton radioSpeed = findViewById(R.id.radio_speed);
        if (radioSpeed.isChecked()) {
            score++;
        } else {
            feedback.append("\n1. Correct Answer: Faster speed.");
        }

        // Question 2 (Multiple Choice)
        CheckBox checkUsbA = findViewById(R.id.check_usb_a);
        CheckBox checkUsbB = findViewById(R.id.check_usb_b);
        CheckBox checkUsbC = findViewById(R.id.check_usb_c);
        CheckBox checkSata = findViewById(R.id.check_sata);
        if (checkUsbA.isChecked() && checkUsbB.isChecked() && checkUsbC.isChecked() && !checkSata.isChecked()) {
            score++;
        } else {
            feedback.append("\n2. Correct Answer: USB-A, USB-B, USB-C (excluding SATA).");
        }

        // Question 3
        RadioButton radioCacheSpeed = findViewById(R.id.radio_cache_speed);
        if (radioCacheSpeed.isChecked()) {
            score++;
        } else {
            feedback.append("\n3. Correct Answer: Improve data access speed.");
        }

        // Question 4
        EditText editSsdCapacity = findViewById(R.id.edit_ssd_capacity);
        String ssdCapacityInput = editSsdCapacity.getText().toString().trim();
        if (!ssdCapacityInput.isEmpty() && ssdCapacityInput.equals("100")) {
            score++;
        } else {
            feedback.append("\n4. Correct Answer: Max capacity is 100TB.");
        }

        // Question 5 (Multiple Choice)
        CheckBox checkNtfs = findViewById(R.id.check_ntfs);
        CheckBox checkExt4 = findViewById(R.id.check_ext4);
        CheckBox checkFat32 = findViewById(R.id.check_fat32);
        CheckBox checkHttp = findViewById(R.id.check_http);
        if (checkNtfs.isChecked() && checkExt4.isChecked() && checkFat32.isChecked() && !checkHttp.isChecked()) {
            score++;
        } else {
            feedback.append("\n5. Correct Answer: NTFS, EXT4, FAT32 (excluding HTTP).");
        }

        // Question 6
        RadioButton radioRaid1 = findViewById(R.id.radio_raid1);
        if (radioRaid1.isChecked()) {
            score++;
        } else {
            feedback.append("\n6. Correct Answer: RAID 1 offers redundancy.");
        }

        // Question 7
        EditText editLifespan = findViewById(R.id.edit_lifespan);
        String lifespanInput = editLifespan.getText().toString().trim();
        if (!lifespanInput.isEmpty() && lifespanInput.equals("10")) {
            score++;
        } else {
            feedback.append("\n7. Correct Answer: Typical SSD lifespan is 10 years.");
        }

        // Question 8
        RadioButton radioXml = findViewById(R.id.radio_xml);
        if (radioXml.isChecked()) {
            score++;
        } else {
            feedback.append("\n8. Correct Answer: XML is not a partition format.");
        }

        // Question 9
        RadioButton radioParallel = findViewById(R.id.radio_parallel);
        if (radioParallel.isChecked()) {
            score++;
        } else {
            feedback.append("\n9. Correct Answer: Parallel data processing makes NVMe faster.");
        }

        // Question 10
        RadioButton radio50Gb = findViewById(R.id.radio_50gb);
        if (radio50Gb.isChecked()) {
            score++;
        } else {
            feedback.append("\n10. Correct Answer: Max storage of a Blu-ray disc is 50GB.");
        }

        // Display score and feedback
        resultText.setText("You scored " + score + "/10\n" + feedback.toString());
    }
}
