package com.example.ar_application;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;

public class QuizActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);
    }
    public void openMotherboardQuiz(View view) {
        startActivity(new Intent(this, MotherboardqActivity.class));
    }
    public void openProcessorQuiz(View view) {
        startActivity(new Intent(this, ProcessorActivity.class));
    }
    public void openRamQuiz(View view) {
        startActivity(new Intent(this, RAMQuizActivity.class));
    }
    public void openStorageQuiz(View view) {
        startActivity(new Intent(this, StorageQuizActivity.class));
    }


}
